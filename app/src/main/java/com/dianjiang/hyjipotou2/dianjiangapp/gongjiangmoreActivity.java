package com.dianjiang.hyjipotou2.dianjiangapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class gongjiangmoreActivity extends AppCompatActivity implements View.OnClickListener{
SimpleDraweeView touxiang;
    EditText Textname;
    TextView gongzhong;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        Intent intent=getIntent();
        String dataString=intent.getStringExtra("leixing");
        if(dataString.equalsIgnoreCase("ketang")) {
            init(0);
        }
        if(dataString.equalsIgnoreCase("gerenxinxi"))
        {init(1);}

    }
void init(int index)
{dataFragment=DataFragment.getInstance();
    if(index==0)
    {

        setContentView(R.layout.fragment_blank);




    }
    if(index==1)
    {setContentView(R.layout.gongjiangxinxi);
        Textname=(EditText)findViewById(R.id.dianjiang_name);
        gongzhong=(TextView)findViewById(R.id.dianjiang_gongzhong);
        gongsi=(EditText)findViewById(R.id.dianjiang_gongsi);
        gongzuodi=(EditText)findViewById(R.id.dianjiang_gongzuodi);
        biaoqian=(EditText)findViewById(R.id.dianjiang_biaoqian);
        zhengshu1=(SimpleDraweeView)findViewById(R.id.dianjiang_zheng);
        zhengshu2=(SimpleDraweeView)findViewById(R.id.dianjiang_zheng2);

        fanhui=(ImageButton)findViewById(R.id.dianjiang_fanhui);
        baocun=(RelativeLayout)findViewById(R.id.yaoqing);





        Textname.setText((String)dataFragment.user_datamap.get("nichang"));
        gongsi.setText((String)dataFragment.user_datamap.get("gongsi"));
        gongzuodi.setText((String)dataFragment.user_datamap.get("gongzuodi"));
        biaoqian.setText((String) dataFragment.user_datamap.get("biaoqian"));
        ziwojieshao.setText((String) dataFragment.user_datamap.get("ziwojieshao"));

        ArrayList<String> zhsngshudata=(ArrayList < String >)dataFragment.user_datamap.get("zhengshu");
        if(zhsngshudata.size()>=2) {
            zhengshu1.setImageURI(mytool.UriFromSenge(zhsngshudata.get(zhsngshudata.size() - 1)));
            zhengshu2.setImageURI(mytool.UriFromSenge(zhsngshudata.get(zhsngshudata.size() - 2)));
        }
        if(zhsngshudata.size()==1)
        {

            zhengshu1.setImageURI(mytool.UriFromSenge(zhsngshudata.get(0)));
        }





        gongzhong.setOnClickListener(this);
        zhengshu1.setOnClickListener(this);
        zhengshu2.setOnClickListener(this);
       // bianji.setOnClickListener(this);
        fanhui.setOnClickListener(this);
        baocun.setOnClickListener(this);

    }
}

    @Override
    public void onClick(View v) {
        
if(v==baocun)
{   final gongjiangmoreActivity activity=this;
    Gson gson=new Gson();
    HashMap<String,String> data=new HashMap<>();
    data.put("nichang",Textname.getText().toString());
    data.put("gongsi",gongsi.getText().toString());
    data.put("gongzuodi",gongzuodi.getText().toString());
    data.put("biaoqian",biaoqian.getText().toString());
    data.put("ziwojieshao",ziwojieshao.getText().toString());
    OkHttpUtils.postString().url(MainActivity.URL+MainActivity.USERAPI).content(gson.toJson(data)).build().execute(new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(activity,"上传失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Toast.makeText(activity,"上传成功",Toast.LENGTH_SHORT).show();
        }
    });



}



    }
}
