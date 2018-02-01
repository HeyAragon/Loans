package com.hackhome.loans.common.download;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;

import com.hackhome.loans.LoanApplication;
import com.hackhome.loans.bean.DownloadRecordModel;
import com.hackhome.loans.bean.ReturnValueBean;
import com.hackhome.loans.common.eventbus.EB;
import com.hackhome.loans.common.eventbus.EventItem;
import com.hackhome.loans.common.tinker.TinkerLoanApplication;
import com.hackhome.loans.common.utils.AppConfig;
import com.hackhome.loans.common.utils.AppUtils;
import com.hackhome.loans.common.utils.DialogUtils;
import com.hackhome.loans.common.utils.NetworkUtils;
import com.hackhome.loans.common.utils.PermissionUtil;
import com.hackhome.loans.common.utils.ToastUtils;
import com.hackhome.loans.greendao.DaoSession;
import com.hackhome.loans.widget.DownloadProgressButton;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.socks.library.KLog;

import java.io.File;
import java.util.List;

/**
 * desc:
 * author: aragon
 * date: 2018/1/9 0009.
 */
public class DownloadHelperT {
    //进度刷新间隔
    public static final int PROGRESS_REFRESH_TIME = 500;
    public static final String DOWNLOAD_ROOT_PATH = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "wnqk" + File.separator + "download" + File.separator;
    private final DaoSession mDaoSession;
    private final List<DownloadRecordModel> mDownloadRecordModelList;

    private SparseArray<DownloadHelperBuilder> mBuilderMap;
    private SparseArray<BaseDownloadTask> mTaskMap;

    private long lastRefreshTime;

    private static class HelperHolder {
        public static final DownloadHelperT instance = new DownloadHelperT();
    }

    private DownloadHelperT() {
        mBuilderMap = new SparseArray<>();
        mTaskMap = new SparseArray<>();

        mDaoSession = TinkerLoanApplication.getTinkerApplication().getDaoSession();
        mDownloadRecordModelList = mDaoSession.getDownloadRecordModelDao().queryBuilder().list();
        if (mDownloadRecordModelList.size() > 0) {
            for (DownloadRecordModel model : mDownloadRecordModelList) {
                DownloadHelperBuilder builder = new DownloadHelperBuilder()
                        .icon(model.getIconUrl())
                        .setFileName(model.getName())
                        .setUrl(model.getUrl())
                        .pkg(model.getPkgName());
                mBuilderMap.put(model.getTaskId(), builder);
            }
        }

    }


    public static DownloadHelperT getInstance() {
        return HelperHolder.instance;
    }

    public void prePareDownload(DownloadHelperBuilder builder) {
        int taskId = FileDownloadUtils.generateId(builder.mDownloadUrl, builder.mDownloadPath);
        mBuilderMap.put(taskId, builder);
        if (builder.mDownloadProgressButton != null) {
            initButton(builder.mDownloadProgressButton, taskId, builder);
        }
    }

    private void initButton(final DownloadProgressButton button, int taskId, final DownloadHelperBuilder builder) {

        if (DownloadTaskManager.getInstance().isReady()) {
            int status = DownloadTaskManager.getInstance().getStatus(taskId, builder.mDownloadPath);
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                button.setState(DownloadProgressButton.STATE_DOWNLOADING);
                button.setCurrentText("waiting");
            } else if (!new File(builder.mDownloadPath).exists()
                     &&!new File(FileDownloadUtils.getTempPath( builder.mDownloadPath)).exists()
                    ) {
                button.setState(DownloadProgressButton.STATE_NORMAL);
                button.setCurrentText("下载");
            } else if (status == FileDownloadStatus.completed) {
                button.setState(DownloadProgressButton.STATE_FINISH);
                if (!AppUtils.isInstallApp(builder.mPkg)) {
                    button.setCurrentText("安装");
                } else {
                    button.setCurrentText("打开");
                    button.setState(DownloadProgressButton.STATE_INSTALLED);
                }
            } else if (status == FileDownloadStatus.progress) {
                button.setState(DownloadProgressButton.STATE_DOWNLOADING);
                float percent = FileDownloader.getImpl().getSoFar(taskId) / (float) FileDownloader.getImpl().getTotal(taskId);
                button.setProgress(percent * 100);
                button.setProgressText("", percent);
            } else if (status == FileDownloadStatus.paused) {
                float percent = FileDownloader.getImpl().getSoFar(taskId) / (float) FileDownloader.getImpl().getTotal(taskId);
                button.setState(DownloadProgressButton.STATE_PAUSE);
                button.setProgress(percent * 100);
                button.setCurrentText("继续");
            }
        }


