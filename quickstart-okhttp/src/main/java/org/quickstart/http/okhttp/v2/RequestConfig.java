/**
 * 项目名称：quickstart-okhttp 
 * 文件名：RequestConfig.java
 * 版本信息：
 * 日期：2017年11月6日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.http.okhttp.v2;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * RequestConfig
 * 
 * @author：yangzl@asiainfo.com
 * @2017年11月6日 下午3:49:40
 * @since 1.0
 */
public class RequestConfig {

    /*
     * 每个call的配置
    使用OkHttpClient，所有的HTTP Client配置包括代理设置、超时设置、缓存设置。当你需要为单个call改变配置的时候，clone 一个 OkHttpClient。这个api将会返回一个浅拷贝（shallow copy），你可以用来单独自定义。下面的例子中，我们让一个请求是500ms的超时、另一个是3000ms的超时。
     */

    private final OkHttpClient client = new OkHttpClient();

    public void run() throws Exception {
        Request request = new Request.Builder().url("http://httpbin.org/delay/1") // This URL is served with a 1 second delay.
                .build();

        try {
            // Clone to make a customized OkHttp for this request.
            OkHttpClient clientTemp = client.clone();
            clientTemp.setReadTimeout(500L, TimeUnit.MILLISECONDS);
            Response response = clientTemp.newCall(request).execute();
            System.out.println("Response 1 succeeded: " + response);
        } catch (IOException e) {
            System.out.println("Response 1 failed: " + e);
        }

        try {
            // Clone to make a customized OkHttp for this request.
            OkHttpClient clientTemp = client.clone();
            clientTemp.setReadTimeout(3000, TimeUnit.MILLISECONDS);
            Response response = clientTemp.newCall(request).execute();
            System.out.println("Response 2 succeeded: " + response);
        } catch (IOException e) {
            System.out.println("Response 2 failed: " + e);
        }
    }

}
