package com.taobao.datax.plugins.writer.guqiangwriter;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.taobao.datax.common.http.GuqiangHttpClient;


public class GuqiangContentService {

    private static GuqiangHttpClient client    = new GuqiangHttpClient();
    private static String            CATALOGID = "";
    private static String            PAGESIZE  = "";
    private static Logger            logger    = Logger.getLogger(GuqiangContentService.class);

    @SuppressWarnings("unchecked")
    public List<BookList> getGuqiangClientBookList(Integer page) {
        String xmlContent = client.getGuqiangClientBookList(CATALOGID, PAGESIZE, page);
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
    
    @SuppressWarnings("unchecked")
    public List<BookList> getGuqiangClientBookList(Integer page,String xmlContent) {
//        String xmlContent = client.getGuqiangClientBookList(CATALOGID, PAGESIZE, page);
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

    @SuppressWarnings("unchecked")
    public List<ChapterList> getGuqiangChapterList(String bookId, int page) {
        String xmlContent = client.getGuqiangClientChapterList(bookId, "", String.valueOf(page));
        List<ChapterList> chapterLists = null;
        try {
            Document document = string2Doc(xmlContent);
            Element root = document.getRootElement();
            List<Element> list = root.getChildren();
            if (CollectionUtils.isNotEmpty(list)) {
                chapterLists = new ArrayList<ChapterList>(list.size());
                for (Element element : list) {
                    ChapterList chapter = new ChapterList();
                    Element chapterId = element.getChild("chapterId");
                    Element chapterName = element.getChild("chapterName");
                    Element chapterSize = element.getChild("chapterSize");
                    Element isVip = element.getChild("isVip");
                    Element price = element.getChild("price");
                    chapter.setChapterId(chapterId.getText());
                    chapter.setChapterName(chapterName.getText());
                    chapter.setChapterSize(chapterSize.getText());
                    chapter.setIsVip(isVip.getText());
                    chapter.setPrice(price.getText());
                    chapterLists.add(chapter);
                }
            }
        } catch (Exception e) {
            logger.error("getGuqiangChapterList" + e.getMessage());
        }
        logger.info("chapterLists list:"+chapterLists.size());
        return chapterLists;
    }

    public String getChapterContent(String bookId, String chapterId) {
        String content = StringUtils.EMPTY;
        String xmlcontent = client.getGuqiangClientChapterInfo(bookId, chapterId);
        Document document;
        try {
            document = string2Doc(xmlcontent);
            Element root = document.getRootElement();
            Element chapter =root.getChild("chapter");
            Element contentElement = chapter.getChild("content");
            content = contentElement.getText();
        } catch (Exception e) {
            logger.error("getChapterContent" + e.getMessage());
        }
        logger.info("content:"+content);
        return content;
    }
    
    public Boolean isUpdateChapter(String bookId, String maxChapterId) {
        String xmlcontent = client.checkUpdateBookChapter(bookId, maxChapterId);
        Document document;
        try {
            document = string2Doc(xmlcontent);
            Element root = document.getRootElement();
            Element book = root.getChild("book");
            Element newNum = book.getChild("newNum");
            String maxnum =newNum.getText();
            if(Integer.parseInt(maxnum)>0){
                return true;
            }
        } catch (Exception e) {
            logger.error("getChapterContent" + e.getMessage());
        }
        return false;
    }

    public GQcontentInfo getContentInfo(String bookId) {
        String xmlcontent = client.getGuqiangClientBookInfo(bookId);
        Document document;
        GQcontentInfo contentInfo = new GQcontentInfo();
        try {
            document = string2Doc(xmlcontent);
            Element root = document.getRootElement();
            Element bookinfo =root.getChild("bookinfo");
            Element id = bookinfo.getChild("id");
            Element bookName = bookinfo.getChild("bookName");
            Element detail = bookinfo.getChild("detail");
            Element bookType = bookinfo.getChild("bookType");
            Element keyWord = bookinfo.getChild("keyWord");
            Element bookStatus = bookinfo.getChild("bookStatus");
            Element subTitle = bookinfo.getChild("subTitle");
            Element size = bookinfo.getChild("size");
            Element author = bookinfo.getChild("author");
            Element isVip = bookinfo.getChild("isVip");
            Element maxFree = bookinfo.getChild("maxFree");
            Element price = bookinfo.getChild("price");
            Element isFee = bookinfo.getChild("isFee");
            Element weekVisit = bookinfo.getChild("weekVisit");
            Element monthVisit = bookinfo.getChild("monthVisit");
            Element allVisit = bookinfo.getChild("allVisit");
            Element bookTypeName = bookinfo.getChild("bookTypeName");
            Element imagePath = bookinfo.getChild("imagePath");
            Element imageMinPath = bookinfo.getChild("imageMinPath");
            Element imageMidPath = bookinfo.getChild("imageMidPath");
            if (StringUtils.isNotBlank(allVisit.getText())) {
                contentInfo.setAllVisit(Integer.parseInt(allVisit.getText()));
            }
            contentInfo.setAuthor(author.getText());
            contentInfo.setBookName(bookName.getText());
            if (StringUtils.isNotBlank(bookStatus.getText())) {
                contentInfo.setBookStatus(Integer.parseInt(bookStatus.getText()));
            }
            if (StringUtils.isNotBlank(bookType.getText())) {
                contentInfo.setBookType(Integer.parseInt(bookType.getText()));
            }
            contentInfo.setBookTypeName(bookTypeName.getText());
            contentInfo.setDetail(detail.getText());
            contentInfo.setId(id.getText());
            contentInfo.setImageMidPath(imageMidPath.getText());
            contentInfo.setImageMinPath(imageMinPath.getText());
            contentInfo.setImagePath(imagePath.getText());
            if (StringUtils.isNotBlank(isFee.getText())) {
                contentInfo.setIsFee(Integer.parseInt(isFee.getText()));
            }
            if (StringUtils.isNotBlank(isVip.getText())) {
                contentInfo.setIsVip(Integer.parseInt(isVip.getText()));
            }
            contentInfo.setKeyWord(keyWord.getText());
            if (StringUtils.isNotBlank(maxFree.getText())) {
                contentInfo.setMaxFree(Integer.parseInt(maxFree.getText()));
            }
            if (StringUtils.isNotBlank(monthVisit.getText())) {
                contentInfo.setMonthVisit(Integer.parseInt(monthVisit.getText()));
            }
            if (StringUtils.isNotBlank(price.getText())) {
                contentInfo.setPrice(Integer.parseInt(price.getText()));
            }
            if (StringUtils.isNotBlank(size.getText())) {
                contentInfo.setSize(Integer.parseInt(size.getText()));
            }
            contentInfo.setSubTitle(subTitle.getText());
            if (StringUtils.isNotBlank(weekVisit.getText())) {
                contentInfo.setWeekVisit(Integer.parseInt(weekVisit.getText()));
            }
        } catch (Exception e) {
            logger.error("getContentInfo" + e.getMessage());
        }
        return contentInfo;
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
