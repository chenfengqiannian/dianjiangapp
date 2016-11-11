package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/24.
 */
public class xiangguangongjiangActivity extends Activity {

    private ImageButton fanhui;
    private ListView listView;
    private dianjiangItemBean bean;
    private dianjiangItemAdapter mAdapter;
    private ArrayList<dianjiangItemBean> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);

        setContentView(R.layout.xiangguangongjiang);
        init();
    }

    public void init(){
        fanhui= (ImageButton) findViewById(R.id.gongjiang_fanhui);
        listView= (ListView) findViewById(R.id.xiangguanlist);
        getHttp();
        mAdapter=new dianjiangItemAdapter(this,arrayList);
        listView.setAdapter(mAdapter);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getHttp(){
        DataFragment dataFragment=DataFragment.getInstance();
        ArrayList<String> gongjiangphone;
        for (LinkedTreeMap<String,Object> nidaye:(ArrayList<LinkedTreeMap<String,Object>>)dataFragment.user_datamap.get("gongcheng_set")){
            gongjiangphone = (ArrayList<String>) nidaye.get("yaoqing");

            if (gongjiangphone!=null) {
                for (int i = 0; i < gongjiangphone.size(); i++) {
                    for (int b = 0; b < dataFragment.xiangguanItemBeans.size(); b++) {
                        if (gongjiangphone.get(i) == dataFragment.xiangguanItemBeans.get(b).phone) {
                            arrayList.add(0, dataFragment.xiangguanItemBeans.get(b));
                        }
                    }
                }
            }

        }
    }
}
