package com.taobao.datax.common.http;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class GuqiangHttpClient {

    public static final String HEZUOHAO="100081";
    public static final String BASE_URL ="http://hezuo.kanshu.cn/";
    public static final String BASE_URL2 ="http://hezuo2.kanshu.cn/";
    public static final String FLAG="0";
    public static final int PAGE=1;
    public static final String PAGESIZE="10";
    public static final Logger LOGGER =Logger.getLogger(GuqiangHttpClient.class);
    private static HttpInvokeClient httpClient = new SyncHttpInvokeClient();
    /***
     * 获取书库内容，catalogId非必须，有值取分类内容，无值取全部书库
     * @param catalogId
     * @return
     */
    public String getGuqiangClientBookList(String catalogId,String pageSize,Integer page){
        if(StringUtils.isEmpty(catalogId)){
            catalogId=StringUtils.EMPTY;
        }
        if(StringUtils.isBlank(pageSize)){
            pageSize =PAGESIZE;
        }
        if(page==null){
            page =PAGE;
        }
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("cono", HEZUOHAO)); //合作号
        params.add(buildParam("categoryid", catalogId));//分类Id
        params.add(buildParam("flag", FLAG)); //0：全部书单1：未获取小说书单默认：0
        params.add(buildParam("pagenum", pageSize));//每页显示条数，默认取全部
        params.add(buildParam("page", String.valueOf(page)));//页数默认1
        
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL2).append("offer/booklist.php");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(10000);
        request.setSoTimeout(5000);
        LOGGER.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOGGER.info("bookList:"+content);
        return content;
    }
    
    /***
     * 检查小说续传章节信息
     * @param bookId
     * @param maxChapterId
     * @return
     */
    public String checkUpdateBookChapter(String bookId,String  maxChapterId){
        if(StringUtils.isBlank(bookId)){
            return StringUtils.EMPTY;
        }
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("cono", HEZUOHAO)); //合作号
        params.add(buildParam("articleid", bookId));//小说ID
        params.add(buildParam("chapterid", maxChapterId)); //章节NUM（现有最大章节数）
        
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL).append("offer/checkUp.php");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(10000);
        request.setSoTimeout(5000);
        LOGGER.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOGGER.info("checkUpdateBookChapter:"+content);
        return content;
    }
    /***
     * 获取小说章节列表信息
     * @param bookId
     * @param chapterId
     * @param chapterNum
     * @return
     */
    public String getGuqiangClientChapterList(String bookId,String chapterId,String chapterNum){
        if(StringUtils.isBlank(bookId)){
            return StringUtils.EMPTY;
        }
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("cono", HEZUOHAO)); //合作号
        params.add(buildParam("bookid", bookId));//小说ID
        params.add(buildParam("chapterid", chapterId)); //小说章节ID 从第一章开始获取（包括第一章）时：chapterid=0 否则chaptered=现有最末章节id（返回不包含当前  id 章节）
        params.add(buildParam("chapternum", chapterNum)); //获取条数（默认全部）
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL2).append("offer/getchapterlist.php");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(10000);
        request.setSoTimeout(5000);
        LOGGER.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOGGER.info("getGuqiangClientChapterList:"+content);
        return content;
    }
    
    /***
     * 获取书籍内容
     * @param bookId
     * @return
     */
    public String getGuqiangClientBookInfo(String bookId){
        if(StringUtils.isBlank(bookId)){
            return StringUtils.EMPTY;
        }
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("cono", HEZUOHAO)); //合作号
        params.add(buildParam("bookid", bookId));//小说ID
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL2).append("offer/bookinfo.php");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(10000);
        request.setSoTimeout(5000);
        LOGGER.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOGGER.info("getGuqiangClientBookInfo:"+content);
        return content;
    }
    /***
     * 获取章节内容信息
     * @param bookId
     * @param chapterId
     * @return
     */
    public String getGuqiangClientChapterInfo(String bookId,String chapterId){
        if(StringUtils.isBlank(bookId)||StringUtils.isBlank(chapterId)){
            return StringUtils.EMPTY;
        }
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();
        params.add(buildParam("cono", HEZUOHAO)); //合作号
        params.add(buildParam("bookid", bookId));//小说ID
        params.add(buildParam("chapterid", chapterId));//小说章节ID
        StringBuilder base = new StringBuilder();
        base.append(BASE_URL2).append("offer/getcontent.php");
        HttpInvokeRequest request = new HttpInvokeRequest(base.toString(), "GET");
        request.setConnTimeout(10000);
        request.setSoTimeout(5000);
        LOGGER.info(base.toString()+params);
        request.addParams(params);
        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
        LOGGER.info("getGuqiangClientChapterInfo:"+content);
        return content;
    }
    
    private DefaultNameValuePair buildParam(String key, String value) {
        DefaultNameValuePair onvp = new DefaultNameValuePair(key, value);
        return onvp;
    }
    
    public static void main(String[] args) {
        GuqiangHttpClient client =new GuqiangHttpClient();
//        System.out.println(client.getGuqiangClientBookList("", "", ""));
//        System.out.println(client.checkUpdateBookChapter("30573", ""));
//        System.out.println(client.getGuqiangClientChapterList("30573", "", ""));
//        System.out.println(client.getGuqiangClientBookInfo("30573"));
        System.out.println(client.getGuqiangClientChapterInfo("30573", "1"));
    }
}
