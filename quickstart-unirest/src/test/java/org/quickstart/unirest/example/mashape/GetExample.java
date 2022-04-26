/**
 * 项目名称：quickstart-unirest 
 * 文件名：GetExample.java
 * 版本信息：
 * 日期：2017年11月6日
 * Copyright yangzl Corporation 2017
 * 版权所有 *
 */
package org.quickstart.unirest.example.mashape;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * GetExample
 * 
 * @author：youngzil@163.com
 * @2017年11月6日 上午10:45:56
 * @since 1.0
 */
public class GetExample {

    private static final Logger logger = LoggerFactory.getLogger(GetExample.class);

    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

    public static void main(String[] args) throws UnirestException {
        Unirest.get("http://httpbin.org/{method}").routeParam("method", "get").queryString("name", "Mark").asJson();

        HttpResponse<String> response = Unirest.get("http://www.baidu.com").header("User-Agent", USER_AGENT).asString();
        System.out.println(response.getBody());
    }

}
