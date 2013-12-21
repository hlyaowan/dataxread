package com.taobao.datax.plugins.reader.client.guqiang.test;


import org.junit.Test;

import com.taobao.datax.plugins.writer.guqiangwriter.GuqiangContentService;


public class BookService {

    GuqiangContentService service =new GuqiangContentService();
    @Test
    public void getGuqiangClientBookList() {
        service.getGuqiangClientBookList(1);
    }
    
    
    @Test
    public void getGuqiangChapterList() {
        service.getGuqiangChapterList("30573",10);
    }
    
    @Test
    public void getChapterContent() {
        service.getChapterContent("30573","1");
    }
    
    @Test
    public void isUpdateChapter() {
        System.out.println(service.isUpdateChapter("30573","1"));
    }
    
    @Test
    public void getContentInfo() {
        System.out.println(service.getContentInfo("30573").getBookName());
    }

}
