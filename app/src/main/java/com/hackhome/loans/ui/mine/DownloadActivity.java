package com.hackhome.loans.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.DownloadRecordModel;
import com.hackhome.loans.bean.ReturnValueBean;
import com.hackhome.loans.common.download.DownloadTaskManager;
import com.hackhome.loans.common.eventbus.EventItem;
import com.hackhome.loans.common.tinker.TinkerLoanApplication;
import com.hackhome.loans.common.utils.AppUtils;
import com.hackhome.loans.common.utils.FileUtils;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.greendao.DownloadRecordModelDao;
import com.hackhome.loans.ui.LoanDetailActivity;
import com.hackhome.loans.ui.adapter.DownloadAdapter;
import com.hackhome.loans.ui.base.BaseActivity;
import com.hackhome.loans.widget.CardViewItemDecoration;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class DownloadActivity extends BaseActivity {

    @BindView(R.id.simple_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tool_bar_title_txt)
    TextView mTitle;
    @BindView(R.id.tool_bar_back_img)
    ImageView mBack;
    @BindView(R.id.tool_bar_share_img)
    ImageView mShare;
    @BindView(R.id.download_recycler_view)
    RecyclerView mDownloadRecyclerView;
    private DownloadRecordModelDao mDownloadRecordModelDao;
    private List<DownloadRecordModel> mDownloadRecordModelList;
    private DownloadAdapter mDownloadAdapter;
    private DownloadRecordModel mCurrentDao;
    private int mCurrentItemPos;

    @Override
    public int getLayoutContentRes() {
        return R.layout.activity_download;
    }

    @Override
    public void loadData() {
        mDownloadRecordModelDao = TinkerLoanApplication.getTinkerApplication().getDaoSession().getDownloadRecordModelDao();
        mDownloadRecordModelList = DownloadTaskManager.getInstance().getDownloadRecordModels();
        if (mDownloadRecordModelList.size() == 0) {
            showEmpty(1);
        }
    }

    @Override
    public void initView() {
        mToolbar.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.VISIBLE);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.base_back), 0);
        mTitle.setText(getResources().getText(R.string.my_downloaded));
        mDownloadAdapter = new DownloadAdapter();
        mDownloadRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDownloadRecyclerView.addItemDecoration(new CardViewItemDecoration(false));
        mDownloadRecyclerView.setAdapter(mDownloadAdapter);
        mDownloadAdapter.setNewData(mDownloadRecordModelList);
        initListener();
    }

    private void initListener() {
        mDownloadAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            int id = view.getId();

            final DownloadRecordModel recordModel = mDownloadRecordModelList.get(position);
            switch (id) {
//                    case R.id.download_item_download_btn:
//                        if (!TextUtils.isEmpty(tag)) {
//                            if (tag.equals("打开")) {
//                                AppUtils.launchApp(recordModel.getPkgName());
//                            } else if (tag.equals("安装")) {
//                                AppUtils.installApp(recordModel.getPath(), AppUtils.AUTHORITIES);
//                            } else if (tag.equals("继续")) {
//                                DownloadHelperT helperT = DownloadHelperT.getInstance();
//                                DownloadHelperT.DownloadHelperBuilder builder = helperT.getBuilderById(recordModel.getTaskId());
//                                helperT.start(builder);
//                            } else if (tag.equals("暂停")) {
//                                FileDownloader.getImpl().pause(recordModel.getTaskId());
//                                Button button = (Button) view;
//                                button.setText("继续");
//                                button.setTag("继续");
//                                mDownloadAdapter.notifyItemChanged(position);
//                            }
//                        }
//                        break;
                case R.id.download_item_speed:
                    String tag = (String) view.getTag();
                    if (!TextUtils.isEmpty(tag)) {
                        if (tag.equals("删除")) {
                            if (FileUtils.isFileExists(recordModel.getPath())) {
                                MaterialDialog.Builder builder = new MaterialDialog.Builder(DownloadActivity.this);
                                builder.title("确认删除该安装包？")
                                        .positiveText("删除").positiveColor(getResources().getColor(R.color.md_deep_orange_500))
                                        .negativeText("取消").negativeColor(getResources().getColor(R.color.md_blue_grey_200))
                                        .onNegative((dialog, which) -> dialog.dismiss())
                                        .onPositive((dialog, which) -> {
                                            FileUtils.deleteFile(recordModel.getPath());
                                            mDownloadRecordModelDao.delete(recordModel);
                                            mDownloadRecordModelList.remove(position);
                                            adapter.notifyItemRemoved(position);
                                            if (mDownloadRecordModelList.size() == 0) {
                                                showEmpty(1);
                                            }
                                            dialog.dismiss();
                                        }).build().show();
                            }
                        } else if (tag.equals("卸载")) {
                            AppUtils.uninstallApp(recordModel.getPkgName());
                            mCurrentItemPos = position;
                            mCurrentDao = recordModel;
                        }
                    }
                    break;
                case R.id.base_download_item_root:
                    ReturnValueBean bean = (ReturnValueBean) view.getTag();
                    if (bean != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("returnValue", bean);
                        Intent intent = new Intent(DownloadActivity.this, LoanDetailActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    break;
            }
        });
    }

    @OnClick(R.id.tool_bar_back_img)
    public void back() {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(EventItem item) {

        if (item.getReceiveObject() == EventItem.DOWNLOAD_OBJECT) {
            switch (item.getMessageType()) {
                case EventItem.REFRESH_PROGRESS:
                case EventItem.INSTALL_SUCCESS:
                    if (mDownloadAdapter != null) {
                        mDownloadAdapter.notifyDataSetChanged();
                    }
                    break;
                case EventItem.UNINSTALL_SUCCESS:
                    if (mDownloadAdapter != null) {
                        mDownloadRecordModelDao.delete(mCurrentDao);
                        mDownloadRecordModelList.remove(mCurrentItemPos);
                        mDownloadAdapter.notifyItemRemoved(mCurrentItemPos);
                        showEmpty(1);
                    }
                    break;

            }

        }
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {

    }

    @Override
    public <K> void showResponse(K t, int responseType) {

    }

    @Override
    public void onRetry() {

    }
}
