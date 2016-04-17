package com.dianjiang.hyjipotou2.dianjiangapp;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview.XListView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyjipotou2 on 16/3/12.
 */
public class MyFragment1 extends Fragment implements XListView.IXListViewListener{

    private View viewFragment;
    private XListView xListView=null;
    private RelativeLayout qingkong;
    private dianjiangItemAdapter mAdapter;
    private Uri uri;
    private List<dianjiangItemBean> itemBeanList;
    private CarouselViewPager jiaodianpager;
    private Uri[] uris;
    private Handler mHandler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewFragment=inflater.inflate(R.layout.fragment1,container,false);

        initViews();


        //设置各种监听器
        setListener();


        return viewFragment;
    }

    private void setListener(){
        //设置各种监听器
        qingkong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBeanList.clear();
                Toast.makeText(getActivity(),"消息已清空",Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
            }
        });


        jiaodianpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initViews(){
        jiaodianpager= (CarouselViewPager) viewFragment.findViewById(R.id.jiaodianpager);
        xListView=(XListView) viewFragment.findViewById(R.id.xiaoxi);
        qingkong=(RelativeLayout)viewFragment.findViewById(R.id.qingkong);

        //焦点图轮播
        uris=new Uri[]{Uri.fromFile(new File("/storage/sdcard1/tieba/kinght.jpg")),Uri.fromFile(new File("/storage/sdcard1/tieba/king.jpg")),Uri.fromFile(new File("/storage/sdcard1/tieba/miaoniang.jpg"))};
        PagerAdapter adapter = new SimpleCarouselAdapter(jiaodianpager,uris,getActivity());
        jiaodianpager.setAdapter(adapter);

        //初始化LISTVIEW adapter
        uri=Uri.fromFile(new File("/storage/sdcard1/tieba/kinght.jpg"));
        itemBeanList=new ArrayList<>();
        for (int i=0;i<10;i++){
            itemBeanList.add(0,new dianjiangItemBean(uri,"装哭的骑士"+i,"歌姬"+i,"MR"+i,"1000"+i));
        }
        mAdapter=new dianjiangItemAdapter(this.getActivity(),itemBeanList);
        xListView.setAdapter(mAdapter);

        xListView.setXListViewListener(this);
        mHandler=new Handler();

    }

    private void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int count;  //获取数据个数
                count = 10;

                //数据
                for (int i = 0; i < count; i++) {
                    itemBeanList.add(0, new dianjiangItemBean(uri, "罗艾娜" + i, "歌姬" + i, "MR" + i, "1000" + i));
                }
                mAdapter.notifyDataSetChanged();


                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onResume() {
        super.onResume();
        jiaodianpager.setLifeCycle(CarouselViewPager.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        jiaodianpager.setLifeCycle(CarouselViewPager.PAUSE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        jiaodianpager.setLifeCycle(CarouselViewPager.DESTROY);
    }

    private static class SimpleCarouselAdapter extends CarouselPagerAdapter<CarouselViewPager> {
        private Uri[] viewResIds;
        private Context context;

        public SimpleCarouselAdapter(CarouselViewPager viewPager, Uri[] viewResIds,Context context) {
            super(viewPager);
            this.viewResIds = viewResIds;
            this.context=context;
        }

        @Override
        public int getRealDataCount() {
            return viewResIds != null ? viewResIds.length : 0;
        }

        @Override
        public Object instantiateRealItem(ViewGroup container, int position) {
            Uri resId = viewResIds[position];
            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
            simpleDraweeView.setImageURI(resId);
            container.addView(simpleDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return simpleDraweeView;
        }
    }
}
