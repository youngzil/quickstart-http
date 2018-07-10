/**
 * 项目名称：quickstart-http-jersey 
 * 文件名：JerseyClient.java
 * 版本信息：
 * 日期：2018年4月23日
 * Copyright asiainfo Corporation 2018
 * 版权所有 *
 */
package org.quickstart.http.jersey.sun;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * JerseyClient 
 *  
 * @author：yangzl@asiainfo.com
 * @2018年4月23日 下午11:14:53 
 * @since 1.0
 */
public class JerseyClient
{

    public static void main(String[] args)
    {

        //简单的请求直接请求URL
        Client client = Client.create();
        WebResource webResource = client.resource("http://127.0.0.1:8090/hello");
        String s = webResource.get(String.class);
        System.out.println("-------Normal Get------>"+s);

        //通过路径传递参数
        WebResource pathParam = client.resource("http://127.0.0.1:8090/hello/show/1212");
        String pathValue = pathParam.get(String.class);
        System.out.println("-------Path Value------>"+pathValue);

        //Get，通过？的形式传递参数的形式
        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        queryParams.add("name", "get_param");
        WebResource getParam = client.resource("http://127.0.0.1:8090/hello/hello_get");
        String getS=getParam.queryParams(queryParams).get(String.class);
        System.out.println("-----Get Has Param---->"+getS);

        //Post，通过Form提交参数的形式
        MultivaluedMap<String,String> formData = new MultivaluedMapImpl();
        formData.add("name", "formValue");
        WebResource postParam = client.resource("http://127.0.0.1:8090/hello/hello_post");
        String response = postParam.type("application/x-www-form-urlencoded").post(String.class, formData);
        System.out.println("----------Post Form Value-----"+response);

        //Get,获取一个对象底层是封装成Json进行传输的，但是client根据类型进行了转换，可以直接使用的对象
        WebResource person = client.resource("http://127.0.0.1:8090/hello/getPerson");
        Person p=person.get(Person.class);
        System.out.println(p);

    }

}
