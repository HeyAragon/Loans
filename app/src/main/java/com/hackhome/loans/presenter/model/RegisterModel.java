package com.hackhome.loans.presenter.model;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.IRegisterContract;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * desc: 注册model
 * author: aragon
 * date: 2018/1/3 0003.
 */
public class RegisterModel implements IRegisterContract.IRegisterModel{

    private ApiService mApiService;

    public RegisterModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<ResponseBean> getAuthCodeRelated(String type, String name) {
        return mApiService.getAuthCodeRelated(type, name);
    }

    @Override
    public Observable<ResponseBean> register(String type, String name, String code, String pass, String passs) {
        return mApiService.register(type,name,code,pass,passs);
    }

}
