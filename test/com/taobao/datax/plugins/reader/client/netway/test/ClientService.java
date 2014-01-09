package com.taobao.datax.plugins.reader.client.netway.test;


import org.junit.Test;

import com.taobao.datax.plugins.writer.netwaywriter.NetwayContentService;

public class ClientService {

    
    private NetwayContentService service=new NetwayContentService();
    @Test
    public void getShuChengList() {
        service.getShuChengList();
    }
    
    @Test
    public void getChapterInfoList() {
        service.getChapterInfoList("23899");
    }
    
    @Test
    public void getBookContent() {
        System.out.println(service.getBookContent("193486"));
    }
    
    @Test
    public void getChapterInfo() {
        System.out.println(service.getChapterInfo("77266","1251139").getChapterContent());
    }
    

}
