package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dianjiang.hyjipotou2.dianjiangapp.demo.myzhifubao;
import com.dianjiang.hyjipotou2.dianjiangapp.wx.wxPay;

/**
 * Created by hyjipotou2 on 16/6/4.
 */
public class dingdanActivity extends Activity {

    private TextView jine1;
    private TextView jine2;
    private TextView phone;
    private RelativeLayout queren;
    private ImageButton fanhui;
    private DataFragment dataFragment;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dingdan);

        init();

        setData();

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] zhifu={"支付宝","微信支付"};
                AlertDialog.Builder builder = new AlertDialog.Builder(dingdanActivity.this)
                        .setTitle("请选择支付方式")
                        .setItems(zhifu, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0){
                                    new myzhifubao(dingdanActivity.this,String.valueOf((int)(double)Double.parseDouble(dataFragment.mprocessItemBean.processnumber)),phone.getText().toString(),price+"").zhifustart();
                                }else {
                                    new wxPay(dingdanActivity.this,String.valueOf((int)(double)Double.parseDouble(dataFragment.mprocessItemBean.processnumber)),(int)(price*100.0)+"").init();
                                }
                            }
                        });
                builder.create().show();
            }
        });
    }

    public void init(){
        jine1= (TextView) findViewById(R.id.jine1);
        jine2=(TextView)findViewById(R.id.jine2);
        phone= (TextView) findViewById(R.id.phone);
        queren= (RelativeLayout) findViewById(R.id.zhifu);
        fanhui=(ImageButton)findViewById(R.id.dingdan_fanhui);
        dataFragment=DataFragment.getInstance();
    }

    public void setData(){
        Intent intent=getIntent();
        price=intent.getDoubleExtra("jiage",0.0);
        jine1.setText(price+"");
        jine2.setText(price+"");
        phone.setText(dataFragment.user_datamap.get("phone").toString());
    }
}
