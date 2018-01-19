package com.hackhome.loans.presenter;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.common.rx.RxSchedulers;
import com.hackhome.loans.common.rx.subscriber.ProgressSubscriber;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.presenter.contract.IRegisterContract;
import com.hackhome.loans.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;

/**
 * desc:
 * author: aragon
 * date: 2018/1/3 0003.
 */
public class RegisterPresenter extends BasePresenter<IRegisterContract.IRegisterModel,IRegisterContract.IRegisterView> {

    @Inject
    public RegisterPresenter(IRegisterContract.IRegisterModel model, IRegisterContract.IRegisterView view) {
        super(model, view);
    }

    /**
     * 获取验证码
     */
    public void getAuthCode(String name) {
        mModel.getAuthCodeRelated(ApiConstants.TYPE_REGISTER_GET_AUTH_CODE, name)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(responseBean -> mView.showResponse(responseBean,ApiConstants.TYPE_REGISTER_GET_AUTH_CODE), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    /**
     * 注册
     */
    public void register(String name, String code, String pass1, String pass2) {
        mModel.register(ApiConstants.TYPE_REGISTER,name,code,pass1,pass2 )
                .compose(RxSchedulers.applySchedulers())
                .subscribe(responseBean -> mView.showResponse(responseBean,ApiConstants.TYPE_REGISTER), throwable -> {

                });
    }
}
