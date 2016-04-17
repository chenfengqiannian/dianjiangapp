package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by hyjipotou2 on 16/4/8.
 */
public class xinxixiugaiActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化Fresco
        Fresco.initialize(xinxixiugaiActivity.this);

        setContentView(R.layout.gerenxinxixiugai);
    }
}
