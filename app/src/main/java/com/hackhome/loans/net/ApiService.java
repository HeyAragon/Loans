package com.hackhome.loans.net;

import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.bean.UpdateBean;
import com.hackhome.loans.bean.UserInfo;
import com.hackhome.loans.common.utils.UserUtil;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

/**
 * desc:
 * author: aragon
 * date: 2017/12/18 0018.
 */
public interface ApiService {

//    @GET("index.php?m=hqwy&c=homePage&a=index&version=1.2.7&channel=sc-xiaomi_fr_scj&appid=111")
//    Observable<BaseDataBean<DataBean>> getHomeData();

    @GET("dk.asp")
    Observable<DataBean> getHomeData(
            @Query("a") String type,
            @Query("sort") int sort,
            @Query("page") int page,
            @Query("ob") int ob);

    @GET("dk.asp")
    Observable<UpdateBean> checkNewVersion(@Query("a") String type);

    /**
     * 验证码相关
     * @param type 类型
     * @param name 账号（手机号）
     * @return Observable
     */
    @GET("wnqk/load/")
    Observable<ResponseBean> getAuthCodeRelated(
            @Query("s") String type,
            @Query("name") String name);

    /**
     * 注册
     * @param type reg
     * @param name 账号（手机号）
     * @param code 验证码
     * @param pass 密码
     * @param passs 密码
     * @return Observable
     */
    @GET("wnqk/load/")
    Observable<ResponseBean> register(
            @Query("s") String type,
            @Query("name") String name,
            @Query("code") String code,
            @Query("pass") String pass,
            @Query("passs") String passs);

    /**
     * 登录
     * @param type login
     * @param name 账号
     * @param pass 密码
     * @return Observable
     */
    @GET("wnqk/load/")
    Observable<Response<ResponseBean>> login(
            @Query("s") String type,
            @Query("name") String name,
            @Query("pass") String pass
           );

    /**
     * 获取用户信息
     * @return Observable
     */
    @FormUrlEncoded
    @POST("wnqk/ajax/")
    Observable<UserInfo> getUserInfo(
            @Query("s") String type,
            @FieldMap Map<String,String> map
            );

    /**
     * 修改 性别or逆臣
     * @param type edit
     * @param map cookie+nick/sex
     * @return
     */
    @FormUrlEncoded
    @POST("wnqk/ajax/")
    Observable<ResponseBean> changeSexOrNickOrPass(
            @Query("s") String type,
            @FieldMap(encoded = true) Map<String,String> map
    );



    @Multipart
    @POST("wnqk/ajax/")
    Observable<ResponseBean> uploadPic(
            @Query("s") String type,
            @PartMap Map<String, String> map,
            @Part MultipartBody.Part file
    );


}
