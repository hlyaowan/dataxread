package com.taobao.datax.common.http;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.datax.common.util.DateUtils;
import com.taobao.datax.common.util.MD5Utils;

public class NetwayHttpClient {
    private static final Logger LOG = LoggerFactory.getLogger(NetwayHttpClient.class);
    private static final String BASE_URL = "http://opendata.readnovel.com/web/";
    private static final String MCPID="1000004";
    private static final String MCPKEY="88ef63a014b3f579";
    private static final String LASTUPDATETIME="0";
    //请求格式：http://opendata.readnovel.com/web/mcp_shuku.php?a=get_book_list&mcpid=1000004&lastUpdateTime=1386732252&sig=7a7d990afbba319a4a1d7c1f7525db5d
    //加密规则：$sig = md5( md5($mcpkey . $lastUpdateTime) . $mcpkey ) ;
    private static HttpInvokeClient httpClient = new SyncHttpInvokeClient();
    /***
     * 获取书库列表
     * @return
     */
    public String getShukuList(){
        String sig =MD5Utils.getMd5(MD5Utils.getMd5(MCPKEY+LASTUPDATETIME)+MCPKEY);
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("mcpid", MCPID));
        DateUtils.getNextDayUnixTime();
        params.add(buildParam("lastUpdateTime",LASTUPDATETIME));
        params.add(buildParam("sig",sig));
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL).append("mcp_shuku.php?a=get_book_list");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOG.info("message:"+content);
        return content;
    }
    
    /***
     * 获取书籍信息接口
     * @param bookId
     * @return
     */
    public String getBookInfo(String bookId){
        if(StringUtils.isEmpty(bookId)){
            return StringUtils.EMPTY;
        }
        String sig =MD5Utils.getMd5(MD5Utils.getMd5(MCPKEY+bookId)+MCPKEY);
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("mcpid", MCPID));
        params.add(buildParam("bookid",bookId));
        params.add(buildParam("sig",sig));
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL).append("mcp_shuku.php?a=get_book_info");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(4000);
        request.setSoTimeout(2000);
        LOG.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOG.info("getBookInfo:"+content);
        return content;
    }
    /***
     * 根据书籍Id获取章节列表信息
     * @param bookId
     * @return
     */
    public String getChapterList(String bookId){
        if(StringUtils.isEmpty(bookId)){
            return StringUtils.EMPTY;
        }
        String sig =MD5Utils.getMd5(MD5Utils.getMd5(MCPKEY+bookId)+MCPKEY);
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("mcpid", MCPID));
        params.add(buildParam("bookid",bookId));
        params.add(buildParam("sig",sig));
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL).append("mcp.php?a=get_text_list");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(4000);
        request.setSoTimeout(2000);
        LOG.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOG.info("getChapterList:"+content);
        return content;
    }
    
    /***
     * 获取章节内容信息接口，根据bookId和chapterId
     * @param bookId
     * @param chapterId
     * @return
     */
    public String getChapterContent(String bookId,String chapterId){
        if(StringUtils.isEmpty(bookId)||StringUtils.isEmpty(chapterId)){
            return StringUtils.EMPTY;
        }
        String sig =MD5Utils.getMd5(MD5Utils.getMd5(MCPKEY+bookId+chapterId)+MCPKEY);
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("mcpid", MCPID));
        params.add(buildParam("bookid",bookId));
        params.add(buildParam("textid",chapterId));
        params.add(buildParam("sig",sig));
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL).append("mcp_shuku.php?a=get_text_info");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(4000);
        request.setSoTimeout(2000);
        LOG.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOG.info("getChapterContent:"+content);
        return content;
    }
    
    
    public static void main(String[] args) {
        NetwayHttpClient client =new NetwayHttpClient();
//        System.out.println(client.getShukuList());
//        System.out.println(client.getChapterList("229204"));
//        System.out.println(client.getBookInfo("229204"));
//        System.out.println(client.getChapterContent("229204", "1"));
    }
    
    private DefaultNameValuePair buildParam(String key, String value) {
        DefaultNameValuePair onvp = new DefaultNameValuePair(key, value);
        return onvp;
    }
}
