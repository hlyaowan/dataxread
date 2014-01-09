/*
 * 
 */
// Created on 2013-11-30

package com.taobao.datax.plugins.reader.zhulangreader;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.taobao.datax.common.http.ZhulangHttpClient;
import com.taobao.datax.common.plugin.Line;
import com.taobao.datax.common.plugin.LineSender;
import com.taobao.datax.common.plugin.PluginStatus;
import com.taobao.datax.common.plugin.Reader;

/**
 * 逐浪内容抓取
 * 
 * @author joe.chen
 */
public class ZhulangReader extends Reader {

    private static Logger     logger     = Logger.getLogger(ZhulangReader.class);

    private ZhulangHttpClient httpClient = new ZhulangHttpClient();

    @Override
    public int init() {
        return 0;
    }

    @Override
    public int connect() {
        return 0;
    }

    @Override
    public int startRead(LineSender sender) {
        logger.info("begin startLoad zhulangreader");
        int ret = PluginStatus.SUCCESS.value();

        int page = 1;
        while (true) {
            List<Map<String, String>> contentList = httpClient.getContentInfo(page++);

            if (CollectionUtils.isEmpty(contentList)) break;

            for (Map<String, String> content : contentList) {

                if (content != null && (!content.isEmpty())) {
                    JSONObject json = JSONObject.fromObject(content);

                    Line line = sender.createLine();

                    line.addField(json.toString());
                    sender.sendToWriter(line);
                    sender.flush();

                }

            }

        }

        return ret;
    }

    @Override
    public int finish() {
        // TODO Auto-generated method stub
        return 0;
    }

    public static void main(String[] args) {
        ZhulangReader reader = new ZhulangReader();
        reader.startRead(null);
    }

}
