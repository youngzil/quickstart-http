/**
 * 项目名称：quickstart-http-httpclient 
 * 文件名：SimpleClient.java
 * 版本信息：
 * 日期：2018年4月23日
 * Copyright yangzl Corporation 2018
 * 版权所有 *
 */
package org.quickstart.http.httpclient.httpclient3;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * SimpleClient
 * 
 * @author：youngzil@163.com
 * @2018年4月23日 下午9:16:12
 * @since 1.0
 */
/**
 * 最简单的HTTP客户端,用来演示通过GET或者POST方式访问某个页面
 * 
 * @authorLiudong
 */

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient();
        // 设置代理服务器地址和端口

        // client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        // 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
        HttpMethod method = new GetMethod("http://java.sun.com");
        // 使用POST方法
        // HttpMethod method = new PostMethod("http://java.sun.com");
        client.executeMethod(method);

        // 打印服务器返回的状态
        System.out.println(method.getStatusLine());
        // 打印返回的信息
        System.out.println(method.getResponseBodyAsString());
        // 释放连接
        method.releaseConnection();
    }
}
