package com.taobao.datax.common.http;

import java.nio.charset.Charset;

/**
 * @ClassName: AsyncHttpInvokeClient
 * @Description: 异步Http调用接口
 * @author joe.chen chenjun296#163.com
 * @date 2013-3-19 下午5:11:50
 */
public class AsyncHttpInvokeClient implements HttpInvokeClient {

    /*
     * (非 Javadoc) <p>Title: invoke</p> <p>Description: </p>
     * @param request
     * @return
     * @see com.tyyd.pay.commons.http.client.HttpInvokeClient#invoke(com.tyyd.pay.commons.http.client.HttpInvokeRequest)
     */
    @Override
    public HttpInvokeResponse invoke(HttpInvokeRequest request) {
        throw new IllegalArgumentException("The method not implemented");
    }
    
    public HttpInvokeResponse invoke(HttpInvokeRequest request, Charset charset) {
    	 throw new IllegalArgumentException("The method not implemented");
    }

}
