/**
 * 项目名称：quickstart-http-jersey 
 * 文件名：HelloJersey.java
 * 版本信息：
 * 日期：2018年4月23日
 * Copyright yangzl Corporation 2018
 * 版权所有 *
 */
package org.quickstart.http.jersey.sun;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
/**
 * HelloJersey 
 *  
 * @author：youngzil@163.com
 * @2018年4月23日 下午11:08:09 
 * @since 1.0
 */
@Path("/hello")
public class HelloJersey
{

    @GET
    @Produces("text/plain")
    public String hello()
    {
        return "Jersey Get!";
    }   

    /**
     * 参数是以URL?param=value的形式传递
     * @param name
     * @return
     */
    @GET
    @Path("hello_get")
    @Produces("text/plain")
    public String hello(@QueryParam("name") String name)
    {
        return "Jersey Get! param="+name;
    }   

    /**
     * 参数是以Form表单的形式传递的
     * @param name
     * @return
     */
    @POST
    @Path("hello_post")
    @Consumes("application/x-www-form-urlencoded")
    public String helloPost(@FormParam("name") String name)
    {
        return "Jersey Post! "+name;
    }

    /**
     * 参数以路径的形式进行传递的
     * @param id
     * @return
     */
    @GET
    @Path("show/{id}")
    @Produces("text/plain")
    public String showName(@PathParam("id") int id)
    {
        return "Hello jersey!"+id;
    }       
    @GET
    @Path("getPerson")
    @Produces(MediaType.APPLICATION_JSON)
    public Person showPerson()
    {
        Person p=new Person();
        p.setName("pName");
        p.setId(11212);
        return p;
    }
}
