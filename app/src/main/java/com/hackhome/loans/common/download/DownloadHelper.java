package com.hackhome.loans.common.download;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.hackhome.loans.bean.DownloadRecordModel;
import com.hackhome.loans.common.eventbus.EB;
import com.hackhome.loans.common.eventbus.EventItem;
import com.hackhome.loans.common.utils.AppUtils;
import com.hackhome.loans.common.utils.PermissionUtil;
import com.hackhome.loans.widget.DownloadProgressButton;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.socks.library.KLog;
import com.tbruyelle.rxpermissions2.Permission;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.functions.Consumer;

/**
 * desc:
 * author: aragon
 * date: 2017/11/8 0008.
 */
public class DownloadHelper {
    //下载根目录
    public static final String DOWNLOAD_ROOT_PATH = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "wnqk" + File.separator+"download"+File.separator;
    //进度刷新间隔
    public static final int PROGRESS_REFRESH_TIME = 1000;

    private static DownloadBuilder mDownloadBuilder;
    private SparseArray<BaseDownloadTask> mSparseArray;
    private DownloadProgressButton mDownloadProgressButton;
    private String mPkg;
    private String mDownloadUrl;
    private String mDownloadPath;
    private String mFileName;
    private String mIconUrl;
    private Context mContext;

    private ArrayList<DownloadRecordModel> mDownloadRecordModels;



    private DownloadHelper(DownloadBuilder builder, String url, String fileName,DownloadProgressButton downloadProgressButton,Context context,String icon,String pkg) {
        mDownloadBuilder = builder;
        this.mContext = context;
        this.mIconUrl = icon;
        this.mFileName = fileName;
        this.mDownloadUrl = url;
        this.mPkg = pkg;
        this.mDownloadProgressButton = downloadProgressButton;
        this.mDownloadPath = DOWNLOAD_ROOT_PATH + fileName+".apk";

    }

    public static DownloadBuilder builder() {
        return new DownloadBuilder();
    }

    private BaseDownloadTask start(DownloadProgressButton btn) {
        BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(btn.getBuilder().mDownloadUrl)
                .setPath(btn.getBuilder().mDownloadPath)
                .setCallbackProgressTimes(PROGRESS_REFRESH_TIME)
                .setTag(btn)
                .setListener(mSampleFileDownloadListener);
        baseDownloadTask.start();
        return baseDownloadTask;
    }

