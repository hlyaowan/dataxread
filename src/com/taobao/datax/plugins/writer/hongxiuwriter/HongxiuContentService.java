package com.taobao.datax.plugins.writer.hongxiuwriter;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.taobao.datax.common.http.HongxiuHttpClient;


public class HongxiuContentService {

    public static HongxiuHttpClient client = new HongxiuHttpClient();


    @SuppressWarnings("unchecked")
    public List<HongxiuContentInfo> getHongxiuBookList(String xmlContent) {
        // String xmlContent =client.getHongxiuClientBookList();
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


    @SuppressWarnings("unchecked")
    public List<HongxiuContentInfo> getHongxiuBookList() {
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


    public HongxiuContentInfo getHongxiuContentInfo(int bookId) {
        String xmlContent = client.getHongxiuBookInfo(String.valueOf(bookId));
        Document document;
        HongxiuContentInfo contentInfo = new HongxiuContentInfo();
        try {
            document = string2Doc(xmlContent);
            Element root = document.getRootElement();
            Element bookInfoElement = root.getChild("bookinfo");
            Element bookName = bookInfoElement.getChild("BookName");
            Element bookCpid = bookInfoElement.getChild("BookCpid");
            Element bookAuthor = bookInfoElement.getChild("BookAuthor");
            Element bookCategory = bookInfoElement.getChild("BookCategory");
            Element bookfinishState = bookInfoElement.getChild("BookfinishState");
            Element imageUrl = bookInfoElement.getChild("ImageUrl");
            Element bookisVip = bookInfoElement.getChild("BookisVip");
            Element bookDescription = bookInfoElement.getChild("BookDescription");
            Element bookCreatetime = bookInfoElement.getChild("BookCreatetime");
            Element bookWordsCount = bookInfoElement.getChild("BookWordsCount");
            Element bookKey = bookInfoElement.getChild("BookKey");

            contentInfo.setBookAuthor(bookAuthor.getText());
            contentInfo.setBookCategory(bookCategory.getText());
            contentInfo.setBookCpid(bookCpid.getText());
            contentInfo.setBookCreatetime(bookCreatetime.getText());
            contentInfo.setBookDescription(bookDescription.getText());
            if (bookfinishState.getText() != null) {
                contentInfo.setBookfinishState(Integer.parseInt(bookfinishState.getText()));
            }
            contentInfo.setBookId(bookId);
            contentInfo.setBookisVip(bookisVip.getText());
            contentInfo.setBookKey(bookKey.getText());
            contentInfo.setBookName(bookName.getText());
            if (bookWordsCount.getText() != null) {
                contentInfo.setBookWordsCount(Integer.parseInt(bookWordsCount.getText()));
            }
            contentInfo.setImageUrl(imageUrl.getText());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return contentInfo;
    }


    public HongxiuChapterInfo getChapterInfo(String bookId, String chapterId) {
        String xmlContent = client.getHongxiuChapterInfo(bookId, chapterId);
        Document document;
        HongxiuChapterInfo chapterinfo = new HongxiuChapterInfo();
        if (StringUtils.isNotBlank(xmlContent)) {
            try {
                document = string2Doc(xmlContent);
                Element root = document.getRootElement();
                Element bookInfoElement = root.getChild("chapter");
                Element bookChapterName = bookInfoElement.getChild("BookChapterName");
                Element bookChapterid = bookInfoElement.getChild("BookChapterid");
                Element bookChapterisVip = bookInfoElement.getChild("BookChapterisVip");
                Element bookChapterTxt = bookInfoElement.getChild("BookChapterTxt");
                Element bookChapterPrice = bookInfoElement.getChild("BookChapterPrice");
                Element bookChapterDate = bookInfoElement.getChild("BookChapterDate");

                chapterinfo.setBookChapterDate(bookChapterDate.getText());
                chapterinfo.setBookChapterId(bookChapterid.getText());
                chapterinfo.setBookChapterisVip(bookChapterisVip.getText());
                chapterinfo.setBookChapterName(bookChapterName.getText());
                chapterinfo.setBookChapterPrice(bookChapterPrice.getText());
                chapterinfo.setBookChapterTxt(bookChapterTxt.getText());

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return chapterinfo;
    }


    @SuppressWarnings("unchecked")
    public List<HongxiuChapterInfo> getChapterInfos(String bookId) {
        String xmlContent = client.getHongxiuChapterList(String.valueOf(bookId));
        List<HongxiuChapterInfo> chapterInfos = Collections.EMPTY_LIST;
        Document document;
        try {
            document = string2Doc(xmlContent);
            Element root = document.getRootElement();
            // Element chapters =root.getChild("chapter");
            List<Element> chapterinfosElements = root.getChildren();
            if (CollectionUtils.isNotEmpty(chapterinfosElements)) {
                chapterInfos = new ArrayList<HongxiuChapterInfo>(chapterinfosElements.size());
                for (Element element : chapterinfosElements) {
                    HongxiuChapterInfo chapterInfo = new HongxiuChapterInfo();
                    Element chapterId = element.getChild("chapterId");
                    Element chapterName = element.getChild("chapterName");
                    Element BookChapterDate = element.getChild("BookChapterDate");
                    Element chapterSize = element.getChild("chapterSize");

                    chapterInfo.setBookChapterDate(BookChapterDate.getText());
                    chapterInfo.setBookChapterId(chapterId.getText());
                    chapterInfo.setBookChapterName(chapterName.getText());
                    chapterInfo.setChapterSize(chapterSize.getText());
                    chapterInfos.add(chapterInfo);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return chapterInfos;
    }


    public int getChapterInfosCount(String bookId) {
        String xmlContent = client.getHongxiuChapterList(String.valueOf(bookId));
        int count = 0;
        Document document;
        try {
            document = string2Doc(xmlContent);
            Element root = document.getRootElement();
            List<Element> chapterinfosElements = root.getChildren();
            if (CollectionUtils.isNotEmpty(chapterinfosElements)) {
                count = chapterinfosElements.size();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
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


    public static void main(String[] args) {
        HongxiuContentService service = new HongxiuContentService();
        // service.getHongxiuBookList();
        // System.out.println(service.getHongxiuContentInfo(29077));
        // System.out.println(service.getChapterInfo("29077", "137010"));
        System.out.println(service.getChapterInfos("29077"));
    }
}
