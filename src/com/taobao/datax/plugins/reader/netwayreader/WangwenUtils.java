package com.taobao.datax.plugins.reader.netwayreader;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.taobao.datax.common.http.NetwayHttpClient;


public class WangwenUtils {
    
    private static Logger logger =Logger.getLogger(WangwenUtils.class);
    private static NetwayHttpClient client =new NetwayHttpClient();
    
    
    @SuppressWarnings("unchecked")
    public static List<CPBookEntity> getShuChengList() {
        String xmlContent = client.getShukuList();
        List<CPBookEntity> list = null;
        try {
            Document document = string2Doc(xmlContent);
            Element root = document.getRootElement();
            Element result = root.getChild("result");
            Element booklist = result.getChild("booklist");

           
            List<Element> booksElements = booklist.getChildren();
            if (CollectionUtils.isNotEmpty(booksElements)) {
                list = new ArrayList<CPBookEntity>(booksElements.size());
                for (Element element : booksElements) {
                    CPBookEntity entity = new CPBookEntity();
                    Element bookid = element.getChild("id");
                    Element finishflag = element.getChild("finishflag");
                    Element maxChapter = element.getChild("maxChapter");
                    entity.setBookId(bookid.getText());
                    entity.setFinishflag(finishflag.getText());
                    entity.setMaxChapter(maxChapter.getText());
                    list.add(entity);
                }
            }
        }
        catch (Exception e) {
            logger.error("xml parse exception:" + e.getMessage());
        }
        logger.info("list count:"+list.size());
        return list;

    }
    
    /**
     * 字符串转换为DOCUMENT
     * @param xmlStr
     * @return doc JDOM的Document
     * @throws Exception
     */
    public static Document string2Doc(String xmlStr) throws Exception {
        Reader in = new StringReader(xmlStr);
        Document doc = (new SAXBuilder()).build(in);
        return doc;
    }
    
}
