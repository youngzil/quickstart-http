/**
 * 项目名称：quickstart-http-feign 
 * 文件名：RemoteService.java
 * 版本信息：
 * 日期：2018年4月23日
 * Copyright asiainfo Corporation 2018
 * 版权所有 *
 */
package org.quickstart.http.feign.client;

import javax.inject.Named;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * RemoteService 
 *  
 * @author：yangzl@asiainfo.com
 * @2018年4月23日 下午5:20:46 
 * @since 1.0
 */
public interface RemoteService {
    
    @RequestLine("GET /users/list?name={name}")
    String getOwner(@Param(value = "name") String name);
    
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("POST /users/list")
    Service getOwner(Service service);
    
    @RequestLine("GET /api/state")
    String getApps();

    @RequestLine("POST /api/services")
    Void createService(Service service);
    
    @RequestLine("PUT /api/services/{id}")
    void updateOrCreateService(@Named("id") String appId, Service service);
    
    @RequestLine("DELETE /api/services/{id}")
    void deleteService(@Named("id") String appId);
    
    @RequestLine("HEAD /v2/{repository}/manifests/{tag}")
    void checkExists(@Named("repository") String repository, @Named("tag") String tag);
    
    @RequestLine("GET /ping")
    void ping();

    @RequestLine("POST /v2/apps")
    void createApp(Service service);
}
