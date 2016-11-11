package com.dianjiang.hyjipotou2.dianjiangapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.dianjiang.hyjipotou2.dianjiangapp.wx.wxPay;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by hyjipotou2 on 16/4/15.
 */
public class StartActivity extends Activity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SimpleDraweeView simpleDraweeView;
    DataFragment dataFragment=DataFragment.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(StartActivity.this);

        setContentView(R.layout.start_item);
        sharedPreferences=getSharedPreferences("shezhi", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        simpleDraweeView= (SimpleDraweeView) findViewById(R.id.start_view);
        if (sharedPreferences.getBoolean("gps",true)){
            didian();
        }
        wxPay.GetNetIp();

        //getprocess();
        imgget();


        //Uri uri=Uri.fromFile(new File("/storage/sdcard1/tieba/queen.jpg"));

        OkHttpUtils
                .get()
                .url(MainActivity.URL + MainActivity.USERAPI)
                .addParams("job", "false")
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {

                        Log.i("LOL", "response1");
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
                        DataFragment dataFragment = DataFragment.getInstance();
                        Log.i("LOL", "response2");
                        dataFragment.gongjiang_data = (ArrayList<LinkedTreeMap<String, Object>>) response;

                        getprocess();
                    }
                });




                    }

                    public void Start() {
                        new Thread() {
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent();
                                intent.setClass(StartActivity.this, dengluActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }.start();
                    }

                    public void didian() {
                        Location mylo = getLocation();
                        if (mylo == null) {
                            Toast.makeText(getApplication(), "定位权限被禁止", Toast.LENGTH_LONG).show();
                            return;
                        }
                        OkHttpUtils
                                .get()
                                .url("http://api.map.baidu.com/geocoder/v2/")
                                .addParams("ak", "OES3kXn9rMm9Svc4DDOzxvjr")
                                .addParams("location", mylo.getLatitude() + "," + mylo.getLongitude())
                                .addParams("output", "json")
                                .build()
                                .execute(new Callback() {
                                    @Override
                                    public Object parseNetworkResponse(Response response) throws IOException {
                                        Log.i("LOL", "response3");
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
                                        DataFragment dataFragment = DataFragment.getInstance();
                                        Log.i("LOL","response4");
                                        LinkedTreeMap<String, Object> o1 = (LinkedTreeMap<String, Object>) response;
                                        LinkedTreeMap<String, Object> o2 = (LinkedTreeMap<String, Object>) o1.get("result");
                                        LinkedTreeMap<String, Object> o3 = (LinkedTreeMap<String, Object>) o2.get("addressComponent");
                                        dataFragment.city = (String) o3.get("city");
                                        dataFragment.province = (String) o3.get("province");


                                    }
                                });


                    }

                    public Location getLocation() {// 获取Location通过LocationManger获取！
                        LocationManager locManger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            //return TODO;
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);

                        } else {
                            Location loc = locManger.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (loc == null) {
                                loc = locManger.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            }
                            return loc;
                        }

                        return null;


                    }

                    public void getprocess() {

                        OkHttpUtils
                                .get()
                                .url(MainActivity.URL + MainActivity.PROCESSAPI)
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

                                        Log.i("LOL","responseprocess");

                                    }

                                    @Override
                                    public void onResponse(Object response) {
                                        Log.i("LOL","response5");
                                        DataFragment dataFragment = DataFragment.getInstance();
                                        dataFragment.process_datamap = (ArrayList<LinkedTreeMap<String, Object>>) response;
                                        Start();

                                    }
                                });
                    }

                    public void requestinit() {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            //return TODO;
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                        }
                    }

    public void imgget(){
        OkHttpUtils
                .get()
                .url(MainActivity.URL + MainActivity.SHEZHIAPI)
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
                        DataFragment dataFragment=DataFragment.getInstance();
                        String string=new String();
                        Uri uri=null;
                        LinkedTreeMap<String,Object> linkedTreeMap= (LinkedTreeMap<String, Object>) response;
                        dataFragment.linkedTreeMap= (LinkedTreeMap<String, Object>) response;
                        ArrayList<String> arrayList= (ArrayList<String>) linkedTreeMap.get("shouyetupian");
                        if (arrayList!=null){
                            string=arrayList.get(1);
                            uri=mytool.UriFromSenge(string);
                        }
                        simpleDraweeView.setImageURI(uri);
                        Log.i("LOL","response6");



                    }
                });

    }

                    @Override
                    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                            didian();
                        }

                    }
}
