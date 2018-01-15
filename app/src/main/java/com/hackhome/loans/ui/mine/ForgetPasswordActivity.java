package com.hackhome.loans.ui.mine;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hackhome.loans.R;
import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.utils.FormatUtils;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.common.utils.ToastUtils;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.dagger.component.DaggerIForgotPassComponent;
import com.hackhome.loans.dagger.module.CommonModule;
import com.hackhome.loans.dagger.module.ForgetPassModule;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.presenter.CommonPresenter;
import com.hackhome.loans.presenter.ForgetPassPresenter;
import com.hackhome.loans.presenter.contract.IForgetPassContract;
import com.hackhome.loans.ui.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * desc:
 * author: aragon
 * date: 2018/1/6 0006.
 */
public class ForgetPasswordActivity extends BaseActivity<ForgetPassPresenter> implements IForgetPassContract.IForgetPassView{
    @BindView(R.id.tool_bar_back_img)
    ImageView mToolBarBackImg;
    @BindView(R.id.tool_bar_title_txt)
    TextView mToolBarTitleTxt;
    @BindView(R.id.simple_toolbar)
    Toolbar mSimpleToolbar;
    @BindView(R.id.edt_account)
    EditText mEdtAccount;
    @BindView(R.id.iv_del_account)
    ImageView mIvDelAccount;
    @BindView(R.id.ll_account)
    LinearLayout mLlAccount;
    @BindView(R.id.edt_auth_code)
    EditText mEdtAuthCode;
    @BindView(R.id.tv_get_auth_code)
    TextView mTvGetAuthCode;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.step_one_rl)
    RelativeLayout mStepOne;
    @BindView(R.id.edt_new_password1)
    EditText mEdtNewPassword1;
    @BindView(R.id.iv_show_password1)
    ImageView mIvShowPassword1;
    @BindView(R.id.edt_new_password2)
    EditText mEdtNewPassword2;
    @BindView(R.id.iv_show_password2)
    ImageView mIvShowPassword2;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.step_two_ll)
    LinearLayout mStepTwo;

//    @Inject
//    CommonPresenter mCommonPresenter;


    private String mPhone;
    private String mAuthCode;
    private String mNewPass;
    private String mNewPassRepeat;
    private Timer mTimer;
    private int mReTime = 60;


    @Override
    public int getLayoutContentRes() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initView() {
        mToolBarTitleTxt.setText(getString(R.string.forget_password));
        mSimpleToolbar.setVisibility(View.VISIBLE);
        mToolBarBackImg.setVisibility(View.VISIBLE);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.md_deep_orange_500),0);
        initListener();
    }

    private void initListener() {

    }

    @Override
    public <K>void showResponse(K t, int responseType) {
        if (t instanceof ResponseBean) {
            ResponseBean responseBean = (ResponseBean) t;
            if (TextUtils.equals(responseBean.getStatus(),"200")) {

                if (responseType==Constants.ResponseType.TYPE_GET_AUTH_CODE) {
                    ToastUtils.showToast(getString(R.string.have_already_sent_auth_code));
                    mTvGetAuthCode.setClickable(false);
                } else if (responseType == Constants.ResponseType.TYPE_FIND_PASSWORD) {
                    ToastUtils.showToast(getString(R.string.find_password_success));
                    finish();
                }
            } else if (TextUtils.equals(responseBean.getStatus(), "100")) {
                if (responseType == Constants.ResponseType.TYPE_GET_AUTH_CODE) {
                    ToastUtils.showToast(getString(R.string.auth_code_send_error));
                } else {
                    ToastUtils.showToast(responseBean.getInfo());
                }
            } else {
                ToastUtils.showToast(responseBean.getInfo());
            }
        }
    }

    @OnClick({R.id.tool_bar_back_img, R.id.iv_del_account, R.id.tv_get_auth_code,
            R.id.btn_next, R.id.iv_show_password1, R.id.iv_show_password2, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tool_bar_back_img:
                finish();
                break;
            case R.id.iv_del_account:
                break;
            case R.id.tv_get_auth_code:
                getAuthCode();
                break;
            case R.id.btn_next:
                nextStep();
                break;
            case R.id.iv_show_password1:
                break;
            case R.id.iv_show_password2:
                break;
            case R.id.btn_confirm:
                findPassword();
                break;
        }
    }

    private void getAuthCode() {
        mPhone = mEdtAccount.getText().toString();
        if (mPhone.length() != 11 || TextUtils.isEmpty(mPhone)) {
            ToastUtils.showToast(getString(R.string.input_num_error));
            return;
        }
        mPresenter.getAuthCode(ApiConstants.TYPE_FIND_PASS_AUTH_CODE,mPhone);
        startCountDown();
    }

    private void startCountDown() {
        mTimer = new Timer();
        mTvGetAuthCode.setClickable(false);

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        --mReTime;
                        mTvGetAuthCode.setText(FormatUtils.format(FormatUtils.SECOND,mReTime));
                        if (mReTime < 0) {
                            mTimer.cancel();
                            mTvGetAuthCode.setClickable(true);
                            mTvGetAuthCode.setText(getString(R.string.get_auth_code));
                            mReTime = 60;
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    private void nextStep() {
        mPhone = mEdtAccount.getText().toString();
        mAuthCode = mEdtAuthCode.getText().toString();
        if (mPhone.length() != 11 || TextUtils.isEmpty(mPhone)) {
            ToastUtils.showToast(getString(R.string.input_num_error));
            return;
        }
        if (TextUtils.isEmpty(mAuthCode) || mAuthCode.length() != 6) {
            ToastUtils.showToast(getString(R.string.verification_code_not_complete));
            return;
        }

        mStepOne.setVisibility(View.GONE);
        mStepTwo.setVisibility(View.VISIBLE);

    }

    private void findPassword() {
        mNewPass = mEdtNewPassword1.getText().toString();
        mNewPassRepeat = mEdtNewPassword2.getText().toString();
        if (mNewPass.length()<=6) {
            ToastUtils.showToast(getString(R.string.input_password_error));
            return;
        }

        if (!TextUtils.equals(mNewPass,mNewPassRepeat)) {
            ToastUtils.showToast(getString(R.string.input_password_not_equal));
            return;
        }

        mPresenter.findPassword(mPhone,mAuthCode,mNewPass,mNewPassRepeat, Constants.ResponseType.TYPE_FIND_PASSWORD);
    }
    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        DaggerIForgotPassComponent.builder()
                .forgetPassModule(new ForgetPassModule(this))
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onRetry() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}
