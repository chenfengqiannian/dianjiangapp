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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    public static final int FIRSTIMG=1;
    public static final int SECONDIMG=2;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gongcheng_xiangxi);

        init();

        setData();
    }

    public void init(){
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

        if (dengluActivity.state==true){
            queren.setVisibility(View.GONE);
            tupian.setVisibility(View.GONE);
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
            @Override
            public void onClick(View v) {
                chuantu();
            }
        });
    }

    public void setData(){
        DataFragment dataFragment=DataFragment.getInstance();
        name.setText(dataFragment.mprocessItemBean.processname);
        number.setText(dataFragment.mprocessItemBean.processnumber);
        miaoshu.setText(dataFragment.mprocessItemBean.miaoshu);

        ArrayList<LinkedTreeMap<String,Object>> gongcheng_set=(ArrayList<LinkedTreeMap<String,Object>>)dataFragment.user_datamap.get("gongcheng_set");
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
                shenfen_state = shenfen_hold;
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

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent,f);
    }

    public void chuantu(){
        DataFragment dataFragment=DataFragment.getInstance();
        id=dataFragment.mprocessItemBean.processnumber;
        PostFormBuilder buider2 =OkHttpUtils.post();
        //ArrayList<File> files;
        //files= (ArrayList<File>)dataFragment.fabu_datamap.get("tupian");
        if (!files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                buider2.addFile(i + "aaaa", files.get(i).getName(), files.get(i));
            }
            buider2
                    .url(MainActivity.URL + MainActivity.IMAGEAPI)
                    .addParams("id", id)
                    .addParams("shangchuan", "gongcheng,tupian")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Request request, Exception e) {
                            String string=request.toString();
                            Log.i("LOL",string);
                            Toast.makeText(gongchengxiangxiActivity.this,"发生未知错误",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(gongchengxiangxiActivity.this, "图片上传成功", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
        }else finish();
    }
    /*public void getProcess(){
        DataFragment dataFragment=DataFragment.getInstance();
        OkHttpUtils
                .get()
                .url(MainActivity.URL+MainActivity.PROCESSAPI)
                .addParams("id",dataFragment.mprocessItemBean.processnumber)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
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

                    }
                });
    }*/
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

        }
    }
}
