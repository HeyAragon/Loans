package com.hackhome.loans.ui.home;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.exception.BaseException;
import com.hackhome.loans.common.utils.DensityUtil;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.common.utils.ToastUtils;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.dagger.component.DaggerHomeComponent;
import com.hackhome.loans.dagger.module.HomeModule;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.ui.MainActivity;
import com.hackhome.loans.ui.WebActivity;
import com.hackhome.loans.ui.adapter.CommonAdapter;
import com.hackhome.loans.ui.base.BaseRefreshFragment;
import com.hackhome.loans.presenter.contract.IHomeContract;
import com.hackhome.loans.presenter.HomePresenter;
import com.hackhome.loans.widget.BannerViewHolder;
import com.socks.library.KLog;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseRefreshFragment<HomePresenter> implements IHomeContract.IHomeView {

    private LayoutInflater mLayoutInflater;
    private View mBannerView;
    private MZBannerView mBanner;
    private CommonAdapter mAdapter;
    private View mLeftLine, mRightLine;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public void initView() {
        mIsSkipPos0 = true;
        super.initView();
    }

    @Override
    public void loadData() {
        mPresenter.getHomeData(ApiConstants.TYPE_HOME, 0, 1, 0, Constants.LoadType.FIRST_LOAD);
    }


    @Override
    protected void onRefreshDada() {
        mPresenter.getHomeData(ApiConstants.TYPE_HOME, 0, 1, 0, Constants.LoadType.REFRESH);
    }

    @Override
    protected void onLoadMoreData() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void showHomeData(DataBean dataBean, boolean isFromRefresh) {

        if (isFromRefresh) {
            mAdapter.removeAllHeaderView();
            mAdapter.addHeaderView(initBanner(dataBean.getBanners()));
            mAdapter.setNewData(dataBean.getReturnValue());
//            mAdapter.addData(dataBean.getReturnValue());
        }

        if (mBaseRefreshLayout.isRefreshing()) {
            mBaseRefreshLayout.finishRefresh();
        }

    }


    @Override
    public <K> void showResponse(K t, int responseType) {
        switch (responseType) {
            case Constants.LoadType.FIRST_LOAD:
                // TODO: 2018/1/11 0011
                break;
            case Constants.LoadType.REFRESH:
                if (mBaseRefreshLayout.isRefreshing()) {
                    if (t instanceof BaseException) {
                        mBaseRefreshLayout.finishRefresh(false);
                    } else {
                        mBaseRefreshLayout.finishRefresh(true);
                    }
                }
                break;
            case Constants.LoadType.LOAD_MORE:
                if (t instanceof BaseException) {
                    mAdapter.loadMoreFail();
                }
                break;
        }
    }

    public View initBanner(final List<DataBean.BannersBean> banners) {
        mLayoutInflater = LayoutInflater.from(mContext);
        mBannerView = mLayoutInflater.inflate(R.layout.banner_layout, null);
        mLeftLine = mBannerView.findViewById(R.id.left_line);
        mRightLine = mBannerView.findViewById(R.id.right_line);

        setDrawable(mLeftLine, GradientDrawable.Orientation.LEFT_RIGHT, true);
        setDrawable(mRightLine, GradientDrawable.Orientation.RIGHT_LEFT, true);

        mBanner = mBannerView.findViewById(R.id.banner);

        ViewGroup.LayoutParams layoutParams = mBanner.getLayoutParams();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            layoutParams.width = layoutParams.MATCH_PARENT;
            layoutParams.height = DensityUtil.dip2px(getContext(), 224);
        } else {
            layoutParams.width = layoutParams.MATCH_PARENT;
            layoutParams.height = DensityUtil.dip2px(getContext(), 168);
        }
        mBanner.setLayoutParams(layoutParams);
        mBanner.setIndicatorVisible(true);
        mBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url", banners.get(position).getLinkeUrl());
                intent.putExtra("name", banners.get(position).getBannerName());
                startActivity(intent);

            }
        });
        mBanner.setPages(banners, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mBanner.start();

        return mBannerView;

    }

    public void setDrawable(View view, GradientDrawable.Orientation orientation, boolean needGradient) {

        GradientDrawable drawable = new GradientDrawable();
        int color = mContext.getResources().getColor(R.color.md_deep_orange_500);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(100);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        if (needGradient && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            drawable.setColors(new int[]{Color.parseColor("#00ffffff"), color});
            if (orientation == GradientDrawable.Orientation.LEFT_RIGHT) {
                drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            } else if (orientation == GradientDrawable.Orientation.RIGHT_LEFT) {
                drawable.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
            }
        }

        view.setBackgroundDrawable(drawable);
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        DaggerHomeComponent.builder()
                .homeModule(new HomeModule(this))
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }


    @Override
    public void onRetry() {
        loadData();
    }

    @Override
    protected BaseQuickAdapter buildAdapter() {
        mAdapter = new CommonAdapter();
        mEnableLoadMore = false;

        return mAdapter;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBanner != null) {
            mBanner.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.start();
        }
    }
}
