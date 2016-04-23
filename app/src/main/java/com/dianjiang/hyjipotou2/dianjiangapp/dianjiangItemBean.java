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
    public Double level;
    public Double price;
    public String phone;
    public String biaoqian;

    public dianjiangItemBean(Uri imageuri,String name,String gongzhong,Double level,Double price,String phone){
        this.imageuri=imageuri;
        this.name=name;
        this.gongzhong=gongzhong;
        this.level=level;
        this.price=price;
        this.phone=phone;
    }
}
