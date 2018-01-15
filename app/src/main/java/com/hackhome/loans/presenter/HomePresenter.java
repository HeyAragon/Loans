package com.hackhome.loans.presenter;

import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.common.rx.RxSchedulers;
import com.hackhome.loans.common.rx.subscriber.ErrorSubscriber;
import com.hackhome.loans.common.rx.subscriber.ProgressSubscriber;
import com.hackhome.loans.ui.base.BasePresenter;
import com.hackhome.loans.presenter.contract.IHomeContract;
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
                .compose(RxSchedulers.<DataBean>applySchedulers())

                .subscribe(new ProgressSubscriber<DataBean>(mContext,mView,loadType) {
                    @Override
                    public void onNext(@NonNull DataBean dataBean) {

//                        KLog.e("aragon"+ dataBean.isSuccess());
                        if (dataBean.isSuccess()) {
                            if (page == 1) {
                                mView.showHomeData(dataBean, true);
                            } else {
                                mView.showHomeData(dataBean, false);
                            }
                        } else {
                            mView.showError("");
                        }
                    }
                });
    }

}
