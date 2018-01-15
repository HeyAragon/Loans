package com.hackhome.loans.dagger.component;

import com.hackhome.loans.dagger.module.LoginModule;
import com.hackhome.loans.dagger.module.RegisterModule;
import com.hackhome.loans.dagger.scope.ActivityScope;
import com.hackhome.loans.ui.mine.LoginActivity;
import com.hackhome.loans.ui.mine.RegisterActivity;

import dagger.Component;

/**
 * desc:
 * author: aragon
 * date: 2018/1/3 0003.
 */
@ActivityScope
@Component(modules = LoginModule.class, dependencies = ApplicationComponent.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

}
