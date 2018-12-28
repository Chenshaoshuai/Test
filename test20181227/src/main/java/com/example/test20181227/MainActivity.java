package com.example.test20181227;

import android.content.Intent;
import android.net.IpPrefix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.test20181227.Adapter.RecyclerAdapter;
import com.example.test20181227.Bean.UserBean;
import com.example.test20181227.P.IPresentImpl;
import com.example.test20181227.V.IView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {
    private IPresentImpl iPresent;
    private boolean isLiear = true;
    private RecyclerAdapter adapter;
    private Button btn_toggle;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iPresent = new IPresentImpl(this);
        recyclerView = findViewById(R.id.recycle);
        btn_toggle = findViewById(R.id.btn_toggle);

        btn_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getsponse();
                loadData();
            }
        });

       loadData();
       getsponse();

    }

    private void loadData() {
        Map<String,String>params = new HashMap<>();
        params.put("keywords","手机");
        params.put("page","1");
        iPresent.getRequest(Apis.HOME_DATA,params,UserBean.class);
    }

    private void getsponse() {
        if(isLiear){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
        }else{
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
            gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        adapter = new RecyclerAdapter(isLiear,this);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickLayout(new RecyclerAdapter.OnClickLayout() {
            @Override
            public void onClick(int postion) {
                Intent intent = new Intent(MainActivity.this,LogActivity.class);

                intent.putExtra("keyIn",postion);
                startActivity(intent);
            }
        });
        isLiear=!isLiear;
    }


    @Override
    public void onSuccess(Object data) {
         if(data instanceof  UserBean){
           UserBean userBean = (UserBean) data;
           adapter.setmData(userBean.getData());
         }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresent.onDetach();
    }
}
