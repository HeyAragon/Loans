package com.hackhome.loans.common.rx.subscriber;

import android.content.Context;

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

public abstract class ErrorSubscriber<T> implements Observer<T> {

    private IBaseView mView;

    private RxErrorHandler mRxErrorHandler;

    protected ErrorSubscriber(Context context, IBaseView view) {
        this.mView = view;
        mRxErrorHandler = new RxErrorHandler(context);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
//        mView.showLoading();
    }


    @Override
    public void onComplete() {
//        mView.dismissLoading();
//        mView.showResponse();
    }

    @Override
    public void onError(@NonNull Throwable e) {

        BaseException baseException = mRxErrorHandler.handleError(e);
        mRxErrorHandler.showErrorMessage(baseException);
//        mView.showResponse(baseException.getDisplayMessage());
        e.printStackTrace();
    }
}
