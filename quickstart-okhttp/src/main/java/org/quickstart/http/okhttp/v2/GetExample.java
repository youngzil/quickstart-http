package org.quickstart.http.okhttp.v2;

import java.io.IOException;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class GetExample {

    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        System.out.println(response.body().string());

        return response.body().string();
    }

    /*提取响应头
    典型的HTTP头 像是一个 Map<String, String> :每个字段都有一个或没有值。但是一些头允许多个值，像Guava的Multimap。例如：HTTP响应里面提供的Vary响应头，就是多值的。OkHttp的api试图让这些情况都适用。
    当写请求头的时候，使用header(name, value)可以设置唯一的name、value。如果已经有值，旧的将被移除，然后添加新的。使用addHeader(name, value)可以添加多值（添加，不移除已有的）。
    当读取响应头时，使用header(name)返回最后出现的name、value。通常情况这也是唯一的name、value。如果没有值，那么header(name)将返回null。如果想读取字段对应的所有值，使用headers(name)会返回一个list。
    为了获取所有的Header，Headers类支持按index访问。*/
    public void run() throws Exception {
        Request request = new Request.Builder().url("https://api.github.com/repos/square/okhttp/issues").header("User-Agent", "OkHttp Headers.java").addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json").build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        System.out.println("Server: " + response.header("Server"));
        System.out.println("Date: " + response.header("Date"));
        System.out.println("Vary: " + response.headers("Vary"));
    }

    public static void main(String[] args) throws IOException {
        GetExample example = new GetExample();
        String response = example.run("https://raw.github.com/square/okhttp/master/README.md");
        System.out.println(response);
    }
}
