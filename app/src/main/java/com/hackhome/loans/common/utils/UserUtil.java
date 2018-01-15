package com.hackhome.loans.common.utils;

import android.content.Context;
import android.text.TextUtils;

import com.hackhome.loans.R;
import com.hackhome.loans.bean.UserInfo;
import com.hackhome.loans.common.Constants;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.Headers;
import okhttp3.HttpUrl;

public class UserUtil {

//    public static ICallbackWxInfo iCallbackWxInfo;

    public static void login() {
        SharedPreferencesUtils.getInstance().putBoolean(Constants.UserInfos.USER_STATE, true);
    }

    public static boolean isLogin() {
        return SharedPreferencesUtils.getInstance().queryBoolean(Constants.UserInfos.USER_STATE, false);
    }

    public static void exit() {
        SharedPreferencesUtils.getInstance().putBoolean(Constants.UserInfos.USER_STATE, false);
        clearUserInfo();
        clearCookies();
//        clearMemory();
//        EMClient.getInstance().logout(true);
    }

//    private static void clearMemory() {
//        Fresco.getImagePipeline().clearMemoryCaches();
//        Fresco.getImagePipeline().clearDiskCaches();
//        Fresco.getImagePipeline().clearCaches();
//        //数据库最好也清空一下
//    }

    //用户登录的方式 0 账户 1 QQ 2 微信 3 新浪
    public static boolean getUserState() {
        return SharedPreferencesUtils.getInstance().queryBoolean(Constants.UserInfos.USER_STATE, false);
    }


    public static void saveCookies(HttpUrl url, Headers headers) {
        for (Cookie cook : Cookie.parseAll(url, headers)) {
            KLog.i("cookie     " + cook.name() + "                   " + cook.value());
            if (TextUtils.equals(Constants.UserInfos.USER_COOKIES_TAG, cook.name())) {
                SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_COOKIES, cook.value());
                break;
            }
        }
    }

    public static Map<String, String> getCookiesParams() {
        String cookies = getCookies();
        if (TextUtils.isEmpty(cookies)) {
            return null;
        }
        int index = cookies.indexOf("&Qcka=");
        String qckb = cookies.substring(5, index);
        String qcka = cookies.substring(index + 6, cookies.length());
        Map<String, String> params = new HashMap<>(2);
        params.put("Qcka", qcka);
        params.put("Qckb", qckb);
        return params;
    }

    public static String getCookies() {
        return SharedPreferencesUtils.getInstance().queryString(Constants.UserInfos.USER_COOKIES, null);
    }

    public static void clearCookies() {
        SharedPreferencesUtils.getInstance().putString( Constants.UserInfos.USER_COOKIES, null);
    }

    public static void savePhoneNum(String phone) {
        SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_PHONE_NUM,phone);
    }

    public static String getPhoneNum() {
        return SharedPreferencesUtils.getInstance().queryString(Constants.UserInfos.USER_PHONE_NUM,"");
    }

    public static String getUserIcon() {
        return SharedPreferencesUtils.getInstance().queryString(Constants.UserInfos.USER_ICON,"");
    }

    public static String getUserNick() {
        return SharedPreferencesUtils.getInstance().queryString(Constants.UserInfos.USER_NICK,"");
    }

    public static String getUserID() {
        return SharedPreferencesUtils.getInstance().queryString(Constants.UserInfos.USER_ID,"");
    }

    public static String getUserLastLoginTime() {
        return SharedPreferencesUtils.getInstance().queryString(Constants.UserInfos.USER_LOGIN_TIME,"");
    }

    public static void saveUerInfo(Context context,UserInfo userInfo) {
        SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_NICK, userInfo.getUsernike());
        SharedPreferencesUtils.getInstance().putString( Constants.UserInfos.USER_ICON, userInfo.getUserpic());
        SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_ID, userInfo.getUserid());
        SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_TYPE, userInfo.getIspass());
        SharedPreferencesUtils.getInstance().putString( Constants.UserInfos.USER_GENDER, TextUtils.equals("0", userInfo.getUsersex()) ? "男" : "女");
        SharedPreferencesUtils.getInstance().putString( Constants.UserInfos.USER_LOGIN_TIME, context.getString(R.string.last_login_time) + userInfo.getUserretime());
        SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_PHONE_NUM,userInfo.getPhone());
    }


    public static void clearUserInfo() {
        SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_NICK, null);
        SharedPreferencesUtils.getInstance().putString( Constants.UserInfos.USER_ICON, null);
        SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_ID, null);
        SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_TYPE, null);
        SharedPreferencesUtils.getInstance().putString( Constants.UserInfos.USER_GENDER, null);
        SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_LOGIN_TIME, null);
    }

    public static void setGender( String gender) {
        if (!TextUtils.isEmpty(gender) && (TextUtils.equals("男", gender) || TextUtils.equals("女", gender))) {
            SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_GENDER, gender);
        }
    }

    public static void changeUserNick(String nick) {
        SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_NICK, nick);
    }

    public static void modifyGender(String gender) {
        SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_NICK, gender);
    }

    public static String getGender(){
        return SharedPreferencesUtils.getInstance().queryString(Constants.UserInfos.USER_GENDER, "男");
    }



    public static boolean canModifyPass() {
        return TextUtils.equals(SharedPreferencesUtils.getInstance().queryString(Constants.UserInfos.USER_TYPE, "True"), "True");
    }

    public static void modifyPass() {
        //如果是第一次修改改变属性，否则不用
        if (TextUtils.equals(SharedPreferencesUtils.getInstance().queryString(Constants.UserInfos.USER_TYPE, "True"), "False"))
            SharedPreferencesUtils.getInstance().putString(Constants.UserInfos.USER_TYPE, "True");
    }

    /**
     *
     */
    public static Map<String, String> getSignParams(Map<String, String> arg) {
        String[] sign = SignUtil.getSign();
        Map<String, String> params = new HashMap<>();
        params.put("int", sign[0]);
        params.put("str", sign[1]);
        params.put("sign", sign[2]);
        params.putAll(arg);
        return params;
    }

    /**
     * 签名
     *
     * @return
     */
    public static Map<String, String> getSign() {
        String[] sign = SignUtil.getSign();
        Map<String, String> params = new HashMap<>(3);
        params.put("int", sign[0]);
        params.put("str", sign[1]);
        params.put("sign", sign[2]);
        return params;
    }


    public static String getSingStr() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] sign = SignUtil.getSign();
        stringBuilder.append("&int=");
        stringBuilder.append(sign[0]);
        stringBuilder.append("&str=");
        stringBuilder.append(sign[1]);
        stringBuilder.append("&sign=");
        stringBuilder.append(sign[2]);
        return stringBuilder.toString();
    }

//    public static void setWxCallback(ICallbackWxInfo ic) {
//        if (null != ic) {
//            iCallbackWxInfo = ic;
//        }
//    }
//
//    public static void setWxInfo(String result) {
//        if (null != iCallbackWxInfo) {
//            iCallbackWxInfo.successGetInfo(result);
//        } else {
//            throw new NullPointerException("ICallbackWxInfo is null");
//        }
//    }
}
