package com.hackhome.loans.common.utils;

import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * desc:
 * author: aragon
 * date: 2018/1/8 0008.
 */
public class ShareUtil {
    public static void localShare(Context context, String title, String desc, String shareUrl) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        Context weakContext = weakReference.get();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, desc);
        intent.putExtra(Intent.EXTRA_TEXT, ("最近手头紧吗？缺钱吗？快来《万能钱咖》尽情贷吧！！！" +  "\n下载链接:" + AppConfig.getInstance().getUpdateUrl()));
        weakContext.startActivity(Intent.createChooser(intent, "分享到"));
    }

}
