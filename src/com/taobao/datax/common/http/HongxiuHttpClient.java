package com.taobao.datax.common.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HongxiuHttpClient {
    
    private static final Logger LOG = LoggerFactory.getLogger(HongxiuHttpClient.class);
    private static final String BASE_URL = "http://open.api.hongxiu.cn/aspxnovel/apiout/";
    private static final String CONNECTIONCODE = "hxdianxinV2C45on6dxv2";
    private static HttpInvokeClient httpClient = new SyncHttpInvokeClient();
    
    public String getHongxiuClientBookList(){
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("code", CONNECTIONCODE));
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL).append("dianxin/apidianxinallbook.aspx");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(10000);
        request.setSoTimeout(5000);
        LOG.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
//        LOG.info("getHongxiuClientBookList"+content);
        return content;
    }
    /***
     * 获取书籍信息接口
     * @url：http://open.api.hongxiu.cn/aspxnovel/apiout/dianxin/apidianxinbookinfo.aspx?bookid=12511&code= hxdianxinV2C45on6dxv2
     * @param bookId
     * @return
     */
    public String getHongxiuBookInfo(String bookId){
        if (StringUtils.isEmpty(bookId)) {
            return StringUtils.EMPTY;
        }
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("code", CONNECTIONCODE));
        params.add(buildParam("bookid", bookId));
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL).append("dianxin/apidianxinbookinfo.aspx");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(10000);
        request.setSoTimeout(5000);
        LOG.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOG.info("getHongxiuBookInfo"+content);
        return content;
    }
    
    /***
     * 获取章节列表
     * http://open.api.hongxiu.cn/aspxnovel/apiout/dianxin/apidianxinchapterlist.aspx?bookid=29077&code=hxdianxinV2C45on6dxv2
     * @param bookId
     * @return
     */
    public String getHongxiuChapterList(String bookId){
        if (StringUtils.isEmpty(bookId)) {
            return StringUtils.EMPTY;
        }
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("code", CONNECTIONCODE));
        params.add(buildParam("bookid", bookId));
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL).append("dianxin/apidianxinchapterlist.aspx");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(10000);
        request.setSoTimeout(5000);
        LOG.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOG.info("getHongxiuBookInfo"+content);
        return content;
    }
    /***
     * 获取红袖章节信息
     * @url：http://open.api.hongxiu.cn/aspxnovel/apiout/dianxin/apidianxincontent.aspx?bookid=29077&chapterid=136858&code=hxdianxinV2C45on6dxv2
     * @param bookId
     * @param chapterId
     * @return
     */
    public String getHongxiuChapterInfo(String bookId,String chapterId){
        if (StringUtils.isEmpty(bookId)) {
            return StringUtils.EMPTY;
        }
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("code", CONNECTIONCODE));
        params.add(buildParam("bookid", bookId));
        if(!StringUtils.isEmpty(chapterId)){
            params.add(buildParam("chapterid", chapterId));
        }
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL).append("dianxin/apidianxincontent.aspx");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(4000);
        request.setSoTimeout(2000);
        LOG.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOG.info("getHongxiuChapterInfo"+content);
        return content;
    }
    private DefaultNameValuePair buildParam(String key, String value) {
        DefaultNameValuePair onvp = new DefaultNameValuePair(key, value);
        return onvp;
    }
    
    public static void main(String[] args) {
        HongxiuHttpClient client =new HongxiuHttpClient();
//        System.out.println(client.getHongxiuClientBookList());
//        System.out.println(client.getHongxiuBookInfo("29077"));
        System.out.println(client.getHongxiuChapterInfo("29077", "137010"));
    }
    
}
