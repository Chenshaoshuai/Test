package com.example.test20181227.M;

import com.example.test20181227.CallBack.MyCallBack;

import java.util.Map;

public interface IModel {
    void getRequest(String Url, Map<String,String> params, Class clazz, MyCallBack callBack);
}
