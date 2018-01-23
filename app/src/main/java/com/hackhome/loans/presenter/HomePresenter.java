package com.hackhome.loans.presenter;

import android.provider.Settings;
import android.support.constraint.solver.Cache;
import android.text.TextUtils;
import android.util.Log;

import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.rx.RxSchedulers;
import com.hackhome.loans.common.rx.subscriber.ErrorSubscriber;
import com.hackhome.loans.common.rx.subscriber.ProgressSubscriber;
import com.hackhome.loans.common.utils.ACache;
import com.hackhome.loans.common.utils.CacheUtils;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.ui.base.BasePresenter;
import com.hackhome.loans.presenter.contract.IHomeContract;
import com.hackhome.loans.ui.home.HomeFragment;
import com.hackhome.loans.ui.loan.LoanFragment;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;
import org.reactivestreams.Subscriber;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * desc:
 * author: aragon
 * date: 2017/12/19 0019.
 */

public class HomePresenter extends BasePresenter<IHomeContract.IHomeModel,IHomeContract.IHomeView> {

    @Inject
    public HomePresenter(IHomeContract.IHomeModel model, IHomeContract.IHomeView view) {
        super(model, view);
    }

    public void getHomeData(String type, int sort, final int page,int ob,int loadType) {

        mModel.getHomeData(type,sort,page,ob)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(new ProgressSubscriber<DataBean>(mContext,mView,loadType) {
                    @Override
                    public void onNext(@NonNull DataBean dataBean) {

//                        KLog.e("aragon"+ dataBean.isSuccess());
                        if (dataBean.isSuccess()) {
                            if (page == 1) {
                                long timeMillis = System.currentTimeMillis();
                                Log.e("time", "onNext: "+timeMillis);
                                if (TextUtils.equals(type, ApiConstants.TYPE_HOME)) {
                                    CacheUtils.cacheHomeData(dataBean);
                                } else if (TextUtils.equals(type, ApiConstants.TYPE_LOAN) && sort == 2 && ob == 2) {
                                    CacheUtils.cacheLoanData(dataBean);

                                }
                                Log.e("time", "onNext: "+(System.currentTimeMillis()-timeMillis));
                                mView.showHomeData(dataBean, true);
                            } else {
                                mView.showHomeData(dataBean, false);
                            }
                        } else {
                            DataBean homeData = CacheUtils.getHomeDataCache();
                            DataBean loanData = CacheUtils.getLoanDataCache();
                            if (mView instanceof HomeFragment && homeData != null) {
                                mView.showHomeData(homeData,true);
                            } else if (mView instanceof LoanFragment && loanData != null) {
                                mView.showHomeData(loanData, false);
                            } else {
                                mView.showError("");
                            }
                        }
                    }
                });
    }

}
