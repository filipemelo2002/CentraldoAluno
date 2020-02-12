package com.feathercompany.www.ApiRequest;


import android.content.Context;
import android.view.View;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestApi {
    private Context context;
    private View view;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private RequestInterface service;
    public RestApi(Context context, View view){
        this.context = context;
        this.view = view;
    }

    public RequestInterface request(){
        this.okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://centraldoaluno.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();

        this.service= retrofit.create(RequestInterface.class);
        return service;

    }





}
