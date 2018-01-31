package com.hackhome.loans.ui;

import android.Manifest;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.hackhome.loans.BuildConfig;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.PatchBean;
import com.hackhome.loans.bean.UpdateBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.tinker.TinkerLoanApplication;
import com.hackhome.loans.common.tinker.TinkerUtils;
import com.hackhome.loans.common.utils.AppConfig;
import com.hackhome.loans.common.utils.PermissionUtil;
import com.hackhome.loans.common.utils.SharedPreferencesUtils;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.common.utils.ToastUtils;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.dagger.component.DaggerMainComponent;
import com.hackhome.loans.dagger.module.MainModule;
import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.MainPresenter;
import com.hackhome.loans.presenter.contract.IMainContract;
import com.hackhome.loans.ui.adapter.MainTabAdapter;
import com.hackhome.loans.ui.base.BaseActivity;
import com.hackhome.loans.ui.base.BaseFragment;
import com.hackhome.loans.ui.home.HomeFragment;
import com.hackhome.loans.ui.loan.LoanFragment;
import com.hackhome.loans.ui.mine.MineFragment;
import com.hackhome.loans.widget.NoScrollViewPager;
import com.hackhome.loans.widget.UpdateDialog;
import com.hackhome.loans.widget.bottombar.CustomBottomBarLayout;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainContract.IMainView {

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
        //test_new_style
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
        mBottomBarLayout.setOnItemSelectedListener((bottomBarItem, position) -> {
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
        });

    }

    @Override
    public void loadData() {

        PermissionUtil.requestPermissions(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(permission -> {

                });

        //新版本检测
        mPresenter.checkNewVersion();

        //热更新补丁检测
        mPresenter.checkPatch();

    }


    @Override
    public <K> void showResponse(K t, int responseType) {
        if (t instanceof UpdateBean) {
            UpdateBean updateBean = (UpdateBean) t;
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
        } else if (t instanceof PatchBean) {
            PatchBean patchBean = (PatchBean) t;
//            int patch = patchBean.getPatchVersion().charAt(0);
//            int client = AppConfig.getInstance().
        }
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

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        DaggerMainComponent.builder()
                .applicationComponent(applicationComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onRetry() {
        //do nothing
    }

}
