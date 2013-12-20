package com.taobao.datax.plugins.reader.zhulangreader;

public final class ParamKey {

    /*
     * @name: ip
     * @description: Mysql database's ip address
     * @range:
     * @mandatory: true
     * @default:
     */
    public final static String connTimeout = "conn_timeout";
    /*
     * @name: port
     * @description: Mysql database's port
     * @range:
     * @mandatory: true
     * @default:3306
     */
    public final static String soTimeout   = "so_timeout";
    /*
     * @name: dbname
     * @description: Mysql database's name
     * @range:
     * @mandatory: true
     * @default:
     */
    public final static String encoding    = "encoding";

    /*
     * @name: concurrency
     * @description: concurrency of the job
     * @range: 1-10
     * @mandatory: false
     * @default: 1
     */
    public final static String concurrency = "concurrency";
}
