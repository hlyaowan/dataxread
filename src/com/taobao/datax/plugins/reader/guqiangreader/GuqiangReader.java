package com.taobao.datax.plugins.reader.guqiangreader;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.taobao.datax.common.plugin.Line;
import com.taobao.datax.common.plugin.LineSender;
import com.taobao.datax.common.plugin.PluginStatus;
import com.taobao.datax.common.plugin.Reader;

public class GuqiangReader extends Reader {

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
        //不涉及分页
        List<BookList> contentList = ReadUtils.getGuqiangtBookList();
        for (BookList bookList : contentList) {
            if (bookList != null) {
                JSONObject content = JSONObject.fromObject(bookList);
                Line line = sender.createLine();
                line.addField(content.toString());
                sender.sendToWriter(line);
                sender.flush();
            }
        }
        logger.info("============begin read guqiang data==========list:"+contentList.size());
        return ret;
    }

    @Override
    public int finish() {
        return 0;
    }

    public static void main(String[] args) {
        List<BookList> contentList = ReadUtils.getGuqiangtBookList();

        for (BookList bookList : contentList) {
            if (bookList != null) {
                JSONObject content = JSONObject.fromObject(bookList);
                System.out.println((content.toString()));
            }
        }
    }
}
