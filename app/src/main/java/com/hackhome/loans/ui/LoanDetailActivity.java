package com.hackhome.loans.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.ReturnValueBean;
import com.hackhome.loans.common.download.DownloadHelper;
import com.hackhome.loans.common.download.DownloadHelperT;
import com.hackhome.loans.common.download.DownloadTaskManager;
import com.hackhome.loans.common.imageloader.GlideApp;
import com.hackhome.loans.common.utils.DensityUtil;
import com.hackhome.loans.common.utils.FormatUtils;
import com.hackhome.loans.common.utils.ShareUtil;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.ui.base.BaseActivity;
import com.hackhome.loans.widget.DownloadProgressButton;
import com.socks.library.KLog;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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
//            DownloadHelper.builder().with(this).icon(mData.getProductImg()).bindBtn(mDownloadProgressButton)
//                    .setFileName(mData.getProductName())
//                    .setUrl(mData.getProductAndroidUrl())
//                    .pkg(mData.getPackageName())
//                    .build().addClickEvent(0);
            DownloadHelperT.DownloadHelperBuilder builder = new DownloadHelperT.DownloadHelperBuilder();
            builder.with(this)
                    .icon(mData.getProductImg())
                    .bindBtn(mDownloadProgressButton)
                    .setFileName(mData.getProductName())
                    .setUrl(mData.getProductAndroidUrl())
                    .pkg(mData.getPackageName());
            DownloadHelperT.getInstance().prePareDownload(builder);
        }
    }

    @Override
    public void initView() {
        mToolbar.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.VISIBLE);
        mShare.setVisibility(View.VISIBLE);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.md_deep_orange_500),0);
        if (mData != null) {
            mTitle.setText(mData.getProductName());

            GlideApp.with(this)
                    .load(mData.getProductImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new RoundedCorners(DensityUtil.dip2px(this, 8)))
                    .into(mAppIcon);

            mAppName.setText(mData.getProductName());
            mAppDesc.setText(mData.getProductCharacteristic());

            String loanRangeMoneyStr = FormatUtils.format(FormatUtils.LOAN_RANGE, mData.getStartLoanMoney(), mData.getEndLoanMoney());
            mLoanLimit.setText(getSpan(loanRangeMoneyStr, 5, loanRangeMoneyStr.length() - 1));

            String loanRangeTimeStr = FormatUtils.format(FormatUtils.TIME_RANGE, mData.getStartLoanTime(), mData.getEndLoanTime());
            mLoanTimeLimit.setText(getSpan(loanRangeTimeStr, 5, 9));

            String successRateStr = FormatUtils.format(FormatUtils.SUCCESS_RATE, mData.getSuccessRate());
            mSuccessRate.setText(getSpan(successRateStr, 4, successRateStr.length() - 1));

            String loanPeopleNumStr = FormatUtils.format(FormatUtils.LOAN_PEOPLE_COUNT, mData.getSecuredLoan());
            mLoanPeopleNum.setText(getSpan(loanPeopleNumStr, 4, loanPeopleNumStr.length() - 1));

            String loanDayInterest = FormatUtils.format(FormatUtils.DAY_INTEREST, mData.getInterestRateDay());
            mLoanInterest.setText(getSpan(loanDayInterest, 6, loanDayInterest.length() - 1));

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
    public void handleEvent(String msg) {
        KLog.e("aragon", LoanDetailActivity.class.getSimpleName()+":"+msg);
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