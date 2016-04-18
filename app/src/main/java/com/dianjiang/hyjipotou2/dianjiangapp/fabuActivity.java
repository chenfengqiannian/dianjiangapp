package com.dianjiang.hyjipotou2.dianjiangapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/7.
 */
public class fabuActivity extends FragmentActivity implements fabuFragment.OnFragmentInteractionListener{

    private fabuFragment gongchengxinxi;
    private fabuFragment jiebaoyaoqiu;
    private fabuFragment yulanfabu;

    private RelativeLayout xiayibu;
    private FragmentManager fragmentManager;
    private int state=1;

    //DATA
    private Map<String,Object> datamap=new HashMap<>();
    private List<String> list;

    private TextView top_bar;
    private TextView button_text;

    private final int FIRST=1;
    private final int SECOND=2;
    private final int THIRD=3;

    private final String PAGE1="工程信息";
    private final String PAGE2="接包要求";
    private final String PAGE3="预览发布";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fabugongcheng);
        init();
        fragmentinit();
        setNextListener();
    }

    public void init(){
        xiayibu= (RelativeLayout) findViewById(R.id.fabu_xiayibu);
        top_bar=(TextView)findViewById(R.id.fabu2_top_text);
        button_text=(TextView)findViewById(R.id.button_text);

        gongchengxinxi=fabuFragment.newInstance(PAGE1);
        jiebaoyaoqiu=fabuFragment.newInstance(PAGE2);
        yulanfabu=fabuFragment.newInstance(PAGE3);
    }
    public void fragmentinit(){

        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fabu_fragment,gongchengxinxi).commit();
    }
    public void setNextListener(){
        xiayibu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state==FIRST){
                    top_bar.setText("接包要求");
                    state=SECOND;
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.fabu_fragment,jiebaoyaoqiu).commit();
                } else if (state==SECOND){
                    top_bar.setText("预览发布");
                    button_text.setText("确认发布");
                    state=THIRD;
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.fabu_fragment,yulanfabu).commit();
                }
                else if(state==THIRD){

                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(Map<String,Object> map) {
        if (state==SECOND){
            list=new ArrayList<>();
            list.add("biaoti");
            list.add("miaoshu");
            list.add("xiangxidizhi");
            list.add("tupian");
            for (int count=0;count<list.size();count++){
                datamap.put(list.get(count),map.get(list.get(count)));
            }
        }
        if (state==THIRD){
            list=new ArrayList<>();
            list.add("gongzhong");
            list.add("yaoqiu");
            list.add("choulao");
            list.add("beizhu");
            list.add("fukuan");
            for (int count=0;count<list.size();count++){
                datamap.put(list.get(count),map.get(list.get(count)));
            }
        }
    }
}
