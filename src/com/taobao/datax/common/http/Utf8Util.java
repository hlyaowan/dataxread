/*
 * 
 */
// Created on 2013-12-6

package com.taobao.datax.common.http;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author joe.chen
 */
public abstract class Utf8Util {

    public static String getWordUtf8(String word) {
        if (!NumberUtils.isNumber(word)) {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                output.append("\\u" + Integer.toString(word.charAt(i), 16));
            }
            return output.toString();
        }

        return word;

    }

}
