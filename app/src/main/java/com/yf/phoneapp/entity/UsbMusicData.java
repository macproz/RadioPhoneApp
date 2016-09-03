package com.yf.phoneapp.entity;

import java.io.Serializable;

/**
 * Created by lishanfeng on 16/8/19.
 */
public class UsbMusicData implements Serializable{
    private String author;  // 音乐作者
    private String title;   // 音乐名字
    private String album;   //音乐专辑名字


    @Override
    public String toString() {
        return "UsbMusicData{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
