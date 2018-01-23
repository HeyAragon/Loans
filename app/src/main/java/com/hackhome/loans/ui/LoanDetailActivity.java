package com.hackhome.loans.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.ReturnValueBean;
import com.hackhome.loans.common.download.DownloadHelperT;
import com.hackhome.loans.common.eventbus.EventItem;
import com.hackhome.loans.common.imageloader.GlideApp;
import com.hackhome.loans.common.utils.DensityUtil;
import com.hackhome.loans.common.utils.ShareUtil;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.ui.base.BaseActivity;
import com.hackhome.loans.widget.DownloadProgressButton;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import butterknife.BindView;
import butterknife.OnClick;

import static com.hackhome.loans.widget.DownloadProgressButton.STATE_INSTALLED;

public class LoanDetailActivity extends BaseActivity {

    @BindView(R.id.simple_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tool_bar_title_txt)
    TextView mTitle;
    @BindView(R.id.loan_detail_app_icon)
    ImageView mAppIcon;
    @BindView(R.id.loan_detail_app_name)
    TextView mAppName;
    @BindView(R.id.loan_app_description)
    TextView mAppDesc;
    @BindView(R.id.loan_detail_money_limit)
    TextView mLoanLimit;
    @BindView(R.id.loan_detail_time_limit)
    TextView mLoanTimeLimit;
    @BindView(R.id.loan_detail_success_rate)
    TextView mSuccessRate;
    @BindView(R.id.loan_detail_people_num)
    TextView mLoanPeopleNum;
    @BindView(R.id.loan_detail_interest)
    TextView mLoanInterest;
    @BindView(R.id.loan_detail_intro)
    TextView mAppIntro;
    @BindView(R.id.download_btn)
    DownloadProgressButton mDownloadProgressButton;
    @BindView(R.id.loan_detail_apply_for_money)
    Button mApplyBtn;
    @BindView(R.id.tool_bar_back_img)
    ImageView mBack;
    @BindView(R.id.tool_bar_share_img)
    ImageView mShare;

    private ReturnValueBean mData;

    @Override
    public int getLayoutContentRes() {
        return R.layout.activity_loan_detail;
    }

    @Override
    public void loadData() {
        Bundle bundle = getIntent().getExtras();
        mData = (ReturnValueBean) bundle.getSerializable("returnValue");
        if (mData != null) {
            DownloadHelperT.DownloadHelperBuilder builder = new DownloadHelperT.DownloadHelperBuilder();
            builder.with(this)
                    .icon(mData.getProductImg())
                    .bindBtn(mDownloadProgressButton)
                    .setFileName(mData.getProductName())
                    .setUrl(mData.getProductAndroidUrl())
                    .pkg(mData.getPackageName())
                    .bean(mData);
            DownloadHelperT.getInstance().prePareDownload(builder);
        }
    }

    @Override
    public void initView() {
        mToolbar.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.VISIBLE);
        mShare.setVisibility(View.VISIBLE);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.base_back),0);
        if (mData != null) {
            mTitle.setText(mData.getProductName());

            GlideApp.with(this)
                    .load(mData.getProductImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new RoundedCorners(DensityUtil.dip2px(this, 8)))
                    .into(mAppIcon);

            mAppName.setText(mData.getProductName());
            mAppDesc.setText(mData.getProductCharacteristic());

            mLoanLimit.setText(mData.getStartLoanMoney()+"-"+ mData.getEndLoanMoney());

            mLoanTimeLimit.setText(mData.getStartLoanTime()+"-"+ mData.getEndLoanTime());

            mSuccessRate.setText(mData.getSuccessRate()+"%");

            mLoanPeopleNum.setText(mData.getSecuredLoan());

            mLoanInterest.setText(mData.getInterestRateDay()+"%");

            mAppIntro.setText(mData.getProductIntroduce());

        }
    }

    @OnClick({R.id.tool_bar_back_img, R.id.tool_bar_share_img,R.id.loan_detail_apply_for_money})
    public void bindClickEvent(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tool_bar_back_img:
                finish();
                break;
            case R.id.tool_bar_share_img:
                ShareUtil.localShare(this,"","","");
                break;
            case R.id.loan_detail_apply_for_money:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("url", mData.getProductH5Url());
                intent.putExtra("name", mData.getProductName());
                startActivity(intent);
                break;
            default:
                // do nothing
                break;
        }
    }

    private SpannableString getSpan(String msg, int start, int end) {
        SpannableString ss = new SpannableString(msg);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLUE);
        ss.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return ss;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(EventItem eventItem) {
        if (eventItem.getReceiveObject() == EventItem.LOAN_DETAIL_OBJECT) {
            switch (eventItem.getMessageType()) {
                case EventItem.INSTALL_SUCCESS:
                    mDownloadProgressButton.setState(STATE_INSTALLED);
                    mDownloadProgressButton.setCurrentText("打开");
                    break;
                case EventItem.UNINSTALL_SUCCESS:
                    break;
            }
        }
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        //do nothing
    }

    @Override
    public<K> void showResponse(K t, int responseType) {

    }

    @Override
    public void onRetry() {

    }



}
