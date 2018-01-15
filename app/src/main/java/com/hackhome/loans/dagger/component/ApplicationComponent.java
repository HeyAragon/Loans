package com.hackhome.loans.dagger.component;

import android.content.Context;

import com.hackhome.loans.LoanApplication;
import com.hackhome.loans.dagger.module.ApplicationModule;
import com.hackhome.loans.dagger.module.HttpModule;
import com.hackhome.loans.net.ApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */

@Singleton
@Component(modules = {HttpModule.class, ApplicationModule.class})
public interface ApplicationComponent {

    Context getContext();

    ApiService getApiService();

    LoanApplication getLoanApplication();

    void inject(LoanApplication application);

}
