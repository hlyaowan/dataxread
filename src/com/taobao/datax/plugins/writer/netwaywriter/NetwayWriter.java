package com.taobao.datax.plugins.writer.netwaywriter;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.taobao.datax.common.plugin.Line;
import com.taobao.datax.common.plugin.LineReceiver;
import com.taobao.datax.common.plugin.PluginParam;
import com.taobao.datax.common.plugin.PluginStatus;
import com.taobao.datax.common.plugin.Writer;
import com.taobao.datax.common.util.DateUtils;
import com.taobao.datax.plugins.common.DBSource;
import com.taobao.datax.plugins.writer.mysqlwriter.ParamKey;

public class NetwayWriter extends Writer {

    private static List<String>         encodingConfigs = null;

    private static NetwayContentService service         = new NetwayContentService();
    private static int                  WORDVALUE       = 0;
    private static String               VOLUMEID        = "";
    private static String               VOLUMENAME      = "";

    static {
        encodingConfigs = new ArrayList<String>();
        encodingConfigs.add("character_set_client");
        encodingConfigs.add("character_set_connection");
        encodingConfigs.add("character_set_database");
        encodingConfigs.add("character_set_results");
        encodingConfigs.add("character_set_server");
    }

    private static Map<String, String>  encodingMaps    = null;
    static {
        encodingMaps = new HashMap<String, String>();
        encodingMaps.put("utf-8", "UTF8");
    }

    private static final String         SOURCE_ID       = "netway";

    private String                      username        = null;

    private String                      password        = null;

    private String                      host            = null;

    private String                      port            = null;

    private String                      dbname          = null;

    private String                      pre             = null;

    private String                      post            = null;

    private String                      encoding        = null;

    private String                      set             = "";

    /* since load-data mechanisms only allowes one thread to load data */
    private int                         concurrency     = 1;

    private String                      sourceUniqKey   = "";

    private static String               DRIVER_NAME     = "com.mysql.jdbc.Driver";

    private Connection                  connection      = null;

    private Logger                      logger          = Logger.getLogger(NetwayWriter.class);

    private Properties genProperties() {
        Properties p = new Properties();
        p.setProperty("driverClassName", DRIVER_NAME);
        p.setProperty("url", String.format("jdbc:mysql://%s:%s/%s", this.host, this.port, this.dbname));
        p.setProperty("username", this.username);
        p.setProperty("password", this.password);
        p.setProperty("maxActive", String.valueOf(this.concurrency + 2));

        return p;
    }

    @Override
    public int prepare(PluginParam param) {
        this.setParam(param);
        DBSource.register(this.sourceUniqKey, this.genProperties());

        return PluginStatus.SUCCESS.value();
    }

    @Override
    public int init() {
        this.username = param.getValue(ParamKey.username, "");
        this.password = param.getValue(ParamKey.password, "");
        this.host = param.getValue(ParamKey.ip);
        this.port = param.getValue(ParamKey.port, "3306");
        this.dbname = param.getValue(ParamKey.dbname);
        this.pre = param.getValue(ParamKey.pre, "");
        this.post = param.getValue(ParamKey.post, "");
        this.encoding = param.getValue(ParamKey.encoding, "UTF8").toLowerCase();
        this.set = param.getValue(ParamKey.set, "");
        this.sourceUniqKey = DBSource.genKey(this.getClass(), host, port, dbname);

        if (!StringUtils.isBlank(this.set)) {
            this.set = "set " + this.set;
        }

        if (encodingMaps.containsKey(this.encoding)) {
            this.encoding = encodingMaps.get(this.encoding);
        }

//         this.username = "root";
//         this.password = "875154";
//         this.host = "localhost";
//         this.port = "3306";
//         this.dbname = "cp_content";
//         this.encoding = "utf-8";
//         this.sourceUniqKey = DBSource.genKey(this.getClass(), host, port,
//         dbname);

        return PluginStatus.SUCCESS.value();
    }

    @Override
    public int connect() {
        return 0;
    }

