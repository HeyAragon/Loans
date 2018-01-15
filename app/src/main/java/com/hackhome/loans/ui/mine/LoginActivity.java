package com.hackhome.loans.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hackhome.loans.R;
import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.bean.UserInfo;
import com.hackhome.loans.common.eventbus.EB;
import com.hackhome.loans.common.eventbus.EventItem;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.common.utils.ToastUtils;
import com.hackhome.loans.common.utils.UserUtil;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.dagger.component.DaggerLoginComponent;
import com.hackhome.loans.dagger.module.LoginModule;
import com.hackhome.loans.presenter.LoginPresenter;
import com.hackhome.loans.presenter.contract.ILoginContract;
import com.hackhome.loans.ui.base.BaseActivity;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * desc:
 * author: aragon
 * date: 2018/1/3 0003.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginContract.ILoginView{


    @BindView(R.id.tool_bar_back_img)
    ImageView mToolBarBackImg;
    @BindView(R.id.tool_bar_share_img)
    ImageView mToolBarShareImg;
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
    @BindView(R.id.mid_line)
    View mMidLine;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;
    @BindView(R.id.iv_del_password)
    ImageView mIvDelPassword;
    @BindView(R.id.ll_password)
    LinearLayout mLlPassword;
    @BindView(R.id.bottom_line)
    View mBottomLine;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_create_account)
    TextView mTvCreateAccount;
    @BindView(R.id.tv_forget_password)
    TextView mTvForgetPassword;
    @BindView(R.id.ll_function)
    LinearLayout mLlFunction;

    @Override
    public int getLayoutContentRes() {
        return R.layout.activity_login;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initView() {
        mSimpleToolbar.setVisibility(View.VISIBLE);
        mToolBarBackImg.setVisibility(View.VISIBLE);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.md_deep_orange_500),0);
    }


    @OnClick({R.id.tool_bar_back_img, R.id.iv_del_account, R.id.btn_login, R.id.tv_create_account,
            R.id.tv_forget_password,R.id.iv_del_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tool_bar_back_img:
                finish();
                break;
            case R.id.iv_del_account:
                mEdtAccount.setText(null);
                break;
            case R.id.iv_del_password:
                mEdtPassword.setText(null);
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_create_account:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forget_password:
                Intent forgetPassIntent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(forgetPassIntent);
                break;
        }
    }

    /**
     * 检查账户和密码是否符合要求然后登录
     */
    private void login() {
        if (TextUtils.isEmpty(mEdtAccount.getText()) || TextUtils.isEmpty(mEdtPassword.getText()) ||
                !(mEdtAccount.getText().length() == 6 || mEdtAccount.getText().length() == 11)) {
            ToastUtils.showToast(getString(R.string.input_error));
        } else {
            //登录操作
            UserUtil.savePhoneNum(mEdtAccount.getText().toString());
            mPresenter.login(mEdtAccount.getText().toString(),mEdtPassword.getText().toString().trim());
        }
    }

    @Override
    public <K> void showResponse(K t, int responseType) {
        KLog.e("aragon","Login");
        if (t instanceof ResponseBean) {
            ResponseBean responseBean = (ResponseBean) t;
            switch (responseBean.getStatus()) {
                //200：登陆成功
                case "200":
                    ToastUtils.showToast(getString(R.string.login_success));
                    UserUtil.login();
                    getUerInfo();
                    break;

                default:
                    ToastUtils.showToast(getString(R.string.login_error));
                    break;
            }
        } else if (t instanceof UserInfo) {
            UserInfo userInfo = (UserInfo) t;
            if (TextUtils.equals("200", userInfo.getStatus())) {
                userInfo.setPhone(mEdtAccount.getText().toString().trim());
                UserUtil.saveUerInfo(this, userInfo);
                EB.getInstance().send(EventItem.MINE_FRAGMENT_OBJECT,EventItem.LOGIN_SUCCESS,userInfo);
                finish();
            }
        }
    }

    private void getUerInfo() {
        mPresenter.getUserInfo(UserUtil.getCookiesParams());
    }

    @Override
    public void showUserInfo(UserInfo userInfo) {
        if (TextUtils.equals("200", userInfo.getStatus())) {
            userInfo.setPhone(mEdtAccount.getText().toString().trim());
            UserUtil.saveUerInfo(this, userInfo);
            EB.getInstance().send(EventItem.MINE_FRAGMENT_OBJECT,EventItem.LOGIN_SUCCESS,userInfo);
            finish();
        }
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        DaggerLoginComponent.builder()
                .applicationComponent(applicationComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);

    }

    @Override
    public void onRetry() {

    }


}
