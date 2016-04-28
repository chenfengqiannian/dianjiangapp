package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/20.
 */
public class dianjiangItemActivity extends Activity {

    private TextView name;
    private SimpleDraweeView touxiang;
    private TextView gongzhong;
    private TextView rixin;
    private TextView dengji;
    private RatingBar pingfen;
    private TextView gongsi;
    private TextView gongzuodi;
    private TextView biaoqian;
    private SimpleDraweeView zhengshu1;
    private SimpleDraweeView zhengshu2;
    private TextView ziwojieshao;
    private LinkedTreeMap<String,Object> user_datamap=new LinkedTreeMap<>();
    private Handler mHandler;
    private Map<Double,String> map=new HashMap<>();
    private ImageButton fanhui;
    private RelativeLayout yaoqing;
    private LinearLayout textDialog;
    private EditText text;
    private String string;
    private Double pingjia;


    String gongsi_;
    String gongzuodi_;
    String biaoqian_;
    String ziwojieshao_;
    ArrayList<String> imagelist=new ArrayList<>();
    Map<Integer,SimpleDraweeView> imageMap=new HashMap<>();




    DataFragment dataFragment=DataFragment.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);

        setContentView(R.layout.dianjiangxinxi);
        getDianjiangInformation();
        init();

        name.setText(dataFragment.dianjiangItemBean.name);

        gongzhong.setText(dataFragment.dianjiangItemBean.gongzhong);
        rixin.setText(dataFragment.dianjiangItemBean.price.toString());
        dengji.setText(map.get(dataFragment.dianjiangItemBean.level));
        touxiang.setImageURI(dataFragment.dianjiangItemBean.imageuri);
        //pingfen.setRating((float) ((double) dataFragment.dianjiangItemBean.pingjia));

        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==0x1233){
                    gongsi.setText(gongsi_);
                    gongzuodi.setText(gongzuodi_);
                    biaoqian.setText(biaoqian_);
                    ziwojieshao.setText(ziwojieshao_);
                    pingfen.setRating((float)((double)pingjia));
                    for (int count=0;count<imagelist.size();count++){
                        imageMap.get(count).setImageURI(mytool.UriFromSenge(imagelist.get(count)));
                    }
                }
            }
        };

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yaoqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDialog();
            }

        });

    }

    public void init(){
        name= (TextView) findViewById(R.id.dianjiang_name);
        gongzhong=(TextView)findViewById(R.id.dianjiang_gongzhong);
        rixin=(TextView)findViewById(R.id.dianjiang_rixin_num);
        dengji=(TextView)findViewById(R.id.dianjiang_dengji);
        pingfen=(RatingBar)findViewById(R.id.dianjiang_xingji);
        gongsi= (TextView) findViewById(R.id.dianjiang_gongsi);
        gongzuodi= (TextView) findViewById(R.id.dianjiang_gongzuodi);
        biaoqian=(TextView)findViewById(R.id.dianjiang_biaoqian);
        zhengshu1=(SimpleDraweeView)findViewById(R.id.dianjiang_zheng1);
        zhengshu2=(SimpleDraweeView)findViewById(R.id.dianjiang_zheng2);
        ziwojieshao=(TextView)findViewById(R.id.ziwojieshao);
        touxiang=(SimpleDraweeView)findViewById(R.id.dianjiang_touxiang);
        fanhui=(ImageButton)findViewById(R.id.dianjiang_fanhui);
        yaoqing=(RelativeLayout)findViewById(R.id.yaoqing);
        textDialog=(LinearLayout)getLayoutInflater().inflate(R.layout.textdialog, null);
        text= (EditText) textDialog.findViewById(R.id.text123);
        imageMap.put(0, zhengshu1);
        imageMap.put(1, zhengshu2);

        map.put(0.0, "新人工匠");
        map.put(1.0, "工匠一级");
        map.put(2.0, "工匠二级");
        map.put(3.0, "工匠三级");
        map.put(4.0,"工匠四级");
        map.put(5.0, "工匠五级");
        map.put(6.0, "工匠达人");
    }

    public void getDianjiangInformation(){
        OkHttpUtils
                .get()
                .url(MainActivity.URL + MainActivity.USERAPI)
                .addParams("phone", dataFragment.dianjiangItemBean.phone)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {

                        Log.i("LOL", "response");
                        String string = response.body().string();

                        Object ps = new Gson().fromJson(string, new TypeToken<Object>() {
                        }.getType());
                        return ps;
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        user_datamap = (LinkedTreeMap<String, Object>) response;
                        gongsi_= (String) user_datamap.get("gongsi");
                        gongzuodi_=(String)user_datamap.get("gongzuodi");
                        biaoqian_=(String)user_datamap.get("biaoqian");
                        ziwojieshao_=(String)user_datamap.get("ziwojieshao");
                        imagelist= (ArrayList<String>) user_datamap.get("zhengshu");
                        pingjia=(Double)user_datamap.get("pingjiaxingji");

                        mHandler.sendEmptyMessage(0x1233);

                    }
                });
    }

    public void setTextDialog(){
        AlertDialog.Builder builder;
        builder=new AlertDialog.Builder(this)
                .setTitle("请输入反馈信息")
                .setView(null)
                .setView(textDialog)
                .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //提取消息
                        string=text.getText().toString();
                        httpwori(Integer.valueOf(string));
                        textDialog=null;
                        if (textDialog==null){
                            textDialog=(LinearLayout)getLayoutInflater().inflate(R.layout.textdialog, null);
                            text= (EditText) textDialog.findViewById(R.id.text123);

                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textDialog=null;
                        if (textDialog==null){
                            textDialog=(LinearLayout)getLayoutInflater().inflate(R.layout.textdialog, null);
                            text= (EditText) textDialog.findViewById(R.id.text123);
                        }
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                textDialog = null;
                if (textDialog == null) {
                    textDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.textdialog, null);
                    text = (EditText) textDialog.findViewById(R.id.text123);
                }
            }
        });
        builder.create().show();
    }

    public void httpwori(Integer id){
        ArrayList<String> strings=new ArrayList<String>();
        DataFragment dataFragment=DataFragment.getInstance();
        for (LinkedTreeMap<String,Object> nidaye:(ArrayList<LinkedTreeMap<String,Object>>)dataFragment.user_datamap.get("gongcheng_set")){
            if ((int)(double)nidaye.get("id")==id) {
                strings = (ArrayList<String>) nidaye.get("yaoqing");
                strings.add(dataFragment.dianjiangItemBean.phone);

                OkHttpClient client = new OkHttpClient();
                Gson gson1 = new Gson();
                HashMap<String, Object> data1 = new HashMap<>();
                data1.put("yaoqing", strings);
                data1.put("id", id);
                OkHttpUtils
                        .postString()
                        .url(MainActivity.URL + MainActivity.PROCESSAPI)
                        .content(gson1.toJson(data1))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                Log.i("LOL", "Dwq");
                                Toast.makeText(dianjiangItemActivity.this, "邀请发送失败", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(dianjiangItemActivity.this, "邀请发送成功", Toast.LENGTH_LONG).show();
                            }
                        });
            }}}

}
