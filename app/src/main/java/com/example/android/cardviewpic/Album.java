package com.example.android.cardviewpic;

/**
 * Created by zengzhi on 2017/6/19.
 */

public class Album {

    private String mName;
    private int mNumOfSongs;
    private int mThumbnail;


    public Album(String name, int numOfSongs, int thumbnail) {
        this.mName = name;
        this.mNumOfSongs = numOfSongs;
        this.mThumbnail = thumbnail;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getNumOfSongs() {
        return mNumOfSongs;
    }

    public void setNumOfSongs(int mNumOfSongs) {
        this.mNumOfSongs = mNumOfSongs;
    }

    public int getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(int mThumbnail) {
        this.mThumbnail = mThumbnail;
    }
}
