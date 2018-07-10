/**
 * 项目名称：quickstart-http-jersey 
 * 文件名：UserClient.java
 * 版本信息：
 * 日期：2018年4月23日
 * Copyright asiainfo Corporation 2018
 * 版权所有 *
 */
package org.quickstart.http.jersey.glassfish;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;  
  
/**
 * UserClient 
 * 
 *  https://blog.csdn.net/zmx729618/article/category/6215365
 *  
 * @author：yangzl@asiainfo.com
 * @2018年4月23日 下午11:24:41 
 * @since 1.0
 */
public class UserClient {  
    
    private static String serverUri = "http://localhost:8089/RestDemo/rest";  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        addUser();  
        getAllUsers();  
        updateUser();  
        getUserById();  
        getAllUsers();  
        delUser();  
        getAllUsers();  
  
    }  
    /** 
     * 添加用户 
     */  
     private static void addUser() {  
         System.out.println("****增加用户addUser****");  
         User user = new User("006","Susan","21");    
         Client client = ClientBuilder.newClient();  
         WebTarget target = client.target(serverUri + "/users");  
         Response response = target.request().buildPost(Entity.entity(user, MediaType.APPLICATION_XML)).invoke();  
         response.close();  
    }  
       
    /** 
     * 删除用户 
     */  
     private static void delUser() {  
         System.out.println("****删除用户****");  
         Client client = ClientBuilder.newClient();  
         WebTarget target = client.target(serverUri + "/users/006");  
         Response response = target.request().delete();  
         response.close();  
    }  
       
       
    /** 
     * 修改用户 
     */  
     private static void updateUser() {  
         System.out.println("****修改用户updateUser****");  
         User user = new User("006","Susan","33");    
         Client client = ClientBuilder.newClient();  
         WebTarget target = client.target(serverUri + "/users");  
         Response response = target.request().buildPut( Entity.entity(user, MediaType.APPLICATION_XML)).invoke();  
         response.close();  
    }  
    /** 
     * 根据id查询用户 
     */  
     private static void getUserById() {  
         System.out.println("****根据id查询用户****");  
         Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);// 注册json 支持  
         WebTarget target = client.target(serverUri + "/users/006");  
         Response response = target.request().get();  
         User user = response.readEntity(User.class);  
         System.out.println(user.getUserId() + user.getUserName()  +  user.getAge());  
         response.close();  
    }  
    /** 
     * 查询所有用户 
     */  
     private static void getAllUsers() {  
         System.out.println("****查询所有getAllUsers****");  
           
         Client client = ClientBuilder.newClient();  
  
         WebTarget target = client.target(serverUri + "/users");  
         Response response = target.request().get();  
 String value = response.readEntity(String.class);  
      System.out.println(value);  
        response.close();  //关闭连接  
     }  
       
}  
