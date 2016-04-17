package com.dianjiang.hyjipotou2.dianjiangapp;


import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by hyjipotou2 on 16/3/12.
 */
public class MyFragment4 extends Fragment {

    private RelativeLayout gerenxinxi;
    private RelativeLayout wodezhanghu;
    private RelativeLayout suoyaofapiao;
    private RelativeLayout bianjiechongzhi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fview=inflater.inflate(R.layout.fragment4,container,false);
        //实例化
        gerenxinxi=(RelativeLayout)fview.findViewById(R.id.xinxi);
        wodezhanghu=(RelativeLayout)fview.findViewById(R.id.zhanghu);
        suoyaofapiao=(RelativeLayout)fview.findViewById(R.id.fapiao);
        bianjiechongzhi=(RelativeLayout)fview.findViewById(R.id.chongzhi);

        //各种页面跳转
        gerenxinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),xinxixiugaiActivity.class);
                startActivity(intent);
            }
        });
        wodezhanghu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),wodezhanghuActivity.class);
                startActivity(intent);
            }
        });
        suoyaofapiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),suoyaofapiaoActivity.class);
                startActivity(intent);
            }
        });


        return fview;
    }
}
