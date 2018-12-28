package com.example.test20181227.M;

import com.example.test20181227.CallBack.MyCallBack;
import com.example.test20181227.OkHttP.ICallBack;
import com.example.test20181227.OkHttP.OkHttp;

import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void getRequest(String Url, Map<String, String> params, Class clazz, final MyCallBack callBack) {
        OkHttp.getInstance().getQueue(Url, params, clazz, new ICallBack() {
            @Override
            public void getResponse(Object obj) {
                callBack.onSuccess(obj);
            }

            @Override
            public void getFails(Exception e) {
               callBack.onSuccess(e);
            }
        });
    }
}
