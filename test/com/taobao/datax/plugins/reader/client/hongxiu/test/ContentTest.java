package com.taobao.datax.plugins.reader.client.hongxiu.test;

import org.junit.Test;

import com.taobao.datax.common.http.HongxiuHttpClient;

public class ContentTest {
    public static HongxiuHttpClient client =new HongxiuHttpClient();
    @Test
    public void getHongxiuClientBookList() {
        client.getHongxiuClientBookList();
    }
    
    
    @Test
    public void getHongxiuChapterList() {
        String bookId ="29077";
        client.getHongxiuChapterList(bookId);
    }
    
    @Test
    public void getHongxiuBookInfo() {
        String bookId ="29077";
        client.getHongxiuBookInfo(bookId);
    }
    
    @Test
    public void getHongxiuChapterInfo() {
        String bookId ="29077";
        String chapterId ="137011";
        client.getHongxiuChapterInfo(bookId,chapterId);
    }
    
//  System.out.println(client.getHongxiuClientBookList());
//  System.out.println(client.getHongxiuBookInfo("29077"));
//  System.out.println(client.getHongxiuChapterInfo("29077", "137010"));

}
