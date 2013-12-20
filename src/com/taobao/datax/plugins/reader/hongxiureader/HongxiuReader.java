package com.taobao.datax.plugins.reader.hongxiureader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.taobao.datax.common.http.HongxiuHttpClient;
import com.taobao.datax.common.plugin.Line;
import com.taobao.datax.common.plugin.LineSender;
import com.taobao.datax.common.plugin.PluginStatus;
import com.taobao.datax.common.plugin.Reader;


public class HongxiuReader extends Reader {
    private static Logger logger = Logger.getLogger(HongxiuReader.class);

    private HongxiuHttpClient httpClient = new HongxiuHttpClient();


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
        logger.info("begin startLoad HongxiuReader");
        int ret = PluginStatus.SUCCESS.value();
        //循环执行
        while (true) {
            //获取书库内容
            String  contentxml = httpClient.getHongxiuClientBookList();
            if (StringUtils.isBlank(contentxml)) break;
            Line line = sender.createLine();
            line.addField(contentxml);
            sender.sendToWriter(line);
        }
        return ret;
    }


    @Override
    public int finish() {
        return 0;
    }


    public static void main(String[] args) {
        HongxiuReader reader = new HongxiuReader();
        reader.startRead(null);
    }
}
