package com.hackhome.loans.dagger.component;

import com.hackhome.loans.dagger.module.CommonModule;
import com.hackhome.loans.dagger.module.UserInfoModule;
import com.hackhome.loans.dagger.scope.ActivityScope;
import com.hackhome.loans.ui.mine.UserInfoActivity;

import dagger.Component;

/**
 * desc:
 * author: aragon
 * date: 2018/1/5 0005.
 */
@ActivityScope
@Component(modules = {UserInfoModule.class},dependencies = ApplicationComponent.class)
public interface IUserInfoComponent {
    void inject(UserInfoActivity userInfoActivity);
}
