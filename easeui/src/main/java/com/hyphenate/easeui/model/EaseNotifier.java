/************************************************************
 *  * Hyphenate CONFIDENTIAL 
 * __________________ 
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved. 
 *
 * NOTICE: All information contained herein is, and remains 
 * the property of Hyphenate Inc.
 * Dissemination of this information or reproduction of this material 
 * is strictly forbidden unless prior written permission is obtained
 * from Hyphenate Inc.
 */
package com.hyphenate.easeui.model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseSettingsProvider;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.EasyUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * new message notifier class
 * <p>
 * this class is subject to be inherited and implement the relative APIs
 */
public class EaseNotifier {
    private final static String TAG = "notify";
    Ringtone ringtone = null;

    protected final static String[] msg_eng = {"sent a message", "sent a picture", "sent a voice",
            "sent location message", "sent a video", "sent a file", "%1 contacts sent %2 messages"
    };
    protected final static String[] msg_ch = {"发来一条消息", "发来一张图片", "发来一段语音", "发来位置信息", "发来一个视频", "发来一个文件",
            "%1个联系人发来%2条消息"
    };

    protected static int notifyID = 0525; // start notification id
    protected static int foregroundNotifyID = 0555;

    protected NotificationManager notificationManager = null;

    protected HashSet<String> fromUsers = new HashSet<String>();
    protected int notificationNum = 0;

    protected Context appContext;
    protected String packageName;
    protected String[] msgs;
    protected long lastNotifiyTime;
    protected AudioManager audioManager;
    protected Vibrator vibrator;
    protected EaseNotificationInfoProvider notificationInfoProvider;

    public EaseNotifier() {
    }

    /**
     * this function can be override
     *
     * @param context
     * @return
     */
    public EaseNotifier init(Context context) {
        appContext = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        packageName = appContext.getApplicationInfo().packageName;
        if (Locale.getDefault().getLanguage().equals("zh")) {
            msgs = msg_ch;
        } else {
            msgs = msg_eng;
        }

        audioManager = (AudioManager) appContext.getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) appContext.getSystemService(Context.VIBRATOR_SERVICE);

