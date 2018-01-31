package com.hackhome.loans.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.hackhome.loans.common.eventbus.EB;
import com.hackhome.loans.common.eventbus.EventItem;

/**
 * desc:
 * author: aragon
 * date: 2018/1/20 0020.
 */
public class LoanBroadcastReceiver extends BroadcastReceiver {

    private PackageManager mPackageManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        mPackageManager = context.getPackageManager();
        String packageName = intent.getData().toString().split(":")[1];

        if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
            EB.getInstance().send(EventItem.LOAN_DETAIL_OBJECT,EventItem.INSTALL_SUCCESS);
            EB.getInstance().send(EventItem.DOWNLOAD_OBJECT,EventItem.INSTALL_SUCCESS);
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REPLACED)) {
            EB.getInstance().send(EventItem.LOAN_DETAIL_OBJECT,EventItem.INSTALL_SUCCESS);
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
            EB.getInstance().send(EventItem.LOAN_DETAIL_OBJECT,EventItem.UNINSTALL_SUCCESS);
            EB.getInstance().send(EventItem.DOWNLOAD_OBJECT,EventItem.UNINSTALL_SUCCESS, packageName);
        }

    }
}
