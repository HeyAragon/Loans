package com.hackhome.loans.presenter;

import android.os.Environment;
import android.text.TextUtils;

import com.hackhome.loans.BuildConfig;
import com.hackhome.loans.bean.PatchBean;
import com.hackhome.loans.bean.UpdateBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.rx.RxDataTransformer;
import com.hackhome.loans.common.rx.RxSchedulers;
import com.hackhome.loans.common.tinker.TinkerSharePreferenceUtils;
import com.hackhome.loans.common.utils.AppConfig;
import com.hackhome.loans.common.utils.SharedPreferencesUtils;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.presenter.contract.IMainContract;
import com.hackhome.loans.ui.base.BasePresenter;
import com.hackhome.loans.widget.UpdateDialog;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * desc:
 * author: aragon
 * date: 2018/1/22 0022.
 */
public class MainPresenter extends BasePresenter<IMainContract.IMainModel, IMainContract.IMainView> {

    private TinkerSharePreferenceUtils mInstance;

    @Inject
    public MainPresenter(IMainContract.IMainModel model, IMainContract.IMainView view) {
        super(model, view);
        mInstance = TinkerSharePreferenceUtils.getInstance();
    }

    public void checkNewVersion() {

        mModel.checkNewVersion(ApiConstants.TYPE_UPDATE)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(
                        updateBean ->
                                mView.showResponse(updateBean, Constants.ResponseType.TYPE_CHECK_NEW_VERSION),
                        throwable ->
                                mView.showResponse(null, 0)
                );
    }

    public void checkPatch() {
        mModel.checkPatch(ApiConstants.TYPE_PATCH)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(
                        patchBean -> {
                            int patch = patchBean.getPatchVersion().charAt(0);
                            int client = AppConfig.getInstance().getCurrentVersion().charAt(0);
                            ArrayList<String> patches = mInstance.getPatches();
                            if (patch == client &&
                                    !TextUtils.isEmpty(patchBean.getPatchUrl()) &&
                                    !patches.contains(patchBean.getPatchVersion())) {
                                FileDownloader.getImpl()
                                        .create(patchBean.getPatchUrl())
                                        .setListener(new FileDownloadListener())
                                        .setPath(mContext.getCacheDir() + "/patch/" + patchBean.getPatchName())
                                        .start();
                                mInstance.setCurrentPatchVersion(patchBean.getPatchVersion());
                            }
                        },
                        throwable -> checkPatch());
    }

    private class FileDownloadListener extends FileDownloadSampleListener {
        @Override
        protected void completed(BaseDownloadTask task) {
            super.completed(task);
            TinkerInstaller.onReceiveUpgradePatch(mContext, task.getPath());
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            super.error(task, e);
        }
    }
}
