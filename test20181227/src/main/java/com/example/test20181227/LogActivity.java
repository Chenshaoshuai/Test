package com.example.test20181227;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test20181227.Bean.ImageBean;
import com.example.test20181227.Fragment.Fragment_comment;
import com.example.test20181227.Fragment.Fragment_commodity;
import com.example.test20181227.Fragment.Fragment_details;


public class LogActivity extends AppCompatActivity  {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Intent intent = getIntent();
        int keyIn = intent.getIntExtra("keyIn", 0);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            String[] strings = new String[]{"商品","详情","评论"};
            @Override
            public Fragment getItem(int i) {
                switch (i){
                    case 0:
                       return new Fragment_commodity();
                    case 1:
                        return new Fragment_details();
                    case 2:
                        return new Fragment_comment();
                        default:
                            return new Fragment_comment();
                }
            }

            @Override
            public int getCount() {
                return strings.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return strings[position];
            }
        });
          tabLayout.setupWithViewPager(viewPager);

    }
}
