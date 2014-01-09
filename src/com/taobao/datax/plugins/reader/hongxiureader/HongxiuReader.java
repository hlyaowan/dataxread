package com.taobao.datax.plugins.reader.hongxiureader;

import java.util.List;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import com.taobao.datax.common.plugin.Line;
import com.taobao.datax.common.plugin.LineSender;
import com.taobao.datax.common.plugin.PluginStatus;
import com.taobao.datax.common.plugin.Reader;


public class HongxiuReader extends Reader {
    private static Logger logger = Logger.getLogger(HongxiuReader.class);

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
        //不涉及分页
        List<HongxiuContentInfo> contentList = HongxiuUtils.getHongxiuBookList();
        for (HongxiuContentInfo bookInfo : contentList) {
            if (bookInfo != null) {
                JSONObject content = JSONObject.fromObject(bookInfo);
                Line line = sender.createLine();
                line.addField(content.toString());
                sender.sendToWriter(line);
                sender.flush();
                System.out.println("send json data============="+content.toString());
            }
        }
        System.out.println("send data============条数"+contentList.size());
        return ret;
    }


    @Override
    public int finish() {
        return 0;
    }


    public static void main(String[] args) {
        List<HongxiuContentInfo> contentList = HongxiuUtils.getHongxiuBookList();
        for (HongxiuContentInfo bookInfo : contentList) {
            if (bookInfo != null) {
                JSONObject content = JSONObject.fromObject(bookInfo);
                System.out.println(content.toString());
            }
        }
    }
}
