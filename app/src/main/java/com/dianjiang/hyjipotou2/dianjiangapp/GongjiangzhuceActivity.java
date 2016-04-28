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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyjipotou2 on 16/4/14.
 */
public class GongjiangzhuceActivity extends Activity {

    public static final int TOUXIANG_CAMERA=1000;
    public static final int TOUXIANG_PHOTO=1001;
    public static final int TOU_PHOTO_REQUEST_CUT=233;

    public static final int ZIGE_CAMERA=900;
    public static final int ZIGE_PHOTO=901;
    public static final int ZIGE_PHOTO_REQUEST_CUT=133;

    public static final int SHENFEN_CAMERA=800;
    public static final int SHENFEN_PHOTO=801;
    public static final int SHENFEN_PHOTO_REQUEST_CUT=311;

    public static final int FIRSTIMG=1;
    public static final int SECONDIMG=2;

    public static final int NO=0;
    public static final int OK=1;

    private ImageView imageOption_touxiang;
    private ImageView touxiang;
    private ImageView zigezheng1;
    private ImageView zigezheng2;
    private ImageView shenfenzheng1;
    private ImageView shenfenzheng2;
    private ImageView imageOption_zhengshu;
    private ImageView imageOption_shenfezheng;
    private TextView touxiang_text;
    private TextView zhengshu_text;
    private TextView shenfenzheng_text;
    private String[] str1=new String[]{"照相上传","选择图片上传"};
    private File tempFile;
    private RelativeLayout zhuce;
    private EditText xingming;
    private EditText shenfen;
    private File touxiang_file;
    private ArrayList<File> zige_files=new ArrayList<>();
    private ArrayList<File> shenfen_files=new ArrayList<>();
    private String phonenow;

    private int touxiang_state=0;

    //身份各种标记
    private int shenfen_state=1;
    private int shenfen_xiugai_state1=NO;
    private int shenfen_xiugai_state2=NO;
    private int shenfen_hold=1;
    private int shenfen_count1=0;
    private int shenfen_count2=0;

    //资格各种标记
    private int zige_state=1;
    private int zige_xiugai_state1=NO;
    private int zige_xiugai_state2=NO;
    private int zige_hold=1;
    private int zige_count1=0;
    private int zige_count2=0;


    private String name;
    private String idcard;

