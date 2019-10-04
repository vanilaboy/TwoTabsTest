package org.bitart.twotabstest;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singleton {

    private static final String BASE_URL = "http://kot3.com";
    private static Singleton sSingleton;
    private static Kot3Api sKot3Api;

    private Retrofit mRetrofit;

    public static Singleton getInstance(Context context) {
        if(sSingleton == null) {
            sSingleton = new Singleton(context);
        }
        return sSingleton;
    }

    private Singleton(Context context) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sKot3Api = mRetrofit.create(Kot3Api.class);
    }

    public Kot3Api getApi() {
        return sKot3Api;
    }
}
