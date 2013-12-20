package com.taobao.datax.plugins.reader.guqiangreader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.taobao.datax.common.http.GuqiangHttpClient;
import com.taobao.datax.common.plugin.Line;
import com.taobao.datax.common.plugin.LineSender;
import com.taobao.datax.common.plugin.PluginStatus;
import com.taobao.datax.common.plugin.Reader;

public class GuqiangReader extends Reader {

    private GuqiangHttpClient httpClient = new GuqiangHttpClient();
    private static Logger     logger     = Logger.getLogger(GuqiangReader.class);

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
        logger.info("begin startLoad GuqiangReader");
        int ret = PluginStatus.SUCCESS.value();
        // 循环执行
        while (true) {
            // 获取书库内容
            String xmlcontent = httpClient.getGuqiangClientBookList("", "", null);
            if (StringUtils.isBlank(xmlcontent)) break;
            Line line = sender.createLine();
            line.addField(xmlcontent);
            sender.sendToWriter(line);
        }
        return ret;
    }

    @Override
    public int finish() {
        return 0;
    }

    public static void main(String[] args) {
        GuqiangReader reader = new GuqiangReader();
        reader.startRead(null);
    }
}
