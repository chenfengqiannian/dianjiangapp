package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Fragment;

import java.io.Serializable;

/**
 * Created by hyjipotou2 on 16/4/17.
 */
public class DataFragment extends Fragment{

    private static DataFragment instance=null;
    public static DataFragment getInstance(){
        if (instance==null){
            synchronized (DataFragment.class){
                if (instance==null){
                    instance=new DataFragment();
                }
            }
        }
     return  instance;
    }



}
