package com.hackhome.loans.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hackhome.loans.R;
import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.common.utils.FormatUtils;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.common.utils.ToastUtils;
import com.hackhome.loans.common.utils.UserUtil;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.dagger.component.DaggerRegisterComponent;
import com.hackhome.loans.dagger.module.RegisterModule;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.presenter.RegisterPresenter;
import com.hackhome.loans.presenter.contract.IRegisterContract;
import com.hackhome.loans.ui.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * desc:
 * author: aragon
 * date: 2018/1/3 0003.
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements IRegisterContract.IRegisterView {

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
    @BindView(R.id.edt_password)
    EditText mEdtPassword;
    @BindView(R.id.iv_show_password)
    ImageView mIvShowPassword;
    @BindView(R.id.edt_auth_code)
    EditText mEdtAuthCode;
    @BindView(R.id.tv_get_auth_code)
    TextView mTvGetAuthCode;
    @BindView(R.id.ll_auth_code)
    LinearLayout mLlAuthCode;
    @BindView(R.id.btn_register)
    Button mBtnLogin;
    @Inject
    Timer mTimer;

    private int mReTime = 60;
    private boolean mIsShowPassword;
    private boolean mIsGetVerificationCode;



    @Override
    public int getLayoutContentRes() {
        return R.layout.activity_register;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initView() {

        mSimpleToolbar.setVisibility(View.VISIBLE);
        mToolBarBackImg.setVisibility(View.VISIBLE);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.md_deep_orange_500),0);

        initListener();
    }

    private void initListener() {
        mEdtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) mIvDelAccount.setVisibility(View.GONE);
                else mIvDelAccount.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEdtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) mIvShowPassword.setVisibility(View.GONE);
                else mIvShowPassword.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.tool_bar_back_img, R.id.iv_del_account, R.id.iv_show_password, R.id.edt_auth_code,
            R.id.tv_get_auth_code, R.id.ll_auth_code, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tool_bar_back_img:
                finish();
                break;
            case R.id.iv_del_account:
                mEdtAccount.setText(null);
                break;
            case R.id.iv_show_password:
                if (mIsShowPassword) {
                    mIsShowPassword = false;
                    mIvShowPassword.setImageResource(R.mipmap.ic_eye_close);
                    mEdtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                } else {
                    mIsShowPassword = true;
                    mIvShowPassword.setImageResource(R.mipmap.ic_eye_open);
                    mEdtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.tv_get_auth_code:
                if (TextUtils.isEmpty(mEdtAccount.getText())) {
                    ToastUtils.showToast(getString(R.string.input_phone_num));
                } else if (mEdtAccount.getText().length() != 11){
                    ToastUtils.showToast(getString(R.string.input_num_error));
                }else if(TextUtils.isEmpty(mEdtPassword.getText())){
                    ToastUtils.showToast(getString(R.string.input_password_empty));
                } else if (mEdtPassword.getText().length() < 6){
                    ToastUtils.showToast(getString(R.string.input_password_error));
                } else{
                    getAuthCode();
                }

                break;

            case R.id.btn_register:
                checkInputInfo();
                break;
        }
    }

    private void getAuthCode() {
        mPresenter.getAuthCode(mEdtAccount.getText().toString());
    }

    private void checkInputInfo() {
        if (TextUtils.isEmpty(mEdtAccount.getText())|| 11!= mEdtAccount.getText().length() ) {
            ToastUtils.showToast( getString(R.string.input_num_error));
        }else if(TextUtils.isEmpty(mEdtPassword.getText())){
            ToastUtils.showToast(getString(R.string.password_not_null));
        } else if(!mIsGetVerificationCode) {
            ToastUtils.showToast(getString(R.string.require_auth_code));
        }else if(TextUtils.isEmpty(mEdtAuthCode.getText()) || 6 != mEdtAuthCode.getText().length()){
            ToastUtils.showToast(getString(R.string.verification_code_not_complete));
        }else{
            //注册操作
            mPresenter.register(
                    mEdtAccount.getText().toString().trim(),mEdtAuthCode.getText().toString().trim(),
                    mEdtPassword.getText().toString().trim(),mEdtPassword.getText().toString().trim());
        }
    }

    private void startCountDown() {
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

    @Override
    public void showResponse(ResponseBean responseBean,String type) {

        ToastUtils.showToast(responseBean.getInfo());
        String status = responseBean.getStatus();
        switch (type) {
            //注册
            case ApiConstants.TYPE_REGISTER:
                switch (status) {
                    case "200":
                        //注册成功不能自动登录未必知道该用户是否需要绑定以前的账户
                        ToastUtils.showToast(getString(R.string.register_success));
                        UserUtil.savePhoneNum(mEdtAccount.getText().toString());
                        break;
                    default:
                        ToastUtils.showToast(responseBean.getInfo());
                        break;
                }
                break;

            //注册验证码
            case ApiConstants.TYPE_REGISTER_GET_AUTH_CODE:
                switch (status) {
                    case "100":
                        ToastUtils.showToast(getString(R.string.auth_code_send_error));
                        break;
                    case "200":
                        mTvGetAuthCode.setClickable(false);
                        mIsGetVerificationCode = true;
                        ToastUtils.showToast(getString(R.string.have_already_sent_auth_code));
                        //开启倒计时
                        startCountDown();
                        break;
                    case "212":
                        ToastUtils.showToast(getString(R.string.phone_have_already_registered));
                        break;
                    case "300":
                        ToastUtils.showToast(getString(R.string.phone_have_already_registered));
                        break;
                    case "400":
                        ToastUtils.showToast(getString(R.string.register_argument_error));
                        break;
                    case "446":
                        ToastUtils.showToast(getString(R.string.require_for_code_too_quick));
                        break;
                    default:
                        ToastUtils.showToast(responseBean.getInfo());
                        break;
                }
                break;
        }



    }

    @Override
    public <K> void showResponse(K t, int responseType) {

    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        DaggerRegisterComponent.builder()
                .applicationComponent(applicationComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @Override
    public void onRetry() {

    }

}
