package com.hackhome.loans.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.ReadRecord;
import com.hackhome.loans.bean.ReturnValueBean;
import com.hackhome.loans.common.tinker.TinkerLoanApplication;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.greendao.ReadRecordDao;
import com.hackhome.loans.ui.LoanDetailActivity;
import com.hackhome.loans.ui.adapter.CommonAdapter;
import com.hackhome.loans.ui.base.BaseActivity;
import com.hackhome.loans.widget.CardViewItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * desc:
 * author: aragon
 * date: 2018/1/8 0008.
 */
public class ReadRecordActivity extends BaseActivity {

    @BindView(R.id.tool_bar_back_img)
    ImageView mToolBarBackImg;
    @BindView(R.id.tool_bar_share_img)
    ImageView mToolBarShareImg;
    @BindView(R.id.tool_bar_title_txt)
    TextView mToolBarTitleTxt;
    @BindView(R.id.simple_toolbar)
    Toolbar mSimpleToolbar;

    @BindView(R.id.read_record_rcv)
    RecyclerView mRecyclerView;
    private List<ReadRecord> mList;
    private List<ReturnValueBean> mReturnValueBeans;

    private CommonAdapter mAdapter;
    private ReadRecordDao mReadRecordDao;

    @Override
    public int getLayoutContentRes() {
        return R.layout.activity_read_record;
    }

    @Override
    public void loadData() {
        mReadRecordDao = TinkerLoanApplication.getTinkerApplication()
                .getDaoSession()
                .getReadRecordDao();
        mList = mReadRecordDao.queryBuilder().list();
    }

    @Override
    public void initView() {
        mToolBarTitleTxt.setText(getString(R.string.read_record));
        mSimpleToolbar.setVisibility(View.VISIBLE);
        mToolBarBackImg.setVisibility(View.VISIBLE);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.base_back),0);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new CardViewItemDecoration(false));
        mAdapter = new CommonAdapter();
        mAdapter.setFrom(CommonAdapter.READ_RECORD);
        initListener();

        mReturnValueBeans = new ArrayList<>();
        if (mList != null && mList.size() != 0) {
            for (ReadRecord record : mList) {
                ReturnValueBean bean = new ReturnValueBean();
                bean.setProductImg(record.getProductImg());
                bean.setStartLoanMoney(record.getStartLoanMoney());
                bean.setEndLoanMoney(record.getEndLoanMoney());
                bean.setProductIntroduce(record.getProductIntroduce());
                bean.setProductH5Url(record.getProductH5Url());
                bean.setProductAndroidUrl(record.getProductAndroidUrl());
                bean.setInterestRateDay(record.getInterestRateDay());
                bean.setAdvertiser(record.getAdvertiser());
                bean.setProductCharacteristic(record.getProductCharacteristic());
                bean.setPackageName(record.getPackageName());
                bean.setSecuredLoan(record.getSecuredLoan());
                bean.setProductName(record.getProductName());
                bean.setStartLoanTime(record.getStartLoanTime());
                bean.setEndLoanTime(record.getEndLoanTime());
                bean.setSuccessRate(record.getSuccessRate());
                mReturnValueBeans.add(bean);
            }
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setNewData(mReturnValueBeans);
        } else {
            showEmpty(2);
        }
    }
    private void initListener() {
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ReturnValueBean bean = (ReturnValueBean) view.getTag();

            if (bean != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("returnValue",bean);
                Intent intent = new Intent(ReadRecordActivity.this, LoanDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mAdapter.setOnItemChildLongClickListener((adapter, view, position) -> {
            new MaterialDialog.Builder(ReadRecordActivity.this)
                    .title("确认删除此浏览记录？")
                    .positiveText("删除").positiveColor(getResources().getColor(R.color.md_deep_orange_500))
                    .negativeText("取消").negativeColor(getResources().getColor(R.color.md_blue_grey_200))
                    .onPositive((dialog, which) -> {
                        mReadRecordDao.delete(mList.get(position));
                        mList.remove(position);
                        mReturnValueBeans.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        if (mList.size() == 0) {
                            showEmpty(2);
                        }
                        dialog.dismiss();

                    })
                    .onNegative((dialog, which) -> dialog.dismiss()).build().show();
            return false;
        });
    }

    @Override
    public void onRetry() {

    }

    @OnClick({R.id.tool_bar_back_img, R.id.tool_bar_title_txt, R.id.simple_toolbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tool_bar_back_img:
                finish();
                break;

        }
    }



    @Override
    public void initInjector(ApplicationComponent applicationComponent) {

    }

    @Override
    public <K> void showResponse(K t, int responseType) {

    }
}
