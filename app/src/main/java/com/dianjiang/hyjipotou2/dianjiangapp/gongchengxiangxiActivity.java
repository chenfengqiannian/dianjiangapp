package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/24.
 */
public class gongchengxiangxiActivity extends Activity {

    private TextView number;
    private TextView name;
    private TextView miaoshu;
    private TextView dizhi;
    private TextView gongzhong;
    private TextView yaoqiu;
    private TextView shichang;
    private TextView choulao;
    private TextView beizhu;
    private RelativeLayout queren;
    private LinearLayout textDialog;
    private ImageButton reactivity;

    private RelativeLayout tupian;
    private TextView xinxi_tupiantext;
    private ImageView xinxi_tupian1;
    private ImageView xinxi_tupian2;
    private ImageView xinxi_tupianOption;
    private List<File> files=new ArrayList<>();
    private String[] str1=new String[]{"照相上传","选择图片上传"};
    public static final int NO=0;
    public static final int OK=1;
    private int shenfen_state=1;
    private int shenfen_xiugai_state1=NO;
    private int shenfen_xiugai_state2=NO;
    private int shenfen_hold=1;
    private int shenfen_count1=0;
    private int shenfen_count2=0;
    private File tempFile;
    public static final int SHENFEN_CAMERA=800;
    public static final int SHENFEN_PHOTO=801;
    public static final int SHENFEN_PHOTO_REQUEST_CUT=311;
    public static final int FINISH_CAMERA=412;
    public static final int FINISH_PHOTO=413;
    public static final int FINISH_REQUEST=414;
    private AlertDialog.Builder builder;
    private EditText text;
    private String jinduMessage;
    private int zhuangtai;
    private double price;
    private String[] gongjiang;
    private boolean[] state;
    private String[] gongjiangphone;

    public static final int FIRSTIMG=1;
    public static final int SECONDIMG=2;

    ArrayList<String> strings;

