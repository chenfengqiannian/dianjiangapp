package com.dianjiang.hyjipotou2.dianjiangapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/14.
 */
public class dianjiangItemAdapter extends BaseAdapter {

    private List<dianjiangItemBean> mList;
    private LayoutInflater mInflater;
    private Map<Double,String> map=new HashMap<>();


    public dianjiangItemAdapter(Context context,List<dianjiangItemBean> mList){
        map.put(0.0,"新人工匠");
        map.put(1.0,"工匠一级");
        map.put(2.0,"工匠二级");
        map.put(3.0,"工匠三级");
        map.put(4.0,"工匠四级");
        map.put(5.0,"工匠五级");
        map.put(6.0,"工匠达人");
        this.mList=mList;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.dianjiangtai_item, null);
            viewHolder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.dianjiangitem_touxiang);
            viewHolder.name1=(TextView)convertView.findViewById(R.id.dianjiangitem_name);
            viewHolder.gongzhong=(TextView)convertView.findViewById(R.id.dianjiangitem_gongzhong);
            viewHolder.level=(TextView)convertView.findViewById(R.id.dianjiangitem_level);
            viewHolder.price=(TextView)convertView.findViewById(R.id.dianjiangitem_price);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        dianjiangItemBean bean=mList.get(position);
        viewHolder.simpleDraweeView.setImageURI(bean.imageuri);
        viewHolder.name1.setText(bean.name);
        if (bean.gongzhong.equalsIgnoreCase("")){
            viewHolder.gongzhong.setText("未设置工种");
        }else {
            viewHolder.gongzhong.setText(bean.gongzhong);
        }
        viewHolder.price.setText(bean.price.toString());
        viewHolder.level.setText(map.get(bean.level));
        return convertView;
    }

    class ViewHolder{
        public SimpleDraweeView simpleDraweeView;
        public TextView name1;
        public TextView gongzhong;
        public TextView level;
        public TextView price;
    }
}
