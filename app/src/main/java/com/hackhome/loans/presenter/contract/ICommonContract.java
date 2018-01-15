package com.hackhome.loans.presenter.contract;

import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.ui.base.IBaseView;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * desc:
 * author: aragon
 * date: 2017/12/19 0019.
 */
public interface ICommonContract {

    interface ICommonModel {

        Observable<ResponseBean> getAuthCodeRelated(
                @Query("s") String type,
                @Query("name") String name);

        Observable<ResponseBean> findPassword(String type, String name, String code, String pass, String passs);

    }


}
