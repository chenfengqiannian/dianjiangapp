package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedHashTreeMap;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by hyjipotou2 on 16/4/15.
 */
public class dengluActivity extends Activity implements View.OnClickListener{

    public static final String URL="http://192.168.191.1:8000";
    public static final String USERAPI="/userapi/";

    private EditText shoujihao;
    private EditText mima;
    private RelativeLayout denglu;
    private TextView zhuce;
    private TextView zhaohui;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static String phone;
    private boolean state;


    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences=getPreferences(MODE_PRIVATE);
        editor=sharedPreferences.edit();

        setContentView(R.layout.zongdenglu);

        init();

        denglu.setOnClickListener(this);
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(dengluActivity.this,zhuceActivity.class);
                startActivity(intent);
            }
        });
        zhaohui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(dengluActivity.this,zhaohuiActivity.class);
                startActivity(intent);
            }
        });

    }

    public void init(){
        shoujihao=(EditText)findViewById(R.id.denglu_shoujihao);
        mima=(EditText)findViewById(R.id.denglu_mima);
        denglu=(RelativeLayout)findViewById(R.id.zhanghao_button);
        zhuce=(TextView)findViewById(R.id.denglu_zhuce);
        zhaohui=(TextView)findViewById(R.id.denglu_wangji);

        GetDenglu();
        if ("".equals(phone)||"".equals(password)){
            return;
        }
        else {
            dengluHttp();
        }
    }

    @Override
    public void onClick(View v) {

        setdata();

        if (phone.equals("")||password.equals("")){
            Toast.makeText(this,"账号密码不能为空",Toast.LENGTH_LONG);
            return;
        }

        final DataFragment fragment=DataFragment.getInstance();

        ProgressDialog.show(this, "登录中", "登录中,请稍后", false, true);

        dengluHttp();
    }

    public void setdata(){
        phone=shoujihao.getText().toString();
        password=mytool.getMD5Str(mima.getText().toString());
    }
    public void SaveDenglu(){
        editor.putString("zhanghao",phone);
        editor.putString("mima",password);
        editor.putBoolean("state",state);
        editor.commit();
    }
    public void GetDenglu(){
        phone=sharedPreferences.getString("zhanghao",null);
        password=sharedPreferences.getString("mima",null);
        state=sharedPreferences.getBoolean("state",true);
    }

    public void dengluHttp(){
        OkHttpUtils
                .get()
                .url(MainActivity.URL + MainActivity.USERAPI)
                .addParams("phone", dengluActivity.phone)
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
                        DataFragment fragment=DataFragment.getInstance();
                        fragment.user_datamap = (LinkedTreeMap<String, Object>) response;
                        state= (boolean) fragment.user_datamap.get("job");
                        Gson gson=new Gson();
                        HashMap<String,String> data =new HashMap<>();
                        data.put("phone", phone);
                        data.put("userpw", password);
                        data.put("leixing", "0");
                        OkHttpUtils
                                .postString()
                                .url(MainActivity.URL + MainActivity.USERAPI)
                                .content(gson.toJson(data))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Request request, Exception e) {
                                        Log.d("LOL", "Dwq");
                                        Toast.makeText(dengluActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(dengluActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                                        SaveDenglu();
                                        if (state == false) {
                                            Intent intent = new Intent(dengluActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        Log.d("LOL", response);
                                    }
                                });
                    }
                });
    }
}
