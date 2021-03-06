package com.coin.b8.wxapi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.coin.b8.R;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.MyToast;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by zhangyi on 2018/6/19.
 */
public class WXShare {
    private static final int THUMB_SIZE = 150;
    public static final String APP_ID = "wx8c9da09f334858dd";
    public static final String APP_SECRET = "3ff01be2394a476a7d6869b83e766204";
    public static final String ACTION_SHARE_RESPONSE = "action_wx_share_response";
    public static final String EXTRA_RESULT = "result";

    private final Context context;
    private final IWXAPI api;
    private OnResponseListener listener;
    private ResponseReceiver receiver;


    //微博
    private WbShareHandler mWbShareHandler;
    public static final String WEIBO_APP_KEY = "3713372297";
    public static final String WEIBO_APP_SECRET = "60f7402c338262df9196a31971707e1b";
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    private MyToast mMyToast;

    public WXShare(Context context, Activity activity) {
        api = WXAPIFactory.createWXAPI(context, APP_ID);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mMyToast = new MyToast(layoutInflater);

        WbSdk.install(context,new AuthInfo(context, WEIBO_APP_KEY,REDIRECT_URL, SCOPE));//创建微博API接口类对象
        if(activity != null){
            mWbShareHandler = new WbShareHandler(activity);
            mWbShareHandler.registerApp();
        }
        this.context = context;
    }

    public WXShare register() {
        // 微信分享
        api.registerApp(APP_ID);
        receiver = new ResponseReceiver();
        IntentFilter filter = new IntentFilter(ACTION_SHARE_RESPONSE);
        context.registerReceiver(receiver, filter);
        return this;
    }

