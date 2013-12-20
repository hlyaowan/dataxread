package com.taobao.datax.plugins.reader.netwayreader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.taobao.datax.common.http.NetwayHttpClient;
import com.taobao.datax.common.plugin.Line;
import com.taobao.datax.common.plugin.LineSender;
import com.taobao.datax.common.plugin.PluginStatus;
import com.taobao.datax.common.plugin.Reader;

public class NetwayReader extends Reader {

    private static Logger    logger     = Logger.getLogger(NetwayReader.class);

    private NetwayHttpClient httpClient = new NetwayHttpClient();

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
        // 循环执行
        while (true) {
            // 获取书库内容
            String contentxml = httpClient.getShukuList();
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
        NetwayReader reader = new NetwayReader();
        reader.startRead(null);
    }
}
