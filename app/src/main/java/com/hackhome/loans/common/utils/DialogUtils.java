package com.hackhome.loans.common.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hackhome.loans.R;
import com.hackhome.loans.common.download.DownloadHelperT;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

/**
 * desc:
 * author: aragon
 * date: 2018/1/19 0019.
 */
public class DialogUtils {

    public static void isContinueDownload(DownloadHelperT.DownloadHelperBuilder builder) {

        boolean[] isContinue = {false};

        new MaterialDialog.Builder(builder.mContext)
                .content("当前未连接wifi,是否使用移动网络下载？")
                .backgroundColor(builder.mContext.getResources().getColor(R.color.base_back))
                .negativeText("取消").negativeColor(builder.mContext.getResources().getColor(R.color.bottom_tab_normal))
                .positiveText("下载").positiveColor(builder.mContext.getResources().getColor(R.color.bottom_tab_selected))
                .onPositive((dialog, which) -> {
                    DownloadHelperT.getInstance().start(builder);
                    AppConfig.getInstance().setUseMobileNetDownload(true);
                })
                .build().show();
    }

}
