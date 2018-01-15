package com.hackhome.loans.dagger.module;

import android.content.Context;

import com.hackhome.loans.LoanApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */
@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    LoanApplication provideLoanApplication() {
        return (LoanApplication) mContext.getApplicationContext();
    }

    @Singleton
    @Provides
    Context provideContext() {
        return mContext;
    }
}
