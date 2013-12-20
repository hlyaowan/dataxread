package com.taobao.datax.plugins.writer.guqiangwriter;

import java.io.Serializable;


@SuppressWarnings("serial")
public class GQcontentInfo implements Serializable {
    private String id;
    private String bookName;
    private String detail;
    private int bookType =0;
    private String keyWord;
    private int bookStatus =0;
    private String subTitle;
    private int size =0;
    private String author;
    private int isVip =0;
    private int maxFree  =0;
    private int price =0;
    private int isFee =0;
    private int weekVisit =0;
    private int monthVisit =0;
    private int allVisit =0;
    private String bookTypeName;
    private String imagePath;
    private String imageMidPath;
    private String imageMinPath;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getBookName() {
        return bookName;
    }
    
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    public String getDetail() {
        return detail;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public int getBookType() {
        return bookType;
    }
    
    public void setBookType(int bookType) {
        this.bookType = bookType;
    }
    
    public String getKeyWord() {
        return keyWord;
    }
    
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    
    public int getBookStatus() {
        return bookStatus;
    }
    
    public void setBookStatus(int bookStatus) {
        this.bookStatus = bookStatus;
    }
    
    public String getSubTitle() {
        return subTitle;
    }
    
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public int getIsVip() {
        return isVip;
    }
    
    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }
    
    public int getMaxFree() {
        return maxFree;
    }
    
    public void setMaxFree(int maxFree) {
        this.maxFree = maxFree;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public int getIsFee() {
        return isFee;
    }
    
    public void setIsFee(int isFee) {
        this.isFee = isFee;
    }
    
    public int getWeekVisit() {
        return weekVisit;
    }
    
    public void setWeekVisit(int weekVisit) {
        this.weekVisit = weekVisit;
    }
    
    public int getMonthVisit() {
        return monthVisit;
    }
    
    public void setMonthVisit(int monthVisit) {
        this.monthVisit = monthVisit;
    }
    
    public int getAllVisit() {
        return allVisit;
    }
    
    public void setAllVisit(int allVisit) {
        this.allVisit = allVisit;
    }
    
    public String getBookTypeName() {
        return bookTypeName;
    }
    
    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public String getImageMidPath() {
        return imageMidPath;
    }
    
    public void setImageMidPath(String imageMidPath) {
        this.imageMidPath = imageMidPath;
    }
    
    public String getImageMinPath() {
        return imageMinPath;
    }
    
    public void setImageMinPath(String imageMinPath) {
        this.imageMinPath = imageMinPath;
    }
}
