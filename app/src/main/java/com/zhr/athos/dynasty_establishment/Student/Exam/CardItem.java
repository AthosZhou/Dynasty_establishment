package com.zhr.athos.dynasty_establishment.Student.Exam;


public class CardItem {

    private String mTextResource;
    private String mTitleResource;
    private String murl;
    private String mflag;

    public CardItem(String title, String text,String url,String flag) {
        mTitleResource = title;
        mTextResource = text;
        murl=url;
        mflag=flag;
    }

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }

    public String getMurl() {
        return murl;
    }

    public String getFlag() {return mflag; }
}
