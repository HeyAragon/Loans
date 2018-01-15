package com.hackhome.loans.presenter.contract;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.ui.base.IBase;
import com.hackhome.loans.ui.base.IBaseView;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * desc:
 * author: aragon
 * date: 2018/1/5 0005.
 */
public interface IUserInfoContract {

    interface IUserInfoModel  {

        Observable<ResponseBean> changeSexOrNick(String type, Map<String, String> map);

        Observable<ResponseBean> uploadPic(
                @Query("s") String type,
                @FieldMap Map<String, String> map,
                @Part("picfile") MultipartBody.Part file
        );

    }

    interface UserInfoView extends IBase {

        <T> void showResponse(T t, int responseType);

    }



}
