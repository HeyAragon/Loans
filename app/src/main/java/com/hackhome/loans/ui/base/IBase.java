package com.hackhome.loans.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackhome.loans.dagger.component.ApplicationComponent;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */
public interface IBase extends IBaseView{

    @LayoutRes
    int getLayoutContentRes();

    void loadData();

    void initView();

    View getRootView();

    void initInjector(ApplicationComponent applicationComponent);

    View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    <K>void showResponse(K t,int responseType);

}
