package com.hackhome.loans.presenter;


import android.content.Context;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.rx.RxSchedulers;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.presenter.contract.ICommonContract;
import com.hackhome.loans.presenter.model.CommonModel;
import com.hackhome.loans.ui.base.IBase;
import com.hackhome.loans.ui.base.IBaseView;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */
public class CommonPresenter {

    protected CommonModel mModel;

    protected IBase mView;

    @Inject
    protected Context mContext;

    @Inject
    public CommonPresenter(CommonModel model, IBase view) {
        mModel = model;
        mView = view;
    }

    public void getAuthCode(String apiType,String name) {
        mModel.getAuthCodeRelated(ApiConstants.TYPE_REGISTER_GET_AUTH_CODE, name)
                .compose(RxSchedulers.<ResponseBean>applySchedulers())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.showResponse(responseBean, Constants.ResponseType.TYPE_GET_AUTH_CODE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public void findPassword(String name, String code, String pass1, String pass2, final int responseType) {
        mModel.findPassword(ApiConstants.TYPE_FIND_USER_PASSWORD,name,code,pass1,pass2 )
                .compose(RxSchedulers.<ResponseBean>applySchedulers())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.showResponse(responseBean,responseType);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }



}
