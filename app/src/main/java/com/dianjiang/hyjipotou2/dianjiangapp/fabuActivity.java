package com.dianjiang.hyjipotou2.dianjiangapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/7.
 */
public class fabuActivity extends FragmentActivity implements fabuFragment.OnFragmentInteractionListener{

    private fabuFragment gongchengxinxi;
    private fabuFragment jiebaoyaoqiu;
    private fabuFragment yulanfabu;

    private RelativeLayout xiayibu;
    private FragmentManager fragmentManager;
    private int state=1;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    //DATA
    private Map<String,Object> datamap=new HashMap<>();
    private List<String> list;

    private TextView top_bar;
    private TextView button_text;
    private ImageButton fanhui;
    private Button baocun;
    private String id;

    private final int FIRST=1;
    private final int SECOND=2;
    private final int THIRD=3;

    private final String PAGE1="工程信息";
    private final String PAGE2="接包要求";
    private final String PAGE3="预览发布";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fabugongcheng);
        init();
        fragmentinit();
        setNextListener();
        //getIntent().getStringExtra("id");
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    public void init(){
        final DataFragment dataFragment=DataFragment.getInstance();
        baocun= (Button) findViewById(R.id.gongchengbaocun);
        xiayibu= (RelativeLayout) findViewById(R.id.fabu_xiayibu);
        top_bar=(TextView)findViewById(R.id.fabu2_top_text);
        button_text=(TextView)findViewById(R.id.button_text);
        fanhui= (ImageButton) findViewById(R.id.fanhui);

        gongchengxinxi=fabuFragment.newInstance(PAGE1);
        jiebaoyaoqiu=fabuFragment.newInstance(PAGE2);
        yulanfabu=fabuFragment.newInstance(PAGE3);

        //preferences=this.getSharedPreferences("fabuMessage", MODE_PRIVATE);
        //editor=preferences.edit();

        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataFragment.mhandler4.sendEmptyMessage(0x1234);
                //postSaveHttp();
            }
        });


    }
    public void fragmentinit(){

        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fabu_fragment,gongchengxinxi).commit();
    }
    public void setNextListener(){
        xiayibu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == FIRST) {
                    top_bar.setText("接包要求");
                    state = SECOND;
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.fabu_fragment, jiebaoyaoqiu).commit();
                } else if (state == SECOND) {
                    top_bar.setText("预览发布");
                    button_text.setText("确认发布");
                    state = THIRD;
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.fabu_fragment, yulanfabu).commit();
                } else if (state == THIRD) {
                    postHttp();
                }
            }
        });
    }

   /* public void postSaveHttp(){
        final DataFragment dataFragment=DataFragment.getInstance();
        OkHttpClient client = new OkHttpClient();
        Gson gson1=new Gson();
        HashMap<String,Object> data1 =new HashMap<>();
        data1.put("biaoti",dataFragment.fabu_datamap.get("biaoti"));
        data1.put("miaoshu",dataFragment.fabu_datamap.get("miaoshu"));
        data1.put("xiangxidizhi",dataFragment.fabu_datamap.get("xiangxidizhi"));
        data1.put("gongzhong",dataFragment.fabu_datamap.get("gongzhong"));
        data1.put("yaoqiu",dataFragment.fabu_datamap.get("yaoqiu"));
        data1.put("xinchou",dataFragment.fabu_datamap.get("choulao"));
        data1.put("beizhu",dataFragment.fabu_datamap.get("beizhu"));

        if (dataFragment.fabu_datamap.get("kaishitime")==null){}else if (dataFragment.fabu_datamap.get("kaishitime").equals("选择施工日期")){}else {
            data1.put("kaishitime",dataFragment.fabu_datamap.get("kaishitime"));
        }

        if (dataFragment.fabu_datamap.get("jieshutime")==null){}else if (dataFragment.fabu_datamap.get("jieshutime").equals("选择完工日期")){}else {
            data1.put("jieshutime",dataFragment.fabu_datamap.get("jieshutime"));
        }


        //data1.put("kaishitime",dataFragment.fabu_datamap.get("kaishitime"));
        //data1.put("jieshutime",dataFragment.fabu_datamap.get("jieshutime"));
        data1.put("leixing","0");
        data1.put("zhuangtai",-3);
        data1.put("phone",dengluActivity.phone);
        OkHttpUtils
                .postString()
                .url(MainActivity.URL + MainActivity.PROCESSAPI)
                .content(gson1.toJson(data1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.i("LOL", "Dwq");
                        Toast.makeText(fabuActivity.this, "发生未知错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        id=response;
                        PostFormBuilder buider2 =OkHttpUtils.post();
                        ArrayList<File> files;
                        files= (ArrayList<File>)dataFragment.fabu_datamap.get("tupian");
                        if (files!=null){
                        for(int i=0;i<files.size();i++){
                            buider2.addFile(i+"aaaa",files.get(i).getName(),files.get(i));
                        }
                        buider2
                                .url(MainActivity.URL + MainActivity.IMAGEAPI)
                                .addParams("id",id)
                                .addParams("shangchuan","gongcheng,tupian")
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Request request, Exception e) {
                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(fabuActivity.this,"工程保存成功",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                        }else {
                            Toast.makeText(fabuActivity.this,"工程保存成功",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
    }*/

    public void postHttp(){
        final DataFragment dataFragment=DataFragment.getInstance();
        OkHttpClient client = new OkHttpClient();
        Gson gson1=new Gson();
        HashMap<String,Object> data1 =new HashMap<>();
        data1.put("biaoti",dataFragment.fabu_datamap.get("biaoti"));
        data1.put("miaoshu",dataFragment.fabu_datamap.get("miaoshu"));
        data1.put("xiangxidizhi",dataFragment.fabu_datamap.get("xiangxidizhi"));
        data1.put("gongzhong",dataFragment.fabu_datamap.get("gongzhong"));
        data1.put("yaoqiu",dataFragment.fabu_datamap.get("yaoqiu"));
        data1.put("xinchou",dataFragment.fabu_datamap.get("choulao"));
        data1.put("beizhu",dataFragment.fabu_datamap.get("beizhu"));
        data1.put("kaishitime",dataFragment.fabu_datamap.get("kaishitime"));
        data1.put("jieshutime",dataFragment.fabu_datamap.get("jieshutime"));
        data1.put("suozaidi",dataFragment.province+","+dataFragment.city);
        data1.put("leixing","0");
        data1.put("phone",dengluActivity.phone);
        OkHttpUtils
                .postString()
                .url(MainActivity.URL + MainActivity.PROCESSAPI)
                .content(gson1.toJson(data1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.i("LOL", "Dwq");
                        Toast.makeText(fabuActivity.this, "发生未知错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        id=response;
                        PostFormBuilder buider2 =OkHttpUtils.post();
                        ArrayList<File> files;
                        files= (ArrayList<File>)dataFragment.fabu_datamap.get("tupian");
                        Log.i("LOL",String.valueOf(files.get(0).isFile()));
                        for(int i=0;i<files.size();i++){
                            buider2.addFile(i+"aaaa",files.get(i).getName(),files.get(i));
                        }
                        buider2
                                .url(MainActivity.URL + MainActivity.IMAGEAPI)
                                .addParams("id",id)
                                .addParams("shangchuan","gongcheng,tupian")
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Request request, Exception e) {
                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(fabuActivity.this,"工程发布成功",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                    }
                });
    }


    @Override
    public void onFragmentInteraction(Map<String,Object> map) {

    }
}
