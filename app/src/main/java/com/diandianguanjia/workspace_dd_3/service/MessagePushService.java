package com.diandianguanjia.workspace_dd_3.service;

/**
 * Created by an on 2017/8/28.
 */


import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.bumptech.glide.request.target.Target;
import com.diandianguanjia.workspace_dd_3.utils.Preferences;
import com.diandianguanjia.workspace_dd_3.utils.UtilPreference;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.FeedbackCmdMessage;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.igexin.sdk.message.SetTagCmdMessage;
import com.orhanobut.logger.Logger;

import static android.R.attr.host;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class MessagePushService extends GTIntentService {

    public MessagePushService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        Logger.d(pid+"");
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {


        byte[] payload = msg.getPayload();

        String taskid = msg.getTaskId();
        String messageid =msg.getMessageId();

        String message = new String(payload);
        Logger.d("---taskid-"+taskid+"---messid--"+messageid+"---mess--"+message);

        //---taskid-GT_0828_951547ac336aea49a95c783554b1d406---messid--101434e4-23d-15e278415ba-3113188966---mess--{"key":"方式热热"}
       /* if(payload != null){
            *//**
         * 如果拿到的数据不为空.那么做相关的处理
         * *//*
            message = new String(payload);
            onGetPushMessageListener.getOstfMessage(message);
        }else{
            message = "";
        }*/

    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "------clientid = " + clientid);


        Preferences.saveString("clientid",clientid,context);


        Log.i(TAG, "onReceiveClientId:--- "+Preferences.getString("clientid","",context));



    }



    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        Logger.d("-----"+online+"---");
    }

    //透传消息
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {


        Logger.d("----透传消息---"+cmdMessage.getAppid()+"---"+cmdMessage.getClientId());

    }
}

