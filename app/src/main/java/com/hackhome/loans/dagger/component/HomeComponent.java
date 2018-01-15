package com.hackhome.loans.dagger.component;

import com.hackhome.loans.dagger.scope.FragmentScope;
import com.hackhome.loans.dagger.module.HomeModule;
import com.hackhome.loans.ui.home.HomeFragment;
import com.hackhome.loans.ui.loan.LoanFragment;

import dagger.Component;

/**
 * desc:
 * author: aragon
 * date: 2017/12/19 0019.
 */
@FragmentScope
@Component(modules = HomeModule.class, dependencies = ApplicationComponent.class)
public interface HomeComponent {

    void inject(HomeFragment homeFragment);
    void inject(LoanFragment loanFragment);
}
