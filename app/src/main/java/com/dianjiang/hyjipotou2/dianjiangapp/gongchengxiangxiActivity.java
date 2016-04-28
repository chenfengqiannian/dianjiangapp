package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/24.
 */
public class gongchengxiangxiActivity extends Activity {

    private TextView number;
    private TextView name;
    private TextView miaoshu;
    private TextView dizhi;
    private TextView gongzhong;
    private TextView yaoqiu;
    private TextView shichang;
    private TextView choulao;
    private TextView beizhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gongcheng_xiangxi);

        init();

        setData();
    }

    public void init(){
        number= (TextView) findViewById(R.id.fabu3_gongchenghao);
        name=(TextView)findViewById(R.id.fabu3_gongchengming);
        miaoshu= (TextView) findViewById(R.id.fabu3_miaoshu);
        dizhi=(TextView)findViewById(R.id.fabu3_dizhi);
        gongzhong= (TextView) findViewById(R.id.fabu3_gongzhong);
        yaoqiu=(TextView)findViewById(R.id.fabu3_yaoqiu);
        shichang=(TextView)findViewById(R.id.fabu3_shichang);
        choulao=(TextView)findViewById(R.id.fabu3_choulao);
        beizhu=(TextView)findViewById(R.id.fabu3_beizhu);
    }

    public void setData(){
        DataFragment dataFragment=DataFragment.getInstance();
        name.setText(dataFragment.mprocessItemBean.processname);
        number.setText(dataFragment.mprocessItemBean.processnumber);
        miaoshu.setText(dataFragment.mprocessItemBean.miaoshu);

        ArrayList<LinkedTreeMap<String,Object>> gongcheng_set=(ArrayList<LinkedTreeMap<String,Object>>)dataFragment.user_datamap.get("gongcheng_set");
        for (int i=0;i<gongcheng_set.size();i++){
            LinkedTreeMap<String,Object> map;
            map=gongcheng_set.get(i);
            if (map.get("id").toString().equals(dataFragment.mprocessItemBean.processnumber)){
                dizhi.setText(map.get("xiangxidizhi").toString());
                gongzhong.setText(map.get("gongzhong").toString());
                yaoqiu.setText(map.get("yaoqiu").toString());
                shichang.setText(map.get("kaishitime").toString()+"至"+map.get("jieshutime").toString());
                choulao.setText(map.get("xinchou").toString());
                beizhu.setText(map.get("beizhu").toString());
            }
        }
    }

    /*public void getProcess(){
        DataFragment dataFragment=DataFragment.getInstance();
        OkHttpUtils
                .get()
                .url(MainActivity.URL+MainActivity.PROCESSAPI)
                .addParams("id",dataFragment.mprocessItemBean.processnumber)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
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

                    }
                });
    }*/
}
