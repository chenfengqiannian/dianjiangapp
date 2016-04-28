package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.w3c.dom.ProcessingInstruction;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hyjipotou2 on 16/4/8.
 */
public class suoyaofapiaoActivity extends Activity {

    private ImageButton fanhui;

    private RelativeLayout fapiaomingcheng;
    private RelativeLayout fapiaotaitou;
    private RelativeLayout youzhengbianma;
    private RelativeLayout shoujianren;
    private RelativeLayout jine;
    private RelativeLayout dizhi;
    private RelativeLayout shijian;
    private RelativeLayout tijiao;
    private RelativeLayout gongchenghao;

    private TextView mingchengtext;
    private TextView taitoutext;
    private TextView youzhengtext;
    private TextView shoujianrentext;
    private TextView jinetext;
    private TextView dizhitext;
    private TextView shijiantext;
    private TextView gongchenghaotext;

    private LinearLayout textDialog;
    private EditText text;
    private AlertDialog.Builder builder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suoyaofapiao);
        init();
        setListener();
    }

    public void init(){
        fanhui=(ImageButton)findViewById(R.id.suoyaofanhui);

        fapiaomingcheng= (RelativeLayout) findViewById(R.id.suoyaofapiao_name);
        fapiaotaitou=(RelativeLayout)findViewById(R.id.suoyaofapiao_taitou);
        youzhengbianma=(RelativeLayout)findViewById(R.id.suoyaofapiao_bianma);
        shoujianren=(RelativeLayout)findViewById(R.id.suoyaofapiao_shoujianren);
        jine=(RelativeLayout)findViewById(R.id.suoyaofapiao_jine);
        dizhi= (RelativeLayout) findViewById(R.id.suoyaofapiao_dizhi);
        shijian=(RelativeLayout)findViewById(R.id.suoyaofapiao_shijian);
        gongchenghao=(RelativeLayout)findViewById(R.id.suoyaofapiao_gongchenghao);

        mingchengtext= (TextView) findViewById(R.id.nametext);
        taitoutext=(TextView)findViewById(R.id.taitoutext);
        youzhengtext=(TextView)findViewById(R.id.youzhengtext);
        shoujianrentext=(TextView)findViewById(R.id.shoujiantext);
        jinetext= (TextView) findViewById(R.id.jine_text);
        dizhitext= (TextView) findViewById(R.id.dizhitext);
        shijiantext= (TextView) findViewById(R.id.shijiantext);
        tijiao=(RelativeLayout)findViewById(R.id.suoyaofapiao_tijiao);
        gongchenghaotext= (TextView) findViewById(R.id.gongchenghaotext);

        textDialog=(LinearLayout)getLayoutInflater().inflate(R.layout.textdialog, null);
        text= (EditText) textDialog.findViewById(R.id.text123);
    }
    public void setListener(){
        fapiaomingcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDialog(mingchengtext);
            }
        });
        fapiaotaitou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDialog(taitoutext);
            }
        });
        youzhengbianma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDialog(youzhengtext);
            }
        });
        shoujianren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDialog(shoujianrentext);
            }
        });
        jine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDialog(jinetext);
            }
        });
        dizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDialog(dizhitext);
            }
        });
        shijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDialog(shijiantext);
            }
        });
        gongchenghao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDialog(gongchenghaotext);
            }
        });


        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postHttp();
            }
        });

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTextDialog(final TextView textView){
        builder=new AlertDialog.Builder(this)
                .setTitle("请输入要求内容")
                .setView(null)
                .setView(textDialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(text.getText().toString());
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

    public void postHttp(){
        OkHttpClient client = new OkHttpClient();
        Gson gson1=new Gson();
        HashMap<String,Object> data1 =new HashMap<>();
        data1.put("fapiaomingcheng",mingchengtext.getText().toString());
        data1.put("fapiaotaitou",taitoutext.getText().toString());
        data1.put("fapiaoyouzheng",youzhengtext.getText().toString());
        data1.put("fapiaodizhi",dizhitext.getText().toString());
        data1.put("fapiaojisongshijian",shijiantext.getText().toString());
        data1.put("fapiaoshoujian",shoujianrentext.getText().toString());
        data1.put("fapiaojine",jinetext.getText().toString());
        data1.put("id",gongchenghaotext.getText().toString());
        OkHttpUtils
                .postString()
                .url(MainActivity.URL + MainActivity.PROCESSAPI)
                .content(gson1.toJson(data1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.i("LOL", "Dwq");
                        Toast.makeText(suoyaofapiaoActivity.this, "发生未知错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(suoyaofapiaoActivity.this,"发票申请提交成功",Toast.LENGTH_LONG).show();
                        finish();

                    }
                });
    }


}
