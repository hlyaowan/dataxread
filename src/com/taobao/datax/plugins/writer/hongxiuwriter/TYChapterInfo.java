/*
 * 
 */
// Created on 2013-12-1

package com.taobao.datax.plugins.writer.hongxiuwriter;

import java.sql.Timestamp;

/**
 * @author joe.chen
 */
public class TYChapterInfo {

    private String    outChapterId;

    private String    outBookId;

    private Integer   innerChapterId;

    private Integer   innerBookId;

    private Integer   chapterOrder;

    private String    rollName;

    private String    roll;

    private String    chapterName;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Integer   chapterSize;

    private Integer   isFree;

    private String    content;

    private String    sourceId;

    public String getOutChapterId() {
        return outChapterId;
    }

    public void setOutChapterId(String outChapterId) {
        this.outChapterId = outChapterId;
    }

    public String getOutBookId() {
        return outBookId;
    }

    public void setOutBookId(String outBookId) {
        this.outBookId = outBookId;
    }

    public Integer getInnerChapterId() {
        return innerChapterId;
    }

    public void setInnerChapterId(Integer innerChapterId) {
        this.innerChapterId = innerChapterId;
    }

    public Integer getInnerBookId() {
        return innerBookId;
    }

    public void setInnerBookId(Integer innerBookId) {
        this.innerBookId = innerBookId;
    }

    public Integer getChapterOrder() {
        return chapterOrder;
    }

    public void setChapterOrder(Integer chapterOrder) {
        this.chapterOrder = chapterOrder;
    }

    public String getRollName() {
        return rollName;
    }

    public void setRollName(String rollName) {
        this.rollName = rollName;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getChapterSize() {
        return chapterSize;
    }

    public void setChapterSize(Integer chapterSize) {
        this.chapterSize = chapterSize;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

}
