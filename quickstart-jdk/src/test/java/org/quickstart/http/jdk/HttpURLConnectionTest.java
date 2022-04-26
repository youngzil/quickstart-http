package org.quickstart.http.jdk;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionTest {

    @Test
    public void testConnection() throws IOException {

        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");//可以手动覆盖请求头中的host
        URL url = new URL("http://172.30.1.201:31080/hermes-proxy/status");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //默认值我GET
        conn.setRequestMethod("GET");
        //添加请求头
        conn.setRequestProperty("host", "hermes-proxy.middleware.wse.test.wacai.info");

        int httpCode = conn.getResponseCode();
        System.out.println(httpCode);

    }
}
