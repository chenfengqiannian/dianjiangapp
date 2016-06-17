package com.dianjiang.hyjipotou2.dianjiangapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by hyjipotou2 on 16/4/19.
 */
public class processItemAdapter extends BaseAdapter {
    private List<processItemBean> mList;
    private LayoutInflater mInflater;


    public processItemAdapter(Context context,List<processItemBean> mList){
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
            convertView = mInflater.inflate(R.layout.gongcheng_item, null);
            viewHolder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.tupian);
            viewHolder.name1=(TextView)convertView.findViewById(R.id.name);
            viewHolder.miaoshu=(TextView)convertView.findViewById(R.id.miaoshu);
            viewHolder.process_number=(TextView)convertView.findViewById(R.id.number);
            viewHolder.process_date=(TextView)convertView.findViewById(R.id.date);
            viewHolder.shenhe_text=(TextView)convertView.findViewById(R.id.shenhe_text);
            viewHolder.shenhe_img=(ImageView)convertView.findViewById(R.id.shenhe_img);
            viewHolder.process_jindu=(TextView)convertView.findViewById(R.id.processjindu);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        processItemBean bean=mList.get(position);
        viewHolder.simpleDraweeView.setImageURI(bean.imageuri);
        viewHolder.name1.setText(bean.processname);
        viewHolder.miaoshu.setText(bean.miaoshu);
        viewHolder.process_number.setText((int)Double.parseDouble(bean.processnumber)+"");
        viewHolder.process_date.setText(bean.date);

        //判断审核状态
        if (bean.shenhe_state==1){
            viewHolder.shenhe_text.setText("审核成功");
            viewHolder.shenhe_img.setImageResource(R.drawable.shenhechenggong);
        }
        else if (bean.shenhe_state==0){
            viewHolder.shenhe_text.setText("审核中");
            viewHolder.shenhe_img.setImageResource(R.drawable.shenhezhong);
        }
        else if (bean.shenhe_state==-1){
            viewHolder.shenhe_text.setText("审核失败");
            viewHolder.shenhe_img.setImageResource(R.drawable.shenheshibai);
        }else if (bean.shenhe_state==-3){
            viewHolder.shenhe_text.setText("");
        }

        return convertView;
    }

    class ViewHolder{
        public SimpleDraweeView simpleDraweeView;
        public TextView name1;
        public TextView miaoshu;
        public TextView process_number;
        public TextView process_date;
        public TextView shenhe_text;
        public TextView process_jindu;
        public ImageView shenhe_img;
    }
}
