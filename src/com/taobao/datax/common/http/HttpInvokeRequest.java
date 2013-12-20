package com.taobao.datax.common.http;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;

import com.taobao.datax.common.constants.Charsets;

/**
 * @ClassName: HttpInvokeRequest
 * @Description: Http调用参数
 * @author joe.chen chenjun296#163.com
 * @date 2013-3-19 下午5:54:20
 */
public final class HttpInvokeRequest {

    public static final String  GET     = HttpGet.METHOD_NAME;

    public static final String  POST    = HttpPost.METHOD_NAME;

    /** http header **/
    private List<Header>        headers;

    /** http parameters **/
    private List<NameValuePair> params;

    /** access url **/
    private String              url;

    /** access method **/
    private String              method  = "post";

    private int                 soTimeout;

    private int                 connTimeout;

    private Charset             charset = Charsets.UTF_8;

    public HttpInvokeRequest(String url, String method){
        this.url = url;
        this.method = method;
        this.headers = new ArrayList<Header>();
        this.params = new ArrayList<NameValuePair>();
    }

    public HttpInvokeRequest(String url, String method, int headerSize, int paramSize){
        this.url = url;
        this.method = method;
        if (headerSize > 0) {
            this.headers = new ArrayList<Header>(headerSize);
        } else {
            this.headers = new ArrayList<Header>();
        }
        if (paramSize > 0) {
            this.params = new ArrayList<NameValuePair>(paramSize);
        } else {
            this.params = new ArrayList<NameValuePair>();
        }
    }

    public void addHeader(String name, String value) {
        Header header = new BasicHeader(name, value);
        headers.add(header);
    }

    public void addHeaders(Map<String, String> headers) {
        if (MapUtils.isNotEmpty(headers)) {
            for (Entry<String, String> entry : headers.entrySet()) {
                addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    public void addParams(List<DefaultNameValuePair> params) {
        if (CollectionUtils.isNotEmpty(params)) {
            this.params.addAll(params);
        }
    }

    public void addParam(String name, String value) {
        NameValuePair nvp = new DefaultNameValuePair(name, value);
        params.add(nvp);
    }

    public void addParam(String name, int value) {
        NameValuePair nvp = new DefaultNameValuePair(name, String.valueOf(value));
        params.add(nvp);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public List<Header> getHeaders() {
        return Collections.unmodifiableList(headers);
    }

    public List<NameValuePair> getParams() {
        return Collections.unmodifiableList(params);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();

        buf.append("url: ").append(this.url).append("\n");
        buf.append("method: ").append(this.method).append("\n");
        buf.append("params_size: ").append(this.params.size()).append("\n");
        buf.append("header_size: ").append(this.headers.size()).append("\n");

        return buf.toString();
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
