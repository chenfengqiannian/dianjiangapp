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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyjipotou2 on 16/4/17.
 */
public class pingjiaActivity extends Activity {

    private ImageButton fanhui;
    private EditText pingjiatext;
    private TextView textView;
    private ImageView imageOption;
    private RelativeLayout tijiao;
    private String pingjia;
    private ImageView img1;
    private ImageView img2;
    private RatingBar pingfen;


    //图片文件
    private List<File> files=new ArrayList<>();
    private String[] str1=new String[]{"照相上传","选择图片上传"};

    private int shenfen_state=1;
    private int shenfen_xiugai_state1=NO;
    private int shenfen_xiugai_state2=NO;
    private int shenfen_hold=1;
    private int shenfen_count1=0;
    private int shenfen_count2=0;
    private Double number;         //    ///  / / / / / / / // / // / / // / // / / /
    private File tempFile;
    private DataFragment dataFragment;

    public static final int SHENFEN_CAMERA=800;
    public static final int SHENFEN_PHOTO=801;
    public static final int SHENFEN_PHOTO_REQUEST_CUT=311;

    public static final int FIRSTIMG=1;
    public static final int SECONDIMG=2;

    public static final int NO=0;
    public static final int OK=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pingjia);
        init();

        imageOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleListOption(str1, SHENFEN_CAMERA, SHENFEN_PHOTO);
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shenfen_xiugai_state1 == OK) {
                    shenfen_hold = shenfen_state;
                    shenfen_state = FIRSTIMG;
                    simpleListOption(str1, SHENFEN_CAMERA, SHENFEN_PHOTO);
                }
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shenfen_xiugai_state2 == OK) {
                    shenfen_hold = shenfen_state;
                    shenfen_state = SECONDIMG;
                    simpleListOption(str1, SHENFEN_CAMERA, SHENFEN_PHOTO);
                }
            }
        });

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void init(){
        dataFragment=DataFragment.getInstance();
        fanhui= (ImageButton) findViewById(R.id.fanhui);
        pingjiatext=(EditText)findViewById(R.id.pingjia_text);
        textView=(TextView)findViewById(R.id.text);
        imageOption=(ImageView)findViewById(R.id.imgoption);
        tijiao=(RelativeLayout)findViewById(R.id.fabu_xiayibu);
        img1= (ImageView) findViewById(R.id.xinxi_tupian1);
        img2= (ImageView) findViewById(R.id.xinxi_tupian2);
        pingfen= (RatingBar) findViewById(R.id.pingjia_xingji);

        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //Okhttp
                //
                Intent mintent=getIntent();
                Intent intent=new Intent(pingjiaActivity.this,dingdanActivity.class);
                intent.putExtra("jiage",mintent.getDoubleExtra("jiage",0.0));
                startActivity(intent);
            }
        });
    }

    public void getData(){
        pingjia=pingjiatext.getText().toString();
        number=Double.valueOf(pingfen.getRating());
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
                shenfen_state=shenfen_hold;
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
        files.add(mytool.getFileByUri(uri,this));
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent,f);
    }

    public void tijiaopingjia(){

    }

    @Override
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
                    textView.setText("");
                }
                break;
            case SHENFEN_CAMERA:
                if (hasSdcard()) {
                    crop(Uri.fromFile(tempFile),SHENFEN_PHOTO_REQUEST_CUT);
                    textView.setText("");
                } else {
                    Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case SHENFEN_PHOTO_REQUEST_CUT:
                if (data != null) {
                    switch (shenfen_state){
                        case FIRSTIMG:
                            Bitmap bitmap = data.getParcelableExtra("data");
                            img1.setImageBitmap(bitmap);
                            shenfen_xiugai_state1=OK;
                            shenfen_count1++;
                            shenfen_state=SECONDIMG;
                            if (shenfen_count1>=2){
                                shenfen_state=shenfen_hold;
                            }
                            break;
                        case SECONDIMG:
                            Bitmap bitmap0=data.getParcelableExtra("data");
                            img2.setImageBitmap(bitmap0);
                            shenfen_xiugai_state2=OK;
                            imageOption.setVisibility(View.INVISIBLE);
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
