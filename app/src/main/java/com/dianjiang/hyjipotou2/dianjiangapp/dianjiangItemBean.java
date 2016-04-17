package com.dianjiang.hyjipotou2.dianjiangapp;

import android.net.Uri;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by hyjipotou2 on 16/4/14.
 */
public class dianjiangItemBean {
    public Uri imageuri;
    public String name;
    public String gongzhong;
    public String level;
    public String price;

    public dianjiangItemBean(Uri imageuri,String name,String gongzhong,String level,String price){
        this.imageuri=imageuri;
        this.name=name;
        this.gongzhong=gongzhong;
        this.level=level;
        this.price=price;
    }
}
