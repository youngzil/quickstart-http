/**
 * 项目名称：quickstart-http-httpclient 
 * 文件名：SimpleHttpClientDemo.java
 * 版本信息：
 * 日期：2018年4月23日
 * Copyright yangzl Corporation 2018
 * 版权所有 *
 */
package org.quickstart.http.httpclient.httpcomponents;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * SimpleHttpClientDemo 
 *  
 * @author：youngzil@163.com
 * @2018年4月23日 下午9:11:31 
 * @since 1.0
 */
/**
 * 简单httpclient实例
 * 
 * @author arron
 * @date 2015年11月11日 下午6:36:49
 * @version 1.0
 */
public class SimpleHttpClientDemo {
    
    public static void main(String[] args) throws ParseException, IOException {  
        String url="http://php.weather.sina.com.cn/iframe/index/w_cl.php";  
        Map<String, String> map = new HashMap<String, String>();  
        map.put("code", "js");  
        map.put("day", "0");  
        map.put("city", "上海");  
        map.put("dfc", "1");  
        map.put("charset", "utf-8");  
        String body = send(url, map,"utf-8");  
        System.out.println("交易响应结果：");  
        System.out.println(body);  
      
        System.out.println("-----------------------------------");  
      
        map.put("city", "北京");  
        body = send(url, map, "utf-8");  
        System.out.println("交易响应结果：");  
        System.out.println(body);  
    }  

    /**
     * 模拟请求
     * 
     * @param url 资源地址
     * @param map 参数列表
     * @param encoding 编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String send(String url, Map<String, String> map, String encoding) throws ParseException, IOException {
        String body = "";

        // 创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址：" + url);
        System.out.println("请求参数：" + nvps.toString());

        // 设置header信息
        // 指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        // 释放链接
        response.close();
        return body;
    }
}
