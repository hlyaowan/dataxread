package com.taobao.datax.plugins.reader.hongxiureader;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.taobao.datax.common.http.HongxiuHttpClient;



public class HongxiuUtils {
    
    private static HongxiuHttpClient client =new HongxiuHttpClient();
    
    @SuppressWarnings("unchecked")
    public static List<HongxiuContentInfo> getHongxiuBookList() {
        String xmlContent = client.getHongxiuClientBookList();
        List<HongxiuContentInfo> contentList = Collections.EMPTY_LIST;
        try {
            Document document = string2Doc(xmlContent);
            Element root = document.getRootElement();
            Element booksElement = root.getChild("books");
            List<Element> books = booksElement.getChildren();
            if (CollectionUtils.isNotEmpty(books)) {
                contentList = new ArrayList<HongxiuContentInfo>(books.size());
                for (Element element : books) {
                    HongxiuContentInfo contentInfo = new HongxiuContentInfo();
                    Element bookid = element.getChild("bookid");
                    Element bookName = element.getChild("bookName");
                    Element bookAuthor = element.getChild("BookAuthor");
                    Element bookfinishState = element.getChild("BookfinishState");
                    Element ImageUrl = element.getChild("ImageUrl");
                    Element BookisVip = element.getChild("BookisVip");
                    Element bookDescription = element.getChild("BookDescription");
                    Element bookCreatetime = element.getChild("BookCreatetime");
                    Element BookWordsCount = element.getChild("BookWordsCount");
                    contentInfo.setBookAuthor(bookAuthor.getText());
                    contentInfo.setBookCreatetime(bookCreatetime.getText());
                    contentInfo.setBookDescription(bookDescription.getText());
                    if (bookfinishState.getText() != null) {
                        contentInfo.setBookfinishState(Integer.parseInt(bookfinishState.getText()));
                    }
                    contentInfo.setBookId(Integer.parseInt(bookid.getText()));
                    contentInfo.setBookisVip(BookisVip.getText());
                    contentInfo.setBookName(bookName.getText());
                    if (BookWordsCount.getText() != null) {
                        contentInfo.setBookWordsCount(Integer.parseInt(BookWordsCount.getText()));
                    }
                    contentInfo.setImageUrl(ImageUrl.getText());
                    contentList.add(contentInfo);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return contentList;
    }
    
    /**
     * 字符串转换为DOCUMENT
     * 
     * @param xmlStr
     *            字符串
     * @return doc JDOM的Document
     * @throws Exception
     */
    public static Document string2Doc(String xmlStr) throws Exception {
        Reader in = new StringReader(xmlStr);
        Document doc = (new SAXBuilder()).build(in);
        return doc;
    }
}
