package com.taobao.datax.common.util;

import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author joe.chen
 */
public abstract class MD5Utils {

    private static final Logger LOG = LoggerFactory.getLogger(MD5Utils.class);


    public static String getMd5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes("UTF-8"));
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString();
            // 16位的加密
            // return buf.toString().substring(8, 24);
        }
        catch (Exception e) {
            LOG.error("build md5 is error!", e);
            return StringUtils.EMPTY;
        }
    }

}
