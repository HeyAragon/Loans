package com.hackhome.loans.presenter.model;

import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.net.ApiService;
import com.hackhome.loans.presenter.contract.IUserInfoContract;

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
public class UserInfoModel implements IUserInfoContract.IUserInfoModel {

    private ApiService mApiService;

    public UserInfoModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<ResponseBean> changeSexOrNick(String type, Map<String, String> map) {
        return mApiService.changeSexOrNickOrPass(type,map);
    }

    @Override
    public Observable<ResponseBean> uploadPic(@Query("s") String type, @FieldMap Map<String, String> map, @Part("picfile") MultipartBody.Part file) {
        return mApiService.uploadPic(type,map,file);
    }


}
