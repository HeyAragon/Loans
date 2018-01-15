package com.hackhome.loans.common.exception;

import android.content.Context;

import com.hackhome.loans.R;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */

public class ErrorMessageFactory {

    public static String create(Context context, int code) {

        String errorMsg = null;

        switch (code) {
            case ErrorConstants.HTTP_ERROR:
                errorMsg = context.getResources().getString(R.string.error_http);
                break;
            case ErrorConstants.SOCKET_TIMEOUT_ERROR:
                errorMsg = context.getResources().getString(R.string.error_socket_timeout);
                break;
            case ErrorConstants.SOCKET_ERROR:
                errorMsg = context.getResources().getString(R.string.error_socket_unreachable);
                break;
            case ErrorConstants.ERROR_HTTP_400:
                errorMsg = context.getResources().getString(R.string.error_http_400);
                break;
            case ErrorConstants.ERROR_HTTP_404:
                errorMsg = context.getResources().getString(R.string.error_http_404);
                break;
            case ErrorConstants.ERROR_HTTP_500:
                errorMsg = context.getResources().getString(R.string.error_http_500);
                break;
            case ErrorConstants.ERROR_API_SYSTEM:
                errorMsg = context.getResources().getString(R.string.error_system);
                break;
            case ErrorConstants.ERROR_API_ACCOUNT_FREEZE:
                errorMsg = context.getResources().getString(R.string.error_account_freeze);
                break;
            case ErrorConstants.ERROR_API_NO_PERMISSION:
                errorMsg = context.getResources().getString(R.string.error_api_no_perission);
                break;
            case ErrorConstants.ERROR_API_LOGIN:
                errorMsg = context.getResources().getString(R.string.error_login);
                break;
            case ErrorConstants.ERROR_TOKEN:
                errorMsg = context.getResources().getString(R.string.error_token);
                break;
            case ErrorConstants.ERROR_PAGE:
                errorMsg = context.getResources().getString(R.string.error_page_beyond_range);
                break;
            default:
                errorMsg = context.getResources().getString(R.string.error_unkown);
                break;


        }

        return errorMsg;


    }
}
