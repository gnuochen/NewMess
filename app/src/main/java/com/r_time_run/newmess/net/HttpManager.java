package com.r_time_run.newmess.net;

import android.net.http.HttpResponseCache;
import android.service.carrier.CarrierMessagingService;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuochen on 2015/9/14.
 */
public class HttpManager {
    private final static String UTF_8 = "gbk";
    private final static int TIMEOUT_CONNECTION = 30000;
    private final static int TIMEOUT_SOCKET = 20000;
    private final static int TIMEOUT_CONN = 20000;
    private final static int MAX_CONNECTION_COUNT = 400;

    static HttpClient getHttpClient() {
        HttpParams params = new BasicHttpParams();

        // 设置字符集
        HttpProtocolParams.setContentCharset(params, UTF_8);

        // 设置http协议版本
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

        // 设置是否验证请求 设为true会导致两次连接请求
        HttpProtocolParams.setUseExpectContinue(params, false);

        // 设置连接池取连接超时时间
        ConnManagerParams.setTimeout(params, TIMEOUT_CONN);

        // 设置最大连接数
        ConnPerRouteBean cRouteBean = new ConnPerRouteBean(MAX_CONNECTION_COUNT);
        ConnManagerParams.setMaxConnectionsPerRoute(params, cRouteBean);

        // 设置连接超时
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONNECTION);

        // 设置读取数据超时
        HttpConnectionParams.setSoTimeout(params, TIMEOUT_SOCKET);

        // 设置连接支持http与https两种协议
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory
                .getSocketFactory(), 443));
        // 使用线程安全的管理创建httpClient
        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params,
                schemeRegistry);
        DefaultHttpClient hr = new DefaultHttpClient(ccm, params);
        return hr;
    }

    public static String openUrl(String url, String method, NMParameters params, String profile_image) throws Exception {

        String result = "";

        HttpClient client = getHttpClient();
        HttpUriRequest request = null;
        ByteArrayOutputStream bos = null;

        if (profile_image != null) {
            HttpPost post = new HttpPost(url);
            MultipartEntity entity = new MultipartEntity();
            ContentBody contentBody = new FileBody(new File(profile_image));
            entity.addPart(params.getValue("user_id") + ".png", contentBody);
            post.setEntity(entity);
            request = post;
        } else if ("GET".equals(method)) {
            url = url + encodeUrl(params);
            HttpGet get = new HttpGet(url);
            request = get;
        } else if ("POST".equals(method)) {

            HttpPost post = new HttpPost(url);
            List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
            for (int i = 0; i < params.size(); i++) {
                parameters.add(new BasicNameValuePair(params.getKey(i), params.getValue(i)));
            }
            HttpEntity entity = new UrlEncodedFormEntity(parameters);
            post.setEntity(entity);
            request = post;

        }
        HttpResponse response = client.execute(request);
        result = readHttpResponse(response);
        return result;
    }

    public static String readHttpResponse(HttpResponse response) throws Exception {
        String result = "";
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity, "UTF-8");
        return result;
    }

    private static String encodeUrl(NMParameters parameters) {
        if (parameters == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int loc = 0; loc < parameters.size(); loc++) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            String _key = parameters.getKey(loc);
            String _value = parameters.getValue(_key);
            if (_value == null) {
                Log.i("httpManage-------encodeUrl", "key:" + _key + "'s value is null");
            } else {
                sb.append(URLEncoder.encode(parameters.getKey(loc)) + "=" + URLEncoder.encode(parameters.getValue(loc)));
            }
            Log.i("httpmanage----------encodeUrl", sb.toString());
        }
        return sb.toString();
    }


}
