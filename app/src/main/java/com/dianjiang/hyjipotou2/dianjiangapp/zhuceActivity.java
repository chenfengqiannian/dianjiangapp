package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

/**
 * Created by hyjipotou2 on 16/4/15.
 */
public class zhuceActivity extends Activity {

    public static final String URL="http://192.168.191.1:8000";
    public static final String USERAPI="/userapi/";

    public static final String GONGJIANG="1";
    public static final String KEHU="2";

    private EditText shoujihao;
    private EditText mima;
    private EditText querenmima;
    private EditText yanzhengma;
    private EditText xiangxidizhi;
    private ImageView huoqu;
    private RelativeLayout zhuce;
    private RadioButton gongjiang;
    private RadioButton kehu;
    private String phone;
    private String password;
    private String password_ok;
    private String test;
    private String gps;
    private String getTest;
    private String state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuce);
        init();


        //设置监听器
        huoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTest();
            }
        });
        gongjiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state=GONGJIANG;
                Log.i("LOL","1");
            }
        });
        kehu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state=KEHU;
                Log.i("LOL","2");
            }
        });
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdata();
                Log.i("LOL","lol");

                //判断确认密码是否相同
                if (password.equals(password_ok)){

                    //判断验证码是否正确
                    if (test.equals(getTest)){
                        setZhuce();
                    }
                    else {
                        Toast.makeText(zhuceActivity.this,"验证码不正确",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.i("LOL","ok");
                    Toast.makeText(zhuceActivity.this,"两次密码输入不相同",Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    public void init(){
        shoujihao=(EditText)findViewById(R.id.zhuce_shoujihao);
        mima=(EditText)findViewById(R.id.zhuce_mima);
        querenmima=(EditText)findViewById(R.id.zhuce_queren);
        yanzhengma=(EditText)findViewById(R.id.zhuce_yanzhengma);
        xiangxidizhi=(EditText)findViewById(R.id.zhuce_dizhi);
        huoqu=(ImageView)findViewById(R.id.huoquimg);
        gongjiang=(RadioButton)findViewById(R.id.zhuce_gongjiang);
        kehu=(RadioButton)findViewById(R.id.zhuce_kehu);
        zhuce=(RelativeLayout)findViewById(R.id.zhanghao_button);
    }

    public void setZhuce(){
        OkHttpClient client = new OkHttpClient();
        Gson gson1=new Gson();
        HashMap<String,String> data1 =new HashMap<>();
        data1.put("phone", "1");
        OkHttpUtils
                .postString()
                .url(URL + USERAPI)
                .content(gson1.toJson(data1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.d("LOL", "Dwq");
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.d("LOL", response);
                    }
                });
    }

    public void setTest(){
        OkHttpClient client = new OkHttpClient();
        Gson gson1=new Gson();
        HashMap<String,String> data1 =new HashMap<>();
        data1.put("phone", "1");
        OkHttpUtils
                .postString()
                .url(URL + USERAPI)
                .content(gson1.toJson(data1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.d("LOL", "Dwq");
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.d("LOL", response);
                    }
                });
    }

    public void setdata(){
        phone=shoujihao.getText().toString();
        password=mima.getText().toString();
        password_ok=querenmima.getText().toString();
        test=yanzhengma.getText().toString();
        gps=xiangxidizhi.getText().toString();
    }
}
