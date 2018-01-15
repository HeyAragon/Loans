package com.hackhome.loans.presenter.model;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.IForgetPassContract;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * desc:
 * author: aragon
 * date: 2018/1/6 0006.
 */
public class ForgetPassModel implements IForgetPassContract.IForgetPassModel {

    private ApiService mApiService;

    public ForgetPassModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<ResponseBean> findPassword(String type, String name, String code, String pass, String passs) {
        return mApiService.register(type,name,code,pass,passs);
    }

    @Override
    public Observable<ResponseBean> getAuthCodeRelated(@Query("s") String type, @Query("name") String name) {
        return mApiService.getAuthCodeRelated(type,name);
    }
}
