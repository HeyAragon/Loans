package com.hackhome.loans.ui.base;


import android.content.Context;

import javax.inject.Inject;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */
public class BasePresenter<M, V extends IBaseView> {

    protected M mModel;

    protected V mView;

    @Inject
    protected Context mContext;

    public BasePresenter(M model, V view) {
        mModel = model;
        mView = view;

//        mContext = LoanApplication.getInstance();
    }



}
