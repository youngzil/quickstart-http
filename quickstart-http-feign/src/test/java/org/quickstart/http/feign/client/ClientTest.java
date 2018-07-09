package org.quickstart.http.feign.client;

import feign.Feign;
import feign.Request.Options;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * bamboo client Created by 9527 on 2017/8/9.
 */
public class ClientTest {

    private static RemoteService getInstance(String endpoint, RequestInterceptor interceptor) {
        
//        options方法指定连接超时时长及响应超时时长，retryer方法指定重试策略,target方法绑定接口与服务端地址。返回类型为绑定的接口类型。
        RemoteService service = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Options(1000, 3500))
                .retryer(new Retryer.Default(5000, 5000, 3))
                .target(RemoteService.class, "http://127.0.0.1:8085");
        
        Decoder decoder = new GsonDecoder(ModelUtils.GSON);
        return Feign.builder()//
                .encoder(new GsonEncoder(ModelUtils.GSON))//
                .decoder(decoder)//
                .errorDecoder(new SimpleErrorDecode(new TextDecoder()))//
                .requestInterceptor(interceptor)//
                .target(RemoteService.class, endpoint);
        
       

    }

    public static void main(String[] args) {

        String endpoint = "http://10.5.1.25:8000";
        RemoteService remoteService = ClientTest.getInstance(endpoint,new HeadersInterceptor());

        remoteService.getApps();
        System.out.println("sss");
        
        // GetAppsResponse rsp =bamboo.getApps();
        // System.out.println(rsp.getApps());

        // Map<String, Service> services=bamboo.getServices();
        // System.out.println(services);

        // Service service =new Service();
        // service.setId("/csf/nginxtest");
        // service.setAcl("path_beg -i /nginx");
        // bamboo.createService(service);
        // bamboo.updateOrCreateService("/csf/nginxtest", service);

        // service.setId("/csf/zkweb");
        // service.setAcl("path_beg -i /zkweb");
        // bamboo.updateOrCreateService("/csf/zkweb", service);
        remoteService.deleteService("/lingf-cluster-group/nginx13-ed9e");

        remoteService.checkExists("nginx", "12");
        
        
        String username = "acc";
        String password = "et";
         remoteService = ClientTest.getInstance(endpoint, new MarathonHeadersInterceptor(username, password));

        remoteService.getApps();
        
    }

}
