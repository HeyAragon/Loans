package com.hackhome.loans.common.utils;

import android.app.Activity;


import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

/**
 * desc:
 * author: aragon
 * date: 2018/1/8 0008.
 */
public class PermissionUtil {

    public static Observable<Boolean> requestSinglePermission(Activity context, String permission) {
        RxPermissions rxPermissions = new RxPermissions(context);
        rxPermissions.setLogging(true);
        return rxPermissions.request(permission);
    }

    public static Observable<Permission> requestPermissions(Activity context, String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(context);
        rxPermissions.setLogging(true);
        return rxPermissions.requestEach(permissions);
    }

}
