package com.dianjiang.hyjipotou2.dianjiangapp.demo;

import android.app.Activity;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.dianjiang.hyjipotou2.dianjiangapp.MainActivity;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
/**
 * Created by user on 2016/1/14.
 */
public class myzhifubao {

    public static final String PARTNER = "2088221710434420";

    public static final String SELLER = "he127654@163.com";

    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJx1fdkRsNByjMLl\n" +
            "XQ25BPzhdTWQmlRAuOWLXSMt7NWD3PMg+KnVN3kyXtoL9e3jTWfKI6k1VxhjcYmN\n" +
            "oxVSMDaqyU7cJ4bkb0tLoPlQRSd7/3kxJE5Y5SAJBE9cRclm+6vaFuZ0Q0H31fNl\n" +
            "esVHDZcEDEVLfhNTyv3criVaK3uFAgMBAAECgYANc0RIXu72XIKhVppzTY6SNsee\n" +
            "vTmnOJEsmph9J7uOd3GNLS5zWzMTpjLlR9Xyh3HmFEiQiVddb18ZViXI9XHTftv9\n" +
            "pzkNTrKeeNfQL0+r+4KRRgxbB+9Y85O+LPQf04aPElD/qMM32h1LTaLWNx0E+jWF\n" +
            "5dibrtr6BbfxcQznAQJBAMyXr3Lc0fJEO4DGYRr+rUydoLZpw4+2LMuA/chK/w85\n" +
            "Rc/5VZSwpsjOVW1x0AQ8Q7busSQqjT4BUH/kV/wRiiUCQQDDxaNDi8Aim+Cm2oxA\n" +
            "jPBctGrDWynOnmptvKxVFEqV/hPY22igMRFDVYZjt7iOvA5cXDAgfM2hKbGxZWvn\n" +
            "CX3hAkAieh1S2et3TBBK7yNGDUze8GE43DHccfKyiJY7voek5R3ioj3NxMcWZ871\n" +
            "loxbalxu42Jumc0bedU6+BvrmnkRAkEAvw4j9Qlvqtbg+TUT5vMkp/RStPBE7qXf\n" +
            "S9TzgdxZpePdzqbhhmQcatAOZUao0HNquGwnc60YqI2eInfORB0ZQQJBAJLp577G\n" +
            "vZRr7hh3/7/bDYaSzFuF6U1fSWbSt7oRpvo6S0zBo1qiIFdDSiWtneunFLxVqzAq\n" +
            "DzYHmUTEUV7rPmI=";



    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    String orderInfo=null;
    Activity activity;
    HashMap<String,String> j=new HashMap<>();
    String jsons;


    public myzhifubao(Activity activity,String subject, String body, String price)
    {

        this.activity=activity;
        orderInfo=getOrderInfo(subject,body,price);
        Log.v("LOLor", orderInfo);

        j.put("orderInfo",orderInfo);
        Gson  gson=new Gson();
        jsons= gson.toJson(j);
        Log.v("LOLjson",jsons);




    }





    public void zhifustart() {

               String sign=null;
                /*try {

                    sign = URLEncoder.encode(response, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/

                String a=SignUtils.sign(orderInfo, RSA_PRIVATE);
        try {
            sign=URLEncoder.encode(a,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();


                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(activity);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);


                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }


              /*  final String payInfo = orderInfo + "&sign=\"" + response+ "\"&" + getSignType();

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {

                        PayTask alipay = new PayTask(activity);

                        String result = alipay.pay(payInfo, true);

                    }
                };


                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

*/


    private String getOrderInfo(String subject, String body, String price) {



        String orderInfo = "partner=" + "\"" + PARTNER + "\"";


        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";


        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";


        orderInfo += "&subject=" + "\"" + subject + "\"";


        orderInfo += "&body=" + "\"" + body + "\"";

        orderInfo += "&total_fee=" + "\"" + price + "\"";


        orderInfo += "&notify_url=" + "\"" + MainActivity.URL+MainActivity.ZHIFUBAOSIGNAPI + "\"";


        orderInfo += "&service=\"mobile.securitypay.pay\"";


        orderInfo += "&payment_type=\"1\"";


        orderInfo += "&_input_charset=\"utf-8\"";


        orderInfo += "&it_b_pay=\"30m\"";




   
        return orderInfo;
    }





    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
