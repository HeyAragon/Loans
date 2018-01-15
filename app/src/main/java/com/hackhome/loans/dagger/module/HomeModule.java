package com.hackhome.loans.dagger.module;

import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.IHomeContract;
import com.hackhome.loans.presenter.model.HomeModel;

import dagger.Module;
import dagger.Provides;

/**
 * desc:
 * author: aragon
 * date: 2017/12/19 0019.
 */
@Module
public class HomeModule {

    private IHomeContract.IHomeView mHomeView;

    public HomeModule( IHomeContract.IHomeView homeView) {
        mHomeView = homeView;
    }

    @Provides
    IHomeContract.IHomeView provideHomeView() {
        return mHomeView;
    }

    @Provides
    IHomeContract.IHomeModel provideHomeModel(ApiService apiService) {
        return new HomeModel(apiService);
    }



}
