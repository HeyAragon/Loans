package com.hackhome.loans.dagger.module;

import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.IUserInfoContract;
import com.hackhome.loans.presenter.model.UserInfoModel;

import dagger.Module;
import dagger.Provides;

/**
 * desc:
 * author: aragon
 * date: 2018/1/5 0005.
 */
@Module
public class UserInfoModule {

    public IUserInfoContract.UserInfoView mUserInfoView;

    public UserInfoModule(IUserInfoContract.UserInfoView userInfoView) {
        mUserInfoView = userInfoView;
    }

    @Provides
    public IUserInfoContract.IUserInfoModel provideUserInfoModel(ApiService apiService) {
        return new UserInfoModel(apiService);
    }

    @Provides
    public IUserInfoContract.UserInfoView provideUserInfoView() {
        return mUserInfoView;
    }
}
