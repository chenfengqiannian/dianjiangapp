package com.dianjiang.hyjipotou2.dianjiangapp.wx;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dianjiang.hyjipotou2.dianjiangapp.DataFragment;
import com.dianjiang.hyjipotou2.dianjiangapp.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hyjipotou2 on 16/6/1.
 */
public class wxPay {
    private IWXAPI api;
    private Context context;
    private Map<String,String> map=new HashMap<>();
    String value;
    PayReq req=new PayReq();
    private String body;
    private String price;



    public wxPay(Context context,String body,String price){
        this.context=context;
        this.body=body;
        this.price=price;
    }

    public void init(){
        //api = WXAPIFactory.createWXAPI(context, "wxb4ba3c02aa476ea1");
        //PayReq req = new PayReq();
        api=WXAPIFactory.createWXAPI(context,"wxec09e44948e6ed5f");
        api.registerApp("wxec09e44948e6ed5f");
        String orderInfo=getOrderInfo(body,price);
        String xml=maptoXml(map);

        xml=xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n", "");


        OkHttpUtils.postString()
                .url("https://api.mch.weixin.qq.com/pay/unifiedorder")
                .content(xml)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.i("LOL","2b");
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.i("LOL",response);

                        try {
                            Document document=DocumentHelper.parseText(response);
                            Element root=document.getRootElement();
                            Element element=root.element("prepay_id");
                            value=element.getText();
                            req.appId = "wxec09e44948e6ed5f";  // 测试用appId
                            req.appId			="wxec09e44948e6ed5f";
                            req.partnerId		="1347947601";
                            req.prepayId		= value;
                            req.nonceStr = map.get("nonce_str");
                            req.timeStamp		= String.valueOf(System.currentTimeMillis()/1000);
                            req.packageValue	= "Sign=WXPay";
                            req.sign			= get2();
                            req.extData			= "app data"; // optional
                            Toast.makeText(context, "正常调起支付", Toast.LENGTH_SHORT).show();
                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                            api.sendReq(req);
                            Log.i("LOL","wocao");
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public static String maptoXml(Map map) {
        Document document = DocumentHelper.createDocument();
        Element nodeElement = document.addElement("xml");
        for (Object obj : map.keySet()) {
            Element keyElement = nodeElement.addElement(String.valueOf(obj));
            //keyElement.addAttribute("label", String.valueOf(obj));
            keyElement.setText(String.valueOf(map.get(obj)));
        }
        return doc2String(document);
    }

    public static String doc2String(Document document) {
        String s = "";
        try {
            // 使用输出流来进行转化
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 使用UTF-8编码
            OutputFormat format = new OutputFormat("   ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            s = out.toString("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }

    private String getOrderInfo(String body, String price) {


        String orderInfo = "appid=wxec09e44948e6ed5f";

        orderInfo += "&attach="+ body;

        //orderInfo += "sign=" + "\"" + subject + "\"";   //

        orderInfo += "&body=" + body;

        orderInfo += "&mch_id=1347947601";

        orderInfo += "&nonce_str=FDEQFEFEDDDSX";

        orderInfo += "&notify_url="+MainActivity.URL+MainActivity.WEIXINSIGNAPI;

        orderInfo += "&out_trade_no="+getOutTradeNo();

        //orderInfo += "&spbill_create_ip=223.104.176.188";

        orderInfo += "&spbill_create_ip="+DataFragment.getIp;


        orderInfo += "&time_expire="+getEndTime();
        orderInfo += "&time_start="+getOutTradeNo();
        orderInfo += "&total_fee="+ price;
        orderInfo += "&trade_type=APP";

        orderInfo += "&key=feifanjiansheyouxiangongsihe1276";

        //orderInfo= mytool.getMD5Str(orderInfo).toUpperCase();
        orderInfo=MD5.getMessageDigest(orderInfo.getBytes()).toUpperCase();

        map.put("appid","wxec09e44948e6ed5f");
        map.put("attach",body);
        map.put("mch_id","1347947601");
        map.put("nonce_str","FDEQFEFEDDDSX");
        map.put("body",body);
        map.put("out_trade_no",getOutTradeNo());
        map.put("total_fee",price);
        //map.put("spbill_create_ip","223.104.176.188");
        map.put("spbill_create_ip",DataFragment.getIp);
        map.put("time_start",getOutTradeNo());
        map.put("time_expire",getEndTime());
        map.put("notify_url",MainActivity.URL+MainActivity.WEIXINSIGNAPI);
        map.put("trade_type","APP");
        map.put("sign",orderInfo);

        return orderInfo;
    }

    public String get2(){

        String orderInfo="";

        orderInfo += "appid=wxec09e44948e6ed5f";

        orderInfo += "&noncestr=FDEQFEFEDDDSX";

        orderInfo += "&package=Sign=WXPay";

        orderInfo += "&partnerid=1347947601";

        orderInfo += "&prepayid="+value;

        orderInfo += "&timestamp="+System.currentTimeMillis()/1000;

        orderInfo +="&key=feifanjiansheyouxiangongsihe1276";

        orderInfo=MD5.getMessageDigest(orderInfo.getBytes()).toUpperCase();

        return orderInfo;
    }


    public static void GetNetIp() {
        DataFragment dataFragment=DataFragment.getInstance();
        /*OkHttpUtils.get()
                .url("http://test.ip138.com/query/")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.i("LOL","cant");
                    }

                    @Override
                    public void onResponse(String response) {
                       Log.i("LOL","ip");
                        Map<String,Object> map;
                        Object obj;
                        Gson gson=new Gson();
                        obj=gson.fromJson(response,new TypeToken<Object>(){}.getType());
                        map= (Map<String, Object>) obj;
                        DataFragment.getIp=map.get("ip").toString();
                    }
                });*/
        DataFragment.getIp="117.25.13.123";
    }


    private String getEndTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        Date now = new Date();
        Date date=new Date(now.getTime()+900000);
        String key = format.format(date);
        return key;
    }

    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        return key;
    }


}