    private int crop_state;
    public static final int TOUXIANG_CROP=6;
    public static final int ZIGE_CROP=7;
    public static final int SHENFEN_CROP=8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gongjiangzhuce);
        init();

        //各种监听
        imageOption_touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleListOption(str1, TOUXIANG_CAMERA, TOUXIANG_PHOTO);
            }
        });
        imageOption_zhengshu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleListOption(str1,ZIGE_CAMERA,ZIGE_PHOTO);
            }
        });
        imageOption_shenfezheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleListOption(str1,SHENFEN_CAMERA,SHENFEN_PHOTO);
            }
        });
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
                if (touxiang_file==null || zige_files.isEmpty() || shenfen_files.isEmpty() || idcard==null || name==null){
                    Toast.makeText(GongjiangzhuceActivity.this,"账户信息不能为空",Toast.LENGTH_LONG).show();
                    return;}


               PostFormBuilder buider =OkHttpUtils.post();
                for(int i=0;i<zige_files.size();i++){
                    buider.addFile(i+"ffff",zige_files.get(i).getName(),zige_files.get(i));
                }
                        buider
                        .url(MainActivity.URL + MainActivity.IMAGEAPI)
                        .addParams("id", zhuceActivity.phonenow)
                        .addParams("shangchuan", "user,zhengshu")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                Log.d("LOL", "wori_zige");
                            }

                            @Override
                            public void onResponse(String response) {

                                PostFormBuilder buider2 =OkHttpUtils.post();
                                for(int i=0;i<shenfen_files.size();i++){
                                    buider2.addFile(i+"aaaa",shenfen_files.get(i).getName(),shenfen_files.get(i));
                                }
                                buider2
                                        .url(MainActivity.URL + MainActivity.IMAGEAPI)
                                        .addParams("id",zhuceActivity.phonenow)
                                        .addParams("shangchuan","user,shenfengzheng")
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Request request, Exception e) {
                                                Log.d("LOL", "wori_shenfen");
                                            }

                                            @Override
                                            public void onResponse(String response) {
                                                Log.i("LOL", "shenfen");

                                                PostFormBuilder buider3 = OkHttpUtils.post();
                                                buider3.addFile("ffff", touxiang_file.getName(), touxiang_file)
                                                        .url(MainActivity.URL + MainActivity.IMAGEAPI)
                                                        .addParams("id", zhuceActivity.phonenow)
                                                        .addParams("shangchuan", "user,touxiang")
                                                        .build()
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onError(Request request, Exception e) {
                                                                Log.d("LOL", "wori_touxiang");
                                                            }

                                                            @Override
                                                            public void onResponse(String response) {
                                                                Log.d("LOL", "touxiang");
                                                                OkHttpClient client = new OkHttpClient();
                                                                Gson gson1 = new Gson();
                                                                HashMap<String, Object> data1 = new HashMap<>();
                                                                data1.put("shenfengzhengid", idcard);
                                                                data1.put("xingming", name);
                                                                data1.put("phone", zhuceActivity.phonenow);
                                                                OkHttpUtils
                                                                        .postString()
                                                                        .url(MainActivity.URL + MainActivity.USERAPI)
                                                                        .content(gson1.toJson(data1))
                                                                        .build()
                                                                        .execute(new StringCallback() {
                                                                            @Override
                                                                            public void onError(Request request, Exception e) {
                                                                                Log.i("LOL", "Dwq");
                                                                                Toast.makeText(GongjiangzhuceActivity.this, "发生未知错误", Toast.LENGTH_LONG).show();
                                                                            }

                                                                            @Override
                                                                            public void onResponse(String response) {
                                                                                Toast.makeText(GongjiangzhuceActivity.this, "注册成功!", Toast.LENGTH_LONG).show();
                                                                                Intent intent = new Intent(GongjiangzhuceActivity.this, dengluActivity.class);
                                                                                startActivity(intent);
                                                                                finish();
                                                                            }
                                                                        });

                                                            }
                                                        });

                                            }
                                        });


                            }
                        });





            }
        });
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (touxiang_state==1){
                    simpleListOption(str1,TOUXIANG_CAMERA,TOUXIANG_PHOTO);
                }
            }
        });
        zigezheng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zige_xiugai_state1==OK){
                    zige_hold=zige_state;
                    zige_state=FIRSTIMG;
                    simpleListOption(str1,ZIGE_CAMERA,ZIGE_PHOTO);
                }
            }
        });
        zigezheng2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zige_xiugai_state2==OK){
                    zige_hold=zige_state;
                    zige_state=SECONDIMG;
                    simpleListOption(str1,ZIGE_CAMERA,ZIGE_PHOTO);
                }
            }
        });
        shenfenzheng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shenfen_xiugai_state1==OK) {
                    shenfen_hold = shenfen_state;
                    shenfen_state = FIRSTIMG;
                    simpleListOption(str1, SHENFEN_CAMERA, SHENFEN_PHOTO);
                }
            }
        });

        shenfenzheng2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shenfen_xiugai_state2==OK) {
                    shenfen_hold = shenfen_state;
                    shenfen_state = SECONDIMG;
                    simpleListOption(str1, SHENFEN_CAMERA, SHENFEN_PHOTO);
                }
            }
        });
    }

    public void init(){
        xingming=(EditText)findViewById(R.id.zhuce2_zhenshixinming);
        shenfen=(EditText)findViewById(R.id.zhuce2_idcard);
        zhuce=(RelativeLayout)findViewById(R.id.zhanghao_button);
        touxiang_text=(TextView)findViewById(R.id.zhuce2_touxiangtext);
        zhengshu_text=(TextView)findViewById(R.id.zhuce2_zhengshutext);
        shenfenzheng_text=(TextView)findViewById(R.id.zhuce2_shenfenzheng);
        imageOption_shenfezheng=(ImageView)findViewById(R.id.zhuce2_shenfenzhengimg);
        imageOption_zhengshu=(ImageView)findViewById(R.id.zhuce2_zhengshu);
        imageOption_touxiang= (ImageView) findViewById(R.id.zhuce2_touxiang);
        touxiang=(ImageView)findViewById(R.id.touxiang);
        zigezheng1=(ImageView)findViewById(R.id.zigezheng1);
        zigezheng2=(ImageView)findViewById(R.id.zigezheng2);
        shenfenzheng1=(ImageView)findViewById(R.id.shenfenzheng1);
        shenfenzheng2=(ImageView)findViewById(R.id.shenfenzheng2);
    }


    //Dialog弹出
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
                zige_state=zige_hold;
                shenfen_state=shenfen_hold;
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {

            case TOUXIANG_PHOTO:
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop_state=TOUXIANG_CROP;
                    crop(uri, TOU_PHOTO_REQUEST_CUT);
                    touxiang_text.setText("");
                }
                break;
            case TOUXIANG_CAMERA:
                if (hasSdcard()) {
                    crop_state=TOUXIANG_CROP;
                    crop(Uri.fromFile(tempFile),TOU_PHOTO_REQUEST_CUT);
                    touxiang_text.setText("");
                } else {
                    Toast.makeText(GongjiangzhuceActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case TOU_PHOTO_REQUEST_CUT:
                if (data != null) {
                    //touxiang_file=mytool.getFileByUri(data.getData(),this);
                    Bitmap bitmap = data.getParcelableExtra("data");
                    touxiang.setImageBitmap(bitmap);
                    touxiang_state=1;
                    imageOption_touxiang.setVisibility(View.INVISIBLE);
                }

                break;
            ///////////////////////////////////////////
            case ZIGE_CAMERA:
                if (hasSdcard()) {
                    crop_state=ZIGE_CROP;
                    crop(Uri.fromFile(tempFile),ZIGE_PHOTO_REQUEST_CUT);
                    zhengshu_text.setText("");
                } else {
                    Toast.makeText(GongjiangzhuceActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case ZIGE_PHOTO:
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop_state=ZIGE_CROP;
                    crop(uri,ZIGE_PHOTO_REQUEST_CUT);
                    zhengshu_text.setText("");
                }
                break;
            case ZIGE_PHOTO_REQUEST_CUT:
                if (data != null) {
                    switch (zige_state){
                        case FIRSTIMG:
                            Bitmap bitmap = data.getParcelableExtra("data");
                            zigezheng1.setImageBitmap(bitmap);
                            zige_xiugai_state1=OK;
                            zige_state=SECONDIMG;
                            zige_count1++;
                            if (zige_count1>=2){
                                zige_state=zige_hold;
                            }

                            break;
                        case SECONDIMG:
                            Bitmap bitmap0=data.getParcelableExtra("data");
                            zigezheng2.setImageBitmap(bitmap0);
                            zige_xiugai_state2=OK;
                            imageOption_zhengshu.setVisibility(View.INVISIBLE);
                            zige_count2++;
                            if (zige_count2>=2){
                                zige_state=zige_hold;
                            }

                            break;
                    }
                }

                break;

            ////////////////////////////////////////////
            case SHENFEN_PHOTO:
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop_state=SHENFEN_CROP;
                    crop(uri,SHENFEN_PHOTO_REQUEST_CUT);
                    shenfenzheng_text.setText("");
                }
                break;
            case SHENFEN_CAMERA:
                if (hasSdcard()) {
                    crop_state=SHENFEN_CROP;
                    crop(Uri.fromFile(tempFile),SHENFEN_PHOTO_REQUEST_CUT);
                    shenfenzheng_text.setText("");
                } else {
                    Toast.makeText(GongjiangzhuceActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case SHENFEN_PHOTO_REQUEST_CUT:
                if (data != null) {
                    switch (shenfen_state){
                        case FIRSTIMG:
                            Bitmap bitmap = data.getParcelableExtra("data");
                            shenfenzheng1.setImageBitmap(bitmap);
                            shenfen_xiugai_state1=OK;
                            shenfen_state=SECONDIMG;
                            shenfen_count1++;
                            if (shenfen_count1>=2){
                                shenfen_state=shenfen_hold;
                            }
                            break;
                        case SECONDIMG:
                            Bitmap bitmap0=data.getParcelableExtra("data");
                            shenfenzheng2.setImageBitmap(bitmap0);
                            shenfen_xiugai_state2=OK;
                            imageOption_shenfezheng.setVisibility(View.INVISIBLE);
                            shenfen_count2++;
                            if (shenfen_count2>=2){
                                shenfen_state=shenfen_hold;
                            }
                            break;
                    }
                }
                break;

        }
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
        startActivityForResult(intent,f);
    }

    //跳转至xiangce
    public void gallery(int f) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent,f);
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
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        if (crop_state==TOUXIANG_CROP) {
            touxiang_file = mytool.getFileByUri(uri, this);
            mytool.compressImage(touxiang_file);
        }else if (crop_state==ZIGE_CROP){
            zige_files.add(mytool.getFileByUri(uri, this));
            for (int i=0;i<zige_files.size();i++){
                mytool.compressImage(zige_files.get(i));
            }
        }else if(crop_state==SHENFEN_CROP){
            shenfen_files.add(mytool.getFileByUri(uri,this));
            for (int i=0;i<shenfen_files.size();i++){
                mytool.compressImage(shenfen_files.get(i));
            }
        }
        startActivityForResult(intent,f);
    }


    private void getdata(){
        idcard=shenfen.getText().toString();
        name=xingming.getText().toString();
    }
}
