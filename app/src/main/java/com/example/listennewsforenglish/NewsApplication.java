package com.example.listennewsforenglish;

import android.app.Application;
import android.app.NotificationManager;

import java.util.List;

/**
 * @author Liang
 * @date 2023/6/20 9:31
 * description
 * 传递全局变量
 */
public class NewsApplication extends Application {
    public List<NewsBean> newsBeanList; //当前播放列表
    public int newsItemPos; // 当前播放位置
    public NotificationManager notificationManager; //通知状态改变
    public INews iNews; //控制新闻播放
}
