package com.hackhome.loans.presenter.contract;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.bean.UserInfo;
import com.hackhome.loans.ui.base.IBase;
import com.hackhome.loans.ui.base.IBaseView;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * desc:
 * author: aragon
 * date: 2018/1/3 0003.
 */
public interface ILoginContract{

    interface ILoginModel{

        Observable<Response<ResponseBean>> login(String type,String name,String pass);

        Observable<UserInfo> getUserInfo(String type,Map<String,String> header);
    }

    interface ILoginView extends IBase{
        void showUserInfo(UserInfo userInfo);
    }


}
