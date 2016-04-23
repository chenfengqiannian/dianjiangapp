package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Fragment;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/17.
 */
public class DataFragment extends Fragment{

    public LinkedTreeMap<String,Object> user_datamap;
    public LinkedTreeMap<String,Object> process_datamap;
    public List<dianjiangItemBean> dianjiangItemBeans=new ArrayList<dianjiangItemBean>();
    public Map<String,Object> fabu_datamap=new HashMap<>();
    public dianjiangItemBean dianjiangItemBean;
    public Uri shouyeImgUri;

    public Handler mhandler1=new Handler();
    public Handler mhandler2=new Handler();


    public static final String URL="http://192.168.191.1:8000";
    public static final String USERAPI="/userapi/";

    private static DataFragment instance=null;
    public static DataFragment getInstance(){
        if (instance==null){
            synchronized (DataFragment.class){
                if (instance==null){
                    instance=new DataFragment();
                }
            }
        }
     return  instance;
    }

    //筛选相关
    public String gongzhong;
    public String diqu;
    public String biaoqian;

    public void getData(){
        OkHttpUtils
                .get()
                .url(URL + USERAPI)
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
                        user_datamap = (LinkedTreeMap<String, Object>) response;


                    }
                });
    }
}
