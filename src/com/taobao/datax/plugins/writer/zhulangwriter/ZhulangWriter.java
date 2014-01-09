/*
 * 
 */
// Created on 2013-12-1

package com.taobao.datax.plugins.writer.zhulangwriter;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.taobao.datax.common.http.ZhulangHttpClient;
import com.taobao.datax.common.plugin.Line;
import com.taobao.datax.common.plugin.LineReceiver;
import com.taobao.datax.common.plugin.PluginParam;
import com.taobao.datax.common.plugin.PluginStatus;
import com.taobao.datax.common.plugin.Writer;
import com.taobao.datax.common.util.DateUtils;
import com.taobao.datax.plugins.common.DBSource;
import com.taobao.datax.plugins.writer.mysqlwriter.ParamKey;

/**
 * 逐浪writer
 * 
 * @author joe.chen
 */
public class ZhulangWriter extends Writer {

    private static List<String>        encodingConfigs = null;

    private static ZhulangHttpClient   httpClient      = new ZhulangHttpClient();

    static {
        encodingConfigs = new ArrayList<String>();
        encodingConfigs.add("character_set_client");
        encodingConfigs.add("character_set_connection");
        encodingConfigs.add("character_set_database");
        encodingConfigs.add("character_set_results");
        encodingConfigs.add("character_set_server");
    }

    private static Map<String, String> encodingMaps    = null;
    static {
        encodingMaps = new HashMap<String, String>();
        encodingMaps.put("utf-8", "UTF8");
    }

    private static final String        SOURCE_ID       = "abc";

    private String                     username        = null;

    private String                     password        = null;

    private String                     host            = null;

    private String                     port            = null;

    private String                     dbname          = null;

    private String                     pre             = null;

    private String                     post            = null;

    private String                     encoding        = null;

    private String                     set             = "";

    /* since load-data mechanisms only allowes one thread to load data */
    private int                        concurrency     = 1;

    private String                     sourceUniqKey   = "";

    private static String              DRIVER_NAME     = "com.mysql.jdbc.Driver";

    private Connection                 connection      = null;

    private Logger                     logger          = Logger.getLogger(ZhulangWriter.class);

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

        // this.username = "root";
        // this.password = "root";
        // this.host = "localhost";
        // this.port = "3306";
        // this.dbname = "cp_content";
        // this.encoding = "utf-8";
        // this.sourceUniqKey = DBSource.genKey(this.getClass(), host, port, dbname);

