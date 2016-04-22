package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
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
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/20.
 */
public class dianjiangItemActivity extends Activity {

    private TextView name;
    private SimpleDraweeView touxiang;
    private TextView gongzhong;
    private TextView rixin;
    private TextView dengji;
    private RatingBar pingfen;
    private TextView gongsi;
    private TextView gongzuodi;
    private TextView biaoqian;
    private SimpleDraweeView zhengshu1;
    private SimpleDraweeView zhengshu2;
    private TextView ziwojieshao;
    private LinkedTreeMap<String,Object> user_datamap=new LinkedTreeMap<>();
    private Handler mHandler;
    private Map<Double,String> map=new HashMap<>();
    private ImageButton fanhui;


    String gongsi_;
    String gongzuodi_;
    String biaoqian_;
    String ziwojieshao_;
    ArrayList<String> imagelist=new ArrayList<>();
    Map<Integer,SimpleDraweeView> imageMap=new HashMap<>();




    DataFragment dataFragment=DataFragment.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);

        setContentView(R.layout.dianjiangxinxi);
        getDianjiangInformation();
        init();

        name.setText(dataFragment.dianjiangItemBean.name);
        gongzhong.setText(dataFragment.dianjiangItemBean.gongzhong);
        rixin.setText(dataFragment.dianjiangItemBean.price.toString());
        dengji.setText(map.get(dataFragment.dianjiangItemBean.level));
        touxiang.setImageURI(dataFragment.dianjiangItemBean.imageuri);

        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==0x1233){
                    gongsi.setText(gongsi_);
                    gongzuodi.setText(gongzuodi_);
                    biaoqian.setText(biaoqian_);
                    ziwojieshao.setText(ziwojieshao_);
                    for (int count=0;count<imagelist.size();count++){
                        imageMap.get(count).setImageURI(mytool.UriFromSenge(imagelist.get(count)));
                    }
                }
            }
        };

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void init(){
        name= (TextView) findViewById(R.id.dianjiang_name);
        gongzhong=(TextView)findViewById(R.id.dianjiang_gongzhong);
        rixin=(TextView)findViewById(R.id.dianjiang_rixin_num);
        dengji=(TextView)findViewById(R.id.dianjiang_dengji);
        pingfen=(RatingBar)findViewById(R.id.dianjiang_xingji);
        gongsi= (TextView) findViewById(R.id.dianjiang_gongsi);
        gongzuodi= (TextView) findViewById(R.id.dianjiang_gongzuodi);
        biaoqian=(TextView)findViewById(R.id.dianjiang_biaoqian);
        zhengshu1=(SimpleDraweeView)findViewById(R.id.dianjiang_zheng1);
        zhengshu2=(SimpleDraweeView)findViewById(R.id.dianjiang_zheng2);
        ziwojieshao=(TextView)findViewById(R.id.ziwojieshao);
        touxiang=(SimpleDraweeView)findViewById(R.id.dianjiang_touxiang);
        fanhui=(ImageButton)findViewById(R.id.dianjiang_fanhui);
        imageMap.put(0,zhengshu1);
        imageMap.put(1,zhengshu2);

        map.put(0.0,"新人工匠");
        map.put(1.0,"工匠一级");
        map.put(2.0,"工匠二级");
        map.put(3.0,"工匠三级");
        map.put(4.0,"工匠四级");
        map.put(5.0,"工匠五级");
        map.put(6.0,"工匠达人");
    }

    public void getDianjiangInformation(){
        OkHttpUtils
                .get()
                .url(MainActivity.URL + MainActivity.USERAPI)
                .addParams("phone", dataFragment.dianjiangItemBean.phone)
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
                        gongsi_= (String) user_datamap.get("gongsi");
                        gongzuodi_=(String)user_datamap.get("gongzuodi");
                        biaoqian_=(String)user_datamap.get("biaoqian");
                        ziwojieshao_=(String)user_datamap.get("ziwojieshao");
                        imagelist= (ArrayList<String>) user_datamap.get("zhengshu");

                        mHandler.sendEmptyMessage(0x1233);

                    }
                });
    }

}
