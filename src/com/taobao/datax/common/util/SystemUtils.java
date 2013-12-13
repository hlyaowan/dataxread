/**   
 * @Title: SystemUtils.java 
 * @Package com.tyyd.pay.commons.utils 
 * @Description: TODO
 * @author joe.chen chenjun296#163.com
 * @date 2013-3-19 下午6:36:34 
 * @version V1.0   
 */
package com.taobao.datax.common.util;

/**
 * @ClassName: SystemUtils
 * @Description: 获取java -D参数传入的值
 * @author joe.chen chenjun296#163.com
 * @date 2013-3-19 下午6:36:34
 */
public abstract class SystemUtils extends org.apache.commons.lang.SystemUtils {

    public static int getSystemPropertyAsInt(String property, final int defaultValue) {
        try {
            final String valueString = System.getProperty(property).trim();
            return valueString != null ? Integer.valueOf(valueString) : defaultValue;
        } catch (final Exception e) {
            return defaultValue;
        }
    }

    public static boolean getSystemPropertyAsBool(String property, boolean defaultValue) {
        try {
            final String valueString = System.getProperty(property).trim();
            return valueString != null ? Boolean.valueOf(valueString) : defaultValue;
        } catch (final Exception e) {
            return defaultValue;
        }
    }

}
