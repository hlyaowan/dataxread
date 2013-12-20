/*
 * 
 */
// Created on 2013-11-30

package com.taobao.datax.common.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.taobao.datax.common.oauth.OAuth;
import com.taobao.datax.plugins.reader.zhulangreader.ZhulangReader;

/**
 * @author joe.chen
 */
public class ZhulangHttpClient {

    private static final String     OAUTH_CONSUMER_KEY      = "tyyd";
    private static final String     CONSUMER_SECRET         = "5JJ3Gs8485R78qVL14I815L3E2G6sI7852c01735f282g7F0";

    private static final String     BASE_URL                = "http://ifbk.zhulang.com/";

    private static final String     DEFAULT_PAGE_ROWS       = "30";

    private static String           contentInfoCustomFields = "outBookId:bk_id," + "createTime:bk_cre_date,"
                                                              + "bookName:bk_name," + "authorName:bk_author,"
                                                              + "bookSize:bk_size," + "bookIntro:bk_intro,"
                                                              + "bookCover:bk_cover,"
                                                              + "lastChapterUpdateTime:bk_last_ch_update,"
                                                              + "bookFullFlag:bk_fullflag," + "chapterTotal:ch_total";

    private static HttpInvokeClient httpClient              = new SyncHttpInvokeClient();

    private static Logger           logger                  = Logger.getLogger(ZhulangHttpClient.class);

    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getContentInfo(int pagenumber) {

        OAuth oauth = new OAuth(OAUTH_CONSUMER_KEY, CONSUMER_SECRET);

        String oauth_timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        HttpInvokeRequest request = new HttpInvokeRequest(BASE_URL, "POST");
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();

        params.add(buildParam("oauth_consumer_key", OAUTH_CONSUMER_KEY));
        params.add(buildParam("oauth_timestamp", oauth_timestamp));
        params.add(buildParam("format", "json"));

        params.add(buildParam("action", "getBookList"));

        params.add(buildParam("customFields", contentInfoCustomFields));
        params.add(buildParam("pagenumber", String.valueOf(pagenumber)));
        params.add(buildParam("pagerows", DEFAULT_PAGE_ROWS));

        StringBuilder base = new StringBuilder("POST").append("&").append(OAuth.encode(BASE_URL)).append("&");
        base.append(OAuth.encode(OAuth.normalizeRequestParameters(params)));
        String oauthBaseString = base.toString();
        String signature = oauth.generateSignature(oauthBaseString);
        request.addParams(params);
        request.addParam("oauth_signature", signature);

        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();
//        logger.info("invoke content_info : " + content);

        List<Map<String, String>> list = (List<Map<String, String>>) JSONArray.toList(JSONArray.fromObject(content),
                                                                                      new HashMap<String, String>(),
                                                                                      new JsonConfig());

        logger.info("invoke content_list size : " + list.size());

        return list;

    }

    public List<Map<String, String>> getChapterInfo(String bookId) {

        OAuth oauth = new OAuth(OAUTH_CONSUMER_KEY, CONSUMER_SECRET);

        String oauth_timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        HttpInvokeRequest request = new HttpInvokeRequest(BASE_URL, "POST");
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();

        params.add(buildParam("oauth_consumer_key", OAUTH_CONSUMER_KEY));
        params.add(buildParam("oauth_timestamp", oauth_timestamp));
        params.add(buildParam("format", "json"));

        params.add(buildParam("action", "getChapterList"));
        params.add(buildParam("bk_id", bookId));

        StringBuilder base = new StringBuilder("POST").append("&").append(OAuth.encode(BASE_URL)).append("&");
        base.append(OAuth.encode(OAuth.normalizeRequestParameters(params)));
        String oauthBaseString = base.toString();
        String signature = oauth.generateSignature(oauthBaseString);
        request.addParams(params);
        request.addParam("oauth_signature", signature);

        HttpInvokeResponse response = httpClient.invoke(request);

        String content = response.getContent();

//        logger.info("invoke chapter_info : " + content);

        // content =
        // "[{\"ch_roll\":\"200\",\"ch_id\":\"390239\",\"ch_name\":\"测试章节11\",\"ch_cre_time\":\"2011-05-31 15:46:17\",\"ch_update\":\"2011-05-31 17:46:17\",\"ch_size\":\"3517\",\"ch_vip\":\"0\",\"ch_roll_name\":\"正文\"},{\"ch_roll\":\"200\",\"ch_id\":\"390239\",\"ch_name\":\"测试章节2\",\"ch_cre_time\":\"2011-05-31 15:46:17\",\"ch_update\":\"2011-05-31 15:46:17\",\"ch_size\":\"3517\",\"ch_vip\":\"0\",\"ch_roll_name\":\"正文\"},{\"ch_roll\":\"200\",\"ch_id\":\"390239\",\"ch_name\":\"测试章节3\",\"ch_cre_time\":\"2011-05-31 15:46:17\",\"ch_update\":\"2011-05-31 17:46:17\",\"ch_size\":\"3517\",\"ch_vip\":\"0\",\"ch_roll_name\":\"正文\"}]";

        List<Map<String, String>> list = (List<Map<String, String>>) JSONArray.toList(JSONArray.fromObject(content),
                                                                                      new HashMap<String, String>(),
                                                                                      new JsonConfig());

        return list;

    }

    public String getChapterContent(String bookId, String chapterId) {

        OAuth oauth = new OAuth(OAUTH_CONSUMER_KEY, CONSUMER_SECRET);

        String oauth_timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        HttpInvokeRequest request = new HttpInvokeRequest(BASE_URL, "POST");
        List<DefaultNameValuePair> params = new ArrayList<DefaultNameValuePair>();

        params.add(buildParam("oauth_consumer_key", OAUTH_CONSUMER_KEY));
        params.add(buildParam("oauth_timestamp", oauth_timestamp));
        params.add(buildParam("format", "json"));

        params.add(buildParam("action", "getChapterContent"));
        params.add(buildParam("bk_id", bookId));
        params.add(buildParam("ch_id", chapterId));

        StringBuilder base = new StringBuilder("POST").append("&").append(OAuth.encode(BASE_URL)).append("&");
        base.append(OAuth.encode(OAuth.normalizeRequestParameters(params)));
        String oauthBaseString = base.toString();
        String signature = oauth.generateSignature(oauthBaseString);
        request.addParams(params);
        request.addParam("oauth_signature", signature);

        HttpInvokeResponse response = httpClient.invoke(request);
        String content = response.getContent();

//        logger.info("invoke chapter_content : " + content);

        JSONObject jsonMap = JSONObject.fromObject(content, new JsonConfig());

        return jsonMap.getString("content");

    }

    private DefaultNameValuePair buildParam(String key, String value) {
        DefaultNameValuePair onvp = new DefaultNameValuePair(key, value);
        return onvp;
    }

    public static void main(String[] args) {
        ZhulangHttpClient client = new ZhulangHttpClient();
        client.getChapterInfo("166085");
    }

}
