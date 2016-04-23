package com.dianjiang.hyjipotou2.dianjiangapp;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview.XListView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyjipotou2 on 16/3/12.
 */
public class MyFragment1 extends Fragment implements XListView.IXListViewListener{

    private View viewFragment;
    private XListView xListView=null;
    private RelativeLayout qingkong;
    private CarouselViewPager jiaodianpager;
    private Uri[] uris;
    private ArrayList<String> arrayList;
    private Handler mHandler;
    private ArrayAdapter mAdapter;
    private String[] strings;

    public DataFragment dataFragment=DataFragment.getInstance();
    private LinkedTreeMap linkedTreeMap;
    private ArrayList<LinkedTreeMap> linkedTreeMapArrayList=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewFragment=inflater.inflate(R.layout.fragment1,container,false);

        initViews();


        //设置各种监听器
        setListener();
        initdata();

        return viewFragment;
    }

    //获取数据
    public void initdata(){
        GetHttp();
    }

    private void setListener(){
        //设置各种监听器
        qingkong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                Toast.makeText(getActivity(),"消息已清空",Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
            }
        });

        //首页轮播图跳转
        jiaodianpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataFragment dataFragment=DataFragment.getInstance();
                int count=jiaodianpager.getCurrentItem();


            }
        });
    }

    private void initViews(){
        arrayList=new ArrayList<>();
        jiaodianpager= (CarouselViewPager) viewFragment.findViewById(R.id.jiaodianpager);
        xListView=(XListView) viewFragment.findViewById(R.id.xiaoxi);
        qingkong=(RelativeLayout)viewFragment.findViewById(R.id.qingkong);

        //焦点图轮播

        uris=new Uri[]{Uri.fromFile(new File("/storage/sdcard1/tieba/kinght.jpg")),Uri.fromFile(new File("/storage/sdcard1/tieba/king.jpg")),Uri.fromFile(new File("/storage/sdcard1/tieba/miaoniang.jpg"))};
        PagerAdapter adapter = new SimpleCarouselAdapter(jiaodianpager,uris,getActivity());
        jiaodianpager.setAdapter(adapter);

        //初始化LISTVIEW adapter

        mAdapter=new ArrayAdapter(this.getActivity(),R.layout.testitem,R.id.testText,arrayList);
        xListView.setAdapter(mAdapter);

        xListView.setXListViewListener(this);
        mHandler=new Handler();
        GetHttp();
    }

    private void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {

       GetHttp();

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

    public void GetHttp(){
        OkHttpUtils
                .get()
                .url(MainActivity.URL + MainActivity.SHEZHIAPI)
                .build()
                .execute(new mCallBack<Object>(this) {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {

                        Log.i("LOL", "response");
                        String string = response.body().string();

                        Object ps = new Gson().fromJson(string, new TypeToken<Object>() {
                        }.getType());
                        return ps;
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        arrayList.clear();
                        linkedTreeMap = (LinkedTreeMap) response;
                        ArrayList<String> arr1=new ArrayList<String>();
                        arr1= (ArrayList<String>) linkedTreeMap.get("xiaoxi");
                        strings=arr1.get(0).split(",");

                        for (int i=0;i<strings.length;i++){
                            arrayList.add(strings[i]);
                        }
                        mAdapter.notifyDataSetChanged();

                        onLoad();
                    }
                });
    }

    abstract class mCallBack<T> extends Callback<T> {
        MyFragment1 mfragment;
        public mCallBack(MyFragment1 mfragment){
            this.mfragment=mfragment;
        }
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
    public void getImgHttp(){
        OkHttpUtils
                .get()
                .url(MainActivity.URL + MainActivity.SHEZHIAPI)
                .build()
                .execute(new mCallBack<Object>(this) {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {

                        Log.i("LOL", "response");
                        String string = response.body().string();

                        Object ps = new Gson().fromJson(string, new TypeToken<Object>() {
                        }.getType());
                        return ps;
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        linkedTreeMap = (LinkedTreeMap) response;
                        ArrayList<String> arrayList1;
                        ArrayList<String> arrayList2;
                        ArrayList<String> arrayList3;
                        arrayList1= (ArrayList<String>) linkedTreeMap.get("tupian1");
                        arrayList2= (ArrayList<String>) linkedTreeMap.get("tupian2");
                        arrayList3= (ArrayList<String>) linkedTreeMap.get("tupian3");

                    }
                });
    }
}
