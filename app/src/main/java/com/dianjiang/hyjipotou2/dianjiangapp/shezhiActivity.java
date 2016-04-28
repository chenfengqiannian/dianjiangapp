package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.okhttp.Response;

import java.util.ArrayList;

/**
 * Created by hyjipotou2 on 16/4/17.
 */
public class shezhiActivity extends Activity {

    private RelativeLayout xiugaimima;
    private LinearLayout dingweikaiguan;
    private Button off;
    private Button on;
    private int state=1;
    private RelativeLayout guanyu;
    private RelativeLayout tuichu;
    private ImageButton fanhui;
    private Boolean gps_state;
    private String dianhua;

    public static final Boolean OFF=false;
    public static final Boolean ON=true;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi);

        sharedPreferences=getSharedPreferences("shezhi",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        init();
        setListener();

    }
    public void init(){
        xiugaimima=(RelativeLayout)findViewById(R.id.shezhi_xiugaimima);
        dingweikaiguan=(LinearLayout)findViewById(R.id.dingweitonggle);
        off= (Button) findViewById(R.id.off);
        on=(Button)findViewById(R.id.on);
        guanyu= (RelativeLayout) findViewById(R.id.shezhi_guanyu);
        tuichu= (RelativeLayout) findViewById(R.id.tuichu);
        fanhui=(ImageButton)findViewById(R.id.shezhi_fanhui);
        gps_state=sharedPreferences.getBoolean("gps",true);
        if (gps_state==true){
            on.setBackgroundColor(Color.parseColor("#0364dc"));
            off.setBackgroundColor(Color.parseColor("#bebebe"));
            off.setText("");
            on.setText("ON");
        }else {
            on.setBackgroundColor(Color.parseColor("#bebebe"));
            off.setBackgroundColor(Color.parseColor("#dc7803"));
            on.setText("");
            off.setText("OFF");
        }
    }

    public void setListener(){

        xiugaimima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(shezhiActivity.this,xiugaimimaActivity.class);
                startActivity(intent);
            }
        });

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                on.setBackgroundColor(Color.parseColor("#0364dc"));
                off.setBackgroundColor(Color.parseColor("#bebebe"));
                off.setText("");
                on.setText("ON");
                gps_state=ON;

                editor.putBoolean("gps",true);
                editor.commit();

            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                on.setBackgroundColor(Color.parseColor("#bebebe"));
                off.setBackgroundColor(Color.parseColor("#dc7803"));
                on.setText("");
                off.setText("OFF");
                gps_state=OFF;

                editor.putBoolean("gps",false);
                editor.commit();
            }
        });

        guanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(shezhiActivity.this,guanyuActivity.class);
                startActivity(intent);
            }
        });

        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataFragment dataFragment=DataFragment.getInstance();
                dataFragment.mhandler1.sendEmptyMessage(0x3333);
                dataFragment.mhandler3.sendEmptyMessage(0x4444);
                editor.clear().commit();
                Intent intent=new Intent(shezhiActivity.this,dengluActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