        return PluginStatus.SUCCESS.value();
    }

    @Override
    public int connect() {
        return 0;
    }

    // public static void main(String[] args) {
    // ZhulangWriter writer = new ZhulangWriter();
    // writer.init();
    // writer.prepare(null);
    // writer.startWrite(null);
    // }

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
                                                                                                                                                + "CHAPTER_ORDER AS chapterOrder,ROLL_NAME AS rollName,ROLL AS roll,CHAPTER_NAME AS chapterName,"
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
            while ((line = receiver.getFromReader()) != null) {
                logger.info("zhulang writer starting");
                if (line.getFieldNum() > 0) {

                    String contentJson = line.getField(0);
                    logger.info("zhulang writer line : " + contentJson);

                    // String contentJson =
                    // "{createTime=\"2011-05-31 15:45:33\", outBookId=\"166085\", bookSize=3517, bookIntro=\"  大街上走着的林枫，被雷电劈中。“丢你老母！”林枫全身冒烟地躺在地上，愤愤不平地想着，凭什么像我这么纯洁的好青年要遭雷劈？这老天爷还有眼睛么？  　正跪在他面前，给他做人工心脏起博的急救漂亮女医生终于放弃似地叹了口气，摇了摇头，“没希望了……”说着把耳朵贴到林枫的嘴边，问：“你还有什么遗言吗？”　　林枫用尽最后的力气，勉强蠕动嘴唇，说：“我的党费...还没交…语罢头一歪，两腿一蹬，眼前一黑，咽气。\", lastChapterUpdateTime=\"2011-05-31 15:46:17\", authorName=\"萧老八\", chapterTotal=1, bookCover=\"http://images.zhulang.com/www/image/no_book.gif\", bookFullFlag=2, bookName=\"异界也风流\"}";
                    JSONObject json = JSONObject.fromObject(contentJson);
                    contentQueryStmt.setString(1, json.getString("outBookId"));
                    ResultSet contentRs = contentQueryStmt.executeQuery();
                    ContentInfo content = bindData(contentRs, ContentInfo.class);

                    int innerContentId = 0; // 更新：原有ID，新增：自增长后的ID

                    if (content != null && content.getInnerBookId() != null) { // 内容记录已存在，判断是否变更
                        logger.info("update content info");
                        contentUpdateStmt.setString(1, json.getString("authorName"));
                        contentUpdateStmt.setInt(2, json.getInt("bookSize"));
                        contentUpdateStmt.setString(3, json.getString("bookIntro"));
                        contentUpdateStmt.setString(4, json.getString("bookCover"));
                        contentUpdateStmt.setTimestamp(5,
                                                       DateUtils.parseDateTime(json.getString("lastChapterUpdateTime")));
                        contentUpdateStmt.setInt(6, json.getInt("bookFullFlag"));
                        contentUpdateStmt.setInt(7, json.getInt("chapterTotal"));
                        contentUpdateStmt.setInt(8, content.getInnerBookId());
                        contentUpdateStmt.executeUpdate();
                        innerContentId = content.getInnerBookId();

                    } else {// 内容记录不存在
                        logger.info("create content info");
                        contentInsertStmt.setString(1, json.getString("outBookId"));
                        contentInsertStmt.setTimestamp(2, DateUtils.parseDateTime(json.getString("createTime")));
                        contentInsertStmt.setString(3, json.getString("bookName"));
                        contentInsertStmt.setString(4, json.getString("authorName"));
                        contentInsertStmt.setInt(5, json.getInt("bookSize"));
                        contentInsertStmt.setString(6, json.getString("bookIntro"));
                        contentInsertStmt.setString(7, json.getString("bookCover"));
                        contentInsertStmt.setTimestamp(8,
                                                       DateUtils.parseDateTime(json.getString("lastChapterUpdateTime")));
                        contentInsertStmt.setInt(9, json.getInt("bookFullFlag"));
                        contentInsertStmt.setInt(10, json.getInt("chapterTotal"));
                        contentInsertStmt.setString(11, SOURCE_ID);
                        contentInsertStmt.execute();

                        ResultSet generatedRs = contentInsertStmt.getGeneratedKeys();

                        if (generatedRs.next()) innerContentId = generatedRs.getInt(1);
                        logger.info("writered one line content,bk_id:" + json.getString("outBookId") + "  id : "
                                    + innerContentId);
                    }

                    chapterQueryStmt.setString(1, json.getString("outBookId"));
                    ResultSet chapterRs = chapterQueryStmt.executeQuery();
                    List<ChapterInfo> existsChapterList = bindDataList(chapterRs, ChapterInfo.class);
                    List<Map<String, String>> newChapterList = httpClient.getChapterInfo(json.getString("outBookId"));

                    if (CollectionUtils.isNotEmpty(existsChapterList)) { // 有内容，需判断是否有更新

                        int existsChapterSize = existsChapterList.size();
                        int newChapterSize = newChapterList.size();

                        if (newChapterSize >= existsChapterSize) { // 连载更新

                            for (int i = 0; i < newChapterSize; i++) {

                                Map<String, String> chapter = newChapterList.get(i);

                                if (i + 1 > existsChapterSize) { // 新增章节
                                    chapterInsertStmt.setString(1, chapter.get("ch_id"));
                                    chapterInsertStmt.setString(2, json.getString("outBookId"));
                                    chapterInsertStmt.setInt(3, innerContentId);
                                    chapterInsertStmt.setInt(4, i + 1);
                                    chapterInsertStmt.setString(5, chapter.get("ch_roll_name"));
                                    chapterInsertStmt.setString(6, chapter.get("ch_roll"));
                                    chapterInsertStmt.setString(7, chapter.get("ch_name"));
                                    chapterInsertStmt.setTimestamp(8,
                                                                   DateUtils.parseDateTime(chapter.get("ch_cre_time")));
                                    chapterInsertStmt.setTimestamp(9, DateUtils.parseDateTime(chapter.get("ch_update")));
                                    chapterInsertStmt.setInt(10, Integer.parseInt(chapter.get("ch_size")));
                                    chapterInsertStmt.setInt(11, Integer.parseInt(chapter.get("ch_vip")));
                                    chapterInsertStmt.setString(12,
                                                                httpClient.getChapterContent(json.getString("outBookId"),
                                                                                             chapter.get("ch_id")));
                                    chapterInsertStmt.setString(13, SOURCE_ID);
                                    chapterInsertStmt.execute();

                                } else {
                                    ChapterInfo chapterInfo = existsChapterList.get(i);
                                    if (DateUtils.parseDate(chapter.get("ch_update")).after(new Date(
                                                                                                     chapterInfo.getUpdateTime().getTime()))) { // 更新时间靠后，需要更新

                                        chapterUpdateStmt.setInt(1, i + 1);
                                        chapterUpdateStmt.setString(2, chapter.get("ch_roll_name"));
                                        chapterUpdateStmt.setString(3, chapter.get("ch_roll"));
                                        chapterUpdateStmt.setString(4, chapter.get("ch_name"));
                                        chapterUpdateStmt.setTimestamp(5,
                                                                       DateUtils.parseDateTime(chapter.get("ch_update")));
                                        chapterUpdateStmt.setInt(6, Integer.parseInt(chapter.get("ch_size")));
                                        chapterUpdateStmt.setInt(7, Integer.parseInt(chapter.get("ch_vip")));
                                        chapterUpdateStmt.setString(8,
                                                                    httpClient.getChapterContent(json.getString("outBookId"),
                                                                                                 chapter.get("ch_id")));
                                        chapterUpdateStmt.setInt(9, chapterInfo.getInnerChapterId());

                                        chapterUpdateStmt.executeUpdate();
                                    }
                                }
                            }

                        } else { // 章节有所删除

                        }

                    } else { // 第一次抓取
                        logger.info("guqiang first collection content info");
                        int order = 1;
                        for (Map<String, String> chapter : newChapterList) {

                            chapterInsertStmt.setString(1, chapter.get("ch_id"));
                            chapterInsertStmt.setString(2, json.getString("outBookId"));
                            chapterInsertStmt.setInt(3, innerContentId);
                            chapterInsertStmt.setInt(4, order++);
                            chapterInsertStmt.setString(5, chapter.get("ch_roll_name"));
                            chapterInsertStmt.setString(6, chapter.get("ch_roll"));
                            chapterInsertStmt.setString(7, chapter.get("ch_name"));
                            chapterInsertStmt.setTimestamp(8, DateUtils.parseDateTime(chapter.get("ch_cre_time")));
                            chapterInsertStmt.setTimestamp(9, DateUtils.parseDateTime(chapter.get("ch_update")));
                            chapterInsertStmt.setInt(10, Integer.parseInt(chapter.get("ch_size")));
                            chapterInsertStmt.setInt(11, Integer.parseInt(chapter.get("ch_vip")));
                            chapterInsertStmt.setString(12, httpClient.getChapterContent(json.getString("outBookId"),
                                                                                         chapter.get("ch_id")));
                            chapterInsertStmt.setString(13, SOURCE_ID);
                            chapterInsertStmt.execute();

                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }  finally {
            if (contentQueryStmt != null) {
                try {
                    contentQueryStmt.close();
                } catch (SQLException ignore) {
                }
            }
            if (contentUpdateStmt != null) {
                try {
                    contentUpdateStmt.close();
                } catch (SQLException ignore) {
                }
            }
            if (contentInsertStmt != null) {
                try {
                    contentInsertStmt.close();
                } catch (SQLException ignore) {
                }
            }
            if (chapterQueryStmt != null) {
                try {
                    chapterQueryStmt.close();
                } catch (SQLException ignore) {
                }
            }
            if (chapterInsertStmt != null) {
                try {
                    chapterInsertStmt.close();
                } catch (SQLException ignore) {
                }
            }
            if (chapterUpdateStmt != null) {
                try {
                    chapterUpdateStmt.close();
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

}
