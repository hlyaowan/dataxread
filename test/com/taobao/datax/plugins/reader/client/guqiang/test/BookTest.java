package com.taobao.datax.plugins.reader.client.guqiang.test;

import org.junit.Test;

import com.taobao.datax.common.http.GuqiangHttpClient;

public class BookTest {

    public static GuqiangHttpClient client =new GuqiangHttpClient();
    @Test
    public void getGuqiangClientBookList() {
        String catalogId="";
        String pageSize="";
        String page="100";
        client.getGuqiangClientBookList(catalogId, pageSize, page);
    }
    
    @Test
    public void checkUpdateBookChapter() {
        String bookId="30573";
        client.checkUpdateBookChapter(bookId, "140");
    }
    
    @Test
    public void getGuqiangClientChapterList() {
        String bookId="30573";
        client.getGuqiangClientChapterList(bookId, "","");
    }
    
    @Test
    public void getGuqiangClientBookInfo() {
        String bookId="30573";
        client.getGuqiangClientBookInfo(bookId);
    }
    
    @Test
    public void getGuqiangClientChapterInfo() {
        String bookId="30573";
        String chapterId="1";
        client.getGuqiangClientChapterInfo(bookId,chapterId);
    }
    
//  System.out.println(client.getGuqiangClientBookList("", "", ""));
//  System.out.println(client.checkUpdateBookChapter("30573", ""));
//  System.out.println(client.getGuqiangClientChapterList("30573", "", ""));
//  System.out.println(client.getGuqiangClientBookInfo("30573"));
//  System.out.println(client.getGuqiangClientChapterInfo("30573", "1"));

}
