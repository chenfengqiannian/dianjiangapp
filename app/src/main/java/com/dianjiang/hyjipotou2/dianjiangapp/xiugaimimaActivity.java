package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

/**
 * Created by hyjipotou2 on 16/4/17.
 */
public class xiugaimimaActivity extends Activity {

    private EditText shoujihao;
    private EditText old_password;
    private EditText new_password;
    private RelativeLayout wancheng;

    private String phonenumber;
    private String password0;
    private String password1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiugaimima);
        sharedPreferences=getPreferences(MODE_PRIVATE);
        editor=sharedPreferences.edit();
        init();
        wancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();

                Gson gson = new Gson();
                HashMap<String, String> data = new HashMap<>();
                data.put("phone", phonenumber);
                data.put("olduserpw", password0);
                data.put("newuserpw",password1);
                data.put("leixing", "2");
                OkHttpUtils
                        .postString()
                        .url(MainActivity.URL + MainActivity.USERAPI)
                        .content(gson.toJson(data))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                Log.d("LOL", "Dwq");
                                Toast.makeText(xiugaimimaActivity.this, "密码信息有误", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(xiugaimimaActivity.this, "密码修改成功", Toast.LENGTH_LONG).show();
                                editor.clear().commit();
                                Intent intent = new Intent(xiugaimimaActivity.this, dengluActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            }
        });
    }

    public void init(){
        shoujihao= (EditText) findViewById(R.id.zhuce_shoujihao);
        old_password=(EditText)findViewById(R.id.zhuce_mima);
        new_password=(EditText)findViewById(R.id.zhuce_queren);
        wancheng=(RelativeLayout)findViewById(R.id.zhanghao_button);
    }

    public void getData(){
        phonenumber=shoujihao.getText().toString();
        password0=mytool.getMD5Str(old_password.getText().toString());
        password1=mytool.getMD5Str(new_password.getText().toString());
    }

}
