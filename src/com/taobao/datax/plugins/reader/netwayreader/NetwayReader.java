package com.taobao.datax.plugins.reader.netwayreader;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.taobao.datax.common.http.NetwayHttpClient;
import com.taobao.datax.common.plugin.Line;
import com.taobao.datax.common.plugin.LineSender;
import com.taobao.datax.common.plugin.PluginStatus;
import com.taobao.datax.common.plugin.Reader;
public class NetwayReader extends Reader {

    private static Logger    logger     = Logger.getLogger(NetwayReader.class);

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
        logger.info("begin startLoad netwayreader");
        int ret = PluginStatus.SUCCESS.value();
        List<CPBookEntity> contentList = WangwenUtils.getShuChengList();
        for (CPBookEntity bookInfo : contentList) {
            if (bookInfo != null) {
                JSONObject content = JSONObject.fromObject(bookInfo);
                Line line = sender.createLine();
                line.addField(content.toString());
                sender.flush();
                sender.sendToWriter(line);
            }
        }
        logger.info("begin startLoad netwayreader======list:"+contentList.size());
        return ret;
    }

    @Override
    public int finish() {
        return 0;
    }

    public static void main(String[] args) {
        List<CPBookEntity> contentList = WangwenUtils.getShuChengList();
        for (CPBookEntity bookInfo : contentList) {
            if (bookInfo != null) {
                JSONObject content = JSONObject.fromObject(bookInfo);
                System.out.println(content.toString());
                //{"bookId":"188594","finishflag":"1","maxChapter":"191"}
            }
        }
    }
}
