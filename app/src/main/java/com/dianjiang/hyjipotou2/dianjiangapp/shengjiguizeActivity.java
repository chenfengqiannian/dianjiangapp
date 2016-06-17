package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by hyjipotou2 on 16/5/4.
 */
public class shengjiguizeActivity extends Activity {

    ImageButton fanhui1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shengjiguize);

        fanhui1= (ImageButton) findViewById(R.id.fanhui);

        fanhui1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
