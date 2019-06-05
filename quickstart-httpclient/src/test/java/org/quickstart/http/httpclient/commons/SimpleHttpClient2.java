/**
 * 项目名称：quickstart-http-httpclient 
 * 文件名：SimpleHttpClient2.java
 * 版本信息：
 * 日期：2018年4月23日
 * Copyright yangzl Corporation 2018
 * 版权所有 *
 */
package org.quickstart.http.httpclient.commons;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * SimpleHttpClient2 
 *  
 * @author：youngzil@163.com
 * @2018年4月23日 下午9:18:10 
 * @since 1.0
 */
/**
 * 提交参数演示 该程序连接到一个用于查询手机号码所属地的页面 以便查询号码段1330227所在的省份以及城市
 * 
 * @authorLiudong
 */

public class SimpleHttpClient2 {

    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient();
        client.getHostConfiguration().setHost("www.imobile.com.cn", 80, "http");
        HttpMethod method = getPostMethod(); // 使用 POST 方式提交数据
        client.executeMethod(method); // 打印服务器返回的状态
        System.out.println(method.getStatusLine()); // 打印结果页面
        String response = new String(method.getResponseBodyAsString().getBytes("8859_1"));

        // 打印返回的信息
        System.out.println(response);
        method.releaseConnection();
    }

   /**  
 
    * 使用 GET 方式提交数据  
    *@return  
    */  
  
   private static HttpMethod getGetMethod(){  
      return new GetMethod("/simcard.php?simcard=1330227");  
   }

    /**
     * 使用 POST 方式提交数据
     * 
     * @return
     */

    private static HttpMethod getPostMethod() {
        PostMethod post = new PostMethod("/simcard.php");
        NameValuePair simcard = new NameValuePair("simcard", "1330227");
        post.setRequestBody(new NameValuePair[] {simcard});
        return post;
    }
}
