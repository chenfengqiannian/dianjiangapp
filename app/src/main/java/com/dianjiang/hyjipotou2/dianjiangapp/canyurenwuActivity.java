package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyjipotou2 on 16/5/4.
 */
public class canyurenwuActivity extends Activity {

    private ImageButton fanhui2;
    private ListView listView;
    private processItemAdapter itemAdapter;
    private List<processItemBean> itemBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canyurenwu);
        init();
        setData();
        itemAdapter=new processItemAdapter(this,itemBeans);
        listView.setAdapter(itemAdapter);
        fanhui2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void init(){
        fanhui2= (ImageButton) findViewById(R.id.fanhui);
        listView= (ListView) findViewById(R.id.renwulist);
    }

    public void setData(){
        DataFragment data=DataFragment.getInstance();
        ArrayList<LinkedTreeMap<String,Object>> gongcheng_set=(ArrayList<LinkedTreeMap<String,Object>>)data.user_datamap.get("gongcheng_set");
        itemBeans=new ArrayList<processItemBean>();

        for(LinkedTreeMap<String,Object> object:gongcheng_set)
        {
                //ArrayList<Map<String,Object>> gongchenglist;
                // DataFragment dataFragment=DataFragment.getInstance();
                // ArrayList<String> gongchengid=new ArrayList<>();
                //gongchenglist= (ArrayList<Map<String, Object>>) dataFragment.user_datamap.get("gongcheng_set");
                ArrayList<String> tupianlist=(ArrayList<String>)object.get("tupian");

                Uri uri;
                String string=new String();
                if (tupianlist.isEmpty()){
                    uri=null;
                }else {
                    string=tupianlist.get(tupianlist.size()-1);
                    uri=mytool.UriFromSenge(string);
                }

                itemBeans.add(0,new processItemBean(uri,(String)object.get("biaoti"),object.get("id").toString(),(String)object.get("miaoshu"),(int)(double)object.get("zhuangtai"),(String)object.get("autotime"),(String)object.get("gongchengjindu")));
        }

    }

}
