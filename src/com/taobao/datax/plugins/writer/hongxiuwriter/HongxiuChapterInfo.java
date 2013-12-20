package com.taobao.datax.plugins.writer.hongxiuwriter;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HongxiuChapterInfo implements Serializable {
    private String bookChapterId;
    private String bookChapterName;
    private String bookChapterisVip;
    private String bookChapterTxt;
    private String bookChapterPrice;
    private String bookChapterDate;
    private String chapterSize;
    public String getChapterSize() {
        return chapterSize;
    }
    public void setChapterSize(String chapterSize) {
        this.chapterSize = chapterSize;
    }
    public String getBookChapterId() {
        return bookChapterId;
    }
    public void setBookChapterId(String bookChapterId) {
        this.bookChapterId = bookChapterId;
    }
    public String getBookChapterName() {
        return bookChapterName;
    }
    public void setBookChapterName(String bookChapterName) {
        this.bookChapterName = bookChapterName;
    }
    public String getBookChapterisVip() {
        return bookChapterisVip;
    }
    public void setBookChapterisVip(String bookChapterisVip) {
        this.bookChapterisVip = bookChapterisVip;
    }
    public String getBookChapterTxt() {
        return bookChapterTxt;
    }
    public void setBookChapterTxt(String bookChapterTxt) {
        this.bookChapterTxt = bookChapterTxt;
    }
    public String getBookChapterPrice() {
        return bookChapterPrice;
    }
    public void setBookChapterPrice(String bookChapterPrice) {
        this.bookChapterPrice = bookChapterPrice;
    }
    public String getBookChapterDate() {
        return bookChapterDate;
    }
    public void setBookChapterDate(String bookChapterDate) {
        this.bookChapterDate = bookChapterDate;
    }
}
