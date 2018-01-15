package com.hackhome.loans.dagger.module;

import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.ILoginContract;
import com.hackhome.loans.presenter.model.LoginModel;

import dagger.Module;
import dagger.Provides;

/**
 * desc:
 * author: aragon
 * date: 2018/1/3 0003.
 */
@Module
public class LoginModule {

    private ILoginContract.ILoginView mLoginView;

    public LoginModule(ILoginContract.ILoginView loginView) {
        mLoginView = loginView;
    }

    @Provides
    public ILoginContract.ILoginModel provideLoginModel(ApiService apiService) {
        return new LoginModel(apiService);
    }

    @Provides
    public ILoginContract.ILoginView provideLoginView() {
        return mLoginView;
    }


}
