package com.hackhome.loans.presenter.contract;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.ui.base.IBase;
import com.hackhome.loans.ui.base.IBaseView;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Query;

/**
 * desc:
 * author: aragon
 * date: 2018/1/3 0003.
 */
public interface IRegisterContract {

    interface IRegisterModel{

        /**
         * 验证码相关
         */
        Observable<ResponseBean> getAuthCodeRelated(String type, String name);

        /**
         *注册
         */
        Observable<ResponseBean> register(String type, String name, String code, String pass, String passs);

    }

    interface IRegisterView extends IBase {

        void showResponse(ResponseBean responseBean,String type);
    }

}