    public void unregister() {
        try {
            api.unregisterApp();
            context.unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WXShare share(String text) {
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        //        msg.title = "Will be ignored";
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        boolean result = api.sendReq(req);
//        Logger.i("text shared: " + result);
        return this;
    }




    /**
     *
     * @param type 0 朋友，1 、朋友圈 2、微博
     * @param resId
     * @return
     */
    public WXShare shareImage(int type,int resId) {
        switch (type){
            case 0:
            case 1:{
                try {
                    if(!api.isWXAppInstalled()){
                        mMyToast.showToast("微信未安装");
                        return this;
                    }
                    Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resId);
                    WXImageObject imgObj = new WXImageObject(bmp);
                    WXMediaMessage msg = new WXMediaMessage();
                    msg.mediaObject = imgObj;
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
                    bmp.recycle();
                    msg.thumbData = CommonUtils.bmpToByteArray(thumbBmp, true);
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("img");
                    req.message = msg;
                    if(type == 0){
                        req.scene = SendMessageToWX.Req.WXSceneSession;
                    }else {
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                    }
                    api.sendReq(req);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
                break;
            case 2:{
                try {
                    Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resId);
                    ImageObject imageObject = new ImageObject();
                    imageObject.setImageObject(bmp);
                    WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                    weiboMessage.imageObject = imageObject;
                    if(mWbShareHandler != null){
                        mWbShareHandler.shareMessage(weiboMessage, false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
                break;
        }

        return this;
    }

    public WXShare shareImage(int type,Bitmap bmp) {
        switch (type){
            case 0:
            case 1:{
                try {
                    if(!api.isWXAppInstalled()){
                        mMyToast.showToast("微信未安装");
                        return this;
                    }
                    if(bmp == null){
                        return this;
                    }
                    WXImageObject imgObj = new WXImageObject(bmp);
                    WXMediaMessage msg = new WXMediaMessage();
                    msg.mediaObject = imgObj;
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
                    bmp.recycle();
                    msg.thumbData = CommonUtils.bmpToByteArray(thumbBmp, true);
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("img");
                    req.message = msg;
                    if(type == 0){
                        req.scene = SendMessageToWX.Req.WXSceneSession;
                    }else {
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                    }
                    api.sendReq(req);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
                break;
            case 2:{
                try {
                    if(bmp == null){
                        return this;
                    }

                    ImageObject imageObject = new ImageObject();
                    imageObject.setImageObject(bmp);

                    WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                    weiboMessage.imageObject = imageObject;
                    if(mWbShareHandler != null){
                        mWbShareHandler.shareMessage(weiboMessage, false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
                break;
        }

        return this;
    }

    public WXShare shareWeb(int type,String title,String content,String webUrl) {

        switch (type){
            case 0:
            case 1:{
                try {
                    if(!api.isWXAppInstalled()){
                        mMyToast.showToast("微信未安装");
                        return this;
                    }
                    WXWebpageObject webpage = new WXWebpageObject();
                    webpage.webpageUrl = webUrl;
                    WXMediaMessage msg = new WXMediaMessage(webpage);
                    msg.title = title;
                    msg.description = content;
                    Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.share_smal_icon);
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                    bmp.recycle();
                    msg.thumbData = CommonUtils.bmpToByteArray(thumbBmp, true);

                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("webpage");
                    req.message = msg;
                    if(type == 0){
                        req.scene = SendMessageToWX.Req.WXSceneSession;
                    }else {
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                    }
                    api.sendReq(req);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
                break;
            case 2:{
                try {
                    TextObject textObject = new TextObject();
                    textObject.text = content;
                    textObject.title = title;
                    textObject.actionUrl = webUrl;


                    Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.share_smal_icon);
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                    bmp.recycle();
                    textObject.setThumbImage(thumbBmp);
                    WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                    weiboMessage.textObject = textObject;

                    WebpageObject mediaObject = new WebpageObject();
                    mediaObject.title = title;
                    mediaObject.description = content;
                    mediaObject.setThumbImage(thumbBmp);
                    mediaObject.actionUrl = webUrl;
                    weiboMessage.mediaObject = mediaObject;
                    if(mWbShareHandler != null){
                        mWbShareHandler.shareMessage(weiboMessage, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
                break;
        }

        return this;
    }




    public IWXAPI getApi() {
        return api;
    }

    public void setListener(OnResponseListener listener) {
        this.listener = listener;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Response response = intent.getParcelableExtra(EXTRA_RESULT);
//            Log.d("type: " + response.getType());
//            Log.d("errCode: " + response.errCode);
            String result;
            if (listener != null) {
                if (response.errCode == BaseResp.ErrCode.ERR_OK) {
                    listener.onSuccess();
                } else if (response.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                    listener.onCancel();
                } else {
                    switch (response.errCode) {
                        case BaseResp.ErrCode.ERR_AUTH_DENIED:
                            result = "发送被拒绝";
                            break;
                        case BaseResp.ErrCode.ERR_UNSUPPORT:
                            result = "不支持错误";
                            break;
                        default:
                            result = "发送返回";
                            break;
                    }
                    listener.onFail(result);
                }
            }
        }
    }

    public static class Response extends BaseResp implements Parcelable {

        public int errCode;
        public String errStr;
        public String transaction;
        public String openId;

        private int type;
        private boolean checkResult;

        public Response(BaseResp baseResp) {
            errCode = baseResp.errCode;
            errStr = baseResp.errStr;
            transaction = baseResp.transaction;
            openId = baseResp.openId;
            type = baseResp.getType();
            checkResult = baseResp.checkArgs();
        }

        @Override
        public int getType() {
            return type;
        }

        @Override
        public boolean checkArgs() {
            return checkResult;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.errCode);
            dest.writeString(this.errStr);
            dest.writeString(this.transaction);
            dest.writeString(this.openId);
            dest.writeInt(this.type);
            dest.writeByte(this.checkResult ? (byte) 1 : (byte) 0);
        }

        protected Response(Parcel in) {
            this.errCode = in.readInt();
            this.errStr = in.readString();
            this.transaction = in.readString();
            this.openId = in.readString();
            this.type = in.readInt();
            this.checkResult = in.readByte() != 0;
        }

        public static final Creator<Response> CREATOR = new Creator<Response>() {
            @Override
            public Response createFromParcel(Parcel source) {
                return new Response(source);
            }

            @Override
            public Response[] newArray(int size) {
                return new Response[size];
            }
        };
    }

}
