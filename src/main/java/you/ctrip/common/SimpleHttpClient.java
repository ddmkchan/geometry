package you.ctrip.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个简单的Http客户端。
 * 
 * @author wgji
 * 
 */
public class SimpleHttpClient {
    private static Logger log = LoggerFactory.getLogger(SimpleHttpClient.class);

    private DefaultHttpClient httpClient;

    private String charset = "UTF-8";

    /**
     * 构造方法。
     * 
     * @param maxPerRoute
     *            每个域名的最大连接数
     * @param maxTotal
     *            总的最大连接数
     * @param socketTimeout
     *            Socket超时时间
     * @param connectionTimeout
     *            连接超时时间
     */
    public SimpleHttpClient(int maxPerRoute, int maxTotal, int socketTimeout, int connectionTimeout) {
        init(maxPerRoute, maxTotal, socketTimeout, connectionTimeout);
    }

    /**
     * 构造方法。
     * 
     * @param maxPerRoute
     *            每个域名的最大连接数
     * @param maxTotal
     *            总的最大连接数
     */
    public SimpleHttpClient(int maxPerRoute, int maxTotal) {
        init(maxPerRoute, maxTotal, 2000, 2000);
    }

    private void init(int maxPerRoute, int maxTotal, int socketTimeout, int connectionTimeout) {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager(schemeRegistry);
        connectionManager.setDefaultMaxPerRoute(maxPerRoute);
        connectionManager.setMaxTotal(maxTotal);

        HttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter("http.socket.timeout", socketTimeout);
        httpParams.setParameter("http.connection.timeout", connectionTimeout);

        httpClient = new DefaultHttpClient(connectionManager, httpParams);
        httpClient.setHttpRequestRetryHandler(new StandardHttpRequestRetryHandler(3, true));
    }

    /**
     * 访问并返回指定页面的内容。 支持UTF-8、GBK以及Gzip方式。 注意：GB2312的Gzip模式中文会有乱码
     * 
     * @param url
     *            页面的URL
     * @return 指定页面的内容；如果出错则返回空字串
     */
    public String getContent(String url) {
        String content = "";
        try {
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            content = getResponseBodyAsString(httpResponse);
        } catch (Exception ex) {
            log.error("Failed to get url: " + url, ex);
        }
        return content;
    }

    /**
     * 访问指定页面，但不返回内容。
     * 
     * @param url
     *            页面的URL
     * @return 是否成功访问（即使返回HTTP错误状态码，也认为是访问成功）
     */
    public boolean get(String url) {
        try {
            httpClient.execute(new HttpGet(url));
            return true;
        } catch (Exception ex) {
            log.error("Failed to get url: " + url, ex);
            return false;
        }
    }

    /**
     * Post请求访问并返回内容 支持UTF-8、GBK以及Gzip方式。 注意：GB2312的Gzip模式中文会有乱码
     * 
     * @param url
     *            页面的URL
     * @param params
     *            要提交的参数
     * @return 请求提交返回的内容；如果出错则返回空字串
     */
    public String post(String url, Map<String, String> params) {
        String content = "";
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, this.charset));
            HttpResponse response = httpClient.execute(httpPost);
            content = getResponseBodyAsString(response);
        } catch (Exception e) {
            log.error("Failed to post request url: " + url, e);
        }
        return content;
    }

    private String getResponseBodyAsString(HttpResponse response) throws IOException {
        String content = "";
        HttpEntity entity = null;
        if (response.getEntity().getContentEncoding() != null
                && response.getEntity().getContentEncoding().getValue().toLowerCase().indexOf("gzip") > -1) {
            entity = new GzipDecompressingEntity(response.getEntity());
        } else {
            entity = response.getEntity();
        }
        if (entity != null) {
            content = EntityUtils.toString(entity, this.charset);
            EntityUtils.consume(entity);
        }
        return content;
    }

    /**
     * 关闭连接。
     */
    public void close() {
        httpClient.getConnectionManager().shutdown();
    }
}
