/**
 * 项目名称：quickstart-okhttp 
 * 文件名：ConfigureTimeouts.java
 * 版本信息：
 * 日期：2017年11月6日
 * Copyright yangzl Corporation 2017
 * 版权所有 *
 */
package org.quickstart.http.okhttp.v2;

import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * ConfigureTimeouts
 * 
 * @author：youngzil@163.com
 * @2017年11月6日 下午3:48:03
 * @since 1.0
 */
public class ConfigureTimeouts {

    // http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html

    /*
     * 超时
    
    没有响应时使用超时结束call。没有响应的原因可能是客户点链接问题、服务器可用性问题或者这之间的其他东西。OkHttp支持连接，读取和写入超时。
     */

    private final OkHttpClient client;

    public ConfigureTimeouts() throws Exception {
        client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
    }

    public void run() throws Exception {
        Request request = new Request.Builder().url("http://httpbin.org/delay/2") // This URL is served with a 2 second delay.
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("Response completed: " + response);
    }

}
