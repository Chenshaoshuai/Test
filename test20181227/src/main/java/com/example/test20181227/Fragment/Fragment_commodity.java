package com.example.test20181227.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test20181227.Apis;
import com.example.test20181227.Bean.ImageBean;
import com.example.test20181227.Message;
import com.example.test20181227.P.IPresentImpl;
import com.example.test20181227.R;
import com.example.test20181227.V.IView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment_commodity extends Fragment implements IView {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.log_title)
    TextView log_title;
    @BindView(R.id.log_price)
    TextView log_price;
    private IPresentImpl iPresent;
    private List<String> mData;
    private ImageBean bean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_commodity,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        iPresent = new IPresentImpl(this);
        log_price = view.findViewById(R.id.log_price);
        log_title = view.findViewById(R.id.log_title);
        mData = new ArrayList<>();
        loadData();
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Uri uri = Uri.parse((String) path);
                imageView.setImageURI(uri);
            }

            @Override
            public ImageView createImageView(Context context) {
                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);

                return simpleDraweeView;
            }
        });
    }

    @OnClick(R.id.log_title)
    public void setTitle(){
        EventBus.getDefault().postSticky(new Message(bean.getData().getTitle(),1));
    }
    @OnClick(R.id.log_price)
    public void setPrice(){
        EventBus.getDefault().postSticky(new Message(bean.getData().getPrice()+"",2));
    }
    private void loadData() {
        Map<String,String> map = new HashMap<>();
        map.put("pid","1");
        iPresent.getRequest(Apis.IMAGE_DATA,map,ImageBean.class);
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof ImageBean){
            bean = (ImageBean) data;
         String image = bean.getData().getImages();
         for(int i =0;i<image.length();i++){
            String replace = image.split("\\|")[0].replace("https","http");
            mData.add(replace);
         }
         banner.setImages(mData);
         banner.start();
         log_title.setText(bean.getData().getTitle());
         log_price.setText(bean.getData().getPrice()+"");
     }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresent.onDetach();
    }
}
