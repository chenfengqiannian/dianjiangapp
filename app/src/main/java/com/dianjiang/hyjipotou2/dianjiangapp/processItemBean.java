package com.dianjiang.hyjipotou2.dianjiangapp;

import android.net.Uri;

/**
 * Created by hyjipotou2 on 16/4/19.
 */
public class processItemBean {
    public Uri imageuri;
    public String processname;
    public String processnumber;
    public String miaoshu;
    public int shenhe_state;
    public String date;
    public String processjindu;

    public processItemBean(Uri imageuri,String processname,String processnumber,String miaoshu,int shenhe_state,String date,String processjindu){
        this.imageuri=imageuri;
        this.date=date;
        this.processname=processname;
        this.miaoshu=miaoshu;
        this.shenhe_state=shenhe_state;
        this.processnumber=processnumber;
        this.processjindu=processjindu;
    }

}
