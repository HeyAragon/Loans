package com.hackhome.loans.presenter.contract;

import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.ui.base.IBase;
import com.hackhome.loans.ui.base.IBaseView;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * desc:
 * author: aragon
 * date: 2017/12/19 0019.
 */
public interface IForgetPassContract {

    interface IForgetPassModel {
        Observable<ResponseBean> findPassword(String type, String name, String code, String pass, String passs);

        Observable<ResponseBean> getAuthCodeRelated(
                @Query("s") String type,
                @Query("name") String name);

    }

    interface IForgetPassView extends IBase {

        <T>void showResponse(T t,int responseType);

    }

}