        button.setOnClickListener(v -> {

            final DownloadRecordModel model = DownloadTaskManager
                    .getInstance()
                    .addTask(builder.mDownloadUrl, builder.mDownloadPath,
                            builder.mFileName, 0, builder.mIconUrl, builder.mPkg, builder.mReturnValueBean);
            final int state = button.getState();
            PermissionUtil.requestPermissions((Activity) builder.mContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(permission -> {
                        if (permission.granted) {
                            switch (state) {
                                case DownloadProgressButton.STATE_NORMAL:
                                    button.updateButtonId(model.getTaskId());
                                    if (NetworkUtils.isWifiConnected()||AppConfig.getInstance().isUseMobileNetDownload()) {
                                        start(builder);
                                    } else if (NetworkUtils.getDataEnabled()) {
                                        DialogUtils.isContinueDownload(builder);
                                    } else {
                                        ToastUtils.showToast("当前未连接网络！");
                                    }

                                    break;
                                case DownloadProgressButton.STATE_DOWNLOADING:

                                    FileDownloader.getImpl().pause(model.getTaskId());

                                    break;
                                case DownloadProgressButton.STATE_PAUSE:

                                    if (NetworkUtils.isWifiConnected()||AppConfig.getInstance().isUseMobileNetDownload()) {
                                        start(builder);
                                    } else if (NetworkUtils.getDataEnabled()) {
                                        DialogUtils.isContinueDownload(builder);
                                    } else {
                                        ToastUtils.showToast("当前未连接网络！");
                                    }
                                    break;

                                case DownloadProgressButton.STATE_FINISH:
                                    AppUtils.installApp(builder.mDownloadPath, AppUtils.AUTHORITIES);
                                    break;
                                case DownloadProgressButton.STATE_INSTALLED:
                                    AppUtils.launchApp(builder.mPkg);
                                    break;
                            }
                        }
                    });

        });
    }

    public BaseDownloadTask start(DownloadHelperBuilder builder) {
        BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(builder.mDownloadUrl)
                .setPath(builder.mDownloadPath)
                .setCallbackProgressTimes(PROGRESS_REFRESH_TIME)
                .setTag(builder.mDownloadProgressButton)
                .setListener(mSampleFileDownloadListener);
        baseDownloadTask.start();
        mTaskMap.put(baseDownloadTask.getId(), baseDownloadTask);
        return baseDownloadTask;
    }

