package com.dianjiang.hyjipotou2.dianjiangapp.wx;

import android.content.Context;
import android.content.Intent;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by hyjipotou2 on 16/6/16.
 */
public class WXfenxiang  {

    WXTextObject textObject=new WXTextObject();
    WXMediaMessage msg=new WXMediaMessage();
    SendMessageToWX.Req req=new SendMessageToWX.Req();
    IWXAPI api;
    String text="我正在使用点匠APP";
    public void init(Context context){
        api= WXAPIFactory.createWXAPI(context, "wxec09e44948e6ed5f");
        //api.registerApp("wxec09e44948e6ed5f");
        textObject.text=text;

        msg.mediaObject=textObject;
        msg.description=text;

        req.transaction=buildTransaction("text");
        req.message=msg;
        req.scene=SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
