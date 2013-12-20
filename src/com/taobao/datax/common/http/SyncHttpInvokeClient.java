package com.taobao.datax.common.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * @ClassName: SyncHttpInvokeClient
 * @Description: 同步Http调用接口
 * @author joe.chen chenjun296#163.com
 * @date 2013-3-19 下午5:20:14
 */
public class SyncHttpInvokeClient implements HttpInvokeClient {

    private static Logger                                    logger          = Logger.getLogger(SyncHttpInvokeClient.class);

    private final static ResponseHandler<HttpInvokeResponse> RESPONSE_HANDLE = new ResponseHandler<HttpInvokeResponse>() {

                                                                                 @Override
                                                                                 public HttpInvokeResponse handleResponse(HttpResponse response)
                                                                                                                                                throws ClientProtocolException,
                                                                                                                                                IOException {
                                                                                     HttpInvokeResponse invokeResponse = new HttpInvokeResponse();

                                                                                     try {
                                                                                         StatusLine statusLine = response.getStatusLine();
                                                                                         HttpEntity entity = response.getEntity();

                                                                                         invokeResponse.setStatusCode(statusLine.getStatusCode());

                                                                                         if (statusLine.getStatusCode() >= 400) {
                                                                                             invokeResponse.setReasonPhrase(statusLine.getReasonPhrase());
                                                                                             EntityUtils.consume(entity);
                                                                                         } else {
                                                                                             invokeResponse.setContent(EntityUtils.toString(entity,
                                                                                                                                            ENCODE));
                                                                                         }
                                                                                     } catch (Throwable e) {
                                                                                         invokeResponse.setThrowable(e);
                                                                                         invokeResponse.setReasonPhrase(e.getMessage());
                                                                                     }

                                                                                     return invokeResponse;
                                                                                 }
                                                                             };

    private final static PoolingClientConnectionManager      PCM;

    private final static HttpClient                          HTTP_CLIENT;

    static {
        PCM = new PoolingClientConnectionManager();
        PCM.setMaxTotal(HTTP_MAX_TOTAL_CONN);
        PCM.setDefaultMaxPerRoute(HTTP_MAX_CONN_PER_ROUTE);

        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(PCM);
        defaultHttpClient.setRedirectStrategy(new DefaultRedirectStrategy());

        HTTP_CLIENT = defaultHttpClient;

        HttpParams httpParams = HTTP_CLIENT.getParams();
        HttpProtocolParams.setContentCharset(httpParams, ENCODE);
        HttpConnectionParams.setSoTimeout(httpParams, HTTP_SO_TIMEOUT);
        HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_CONN_TIMEOUT);
        HttpConnectionParams.setSoKeepalive(httpParams, HTTP_KEEPALIVE);
        HttpConnectionParams.setStaleCheckingEnabled(httpParams, HTTP_STALE_CHECK);

        HttpConnectionParams.setTcpNoDelay(httpParams, true);
    }

    public HttpInvokeResponse invoke(HttpInvokeRequest request) {

        logger.info("http request : \n" + request.toString());
        HttpInvokeResponse response = null;
        if (HttpGet.METHOD_NAME.equalsIgnoreCase(request.getMethod())) {
            response = get(request);
        } else {
            response = post(request);
        }

        return response;
    }

    private HttpInvokeResponse get(HttpInvokeRequest request) {
        HttpInvokeResponse response = null;
        URIBuilder builder;
        try {
            builder = new URIBuilder(request.getUrl());
            List<NameValuePair> params = request.getParams();
            if (CollectionUtils.isNotEmpty(params)) {
                for (NameValuePair nvp : params) {
                    builder.setParameter(nvp.getName(), nvp.getValue());
                }
            }

            HttpGet httpGet = new HttpGet(builder.build());
            addHeaders(request.getHeaders(), httpGet);

            response = invoke(httpGet, request.getSoTimeout(), request.getConnTimeout());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }

        return response;
    }

    private HttpInvokeResponse post(HttpInvokeRequest request) {
        HttpPost httpPost = new HttpPost(request.getUrl());

        if (CollectionUtils.isNotEmpty(request.getParams())) {
            httpPost.setEntity(new UrlEncodedFormEntity(request.getParams(), request.getCharset()));
        }

        return invoke(httpPost, request.getSoTimeout(), request.getConnTimeout());
    }

    private void addHeaders(List<Header> headers, HttpRequestBase request) {
        if (headers != null) {
            for (final Header header : headers) {
                if (header == null) {
                    continue;
                }
                request.addHeader(header);
            }
        }
    }

    private HttpInvokeResponse invoke(HttpRequestBase request, int soTimeout, int connTimeout) {
        HttpInvokeResponse response = null;
        if (soTimeout > 0) {
            request.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
        }
        if (connTimeout > 0) {
            request.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeout);
        }

        try {
            response = HTTP_CLIENT.execute(request, RESPONSE_HANDLE);

            if (response.getThrowable() != null) {
                request.abort();
                logger.error("http invoke is error ! status_code : " + response.getStatusCode() + ", url :"
                             + request.getURI().toString(), response.getThrowable());
            }

            request.releaseConnection();
        } catch (Throwable e) {
            request.abort();
            logger.error("http invoke is error ! ,url=" + request.getURI().toString(), e);

            response = new HttpInvokeResponse();
            response.setThrowable(e);
            response.setReasonPhrase(e.getMessage());

        } finally {
            request.reset();
        }

        return response;
    }

}
