package com.dianjiang.hyjipotou2.dianjiangapp;


import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by hyjipotou2 on 16/3/12.
 */
public class MyFragment4 extends Fragment{


    View fview;
    private RelativeLayout gerenxinxi;
    private RelativeLayout wodezhanghu;
    private RelativeLayout suoyaofapiao;
    private RelativeLayout bianjiechongzhi;
    private ImageView shezhi;
    private RelativeLayout lianxikefu;
    private EditText text;
    private AlertDialog.Builder builder;
    private LinearLayout textDialog;
    private RelativeLayout fankui;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fview=inflater.inflate(R.layout.fragment4,container,false);

        init();
        setIntentListener();


        return fview;
    }

    private void init(){
        //实例化
        fankui=(RelativeLayout)fview.findViewById(R.id.yijian);
        shezhi=(ImageView)fview.findViewById(R.id.shezhi);
        gerenxinxi=(RelativeLayout)fview.findViewById(R.id.xinxi);
        wodezhanghu=(RelativeLayout)fview.findViewById(R.id.zhanghu);
        suoyaofapiao=(RelativeLayout)fview.findViewById(R.id.fapiao);
        bianjiechongzhi=(RelativeLayout)fview.findViewById(R.id.chongzhi);
        lianxikefu= (RelativeLayout) fview.findViewById(R.id.kefu);
        textDialog=(LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.textdialog, null);
        text= (EditText) textDialog.findViewById(R.id.text123);
    }
    private void setIntentListener(){
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
        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),shezhiActivity.class);
                startActivity(intent);
            }
        });
        lianxikefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lianxidialog();
            }
        });
        fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDialog();
            }
        });
    }

    public void lianxidialog(){
        final String[] phonenum={"18842635112"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("点击拨打客服电话")
                .setItems(phonenum, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phonenum[0]));
                        startActivity(intent);
                    }
                });
        builder.create().show();
    }

    public void setTextDialog(){
        builder=new AlertDialog.Builder(getActivity())
                .setTitle("请输入反馈信息")
                .setView(null)
                .setView(textDialog)
                .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //提取消息

                        textDialog=null;
                        if (textDialog==null){
                            textDialog=(LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.textdialog, null);
                            text= (EditText) textDialog.findViewById(R.id.text123);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textDialog=null;
                        if (textDialog==null){
                            textDialog=(LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.textdialog, null);
                            text= (EditText) textDialog.findViewById(R.id.text123);
                        }
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                textDialog=null;
                if (textDialog==null){
                    textDialog=(LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.textdialog, null);
                    text= (EditText) textDialog.findViewById(R.id.text123);
                }
            }
        });
        builder.create().show();
    }
}