package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hyjipotou2 on 16/4/8.
 */
public class xinxixiugaiActivity extends Activity {

    public static final int TOUXIANG_CAMERA=1000;
    public static final int TOUXIANG_PHOTO=1001;
    public static final int TOU_PHOTO_REQUEST_CUT=233;

    private ImageButton fanhui;
    private EditText nicheng;
    private EditText gongsimingcheng;
    private EditText gongsidizhi;
    private SimpleDraweeView touxiang;

    private File tempFile;
    private File touxiang_file;
    private String[] str1=new String[]{"照相上传","选择图片上传"};
    private Uri mUri;

    private String nichengtext;
    private String gongsimingchengtext;
    private String gongsidizhitext;

    private RelativeLayout baocun;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化Fresco
        Fresco.initialize(xinxixiugaiActivity.this);
        setContentView(R.layout.gerenxinxixiugai);

        init();
        setClickListener();

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void init(){
        fanhui= (ImageButton) findViewById(R.id.fanhuixiugai);
        nicheng= (EditText) findViewById(R.id.nicheng);
        gongsimingcheng= (EditText) findViewById(R.id.gongsiming);
        gongsidizhi= (EditText) findViewById(R.id.gongsidizhi);
        touxiang= (SimpleDraweeView) findViewById(R.id.xinxixiugai_touxiang);
        baocun= (RelativeLayout) findViewById(R.id.xinxixiugai_baocun);
        getUserData();
    }

    public void setClickListener(){
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleListOption(str1, TOUXIANG_CAMERA, TOUXIANG_PHOTO);
            }
        });

        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传数据  touxiang_file
                getData();
                shangchuanData();

            }
        });
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
        mUri=uri;
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        touxiang_file = mytool.getFileByUri(uri, this);
        mytool.compressImage(touxiang_file);
        startActivityForResult(intent,f);
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
        builder.create().show();
    }

    public void getData(){
        nichengtext=nicheng.getText().toString();
        gongsimingchengtext=gongsimingcheng.getText().toString();
        gongsidizhitext=gongsidizhi.getText().toString();
    }

    public void shangchuanData(){

        OkHttpClient client = new OkHttpClient();
        String str;
        Gson gson1=new Gson();
        HashMap<String,Object> data2 =new HashMap<>();
        data2.put("nichang",nichengtext);
        data2.put("dizhi",gongsidizhitext);
        data2.put("gongsi",gongsimingchengtext);
        data2.put("phone",dengluActivity.phone);
        str=gson1.toJson(data2);
                OkHttpUtils
                        .postString()
                        .url(MainActivity.URL + MainActivity.USERAPI)
                        .content(str)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                Log.i("LOL", "Dwq");
                                Toast.makeText(xinxixiugaiActivity.this, "发生未知错误", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(String response) {
                                if (!(touxiang_file==null)) {
                                    PostFormBuilder buider3 = OkHttpUtils.post();
                                    buider3.addFile("ffff", touxiang_file.getName(), touxiang_file)
                                            .url(MainActivity.URL + MainActivity.IMAGEAPI)
                                            .addParams("id", dengluActivity.phone)
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
                                                                    DataFragment dataFragment1 = DataFragment.getInstance();
                                                                    dataFragment1.user_datamap = (LinkedTreeMap<String, Object>) response;
                                                                    Toast.makeText(xinxixiugaiActivity.this, "信息修改成功", Toast.LENGTH_LONG).show();
                                                                    finish();

                                                                }
                                                            });
                                                }
                                            });
                                }else {
                                    finish();
                                }
                            }
                        });


    }

    public void getUserData(){
        DataFragment dataFragment=DataFragment.getInstance();
        gongsidizhitext= (String) dataFragment.user_datamap.get("dizhi");
        gongsimingchengtext=(String)dataFragment.user_datamap.get("gongsi");
        nichengtext=(String)dataFragment.user_datamap.get("nichang");
        nicheng.setText(nichengtext);
        gongsidizhi.setText(gongsidizhitext);
        gongsimingcheng.setText(gongsimingchengtext);
        //头像
        Uri uri;
        ArrayList<String> arrayList;
        arrayList=(ArrayList<String>) dataFragment.user_datamap.get("touxiang");
        if (arrayList.size()>=1){
           uri=mytool.UriFromSenge(arrayList.get(arrayList.size()-1));
            touxiang.setImageURI(uri);
        }
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
                    crop(uri, TOU_PHOTO_REQUEST_CUT);
                }
                break;
            case TOUXIANG_CAMERA:
                if (hasSdcard()) {
                    crop(Uri.fromFile(tempFile), TOU_PHOTO_REQUEST_CUT);
                } else {
                    Toast.makeText(xinxixiugaiActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case TOU_PHOTO_REQUEST_CUT:
                if (data != null) {
                    touxiang.setImageURI(mUri);
                }

                break;

        }
    }
}
