package com.hackhome.loans.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.DownloadRecordModel;
import com.hackhome.loans.common.download.DownloadHelperT;
import com.hackhome.loans.common.imageloader.GlideApp;
import com.hackhome.loans.common.utils.AppUtils;
import com.hackhome.loans.common.utils.DensityUtil;
import com.hackhome.loans.common.utils.FileUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadList;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;

import java.text.DecimalFormat;


/**
 * desc:
 * author: aragon
 * date: 2017/12/29 0029.
 */
public class DownloadAdapter extends BaseQuickAdapter<DownloadRecordModel, BaseViewHolder> {

    private FileDownloader mDownloader;

    public DownloadAdapter() {
        super(R.layout.download_item);
        mDownloader = FileDownloader.getImpl();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DownloadRecordModel item) {
        int taskId = item.getTaskId();
        long soFar = mDownloader.getSoFar(taskId);
        long total = mDownloader.getTotal(taskId);
        byte status = mDownloader.getStatus(taskId, item.getPath());
        BaseDownloadTask.IRunningTask task = FileDownloadList.getImpl().get(taskId);

        ProgressBar progressbar = helper.getView(R.id.download_item_progress_bar);

        ImageView icon = helper.getView(R.id.download_item_icon);
        GlideApp.with(mContext)
                .load(item.getIconUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundedCorners(DensityUtil.dip2px(mContext, 8)))
                .into(icon);

        helper.setText(R.id.download_item_title, item.getName());

        helper.addOnClickListener(R.id.download_item_download_btn);
        helper.addOnClickListener(R.id.download_item_speed);

        if (soFar != 0 && total != 0) {
            helper.setText(R.id.download_item_size,
                    getSize(soFar) + "/" + getSize(total));
        }

        if (status == FileDownloadStatus.paused) {
            helper.setVisible(R.id.download_item_progress_bar, true);
            helper.setText(R.id.download_item_speed, mContext.getResources().getText(R.string.paused));
            helper.setText(R.id.download_item_download_btn, mContext.getResources().getText(R.string.continue_));
            helper.setTag(R.id.download_item_download_btn, mContext.getResources().getText(R.string.continue_));
            progressbar.setMax((int) total);
            progressbar.setProgress((int) soFar);
        } else if (status == FileDownloadStatus.progress) {
            helper.setVisible(R.id.download_item_progress_bar, true);
            helper.setText(R.id.download_item_speed, task == null ? "等待" : getSpeed(task.getOrigin().getSpeed()));
            helper.setText(R.id.download_item_download_btn, mContext.getResources().getText(R.string.pause));
            helper.setTag(R.id.download_item_download_btn, mContext.getResources().getText(R.string.pause));
            progressbar.setMax((int) total);
            progressbar.setProgress((int) soFar);
        } else if (status == FileDownloadStatus.pending) {
            helper.setVisible(R.id.download_item_progress_bar, true);
            helper.setText(R.id.download_item_speed, task == null ? "等待" : getSpeed(task.getOrigin().getSpeed()));
            helper.setText(R.id.download_item_download_btn, mContext.getResources().getText(R.string.waiting));
            helper.setText(R.id.download_item_download_btn, mContext.getResources().getText(R.string.waiting));
            helper.setTag(R.id.download_item_size, mContext.getResources().getText(R.string.waiting));
            progressbar.setMax((int) total);
            progressbar.setProgress((int) soFar);
        } else if (status == FileDownloadStatus.completed) {
            helper.setVisible(R.id.download_item_progress_bar, false);
            helper.setVisible(R.id.download_item_size, false);
            helper.setVisible(R.id.download_item_total_size, true);
            helper.setText(R.id.download_item_total_size, FileUtils.getFileSize(item.getPath()));
            if (!AppUtils.isInstallApp(item.getPkgName())) {
//
                if (FileUtils.isFileExists(item.getPath())) {
                    helper.setText(R.id.download_item_download_btn, mContext.getResources().getText(R.string.install));
                    helper.setTag(R.id.download_item_download_btn, mContext.getResources().getText(R.string.install));
                    helper.setText(R.id.download_item_speed, mContext.getResources().getText(R.string.delete));
                    helper.setTag(R.id.download_item_speed, mContext.getResources().getText(R.string.delete));
                }
            } else {
                if (FileUtils.isFileExists(item.getPath())) {
                    helper.setText(R.id.download_item_download_btn, mContext.getResources().getText(R.string.open));
                    helper.setTag(R.id.download_item_download_btn, mContext.getResources().getText(R.string.open));
                    helper.setText(R.id.download_item_speed, mContext.getResources().getText(R.string.uninstall));
                    helper.setTag(R.id.download_item_speed, mContext.getResources().getText(R.string.uninstall));
                }

            }
        }

        final Button button = helper.getView(R.id.download_item_download_btn);
        helper.setOnClickListener(R.id.download_item_download_btn, new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                String tag = (String) v.getTag();
//                if (!TextUtils.isEmpty(tag)) {
//                    if (tag.equals("打开")) {
//                        AppUtils.launchApp(item.getPkgName());
//                    } else if (tag.equals("安装")) {
//                        AppUtils.installApp(item.getPath(), AppUtils.AUTHORITIES);
//                    } else if (tag.equals("继续")) {
//                        DownloadHelperT helperT = DownloadHelperT.getInstance();
//                        DownloadHelperT.DownloadHelperBuilder builder = helperT.getBuilderById(item.getTaskId());
//                        helperT.start(builder);
//                    } else if (tag.equals("暂停")) {
//                        FileDownloader.getImpl().pause(item.getTaskId());
////                        helper.setText(R.id.download_item_download_btn, "继续");
////                        helper.setText(R.id.download_item_speed, "已暂停");
////                        helper.setTag(R.id.download_item_download_btn, "继续");
//////                        notifyItemChanged(helper.getLayoutPosition());
//                    }
//                }

                String s = button.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    if (s.equals("打开")) {
                        AppUtils.launchApp(item.getPkgName());
                    } else if (s.equals("安装")) {
                        AppUtils.installApp(item.getPath(), AppUtils.AUTHORITIES);
                    } else if (s.equals("继续")) {
                        DownloadHelperT helperT = DownloadHelperT.getInstance();
                        DownloadHelperT.DownloadHelperBuilder builder = helperT.getBuilderById(item.getTaskId());
                        helperT.start(builder);
                    } else if (s.equals("暂停")) {
                        FileDownloader.getImpl().pause(item.getTaskId());
                        helper.setText(R.id.download_item_download_btn, "继续");
                        helper.setText(R.id.download_item_speed, "已暂停");
//                        helper.setTag(R.id.download_item_download_btn, "继续");
//                        notifyItemChanged(helper.getLayoutPosition());

                    }
                }
            }
        });
    }

    public String getSize(long size) {
        if (size == 0) return "0";
        int sizeToStringLength = String.valueOf(size).length();
        String humanSize = "";
        DecimalFormat formatter = new DecimalFormat("##0.00");

        if (sizeToStringLength > 9) {
            humanSize += formatter.format((double) size / (1024 * 1024 * 1024)) + " GB";
        } else if (sizeToStringLength > 6) {
            humanSize += formatter.format((double) size / (1024 * 1024)) + " MB";
        } else if (sizeToStringLength > 3) {
            humanSize += formatter.format((double) size / 1024) + " KB";
        } else {
            humanSize += String.valueOf(size) + " Bytes";
        }
        return humanSize;
    }

    public String getSpeed(long speed) {
        DecimalFormat formatter = new DecimalFormat("##0.00");
        if (speed > 1024) {
            return formatter.format((double) speed / 1024) + "MB/S";
        } else {
            return String.valueOf(speed) + "KB/S";
        }

    }
}
