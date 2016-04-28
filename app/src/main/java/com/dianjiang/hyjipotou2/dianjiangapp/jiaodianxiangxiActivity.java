package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by hyjipotou2 on 16/4/23.
 */
public class jiaodianxiangxiActivity extends Activity {

    private SimpleDraweeView simpleDraweeView;
    private TextView textView;
    private ImageButton fanhui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);

        setContentView(R.layout.shouyeimg_xiangxi);
        init();
        Uri uri=getIntent().getData();
        String string=getIntent().getStringExtra("text");
        textView.setText(string);
        simpleDraweeView.setImageURI(uri);

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void init(){
        textView= (TextView) findViewById(R.id.xiangxi_text);
        simpleDraweeView= (SimpleDraweeView) findViewById(R.id.xiangxi_img);
        fanhui=(ImageButton)findViewById(R.id.xiangxi_fanhui);
    }


}
