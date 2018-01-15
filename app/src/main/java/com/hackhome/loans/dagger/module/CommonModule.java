package com.hackhome.loans.dagger.module;

import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.ICommonContract;
import com.hackhome.loans.presenter.model.CommonModel;
import com.hackhome.loans.presenter.CommonPresenter;
import com.hackhome.loans.ui.base.IBase;

import dagger.Module;
import dagger.Provides;

/**
 * desc:
 * author: aragon
 * date: 2017/12/19 0019.
 */
@Module
public class CommonModule {

    private IBase mView;
    private CommonModel mCommonModel;

    public CommonModule(IBase view) {
        mView = view;
    }

    @Provides
    IBase  provideCommonView() {
        return mView;
    }

    @Provides
    ICommonContract.ICommonModel provideCommonModel(ApiService apiService) {
        mCommonModel = new CommonModel(apiService);
        return mCommonModel;
    }

    @Provides
    CommonPresenter provideCommonPresenter() {
        return new CommonPresenter(mCommonModel, mView);
    }



}