    public void addClickEvent(final int position) {

        final int taskId = FileDownloadUtils.generateId(mDownloadProgressButton.getBuilder().mDownloadUrl, mDownloadProgressButton.getBuilder().mDownloadPath);
        mDownloadProgressButton.updateButtonId(taskId);
        mDownloadProgressButton.setCurrentText("下载");
        if (DownloadTaskManager.getInstance().isReady()) {
//            Log.i("aragonhy", "addClickEvent:statusIn=" + status + "----pos=" + position);
            int status = DownloadTaskManager.getInstance().getStatus(taskId, mDownloadProgressButton.getBuilder().mDownloadPath);
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                mDownloadProgressButton.setState(DownloadProgressButton.STATE_DOWNLOADING);
                mDownloadProgressButton.setCurrentText("waiting");
            } else if (!new File(mDownloadProgressButton.getBuilder().mDownloadPath).exists() &&
                    !new File(FileDownloadUtils.getTempPath(mDownloadProgressButton.getBuilder().mDownloadPath)).exists()) {
                mDownloadProgressButton.setState(DownloadProgressButton.STATE_NORMAL);
                mDownloadProgressButton.setCurrentText("下载");
            } else if (status == FileDownloadStatus.completed) {
                mDownloadProgressButton.setState(DownloadProgressButton.STATE_FINISH);
                if (!AppUtils.isInstallApp(mDownloadProgressButton.getBuilder().mPkg)) {
                    mDownloadProgressButton.setCurrentText("安装");
                } else {
                    mDownloadProgressButton.setCurrentText("打开");
                    mDownloadProgressButton.setState(DownloadProgressButton.STATE_INSTALLED);
                }

            } else if (status == FileDownloadStatus.progress) {
                mDownloadProgressButton.setState(DownloadProgressButton.STATE_DOWNLOADING);
                float percent = DownloadTaskManager.getInstance().getSoFar(taskId) / (float) DownloadTaskManager.getInstance().getTotal(taskId);
                mDownloadProgressButton.setProgressText("", percent);
            } else if (status == FileDownloadStatus.paused) {
                float percent = DownloadTaskManager.getInstance().getSoFar(taskId) / (float) DownloadTaskManager.getInstance().getTotal(taskId);
                mDownloadProgressButton.setState(DownloadProgressButton.STATE_PAUSE);
                mDownloadProgressButton.setProgress(percent * 100);
                mDownloadProgressButton.setCurrentText("继续");
            }
        }
        mDownloadProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DownloadRecordModel model = DownloadTaskManager.getInstance().addTask(mDownloadUrl, mDownloadPath, mFileName, position,mIconUrl,mPkg,null);
                final int state = mDownloadProgressButton.getState();
                PermissionUtil.requestPermissions((Activity) mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                if (permission.granted) {
                                    switch (state) {
                                        case DownloadProgressButton.STATE_NORMAL:
                                            mDownloadProgressButton.updateButtonId(model.getTaskId());
                                            Log.i("aragonhy", "addClickEvent: id=" + taskId + "----modelTaskId=" + model.getTaskId());
                                            start(mDownloadProgressButton);
                                            break;
                                        case DownloadProgressButton.STATE_DOWNLOADING:
                                            FileDownloader.getImpl().pause(model.getTaskId());
                                            break;
                                        case DownloadProgressButton.STATE_PAUSE:
                                            start(mDownloadProgressButton);
                                            break;

                                        case DownloadProgressButton.STATE_FINISH:
                                            break;
                                        case DownloadProgressButton.STATE_INSTALLED:
                                            AppUtils.launchApp(mPkg);
                                            break;
                                    }
                                }
                            }
                        });

            }
        });
    }

    private FileDownloadListener mSampleFileDownloadListener = new  FileDownloadSampleListener() {

        public DownloadProgressButton checkCurrentBtn(BaseDownloadTask task) {
            DownloadProgressButton downloadProgressButton = (DownloadProgressButton) task.getTag();
            int buttonId = downloadProgressButton.getButtonId();
            if (buttonId != task.getId()) {
                return null;
            }

            EB.getInstance().send(EventItem.DOWNLOAD_OBJECT,0);
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
            float percent = soFarBytes /(float) totalBytes;
            downloadProgressButton.setProgressText("", percent);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            DownloadProgressButton downloadProgressButton = checkCurrentBtn(task);
            if (downloadProgressButton == null) {
                return;
            }
            if (!AppUtils.isInstallApp("com.zzoo.goosebumps")) {
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

    public static class DownloadBuilder {

        private DownloadProgressButton mDownloadProgressButton;
        private Context mContext;
        private String mPkg;
        private String mFileName;
        private String mDownloadUrl;
        private String mDownloadPath;
        private String mIconUrl;
        private int mBtnPosition;

        public DownloadBuilder bindBtn(DownloadProgressButton btn) {
            this.mDownloadProgressButton = btn;
            mDownloadProgressButton.setBuilder(this);
            return this;
        }

        public DownloadBuilder setFileName(String fileName) {
            this.mFileName = fileName;
            this.mDownloadPath = DOWNLOAD_ROOT_PATH + fileName + ".apk";
            return this;
        }

        public DownloadBuilder setUrl(String url) {
            this.mDownloadUrl = url;
            return this;
        }

        public DownloadBuilder with(Context context) {
            this.mContext = context;
            return this;
        }

        public DownloadBuilder bindBtnPosition(int position) {
            this.mBtnPosition = position;
            return this;
        }

        public DownloadBuilder icon(String icon) {
            this.mIconUrl = icon;
            return this;
        }

        public DownloadBuilder pkg(String pkg) {
            this.mPkg = pkg;
            return this;
        }

        public DownloadHelper build() {

            return new DownloadHelper(this, mDownloadUrl, mFileName,mDownloadProgressButton,mContext,mIconUrl,mPkg);
        }

    }
}
