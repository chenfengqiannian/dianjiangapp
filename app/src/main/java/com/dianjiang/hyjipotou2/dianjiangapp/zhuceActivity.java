package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.content.Intent;
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

    public static final boolean GONGJIANG=false;
    public static final boolean KEHU=true;
    public static final int TOAST_TIME=Toast.LENGTH_LONG;

    private EditText shoujihao;
    private EditText mima;
    private EditText querenmima;
    private EditText yanzhengma;
    private EditText xiangxidizhi;
    private ImageView huoqu;
    private RelativeLayout zhuce;
    private RadioButton gongjiang;
    private RadioButton kehu;
    private String password;
    private String password_ok;
    private String test;
    private String gps;
    private String getTest;
    private boolean state;
    public static String phonenow;


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
                Log.i("LOL","kehu");
            }
        });
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdata();

                //判断确认密码是否相同
                if (password.equals(password_ok)){

                    //判断验证码是否正确
                    if (true){
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
        HashMap<String,Object> data1 =new HashMap<>();
        data1.put("job",state);
        data1.put("userpw",password);
        data1.put("didianchar",gps);
        data1.put("leixing","1");
        data1.put("phone",phonenow);
        OkHttpUtils
                .postString()
                .url(URL + USERAPI)
                .content(gson1.toJson(data1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.i("LOL", "Dwq");
                        Toast.makeText(zhuceActivity.this,"该用户名已注册",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        if (state==KEHU){
                            Toast.makeText(zhuceActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                            dengluActivity.phone=phonenow;
                            //DataFragment fragment=DataFragment.getInstance();
                            //fragment.getData();
                            Intent intent=new Intent(zhuceActivity.this,dengluActivity.class);
                            startActivity(intent);

                        }
                        if (state==GONGJIANG){
                            Toast.makeText(zhuceActivity.this,"请继续完善注册信息",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(zhuceActivity.this,GongjiangzhuceActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    public void setTest(){

        OkHttpClient client = new OkHttpClient();
        Gson gson1=new Gson();
        HashMap<String,String> data1 =new HashMap<>();
        data1.put("phone",phonenow);
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
        phonenow=shoujihao.getText().toString();
        password=mytool.getMD5Str(mima.getText().toString());
        password_ok=mytool.getMD5Str(querenmima.getText().toString());
        test=yanzhengma.getText().toString();
        gps=xiangxidizhi.getText().toString();

    }
}
