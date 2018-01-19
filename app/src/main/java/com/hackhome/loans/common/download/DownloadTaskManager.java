package com.hackhome.loans.common.download;

import android.util.Log;
import android.util.SparseArray;

import com.hackhome.loans.LoanApplication;
import com.hackhome.loans.bean.DownloadRecordModel;
import com.hackhome.loans.bean.ReturnValueBean;
import com.hackhome.loans.common.utils.ToastUtils;
import com.hackhome.loans.greendao.DaoMaster;
import com.hackhome.loans.greendao.DaoSession;
import com.hackhome.loans.greendao.DownloadRecordModelDao;
import com.hackhome.loans.ui.LoanDetailActivity;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.socks.library.KLog;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by aragon on 2017/11/21 0021.
 */
public class DownloadTaskManager {

    private int mTaskId;

    private FileDownloadConnectListener listener;
    private List<DownloadRecordModel> mDownloadRecordModels;

    private SparseArray<BaseDownloadTask> mSparseArray;
    private DaoSession mDaoSession;
    private DownloadRecordModelDao mDownloadRecordModelDao;

    public static final class DownloadManagerHolder {
        public static final DownloadTaskManager INSTANCE = new DownloadTaskManager();

    }

    public static DownloadTaskManager getInstance() {

        return DownloadManagerHolder.INSTANCE;
    }

    private DownloadTaskManager() {
        initDataBase();
    }

    private void initDataBase() {
        mDaoSession = LoanApplication.getInstance().getDaoSession();
        mDownloadRecordModelDao = mDaoSession.getDownloadRecordModelDao();
        mDownloadRecordModels = mDownloadRecordModelDao.queryBuilder().list();
        Log.i("aragonh", "initDataBase: size=" + mDownloadRecordModels.size());
        mSparseArray = new SparseArray<>();
//        if (mDownloadRecordModels.size() > 0) {
//            for (DownloadRecordModel model : mDownloadRecordModels) {
//                addDownloadRecord(model.getTaskId(), model.getBtnPos());
//            }
//        }

    }

    public DownloadRecordModel addTask(String url, String path, String fileName, int position, String icon, String pkg, ReturnValueBean bean) {

        mTaskId = FileDownloadUtils.generateId(url, path);
        DownloadRecordModel model = getModelById(mTaskId);
        if (model != null) {
            return model;
        }
        DownloadRecordModel modelNew = new DownloadRecordModel();
        modelNew.setTaskId(mTaskId);
        modelNew.setBtnPos(position);
        modelNew.setName(fileName);
        modelNew.setPath(path);
        modelNew.setUrl(url);
        modelNew.setIconUrl(icon);
        modelNew.setPkgName(pkg);

        insertIntoDb(modelNew, bean);

        mDownloadRecordModels.add(0,modelNew);
        mDownloadRecordModelDao.insert(modelNew);
        return modelNew;
    }

    public List<DownloadRecordModel> getDownloadRecordModels() {
        return mDownloadRecordModels;
    }

    public void addDownloadRecord(int taskId, BaseDownloadTask task) {
//        if (getRecord(taskId) == task) {
//            return;
//        }
        mSparseArray.put(taskId, task);
    }

    private void insertIntoDb(DownloadRecordModel bean,ReturnValueBean returnValueBean) {

        bean.setAdvertiser(returnValueBean.getAdvertiser());
        bean.setCreated(returnValueBean.getCreated());
        bean.setStartLoanMoney(returnValueBean.getStartLoanMoney());
        bean.setEndLoanMoney(returnValueBean.getEndLoanMoney());
        bean.setInterestRateDay(returnValueBean.getInterestRateDay());
        bean.setPackageName(returnValueBean.getPackageName());
        bean.setProductAndroidUrl(returnValueBean.getProductAndroidUrl());
        bean.setProductH5Url(returnValueBean.getProductH5Url());
        bean.setProductIntroduce(returnValueBean.getProductIntroduce());
        bean.setProductImg(returnValueBean.getProductImg());
        bean.setSecuredLoan(returnValueBean.getSecuredLoan());
        bean.setEndLoanMoney(returnValueBean.getEndLoanMoney());
        bean.setProductCharacteristic(returnValueBean.getProductCharacteristic());
        bean.setProductName(returnValueBean.getProductName());
        bean.setEndLoanTime(returnValueBean.getEndLoanTime());
        bean.setStartLoanTime(returnValueBean.getStartLoanTime());
        bean.setSuccessRate(returnValueBean.getSuccessRate());
    }

    public DownloadRecordModel getModelById(int id) {
        for (DownloadRecordModel model : mDownloadRecordModels) {
            if (model.getTaskId() == id) {
                return model;
            }
        }
        return null;
    }

    public int getStatus(int id, String path) {
        return FileDownloader.getImpl().getStatus(id, path);
    }

    public long getTotal(int id) {
        return FileDownloader.getImpl().getTotal(id);
    }


    public boolean isDownloaded(int status) {
        return status == FileDownloadStatus.completed;
    }

    public long getSoFar(int id) {
        return FileDownloader.getImpl().getSoFar(id);
    }

    private void registerServiceConnectionListener() {

        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
        }

        listener = new FileDownloadConnectListener() {

            @Override
            public void connected() {

//                KLog.e("aragon"+Thread.currentThread()==Thread.currentThread()=);
//                reference.get().postNotifyDataChanged();
            }

            @Override
            public void disconnected() {


//                reference.get().postNotifyDataChanged();
            }
        };

        FileDownloader.getImpl().addServiceConnectListener(listener);
    }

    private void unregisterServiceConnectionListener() {
        FileDownloader.getImpl().removeServiceConnectListener(listener);
        listener = null;
    }

    public void onCreate() {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            registerServiceConnectionListener();
        }
    }

    public void onDestroy() {
        unregisterServiceConnectionListener();
//            releaseTask();
    }

    public boolean isReady() {
        return FileDownloader.getImpl().isServiceConnected();
    }

}
