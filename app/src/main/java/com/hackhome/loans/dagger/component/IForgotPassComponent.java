package com.hackhome.loans.dagger.component;

import com.hackhome.loans.dagger.module.CommonModule;
import com.hackhome.loans.dagger.module.ForgetPassModule;
import com.hackhome.loans.dagger.scope.ActivityScope;
import com.hackhome.loans.ui.mine.ForgetPasswordActivity;

import dagger.Component;

/**
 * desc:
 * author: aragon
 * date: 2018/1/6 0006.
 */
@ActivityScope
@Component(modules = {ForgetPassModule.class},dependencies = ApplicationComponent.class)
public interface IForgotPassComponent {

    void inject(ForgetPasswordActivity activity);

}
