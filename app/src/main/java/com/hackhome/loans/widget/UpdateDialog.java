package com.hackhome.loans.widget;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hackhome.loans.LoanApplication;
import com.hackhome.loans.R;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.download.DownloadHelper;
import com.hackhome.loans.common.utils.AppUtils;
import com.hackhome.loans.common.utils.CloseUtils;
import com.hackhome.loans.common.utils.SharedPreferencesUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * desc:
 * author: aragon
 * date: 2017/12/28 0028.
 */
public class UpdateDialog extends DialogFragment {

    public static final String URL = "update_url";
    public static final String MSG = "update_msg";

    @BindView(R.id.update_cancel)
    TextView mCancel;
    @BindView(R.id.update_confirm)
    TextView mUpdate;
    @BindView(R.id.update_content)
    TextView mContent;
    @BindView(R.id.update_progress_bar)
    ProgressBar mProgressBar;

    private String mUpdateUrl;
    private String mUpdateMsg;

    public static UpdateDialog newInstance(String url,String msg) {

        UpdateDialog dialog = new UpdateDialog();
        Bundle bundle = new Bundle();
        bundle.putString(URL,url );
        bundle.putString(MSG, msg);
        dialog.setArguments(bundle);
        return dialog;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME,R.style.loan_dialog);
        if (bundle != null) {
            mUpdateUrl = bundle.getString(URL);
            mUpdateMsg = bundle.getString(MSG);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_dialog_layout, container);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mContent.setText(mUpdateMsg);
    }

    @OnClick({R.id.update_cancel, R.id.update_confirm})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.update_confirm:
                update();
                break;
            case R.id.update_cancel:
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
        SharedPreferencesUtils
                .getInstance()
                .putLong(Constants.UPDATE_FREQUENCY_KEY,System.currentTimeMillis());
    }

    public void update() {
        if (!TextUtils.isEmpty(mUpdateUrl)) {
            FileDownloader.getImpl()
                    .create(mUpdateUrl)
                    .setPath(DownloadHelper.DOWNLOAD_ROOT_PATH+"wnqk.apk")
                    .setListener(new FileDownloadSampleListener() {
                        @Override
                        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            super.pending(task, soFarBytes, totalBytes);

                        }

                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            super.progress(task, soFarBytes, totalBytes);
                            mProgressBar.setVisibility(View.VISIBLE);
                            if (totalBytes == -1) {
                                mProgressBar.setIndeterminate(true);
                            } else {
                                mProgressBar.setMax(totalBytes);
                                mProgressBar.setProgress(soFarBytes);
                            }
                        }

                        @Override
                        protected void blockComplete(BaseDownloadTask task) {
                            super.blockComplete(task);
                        }

                        @Override
                        protected void completed(BaseDownloadTask task) {
                            super.completed(task);
                            AppUtils.installApp(task.getPath(), AppUtils.AUTHORITIES);
                            dismiss();
                        }

                        @Override
                        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            super.paused(task, soFarBytes, totalBytes);
                        }

                        @Override
                        protected void error(BaseDownloadTask task, Throwable e) {
                            super.error(task, e);
                        }

                        @Override
                        protected void warn(BaseDownloadTask task) {
                            super.warn(task);
                        }
                    }).start();

        }
    }

}
