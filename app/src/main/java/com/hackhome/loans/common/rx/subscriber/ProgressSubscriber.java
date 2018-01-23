package com.hackhome.loans.common.rx.subscriber;

import android.content.Context;
import android.view.View;

import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.exception.BaseException;
import com.hackhome.loans.common.rx.RxErrorHandler;
import com.hackhome.loans.common.utils.ACache;
import com.hackhome.loans.common.utils.CacheUtils;
import com.hackhome.loans.ui.base.IBase;
import com.hackhome.loans.ui.base.IBaseView;
import com.hackhome.loans.ui.home.HomeFragment;
import com.hackhome.loans.ui.loan.LoanFragment;

import java.net.UnknownHostException;

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
    private Context mContext;
    private RxErrorHandler mRxErrorHandler;

    protected ProgressSubscriber(Context context, IBase view, int loadType) {
        this.mView = view;
        this.mContext = context;
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

        DataBean homeData = CacheUtils.getHomeDataCache();
        DataBean loanData = CacheUtils.getLoanDataCache();





        e.printStackTrace();

        switch (mLoadType) {
            case Constants.LoadType.FIRST_LOAD:

                if (mView instanceof HomeFragment && homeData != null) {
                    ((HomeFragment) mView).showHomeData(homeData,true);
                    mView.dismissLoading();
                } else if (mView instanceof LoanFragment && loanData != null) {
                    ((LoanFragment) mView).showHomeData(loanData, false);
                    mView.dismissLoading();
                } else {
                    mView.showError(baseException.getDisplayMessage());
                    mRxErrorHandler.showErrorMessage(baseException);
                }

                break;
            case Constants.LoadType.REFRESH:
                mView.showResponse(baseException,mLoadType);
                mRxErrorHandler.showErrorMessage(baseException);
                break;
            case Constants.LoadType.LOAD_MORE:
                mView.showResponse(baseException,mLoadType);
                mRxErrorHandler.showErrorMessage(baseException);
                break;
        }

    }
}
