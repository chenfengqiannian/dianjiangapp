package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by hyjipotou2 on 16/4/21.
 */
public class tixianActivity extends Activity {

    private ImageButton fanhui;
    private TextView zhanghu;
    private TextView yue;
    private EditText shenqingjine;

    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tixianshenqing);
        init();

    }

    public void init(){
        fanhui= (ImageButton) findViewById(R.id.fanhui);
        zhanghu=(TextView)findViewById(R.id.zhanghu);
        yue=(TextView)findViewById(R.id.tixianshenqing_num);
        shenqingjine=(EditText)findViewById(R.id.tixianshenqing_price);

        zhanghu.setText(dengluActivity.phone);
        DataFragment dataFragment=DataFragment.getInstance();
        yue.setText(dataFragment.user_datamap.get("zhanghuyue").toString());
    }

    public void getData(){
        price=shenqingjine.getText().toString();
    }
}
