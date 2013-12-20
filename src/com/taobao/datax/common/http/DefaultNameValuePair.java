/*
 * 
 */
// Created on 2013-11-30

package com.taobao.datax.common.http;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author joe.chen
 */
public class DefaultNameValuePair extends BasicNameValuePair implements NameValuePair, Comparable<DefaultNameValuePair> {

    /**
     * 
     */
    private static final long serialVersionUID = 7330766169351013191L;

    public DefaultNameValuePair(String name, String value){
        super(name, value);
    }

    @Override
    public int compareTo(DefaultNameValuePair o) {
        int compared;
        DefaultNameValuePair that = o;
        compared = this.getName().compareTo(that.getName());
        if (0 == compared) {
            compared = this.getValue().compareTo(that.getValue());
        }
        return compared;
    }

}
