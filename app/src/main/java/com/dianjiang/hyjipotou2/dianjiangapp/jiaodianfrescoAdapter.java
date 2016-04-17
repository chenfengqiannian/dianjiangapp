package com.dianjiang.hyjipotou2.dianjiangapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by hyjipotou2 on 16/4/16.
 */
public class jiaodianfrescoAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context context;
    private Uri[] uris;


    public jiaodianfrescoAdapter(Context context,Uri[] uris){
        mInflater=LayoutInflater.from(context);
        this.context=context;
        this.uris=uris;
    }

    @Override
    public int getCount() {
        return uris.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        simpleDraweeView.setImageURI(uris[position]);
        simpleDraweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return simpleDraweeView;
    }

}
