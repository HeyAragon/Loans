package com.hackhome.loans.dagger.module;

import com.hackhome.loans.common.utils.AppConfig;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.net.ApiService;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * desc:
 * author: aragon
 * date: 2017/12/16 0016.
 */
@Module
public class HttpModule {

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

    }

    @Singleton
    @Provides
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    private Interceptor mInterceptor = chain -> {
        Request request = chain.request();
        AppConfig.getInstance().setCurrentHttpUrl(request.url());
        long startTime = System.currentTimeMillis();
        Response response = null;
        response = chain.proceed(request) ;
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
//            okhttp3.MediaType mediaType = response.body().contentType();
//            String content = response.body().string();
        KLog.e("----------Request Start----------------");
        KLog.e("| " + request.toString());
//            KLog.json("| Response:" + content);
        KLog.e("----------Request End:" + duration + "毫秒----------");
        return response;
    };
}
