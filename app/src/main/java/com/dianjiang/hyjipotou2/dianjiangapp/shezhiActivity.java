package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by hyjipotou2 on 16/4/17.
 */
public class shezhiActivity extends Activity {

    private RelativeLayout xiugaimima;
    private LinearLayout dingweikaiguan;
    private Button off;
    private Button on;
    private int state=0;
    private RelativeLayout guanyu;

    public static final int OFF=0;
    public static final int ON=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi);
        init();
        setListener();

    }
    public void init(){
        xiugaimima=(RelativeLayout)findViewById(R.id.shezhi_xiugaimima);
        dingweikaiguan=(LinearLayout)findViewById(R.id.dingweitonggle);
        off= (Button) findViewById(R.id.off);
        on=(Button)findViewById(R.id.on);
        guanyu= (RelativeLayout) findViewById(R.id.shezhi_guanyu);

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
                state=ON;
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                on.setBackgroundColor(Color.parseColor("#bebebe"));
                off.setBackgroundColor(Color.parseColor("#dc7803"));
                on.setText("");
                off.setText("OFF");
                state=OFF;
            }
        });

        guanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(shezhiActivity.this,guanyuActivity.class);
                startActivity(intent);
            }
        });
    }
}
