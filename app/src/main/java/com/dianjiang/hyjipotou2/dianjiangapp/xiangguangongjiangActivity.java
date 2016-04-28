package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
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
        setContentView(R.layout.xiangguangongjiang);

    }

    public void init(){
        fanhui= (ImageButton) findViewById(R.id.gongjiang_fanhui);
        listView= (ListView) findViewById(R.id.xiangguanlist);

        mAdapter=new dianjiangItemAdapter(this,arrayList);
        listView.setAdapter(mAdapter);
    }

    public void getHttp(){
        ArrayList<Map<String,Object>> gongchenglist;
        DataFragment dataFragment=DataFragment.getInstance();
        ArrayList<String> gongchengid=new ArrayList<>();
        gongchenglist= (ArrayList<Map<String, Object>>) dataFragment.user_datamap.get("gongcheng_set");
        for (int i=0;i<gongchenglist.size();i++){
            gongchengid.add((String)( gongchenglist.get(i).get("id")));
        }

    }
}
