package com.hackhome.loans.presenter;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.rx.RxSchedulers;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.presenter.contract.ICommonContract;
import com.hackhome.loans.presenter.contract.IForgetPassContract;
import com.hackhome.loans.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * desc:
 * author: aragon
 * date: 2018/1/6 0006.
 */
public class ForgetPassPresenter extends BasePresenter<IForgetPassContract.IForgetPassModel,IForgetPassContract.IForgetPassView> {

    @Inject
    public ForgetPassPresenter(IForgetPassContract.IForgetPassModel model, IForgetPassContract.IForgetPassView view) {
        super(model, view);
    }

    public void findPassword(String name, String code, String pass1, String pass2, final int responseType) {
        mModel.findPassword(ApiConstants.TYPE_FIND_USER_PASSWORD,name,code,pass1,pass2 )
                .compose(RxSchedulers.<ResponseBean>applySchedulers())
                .subscribe(responseBean -> mView.showResponse(responseBean,responseType), throwable -> {

                });
    }

    public void getAuthCode(String apiType,String name) {
        mModel.getAuthCodeRelated(ApiConstants.TYPE_FIND_PASS_AUTH_CODE, name)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(responseBean -> mView.showResponse(responseBean, Constants.ResponseType.TYPE_GET_AUTH_CODE), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ResponseBean responseBean = new ResponseBean();
                        responseBean.setInfo("获取验证码失败");
                        mView.showResponse(responseBean,Constants.ResponseType.TYPE_GET_AUTH_CODE);
                    }
                });
    }
}
