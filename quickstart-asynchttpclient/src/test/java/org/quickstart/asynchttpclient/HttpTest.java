package org.quickstart.asynchttpclient;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Param;
import org.asynchttpclient.Response;
import org.asynchttpclient.netty.NettyResponse;

import java.util.ArrayList;
import java.util.List;

public class HttpTest {
    static AsyncHttpClient asyncHttpClient = Dsl
        //实例化所有池和检测器
        .asyncHttpClient(
            Dsl.config()
                .setMaxConnections(500)
                .setMaxConnectionsPerHost(50)
                .setPooledConnectionIdleTimeout(6000)
                .setConnectionTtl(500)
                .setIoThreadsCount(100)
                .setConnectTimeout(60000)
                .setUseNativeTransport(
                    System.getProperty("os.name").toLowerCase().indexOf("linux") > 0));

    public static void main(String[] args) throws Exception {
        List<Param> params = new ArrayList<>();
        params.add(new Param("keyfrom", "XXX"));

        asyncHttpClient
            .prepareGet("http://fanyi.youdao.com/openapi.do")
            .addQueryParams(params)
            //这里进入发送请求阶段
            .execute()
            .toCompletableFuture()
            //超时报错，或请求异常，做容错处理，抛出一个Response
            // .exceptionally(t -> new Response() {...})
            // .exceptionally(t -> new NettyResponse())
            .thenAccept(rep -> System.out.println("RESPONSE BODY" + rep.getResponseBody()));
    }
}
