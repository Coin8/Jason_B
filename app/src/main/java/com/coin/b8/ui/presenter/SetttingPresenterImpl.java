package com.coin.b8.ui.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;

import com.coin.b8.app.B8Application;
import com.coin.b8.ui.iView.ISettingView;
import com.coin.b8.utils.FileUtils;

import java.io.File;
import java.util.Arrays;

/**
 * Created by zhangyi on 2018/6/8.
 */
public class SetttingPresenterImpl {
    private ISettingView mView;
    private Context mContext;

    public SetttingPresenterImpl(Context context,ISettingView mainView) {
        this.mView = mainView;
        this.mContext = context;
    }


    public void initCache() {
        new GetDiskCacheSizeTask().execute(new File(mContext.getCacheDir().getAbsolutePath()));
    }

    public void cleanCache() {
//        mView.showBaseProgressDialog("正在清理缓存...");
        //清楚硬盘缓存,需要后台线程清楚
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileUtils.clearDir(mContext.getCacheDir().getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                B8Application.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        //清除内存缓存
                        if (mView != null) {
                            mView.cleanCacheSuccess();
                            initCache();
                        }
                    }
                });
            }
        }).start();

    }


    private class GetDiskCacheSizeTask extends AsyncTask<File, Long, Long> {
        private static final String TAG = "GetDiskCacheSizeTask";

        @Override
        protected void onPreExecute() {
            if (mView != null) {
                mView.setCacheSize("计算中...");
            }
        }

        @Override
        protected void onProgressUpdate(Long... values) { /* onPostExecute(values[values.length - 1]); */ }

        @Override
        protected Long doInBackground(File... dirs) {
            try {
                long totalSize = 0;
                for (File dir : dirs) {
                    publishProgress(totalSize);
                    totalSize += FileUtils.calculateSize(dir);
                }
                return totalSize;
            } catch (RuntimeException ex) {
                final String message = String.format("Cannot get size of %s: %s", Arrays.toString(dirs), ex);
                Log.i(TAG, message);
            }
            return 0L;
        }

        @Override
        protected void onPostExecute(Long size) {
            String sizeText = Formatter.formatFileSize(B8Application.getIntstance(), size);
            if (mView != null) {
                mView.setCacheSize(sizeText);
            }
        }

    }

    public void onDetach(){
        mView = null;
    }



}
