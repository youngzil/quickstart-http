package org.quickstart.asynchttpclient;

import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.asynchttpclient.proxy.ProxyServer;
import org.asynchttpclient.proxy.ProxyServerSelector;
import org.asynchttpclient.uri.Uri;
import org.asynchttpclient.ws.WebSocket;
import org.asynchttpclient.ws.WebSocketListener;
import org.asynchttpclient.ws.WebSocketUpgradeHandler;
import org.junit.Test;
import static org.asynchttpclient.Dsl.*;
import java.net.ProxySelector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.get;

public class BasicTest {

  /** 执行同步HTTP请求 */
  @Test
  public void synRequest() {

    String url = "http://www.baidu.com";
    AsyncHttpClient c = new DefaultAsyncHttpClient();
    Future<Response> f = c.prepareGet(url).execute();
    try {
      System.out.println(f.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

  /** 执行异步HTTP请求 */
  @Test
  public void asyncRequest() {

    String url = "http://www.baidu.com";
    AsyncHttpClient c = new DefaultAsyncHttpClient();
    Future<Response> f =
        c.prepareGet(url)
            .execute(
                new AsyncCompletionHandler<Response>() {

                  // onCompleted method will be invoked once the http response has been fully read.
                  // 一旦完全读取Http响应，就调用onCompleted方法
                  // Response object includes the http headers and the response body.
                  // Response 对象包括HTTP请求头和响应体
                  @Override
                  public Response onCompleted(Response response) throws Exception {
                    System.out.println("完成请求");
                    return response;
                  }

                  @Override
                  public void onThrowable(Throwable t) {
                    System.out.println("出现异常");
                    super.onThrowable(t);
                  }
                });

    try {
      Response response = f.get();
      System.out.println(response.getResponseBody());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

    @Test
    public void testCongigure() {

        AsyncHttpClient c = asyncHttpClient(config().setProxyServer(proxyServer("127.0.0.1", 38080)));

         AsyncHttpClient client = asyncHttpClient(
            new DefaultAsyncHttpClientConfig.Builder()
                .setFollowRedirect(true)
                // .setProxyServerSelector(new ProxySelector())
                .setIoThreadsCount(Runtime.getRuntime().availableProcessors() * 2)
                .setConnectTimeout(1000)
                .setReadTimeout(1000)
                .setRequestTimeout(3000)
                .setMaxRequestRetry(2)
                .setThreadPoolName("ASYNC-CLIENT")
        );

         /*class ProxySelector implements ProxyServerSelector {

            @Override
            public ProxyServer select(Uri uri) {
                //从代理池中获取HttpHost
                final HttpHost proxy = getProxy(uri.getHost());
                if (proxy == null) {
                    return null;
                }
                final ProxyServer.Builder builder = new ProxyServer.Builder(proxy.getHostName(), proxy.getPort());
                return builder.build();
            }
        }*/

    }

  @Test
  public void testGet() throws ExecutionException, InterruptedException {

    Future<Integer> whenStatusCode =
        asyncHttpClient()
            .prepareGet("http://www.baidu.com/")
            .execute(
                new AsyncHandler<Integer>() {
                  private Integer status;

                  @Override
                  public State onStatusReceived(HttpResponseStatus responseStatus)
                      throws Exception {
                    status = responseStatus.getStatusCode();
                    return State.ABORT;
                  }

                  @Override
                  public State onHeadersReceived(HttpHeaders headers) throws Exception {
                    return State.ABORT;
                  }

                  @Override
                  public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                    return State.ABORT;
                  }

                  @Override
                  public Integer onCompleted() throws Exception {
                    return status;
                  }

                  @Override
                  public void onThrowable(Throwable t) {}
                });

    Integer statusCode = whenStatusCode.get();
    System.out.println("statusCode=" + statusCode);
  }

  @Test
  public void testGet32() {

    // bound
    Future<Response> whenResponse = asyncHttpClient().prepareGet("http://www.baidu.com/").execute();

    // unbound
    Request request = get("http://www.baidu.com/").build();
    Future<Response> whenResponse2 = asyncHttpClient().executeRequest(request);
  }

  @Test
  public void testWebSocket() throws ExecutionException, InterruptedException {

    WebSocket websocket =
        asyncHttpClient()
            .prepareGet("ws://demos.kaazing.com/echo")
            .execute(
                new WebSocketUpgradeHandler.Builder()
                    .addWebSocketListener(
                        new WebSocketListener() {

                          @Override
                          public void onOpen(WebSocket websocket) {
                            websocket.sendTextFrame("...");
                          }

                          @Override
                          public void onClose(WebSocket websocket, int code, String reason) {}

                          @Override
                          public void onTextFrame(String payload, boolean finalFragment, int rsv) {
                            System.out.println(payload);
                          }

                          @Override
                          public void onError(Throwable t) {}
                        })
                    .build())
            .get();
  }
}
