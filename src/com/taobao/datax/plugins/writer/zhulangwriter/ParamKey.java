package com.taobao.datax.plugins.writer.zhulangwriter;

public final class ParamKey {

    /*
     * @name: ip
     * @description: Mysql database ip address
     * @range:
     * @mandatory: true
     * @default:
     */
    public final static String ip          = "ip";
    /*
     * @name: port
     * @description: Mysql database port
     * @range:
     * @mandatory: true
     * @default:3306
     */
    public final static String port        = "port";
    /*
     * @name: dbname
     * @description: Mysql database name
     * @range:
     * @mandatory: true
     * @default:
     */
    public final static String dbname      = "dbname";
    /*
     * @name: username
     * @description: Mysql database login username
     * @range:
     * @mandatory: true
     * @default:
     */
    public final static String username    = "username";
    /*
     * @name: password
     * @description: Mysql database login password
     * @range:
     * @mandatory: true
     * @default:
     */
    public final static String password    = "password";

    /*
     * @name: encoding
     * @description:
     * @range: UTF-8|GBK|GB2312
     * @mandatory: false
     * @default: UTF-8
     */
    public final static String encoding    = "encoding";
    /*
     * @name: pre
     * @description: execute sql before dumping data
     * @range:
     * @mandatory: false
     * @default:
     */
    public final static String pre         = "pre";
    /*
     * @name: post
     * @description: execute sql after dumping data
     * @range:
     * @mandatory: false
     * @default:
     */
    public final static String post        = "post";

    /*
     * @name:concurrency
     * @description:concurrency of the job
     * @range:1-100
     * @mandatory: false
     * @default:1
     */
    public final static String concurrency = "concurrency";

}
