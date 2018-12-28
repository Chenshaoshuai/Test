package com.example.test20181227.P;

import com.example.test20181227.CallBack.MyCallBack;
import com.example.test20181227.M.IModelImpl;
import com.example.test20181227.V.IView;

import java.util.Map;

public class IPresentImpl implements IPresent {
    private IView iView;
    private IModelImpl iModel;

    public IPresentImpl(IView iView) {
        this.iView = iView;
        iModel=  new IModelImpl();
    }

    @Override
    public void getRequest(String Url, Map<String, String> params, Class clazz) {
        iModel.getRequest(Url, params, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.onSuccess(data);
            }
        });
    }
    public void onDetach(){
        if(iModel != null){
            iModel=null;
        }else if(iView!=null){
            iView=null;
        }
    }
}
