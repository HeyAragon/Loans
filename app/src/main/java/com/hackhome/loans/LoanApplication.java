package com.hackhome.loans;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.dagger.component.DaggerApplicationComponent;
import com.hackhome.loans.dagger.module.ApplicationModule;
import com.hackhome.loans.dagger.module.HttpModule;
import com.hackhome.loans.greendao.DaoMaster;
import com.hackhome.loans.greendao.DaoSession;
import com.hackhome.loans.net.ApiService;
import com.liulishuo.filedownloader.FileDownloader;
import com.meituan.android.walle.WalleChannelReader;
import com.umeng.analytics.MobclickAgent;


import javax.inject.Inject;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */

public class LoanApplication extends Application {

    @Inject
    ApiService mApiService;

    private static LoanApplication mLoanApplication;

    private DaoSession mDaoSession;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mLoanApplication = this;
        mApplicationComponent =
            DaggerApplicationComponent
                    .builder()
                    .httpModule(new HttpModule())
                    .applicationModule(new ApplicationModule(this))
                    .build();
        mApplicationComponent.inject(this);
        FileDownloader.setup(this);
        mDaoSession = DaoMaster.newDevSession(this, "loan_db");
        String channel = WalleChannelReader.getChannel(this);
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, "5a530495b27b0a6ca600029a", channel, MobclickAgent.EScenarioType.E_UM_NORMAL));
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static LoanApplication getInstance() {
        return mLoanApplication;
    }

    public ApiService getApiService() {
        return mApiService;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
