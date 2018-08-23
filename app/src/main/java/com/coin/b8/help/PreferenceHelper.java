package com.coin.b8.help;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.coin.b8.model.LoginResponseInfo;
import com.coin.b8.model.UserInfoResponse;

/**
 * Created by zhangyi on 2018/7/3.
 */
public class PreferenceHelper {
    private static final String mYaoWenName = "Preference_YaoWen_B8";
    private static final String mName = "Preference_B8";
    private static final String KEY_IS_LOGIN = "new_is_login";
    private static final String KEY_FIST_START = "new_first_start";
    private static final String KEY_MONEY_DISPLAY = "new_money_display";
    private static final String KEY_UID = "new_uid";
    private static final String KEY_IMEI = "new_imei";
    private static final String KEY_TOKEN = "new_token";
    private static final String KEY_EASE_NAME = "new_ease_name";
    private static final String KEY_EASE_PASSWORD = "new_ease_password";

    private static final String KEY_SEX = "new_sex";
    private static final String KEY_HEAD_ICON = "new_head_icon";
    private static final String KEY_NICKNAME = "new_nickname";

    private static final String DEFAULT_VALUE = "";

    private static boolean mIsLogin = false;
    private static String mUid = "";
    private static String mImei = "";
    private static String mToken = "";
    private static String mNickName = "";
    private static String mEaseName = "";
    private static String mEasePassword = "";
    private static int mSex = 0;
    private static String mHeadIcon = "";

    public static boolean setYaoWenValue(Context context, String key, boolean value){
        SharedPreferences.Editor editor = context.getSharedPreferences(mYaoWenName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getYaoWenValue(Context context,String key){
        return context.getSharedPreferences(mYaoWenName,Context.MODE_PRIVATE).getBoolean(key,false);
    }

    private static boolean setValue(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences(mName, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    private static String getValue(Context context,String key){
        return context.getSharedPreferences(mName,Context.MODE_PRIVATE).getString(key,DEFAULT_VALUE);
    }

    private static boolean setValue(Context context,String key,int value){
        SharedPreferences.Editor editor = context.getSharedPreferences(mName, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        return editor.commit();
    }
    private static int getValue(Context context,String key,int value){
        return context.getSharedPreferences(mName,Context.MODE_PRIVATE).getInt(key,value);
    }

    private static boolean setValue(Context context,String key,boolean value){
        SharedPreferences.Editor editor = context.getSharedPreferences(mName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }
    private static  boolean getValue(Context context,String key,boolean value){
        return context.getSharedPreferences(mName,Context.MODE_PRIVATE).getBoolean(key,value);
    }

    public static boolean setFirstStart(Context context,boolean value){
        return setValue(context,KEY_FIST_START,value);
    }

    public static boolean getFirstStart(Context context){
        return getValue(context,KEY_FIST_START,true);
    }

    public static boolean setMoneyDisplay(Context context,boolean value){
        return setValue(context,KEY_MONEY_DISPLAY,value);
    }

    public static boolean getMoneyDisplay(Context context){
        return getValue(context,KEY_MONEY_DISPLAY,true);
    }

    public static boolean setUid(Context context,String uid){
        mUid = uid;
        return setValue(context,KEY_UID,uid);
    }

    public static String getUid(Context context){
        if(!TextUtils.isEmpty(mUid)){
            return mUid;
        }
        return getValue(context,KEY_UID);
    }

    public static boolean setIMEI(Context context,String imei){
        mImei = imei;
        return setValue(context,KEY_IMEI,imei);
    }

    public static String getIMEI(Context context){
        if(!TextUtils.isEmpty(mImei)){
            return mImei;
        }
        return getValue(context,KEY_IMEI);
    }

    public static boolean setToken(Context context,String token){
        mToken = token;
        return setValue(context,KEY_TOKEN,token);
    }

    public static String getToken(Context context){
        if(!TextUtils.isEmpty(mToken)){
            return mToken;
        }
        return getValue(context,KEY_TOKEN);
    }

    public static boolean setNickName(Context context,String nickName){
        mNickName = nickName;
        return setValue(context,KEY_NICKNAME,nickName);
    }

    public static String getNickName(Context context){
        if(!TextUtils.isEmpty(mNickName)){
            return mNickName;
        }
        return getValue(context,KEY_NICKNAME);
    }

    public static boolean setSex(Context context,int sex){
        mSex = sex;
        return setValue(context,KEY_SEX,sex);
    }

    public static int getSex(Context context){
        if(mSex != 0){
            return mSex;
        }
        return getValue(context,KEY_SEX,1);
    }

    public static boolean setHeadIcon(Context context,String headIcon){
        mHeadIcon = headIcon;
        return setValue(context,KEY_HEAD_ICON,headIcon);
    }

    public static String getHeadIcon(Context context){
        if(!TextUtils.isEmpty(mHeadIcon)){
            return mHeadIcon;
        }
        return getValue(context,KEY_HEAD_ICON);
    }


    /**
     *
     * @param context
     * @param easeName  环信用户名
     * @return
     */
    public static boolean setEaseName(Context context,String easeName){
        mEaseName = easeName;
        return setValue(context,KEY_EASE_NAME,easeName);
    }

    /**
     *
     * @param context
     * @return  环信用户名
     */
    public static String getEaseName(Context context){
        if(!TextUtils.isEmpty(mEaseName)){
            return mEaseName;
        }
        return getValue(context,KEY_EASE_NAME);
    }

    /**
     *
     * @param context
     * @param easePassword  环信密码
     * @return
     */
    public static boolean setEasePassword(Context context,String easePassword){
        mEasePassword = easePassword;
        return setValue(context,KEY_EASE_PASSWORD,easePassword);
    }

    /**
     *
     * @param context
     * @return  环信密码
     */
    public static String getEasePassword(Context context){
        if(!TextUtils.isEmpty(mEasePassword)){
            return mEasePassword;
        }
        return getValue(context,KEY_EASE_PASSWORD);
    }


    public static void saveLoinInfo(Context context,LoginResponseInfo loginResponseInfo){
        if(loginResponseInfo == null || loginResponseInfo.getData() == null){
            return;
        }
        setUid(context,String.valueOf(loginResponseInfo.getData().getUid()));
        setEaseName(context,loginResponseInfo.getData().getEasename());
        setEasePassword(context,loginResponseInfo.getData().getPassword());
        setToken(context,loginResponseInfo.getData().getToken());
    }

    public static void clearUserInfo(Context context){
        setSex(context,1);
        setNickName(context,"");
        setHeadIcon(context,"");
    }


    public static void saveUserInfo(Context context , UserInfoResponse userInfoResponse){
        if(userInfoResponse == null || userInfoResponse.getData() == null){
            return;
        }
        setSex(context,userInfoResponse.getData().getGender());
        setNickName(context,userInfoResponse.getData().getName());
        setHeadIcon(context,userInfoResponse.getData().getIcon());
    }

    public static boolean getIsLogin(Context context){
        if(TextUtils.isEmpty(getToken(context))){
            return false;
        }
        return true;
    }

}
