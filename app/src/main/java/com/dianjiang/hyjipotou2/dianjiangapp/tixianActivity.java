package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tixianshenqing);
        init();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getData0();
                OkHttpClient client = new OkHttpClient();
                Gson gson1=new Gson();
                HashMap<String,Object> data1 =new HashMap<>();
                data1.put("tixianshenqing",true);
                data1.put("phone",dengluActivity.phone);
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
                                Toast.makeText(tixianActivity.this,"提现申请发送成功,请耐心等待",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    public void init(){
        fanhui= (ImageButton) findViewById(R.id.fanhui);
        zhanghu=(TextView)findViewById(R.id.zhanghu);
        yue=(TextView)findViewById(R.id.tixianshenqing_num);
        shenqingjine=(TextView)findViewById(R.id.tixianshenqing_price);
        button= (RelativeLayout) findViewById(R.id.tixianshenqing_button);

        zhanghu.setText(dengluActivity.phone);
        DataFragment dataFragment=DataFragment.getInstance();
        yue.setText(dataFragment.user_datamap.get("zhanghuyue").toString());
        shenqingjine.setText(dataFragment.user_datamap.get("zhanghuyue").toString());
    }

    public void getData0(){
        price=shenqingjine.getText().toString();
    }
}