    @Override
    public int startWrite(LineReceiver receiver) {
        Line line = null;
        this.connection = DBSource.getConnection(this.sourceUniqKey);
        PreparedStatement contentQueryStmt = null;
        PreparedStatement contentInsertStmt = null;
        PreparedStatement contentUpdateStmt = null;
        PreparedStatement chapterQueryStmt = null;
        PreparedStatement chapterInsertStmt = null;
        PreparedStatement chapterUpdateStmt = null;
        try {
            contentQueryStmt = ((org.apache.commons.dbcp.DelegatingConnection) this.connection).getInnermostDelegate().prepareStatement("SELECT "
                                                                                                                                                + "INNER_BOOK_ID AS innerBookId,OUT_BOOK_ID AS outBookId,CREATE_TIME AS createTime,"
                                                                                                                                                + " AUTHOR_NAME AS authorName,BOOK_SIZE AS bookSize,BOOK_INTRO AS bookIntro,"
                                                                                                                                                + "BOOK_COVER AS bookCover, LAST_CHAPTER_UPDATE_TIME AS lastChapterUpdateTime,"
                                                                                                                                                + "BOOK_FULLFLAG AS bookFullFlag ,CHAPTER_TOTAL AS chapterTotal,SOURCE_ID AS sourceId "
                                                                                                                                                + "FROM CONTENT_INFO WHERE OUT_BOOK_ID=? AND SOURCE_ID='"
                                                                                                                                                + SOURCE_ID
                                                                                                                                                + "'");

            contentUpdateStmt = ((org.apache.commons.dbcp.DelegatingConnection) this.connection).getInnermostDelegate().prepareStatement("UPDATE CONTENT_INFO SET   "
                                                                                                                                                 + " AUTHOR_NAME = ?,BOOK_SIZE =?,BOOK_INTRO =?,"
                                                                                                                                                 + "BOOK_COVER=?, LAST_CHAPTER_UPDATE_TIME =?,"
                                                                                                                                                 + "BOOK_FULLFLAG=? ,CHAPTER_TOTAL = ? "
                                                                                                                                                 + " WHERE INNER_BOOK_ID=?");

            contentInsertStmt = ((org.apache.commons.dbcp.DelegatingConnection) this.connection).getInnermostDelegate().prepareStatement("INSERT INTO CONTENT_INFO("
                                                                                                                                                 + "OUT_BOOK_ID,CREATE_TIME,BOOK_NAME,AUTHOR_NAME,BOOK_SIZE,BOOK_INTRO,BOOK_COVER,LAST_CHAPTER_UPDATE_TIME,BOOK_FULLFLAG,CHAPTER_TOTAL,SOURCE_ID)"
                                                                                                                                                 + " VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                                                                                                                                         Statement.RETURN_GENERATED_KEYS);

            chapterQueryStmt = ((org.apache.commons.dbcp.DelegatingConnection) this.connection).getInnermostDelegate().prepareStatement("SELECT "
                                                                                                                                                + "OUT_BOOK_ID AS outBookId, OUT_CHAPTER_ID AS outChapterId,"
                                                                                                                                                + "INNER_CHAPTER_ID AS innerChapterId,INNER_BOOK_ID AS innerBookId,"
                                                                                                                                                + "CHAPTER_ ORDER AS chapterOrder,ROLL_NAME AS rollName,ROLL AS roll,CHAPTER_NAME AS chapterName,"
                                                                                                                                                + "CREATE_TIME AS createTime,UPDATE_TIME AS updateTime,CHAPTER_SIZE AS chapterSize,"
                                                                                                                                                + "IS_FREE AS isFree,CONTENT AS content  FROM CHAPTER_INFO WHERE OUT_BOOK_ID =? AND SOURCE_ID ='"
                                                                                                                                                + SOURCE_ID
                                                                                                                                                + "' ORDER BY CHAPTER_ORDER");

            chapterInsertStmt = ((org.apache.commons.dbcp.DelegatingConnection) this.connection).getInnermostDelegate().prepareStatement("INSERT INTO CHAPTER_INFO("
                                                                                                                                                 + "OUT_CHAPTER_ID,OUT_BOOK_ID,INNER_BOOK_ID,CHAPTER_ORDER,ROLL_NAME,ROLL,CHAPTER_NAME,CREATE_TIME,UPDATE_TIME,CHAPTER_SIZE,IS_FREE,CONTENT,SOURCE_ID)"
                                                                                                                                                 + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

            chapterUpdateStmt = ((org.apache.commons.dbcp.DelegatingConnection) this.connection).getInnermostDelegate().prepareStatement("UPDATE CHAPTER_INFO SET   "
                                                                                                                                                 + " CHAPTER_ORDER=?,ROLL_NAME = ?,ROLL =?,CHAPTER_NAME =?,"
                                                                                                                                                 + "UPDATE_TIME=?, CHAPTER_SIZE =?,"
                                                                                                                                                 + "IS_FREE=? ,CONTENT = ? "
                                                                                                                                                 + " WHERE INNER_CHAPTER_ID=?");
            if ((line = receiver.getFromReader()) != null) {
                logger.info("hongxiu writer starting");
                if (line.getFieldNum() > 0) {
                    String contentXml = line.getField(0);
                    List<CPBookEntity> contentInfos = service.getShuChengList(contentXml);
                    for (CPBookEntity bookEntity : contentInfos) {
                        logger.info("hongxiu writer line : " + bookEntity.getBookId());
                        CPBookContent netwayContent = service.getBookContent(bookEntity.getBookId());
                        contentQueryStmt.setString(1, String.valueOf(bookEntity.getBookId()));
                        ResultSet contentRs = contentQueryStmt.executeQuery();
                        TYContentInfo content = bindData(contentRs, TYContentInfo.class);
                        int innerContentId = 0; // 更新：原有ID，新增：自增长后的ID
                        if (content != null && content.getInnerBookId() != null) { // 内容记录已存在，判断是否变更
                            logger.info("update content info");
                            contentUpdateStmt.setString(1, netwayContent.getAuthorName());
                            contentUpdateStmt.setInt(2, WORDVALUE);
                            contentUpdateStmt.setString(3, netwayContent.getDetail());
                            contentUpdateStmt.setString(4, netwayContent.getImgPath());
                            contentUpdateStmt.setTimestamp(5, DateUtils.parseDateNowTime());
                          //接口小说完结状态 0：连载中 1：完结
                            int flag =0;
                            if(netwayContent.getFinishflag()==0){
                                flag =2;
                            }else  if(netwayContent.getFinishflag()==1){
                                flag =1;
                            }
                            contentUpdateStmt.setInt(6, flag);
                            contentUpdateStmt.setInt(7, netwayContent.getMaxChapter());
                            contentUpdateStmt.setInt(8, Integer.parseInt(bookEntity.getBookId()));
                            contentUpdateStmt.executeUpdate();
                            innerContentId = content.getInnerBookId();
                        } else {// 内容记录不存在,数据库不存在网文的数据，则插入记录
                            logger.info("create content info");
                            // "INSERT INTO
                            // CONTENT_INFO(OUT_BOOK_ID,CREATE_TIME,BOOK_NAME,AUTHOR_NAME,BOOK_SIZE,BOOK_INTRO,BOOK_COVER,LAST_CHAPTER_UPDATE_TIME,BOOK_FULLFLAG,CHAPTER_TOTAL,SOURCE_ID)VALUES(?,?,?,?,?,?,?,?,?,?,?)
                            contentInsertStmt.setString(1, String.valueOf(bookEntity.getBookId()));
                            contentInsertStmt.setTimestamp(2, DateUtils.parseDateNowTime());
                            contentInsertStmt.setString(3, netwayContent.getBookName());
                            contentInsertStmt.setString(4, netwayContent.getAuthorName());
                            contentInsertStmt.setInt(5, WORDVALUE);
                            contentInsertStmt.setString(6, netwayContent.getDetail());
                            contentInsertStmt.setString(7, netwayContent.getImgPath());
                            contentInsertStmt.setTimestamp(8, DateUtils.parseDateNowTime());
                            //接口小说完结状态 0：连载中 1：完结
                            int flag =0;
                            if(netwayContent.getFinishflag()==0){
                                flag =2;
                            }else  if(netwayContent.getFinishflag()==1){
                                flag =1;
                            }
                            contentInsertStmt.setInt(9, flag); //数据库1完本，2连载
                            contentInsertStmt.setInt(10, netwayContent.getMaxChapter());
                            contentInsertStmt.setString(11, SOURCE_ID);
                            contentInsertStmt.execute();
                            ResultSet generatedRs = contentInsertStmt.getGeneratedKeys();
                            if (generatedRs.next()) {
                                innerContentId = generatedRs.getInt(1);
                                logger.info("writered one line content,bk_id:" + bookEntity.getBookId() + "  id : "
                                            + innerContentId);
                            }
                        }
                        chapterQueryStmt.setString(1, String.valueOf(bookEntity.getBookId()));
                        ResultSet chapterRs = chapterQueryStmt.executeQuery();
                        List<TYChapterInfo> existsChapterList = bindDataList(chapterRs, TYChapterInfo.class);// 现有数据库书籍章节内容
                        List<CPChapterInfo> newChapterList = service.getChapterInfoList(String.valueOf(bookEntity.getBookId()));// Cp最新章节内容
                        if (CollectionUtils.isNotEmpty(existsChapterList)) { // 有内容，需判断是否有更新
                            int existsChapterSize = existsChapterList.size();
                            int newChapterSize = newChapterList.size();
                            if (newChapterSize >= existsChapterSize) { // 连载更新
                                for (int i = 0; i < newChapterSize; i++) {
                                    CPChapterInfo cpchapter = newChapterList.get(i);
                                    if (i + 1 > existsChapterSize) { // 新增章节
                                        chapterInsertStmt.setString(1, String.valueOf(cpchapter.getChapterId()));
                                        chapterInsertStmt.setString(2, String.valueOf(bookEntity.getBookId()));
                                        chapterInsertStmt.setInt(3, innerContentId);
                                        chapterInsertStmt.setInt(4, i + 1);
                                        chapterInsertStmt.setString(5, VOLUMENAME);
                                        chapterInsertStmt.setString(6, VOLUMEID);
                                        chapterInsertStmt.setString(7, cpchapter.getChapterName());
                                        chapterInsertStmt.setTimestamp(8, DateUtils.parseDateNowTime());
                                        chapterInsertStmt.setTimestamp(9, DateUtils.parseDateNowTime());
                                        chapterInsertStmt.setInt(10, WORDVALUE);
                                        if (StringUtils.isNotBlank(cpchapter.getIsVip())) {
                                            chapterInsertStmt.setInt(11, Integer.parseInt(cpchapter.getIsVip()));
                                        } else {
                                            chapterInsertStmt.setInt(11, 0);
                                        }
                                        CPChapterInfo chapterInfo = service.getChapterInfo(bookEntity.getBookId(),
                                                                                           String.valueOf(cpchapter.getChapterId()));
                                        chapterInsertStmt.setString(12, chapterInfo.getChapterContent());
                                        chapterInsertStmt.setString(13, SOURCE_ID);
                                        chapterInsertStmt.execute();
                                    } else {
                                        // 暂无章节时间更新字段
                                    }
                                }
                            } else {
                                // 章节有所删除,现在预留还没有章节增减
                            }
                        } else {
                            // 第一次抓取，表示数据库没有章节内容信息
                            // "INSERT INTO
                            // CHAPTER_INFO(OUT_CHAPTER_ID,OUT_BOOK_ID,INNER_BOOK_ID,CHAPTER_ORDER,ROLL_NAME,ROLL,CHAPTER_NAME,CREATE_TIME,UPDATE_TIME,CHAPTER_SIZE,IS_FREE,CONTENT,SOURCE_ID)
                            // VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)
                            int order = 1;
                            for (CPChapterInfo chapter : newChapterList) {
                                chapterInsertStmt.setString(1, String.valueOf(chapter.getChapterId()));
                                chapterInsertStmt.setString(2, String.valueOf(bookEntity.getBookId()));
                                chapterInsertStmt.setInt(3, innerContentId);
                                chapterInsertStmt.setInt(4, order++);
                                chapterInsertStmt.setString(5, VOLUMENAME);
                                chapterInsertStmt.setString(6, VOLUMEID); // chapter.get("ch_roll"
                                chapterInsertStmt.setString(7, chapter.getChapterName());
                                chapterInsertStmt.setTimestamp(8, DateUtils.parseDateNowTime());
                                chapterInsertStmt.setTimestamp(9, DateUtils.parseDateNowTime());
                                chapterInsertStmt.setInt(10, chapter.getChapterNum());
                                if (StringUtils.isNotBlank(chapter.getIsVip())) {
                                    chapterInsertStmt.setInt(11, Integer.parseInt(chapter.getIsVip()));
                                } else {
                                    chapterInsertStmt.setInt(11, 0);
                                }
                                CPChapterInfo chapterInfo = service.getChapterInfo(bookEntity.getBookId(),
                                                                                   String.valueOf(chapter.getChapterNum()));
                                chapterInsertStmt.setString(12, chapterInfo.getChapterContent());
                                chapterInsertStmt.setString(13, SOURCE_ID);
                                chapterInsertStmt.execute();

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (contentQueryStmt != null) {
                try {
                    contentQueryStmt.close();
                } catch (SQLException ignore) {
                }
            }
            if (this.connection != null) {
                try {
                    this.connection.close();
                } catch (SQLException ignore) {
                }
                this.connection = null;
            }

        }

        return 0;
    }

    public <T> T bindData(ResultSet rs, Class<T> cls) throws Exception {

        // 取得Method方法
        Method[] methods = cls.getMethods();
        T obj = cls.newInstance();
        // 取得ResultSet的列名
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsCount = rsmd.getColumnCount();
        String[] columnNames = new String[columnsCount];
        for (int i = 0; i < columnsCount; i++) {
            columnNames[i] = rsmd.getColumnLabel(i + 1);
        }
        // 遍历ResultSet
        while (rs.next()) {
            // 反射, 从ResultSet绑定到JavaBean
            for (int i = 0; i < columnNames.length; i++) {
                // 取得Set方法
                String setMethodName = "set" + toUpperCaseFirstOne(columnNames[i]);
                // 遍历Method
                for (int j = 0; j < methods.length; j++) {
                    if (methods[j].getName().equalsIgnoreCase(setMethodName)) {
                        setMethodName = methods[j].getName();
                        Object value = rs.getObject(columnNames[i]);
                        // 实行Set方法
                        try {
                            // JavaBean内部属性和ResultSet中一致时候
                            Method setMethod = cls.getMethod(setMethodName, value.getClass());
                            setMethod.invoke(obj, value);
                        } catch (Exception e) {
                            // JavaBean内部属性和ResultSet中不一致时候，使用String来输入值。
                            Method setMethod = cls.getMethod(setMethodName, String.class);
                            setMethod.invoke(obj, value.toString());
                        }
                    }
                }
            }
        }
        return obj;
    }

    public <T> List<T> bindDataList(ResultSet rs, Class<T> cls) throws Exception {

        // 取得Method方法
        Method[] methods = cls.getMethods();
        List<T> list = new ArrayList<T>();

        // 取得ResultSet的列名
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsCount = rsmd.getColumnCount();
        String[] columnNames = new String[columnsCount];
        for (int i = 0; i < columnsCount; i++) {
            columnNames[i] = rsmd.getColumnLabel(i + 1);
        }

        // 遍历ResultSet
        while (rs.next()) {
            T obj = cls.newInstance();
            // 反射, 从ResultSet绑定到JavaBean
            for (int i = 0; i < columnNames.length; i++) {
                // 取得Set方法
                String setMethodName = "set" + toUpperCaseFirstOne(columnNames[i]);
                // 遍历Method
                for (int j = 0; j < methods.length; j++) {
                    if (methods[j].getName().equalsIgnoreCase(setMethodName)) {
                        setMethodName = methods[j].getName();
                        Object value = rs.getObject(columnNames[i]);
                        // 实行Set方法
                        try {
                            // JavaBean内部属性和ResultSet中一致时候
                            Method setMethod = cls.getMethod(setMethodName, value.getClass());
                            setMethod.invoke(obj, value);
                        } catch (Exception e) {
                            // JavaBean内部属性和ResultSet中不一致时候，使用String来输入值。
                            Method setMethod = cls.getMethod(setMethodName, String.class);
                            setMethod.invoke(obj, value.toString());
                        }
                    }
                }
            }
            list.add(obj);
        }
        return list;
    }

    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) return s;
        else return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    @Override
    public int commit() {
        return 0;
    }

    @Override
    public int finish() {
        return 0;
    }

    public static void main(String[] args) {
        NetwayWriter writer = new NetwayWriter();
        writer.init();
        writer.prepare(null);
        writer.startWrite(null);
    }
}
