package com.example.listennewsforenglish;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Liang
 * @date 2023/6/20 10:42
 * description
 */
public class NewsReceiver  extends BroadcastReceiver {
    private NewsApplication newsApplication;
    private INews iNews;
    private static final String TAG = "DailyVOA";
    @Override
    public void onReceive(Context context, Intent intent) {
        newsApplication = (NewsApplication) context.getApplicationContext();
        String ctrl_code = intent.getAction();
        Log.d(TAG,"broadcast " + ctrl_code);
        //Toast.makeText(context,"收到广播", Toast.LENGTH_SHORT).show();
        iNews = newsApplication.iNews;
        if (iNews != null) {
            if ("play".equals(ctrl_code)) {
                iNews.moveon();
                Log.d(TAG,"NewsReceiver play");
            }else if ("next".equals(ctrl_code)) {
                iNews.nextNews();
                Log.d(TAG,"NewsReceiver next");
            }
            else if ("last".equals(ctrl_code)) {
                iNews.LastNews();
                Log.d(TAG,"NewsReceiver pre");
            }
            else if ("pause".equals(ctrl_code)) {
                iNews.pause();
                Log.d(TAG,"NewsReceiver pause");
            }
            else if ("seek".equals(ctrl_code)) {
                iNews.SeekNews();
                Log.d(TAG,"NewsReceiver seek");
            }
            else if ("back".equals(ctrl_code)) {
                iNews.BackNews();
                Log.d(TAG,"NewsReceiver back");
            }
        }
    }
}