    private FileDownloadListener mSampleFileDownloadListener = new FileDownloadSampleListener() {

        public DownloadProgressButton checkCurrentBtn(BaseDownloadTask task) {
//            DownloadProgressButton downloadProgressButton = (DownloadProgressButton) task.getTag();
//            int buttonId = downloadProgressButton.getButtonId();
//            if (buttonId != task.getId()) {
//                return null;
//            }
            DownloadProgressButton downloadProgressButton = mBuilderMap.get(task.getId()).mDownloadProgressButton;
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastRefreshTime > 1000) {
                KLog.e("aragon", "status:" + task.getStatus() + "------" + mBuilderMap.get(task.getId()).mFileName);
                EB.getInstance().send(EventItem.DOWNLOAD_OBJECT, EventItem.REFRESH_PROGRESS);
                lastRefreshTime = currentTime;
            } else if (task.getStatus() == FileDownloadStatus.completed) {
                EB.getInstance().send(EventItem.DOWNLOAD_OBJECT, EventItem.REFRESH_PROGRESS, "status:" + task.getStatus());
            }

            if (downloadProgressButton == null) {
                return null;
            }
            return downloadProgressButton;
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            DownloadProgressButton downloadProgressButton = checkCurrentBtn(task);
            if (downloadProgressButton == null) {
                return;
            }

            downloadProgressButton.setCurrentText("waiting");
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            DownloadProgressButton downloadProgressButton = checkCurrentBtn(task);
            if (downloadProgressButton == null) {
                return;
            }
            downloadProgressButton.setState(DownloadProgressButton.STATE_DOWNLOADING);
            float percent = soFarBytes / (float) totalBytes;
            downloadProgressButton.setProgressText("", percent);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            DownloadProgressButton downloadProgressButton = checkCurrentBtn(task);
            if (downloadProgressButton == null) {
                return;
            }
            if (!AppUtils.isInstallApp(mBuilderMap.get(task.getId()).mPkg)) {
                downloadProgressButton.setCurrentText("安装");
                AppUtils.installApp(task.getPath(), AppUtils.AUTHORITIES);
//                AppUtils.installAppSilent(task.getPath());
                downloadProgressButton.setState(DownloadProgressButton.STATE_FINISH);

                KLog.e("aragon", task.getPath());
            } else {
                downloadProgressButton.setCurrentText("打开");
                downloadProgressButton.setState(DownloadProgressButton.STATE_INSTALLED);
            }
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            DownloadProgressButton downloadProgressButton = checkCurrentBtn(task);
            if (downloadProgressButton == null) {
                return;
            }
            downloadProgressButton.setState(DownloadProgressButton.STATE_PAUSE);
            downloadProgressButton.setCurrentText("继续");
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {

        }

        @Override
        protected void warn(BaseDownloadTask task) {

        }
    };

    public SparseArray<BaseDownloadTask> getTasks() {
        return mTaskMap;
    }

    public BaseDownloadTask getTaskById(int id) {
        return mTaskMap.get(id);
    }

    public SparseArray<DownloadHelperBuilder> getBuilders() {
        return mBuilderMap;
    }

    public DownloadHelperBuilder getBuilderById(int id) {
        return mBuilderMap.get(id);
    }

    public static class DownloadHelperBuilder {
        public DownloadProgressButton mDownloadProgressButton;
        public Context mContext;
        public String mPkg;
        public String mFileName;
        public String mDownloadUrl;
        public String mDownloadPath;
        public String mIconUrl;
        public ReturnValueBean mReturnValueBean;

        public DownloadHelperBuilder bindBtn(DownloadProgressButton btn) {
            this.mDownloadProgressButton = btn;
            mDownloadProgressButton.setBuilder(this);
            return this;
        }

        public DownloadHelperBuilder setFileName(String fileName) {
            this.mFileName = fileName;
            this.mDownloadPath = DOWNLOAD_ROOT_PATH + fileName + ".apk";
            return this;
        }

        public DownloadHelperBuilder setUrl(String url) {
            this.mDownloadUrl = url;
            return this;
        }

        public DownloadHelperBuilder with(Context context) {
            this.mContext = context;
            return this;
        }

        public DownloadHelperBuilder icon(String icon) {
            this.mIconUrl = icon;
            return this;
        }

        public DownloadHelperBuilder pkg(String pkg) {
            this.mPkg = pkg;
            return this;
        }

        public DownloadHelperBuilder bean(ReturnValueBean bean) {
            this.mReturnValueBean = bean;
            return this;
        }

//        public DownloadHelperT build() {
//
//            return DownloadHelperT.getInstance(this);
//        }
    }


}
