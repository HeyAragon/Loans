package com.hackhome.loans.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.github.nukc.stateview.StateView;
import com.hackhome.loans.LoanApplication;
import com.hackhome.loans.R;
import com.hackhome.loans.common.download.DownloadTaskManager;
import com.hackhome.loans.common.eventbus.EventItem;
import com.hackhome.loans.common.utils.ToastUtils;
import com.hackhome.loans.ui.mine.DownloadActivity;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */
public  abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBase {

    private Unbinder mUnBinder;

    protected View mRootView;
    protected StateView mStateView;
    private int count;

    @Inject
    protected T mPresenter;
    private LottieAnimationView mLottieAnimationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = createView(null,null,savedInstanceState);
        setContentView(mRootView);
        DownloadTaskManager.getInstance().onCreate();
        EventBus.getDefault().register(this);
        initInjector(LoanApplication.getInstance().getApplicationComponent());
        initStateView();
        loadData();
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
        EventBus.getDefault().unregister(this);
        DownloadTaskManager.getInstance().onDestroy();
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(getLayoutContentRes(), container);
        mUnBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public void showLoading() {
        View view = mStateView.showLoading();
        mLottieAnimationView = view.findViewById(R.id.loading_animation_view);
        mLottieAnimationView.setImageAssetsFolder("images/");
    }

    @Override
    public void dismissLoading() {
        mStateView.showContent();
    }

    @Override
    public void showError(String error) {
        mStateView.showRetry();
        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                onRetry();
            }
        });
    }

    public void showEmpty(int type) {
        View view = mStateView.showEmpty();
        TextView textView = view.findViewById(R.id.empty_txt);
        if (type == 1) {
            textView.setText(getString(R.string.empty_no_download));
        } else if (type == 2) {
            textView.setText(getString(R.string.empty_no_read_record));
        }
    }

    @Subscribe
    public void handleEventBase(EventItem eventItem) {
        //do nothing
        count++;
        KLog.i("aragon",BaseActivity.class.getSimpleName()+count);
    }

    private void initStateView() {
        mStateView = StateView.inject(this,true);
        mStateView.setLoadingResource(R.layout.loading_layout);
        mStateView.setRetryResource(R.layout.error_layout);
        mStateView.setEmptyResource(R.layout.empty_layout);
    }


}
