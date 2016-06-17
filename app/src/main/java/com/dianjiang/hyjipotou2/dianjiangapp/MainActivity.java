package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    private TextView sheng;
    private TextView shi;

    private String sheng_;
    private String shi_;

    //Right Menu
    //private RelativeLayout gongzhong;
    private RelativeLayout diqu;
    private String shaixuantext;
    private EditText biaoqian;
    //private TextView gongzhongtext;
    private TextView diqutext;
    private Button qiehuan;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String URL="http://120.27.30.221:8000";
    //public static final String URL="http://192.168.191.1:8000";
    public static final String USERAPI="/userapi/";
    public static final String IMAGEAPI="/imageupapi/";
    public static final String PROCESSAPI="/gongchengapi/";
    public static final String SHEZHIAPI="/shezhiapi/";
    public static final String ZHIFUBAOSIGNAPI="/zhifubaosignapi/";
    public static final String WEIXINSIGNAPI="/weixinsignapi/";

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
        qiehuan=(Button)findViewById(R.id.qiehuan);

        sheng=(TextView)findViewById(R.id.sheng);
        shi= (TextView) findViewById(R.id.shi);
        DataFragment dataFragment=DataFragment.getInstance();
        sheng.setText(dataFragment.province);
        shi.setText(dataFragment.city);

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
        fragment.mhandler1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x2333){
                    right_menu.openDrawer(right_content);
                }
                if (msg.what==0x3333){
                    finish();
                }
            }
        };
        option_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right_menu.closeDrawer(right_content);
            }
        });


        qiehuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDiqudialog();
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
                top_bar_text.setText("点匠台");

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
                top_bar_text.setText("我的工程");

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
                top_bar_text.setText("首页");

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

        setRightMenuListener();

    }

    public void RightMenu_init(){
        biaoqian=(EditText)findViewById(R.id.biaoqiantext);
        //gongzhong=(RelativeLayout)findViewById(R.id.gongzhong);
        diqu=(RelativeLayout)findViewById(R.id.diqu);
        //gongzhongtext=(TextView)findViewById(R.id.gongzhong_text);
        diqutext=(TextView)findViewById(R.id.diqutext);
    }

    public void setRightMenuListener(){
        /*gongzhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongzhongdialog();
            }
        });*/

        //取出筛选要求数据
        option_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataFragment dataFragment=DataFragment.getInstance();
                //dataFragment.gongzhong=gongzhongtext.getText().toString();
                dataFragment.biaoqian=biaoqian.getText().toString();
                dataFragment.mhandler2.sendEmptyMessage(0x1111);
                right_menu.closeDrawer(right_content);
            }
        });

        diqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDiqudialog2();
            }
        });
    }

    public void gongzhongdialog() {
        final String[] gongzhong = {"1电工","1木工","1瓦工","1焊工","1架子工","1钢筋工","1抹灰工","1砌筑工","1混凝土工","1油漆工","1防水工","1管道工","吊顶工","2无气喷涂工","钻孔工","拆除工","普工/杂工","项目经理","生产经理","工长","监理","施工员","质量员","安全员","材料员","资料员","预算员","机械员","测量员","劳务员","司索指挥","塔吊司机","吊车司机","起重机司机","升降机司机","挖掘机司机","推土机司机","叉车司机","电梯司机","机械修理工","机械安装/拆除工"};
        String[] gongzhongfenlei={"室内装修类","室外建筑类",};
        String[] shinei1={"电工","瓦工","木工","油漆工","防水工","管道工"};
        String[] jianzhu2={"焊工","架子工","钢筋工","抹灰工","砌筑工","混凝土工"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("请选择工种分类")
                .setItems(gongzhong, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //gongzhongtext.setText(gongzhong[which]);
                    }
                });
        builder.create().show();
    }

    public void setDiqudialog(){
        final ArrayList<Map<String,Object>> arrayList;
        final DataFragment dataFragment=DataFragment.getInstance();
        arrayList=dataFragment.getGson();


        ArrayList<ArrayList> citylist=new ArrayList<>();
        final String[] sheng1=new String[arrayList.size()];
        for (int i=0;i<arrayList.size();i++){
            sheng1[i]=(String) arrayList.get(i).get("name");
            //citylist.add((ArrayList) arrayList.get(i).get("city"));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("请选择区域")
                .setItems(sheng1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataFragment.province=sheng1[which];
                        sheng.setText(sheng1[which]);
                        ArrayList<LinkedTreeMap<String,Object>> arrayList1;
                        arrayList1= (ArrayList<LinkedTreeMap<String, Object>>) arrayList.get(which).get("city");
                        final String[] shi1=new String[arrayList1.size()];
                        for (int i=0;i<arrayList1.size();i++){
                            shi1[i]= (String) arrayList1.get(i).get("name");
                        }
                        AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this)
                                .setTitle("请选择区域")
                                .setItems(shi1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        shi.setText(shi1[which]);
                                        dataFragment.city=shi1[which];
                                    }
                                });
                        builder1.create().show();
                    }
                });
        builder.create().show();
    }

    public void setDiqudialog2(){
        final ArrayList<Map<String,Object>> arrayList;
        final DataFragment dataFragment=DataFragment.getInstance();
        arrayList=dataFragment.getGson();


        ArrayList<ArrayList> citylist=new ArrayList<>();
        final String[] sheng1=new String[arrayList.size()];
        for (int i=0;i<arrayList.size();i++){
            sheng1[i]=(String) arrayList.get(i).get("name");
            //citylist.add((ArrayList) arrayList.get(i).get("city"));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("请选择区域")
                .setItems(sheng1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataFragment.sheng_=sheng1[which];
                        ArrayList<LinkedTreeMap<String,Object>> arrayList1;
                        arrayList1= (ArrayList<LinkedTreeMap<String, Object>>) arrayList.get(which).get("city");
                        final String[] shi1=new String[arrayList1.size()];
                        for (int i=0;i<arrayList1.size();i++){
                            shi1[i]= (String) arrayList1.get(i).get("name");
                        }
                        AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this)
                                .setTitle("请选择区域")
                                .setItems(shi1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dataFragment.shi_=shi1[which];

                                        diqutext.setText(dataFragment.sheng_+","+dataFragment.shi_);
                                    }
                                });
                        builder1.create().show();
                    }
                });
        builder.create().show();
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
