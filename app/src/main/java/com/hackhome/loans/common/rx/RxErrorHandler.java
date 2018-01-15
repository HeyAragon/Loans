package com.hackhome.loans.common.rx;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.hackhome.loans.common.exception.ApiException;
import com.hackhome.loans.common.exception.BaseException;
import com.hackhome.loans.common.exception.ErrorConstants;
import com.hackhome.loans.common.exception.ErrorMessageFactory;
import com.hackhome.loans.common.utils.ToastUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import retrofit2.HttpException;

/**
 * desc: 全局异常处理类
 * author: aragon
 * date: 2017/12/18 0018.
 */

public class RxErrorHandler {

    private Context mContext;

    public RxErrorHandler(Context context) {

        this.mContext = context;
    }

    public BaseException handleError(Throwable e) {

        BaseException exception = new BaseException();

        if (e instanceof ApiException) {
            exception.setCode(((ApiException) e).getCode());
        } else if (e instanceof JsonParseException) {
            exception.setCode(ErrorConstants.JSON_ERROR);
        } else if (e instanceof HttpException) {
            exception.setCode(((HttpException) e).code());
        } else if (e instanceof SocketTimeoutException) {
            exception.setCode(ErrorConstants.SOCKET_TIMEOUT_ERROR);
        } else if (e instanceof UnknownHostException) {
            exception.setCode(ErrorConstants.SOCKET_ERROR);
        } else {
            exception.setCode(ErrorConstants.UNKNOWN_ERROR);
        }

        exception.setDisplayMessage(ErrorMessageFactory.create(mContext, exception.getCode()));

        return exception;
    }

    public void showErrorMessage(BaseException e) {

        ToastUtils.showToast(e.getDisplayMessage());

    }
}
