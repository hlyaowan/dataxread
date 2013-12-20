package com.taobao.datax.common.http;

import org.apache.http.HttpStatus;

/**
 * @ClassName: HttpInvokeResponse
 * @Description: Http调用返回结果
 * @author joe.chen chenjun296#163.com
 * @date 2013-3-19 下午7:41:06
 */
public class HttpInvokeResponse {

    private String    content;

    private String    reasonPhrase;

    private int       statusCode = 0;

    private Throwable throwable;

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return this.statusCode == HttpStatus.SC_OK && this.throwable == null ? true : false;
    }

}
