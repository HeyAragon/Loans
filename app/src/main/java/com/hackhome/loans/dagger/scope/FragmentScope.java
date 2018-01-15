package com.hackhome.loans.dagger.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * desc:
 * author: aragon
 * date: 2017/12/16 0016.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface FragmentScope {
}
