package com.coin.b8.utils;

import android.app.Activity;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/8/15.
 */
public class ActivityManagerUtil {

    private static List<WeakReference<Activity>> activityList;

    public static synchronized void addActivityToList(Activity activity) {
        if (activityList == null) {
            activityList = new ArrayList<WeakReference<Activity>>();
        }
        activityList.add(new WeakReference<Activity>(activity));
    }

    public static synchronized void removeActivityFromList(Activity activity) {
        if(activity != null) {
            if(activityList != null) {
                for(WeakReference<Activity> activityWeakReference : activityList) {
                    if(activity.equals(activityWeakReference.get())) {
                        activityList.remove(activityWeakReference);
                        break;
                    }
                }
            }
        }
    }

    public static synchronized void clearActivityList() {
        if (activityList != null) {
            for (int i = activityList.size() - 1; i >= 0; i--) {
                Activity tmpActivity = activityList.get(i).get();
                if (tmpActivity != null){
                    tmpActivity.finish();
                }
            }
            activityList.clear();
        }
    }

    public static synchronized Activity getTopActivity() {
        if(activityList != null && activityList.size() > 0) {
            return activityList.get(activityList.size() -1).get();
        }
        return null;
    }


    public static synchronized Activity getSecTopActivity() {
        if(activityList != null && activityList.size() > 1) {
            return activityList.get(activityList.size() -2).get();
        }
        return null;
    }

    public static synchronized List<WeakReference<Activity>> getActivityList(){
        return activityList;
    }

}
