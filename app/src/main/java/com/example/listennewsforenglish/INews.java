package com.example.listennewsforenglish;

/**
 * @author Liang
 * @date 2023/6/20 9:27
 * description
 * 播放新闻的控制函数
 */
public interface INews {
    public void moveon();//继续
    public void pause();//暂停
    public void stop(); // 停止
    public void nextNews(); //下一条新闻
    public void LastNews(); // 上一条新闻

    public void SeekNews();//快进
    public void BackNews();//后退
}
