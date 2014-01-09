package com.taobao.datax.plugins.reader.guqiangreader;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import com.taobao.datax.common.http.GuqiangHttpClient;


public class ReadUtils {
    
    private static GuqiangHttpClient client    = new GuqiangHttpClient();
    private static String            CATALOGID = "";
    private static String            PAGESIZE  = "";
    private static String            PAGE  = "";
    private static Logger            logger    = Logger.getLogger(ReadUtils.class);
    
    @SuppressWarnings("unchecked")
    public static List<BookList> getGuqiangtBookList() {
        String xmlContent = client.getGuqiangClientBookList(CATALOGID, PAGESIZE, PAGE);
        List<BookList> bookLists = null;
        try {
            Document document = string2Doc(xmlContent);
            Element root = document.getRootElement();
            List<Element> list = root.getChildren();
            if (CollectionUtils.isNotEmpty(list)) {
                bookLists = new ArrayList<BookList>(list.size());
                for (Element element : list) {
                    BookList book = new BookList();
                    Element id = element.getChild("id");
                    Element bookName = element.getChild("bookName");
                    Element categoryId = element.getChild("categoryId");
                    Element categoryPid = element.getChild("categoryPid");
                    book.setBookName(bookName.getText());
                    book.setCategoryId(categoryId.getText());
                    book.setCategoryPid(categoryPid.getText());
                    book.setId(Integer.parseInt(id.getText()));
                    bookLists.add(book);
                }
            }
        } catch (Exception e) {
            logger.error("getGuqiangClientBookList" + e.getMessage());
        }
        logger.info("book list:"+bookLists.size());
        return bookLists;
    }
    
    /**
     * 字符串转换为DOCUMENT
     * 
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
