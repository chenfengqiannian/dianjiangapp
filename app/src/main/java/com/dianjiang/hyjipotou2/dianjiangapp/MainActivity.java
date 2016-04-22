package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.okhttp.MediaType;

public class MainActivity extends FragmentActivity implements MyFragment3.OnFragmentInteractionListener,MyFragment2.OnFragmentInteractionListener1,myfactoryFragment.OnFragmentInteractionListener{

    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    private TextView top_bar_text;
    private RelativeLayout rb1;
    private RelativeLayout rb2;
    private RelativeLayout rb3;
    private RelativeLayout rb4;
    private NoSlideViewPager pager;
    private MyFragmentPagerAdapter myAdapter;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView sousuo;
    private Button fabu;
    private RelativeLayout topbar;
    private RelativeLayout gpsquyu;
    private DrawerLayout right_menu;
    private RelativeLayout right_content;
    private ImageButton option_fanhui;
    private Button option_queren;

    //Right Menu
    private RelativeLayout gongzhong;
    private RelativeLayout diqu;
    private String shaixuantext;
    private EditText biaoqian;
    private TextView gongzhongtext;
    private TextView diqutext;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String URL="http://192.168.191.1:8000";
    public static final String USERAPI="/userapi/";
    public static final String IMAGEAPI="/imageupapi/";
    public static final String PROCESSAPI="/gongchengapi/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化Fresco
        Fresco.initialize(MainActivity.this);

        setContentView(R.layout.shouye_activity);


        //实例化
        top_bar_text=(TextView)findViewById(R.id.txt_topbar);

        rb1=(RelativeLayout)findViewById(R.id.tab1);
        rb2=(RelativeLayout)findViewById(R.id.tab2);
        rb3=(RelativeLayout)findViewById(R.id.tab3);
        rb4=(RelativeLayout)findViewById(R.id.tab4);

        textView1=(TextView)findViewById(R.id.tv1);
        textView2=(TextView)findViewById(R.id.tv2);
        textView3=(TextView)findViewById(R.id.tv3);
        textView4=(TextView)findViewById(R.id.tv4);

        imageView1=(ImageView)findViewById(R.id.img1);
        imageView2=(ImageView)findViewById(R.id.img2);
        imageView3=(ImageView)findViewById(R.id.img3);
        imageView4=(ImageView)findViewById(R.id.img4);

        RightMenu_init();

        gpsquyu= (RelativeLayout) findViewById(R.id.gpsquyu);
        sousuo=(ImageView)findViewById(R.id.sousuo);
        fabu=(Button)findViewById(R.id.fabu);
        topbar=(RelativeLayout)findViewById(R.id.top_bar);

        pager=(NoSlideViewPager)findViewById(R.id.pager);
        right_menu=(DrawerLayout)findViewById(R.id.shaixuanlayout);
        right_content=(RelativeLayout)findViewById(R.id.right_content);
        option_fanhui= (ImageButton) findViewById(R.id.option_fanhui);
        option_queren= (Button) findViewById(R.id.option_queren);

        DataFragment fragment=DataFragment.getInstance();
        //接收消息
        fragment.mhandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x2333){
                    right_menu.openDrawer(right_content);
                }
            }
        };
        option_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right_menu.closeDrawer(right_content);
            }
        });

        //确认筛选
        option_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,fabuActivity.class);
                startActivity(intent);
            }
        });



        //获取图片
        //获取颜色
        Resources resources=(Resources)getBaseContext().getResources();
        final ColorStateList main_color=(ColorStateList)resources.getColorStateList(R.color.main_color);
        final ColorStateList non_color=(ColorStateList)resources.getColorStateList(R.color.non_color);


        myAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(myAdapter);
        pager.setCurrentItem(0);

        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //底部导航栏设置监听器,进行页面跳转
        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(PAGE_FOUR);
                //字体颜色设置
                textView1.setTextColor(non_color);
                textView2.setTextColor(non_color);
                textView3.setTextColor(non_color);
                textView4.setTextColor(main_color);

                //图像颜色更换
                imageView1.setImageResource(R.drawable.tab_1_0);
                imageView2.setImageResource(R.drawable.tab_2_0);
                imageView3.setImageResource(R.drawable.tab_3_0);
                imageView4.setImageResource(R.drawable.tab_4_1);

                gpsquyu.setVisibility(View.GONE);
                topbar.setVisibility(View.GONE);

            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(PAGE_TWO);
                //字体颜色设置
                textView1.setTextColor(non_color);
                textView2.setTextColor(main_color);
                textView3.setTextColor(non_color);
                textView4.setTextColor(non_color);

                //图像颜色更换
                imageView1.setImageResource(R.drawable.tab_1_0);
                imageView2.setImageResource(R.drawable.tab_2_1);
                imageView3.setImageResource(R.drawable.tab_3_0);
                imageView4.setImageResource(R.drawable.tab_4_0);

                gpsquyu.setVisibility(View.VISIBLE);
                topbar.setVisibility(View.VISIBLE);
                fabu.setVisibility(View.INVISIBLE);
                sousuo.setVisibility(View.VISIBLE);

            }
        });
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(PAGE_THREE);
                //字体颜色设置
                textView1.setTextColor(non_color);
                textView2.setTextColor(non_color);
                textView3.setTextColor(main_color);
                textView4.setTextColor(non_color);

                //图像颜色更换
                imageView1.setImageResource(R.drawable.tab_1_0);
                imageView2.setImageResource(R.drawable.tab_2_0);
                imageView3.setImageResource(R.drawable.tab_3_1);
                imageView4.setImageResource(R.drawable.tab_4_0);

                gpsquyu.setVisibility(View.GONE);
                topbar.setVisibility(View.VISIBLE);
                fabu.setVisibility(View.VISIBLE);
                sousuo.setVisibility(View.INVISIBLE);

            }
        });
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(PAGE_ONE);
                //字体颜色设置
                textView1.setTextColor(main_color);
                textView2.setTextColor(non_color);
                textView3.setTextColor(non_color);
                textView4.setTextColor(non_color);

                //图像颜色更换
                imageView1.setImageResource(R.drawable.tab_1_1);
                imageView2.setImageResource(R.drawable.tab_2_0);
                imageView3.setImageResource(R.drawable.tab_3_0);
                imageView4.setImageResource(R.drawable.tab_4_0);

                gpsquyu.setVisibility(View.VISIBLE);
                topbar.setVisibility(View.VISIBLE);
                fabu.setVisibility(View.INVISIBLE);
                sousuo.setVisibility(View.VISIBLE);

            }
        });

    }

    public void RightMenu_init(){
        biaoqian=(EditText)findViewById(R.id.biaoqiantext);
        gongzhong=(RelativeLayout)findViewById(R.id.gongzhong);
        diqu=(RelativeLayout)findViewById(R.id.diqu);
        gongzhongtext=(TextView)findViewById(R.id.gongzhong_text);
        diqutext=(TextView)findViewById(R.id.diqutext);
    }

    public void setRightMenuListener(){

    }

    ////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onFragmentInteraction1(View tview) {

    }

    @Override
    public void yifabugongcheng(View tview) {
    }

    //myfactoryFragment
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
