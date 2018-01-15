package com.hackhome.loans.ui.loan;


import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CheckedTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.exception.BaseException;
import com.hackhome.loans.common.utils.DensityUtil;

import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.dagger.component.DaggerHomeComponent;
import com.hackhome.loans.dagger.module.HomeModule;
import com.hackhome.loans.net.ApiConstants;

import com.hackhome.loans.ui.adapter.CommonAdapter;
import com.hackhome.loans.ui.base.BaseRefreshFragment;
import com.hackhome.loans.presenter.contract.IHomeContract;
import com.hackhome.loans.presenter.HomePresenter;


/**
 * desc: 贷款Fragment
 * author: aragon
 * date: 2017/12/19 0019.
 */
public class LoanFragment extends BaseRefreshFragment<HomePresenter> implements IHomeContract.IHomeView, View.OnClickListener {

    public static final int KEY = 1024;
    //    private int mSort = 2;//2：金额    1：利率
//    private int mOb = 1;//1:升序 2:降序
    private int mCurrentOb = 2;//升序
    private int mCurrentSort = 2;
    private int mCurrentPage = 1;
    private CheckedTextView mLoanValueTxt, mLoanInterestTxt;
    private View mLoanLine, mInterestLine;
    private CommonAdapter mAdapter;

    public LoanFragment() {
        // Required empty public constructor
    }

    public static LoanFragment getInstance() {
        return new LoanFragment();
    }

    @Override
    public void initView() {
        initHeadView();
        mIsSkipPos0 = false;
        super.initView();
    }

    private void initHeadView() {
        mViewStub.setLayoutResource(R.layout.loan_head_layout2);
        View view = mViewStub.inflate();
        mLoanValueTxt =  view.findViewById(R.id.loan_value_sort_txt);
        mLoanInterestTxt = view.findViewById(R.id.loan_interest_sort_txt);
        mLoanLine = view.findViewById(R.id.loan_value_line);
        mInterestLine = view.findViewById(R.id.loan_interest_line);
        mLoanValueTxt.setTag(2);
        mLoanInterestTxt.setTag(2);
        mLoanValueTxt.setOnClickListener(this);
        mLoanInterestTxt.setOnClickListener(this);

        mViewStub.setVisibility(View.VISIBLE);
    }


    @Override
    public void loadData() {
        mPresenter.getHomeData(
                ApiConstants.TYPE_LOAN,
                mCurrentSort, mCurrentPage,
                mCurrentOb, Constants.LoadType.FIRST_LOAD);
    }

    @Override
    protected void onLoadMoreData() {
        mPresenter.getHomeData(ApiConstants.TYPE_LOAN,
                mCurrentSort, mCurrentPage
                , mCurrentOb, Constants.LoadType.LOAD_MORE);
    }

    @Override
    protected void onRefreshDada() {
        mCurrentPage = 1;
        mPresenter.getHomeData(ApiConstants.TYPE_LOAN
                , mCurrentSort, mCurrentPage, mCurrentOb
                , Constants.LoadType.REFRESH);
    }

    @Override
    public void showHomeData(DataBean dataBean, boolean isFromRefresh) {
        int totalPage = dataBean.getPages();
        if (isFromRefresh) {
            mAdapter.setNewData(dataBean.getReturnValue());
            mBaseRecyclerView.scrollToPosition(0);
        } else {
            mAdapter.addData(dataBean.getReturnValue());
            mAdapter.loadMoreComplete();
        }

        if (mCurrentPage < totalPage) {
            mCurrentPage++;
        } else {
            mAdapter.loadMoreEnd();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Drawable drawableDown = getResources().getDrawable(R.mipmap.ic_arrow_down);
        Drawable drawableUp = getResources().getDrawable(R.mipmap.ic_arrow_up);
        Drawable drawableDefault = getResources().getDrawable(R.mipmap.ic_arrow_default);
        drawableDown.setBounds(0, 0, drawableDown.getMinimumWidth(), drawableDown.getMinimumHeight());
        drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
        drawableDefault.setBounds(0, 0, drawableDefault.getMinimumWidth(), drawableDefault.getMinimumHeight());
        mLoanValueTxt.setCompoundDrawablePadding(DensityUtil.dip2px(mContext, 2));
        mLoanInterestTxt.setCompoundDrawablePadding(DensityUtil.dip2px(mContext, 2));
        switch (id) {
            case R.id.loan_value_sort_txt:

                mCurrentSort = 2;

                int tag = (int) mLoanValueTxt.getTag();
                if (tag != -1 && tag == 1) {
                    mLoanValueTxt.setCompoundDrawables(null, null, drawableDown, null);
                    mLoanValueTxt.setTag(2);
                    mCurrentOb = 2;
                } else if (tag == 2) {
                    mLoanValueTxt.setCompoundDrawables(null, null, drawableUp, null);
                    mLoanValueTxt.setTag(1);
                    mCurrentOb = 1;
                }
                mLoanInterestTxt.setCompoundDrawables(null, null, drawableDefault, null);
                mLoanValueTxt.setTextColor(getResources().getColor(R.color.md_blue_500));
                mLoanInterestTxt.setTextColor(getResources().getColor(R.color.black));
                mLoanLine.setVisibility(View.VISIBLE);
                mInterestLine.setVisibility(View.GONE);

                break;
            case R.id.loan_interest_sort_txt:
                mCurrentSort = 1;
                int tag2 = (int) mLoanInterestTxt.getTag();
                if (tag2 != -1 && tag2 == 1) {
                    mLoanInterestTxt.setCompoundDrawables(null, null, drawableDown, null);
                    mLoanInterestTxt.setTag(2);
                    mCurrentOb = 2;
                } else if (tag2 == 2) {
                    mLoanInterestTxt.setCompoundDrawables(null, null, drawableUp, null);
                    mLoanInterestTxt.setTag(1);
                    mCurrentOb = 1;
                }
                mLoanValueTxt.setCompoundDrawables(null, null, drawableDefault, null);
                mLoanInterestTxt.setTextColor(getResources().getColor(R.color.md_blue_500));
                mLoanValueTxt.setTextColor(getResources().getColor(R.color.black));
                mInterestLine.setVisibility(View.VISIBLE);
                mLoanLine.setVisibility(View.GONE);
                break;

        }
        mCurrentPage = 1;

        mPresenter.getHomeData(ApiConstants.TYPE_LOAN
                , mCurrentSort, mCurrentPage, mCurrentOb
                , Constants.LoadType.REFRESH);
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        DaggerHomeComponent.builder()
                .applicationComponent(applicationComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected BaseQuickAdapter buildAdapter() {
        mAdapter = new CommonAdapter();
        mEnableLoadMore = true;
        return mAdapter;
    }


    @Override
    public void onRetry() {
        loadData();
    }

}
