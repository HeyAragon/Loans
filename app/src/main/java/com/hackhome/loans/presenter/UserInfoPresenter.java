package com.hackhome.loans.presenter;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.rx.RxSchedulers;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.presenter.contract.IUserInfoContract;
import com.hackhome.loans.ui.base.BasePresenter;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;

/**
 * desc:
 * author: aragon
 * date: 2018/1/5 0005.
 */
public class UserInfoPresenter extends BasePresenter<IUserInfoContract.IUserInfoModel,IUserInfoContract.UserInfoView> {

    @Inject
    public UserInfoPresenter(IUserInfoContract.IUserInfoModel model, IUserInfoContract.UserInfoView view) {
        super(model, view);
    }

    public void changeNickOrGenderOrPassword(Map<String, String> map,String apiType ,final int responseType) {
        mModel.changeSexOrNick(apiType, map)
                .compose(RxSchedulers.<ResponseBean>applySchedulers())
                .subscribe(responseBean -> mView.showResponse(responseBean, responseType), throwable -> mView.showResponse(throwable.getMessage(),responseType));

    }

    public void uploadPic(String apiType,Map<String, String> map, MultipartBody.Part filePart,
                          final int responseType) {
        mModel.uploadPic(apiType, map, filePart)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(responseBean -> mView.showResponse(responseBean,responseType), throwable -> {

                });

    }
}
