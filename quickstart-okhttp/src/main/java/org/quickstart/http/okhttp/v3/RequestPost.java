/**
 * 项目名称：quickstart-okhttp 
 * 文件名：RequestPost.java
 * 版本信息：
 * 日期：2017年11月3日
 * Copyright yangzl Corporation 2017
 * 版权所有 *
 */
package org.quickstart.http.okhttp.v3;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * RequestPost
 * 
 * @author：youngzil@163.com
 * @2017年11月3日 下午3:18:04
 * @since 1.0
 */
public class RequestPost {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    // 定义上传文件类型
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    static final Logger logger = LoggerFactory.getLogger(RequestPost.class);

    public static void main(String[] args) {}

    // POST提交Json数据
    private void postJson() throws IOException {
        String url = "http://write.blog.csdn.net/postlist/0/0/enabled/1";
        String json = "haha";

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                logger.info(response.body().string());
            }
        });

    }

    // POST提交键值对
    // 很多时候我们会需要通过POST方式把键值对数据传送到服务器。 OkHttp提供了很方便的方式来做这件事情。
    private void post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("name", "liming").add("school", "beida").build();

        Request request = new Request.Builder().url(url).post(formBody).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                logger.info(str);

            }

        });
    }

    // 异步上传文件 、上传文件本身也是一个POST请求 、定义上传文件类型

    private void postFile() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        File file = new File("/sdcard/demo.txt");
        Request request = new Request.Builder().url("https://api.github.com/markdown/raw").post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file)).build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                logger.info(response.body().string());
            }
        });
    }

    // 提取响应头
    // 典型的HTTP头 像是一个 Map

    private final OkHttpClient client = new OkHttpClient();

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

    // Post方式提交String
    // 使用HTTP POST提交请求到服务。这个例子提交了一个markdown文档到web服务，以HTML方式渲染markdown。因为整个请求体都在内存中，因此避免使用此api提交大文档（大于1MB）。

    private void postString() throws IOException {

        OkHttpClient client = new OkHttpClient();

        String postBody = "" + "Releases\n" + "--------\n" + "\n" + " * zhangfei\n" + " * guanyu\n" + " * liubei\n";

        Request request = new Request.Builder().url("https://api.github.com/markdown/raw").post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody)).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());

            }

        });

    }

    // Post方式提交流
    //
    // 以流的方式POST提交请求体。请求体的内容由流写入产生。这个例子是流直接写入Okio的BufferedSink。你的程序可能会使用OutputStream，你可以使用BufferedSink.outputStream()来获取。

    private void postStream() throws IOException {
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }

            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n)
                        return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("-------\n");
                for (int i = 2; i <= 997; i++) {
                    sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                }

            }
        };

        Request request = new Request.Builder().url("https://api.github.com/markdown/raw").post(requestBody).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());

            }

        });
    }

    // Post方式提交表单
    private void postForm() {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder().add("search", "Jurassic Park").build();

        Request request = new Request.Builder().url("https://en.wikipedia.org/w/index.php").post(formBody).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());

            }

        });

    }

    // Post方式提交分块请求
    // MultipartBody 可以构建复杂的请求体，与HTML文件上传形式兼容。多块请求体中每块请求都是一个请求体，可以定义自己的请求头。这些请求头可以用来描述这块请求，例如他的Content-Disposition。如果Content-Length和Content-Type可用的话，他们会被自动添加到请求头中。

    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private void postMultipartBody() {
        OkHttpClient client = new OkHttpClient();

        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        MultipartBody body =
                new MultipartBody.Builder("AaB03x").setType(MultipartBody.FORM).addPart(Headers.of("Content-Disposition", "form-data; name=\"title\""), RequestBody.create(null, "Square Logo"))
                        .addPart(Headers.of("Content-Disposition", "form-data; name=\"image\""), RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png"))).build();

        Request request = new Request.Builder().header("Authorization", "Client-ID " + IMGUR_CLIENT_ID).url("https://api.imgur.com/3/image").post(body).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());

            }

        });
    }

    // 响应缓存
    // 为了缓存响应，你需要一个你可以读写的缓存目录，和缓存大小的限制。这个缓存目录应该是私有的，不信任的程序应不能读取缓存内容。
    // 一个缓存目录同时拥有多个缓存访问是错误的。大多数程序只需要调用一次new OkHttpClient()，在第一次调用时配置好缓存，然后其他地方只需要调用这个实例就可以了。否则两个缓存示例互相干扰，破坏响应缓存，而且有可能会导致程序崩溃。
    // 响应缓存使用HTTP头作为配置。你可以在请求头中添加Cache-Control: max-stale=3600 ,OkHttp缓存会支持。你的服务通过响应头确定响应缓存多长时间，例如使用Cache-Control: max-age=9600。
    private void ResponseCache() {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB

        File cacheDirectory = new File("/sdcard/demo.txt");
        Cache cache = new Cache(cacheDirectory, cacheSize);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache);
        OkHttpClient client = builder.build();

        Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String response1Body = response.body().string();
                System.out.println("Response 1 response:          " + response);
                System.out.println("Response 1 cache response:    " + response.cacheResponse());
                System.out.println("Response 1 network response:  " + response.networkResponse());
            }

        });
    }

    // 超时
    // 没有响应时使用超时结束call。没有响应的原因可能是客户点链接问题、服务器可用性问题或者这之间的其他东西。OkHttp支持连接，读取和写入超时。
    private void ConfigureTimeouts() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();

        client.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().writeTimeout(10, TimeUnit.SECONDS);

        Request request = new Request.Builder().url("http://httpbin.org/delay/2") // This URL is served with a 2 second delay.
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("Response completed: " + response);
            }

        });

    }

}
