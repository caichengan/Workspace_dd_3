package com.diandianguanjia.workspace_dd_3.base;

import com.diandianguanjia.workspace_dd_3.net.ApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by an on 2017/9/9.
 */

public class BaseApi {

    public static ApiService getApiService(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://weixin.dd1760.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }

}
