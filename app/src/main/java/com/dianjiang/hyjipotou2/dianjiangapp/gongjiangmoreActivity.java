package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class gongjiangmoreActivity extends AppCompatActivity implements View.OnClickListener {
    SimpleDraweeView touxiang;
    EditText Textname;
    TextView gongzhong1;
    EditText gongsi;
    EditText gongzuodi;
    EditText biaoqian;
    EditText ziwojieshao;
    SimpleDraweeView zhengshu1;
    SimpleDraweeView zhengshu2;
    ImageButton fanhui;
    Button bianji;
    RelativeLayout baocun;
    DataFragment dataFragment;
    TextView dengji;
    EditText rixin;


    TextView yeji_dengji;
    ProgressBar jingyanzhi;
    RelativeLayout guize;
    RatingBar pingfen;
    RelativeLayout canyurenwu;




    Uri zige_uri;
    Uri tou_uri;
    private String[] str1 = new String[]{"照相上传", "选择图片上传"};

    public static final int TOUXIANG_CAMERA = 1000;
    public static final int TOUXIANG_PHOTO = 1001;
    public static final int TOU_PHOTO_REQUEST_CUT = 233;

    public static final int ZIGE_CAMERA = 900;
    public static final int ZIGE_PHOTO = 901;
    public static final int ZIGE_PHOTO_REQUEST_CUT = 133;

    public static final int FIRSTIMG = 1;
    public static final int SECONDIMG = 2;

    public static final int NO = 0;
    public static final int OK = 1;

    private File tempFile;

    private File touxiang_file;
    private ArrayList<File> zige_files = new ArrayList<>();
    private int touxiang_state = 0;
    //资格各种标记
    private int zige_state = 1;
    private int zige_xiugai_state1 = NO;
    private int zige_xiugai_state2 = NO;
    private int zige_hold = 1;
    private int zige_count1 = 0;
    private int zige_count2 = 0;
    private int crop_state;
    public static final int TOUXIANG_CROP = 6;
    public static final int ZIGE_CROP = 7;
    private static boolean[] state={false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        Intent intent = getIntent();

        String dataString = intent.getStringExtra("leixing");
        if (dataString.equalsIgnoreCase("ketang")) {
            init(0);
        }
        if (dataString.equalsIgnoreCase("gerenxinxi")) {
            init(1);
        }
        if (dataString.equalsIgnoreCase("wodeyeji")){
            init(2);
        }
    }

    void init(int index) {
        dataFragment = DataFragment.getInstance();
        if (index == 0) {

            setContentView(R.layout.fragment_blank);


        }
        if (index == 1) {
            setContentView(R.layout.gongjiangxinxi);
            dengji = (TextView) findViewById(R.id.dianjiang_dengji);
            Textname = (EditText) findViewById(R.id.dianjiang_name);
            gongzhong1 = (TextView) findViewById(R.id.dianjiang_gongzhong);
            gongsi = (EditText) findViewById(R.id.dianjiang_gongsi);
            gongzuodi = (EditText) findViewById(R.id.dianjiang_gongzuodi);
            biaoqian = (EditText) findViewById(R.id.dianjiang_biaoqian);
            zhengshu1 = (SimpleDraweeView) findViewById(R.id.dianjiang_zheng);
            zhengshu2 = (SimpleDraweeView) findViewById(R.id.dianjiang_zheng2);
            ziwojieshao = (EditText) findViewById(R.id.ziwojieshao);
            rixin = (EditText) findViewById(R.id.dianjiang_rixin_num);
            touxiang=(SimpleDraweeView)findViewById(R.id.dianjiang_touxiang);

            fanhui = (ImageButton) findViewById(R.id.dianjiang_fanhui);
            baocun = (RelativeLayout) findViewById(R.id.yaoqing);


            Textname.setText((String) dataFragment.user_datamap.get("xingming"));
            gongsi.setText((String) dataFragment.user_datamap.get("gongsi"));
            gongzuodi.setText((String) dataFragment.user_datamap.get("gongzuodi"));
            biaoqian.setText((String) dataFragment.user_datamap.get("biaoqian"));
            ziwojieshao.setText((String) dataFragment.user_datamap.get("ziwojieshao"));
            gongzhong1.setText((String) dataFragment.user_datamap.get("gongzhong"));
            rixin.setText(dataFragment.user_datamap.get("rixin").toString());
            Map<Double,String> map=new HashMap<>();
            map.put(0.0, "新人工匠");
            map.put(1.0, "工匠一级");
            map.put(2.0, "工匠二级");
            map.put(3.0, "工匠三级");
            map.put(4.0, "工匠四级");
            map.put(5.0, "工匠五级");
            map.put(6.0, "工匠达人");
            dengji.setText(map.get(dataFragment.user_datamap.get("dengji")));



            ArrayList<String> zhsngshudata = (ArrayList<String>) dataFragment.user_datamap.get("zhengshu");
            if (zhsngshudata.size() >= 2) {
                zhengshu1.setImageURI(mytool.UriFromSenge(zhsngshudata.get(zhsngshudata.size() - 1)));
                zhengshu2.setImageURI(mytool.UriFromSenge(zhsngshudata.get(zhsngshudata.size() - 2)));
            }
            if (zhsngshudata.size() == 1) {

                zhengshu1.setImageURI(mytool.UriFromSenge(zhsngshudata.get(0)));
            }

            Uri uri;
            ArrayList<String> arrayList;
            arrayList=(ArrayList<String>) dataFragment.user_datamap.get("touxiang");
            if (arrayList.size()>=1){
                uri=mytool.UriFromSenge(arrayList.get(arrayList.size()-1));
                touxiang.setImageURI(uri);
            }


            gongzhong1.setOnClickListener(this);
            zhengshu1.setOnClickListener(this);
            zhengshu2.setOnClickListener(this);
            // bianji.setOnClickListener(this);
            fanhui.setOnClickListener(this);
            baocun.setOnClickListener(this);
            touxiang.setOnClickListener(this);

        }

        if (index==2){
            DataFragment dataFragment=DataFragment.getInstance();
            Map<Double,String> map=new HashMap<>();
            map.put(0.0,"新人工匠");
            map.put(1.0,"工匠一级");
            map.put(2.0,"工匠二级");
            map.put(3.0,"工匠三级");
            map.put(4.0,"工匠四级");
            map.put(5.0, "工匠五级");
            map.put(6.0, "工匠达人");
            setContentView(R.layout.wodeyeji);
            fanhui= (ImageButton) findViewById(R.id.fanhui);
            yeji_dengji= (TextView) findViewById(R.id.wodeyeji_dengji);
            jingyanzhi= (ProgressBar) findViewById(R.id.jingyanzhi);
            guize= (RelativeLayout) findViewById(R.id.wodeyeji_guize);
            pingfen= (RatingBar) findViewById(R.id.wodeyeji_xingji);
            canyurenwu= (RelativeLayout) findViewById(R.id.wodeyeji_canyurenwu);

            fanhui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            yeji_dengji.setText(map.get(dataFragment.user_datamap.get("dengji")));
            Double jingyan;
            int jingyannow;
            Double jingyanper = Double.valueOf(10);
            jingyan= (Double) dataFragment.user_datamap.get("jingyanzhi");
            jingyannow = (int)((double) (jingyan - ((Double)dataFragment.user_datamap.get("dengji") * jingyanper)));
            jingyanzhi.setProgress(jingyannow);

            Double pingjia;
            pingjia= (Double) dataFragment.user_datamap.get("pingjiaxingji");
            pingfen.setRating((float)((double)pingjia));

            guize.setOnClickListener(this);
            canyurenwu.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == baocun) {
            final gongjiangmoreActivity activity = this;
            Gson gson = new Gson();
            HashMap<String,Object> data = new HashMap<>();
            data.put("xingming", Textname.getText().toString());
            data.put("gongsi", gongsi.getText().toString());
            data.put("gongzuodi", gongzuodi.getText().toString());
            data.put("biaoqian", biaoqian.getText().toString());
            data.put("ziwojieshao", ziwojieshao.getText().toString());
            data.put("gongzhong",gongzhong1.getText().toString());
            data.put("rixin",Integer.getInteger((rixin.getText().toString())));
            data.put("phone",dengluActivity.phone);
            OkHttpUtils.postString().url(MainActivity.URL + MainActivity.USERAPI).content(gson.toJson(data)).build().execute(new StringCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    Toast.makeText(activity, "上传失败", Toast.LENGTH_SHORT).show();
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
                                                        //DataFragment dataFragment1 = DataFragment.getInstance();
                                                        //dataFragment1.user_datamap = (LinkedTreeMap<String, Object>) response;
                                                        //Toast.makeText(gongjiangmoreActivity.this, "信息修改成功", Toast.LENGTH_LONG).show();
                                                        //finish();
                                                        if (!zige_files.isEmpty()){
                                                        PostFormBuilder buider =OkHttpUtils.post();
                                                        for(int i=0;i<zige_files.size();i++){
                                                            buider.addFile(i+"ffff",zige_files.get(i).getName(),zige_files.get(i));
                                                        }
                                                        buider
                                                                .url(MainActivity.URL + MainActivity.IMAGEAPI)
                                                                .addParams("id",dengluActivity.phone)
                                                                .addParams("shangchuan","user,zhengshu")
                                                                .build()
                                                                .execute(new StringCallback() {
                                                                    @Override
                                                                    public void onError(Request request, Exception e) {
                                                                        Log.d("LOL", "wori_zige");
                                                                    }

                                                                    @Override
                                                                    public void onResponse(String response) {
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
                                                                                        Toast.makeText(gongjiangmoreActivity.this, "信息修改成功", Toast.LENGTH_LONG).show();
                                                                                        finish();

                                                                                    }
                                                                                });
                                                                    }

                                                                });
                                                        }else {
                                                            Toast.makeText(gongjiangmoreActivity.this, "信息修改成功", Toast.LENGTH_LONG).show();
                                                            finish();
                                                        }
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
        if (v == fanhui) {
            finish();
        }

        if (v == zhengshu1) {
            zige_state=FIRSTIMG;
            simpleListOption(str1, ZIGE_CAMERA, ZIGE_PHOTO);
        }

        if (v == zhengshu2) {
            zige_state=SECONDIMG;
            simpleListOption(str1, ZIGE_CAMERA, ZIGE_PHOTO);
        }

        if (v==touxiang){
            simpleListOption(str1, TOUXIANG_CAMERA, TOUXIANG_PHOTO);
        }

        if (v==gongzhong1){
            gongzhong1.setText("请设置工种");
            gongzhongdialog();
        }
        if (v==guize){
            Intent intent=new Intent(gongjiangmoreActivity.this,shengjiguizeActivity.class);
            startActivity(intent);
        }
        if (v==canyurenwu){
            Intent intent=new Intent(gongjiangmoreActivity.this,canyurenwuActivity.class);
            startActivity(intent);
        }

    }

    //Dialog弹出
    public void simpleListOption(String[] str1, final int camera, final int photo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
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
                zige_state = zige_hold;
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
            tempFile = new File(Environment.getExternalStorageDirectory(), "camera_photo");
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
    private void crop(Uri uri, int f) {
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
        if (crop_state == TOUXIANG_CROP) {
            tou_uri=uri;
            touxiang_file = mytool.getFileByUri(uri, this);
            mytool.compressImage(touxiang_file);
        } else if (crop_state == ZIGE_CROP) {
            zige_uri=uri;
            zige_files.add(mytool.getFileByUri(uri, this));
            for (int i=0;i<zige_files.size();i++){
                mytool.compressImage(zige_files.get(i));
            }
        }
        startActivityForResult(intent, f);
    }
    public void gongzhongdialog() {
        //final String[] gongzhong = {"电工","木工","瓦工","焊工","架子工","钢筋工","抹灰工","砌筑工","混凝土工","油漆工","防水工","管道工","吊顶工","无气喷涂工","钻孔工","拆除工","普工/杂工","项目经理","生产经理","工长","监理","施工员","质量员","安全员","材料员","资料员","预算员","机械员","测量员","劳务员","司索指挥","塔吊司机","吊车司机","起重机司机","升降机司机","挖掘机司机","推土机司机","叉车司机","电梯司机","机械修理工","机械安装/拆除工"};
        final String[] gongzhong = {"木模工","钢模工","砌墙工","粉刷工","钢筋工","混凝土工","油漆工","玻璃工","","起重工","吊车司机","指挥","电焊工","机修工","维修电工","","测量工","防水工","架子工","普工","建筑设备安装工","","水工","电工","白铁工","管工"};
        String[] gongzhongfenlei={"室内装修类","室外建筑类",};
        String[] shinei1={"电工","瓦工","木工","油漆工","防水工","管道工"};
        String[] jianzhu2={"焊工","架子工","钢筋工","抹灰工","砌筑工","混凝土工"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("请选择工种分类")
                .setItems(gongzhong, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which!=8 && which!=15 && which!=21){
                            gongzhong1.setText(gongzhong[which]);
                            dataFragment.gongzhong=gongzhong1.getText().toString();
                        }
                    }
                });
        builder.create().show();
    }
    public void gongzhongdialog2() {
        // boolean[] state={false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
        final String[] gongzhong = {"电工","木工","瓦工","焊工","架子工","钢筋工","抹灰工","砌筑工","混凝土工","油漆工","防水工","管道工","吊顶工","无气喷涂工","钻孔工","拆除工","普工/杂工","项目经理","生产经理","工长","监理","施工员","质量员","安全员","材料员","资料员","预算员","机械员","测量员","劳务员","司索指挥","塔吊司机","吊车司机","起重机司机","升降机司机","挖掘机司机","推土机司机","叉车司机","电梯司机","机械修理工","机械安装/拆除工"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("请选择工种")
                .setMultiChoiceItems(gongzhong,state, new DialogInterface.OnMultiChoiceClickListener() {
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
                                dataFragment.gongzhongstring=dataFragment.gongzhongstring+" "+gongzhong[i];
                                state[i]=false;
                            }
                        }
                        gongzhong1.setText(dataFragment.gongzhongstring);
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
                    crop_state = TOUXIANG_CROP;
                    crop(uri, TOU_PHOTO_REQUEST_CUT);
                }
                break;
            case TOUXIANG_CAMERA:
                if (hasSdcard()) {
                    crop_state = TOUXIANG_CROP;
                    crop(Uri.fromFile(tempFile), TOU_PHOTO_REQUEST_CUT);
                } else {
                    Toast.makeText(gongjiangmoreActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case TOU_PHOTO_REQUEST_CUT:
                if (data != null) {
                    //touxiang_file=mytool.getFileByUri(data.getData(),this);
                    touxiang.setImageURI(tou_uri);
                    touxiang_state = 1;
                }

                break;
            ///////////////////////////////////////////
            case ZIGE_CAMERA:
                if (hasSdcard()) {
                    crop_state = ZIGE_CROP;
                    crop(Uri.fromFile(tempFile), ZIGE_PHOTO_REQUEST_CUT);
                } else {
                    Toast.makeText(gongjiangmoreActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case ZIGE_PHOTO:
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop_state = ZIGE_CROP;
                    crop(uri, ZIGE_PHOTO_REQUEST_CUT);
                }
                break;
            case ZIGE_PHOTO_REQUEST_CUT:
                if (data != null) {
                    switch (zige_state) {
                        case FIRSTIMG:
                            zhengshu1.setImageURI(zige_uri);
                            /*zige_xiugai_state1 = OK;
                            zige_state = SECONDIMG;
                            zige_count1++;
                            if (zige_count1 >= 2) {
                                zige_state = zige_hold;
                            }*/

                            break;
                        case SECONDIMG:
                            zhengshu2.setImageURI(zige_uri);
                            /*zige_xiugai_state2 = OK;
                            zige_count2++;
                            if (zige_count2 >= 2) {
                                zige_state = zige_hold;
                            }*/

                            break;
                    }
                }

                break;
        }
    }
}
