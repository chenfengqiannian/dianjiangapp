package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyjipotou2 on 16/5/5.
 */
public class zhaobiaoActivity extends Activity {

    private RelativeLayout wancheng;
    private ListView listView;
    private dianjiangItemAdapter mAdapter;
    private List<dianjiangItemBean> itemBeans=new ArrayList<>();
    private DataFragment dataFragment=DataFragment.getInstance();
    private ArrayList<String> phonearr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);

        setContentView(R.layout.zhaobiaogongjiang);
        init();
        getinitData();

        mAdapter=new dianjiangItemAdapter(this,itemBeans);
        listView.setAdapter(mAdapter);

        wancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //post请求
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataFragment.dianjiangItemBean=itemBeans.get(position-1);
                Intent intent=new Intent(zhaobiaoActivity.this,dianjiangItemActivity.class);
                DataFragment.zhaobiaostate=1;
                startActivity(intent);

            }
        });
    }

    public void init(){
        wancheng= (RelativeLayout) findViewById(R.id.fabu2_queren);
        listView= (ListView) findViewById(R.id.zhaobiaolist);
        wancheng= (RelativeLayout) findViewById(R.id.fabu2_queren);
    }

    public void getinitData(){
        ArrayList<LinkedTreeMap<String,Object>> gongcheng= (ArrayList<LinkedTreeMap<String, Object>>) dataFragment.user_datamap.get("gongchengset");
        if (gongcheng!=null) {
            for (int i = 0; i < gongcheng.size(); i++) {
                if (gongcheng.get(i).get("id") == dataFragment.mprocessItemBean.processnumber) {
                    phonearr = (ArrayList<String>) gongcheng.get(i).get("yaoqing");
                }
            }
            if (phonearr!=null) {
                for (int i = 0; i < phonearr.size(); i++) {
                    for (int b = 0; b < dataFragment.zhaobiaoItembeans.size(); b++) {
                        if (phonearr.get(i).equalsIgnoreCase(dataFragment.zhaobiaoItembeans.get(b).phone)) {
                            itemBeans.add(0, dataFragment.zhaobiaoItembeans.get(b));
                        }
                    }
                }
            }
        }

    }

}
