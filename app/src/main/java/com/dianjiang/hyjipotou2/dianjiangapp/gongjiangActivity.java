package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

public class gongjiangActivity extends AppCompatActivity implements gongjianglstFragment.OnFragmentInteractionListener,View.OnClickListener{
NoSlideViewPager page;
    TextView title;
    gongchengadapter adapter;
    private RelativeLayout rb1;
    private RelativeLayout rb2;
    private RelativeLayout rb3;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    TextView tabtext1;
    TextView tabtext2;
    TextView tabtext3;
    Resources resources;
    ColorStateList main_text;
    ColorStateList non_color;

    RelativeLayout topbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.gongjiang);

        init();
        adapter=new gongchengadapter(getSupportFragmentManager());
        page.setAdapter(adapter);


        DataFragment dataFragment=DataFragment.getInstance();
        dataFragment.mhandler3=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x4444){
                    finish();
                }

            }
        };



    }



void init()
{

    page=(NoSlideViewPager)findViewById(R.id.nopager);
    rb1=(RelativeLayout)findViewById(R.id.gonjiang_tab1);

    rb2=(RelativeLayout)findViewById(R.id.gongjiang_tab2);
    rb3=(RelativeLayout)findViewById(R.id.gongjiang_tab3);
    image1=(ImageView)findViewById(R.id.img1);
    image2=(ImageView)findViewById(R.id.img2);
    image3=(ImageView)findViewById(R.id.img3);

    rb1.setOnClickListener(this);
    rb2.setOnClickListener(this);
    rb3.setOnClickListener(this);
    tabtext1=(TextView)findViewById(R.id.tv1);
    tabtext2=(TextView)findViewById(R.id.tv2);
    tabtext3=(TextView)findViewById(R.id.tv3);
    resources=(Resources)getApplicationContext().getResources();
    main_text=(ColorStateList)resources.getColorStateList(R.color.main_color);
    non_color=(ColorStateList)resources.getColorStateList(R.color.non_color);

    topbar= (RelativeLayout) findViewById(R.id.gongjiang_top_bar);
    title=(TextView)findViewById(R.id.gongjiang_txt_topbar);


}


    @Override
    public void onFragmentInteraction(String uri) {
        Intent intent=new Intent();
        if(uri.equalsIgnoreCase("shezhi"))
        {
            intent.setClass(getApplicationContext(),shezhiActivity.class);
            startActivity(intent);
        }



        if(uri.equalsIgnoreCase("dianjiangketang"))
        {


            intent.setClass(getApplicationContext(),gongjiangmoreActivity.class);
            intent.putExtra("leixing","ketang");
           startActivity(intent);



        }
        if(uri.equalsIgnoreCase("banggongyou"))
        {

            intent.setClass(getApplicationContext(), zhuceActivity.class);
            startActivity(intent);
        }
        if(uri.equalsIgnoreCase("gerenxinxi"))
        {
            intent.putExtra("leixing","gerenxinxi");
            intent.setClass(getApplicationContext(),gongjiangmoreActivity.class);
            startActivity(intent);

        }
        if (uri.equalsIgnoreCase("wodebanzu")){
            Intent intent1=new Intent(gongjiangActivity.this,wodebanzu.class);
            startActivity(intent1);
        }
        if (uri.equalsIgnoreCase("wodeyeji")){
            Intent intent2=new Intent(gongjiangActivity.this,gongjiangmoreActivity.class);
            intent2.putExtra("leixing","wodeyeji");
            startActivity(intent2);
        }
    }

    @Override
    public void onClick(View v) {

if(v==rb1)
{
    xuanzi(0);
}
if(v==rb2)
{
    xuanzi(1);

}
if(v==rb3)
{
    xuanzi(2);

}






    }

    private void xuanzi(int index)
    {chushihua();
        if(index==0)
        { page.setCurrentItem(0);
            image1.setImageResource(R.drawable.xinrenwu);
            tabtext1.setTextColor(main_text);
            title.setText("新任务");
            topbar.setVisibility(View.VISIBLE);
        }

        if(index==1)
        {
            page.setCurrentItem(1);
            image2.setImageResource(R.drawable.lishirenwu1);
            tabtext2.setTextColor(main_text);
            title.setText("历史任务");
            topbar.setVisibility(View.VISIBLE);
        }
        if(index==2)
        {
            page.setCurrentItem(2);
            image3.setImageResource(R.drawable.gerenzhongxin1);
            tabtext3.setTextColor(main_text);
            topbar.setVisibility(View.GONE);
        }



    }
private void chushihua()
{



    image1.setImageResource(R.drawable.xinrenwu0);
    image2.setImageResource(R.drawable.lishirenwu);
    image3.setImageResource(R.drawable.zhongxin);
    tabtext1.setTextColor(non_color);
    tabtext2.setTextColor(non_color);
    tabtext3.setTextColor(non_color);

}

    public void getNewData(){
        OkHttpUtils
                .get()
                .url(MainActivity.URL + MainActivity.USERAPI)
                .addParams("phone", dengluActivity.phone)
                .build()
                .execute(new Callback() {
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
                        DataFragment fragment = DataFragment.getInstance();
                        fragment.user_datamap = (LinkedTreeMap<String, Object>) response;
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        getNewData();
    }
}
class  gongchengadapter extends FragmentPagerAdapter
{private final int PAGER_COUNT=3;
    gongjianglstFragment myFragment1=null;
    gongjianglstFragment myFragment2=null;
    gongjianglstFragment myFragment3=null;

    public gongchengadapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
        myFragment1=gongjianglstFragment.newInstance("tab1","tab1");
        myFragment2=gongjianglstFragment.newInstance("tab2","tab2");
        myFragment3=gongjianglstFragment.newInstance("tab3","tab3");

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = myFragment1;
                break;
            case MainActivity.PAGE_TWO:
                fragment = myFragment2;
                break;
            case MainActivity.PAGE_THREE:
                fragment = myFragment3;
                break;

        }
        return fragment;
    }


    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}