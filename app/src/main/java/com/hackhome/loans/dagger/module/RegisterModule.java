package com.hackhome.loans.dagger.module;

import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.IRegisterContract;
import com.hackhome.loans.presenter.model.RegisterModel;

import java.util.Timer;

import dagger.Module;
import dagger.Provides;

/**
 * desc:
 * author: aragon
 * date: 2018/1/3 0003.
 */
@Module
public class RegisterModule {

    private IRegisterContract.IRegisterView mRegisterView;


    public RegisterModule(IRegisterContract.IRegisterView registerView) {
        mRegisterView = registerView;
    }

    @Provides
    public Timer provideTimer() {
        return new Timer();
    }

    @Provides
    public IRegisterContract.IRegisterView provideRegisterView() {
        return mRegisterView;
    }

    @Provides
    public IRegisterContract.IRegisterModel provideRegisterModel(ApiService apiService) {
        return new RegisterModel(apiService);
    }

}
