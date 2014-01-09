package com.taobao.datax.plugins.reader.hongxiureader;

import java.io.Serializable;

public class HongxiuContentInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int bookId;
    private String bookName;
    private String bookCpid;
    private String bookAuthor;
    private String bookCategory;
    private int bookfinishState;
    private String ImageUrl;
    private String bookisVip;
    private String bookDescription;
    private String bookCreatetime;
    private int bookWordsCount;
    private String bookKey;
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getBookCpid() {
        return bookCpid;
    }
    public void setBookCpid(String bookCpid) {
        this.bookCpid = bookCpid;
    }
    public String getBookAuthor() {
        return bookAuthor;
    }
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
    public String getBookCategory() {
        return bookCategory;
    }
    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }
    public int getBookfinishState() {
        return bookfinishState;
    }
    public void setBookfinishState(int bookfinishState) {
        this.bookfinishState = bookfinishState;
    }
    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
    public String getBookisVip() {
        return bookisVip;
    }
    public void setBookisVip(String bookisVip) {
        this.bookisVip = bookisVip;
    }
    public String getBookDescription() {
        return bookDescription;
    }
    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
    public String getBookCreatetime() {
        return bookCreatetime;
    }
    public void setBookCreatetime(String bookCreatetime) {
        this.bookCreatetime = bookCreatetime;
    }
    public int getBookWordsCount() {
        return bookWordsCount;
    }
    public void setBookWordsCount(int bookWordsCount) {
        this.bookWordsCount = bookWordsCount;
    }
    public String getBookKey() {
        return bookKey;
    }
    public void setBookKey(String bookKey) {
        this.bookKey = bookKey;
    }
}
