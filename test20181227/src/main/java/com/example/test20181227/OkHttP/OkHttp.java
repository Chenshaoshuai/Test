package com.example.test20181227.OkHttP;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Time;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttp {
    private static  OkHttp instance;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private OkHttpClient client;

    public static OkHttp getInstance() {
        if(instance==null){
            synchronized (OkHttp.class){
                if(null==instance){
                    instance = new OkHttp();
                }
            }
        }
        return instance;
    }
    public OkHttp(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

    }
    public void getQueue(String Url, Map<String,String> params, final Class clazz, final ICallBack callBack){
        FormBody.Builder builder = new FormBody.Builder();
        for(Map.Entry<String,String> entry: params.entrySet()){
             builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody build = builder.build();
        Request build1 = new Request.Builder()
                .url(Url)
                .post(build)
                .build();
        Call call = client.newCall(build1);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.getFails(e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                String string = response.body().string();
                final Object o = new Gson().fromJson(string, clazz);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.getResponse(o);
                    }
                });
            }
        });
    }
}
