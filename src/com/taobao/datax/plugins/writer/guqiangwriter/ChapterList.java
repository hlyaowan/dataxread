package com.taobao.datax.plugins.writer.guqiangwriter;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ChapterList implements Serializable {
    private String chapterId;
    private String chapterName;
    private String chapterSize;
    private String isVip;
    private String  price;
    private String  content;
    
    
    public String getContent() {
        return content;
    }

    
    public void setContent(String content) {
        this.content = content;
    }

    public String getChapterId() {
        return chapterId;
    }
    
    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
    
    public String getChapterName() {
        return chapterName;
    }
    
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    
    public String getChapterSize() {
        return chapterSize;
    }
    
    public void setChapterSize(String chapterSize) {
        this.chapterSize = chapterSize;
    }
    
    public String getIsVip() {
        return isVip;
    }
    
    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
}
