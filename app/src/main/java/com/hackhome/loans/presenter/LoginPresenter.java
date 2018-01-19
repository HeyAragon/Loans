package com.hackhome.loans.presenter;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.bean.UserInfo;
import com.hackhome.loans.common.rx.RxSchedulers;
import com.hackhome.loans.common.rx.subscriber.ErrorSubscriber;
import com.hackhome.loans.common.utils.AppConfig;
import com.hackhome.loans.common.utils.UserUtil;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.presenter.contract.ILoginContract;
import com.hackhome.loans.ui.base.BasePresenter;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import retrofit2.Response;

/**
 * desc:
 * author: aragon
 * date: 2018/1/3 0003.
 */
public class LoginPresenter extends BasePresenter<ILoginContract.ILoginModel,ILoginContract.ILoginView> {

    @Inject
    public LoginPresenter(ILoginContract.ILoginModel model, ILoginContract.ILoginView view) {
        super(model, view);
    }

    public void login(String name, String pass) {
        mModel.login(ApiConstants.TYPE_LOGIN, name, pass)
                .compose(RxSchedulers.<Response<ResponseBean>>applySchedulers())
                .subscribe(responseBeanResponse -> {
                    ResponseBean responseBean = responseBeanResponse.body();
                    if (responseBean == null) {
                        responseBean = new ResponseBean();
                        responseBean.setStatus("");
                    }
                    UserUtil.saveCookies(AppConfig.getInstance().getCurrentHttpUrl(), responseBeanResponse.headers());
                    mView.showResponse(responseBean,0);
                }, throwable -> {
                    ResponseBean responseBean = new ResponseBean();
                    responseBean.setStatus("");
                    mView.showResponse(responseBean,0);
                });
    }

    public void getUserInfo(Map<String,String> map) {
        mModel.getUserInfo(ApiConstants.TYPE_USER_INFO,map)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(userInfo -> mView.showResponse(userInfo,1), throwable -> {

                });
    }
}
