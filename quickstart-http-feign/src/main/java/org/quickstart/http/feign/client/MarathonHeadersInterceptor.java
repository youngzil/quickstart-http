package org.quickstart.http.feign.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.util.EncodingUtil;


/**
 * Created by 9527 on 2017/8/9.
 */
public class MarathonHeadersInterceptor implements RequestInterceptor {

    private String username;
    private String password;

    public MarathonHeadersInterceptor() {
        this.username = null;
        this.password = null;
    }

    public MarathonHeadersInterceptor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Accept", "application/json");
        template.header("Content-Type", "application/json");

        if (username != null && password != null) {
            template.header("Authorization", authenticate(username, password));
        }
    }

    private static String authenticate(String username, String password) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(username);
        buffer.append(":");
        buffer.append(password);

        return "Basic " + EncodingUtil.getAsciiString(Base64.encodeBase64(EncodingUtil.getBytes(buffer.toString(), "US-ASCII")));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) {
        String username = "acc";
        String password = "et";
        StringBuffer buffer = new StringBuffer();
        buffer.append(username);
        buffer.append(":");
        buffer.append(password);

        String str = "Basic " + EncodingUtil.getAsciiString(Base64.encodeBase64(EncodingUtil.getBytes(buffer.toString(), "US-ASCII")));

        System.out.println(str);
    }
}
