package com.hackhome.loans.common.tinker;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.hackhome.loans.BuildConfig;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.dagger.component.DaggerApplicationComponent;
import com.hackhome.loans.dagger.module.ApplicationModule;
import com.hackhome.loans.dagger.module.HttpModule;
import com.hackhome.loans.greendao.DaoMaster;
import com.hackhome.loans.greendao.DaoSession;
import com.hackhome.loans.net.ApiService;
import com.liulishuo.filedownloader.FileDownloader;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

/**
 * desc:
 * author: aragon
 * date: 2018/1/20 0020.
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.hackhome.loans.LoanApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class TinkerLoanApplication extends DefaultApplicationLike {

    @Inject
    ApiService mApiService;

    private static Application mLoanApplication;
    private static TinkerLoanApplication mTinkerApplication;

    private DaoSession mDaoSession;

    private ApplicationComponent mApplicationComponent;

    public TinkerLoanApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        TinkerInstaller.install(this, new DefaultLoadReporter(getApplication())
                , new DefaultPatchReporter(getApplication()), new DefaultPatchListener(getApplication()), TinkerResultService.class, new UpgradePatch());
        Tinker tinker = Tinker.with(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLoanApplication = getApplication();
        mTinkerApplication = this;
        mApplicationComponent =
                DaggerApplicationComponent
                        .builder()
                        .httpModule(new HttpModule())
                        .applicationModule(new ApplicationModule(getApplication()))
                        .build();
        mApplicationComponent.inject(this);
        FileDownloader.setup(getApplication());
        mDaoSession = DaoMaster.newDevSession(getApplication(), "loan_db");
        String channel = WalleChannelReader.getChannel(getApplication());
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(getApplication(), "5a530495b27b0a6ca600029a", channel, MobclickAgent.EScenarioType.E_UM_NORMAL));
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getLoanApplication());
        strategy.setAppChannel(channel);
        CrashReport.initCrashReport(getLoanApplication(), "8175ae6d59", BuildConfig.DEBUG,strategy);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Application getLoanApplication() {
        return mLoanApplication;
    }

    public static TinkerLoanApplication getTinkerApplication() {
        return mTinkerApplication;
    }

    public ApiService getApiService() {
        return mApiService;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
