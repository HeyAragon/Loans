package com.hackhome.loans.presenter.model;

import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.ICommonContract;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * desc:
 * author: aragon
 * date: 2017/12/19 0019.
 */

public class CommonModel implements ICommonContract.ICommonModel {

    private ApiService mApiService;

    public CommonModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<ResponseBean> getAuthCodeRelated(@Query("s") String type, @Query("name") String name) {
        return mApiService.getAuthCodeRelated(type,name);
    }

    @Override
    public Observable<ResponseBean> findPassword(String type, String name, String code, String pass, String passs) {
        return mApiService.register(type,name,code,pass,passs);
    }
}
