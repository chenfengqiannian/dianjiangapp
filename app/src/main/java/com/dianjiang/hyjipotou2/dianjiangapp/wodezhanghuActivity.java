package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/8.
 */
public class wodezhanghuActivity extends Activity {

    public static final int ON=1;
    public static final int OFF=0;

    private TextView yue;
    private RelativeLayout tixian;
    private RelativeLayout daishoukuan;
    private RelativeLayout jilu;
    private ListView jilulist;
    private int liststate=0;
    private ImageButton fanhui;

    ArrayList<String> shouzhijilulist=new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wodezhanghu);
        init();
        //test();
        setListener();


    }
    public void setListener(){

        tixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wodezhanghuActivity.this, tixianActivity.class);
                startActivity(intent);
            }
        });

        jilu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (liststate == OFF) {
                    jilulist.setVisibility(View.VISIBLE);
                    liststate = ON;
                } else {
                    jilulist.setVisibility(View.INVISIBLE);
                    liststate = OFF;
                }
            }
        });

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void test() {
        DataFragment dataFragment=DataFragment.getInstance();
        dataFragment.user_datamap.get("shouzhijilu");
        ArrayList<String> arrayList;
        arrayList= (ArrayList<String>) dataFragment.user_datamap.get("shouzhijilu");
        int size=arrayList.size();
        if (arrayList!=null) {
            String[] date = new String[]{"2013-13-14 20:30", "2015-12-22 12:00", "2014-11-22 22:22"};
            String[] jilu=arrayList.toArray(new String[size]);
            List<Map<String, Object>> listItems = new ArrayList<>();

            for (int i = 0; i < jilu.length; i++) {
                Map<String, Object> listItem = new HashMap<>();
                listItem.put("reason", jilu[i]);
                listItem.put("date", date[i]);
                listItems.add(listItem);
            }

            SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.shouzhijilu_item, new String[]{"reason", "date"}, new int[]{R.id.shouzhileixing, R.id.shouzhishijian});
            jilulist.setAdapter(simpleAdapter);
        }
    }

    public void init() {

        fanhui=(ImageButton)findViewById(R.id.zhanghu_fanhui);
        yue = (TextView) findViewById(R.id.wodezhanghu_yue);
        tixian = (RelativeLayout) findViewById(R.id.wodezhanghu_tixian);
        daishoukuan = (RelativeLayout) findViewById(R.id.wodezhanghu_daishoukuan);
        jilu = (RelativeLayout) findViewById(R.id.wodezhanghu_shouzhijilu);
        jilulist = (ListView) findViewById(R.id.jilulist);
        daishoukuan.setVisibility(View.GONE);
        jilu.setVisibility(View.GONE);
        DataFragment dataFragment=DataFragment.getInstance();

        yue.setText(dataFragment.user_datamap.get("zhanghuyue").toString());
    }

    public void getHttp(){
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
                        shouzhijilulist= (ArrayList<String>) fragment.user_datamap.get("shouzhijilu");
                    }
                });
    }
}
