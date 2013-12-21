package com.taobao.datax.plugins.reader.client.hongxiu.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taobao.datax.plugins.writer.hongxiuwriter.HongxiuContentService;

public class ContentService {

    HongxiuContentService service =new HongxiuContentService();
    
    @Test
    public void getChapterList() {
        
        service.getChapterInfos("29077");
    }
    
    @Test
    public void getChapterListCount() {
        
        service.getChapterInfosCount("29077");
    }
    
    @Test
    public void getChapterInfo() {
        
        System.out.println(service.getChapterInfo("29077", "137011"));
    }

}
