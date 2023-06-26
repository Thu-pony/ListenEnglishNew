package com.example.listennewsforenglish;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.util.List;

//播放新闻的服务
public class PlayService extends Service {
    private MediaPlayer mediaPlayer;
    private boolean noPlayFile = true;
    private NewsApplication newsApplication;
    private List<NewsBean> newsBeanList;
    private int newsItemPos = 0;
    private final String TAG = "DailyVOA";
    public NewsListener NewsListener;

    public PlayService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Service on create");
        newsApplication = (NewsApplication) getApplication();
        Log.d(TAG, String.valueOf("newsApplication 是否为空 " + newsApplication == null));
        //newsBeanList = newsApplication.newsBeanList;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"Service onBind");
        //play(0);
        NewsListener = new NewsListener();
        Log.d(TAG,newsApplication.newsItemPos + "要播放了");
        newsApplication.iNews = NewsListener;
        return NewsListener;
    }

    private void play(int newsItemPos) {
        /*if (newsItemPos >= 0) {
            newsItemPos = newsItemPos % newsApplication.newsBeanList.size();
        }else {
            newsItemPos += newsApplication.newsBeanList.size();
        }
        newsApplication.newsItemPos = newsItemPos;//设置全局变量*/
        //NewsBean currentNews = newsBeanList.get(newsItemPos);
        if (mediaPlayer != null) {
            Log.d(TAG,"继续播放");
            mediaPlayer.start();
            return;
        }
        AssetManager assetManager = getAssets();
        try {
            Log.d(TAG,"play news " + newsItemPos);
            AssetFileDescriptor descriptor = assetManager.openFd( newsApplication.newsItemPos % 6 + ".mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.d(TAG,"play news error");
            Log.d(TAG,e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind");
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"Service onDestroy");
        super.onDestroy();
    }
    //播放新闻的接口
    public class NewsListener extends Binder implements INews{

        @Override
        public void moveon() {
            /*if (mediaPlayer != null){
                mediaPlayer.start();
            }*/
            play(newsApplication.newsItemPos);
        }

        @Override
        public void pause() {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        }

        @Override
        public void stop() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }

        @Override
        public void nextNews() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                play(newsApplication.newsItemPos);
            }
        }

        @Override
        public void LastNews() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                play(newsApplication.newsItemPos);
            }
        }

        @Override
        public void SeekNews() {
            Log.d(TAG,"service 快进3秒");
            if (mediaPlayer != null) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(currentPosition + 3000);
            }
        }

        @Override
        public void BackNews() {
            Log.d(TAG,"service 后退3秒");
            if (mediaPlayer != null) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(currentPosition - 3000);
            }
        }

        public PlayService getService() {
            return PlayService.this;
        }
    }
}