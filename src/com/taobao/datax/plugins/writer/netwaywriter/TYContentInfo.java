/*
 * 
 */
// Created on 2013-11-30

package com.taobao.datax.plugins.writer.netwaywriter;

import java.sql.Timestamp;

/**
 * @author joe.chen
 */
public class TYContentInfo {

    private Integer   innerBookId;

    private String    outBookId;

    private Timestamp createTime;

    private String    bookName;

    private String    authorName;

    private Integer   bookSize;

    private String    bookIntro;

    private String    bookCover;

    private Timestamp lastChapterUpdateTime;

    private Integer   bookFullFlag;

    private Integer   chapterTotal;

    private String    sourceId;

    public Integer getInnerBookId() {
        return innerBookId;
    }

    public void setInnerBookId(Integer innerBookId) {
        this.innerBookId = innerBookId;
    }

    public String getOutBookId() {
        return outBookId;
    }

    public void setOutBookId(String outBookId) {
        this.outBookId = outBookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getBookSize() {
        return bookSize;
    }

    public void setBookSize(Integer bookSize) {
        this.bookSize = bookSize;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public Integer getBookFullFlag() {
        return bookFullFlag;
    }

    public void setBookFullFlag(Integer bookFullFlag) {
        this.bookFullFlag = bookFullFlag;
    }

    public Integer getChapterTotal() {
        return chapterTotal;
    }

    public void setChapterTotal(Integer chapterTotal) {
        this.chapterTotal = chapterTotal;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastChapterUpdateTime() {
        return lastChapterUpdateTime;
    }

    public void setLastChapterUpdateTime(Timestamp lastChapterUpdateTime) {
        this.lastChapterUpdateTime = lastChapterUpdateTime;
    }

}
