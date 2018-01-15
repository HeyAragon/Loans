package com.hackhome.loans.ui.base;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */
public interface IBaseView {

    /**
     * 正在加载
     */
    void showLoading();

    /**
     * 加载结束
     */
    void dismissLoading();

    /**
     * 加载错误
     */
    void showError(String error);

    /**
     * 从新加载
     */
    void onRetry();


}
