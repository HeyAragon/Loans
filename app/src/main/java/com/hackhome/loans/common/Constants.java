package com.hackhome.loans.common;

/**
 * desc:
 * author: aragon
 * date: 2017/12/28 0028.
 */
public class Constants {
    /**
     * sp key
     */
    public static final String SHARED_PREFERENCE_NAME = "com.hackhome.loan";

    /**
     * update frequency KEY
     */
    public static final String UPDATE_FREQUENCY_KEY = "update_frequency";

    /**
     * update frequency
     */
    public static final int UPDATE_FREQUENCY = 12 * 60 * 60 * 1000;

    /**
     * 用户信息相关常量
     */
    public static final class UserInfos {

        public static final String USER_COOKIES = "SP_WNQK_USER_COOKIES";
        public static final String USER_COOKIES_TAG = "Quser";
        public static final String USER_STATE = "IS_USER_ENTER";
        public static final String USER_NICK = "USER_NICK";
        public static final String USER_ICON = "USER_ICON";
        public static final String USER_ID = "USER_ID";
        public static final String USER_TYPE = "USER_TYPE";
        public static final String USER_GENDER = "USER_GENDER";
        public static final String USER_LOGIN_TIME = "USER_LOGIN_TIME";
        public static final String NEED_USER_INFO = "need_user_inco";
        public static final String USER_PHONE_NUM = "USER_PHONE_NUM";

    }

    /**
     * 请求响应结果类型
     */
    public static final class ResponseType{

        public static final int TYPE_CHANGE_PASSWORD = 0x1;
        public static final int TYPE_CHANGE_NICK = 0X2;
        public static final int TYPE_CHANGE_GENDER = 0X3;
        public static final int TYPE_CHANGE_USER_LOGO = 0X4;
        public static final int TYPE_GET_AUTH_CODE = 0X5;
        public static final int TYPE_FIND_PASSWORD= 0X6;

        public static final int TYPE_CHECK_NEW_VERSION = 0X7;
        public static final int TYPE_CHECK_NEW_PATCH = 0X8;



    }

    /**
     * 加载操作类型
     */
    public static final class LoadType{

        public static final int FIRST_LOAD = 0x1;
        public static final int REFRESH = 0x2;
        public static final int LOAD_MORE = 0x3;

    }

    /**
     * 缓存 相关常量
     */
    public static final class CacheInfo{

        public static final String CACHE_DIR_NAME = "response";

        public static final String HOME_CACHE = "home_cache";

        public static final String LOAN_CACHE = "loan_cache";

    }
}
