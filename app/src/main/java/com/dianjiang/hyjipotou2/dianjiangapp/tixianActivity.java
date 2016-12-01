package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

/**
 * Created by hyjipotou2 on 16/4/21.
 */
public class tixianActivity extends Activity {

    private ImageButton fanhui;
    private TextView zhanghu;
    private TextView yue;
    private TextView shenqingjine;
    private RelativeLayout button;
    AlertDialog.Builder builder;
    AlertDialog.Builder builder2;
    private String price;
    EditText text;
    String zhifubao;
    private LinearLayout textDialog;
    String zhufutype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tixianshenqing);
        init();

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getData0();
                setSpinnerDialog();
            }
        });
    }

    public void setSpinnerDialog() {
        final String[] str={"银行卡","支付宝","微信"};

        builder2=new AlertDialog.Builder(this)
                .setTitle("列表框")

                .setNegativeButton("取消", null)
        ;
        builder2.setSingleChoiceItems(str,0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                zhufutype=str[which];
                setTextDialog();

            }
        });
        builder2.show();


    }

    public void setTextDialog() {
        builder = new AlertDialog.Builder(this)
                .setTitle("请输入账号")
                .setView(null)
                .setView(textDialog)
                .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //提取消息
                        zhifubao = text.getText().toString();
                        textDialog = null;
                        if (textDialog == null) {
                            textDialog = (LinearLayout)getLayoutInflater().inflate(R.layout.textdialog, null);
                            text = (EditText) textDialog.findViewById(R.id.text123);
                        }
                        setTakemoney();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textDialog = null;
                        if (textDialog == null) {
                            textDialog = (LinearLayout)getLayoutInflater().inflate(R.layout.textdialog, null);
                            text = (EditText) textDialog.findViewById(R.id.text123);
                        }
                    }
                });
        builder.create().show();
    }

    public void setTakemoney(){
        OkHttpClient client = new OkHttpClient();
        Gson gson1=new Gson();
        HashMap<String,Object> data1 =new HashMap<>();
        data1.put("tixianshenqing",true);
        data1.put("phone",dengluActivity.phone);
        data1.put("zhifubaozhanghao","申请类型:"+zhufutype+"   账号:"+zhifubao);
        OkHttpUtils
                .postString()
                .url(MainActivity.URL + MainActivity.USERAPI)
                .content(gson1.toJson(data1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.i("LOL", "Dwq");
                        Toast.makeText(tixianActivity.this, "提现失败", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(tixianActivity.this, "提现申请发送成功,请耐心等待", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void init(){
        fanhui= (ImageButton) findViewById(R.id.fanhui);
        zhanghu=(TextView)findViewById(R.id.zhanghu);
        yue=(TextView)findViewById(R.id.tixianshenqing_num);
        shenqingjine=(TextView)findViewById(R.id.tixianshenqing_price);
        button= (RelativeLayout) findViewById(R.id.tixianshenqing_button);

        textDialog=(LinearLayout)getLayoutInflater().inflate(R.layout.textdialog, null);
        text= (EditText) textDialog.findViewById(R.id.text123);

        zhanghu.setText(dengluActivity.phone);
        DataFragment dataFragment=DataFragment.getInstance();
        yue.setText(dataFragment.user_datamap.get("zhanghuyue").toString());
        shenqingjine.setText(dataFragment.user_datamap.get("zhanghuyue").toString());
    }

    public void getData0(){
        price=shenqingjine.getText().toString();
    }
}
