package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


/**
 * Created by hyjipotou2 on 16/4/15.
 */
public class StartActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(StartActivity.this);

        setContentView(R.layout.start_item);


        SimpleDraweeView simpleDraweeView= (SimpleDraweeView) findViewById(R.id.start_view);

        Uri uri=Uri.fromFile(new File("/storage/sdcard1/tieba/queen.jpg"));
        simpleDraweeView.setImageURI(uri);

        Start();

    }
    public void Start() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                    }
                Intent intent = new Intent();
                intent.setClass(StartActivity.this,dengluActivity.class);
                startActivity(intent);
                finish();
                }
            }.start();
        }
}
