package com.taobao.datax.plugins.writer.netwaywriter;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.taobao.datax.common.http.NetwayHttpClient;


public class NetwayContentService {
    private static NetwayHttpClient client = new NetwayHttpClient();
    private Logger logger = Logger.getLogger(NetwayContentService.class);


    @SuppressWarnings("unchecked")
    public List<CPBookEntity> getShuChengList() {
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
    @SuppressWarnings("unchecked")
    public List<CPBookEntity> getShuChengList(String xmlContent) {
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

    @SuppressWarnings("unchecked")
    public List<CPChapterInfo> getChapterInfoList(String bookId) {
        String xmlchapter = client.getChapterList(bookId);
        List<CPChapterInfo> list = null;
        try {
            Document document = string2Doc(xmlchapter);
            Element root = document.getRootElement();
            Element result = root.getChild("result");
            Element chapterlist = result.getChild("chapters");
            List<Element> chaptElements = chapterlist.getChildren();
            if (CollectionUtils.isNotEmpty(chaptElements)) {
                list = new ArrayList<CPChapterInfo>(chaptElements.size());
                for (Element element : chaptElements) {
                    CPChapterInfo entity = new CPChapterInfo();
                    Element chapterNum = element.getChild("chapterNum");
                    Element chapterId = element.getChild("chapterId");
                    Element chapterName = element.getChild("chapterName");
                    Element isVip = element.getChild("isVip");
                    if (StringUtils.isNotBlank(chapterId.getText())) {
                        entity.setChapterId(Integer.parseInt(chapterId.getText()));
                    }
                    entity.setChapterName(chapterName.getText());
                    if (StringUtils.isNotBlank(chapterNum.getText())) {
                        entity.setChapterNum(Integer.parseInt(chapterNum.getText()));
                    }

                    entity.setIsVip(isVip.getText());
                    list.add(entity);
                }
            }
        }
        catch (Exception e) {
            logger.error("xml parse exception:" + e.getMessage());
            return Collections.EMPTY_LIST;
        }
        logger.info("list:"+list.size());
        return list;
    }


    public CPBookContent getBookContent(String bookId) {
        String xmlcontent = client.getBookInfo(bookId);
        CPBookContent content =null;
        try {
            Document document =string2Doc(xmlcontent);
            Element root =document.getRootElement();
            Element resultElement =root.getChild("result");
            Element book =resultElement.getChild("book");
            
            Element id =book.getChild("id");
            Element finishflag =book.getChild("finishflag");
            Element bookName =book.getChild("bookName");
            Element detail =book.getChild("detail");
            Element bookType =book.getChild("bookType");
            Element keyWord =book.getChild("keyWord");
            Element authorName =book.getChild("authorName");
            Element imgPath =book.getChild("imgPath");
            Element maxChapter =book.getChild("maxChapter");
            Element channelType =book.getChild("channelType");
            content =new CPBookContent();
            content.setAuthorName(authorName.getText());
            content.setBookName(bookName.getText());
            content.setBookType(bookType.getText());
            if(StringUtils.isNotBlank(channelType.getText())){
                content.setChannelType(Integer.parseInt(channelType.getText()));
            }
            content.setDetail(detail.getText());
            if(StringUtils.isNotBlank(finishflag.getText())){
                content.setFinishflag(Integer.parseInt(finishflag.getText()));
            }
            if(StringUtils.isNotBlank(id.getText())){
                content.setId(Integer.parseInt(id.getText()));
            }
            content.setImgPath(imgPath.getText());
            content.setKeyWord(keyWord.getText());
            if(StringUtils.isNotBlank(maxChapter.getText())){
                content.setMaxChapter(Integer.parseInt(maxChapter.getText()));
            }
        }
        catch (Exception e) {
            logger.error("getcontentinfo xml:"+e.getMessage());
            return null;
        }
        return content;
    }

    public CPChapterInfo getChapterInfo(String bookId,String chapterId) {
        String xmlcontent = client.getChapterContent(bookId, chapterId);
        CPChapterInfo chapterInfo =null;
        try {
            Document document =string2Doc(xmlcontent);
            Element root =document.getRootElement();
            Element resultElement =root.getChild("result");
            Element chapters =resultElement.getChild("chapters");
            Element chapter =chapters.getChild("chapter");
            
            Element chaId =chapter.getChild("chapterId");
            Element chapterName =chapter.getChild("chapterName");
            Element chapterSize =chapter.getChild("chapterSize");
            Element chapterContent =chapter.getChild("chapterContent");
            Element isVip =chapter.getChild("isVip");
            chapterInfo =new CPChapterInfo();
            chapterInfo.setChapterId(Integer.parseInt(chapterId));
            chapterInfo.setChapterContent(chapterContent.getText());
            chapterInfo.setChapterName(chapterName.getText());
            if(StringUtils.isNotBlank(chapterSize.getText())){
                chapterInfo.setChapterSize(Integer.parseInt(chapterSize.getText()));
            }
            
            chapterInfo.setIsVip(isVip.getText());
        }
        catch (Exception e) {
            logger.error("chapterInfo xml:"+e.getMessage());
            return null;
        }
        return chapterInfo;
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
