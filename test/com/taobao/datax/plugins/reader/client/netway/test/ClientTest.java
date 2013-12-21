package com.taobao.datax.plugins.reader.client.netway.test;

import org.junit.Test;

import com.taobao.datax.common.http.NetwayHttpClient;


public class ClientTest {

    public static NetwayHttpClient client = new NetwayHttpClient();


    @Test
    public void getShukuList() {
        client.getShukuList();
    }


    @Test
    public void getChapterList() {
        String bookId = "229204";
        client.getChapterList(bookId);
    }


    @Test
    public void getBookInfo() {
        String bookId = "229204";
        client.getBookInfo(bookId);
    }


    @Test
    public void getChapterContent() {
        String bookId = "229204";
        String chapterId = "1";
        client.getChapterContent(bookId, chapterId);
    }

    // System.out.println(client.getShukuList());
    // System.out.println(client.getChapterList("229204"));
    // System.out.println(client.getBookInfo("229204"));
    // System.out.println(client.getChapterContent("229204", "1"));
}
