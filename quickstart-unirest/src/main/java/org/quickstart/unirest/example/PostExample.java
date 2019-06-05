/**
 * 项目名称：quickstart-unirest 
 * 文件名：PostExample.java
 * 版本信息：
 * 日期：2017年11月6日
 * Copyright yangzl Corporation 2017
 * 版权所有 *
 */
package org.quickstart.unirest.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * PostExample
 * 
 * @author：youngzil@163.com
 * @2017年11月6日 上午10:46:06
 * @since 1.0
 */
public class PostExample {

    private static final Logger logger = LoggerFactory.getLogger(PostExample.class);

    // Features
    /*Make GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS requests
    Both syncronous and asynchronous (non-blocking) requests
    It supports form parameters, file uploads and custom body entities
    Easily add route parameters without ugly string concatenations
    Supports gzip
    Supports Basic Authentication natively
    Customizable timeout, concurrency levels and proxy settings
    Customizable default headers for every request (DRY)
    Customizable HttpClient and HttpAsyncClient implementation
    Automatic JSON parsing into a native object for JSON responses
    Customizable binding, with mapping from response body to java Object*/

    public static void main(String[] args) throws UnirestException, URISyntaxException, IOException {

        // 参数
        String url = "http://www.baidu.com";
        Map<String, Object> param = new HashMap<>();

        // 请求对象
        Unirest.setTimeouts(5000L, 6000L);
        Unirest.setDefaultHeader("test", "testValue");
        // Unirest.setProxy(proxy);

        HttpRequestWithBody request = Unirest.post(url);
        request.fields(param);

        // 获取请求结果,request.as等
        // 1、String结果
        HttpResponse<String> response = request.asString();
        String stringBody = (String) response.getBody();
        System.out.println(stringBody);

        // 2、异步请求
        request.asStringAsync(new Callback<String>() {

            @Override
            public void failed(UnirestException e) {
                System.out.println("请求失败");

            }

            @Override
            public void completed(HttpResponse<String> response) {
                System.out.println("请求完成");

            }

            @Override
            public void cancelled() {
                System.out.println("请求取消");

            }
        });

        HttpResponse<JsonNode> jsonResponse = Unirest.post("http://httpbin.org/post").header("accept", "application/json").queryString("id", "123456")// url后拼接的参数
                .field("last", "Polo").asJson();

        System.out.println(jsonResponse.getStatus());
        System.out.println(jsonResponse.getStatusText());
        System.out.println(jsonResponse.getHeaders());
        System.out.println(jsonResponse.getBody());
        System.out.println(jsonResponse.getRawBody());

        // Creating Request
        // So you're probably wondering how using Unirest makes creating requests in Java easier, here is a basic POST request that will explain everything:
        // Requests are made when as[Type]() is invoked, possible types include Json, Binary, String, Object.
        // If the request supports and it is of type HttpRequestWithBody, a body it can be passed along with .body(String|JsonNode|Object). For using .body(Object) some pre-configuration is needed
        // (see below).
        // If you already have a map of parameters or do not wish to use seperate field methods for each one there is a .fields(Map<String, Object> fields) method that will serialize each key - value
        // to form parameters on your request.
        // .headers(Map<String, String> headers) is also supported in replacement of multiple header methods.

        HttpResponse<JsonNode> jsonResponse1 =
                Unirest.post("http://httpbin.org/post").header("accept", "application/json").queryString("apiKey", "123").field("parameter", "value").field("foo", "bar").asJson();

        // Serialization
        // Before an asObject(Class) or a .body(Object) invokation, is necessary to provide a custom implementation of the ObjectMapper interface. This should be done only the first time, as the
        // instance of the ObjectMapper will be shared globally.
        // For example, serializing Json from\to Object using the popular Jackson ObjectMapper takes only few lines of code.

        /* // Only one time
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                        = new com.fasterxml.jackson.databind.ObjectMapper();
        
            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        
            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        
        // Response to Object
        HttpResponse<Book> bookResponse = Unirest.get("http://httpbin.org/books/1").asObject(Book.class);
        Book bookObject = bookResponse.getBody();
        
        HttpResponse<Author> authorResponse = Unirest.get("http://httpbin.org/books/{id}/author")
            .routeParam("id", bookObject.getId())
            .asObject(Author.class);
        
        Author authorObject = authorResponse.getBody();
        
        // Object to Json
        HttpResponse<JsonNode> postResponse = Unirest.post("http://httpbin.org/authors/post")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(authorObject)
                .asJson();*/

        // Route Parameters
        // Sometimes you want to add dynamic parameters in the URL, you can easily do that by adding a placeholder in the URL, and then by setting the route parameters with the routeParam function,
        // like:
        // In the example above the final URL will be http://httpbin.org/get - Basically the placeholder {method} will be replaced with get.
        // The placeholder's format is as easy as: {custom_name}
        Unirest.get("http://httpbin.org/{method}").routeParam("method", "get").queryString("name", "Mark").asJson();

        // Asynchronous Requests
        // Sometimes, well most of the time, you want your application to be asynchronous and not block, Unirest supports this in Java using anonymous callbacks, or direct method placement:
        Future<HttpResponse<JsonNode>> future =
                Unirest.post("http://httpbin.org/post").header("accept", "application/json").field("param1", "value1").field("param2", "value2").asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        System.out.println("The request has failed");
                    }

                    public void completed(HttpResponse<JsonNode> response) {
                        int code = response.getStatus();
                        Map<String, List<String>> headers = response.getHeaders();
                        JsonNode body = response.getBody();
                        InputStream rawBody = response.getRawBody();

                        System.out.println("code=" + code);
                        System.out.println("headers=" + headers);
                        System.out.println("JsonNode body=" + body);
                        System.out.println("rawBody=" + rawBody);
                    }

