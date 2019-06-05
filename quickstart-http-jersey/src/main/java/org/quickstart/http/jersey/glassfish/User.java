/**
 * 项目名称：quickstart-http-jersey 
 * 文件名：User.java
 * 版本信息：
 * 日期：2018年4月23日
 * Copyright yangzl Corporation 2018
 * 版权所有 *
 */
package org.quickstart.http.jersey.glassfish;

import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User
 * 
 * @author：youngzil@163.com
 * @2018年4月23日 下午11:29:46
 * @since 1.0
 */
@XmlRootElement(name = "user")
public class User {
    
    User(String userId ,String userName ,String age){
        this.userId = userId;
        this.userName = userName;
        this.age = age;
    }
    
    @PathParam("userId")
    private String userId;
    
    @PathParam("userName")
    private String userName;
    
    @PathParam("age")
    private String age;
    
    

    @FormParam("name")
    private String name;

    @FormParam("telephone")
    private String telephone;

    @FormParam("email")
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    
}
