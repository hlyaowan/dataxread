/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.taobao.datax.common.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.taobao.datax.common.exception.ExceptionTracker;
import com.taobao.datax.common.http.DefaultNameValuePair;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @see <a href="http://oauth.net/core/1.0/">OAuth Core 1.0</a>
 */
public class OAuth implements java.io.Serializable {

    private static final String HMAC_SHA1        = "HmacSHA1";
    private static final long   serialVersionUID = -4368426677157998618L;
    private String              consumerKey      = "";
    private String              consumerSecret;

    private Logger              logger           = Logger.getLogger(OAuth.class);

    public OAuth(String consumerKey, String consumerSecret){
        setConsumerKey(consumerKey);
        setConsumerSecret(consumerSecret);
    }

    /**
     * Computes RFC 2104-compliant HMAC signature.
     * 
     * @param data the data to be signed
     * @return signature
     * @see <a href="http://oauth.net/core/1.0/#rfc.section.9.2.1">OAuth Core - 9.2.1. Generating Signature</a>
     */
    /* package */
    public String generateSignature(String data) {
        byte[] byteHMAC = null;
        try {
            java.security.Security.addProvider(new com.sun.crypto.provider.SunJCE());
            Mac mac = Mac.getInstance(HMAC_SHA1);
            SecretKeySpec spec;
            String oauthSignature = encode(consumerSecret) + "&";
            spec = new SecretKeySpec(oauthSignature.getBytes(), HMAC_SHA1);
            mac.init(spec);
            byteHMAC = mac.doFinal(data.getBytes());
        } catch (Exception e) {
            logger.error(ExceptionTracker.trace(e));
        }
        return new BASE64Encoder().encode(byteHMAC);
    }

    public static String normalizeRequestParameters(List<DefaultNameValuePair> params) {
        Collections.sort(params);
        return encodeParameters(params);
    }

    public static String encodeParameters(List<DefaultNameValuePair> params) {
        StringBuffer buf = new StringBuffer();
        for (DefaultNameValuePair param : params) {
            if (buf.length() != 0) {
                buf.append("&".intern());
            }
            buf.append(encode(param.getName())).append("=");
            buf.append(encode(param.getValue()));
        }
        if (buf.length() != 0) {
        }
        return buf.toString();
    }

    /**
     * @param value string to be encoded
     * @return encoded string
     * @see <a href="http://wiki.oauth.net/TestCases">OAuth / TestCases</a>
     * @see <a
     * href="http://groups.google.com/group/oauth/browse_thread/thread/a8398d0521f4ae3d/9d79b698ab217df2?hl=en&lnk=gst&q=space+encoding#9d79b698ab217df2">Space
     * encoding - OAuth | Google Groups</a>
     * @see <a href="http://tools.ietf.org/html/rfc3986#section-2.1">RFC 3986 - Uniform Resource Identifier (URI):
     * Generic Syntax - 2.1. Percent-Encoding</a>
     */
    public static String encode(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
        }
        StringBuilder buf = new StringBuilder(encoded.length());
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && (i + 1) < encoded.length() && encoded.charAt(i + 1) == '7'
                       && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
        }
        return buf.toString();
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = null != consumerKey ? consumerKey : "";
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = null != consumerSecret ? consumerSecret : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OAuth)) return false;

        OAuth oAuth = (OAuth) o;

        if (consumerKey != null ? !consumerKey.equals(oAuth.consumerKey) : oAuth.consumerKey != null) return false;
        if (consumerSecret != null ? !consumerSecret.equals(oAuth.consumerSecret) : oAuth.consumerSecret != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = consumerKey != null ? consumerKey.hashCode() : 0;
        result = 31 * result + (consumerSecret != null ? consumerSecret.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OAuth{" + "consumerKey='" + consumerKey + '\'' + ", consumerSecret='" + consumerSecret + '\'' + '}';
    }
}
