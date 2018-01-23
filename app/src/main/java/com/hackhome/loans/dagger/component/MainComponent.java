package com.hackhome.loans.dagger.component;

import com.hackhome.loans.dagger.module.MainModule;
import com.hackhome.loans.dagger.scope.ActivityScope;
import com.hackhome.loans.ui.MainActivity;

import dagger.Component;

/**
 * desc:
 * author: aragon
 * date: 2018/1/22 0022.
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = ApplicationComponent.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);

}
