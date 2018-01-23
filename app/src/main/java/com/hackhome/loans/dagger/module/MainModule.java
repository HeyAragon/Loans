package com.hackhome.loans.dagger.module;

import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.IMainContract;
import com.hackhome.loans.presenter.model.MainModel;

import dagger.Module;
import dagger.Provides;

/**
 * desc:
 * author: aragon
 * date: 2018/1/22 0022.
 */
@Module
public class MainModule {

    private IMainContract.IMainView mMainView;

    public MainModule(IMainContract.IMainView mainView) {
        mMainView = mainView;
    }

    @Provides
    public IMainContract.IMainView provideMainView() {
        return mMainView;
    }

    @Provides
    public IMainContract.IMainModel provideMainModel(ApiService apiService) {
        return new MainModel(apiService);
    }
}
