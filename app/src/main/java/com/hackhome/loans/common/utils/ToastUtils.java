package com.hackhome.loans.common.utils;

import android.widget.Toast;

import com.hackhome.loans.common.tinker.TinkerLoanApplication;

/**
 * desc: Toast工具类
 * author: aragon
 * date: 2017/12/19 0019.
 */
public class ToastUtils {

    private static Toast mToast;

    /**
     * 显示Toast
     */
    public static void showToast( CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(TinkerLoanApplication.getLoanApplication(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
}
