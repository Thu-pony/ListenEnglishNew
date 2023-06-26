package com.example.listennewsforenglish;

/**
 * @author Liang
 * @date 2023/6/19 20:59
 * description
 */
public class NewsBean {
    private int ID;
    private String newsTitle;
    private int newsCover;
    private String newsContent;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public NewsBean(int id, String newsTitle, int newsCover, String newsContent) {
        ID = id;
        this.newsTitle = newsTitle;
        this.newsCover = newsCover;
        this.newsContent = newsContent;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public int getNewsCover() {
        return newsCover;
    }

    public void setNewsCover(int newsCover) {
        this.newsCover = newsCover;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}
