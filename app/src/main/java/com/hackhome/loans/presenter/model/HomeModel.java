package com.hackhome.loans.presenter.model;

import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.IHomeContract;

import io.reactivex.Observable;

/**
 * desc:
 * author: aragon
 * date: 2017/12/19 0019.
 */

public class HomeModel implements IHomeContract.IHomeModel {

    private ApiService mApiService;

    public HomeModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<DataBean> getHomeData(String type,int sort,int page,int ob) {
        return mApiService.getHomeData(type,sort,page,ob);
    }

}
