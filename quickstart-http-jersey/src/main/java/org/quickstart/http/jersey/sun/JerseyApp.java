/**
 * 项目名称：quickstart-http-jersey 
 * 文件名：JerseyApp.java
 * 版本信息：
 * 日期：2018年4月23日
 * Copyright asiainfo Corporation 2018
 * 版权所有 *
 */
package org.quickstart.http.jersey.sun;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;


/**
 * JerseyApp 
 *  
 * @author：yangzl@asiainfo.com
 * @2018年4月23日 下午11:09:19 
 * @since 1.0
 */
public class JerseyApp {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception 
    {
        final Server server = new Server(8090);
        ServletHolder sh = new ServletHolder(ServletContainer.class);
        sh.setInitParameter(ServletContainer.RESOURCE_CONFIG_CLASS, PackagesResourceConfig.class.getCanonicalName());
        sh.setInitParameter(PackagesResourceConfig.PROPERTY_PACKAGES, "cn.test.jersey.sample1");
        ServletContextHandler context = new ServletContextHandler(server, null);
        context.addServlet(sh, "/*");
        server.start();
        server.join();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run()
            {
                try
                {
                    server.stop();
                }
                catch (Exception e)
                {                   
                    e.printStackTrace();
                }
            }
        });
    }
}