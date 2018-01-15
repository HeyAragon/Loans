package com.hackhome.loans.common.exception;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */

public class ApiException extends BaseException{

    public ApiException(int code, String displayMessage) {
        super(code, displayMessage);
    }
}
