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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dianjiang.hyjipotou2.dianjiangapp.wx.WXfenxiang;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/3/12.
 */
public class MyFragment4 extends Fragment{


    View fview;
    private RelativeLayout gerenxinxi;
    private RelativeLayout wodezhanghu;
    private RelativeLayout suoyaofapiao;
    private RelativeLayout bianjiechongzhi;
    private RelativeLayout gongjiangxinxi;
    private ImageView shezhi;
    private RelativeLayout lianxikefu;
    private EditText text;
    private AlertDialog.Builder builder;
    private LinearLayout textDialog;
    private RelativeLayout fankui;
    private SimpleDraweeView touxiang;
    private RelativeLayout fenxiang;
    private Uri uri;
    String fankuiMessage;
    private String dianhua;
    private TextView nicheng;
    private String si;
    private TextView sii;
    private TextView dengji;
    private ProgressBar jingyanzhi;

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
        sii=(TextView)fview.findViewById(R.id.shoujihao);
        fankui=(RelativeLayout)fview.findViewById(R.id.yijian);
        shezhi=(ImageView)fview.findViewById(R.id.shezhi);
        gerenxinxi=(RelativeLayout)fview.findViewById(R.id.xinxi);
        wodezhanghu=(RelativeLayout)fview.findViewById(R.id.zhanghu);
        suoyaofapiao=(RelativeLayout)fview.findViewById(R.id.fapiao);
        fenxiang=(RelativeLayout)fview.findViewById(R.id.fenxiang);
        bianjiechongzhi=(RelativeLayout)fview.findViewById(R.id.chongzhi);
        lianxikefu= (RelativeLayout) fview.findViewById(R.id.kefu);
        textDialog=(LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.textdialog, null);
        text= (EditText) textDialog.findViewById(R.id.text123);
        touxiang=(SimpleDraweeView)fview.findViewById(R.id.touxiang);
        gongjiangxinxi= (RelativeLayout) fview.findViewById(R.id.gongjiang);
        nicheng=(TextView)fview.findViewById(R.id.nicheng);
        dengji= (TextView) fview.findViewById(R.id.wode_dengji);
        jingyanzhi= (ProgressBar) fview.findViewById(R.id.jingyanzhi);

        //加载数据
        getUserData();
        getkefudianhua();
    }
    private void setIntentListener(){
        //各种页面跳转
        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WXfenxiang().init(getActivity());
            }
        });
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

        gongjiangxinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),xiangguangongjiangActivity.class);
                startActivity(intent);
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
        final String[] phonenum={dianhua};
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
                .setPositiveButton("提交", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //提取消息
                        fankuiMessage=text.getText().toString();
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
    public void getUserData(){
        //头像
        ArrayList<String> arrayList;
        DataFragment dataFragment=DataFragment.getInstance();
        arrayList=(ArrayList<String>) dataFragment.user_datamap.get("touxiang");
        if (arrayList.size()>=1){
            uri=mytool.UriFromSenge(arrayList.get(arrayList.size()-1));
        }
        touxiang.setImageURI(uri);

        //
        Map<Double,String> map=new HashMap<>();
        map.put(0.0,"点匠新人");
        map.put(1.0,"点匠一级");
        map.put(2.0,"点匠二级");
        map.put(3.0, "点匠三级");
        map.put(4.0, "点匠四级");
        map.put(5.0, "点匠五级");
        map.put(6.0, "点匠达人");
        sii.setText(dengluActivity.phone);
        dengji.setText(map.get(dataFragment.user_datamap.get("dengji")));
        nicheng.setText(dataFragment.user_datamap.get("nichang").toString());

        /////////////////////经验
        Double jingyan;
        int jingyannow;
        Double jingyanper = Double.valueOf(10);
        jingyan= (Double) dataFragment.user_datamap.get("jingyanzhi");
        jingyannow = (int)((double) (jingyan - ((Double)dataFragment.user_datamap.get("dengji") * jingyanper)));
        jingyanzhi.setProgress(jingyannow);


    }

    public void fankui(){

    }

    public void getkefudianhua(){
        ArrayList<String> arrayList=new ArrayList<>();
        DataFragment dataFragment=DataFragment.getInstance();
        arrayList= (ArrayList<String>) dataFragment.linkedTreeMap.get("lianxikefu");
        dianhua=arrayList.get(0);

    }

    @Override
    public void onResume() {
        super.onResume();
        getUserData();
    }
}
