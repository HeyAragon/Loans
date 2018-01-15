package com.hackhome.loans.common.rx.subscriber;

import android.content.Context;
import android.view.View;

import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.exception.BaseException;
import com.hackhome.loans.common.rx.RxErrorHandler;
import com.hackhome.loans.ui.base.IBase;
import com.hackhome.loans.ui.base.IBaseView;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */

public abstract class ProgressSubscriber<T> implements Observer<T> {

    private IBase mView;
    private int mLoadType = -1;
    private RxErrorHandler mRxErrorHandler;

    protected ProgressSubscriber(Context context, IBase view, int loadType) {
        this.mView = view;
        this.mLoadType = loadType;
        mRxErrorHandler = new RxErrorHandler(context);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

        switch (mLoadType) {
            case Constants.LoadType.FIRST_LOAD:
                mView.showLoading();
                break;
            case Constants.LoadType.REFRESH:
                break;
            case Constants.LoadType.LOAD_MORE:
                break;
        }
    }


    @Override
    public void onComplete() {
        switch (mLoadType) {
            case Constants.LoadType.FIRST_LOAD:
                mView.dismissLoading();
                break;
            case Constants.LoadType.REFRESH:
                mView.showResponse("Refresh_Completed",mLoadType);
                break;
            case Constants.LoadType.LOAD_MORE:
                mView.showResponse("Load_More_Completed",mLoadType);
                break;
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {

        BaseException baseException = mRxErrorHandler.handleError(e);
        mRxErrorHandler.showErrorMessage(baseException);

        e.printStackTrace();

        switch (mLoadType) {
            case Constants.LoadType.FIRST_LOAD:
                mView.showError(baseException.getDisplayMessage());
                break;
            case Constants.LoadType.REFRESH:
                mView.showResponse(baseException,mLoadType);
                break;
            case Constants.LoadType.LOAD_MORE:
                mView.showResponse(baseException,mLoadType);
                break;
        }

    }
}
