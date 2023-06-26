package com.example.listennewsforenglish;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imageButton;
    private ImageButton palyImageButton;
    private ImageButton nextImageButton;
    private ImageButton preImageButton;
    private ImageButton seekImageButton;
    private ImageButton backImageButton;
    private TextView ContenttextView;
    private final String TAG = "DailyVOA";
    private RemoteViews contentView;
    private Notification notification;
    private NewsApplication newsApplication;
    private INews iNews;
    private PlayService playService;
    private boolean isPlaying = false;
    private ServiceConnection connection =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"bindService");
            playService = ((PlayService.NewsListener) service ).getService();
        }
        //不成功时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"bind failed");
            playService = null;
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"Activity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsApplication = (NewsApplication)getApplication();
        imageButton = (ImageButton) findViewById(R.id.ib_list);
        imageButton.setOnClickListener(this);
        palyImageButton = (ImageButton) findViewById(R.id.ib_play);
        palyImageButton.setOnClickListener(this);
        nextImageButton = (ImageButton) findViewById(R.id.ib_next);
        nextImageButton.setOnClickListener(this);
        preImageButton = (ImageButton) findViewById(R.id.ib_pre);
        preImageButton.setOnClickListener(this);
        backImageButton = (ImageButton)findViewById(R.id.ib_back);
        backImageButton.setOnClickListener(this);
        seekImageButton = (ImageButton)findViewById(R.id.ib_seek);
        seekImageButton.setOnClickListener(this);
        ContenttextView = (TextView)findViewById(R.id.tv_content);
        ContenttextView.setText(newsApplication.newsBeanList.get(newsApplication.newsItemPos).getNewsContent());
        // 初始化通知栏播放控件
        Log.d(TAG,"play news");
        Intent intent1 = new Intent(this,PlayService.class);
        this.bindService(intent1,connection, Context.BIND_AUTO_CREATE);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_list:
                Log.d(TAG,"点击imageButton");
                Intent intent = new Intent(this,GetlistActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_play:
                isPlaying = !isPlaying;
                String text = isPlaying == true?"播放":"暂停";
                Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
                Log.d(TAG,"点击了播放按钮");
                Intent intentPlay = new  Intent();
                if (isPlaying)
                intentPlay.setAction("play");
                else
                intentPlay.setAction("pause");
                //发送无序广播
                //Android 8.0之后的改动
                intentPlay.setComponent(new ComponentName("com.example.listennewsforenglish","com.example.listennewsforenglish.NewsReceiver"));
                sendBroadcast(intentPlay);
                    break;
            case R.id.ib_next:
                Log.d(TAG,"点击了下一首按钮");
                ++newsApplication.newsItemPos;
                if (newsApplication.newsItemPos >= newsApplication.newsBeanList.size())newsApplication.newsItemPos = newsApplication.newsBeanList.size() - 1 ;
                newsApplication.newsItemPos %= 6;
                ContenttextView.setText(newsApplication.newsBeanList.get(newsApplication.newsItemPos).getNewsContent());
                Intent intentNext = new  Intent();
                intentNext.setAction("next");
                //发送无序广播
                //Android 8.0之后的改动
                intentNext.setComponent(new ComponentName("com.example.listennewsforenglish","com.example.listennewsforenglish.NewsReceiver"));
                sendBroadcast(intentNext);
                break;

            case R.id.ib_pre:
                Log.d(TAG,"点击了上一首按钮");
                --newsApplication.newsItemPos;
                if (newsApplication.newsItemPos <= 0)newsApplication.newsItemPos = 0;
                newsApplication.newsItemPos %= 6;
                ContenttextView.setText(newsApplication.newsBeanList.get(newsApplication.newsItemPos).getNewsContent());
                Intent intentPre = new Intent();
                intentPre.setAction("last");
                intentPre.setComponent(new ComponentName("com.example.listennewsforenglish","com.example.listennewsforenglish.NewsReceiver"));
                sendBroadcast(intentPre);
                break;
            case R.id.ib_back:
                ContenttextView.setText(newsApplication.newsBeanList.get(newsApplication.newsItemPos).getNewsContent());
                Log.d(TAG,"点击了后退按钮");
                Intent intentBack = new Intent();
                intentBack.setAction("back");
                intentBack.setComponent(new ComponentName("com.example.listennewsforenglish","com.example.listennewsforenglish.NewsReceiver"));
                sendBroadcast(intentBack);
                Toast.makeText(this,"后退3秒",Toast.LENGTH_SHORT).show();
                break;

            case  R.id.ib_seek:
                Log.d(TAG,"点击了快进按钮");
                Intent intentSeek = new Intent();
                intentSeek.setAction("seek");
                intentSeek.setComponent(new ComponentName("com.example.listennewsforenglish","com.example.listennewsforenglish.NewsReceiver"));
                sendBroadcast(intentSeek);
                Toast.makeText(this,"快进3秒",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}