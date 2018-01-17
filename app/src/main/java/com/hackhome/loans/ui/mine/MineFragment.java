package com.hackhome.loans.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.UserInfo;
import com.hackhome.loans.common.eventbus.EventItem;
import com.hackhome.loans.common.imageloader.GlideApp;
import com.hackhome.loans.common.utils.AppConfig;
import com.hackhome.loans.common.utils.FormatUtils;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.common.utils.ToastUtils;
import com.hackhome.loans.common.utils.UserUtil;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.ui.MainActivity;
import com.hackhome.loans.ui.base.BaseFragment;
import com.hackhome.loans.widget.UpdateDialog;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * desc: 我的Fragment
 * author: aragon
 * date: 2017/12/19 0019.
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.mine_default_user_logo)
    ImageView mUserLogo;
    @BindView(R.id.mine_info_txt)
    TextView mMineRecord;
    @BindView(R.id.mine_read_txt)
    TextView mMineCollected;
    @BindView(R.id.mine_downloaded_txt)
    TextView mMineDownloaded;
    @BindView(R.id.mine_login_txt)
    TextView mMineLoginTxt;
    @BindView(R.id.mine_setting_txt)
    TextView mMineSetting;
    @BindView(R.id.current_version)
    TextView mCurrentVersion;


    public static MineFragment getInstance() {
        return new MineFragment();
    }


    @Override
    public int getLayoutContentRes() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        if (UserUtil.isLogin()) {
            GlideApp.with(mContext)
                    .load(UserUtil.getUserIcon())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new CircleCrop())
                    .into(mUserLogo);

            mMineLoginTxt.setText(UserUtil.getPhoneNum());

        }
        mCurrentVersion.setText(
                FormatUtils.format(getString(R.string.current_version),
                AppConfig.getInstance().getCurrentVersion()
                ));

    }

    @Override
    public void loadData() {

    }

    @Override
    public <K> void showResponse(K t, int responseType) {
        KLog.e("aragon","Mine");
    }

    @OnClick({R.id.mine_default_user_logo, R.id.mine_login_txt,
            R.id.mine_info_container, R.id.mine_read_container,
            R.id.mine_download_container, R.id.mine_setting_container})
    public void onViewClicked(View view) {
        Intent loginIntent = new Intent(mContext, LoginActivity.class);
        switch (view.getId()) {
            case R.id.mine_default_user_logo:
            case R.id.mine_login_txt:
                if (UserUtil.isLogin()) {
//                    Intent loginIntent = new Intent(mContext, UserInfoActivity.class);
//                    startActivity(loginIntent);
                } else {

                    startActivity(loginIntent);
                }
                break;
            case R.id.mine_info_container:
                if (UserUtil.isLogin()) {
                    Intent userInfoIntent = new Intent(mContext, UserInfoActivity.class);
                    startActivity(userInfoIntent);
                } else {
                    startActivity(loginIntent);
                }
                break;
            case R.id.mine_read_container:
                if (UserUtil.isLogin()) {
                    Intent readRecordIntent = new Intent(mContext, ReadRecordActivity.class);
                    startActivity(readRecordIntent);
                } else {
                    startActivity(loginIntent);
                }
                break;
            case R.id.mine_download_container:
                Intent downloadIntent = new Intent(mContext, DownloadActivity.class);
                startActivity(downloadIntent);
                break;
            case R.id.mine_setting_container:
                int newVersionCode = AppConfig.getInstance().getNewVersionCode();
                int currentVersionCode = AppConfig.getInstance().getCurrentVersionCode();
                String url = AppConfig.getInstance().getUpdateUrl();
                String msg = AppConfig.getInstance().getUpdateMsg();

                if (newVersionCode > currentVersionCode) {
                    UpdateDialog
                            .newInstance(url,msg)
                            .show(getFragmentManager(), "update_dialog");
                    ToastUtils.showToast(getString(R.string.find_new_version));
                } else {
                    ToastUtils.showToast(getString(R.string.not_found_new_version));
                }
                break;
        }
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        //do nothing
    }

    @Override
    public void onRetry() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hadleEvent(EventItem item) {

        if (item.getReceiveObject() == EventItem.MINE_FRAGMENT_OBJECT) {

            switch (item.getMessageType()) {
                case EventItem.LOGIN_SUCCESS:
                    UserInfo userInfo = (UserInfo) item.getOb();
                    if (userInfo != null) {
                        GlideApp.with(mContext)
                                .load(userInfo.getUserpic())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .transform(new CircleCrop())
                                .into(mUserLogo);
                        mMineLoginTxt.setText(UserUtil.getPhoneNum());
                    }
                    break;
                case EventItem.EXIT_SUCCESS:
                    mMineLoginTxt.setText(getString(R.string.login_now));
                    GlideApp.with(mContext)
                            .load(R.mipmap.ic_user_logo)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .transform(new CircleCrop())
                            .into(mUserLogo);
                    break;
            }
        }


    }


//    public void resetStatusBar() {
//        if (getActivity() != null) {
//            StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), 0,null);
//        }
//    }
}
