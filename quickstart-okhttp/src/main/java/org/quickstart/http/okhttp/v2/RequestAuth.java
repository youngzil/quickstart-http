/**
 * 项目名称：quickstart-okhttp 
 * 文件名：RequestAuth.java
 * 版本信息：
 * 日期：2017年11月6日
 * Copyright yangzl Corporation 2017
 * 版权所有 *
 */
package org.quickstart.http.okhttp.v2;

import java.io.IOException;
import java.net.Proxy;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * RequestAuth
 * 
 * @author：youngzil@163.com
 * @2017年11月6日 下午5:29:10
 * @since 1.0
 */
public class RequestAuth {

    // http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html

    private final OkHttpClient client = new OkHttpClient();

    public void run() throws Exception {
        client.setAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(Proxy proxy, Response response) {
                System.out.println("Authenticating for response: " + response);
                System.out.println("Challenges: " + response.challenges());
                String credential = Credentials.basic("jesse", "password1");
                return response.request().newBuilder().header("Authorization", credential).build();
            }

            @Override
            public Request authenticateProxy(Proxy proxy, Response response) {
                return null; // Null indicates no attempt to authenticate.
            }
        });

        Request request = new Request.Builder().url("http://publicobject.com/secrets/hellosecret.txt").build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }

}
