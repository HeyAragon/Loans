package com.hackhome.loans.presenter.model;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.bean.UserInfo;
import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.ILoginContract;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * desc:
 * author: aragon
 * date: 2018/1/3 0003.
 */
public class LoginModel implements ILoginContract.ILoginModel{

    private ApiService mApiService;

    public LoginModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<Response<ResponseBean>> login(String type, String name, String pass) {
        return mApiService.login(type,name,pass);
    }

    @Override
    public Observable<UserInfo> getUserInfo(String type,Map<String,String> map) {
        return mApiService.getUserInfo(type,map);
    }
}