        return this;
    }

    /**
     * this function can be override
     */
    public void reset() {
        resetNotificationCount();
        cancelNotificaton();
    }

    void resetNotificationCount() {
        notificationNum = 0;
        fromUsers.clear();
    }

    void cancelNotificaton() {
        if (notificationManager != null)
            notificationManager.cancel(notifyID);
    }

    /**
     * handle the new message
     * this function can be override
     *
     * @param message
     */
    public synchronized void onNewMsg(EMMessage message) {
        if (EaseCommonUtils.isSilentMessage(message)) {
            return;
        }
        EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
        if (!settingsProvider.isMsgNotifyAllowed(message)) {
            return;
        }

        // check if app running background
        if (!EasyUtils.isAppRunningForeground(appContext)) {
            EMLog.d(TAG, "app is running in backgroud");
            sendNotification(message, false);
        } else {
            sendNotification(message, true);

        }

        vibrateAndPlayTone(message);
    }

    public synchronized void onNewMesg(List<EMMessage> messages) {
        if (EaseCommonUtils.isSilentMessage(messages.get(messages.size() - 1))) {
            return;
        }
        EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
        if (!settingsProvider.isMsgNotifyAllowed(null)) {
            return;
        }
        // check if app running background
        if (!EasyUtils.isAppRunningForeground(appContext)) {
            EMLog.d(TAG, "app is running in backgroud");
            sendNotification(messages, false);
        } else {
            sendNotification(messages, true);
        }
        vibrateAndPlayTone(messages.get(messages.size() - 1));
    }

    /**
     * send it to notification bar
     * This can be override by subclass to provide customer implementation
     *
     * @param messages
     * @param isForeground
     */
    protected void sendNotification(List<EMMessage> messages, boolean isForeground) {
        for (EMMessage message : messages) {
            if (!isForeground) {
                notificationNum++;
                fromUsers.add(message.getFrom());
            }
        }
        sendNotification(messages.get(messages.size() - 1), isForeground, false);
    }

    protected void sendNotification(EMMessage message, boolean isForeground) {
        sendNotification(message, isForeground, true);
    }

    /**
     * send it to notification bar
     * This can be override by subclass to provide customer implementation
     *
     * @param message
     */
    protected void sendNotification(EMMessage message, boolean isForeground, boolean numIncrease) {
        String username = message.getFrom();
        try {
            //String notifyText = username + " ";
            String notifyText = "";
            switch (message.getType()) {
                case TXT:
                    // notifyText += msgs[0];
                    EMTextMessageBody emTextMessageBody = (EMTextMessageBody) message.getBody();
                    if (emTextMessageBody != null) {
                        notifyText += emTextMessageBody.getMessage();
                        Log.e("zy", "msg body = " + emTextMessageBody.getMessage());
                    }
                    break;
                case IMAGE:
                    // notifyText += msgs[1];
                    EMImageMessageBody emImageMessageBody = (EMImageMessageBody) message.getBody();
                    if (emImageMessageBody != null) {
                        notifyText += emImageMessageBody.getThumbnailUrl();
                        Log.e("zy", "msg imageFileName = " + emImageMessageBody.getFileName()
                                + "/" + emImageMessageBody.getThumbnailUrl() + "/" + emImageMessageBody.describeContents());
                    }

                    break;
                case VOICE:

                    notifyText += msgs[2];
                    break;
                case LOCATION:
                    notifyText += msgs[3];
                    break;
                case VIDEO:
                    notifyText += msgs[4];
                    break;
                case FILE:
                    notifyText += msgs[5];
                    break;
            }

            PackageManager packageManager = appContext.getPackageManager();
            String appname = (String) packageManager.getApplicationLabel(appContext.getApplicationInfo());

            // notification title
            String contentTitle = appname;
            if (notificationInfoProvider != null) {
                String customNotifyText = notificationInfoProvider.getDisplayedText(message);
                String customCotentTitle = notificationInfoProvider.getTitle(message);
                if (customNotifyText != null) {
//                    notifyText = customNotifyText;
                }

                if (customCotentTitle != null) {
                    contentTitle = customCotentTitle;
                }
            }

            // create and send notificaiton
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(appContext)
                    .setSmallIcon(appContext.getApplicationInfo().icon)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true);

            Intent msgIntent = appContext.getPackageManager().getLaunchIntentForPackage(packageName);
            if (notificationInfoProvider != null) {
                Intent intent = notificationInfoProvider.getLaunchIntent(message);
                if (intent != null) {
                    msgIntent = intent;
                }
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(appContext, notifyID, msgIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (numIncrease) {
                // prepare latest event info section
                if (!isForeground) {
                    notificationNum++;
                    fromUsers.add(message.getFrom());
                }
            }

            int fromUsersNum = fromUsers.size();
            String summaryBody = msgs[6].replaceFirst("%1", Integer.toString(fromUsersNum)).replaceFirst("%2", Integer.toString(notificationNum));

            if (notificationInfoProvider != null) {
                // lastest text
                String customSummaryBody = notificationInfoProvider.getLatestText(message, fromUsersNum, notificationNum);
                if (customSummaryBody != null) {
                    summaryBody = customSummaryBody;
                }

                // small icon
                int smallIcon = notificationInfoProvider.getSmallIcon(message);
                if (smallIcon != 0) {
//                    mBuilder.setSmallIcon(smallIcon);
//                    mBuilder.setSmallIcon(smallIcon);
                    mBuilder.setSmallIcon(smallIcon);
                } else {
                    mBuilder.setSmallIcon(R.drawable.ease_open_icon);
                }
            }

            mBuilder.setContentTitle(contentTitle);
//            mBuilder.setTicker(notifyText);
            mBuilder.setContentText(notifyText);
//            mBuilder.setContentIntent(pendingIntent);
            // mBuilder.setNumber(notificationNum);
            Notification notification = mBuilder.build();


            Log.e("zy", "msg lllllllllllllllllll");


            int randomId = (int)System.currentTimeMillis();
            if (isForeground) {
                // notificationManager.notify(foregroundNotifyID, notification);
                // notificationManager.cancel(foregroundNotifyID);
            } else {
                //     notificationManager.notify(notifyID, notification);
            }
            notificationManager.notify(randomId, notification);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNotficationDemo() {
        /**
         *  创建通知栏管理工具
         */

        NotificationManager notificationManager = (NotificationManager) appContext.getSystemService
                (Context.NOTIFICATION_SERVICE);

        /**
         *  实例化通知栏构造器
         */

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                appContext, "111111111");

        /**
         *  设置Builder
         */
        //设置标题
        mBuilder.setContentTitle("我是标题")
                //设置内容
                .setContentText("我是内容")
                //设置小图标
                .setSmallIcon(R.drawable.ease_open_icon)
                //设置通知时间
                .setWhen(System.currentTimeMillis())
                //首次进入时显示效果
                .setTicker("我是测试内容")
                //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
                .setDefaults(Notification.DEFAULT_SOUND);

        notificationManager.notify(10, mBuilder.build());
    }


    /**
     * vibrate and  play tone
     */
    public void vibrateAndPlayTone(EMMessage message) {
        if (message != null) {
            if (EaseCommonUtils.isSilentMessage(message)) {
                return;
            }
        }

        if (System.currentTimeMillis() - lastNotifiyTime < 1000) {
            // received new messages within 2 seconds, skip play ringtone
            return;
        }

        try {
            lastNotifiyTime = System.currentTimeMillis();

            // check if in silent mode
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                EMLog.e(TAG, "in slient mode now");
                return;
            }
            EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
            if (settingsProvider.isMsgVibrateAllowed(message)) {
                long[] pattern = new long[]{0, 180, 80, 120};
                vibrator.vibrate(pattern, -1);
            }

            if (settingsProvider.isMsgSoundAllowed(message)) {
                if (ringtone == null) {
                    Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    ringtone = RingtoneManager.getRingtone(appContext, notificationUri);
                    if (ringtone == null) {
                        EMLog.d(TAG, "cant find ringtone at:" + notificationUri.getPath());
                        return;
                    }
                }

                if (!ringtone.isPlaying()) {
                    String vendor = Build.MANUFACTURER;

                    ringtone.play();
                    // for samsung S3, we meet a bug that the phone will
                    // continue ringtone without stop
                    // so add below special handler to stop it after 3s if
                    // needed
                    if (vendor != null && vendor.toLowerCase().contains("samsung")) {
                        Thread ctlThread = new Thread() {
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                    if (ringtone.isPlaying()) {
                                        ringtone.stop();
                                    }
                                } catch (Exception e) {
                                }
                            }
                        };
                        ctlThread.run();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * set notification info Provider
     *
     * @param provider
     */
    public void setNotificationInfoProvider(EaseNotificationInfoProvider provider) {
        notificationInfoProvider = provider;
    }

    public interface EaseNotificationInfoProvider {
        /**
         * set the notification content, such as "you received a new image from xxx"
         *
         * @param message
         * @return null-will use the default text
         */
        String getDisplayedText(EMMessage message);

        /**
         * set the notification content: such as "you received 5 message from 2 contacts"
         *
         * @param message
         * @param fromUsersNum- number of message sender
         * @param messageNum    -number of messages
         * @return null-will use the default text
         */
        String getLatestText(EMMessage message, int fromUsersNum, int messageNum);

        /**
         * 设置notification标题
         *
         * @param message
         * @return null- will use the default text
         */
        String getTitle(EMMessage message);

        /**
         * set the small icon
         *
         * @param message
         * @return 0- will use the default icon
         */
        int getSmallIcon(EMMessage message);

        /**
         * set the intent when notification is pressed
         *
         * @param message
         * @return null- will use the default icon
         */
        Intent getLaunchIntent(EMMessage message);
    }
}