                    public void cancelled() {
                        System.out.println("The request has been cancelled");
                    }

                });

        // File Uploads
        // Creating multipart requests with Java is trivial, simply pass along a File or an InputStream Object as a field:
        HttpResponse<JsonNode> jsonResponse2 = Unirest.post("http://httpbin.org/post").header("accept", "application/json").field("parameter", "value").field("file", new File("/tmp/file")).asJson();

        // Custom Entity Body
        HttpResponse<JsonNode> jsonResponse3 = Unirest.post("http://httpbin.org/post").header("accept", "application/json").body("{\"parameter\":\"value\", \"foo\":\"bar\"}").asJson();

        // Byte Stream as Entity Body
        // final InputStream stream = new FileInputStream(new File(getClass().getResource("/image.jpg").toURI()));
        final InputStream stream = new FileInputStream(new File(PostExample.class.getResource("/image.jpg").toURI()));
        final byte[] bytes = new byte[stream.available()];
        stream.read(bytes);
        stream.close();
        final HttpResponse<JsonNode> jsonResponse4 = Unirest.post("http://httpbin.org/post").field("name", "Mark").field("file", bytes, "image.jpg").asJson();

        // InputStream as Entity Body
        HttpResponse<JsonNode> jsonResponse5 = Unirest.post("http://httpbin.org/post").field("name", "Mark")
                .field("file", new FileInputStream(new File(PostExample.class.getResource("/image.jpg").toURI())), ContentType.APPLICATION_OCTET_STREAM, "image.jpg")
                // .field("file", new FileInputStream(new File(getClass().getResource("/image.jpg").toURI())), ContentType.APPLICATION_OCTET_STREAM, "image.jpg")
                .asJson();

        // Basic Authentication
        // Authenticating the request with basic authentication can be done by calling the basicAuth(username, password) function:
        HttpResponse<JsonNode> response2 = Unirest.get("http://httpbin.org/headers").basicAuth("username", "password").asJson();

        // 1、Request
        // The Java Unirest library follows the builder style conventions. You start building your request by creating a HttpRequest object using one of the following:

        /* GetRequest request = Unirest.get(String url);
         GetRequest request = Unirest.head(String url);
         HttpRequestWithBody request = Unirest.post(String url);
         HttpRequestWithBody request = Unirest.put(String url);
         HttpRequestWithBody request = Unirest.patch(String url);
         HttpRequestWithBody request = Unirest.options(String url);
         HttpRequestWithBody request = Unirest.delete(String url);*/

        // 2、Response
        // Upon recieving a response Unirest returns the result in the form of an Object, this object should always have the same keys for each language regarding to the response details.

        /* .getStatus() - HTTP Response Status Code (Example: 200)
        .getStatusText() - HTTP Response Status Text (Example: "OK")
        .getHeaders() - HTTP Response Headers
        .getBody() - Parsed response body where applicable, for example JSON responses are parsed to Objects / Associative Arrays.
        .getRawBody() - Un-parsed response body*/

        // 3、Advanced Configuration
        // You can set some advanced configuration to tune Unirest-Java:

        // 31、Custom HTTP clients
        // You can explicitly set your own HttpClient and HttpAsyncClient implementations by using the following methods:

        /*Unirest.setHttpClient(httpClient);
        Unirest.setAsyncHttpClient(asyncHttpClient);*/

        // 32、Timeouts
        // You can set custom connection and socket timeout values (in milliseconds):
        // By default the connection timeout (the time it takes to connect to a server) is 10000, and the socket timeout (the time it takes to receive data) is 60000. You can set any of these timeouts
        // to zero to disable the timeout.

        /* Unirest.setTimeouts(long connectionTimeout, long socketTimeout);*/

        // 33、Default Request Headers
        // You can set default headers that will be sent on every request:

        /*Unirest.setDefaultHeader("Header1", "Value1");
        Unirest.setDefaultHeader("Header2", "Value2");*/

        // 34、You can clear the default headers anytime with:
        /*Unirest.clearDefaultHeaders();*/

        // 35、Concurrency
        // You can set custom concurrency levels if you need to tune the performance of the syncronous or asyncronous client:
        // By default the maxTotal (overall connection limit in the pool) is 200, and the maxPerRoute (connection limit per target host) is 20.

        /*Unirest.setConcurrency(int maxTotal, int maxPerRoute);*/

        // 36、Proxy
        // You can set a proxy by invoking:

        /*Unirest.setProxy(new HttpHost("127.0.0.1", 8000));*/

        // 37、 Exiting an application
        // Unirest starts a background event loop and your Java application won't be able to exit until you manually shutdown all the threads by invoking:

        /* Unirest.shutdown();*/

    }

    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

    public static String sendGet(String url) {
        try {
            HttpResponse<String> response = Unirest.get(url).header("User-Agent", USER_AGENT).asString();
            return response.getBody();
        } catch (UnirestException e) {
            logger.error("HTTP Get Error:{}", e);
        }
        return "";
    }

    public static String sendPost(String url, String bodyParams, String contentType, String cookie) {
        try {
            HttpResponse<String> response =
                    Unirest.post(url).header("User-Agent", USER_AGENT).header("Connection", "Keep-Alive").header("Content-Type", contentType).header("Cookie", cookie).body(bodyParams).asString();
            return response.getBody();
        } catch (UnirestException e) {
            logger.error("HTTP Get Error:{}", e);
        }
        return "";
    }

}