    private String id;
    private TextView buttontext;
    private RelativeLayout canyuzhaobiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gongcheng_xiangxi);

        init();

        setData();

    }

    public void init(){
        DataFragment dataFragment=DataFragment.getInstance();
        ///
        ArrayList<LinkedTreeMap<String,Object>> gongcheng_set=(ArrayList<LinkedTreeMap<String,Object>>)dataFragment.user_datamap.get("gongcheng_set");
        for (int i=0;i<gongcheng_set.size();i++){
            LinkedTreeMap<String,Object> map;
            map=gongcheng_set.get(i);
            if (map.get("id").toString().equals(dataFragment.mprocessItemBean.processnumber)){
                zhuangtai=(int)((double)map.get("zhuangtai"));
            }
        }

        ///
        number= (TextView) findViewById(R.id.fabu3_gongchenghao);
        name=(TextView)findViewById(R.id.fabu3_gongchengming);
        miaoshu= (TextView) findViewById(R.id.fabu3_miaoshu);
        dizhi=(TextView)findViewById(R.id.fabu3_dizhi);
        gongzhong= (TextView) findViewById(R.id.fabu3_gongzhong);
        yaoqiu=(TextView)findViewById(R.id.fabu3_yaoqiu);
        shichang=(TextView)findViewById(R.id.fabu3_shichang);
        choulao=(TextView)findViewById(R.id.fabu3_choulao);
        beizhu=(TextView)findViewById(R.id.fabu3_beizhu);
        queren=(RelativeLayout)findViewById(R.id.fabu2_queren);
        xinxi_tupian1= (ImageView)findViewById(R.id.xinxi_tupian1);
        xinxi_tupian2= (ImageView)findViewById(R.id.xinxi_tupian2);
        xinxi_tupianOption= (ImageView)findViewById(R.id.img_option);
        xinxi_tupiantext=(TextView)findViewById(R.id.fabu1_tupian);
        tupian= (RelativeLayout) findViewById(R.id.fabu2_tupian_);
        buttontext= (TextView) findViewById(R.id.button_text);
        textDialog=(LinearLayout)getLayoutInflater().inflate(R.layout.textdialog, null);
        text= (EditText) textDialog.findViewById(R.id.text123);
        reactivity= (ImageButton) findViewById(R.id.reactivity);

        reactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //canyuzhaobiao= (RelativeLayout) findViewById(R.id.fabu2_canyu);

        /*if (dengluActivity.state==true){
            tupian.setVisibility(View.GONE);
            buttontext.setText("参与招标");
            if (DataFragment.dianjigongcheng=false){
                //canyuzhaobiao.setVisibility(View.GONE);
                queren.setVisibility(View.GONE);
                DataFragment.dianjigongcheng=true;
            }
        }else {
            buttontext.setText("查看招标工匠");  //这里需定义各种状态
        }*/
        switch (DataFragment.gongchengbiaoji){
            case 1:tupian.setVisibility(View.GONE);
                buttontext.setText("参与招标");
                break;

            case 2:tupian.setVisibility(View.GONE);
                buttontext.setText("同意加入该工程");
                break;

            case 3:tupian.setVisibility(View.GONE);
                buttontext.setText("提交工程进度");
                break;

            case 4:buttontext.setText("提交修改");
                //判断审核
                if (zhuangtai>=1){
                    buttontext.setText("支付30%工程款");
                    tupian.setVisibility(View.GONE);
                }

                break;

            case 5:tupian.setVisibility(View.GONE);
                buttontext.setText("查看招标工匠");
                break;

            case 6:tupian.setVisibility(View.GONE);
                buttontext.setText("确认结款");
                break;

            case 7:tupian.setVisibility(View.GONE);
                buttontext.setText("提交完工图片");
                break;

            default:tupian.setVisibility(View.GONE);
                queren.setVisibility(View.GONE);
                break;
        }



        //监听器

        xinxi_tupianOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleListOption(str1, SHENFEN_CAMERA, SHENFEN_PHOTO);
            }
        });
        xinxi_tupian1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shenfen_xiugai_state1==OK) {
                    shenfen_hold = shenfen_state;
                    shenfen_state = FIRSTIMG;
                    simpleListOption(str1, SHENFEN_CAMERA, SHENFEN_PHOTO);
                }
            }
        });

        xinxi_tupian2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shenfen_xiugai_state2==OK) {
                    shenfen_hold = shenfen_state;
                    shenfen_state = SECONDIMG;
                    simpleListOption(str1, SHENFEN_CAMERA, SHENFEN_PHOTO);
                }
            }
        });

        queren.setOnClickListener(new View.OnClickListener() {
            DataFragment dataFragment=DataFragment.getInstance();
            @Override
            public void onClick(View v) {
                switch (DataFragment.gongchengbiaoji){
                    case 1:canyuzhaobiao(); //参与招标//
                        break;

                    case 2:tongyicanyu();  //同意参与该工程//
                        break;

                    case 3:setTextDialog();  //提交工程进度
                        break;

                    case 4:
                        if (zhuangtai>=1){
                            //订单确认页面
                            Intent intent=new Intent(gongchengxiangxiActivity.this,dingdanActivity.class);
                            intent.putExtra("jiage",price*0.3);
                            startActivity(intent);
                        }else {
                            chuantu();
                        }
                        break;

                    case 5://Intent intent2=new Intent(gongchengxiangxiActivity.this,zhaobiaoActivity.class); //招标中  查看工匠
                        //startActivity(intent2);
                        zhaobiaodialog();
                        break;

                    case 6:Intent intent=new Intent(gongchengxiangxiActivity.this,pingjiaActivity.class);  //结款
                        intent.putExtra("jiage",price*0.7);
                        startActivity(intent);
                        break;

                    case 7:
                        simpleListOption(str1,FINISH_CAMERA,FINISH_PHOTO);
                }
            }
        });
    }

    public void setData(){
        DataFragment dataFragment=DataFragment.getInstance();
        name.setText(dataFragment.mprocessItemBean.processname);
        number.setText((int) Double.parseDouble(dataFragment.mprocessItemBean.processnumber) + "");
        miaoshu.setText(dataFragment.mprocessItemBean.miaoshu);



        ArrayList<LinkedTreeMap<String,Object>> gongcheng_set=dataFragment.process_datamap;
        //(ArrayList<LinkedTreeMap<String,Object>>)dataFragment.user_datamap.get("gongcheng_set");



        for (int i=0;i<gongcheng_set.size();i++){
            LinkedTreeMap<String,Object> map;
            map=gongcheng_set.get(i);
            if (map.get("id").toString().equals(dataFragment.mprocessItemBean.processnumber)){
                dizhi.setText(map.get("xiangxidizhi").toString());
                gongzhong.setText(map.get("gongzhong").toString());
                yaoqiu.setText(map.get("yaoqiu").toString());
                shichang.setText(map.get("kaishitime").toString()+"至"+map.get("jieshutime").toString());
                choulao.setText(map.get("xinchou").toString());
                beizhu.setText(map.get("beizhu").toString());
                price= (double) map.get("xinchou");
            }
        }
    }


    public void simpleListOption(String[] str1, final int camera, final int photo){
        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setTitle("请选择上传方式")
                .setItems(str1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            camera(camera);
                        }
                        if (which == 1) {
                            gallery(photo);
                        }
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (DataFragment.gongchengbiaoji == 7) {
                } else {
                    shenfen_state = shenfen_hold;
                }
            }
        });
        builder.create().show();
    }

    //跳转至照相机
    public void camera(int f) {
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            tempFile = new File(Environment.getExternalStorageDirectory(),"camera_photo");
            // 从文件中创建uri
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, f);
    }

    //跳转至xiangce
    public void gallery(int f) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, f);
    }

    //判断存储卡是否存在
    public boolean hasSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    //剪切图片
    private void crop(Uri uri,int f) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        files.add(mytool.getFileByUri(uri, this));
        for (int i=0;i<files.size();i++){
            mytool.compressImage(files.get(i));
        }

        if (DataFragment.gongchengbiaoji==7){
            finishimage();
        }else {

            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
            startActivityForResult(intent, f);
        }
    }

    public void finishimage(){
        if (files.size()>0) {
            OkHttpUtils
                    .post()
                    .url(MainActivity.URL + MainActivity.WANCHENGIMAGE)
                    .addParams("phone", dengluActivity.phone)
                    .addFile("formimage",files.get(0).getName(),files.get(0))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Request request, Exception e) {
                            Log.i("LOL","finishimage error");
                        }

                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(gongchengxiangxiActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

        }
    }

    public void zhaobiaopost(){

    }

    public void chuantu(){
        DataFragment dataFragment=DataFragment.getInstance();
        id=dataFragment.mprocessItemBean.processnumber;
        String id_str=String.valueOf((int)((double)Double.valueOf(id)));
        PostFormBuilder buider2 =OkHttpUtils.post();
        //ArrayList<File> files;
        //files= (ArrayList<File>)dataFragment.fabu_datamap.get("tupian");
        if (!files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                buider2.addFile(i + "aaaa", files.get(i).getName(), files.get(i));
            }
            buider2
                    .url(MainActivity.URL + MainActivity.IMAGEAPI)
                    .addParams("id", id_str)
                    .addParams("shangchuan", "gongcheng,tupian")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Request request, Exception e) {
                            String string=request.toString();
                            Log.i("LOL",string);
                            Toast.makeText(gongchengxiangxiActivity.this,"图片上传成功",Toast.LENGTH_LONG).show(); ///////等人
                            finish();
                        }

                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(gongchengxiangxiActivity.this, "图片上传成功", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
        }else finish();
    }

    public void tijiaojindu(){
        DataFragment dataFragment=DataFragment.getInstance();
        Double a=Double.valueOf(dataFragment.mprocessItemBean.processnumber);
        int b=(int)((double)a);
        OkHttpClient client = new OkHttpClient();
        Gson gson1=new Gson();
        HashMap<String,Object> data1 =new HashMap<>();
        data1.put("id",b);
        data1.put("gongchengjindu",jinduMessage);
        OkHttpUtils
                .postString()
                .url(MainActivity.URL + MainActivity.PROCESSAPI)
                .content(gson1.toJson(data1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.i("LOL", "Dwq");
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(gongchengxiangxiActivity.this, "进度提交成功", Toast.LENGTH_LONG).show();
                    }
                });
    }


    public void tongyicanyu(){
        ArrayList<String> strings = null;
        ArrayList<String> strings2=null;
        int value;
        DataFragment dataFragment=DataFragment.getInstance();
        value=(int)Double.parseDouble(dataFragment.mprocessItemBean.processnumber);
        for (LinkedTreeMap<String,Object> nidaye:dataFragment.process_datamap) {
            if ((int) (double) nidaye.get("id") == value) {
                strings = (ArrayList<String>) nidaye.get("zhiding");
                strings2=(ArrayList<String>)nidaye.get("yaoqing");

                for (int i=0;i<strings2.size();i++){
                    if (dengluActivity.phone.equalsIgnoreCase(strings2.get(i))){
                        strings2.remove(i);
                    }
                }

                strings.add(dengluActivity.phone);
            }
        }

                OkHttpClient client = new OkHttpClient();
                Gson gson1 = new Gson();
                HashMap<String, Object> data1 = new HashMap<>();
                data1.put("zhiding", strings);
                data1.put("yaoqing",strings2);
                data1.put("id",value);
                OkHttpUtils
                        .postString()
                        .url(MainActivity.URL + MainActivity.PROCESSAPI)
                        .content(gson1.toJson(data1))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                Log.i("LOL", "Dwq");
                                Toast.makeText(gongchengxiangxiActivity.this, "未能成功加入", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(gongchengxiangxiActivity.this, "成功加入工程", Toast.LENGTH_LONG).show();
                                finish();
                                Log.i("LOL2",response);
                            }
                        });
            }


    public void canyuzhaobiao(){
        ArrayList<String> strings = null;
        int value;
        DataFragment dataFragment=DataFragment.getInstance();
        value=(int)Double.parseDouble(dataFragment.mprocessItemBean.processnumber);
        for (LinkedTreeMap<String,Object> nidaye:dataFragment.process_datamap) {
            if ((int) (double) nidaye.get("id") == value) {
                strings = (ArrayList<String>) nidaye.get("zhaobiao");
                strings.add(dengluActivity.phone);
            }
        }

                OkHttpClient client = new OkHttpClient();
                Gson gson1 = new Gson();
                HashMap<String, Object> data1 = new HashMap<>();
                data1.put("zhaobiao", strings);
                data1.put("id",value);
                OkHttpUtils
                        .postString()
                        .url(MainActivity.URL + MainActivity.PROCESSAPI)
                        .content(gson1.toJson(data1))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                Log.i("LOL", "Dwq");
                                Toast.makeText(gongchengxiangxiActivity.this, "参与招标失败", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(gongchengxiangxiActivity.this, "参与招标中", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
        //Intent intent=new Intent(gongchengxiangxiActivity.this,dingdanActivity.class);
        //由平台指定
       // intent.putExtra("jiage", price);
       // startActivity(intent);
    }

    public void setTextDialog(){
        builder=new AlertDialog.Builder(this)
                .setTitle("请输入工程进度")
                .setView(null)
                .setView(textDialog)
                .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //提取消息
                        jinduMessage=text.getText().toString();
                        tijiaojindu();
                        textDialog=null;
                        if (textDialog==null){
                            textDialog=(LinearLayout)getLayoutInflater().inflate(R.layout.textdialog, null);
                            text= (EditText) textDialog.findViewById(R.id.text123);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textDialog=null;
                        if (textDialog==null){
                            textDialog=(LinearLayout)getLayoutInflater().inflate(R.layout.textdialog, null);
                            text= (EditText) textDialog.findViewById(R.id.text123);
                        }
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                textDialog=null;
                if (textDialog==null){
                    textDialog=(LinearLayout)getLayoutInflater().inflate(R.layout.textdialog, null);
                    text= (EditText) textDialog.findViewById(R.id.text123);
                }
            }
        });
        builder.create().show();
    }

    public void jinduHttp(){

    }

    public void zhaobiaodialog(){
        //ArrayList<String> strings;
        final int value;
        DataFragment dataFragment=DataFragment.getInstance();
        value=(int)Double.parseDouble(dataFragment.mprocessItemBean.processnumber);
        for (LinkedTreeMap<String,Object> nidaye:dataFragment.process_datamap) {
            if ((int) (double) nidaye.get("id") == value) {
                strings = mytool.RemoveSame((ArrayList < String>) nidaye.get("zhaobiao"));

                //strings.add(dataFragment.dianjiangItemBean.phone);
                if (strings.size()>0) {
                    gongjiang = new String[strings.size()];
                    gongjiangphone = new String[strings.size()];
                    int i;
                    int a=0;
                    for (LinkedTreeMap map:dataFragment.gongjiang_data) {
                        for (i=0;i<strings.size();i++) {
                            if (strings.get(i).equalsIgnoreCase(map.get("phone").toString())) {
                                gongjiang[a] = map.get("xingming").toString();
                                gongjiangphone[a]=strings.get(i);
                                a++;
                            }
                        }
                    }
                    state=new boolean[gongjiang.length];

                    //final String[] gongzhong = {"木模工","钢模工","砌墙工","粉刷工","钢筋工","混凝土工","油漆工","玻璃工","","起重工","吊车司机","指挥","电焊工","机修工","维修电工","","测量工","防水工","架子工","普工","建筑设备安装工","","水工","电工","白铁工","管工"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("请选择工匠加入工程")
                            .setMultiChoiceItems(gongjiang,state, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    //if (fabuFragment.state[which])
                                    //DataFragment dataFragment=DataFragment.getInstance();
                                    //dataFragment.gongzhongstring = dataFragment.gongzhongstring +" "+gongzhong[which];
                                    //dataFragment.gongzhongstring.replace()
                                }
                            })
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DataFragment dataFragment=DataFragment.getInstance();
                                    for (int i=0;i<state.length;i++){
                                        if (state[i]==true){
                                            strings.add(gongjiangphone[i]);
                                            state[i]=false;
                                        }
                                    }

                                    if (strings.size()>0) {
                                        //
                                        OkHttpClient client = new OkHttpClient();
                                        Gson gson1 = new Gson();
                                        HashMap<String, Object> data1 = new HashMap<>();
                                        data1.put("zhiding", strings);
                                        data1.put("id", value);
                                        OkHttpUtils
                                                .postString()
                                                .url(MainActivity.URL + MainActivity.PROCESSAPI)
                                                .content(gson1.toJson(data1))
                                                .build()
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onError(Request request, Exception e) {
                                                        Log.i("LOL", "Dwq");
                                                        Toast.makeText(gongchengxiangxiActivity.this, "发生错误", Toast.LENGTH_LONG).show();
                                                    }

                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(gongchengxiangxiActivity.this, "已同意招标", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    }else {
                                        Toast.makeText(gongchengxiangxiActivity.this,"未选择招标工匠",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    builder.create().show();
                }else {
                    Toast.makeText(gongchengxiangxiActivity.this,"暂无工匠参与招标",Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int a=RESULT_OK;
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {

            ////////////////////////////////////////////
            case SHENFEN_PHOTO:
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop(uri,SHENFEN_PHOTO_REQUEST_CUT);
                    xinxi_tupiantext.setText("");
                }
                break;
            case SHENFEN_CAMERA:
                if (hasSdcard()) {
                    crop(Uri.fromFile(tempFile),SHENFEN_PHOTO_REQUEST_CUT);
                    xinxi_tupiantext.setText("");
                } else {
                    Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case SHENFEN_PHOTO_REQUEST_CUT:
                if (data != null) {
                    switch (shenfen_state){
                        case FIRSTIMG:
                            Bitmap bitmap = data.getParcelableExtra("data");
                            xinxi_tupian1.setImageBitmap(bitmap);
                            shenfen_xiugai_state1=OK;
                            shenfen_count1++;
                            shenfen_state=SECONDIMG;
                            if (shenfen_count1>=2){
                                shenfen_state=shenfen_hold;
                            }
                            break;
                        case SECONDIMG:
                            Bitmap bitmap0=data.getParcelableExtra("data");
                            xinxi_tupian2.setImageBitmap(bitmap0);
                            shenfen_xiugai_state2=OK;
                            xinxi_tupianOption.setVisibility(View.INVISIBLE);
                            shenfen_count2++;
                            if (shenfen_count2>=2){
                                shenfen_state=shenfen_hold;
                            }
                            break;
                    }
                }
                try {
                    // 将临时文件删除
                    tempFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case FINISH_CAMERA:
                if (hasSdcard()) {
                    crop(Uri.fromFile(tempFile),SHENFEN_PHOTO_REQUEST_CUT);
                    xinxi_tupiantext.setText("");
                } else {
                    Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;

            case FINISH_PHOTO:
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop(uri, 31333);
                    //xinxi_tupiantext.setText("");
                }
                break;
        }
    }
}
