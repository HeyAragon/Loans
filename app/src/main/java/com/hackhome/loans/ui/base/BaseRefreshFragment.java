package com.hackhome.loans.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hackhome.loans.LoanApplication;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.ReadRecord;
import com.hackhome.loans.bean.ReturnValueBean;
import com.hackhome.loans.greendao.DaoSession;
import com.hackhome.loans.greendao.ReadRecordDao;
import com.hackhome.loans.ui.LoanDetailActivity;
import com.hackhome.loans.widget.CardViewItemDecoration;
import com.hackhome.loans.widget.MyLoadMoreView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * desc:
 * author: aragon
 * date: 2017/12/19 0019.
 */

public abstract class BaseRefreshFragment<T extends BasePresenter> extends BaseFragment {

    @BindView(R.id.temp_view)
    protected ViewStub mViewStub;
//    @BindView(R.id.simple_toolbar)
//    protected Toolbar mToolbar;
    @BindView(R.id.base_recycler_view)
    protected RecyclerView mBaseRecyclerView;
    @BindView(R.id.refresh_layout)
    protected SmartRefreshLayout mBaseRefreshLayout;

    protected BaseQuickAdapter mBaseQuickAdapter;
    protected Boolean mEnableLoadMore = true;
    protected Boolean mIsSkipPos0 = false;

    @Inject
    protected T mPresenter;

    @Override
    public int getLayoutContentRes() {
        return R.layout.base_refresh_layout;
    }

    @Override
    public void initView() {

//        KLog.e("fuck","initView --baseRefresh");
        mBaseQuickAdapter = buildAdapter();
        if (mBaseQuickAdapter != null) {
            mBaseQuickAdapter.setLoadMoreView(new MyLoadMoreView());
            initRecyclerView(mIsSkipPos0);
            initListener();
        }
    }

    protected void initRecyclerView(boolean isSkipPos0){
        mBaseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBaseRecyclerView.addItemDecoration(new CardViewItemDecoration(isSkipPos0));
        mBaseRecyclerView.setAdapter(mBaseQuickAdapter);
    }

    protected void initListener() {
        mBaseRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                onRefreshDada();
            }
        });

        if (mBaseQuickAdapter != null && mEnableLoadMore) {
            mBaseQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    onLoadMoreData();
                }
            }, mBaseRecyclerView);
        }


        mBaseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ReturnValueBean bean = (ReturnValueBean) view.getTag();

                if (bean != null) {
                    insertIntoDb(bean);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("returnValue",bean);
                    Intent intent = new Intent(mContext, LoanDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    public SmartRefreshLayout getBaseRefreshLayout() {
        return mBaseRefreshLayout;
    }

    protected abstract void onRefreshDada();

    protected abstract void onLoadMoreData();

    protected abstract BaseQuickAdapter buildAdapter();

    private void insertIntoDb(ReturnValueBean bean) {
        DaoSession daoSession = LoanApplication.getInstance().getDaoSession();
        ReadRecordDao readRecordDao = daoSession.getReadRecordDao();

        ReadRecord readRecord = new ReadRecord();
        readRecord.setAdvertiser(bean.getAdvertiser());
        readRecord.setCreated(bean.getCreated());
        readRecord.setStartLoanMoney(bean.getStartLoanMoney());
        readRecord.setEndLoanMoney(bean.getEndLoanMoney());
        readRecord.setID((long) bean.getID());
        readRecord.setInterestRateDay(bean.getInterestRateDay());
        readRecord.setPackageName(bean.getPackageName());
        readRecord.setProductAndroidUrl(bean.getProductAndroidUrl());
        readRecord.setProductH5Url(bean.getProductH5Url());
        readRecord.setProductIntroduce(bean.getProductIntroduce());
        readRecord.setProductImg(bean.getProductImg());
        readRecord.setSecuredLoan(bean.getSecuredLoan());
        readRecord.setEndLoanMoney(bean.getEndLoanMoney());
        readRecord.setProductCharacteristic(bean.getProductCharacteristic());
        readRecord.setProductName(bean.getProductName());
        readRecord.setEndLoanTime(bean.getEndLoanTime());
        readRecord.setStartLoanTime(bean.getStartLoanTime());
        ReadRecord unique = readRecordDao.queryBuilder().where(ReadRecordDao.Properties.ID.eq(readRecord.getID())).build().unique();
        if (unique!=null) {
            return;
        }
        readRecordDao.insert(readRecord);
    }


}
