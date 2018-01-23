package com.hackhome.loans.presenter.model;

import com.hackhome.loans.bean.PatchBean;
import com.hackhome.loans.bean.UpdateBean;
import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.IMainContract;

import io.reactivex.Observable;

/**
 * desc:
 * author: aragon
 * date: 2018/1/22 0022.
 */
public class MainModel implements IMainContract.IMainModel {

    private ApiService mApiService;

    public MainModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<UpdateBean> checkNewVersion(String type) {
        return mApiService.checkNewVersion(type);
    }

    @Override
    public Observable<PatchBean> checkPatch(String type) {
        return mApiService.checkPatch(type);
    }
}
