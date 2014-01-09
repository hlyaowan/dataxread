package com.taobao.datax.plugins.reader.guqiangreader;

import java.io.Serializable;


@SuppressWarnings("serial")
public class BookList implements Serializable {
    private int id;
    private String bookName;
    private String categoryId;
    private String categoryPid;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getBookName() {
        return bookName;
    }
    
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryPid() {
        return categoryPid;
    }
    
    public void setCategoryPid(String categoryPid) {
        this.categoryPid = categoryPid;
    }
    
}
