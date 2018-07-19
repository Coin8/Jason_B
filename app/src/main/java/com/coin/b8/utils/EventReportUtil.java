package com.coin.b8.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created by zhangyi on 2018/7/19.
 */
public class EventReportUtil {

    /**
     * 首页tab 点击事件
     * @param context
     * @param type
     */
    public static void mainTabClick(Context context,int type){
        switch (type){
            case 0:
                CommonUtils.umengReport(context,"main_tab_first_click");
                break;
            case 1:
                CommonUtils.umengReport(context,"main_tab_second_click");
                break;
            case 2:
                CommonUtils.umengReport(context,"main_tab_third_click");
                break;
            case 3:
                CommonUtils.umengReport(context,"main_tab_fourth_click");
                break;
        }
    }

    /**
     * 行情tab点击
     * @param context
     * @param type
     */
    public static void marketTabClick(Context context,int type){
        switch (type){
            case 0:
                CommonUtils.umengReport(context,"hq_top_optional_click");
                break;
            case 1:
                CommonUtils.umengReport(context,"hq_top_market_value_click");
                break;
            case 2:
                CommonUtils.umengReport(context,"hq_top_fire_coin_click");
                break;
        }
    }


    public static void marketListItemReport(Context context,String trade_name,String list_name){
        if(context == null){
            return;
        }
        HashMap<String,String> map = CommonUtils.getCommonPara();
        map.put("trade_name",trade_name);
        map.put("list_name",list_name);
        MobclickAgent.onEvent(context, "hq_list_click", map);
    }

    /**
     * 动态页面 tab 点击
     * @param context
     * @param type
     */
    public static void dynamicTabClick(Context context,int type){
        switch (type){
            case 0:
                CommonUtils.umengReport(context,"dynamic_top_news_click");
                break;
            case 1:
                CommonUtils.umengReport(context,"hq_top_market_value_click");
                break;
        }
    }

    public static void selectCoinItemClick(Context context,String coin_subject_name){
        if(context == null){
            return;
        }
        HashMap<String,String> map = CommonUtils.getCommonPara();
        map.put("coin_subject_name",coin_subject_name);
        MobclickAgent.onEvent(context, "coin_list_click", map);
    }

    public static void selectCoinSubjectItemClick(Context context,String coin_subject_name,String trade_name){
        if(context == null){
            return;
        }
        HashMap<String,String> map = CommonUtils.getCommonPara();
        map.put("coin_subject_name",coin_subject_name);
        map.put("trade_name",trade_name);
        MobclickAgent.onEvent(context, "coin_subject_list_click", map);
    }


}
