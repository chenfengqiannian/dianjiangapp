package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.internal.LinkedTreeMap;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/25.
 */
public class wodebanzu extends Activity {

    private ListView listView;
    private ImageButton fanhui;
    private dianjiangItemAdapter itemAdapter;

    private dianjiangItemBean bean;
    private ArrayList<dianjiangItemBean> dianjiangItemBeans=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wodebanzu);

        Fresco.initialize(this);

        init();
        getMessage();

        itemAdapter=new dianjiangItemAdapter(this,dianjiangItemBeans);
        listView.setAdapter(itemAdapter);


    }

    public void init(){
        listView= (ListView) findViewById(R.id.banzulist);
        fanhui= (ImageButton) findViewById(R.id.fanhui);
    }

    public void getMessage(){
        DataFragment dataFragment=DataFragment.getInstance();
        Uri uri=null;
        ArrayList<LinkedTreeMap<String,Object>> arr1;
        ArrayList<String> tou=new ArrayList<>();
        ArrayList<LinkedTreeMap<String,Object>> arrayList= (ArrayList<LinkedTreeMap<String, Object>>) dataFragment.user_datamap.get("gongcheng_set");
        for (int i=0;i<arrayList.size();i++){
            int double1=(int) ((double) arrayList.get(i).get("zhuangtai"));
            if (double1>=2 && double1<=4){
                arr1=(ArrayList<LinkedTreeMap<String,Object>>)arrayList.get(i).get("gongcengdj");
                if (arr1!=null){
                for (int o=0;o<arr1.size();o++){
                    //arr1.get(o).get("phone")
                    tou= (ArrayList<String>) arr1.get(o).get("touxiang");
                    if (tou!=null){
                        uri=mytool.UriFromSenge(tou.get(tou.size()-1));
                    }

                    String name=arr1.get(i).get("xingming").toString();
                    String gongzhong=arr1.get(i).get("gongzhong").toString();
                    Double dengji= (Double) arr1.get(i).get("dengji");
                    Double rixin=(Double)arr1.get(i).get("rixin");
                    String phone=(String)arr1.get(i).get("phone");

                    bean=new dianjiangItemBean(uri,name,gongzhong,dengji,rixin,phone);

                    dianjiangItemBeans.add(bean);

                }}
            }
        }


    }
}
