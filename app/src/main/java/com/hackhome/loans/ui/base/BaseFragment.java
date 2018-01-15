package com.hackhome.loans.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.github.nukc.stateview.StateView;
import com.hackhome.loans.LoanApplication;
import com.hackhome.loans.R;
import com.hackhome.loans.common.eventbus.EventItem;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */
public abstract class BaseFragment extends LazyLoadFragment implements IBase,IBaseView{

    protected Context mContext;

    protected View mRootView;

    protected StateView mStateView;
    private Unbinder mUnBinder;
    private LottieAnimationView mLottieAnimationView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        WeakReference<Context> reference = new WeakReference<>(context);
        mContext = reference.get();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = createView(inflater, container, savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        initInjector(LoanApplication.getInstance().getApplicationComponent());
        initStateView();
        initView();
        return mRootView;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutContentRes(), container, false);
        mUnBinder = ButterKnife.bind(this, view);
        return view;
    }



    @Override
    protected void onFragmentFirstVisible() {

        loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnBinder !=Unbinder.EMPTY){
            mUnBinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showLoading() {
        if (isFirstEnter) {
            View view = mStateView.showLoading();
            mLottieAnimationView = view.findViewById(R.id.loading_animation_view);
            mLottieAnimationView.setImageAssetsFolder("images/");
        }
    }

    @Override
    public void dismissLoading() {
        mStateView.showContent();
    }

    @Override
    public void showError(String error) {
        mStateView.showContent();
        mStateView.showRetry();
        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                onRetry();
            }
        });
    }

    public View getRootView() {
        return mRootView;
    }

    private void initStateView() {
        mStateView = StateView.inject(mRootView);
        mStateView.setLoadingResource(R.layout.loading_layout);
        mStateView.setEmptyResource(R.layout.empty_layout);
        mStateView.setRetryResource(R.layout.error_layout);
    }

    @Subscribe
    public void handleEventBase(EventItem eventItem) {
        //do nothing
    }
}
