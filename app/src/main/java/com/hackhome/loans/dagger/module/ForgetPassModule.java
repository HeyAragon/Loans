package com.hackhome.loans.dagger.module;

import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.IForgetPassContract;
import com.hackhome.loans.presenter.contract.IUserInfoContract;
import com.hackhome.loans.presenter.model.ForgetPassModel;
import com.hackhome.loans.presenter.model.UserInfoModel;

import dagger.Module;
import dagger.Provides;

/**
 * desc:
 * author: aragon
 * date: 2018/1/5 0005.
 */
@Module
public class ForgetPassModule {

    public IForgetPassContract.IForgetPassView mForgetPassView;

    public ForgetPassModule(IForgetPassContract.IForgetPassView forgetPassView) {
        mForgetPassView = forgetPassView;
    }

    @Provides
    public IForgetPassContract.IForgetPassModel provideForgetPassModel(ApiService apiService) {
        return new ForgetPassModel(apiService);
    }

    @Provides
    public IForgetPassContract.IForgetPassView provideForgetePassView() {
        return mForgetPassView;
    }
}
