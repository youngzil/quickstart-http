/**
 * 项目名称：quickstart-okhttp 
 * 文件名：CacheRequest.java
 * 版本信息：
 * 日期：2017年11月6日
 * Copyright yangzl Corporation 2017
 * 版权所有 *
 */
package org.quickstart.http.okhttp.v2;

import java.io.File;
import java.io.IOException;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * CacheRequest
 * 
 * @author：youngzil@163.com
 * @2017年11月6日 下午3:42:51
 * @since 1.0
 */
public class CacheResponse {
    // http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html

    // 响应缓存
    // 为了缓存响应，你需要一个你可以读写的缓存目录，和缓存大小的限制。这个缓存目录应该是私有的，不信任的程序应不能读取缓存内容。
    // 一个缓存目录同时拥有多个缓存访问是错误的。大多数程序只需要调用一次new OkHttp()，在第一次调用时配置好缓存，然后其他地方只需要调用这个实例就可以了。否则两个缓存示例互相干扰，破坏响应缓存，而且有可能会导致程序崩溃。
    // 响应缓存使用HTTP头作为配置。你可以在请求头中添加Cache-Control: max-stale=3600 ,OkHttp缓存会支持。你的服务通过响应头确定响应缓存多长时间，例如使用Cache-Control: max-age=9600。

    private final OkHttpClient client;

    public CacheResponse(File cacheDirectory) throws Exception {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(cacheDirectory, cacheSize);

        client = new OkHttpClient();
        client.setCache(cache);
    }

    public void run() throws Exception {
        Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();

        Response response1 = client.newCall(request).execute();
        if (!response1.isSuccessful())
            throw new IOException("Unexpected code " + response1);

        String response1Body = response1.body().string();
        System.out.println("Response 1 response:          " + response1);
        System.out.println("Response 1 cache response:    " + response1.cacheResponse());
        System.out.println("Response 1 network response:  " + response1.networkResponse());

        Response response2 = client.newCall(request).execute();
        if (!response2.isSuccessful())
            throw new IOException("Unexpected code " + response2);

        String response2Body = response2.body().string();
        System.out.println("Response 2 response:          " + response2);
        System.out.println("Response 2 cache response:    " + response2.cacheResponse());
        System.out.println("Response 2 network response:  " + response2.networkResponse());

        System.out.println("Response 2 equals Response 1? " + response1Body.equals(response2Body));
    }

}
