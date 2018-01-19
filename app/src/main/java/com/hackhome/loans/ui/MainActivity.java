package com.hackhome.loans.ui;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.hackhome.loans.BuildConfig;
import com.hackhome.loans.LoanApplication;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.UpdateBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.utils.AppConfig;
import com.hackhome.loans.common.utils.PermissionUtil;
import com.hackhome.loans.common.utils.SharedPreferencesUtils;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.common.utils.ToastUtils;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.ui.adapter.MainTabAdapter;
import com.hackhome.loans.ui.base.BaseActivity;
import com.hackhome.loans.ui.base.BaseFragment;
import com.hackhome.loans.ui.home.HomeFragment;
import com.hackhome.loans.ui.loan.LoanFragment;
import com.hackhome.loans.ui.mine.MineFragment;
import com.hackhome.loans.widget.CustomBottomNavigationView;
import com.hackhome.loans.widget.NoScrollViewPager;
import com.hackhome.loans.widget.UpdateDialog;
import com.hackhome.loans.widget.bottombar.BottomBarItem;
import com.hackhome.loans.widget.bottombar.CustomBottomBarLayout;
import com.tbruyelle.rxpermissions2.Permission;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    ApiService mApiService;
    @BindView(R.id.simple_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_bottom_bar)
    CustomBottomBarLayout mBottomBarLayout;
    @BindView(R.id.vp_content)
    NoScrollViewPager mViewPager;
    private HomeFragment mHomeFragment;

    private LoanFragment mLoanFragment;
    private MineFragment mMineFragment;
    private ArrayList<BaseFragment> mFragments;
    private MainTabAdapter mTabAdapter;
    private long exitAppLastClickTime;

    @Override
    public int getLayoutContentRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mToolbar.setVisibility(View.GONE);
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 0, mToolbar);
        initFragment();
        initListener();

//        setSupportActionBar(mToolbar);
    }

    private void initFragment() {
        mHomeFragment = HomeFragment.getInstance();
        mLoanFragment = LoanFragment.getInstance();
        mMineFragment = MineFragment.getInstance();
        mFragments = new ArrayList<>();
        mFragments.add(mHomeFragment);
        mFragments.add(mLoanFragment);
        mFragments.add(mMineFragment);
    }

    public void initListener() {
        mTabAdapter = new MainTabAdapter(mFragments, getSupportFragmentManager());
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mBottomBarLayout.setViewPager(mViewPager);
        mBottomBarLayout.setOnItemSelectedListener(new CustomBottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int position) {
                switch (position) {
                    case 0:
                        mToolbar.setVisibility(View.GONE);
                        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 0, mToolbar);
                        break;
                    case 1:
                        mToolbar.setVisibility(View.VISIBLE);
                        StatusBarUtil.setLoanColor(MainActivity.this, getResources().getColor(R.color.base_back), 0);
                        break;
                    case 2:
                        mToolbar.setVisibility(View.GONE);
                        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 0, mToolbar);

                        break;

                }
            }
        });

    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        //do nothing
    }

    @Override
    public <K> void showResponse(K t, int responseType) {

    }

    @Override
    public void onRetry() {
        //do nothing
    }

    @Override
    public void loadData() {

        PermissionUtil.requestPermissions(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {

                    }
                });

        //do nothing
        mApiService = LoanApplication.getInstance().getApiService();
        mApiService.checkNewVersion("update")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateBean>() {
                    @Override
                    public void accept(UpdateBean updateBean) throws Exception {
                        int newVersion = Integer.valueOf(updateBean.getVcode());
                        long value = SharedPreferencesUtils.getInstance().queryLong(Constants.UPDATE_FREQUENCY_KEY);
                        if (newVersion != -1 && (newVersion > BuildConfig.VERSION_CODE) &&
                                (System.currentTimeMillis() - value) > Constants.UPDATE_FREQUENCY
                                ) {

                            UpdateDialog
                                    .newInstance(updateBean.getUrl(), updateBean.getMsg())
                                    .show(getSupportFragmentManager(), "update_dialog");
                            AppConfig.getInstance().setNewVersion(updateBean.getVer());
                            AppConfig.getInstance().setNewVersionCode(Integer.parseInt(updateBean.getVcode()));
                            AppConfig.getInstance().setUpdateMsg(updateBean.getMsg());
                            AppConfig.getInstance().setUpdateUrl(updateBean.getUrl());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - exitAppLastClickTime < 3000) {
            System.exit(0);
        } else {
            exitAppLastClickTime = currentTime;
            ToastUtils.showToast("再按返回将退出万能钱咖");
        }
    }
}
