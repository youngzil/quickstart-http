package org.quickstart.asynchttpclient;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.asynchttpclient.Dsl.asyncHttpClient;

public class CompletableFutureTest {

  public static void main(String[] args) throws IOException {
    try (AsyncHttpClient asyncHttpClient = asyncHttpClient()) {
      asyncHttpClient
          .prepareGet("http://www.baidu.com/")
          .execute()
          .toCompletableFuture()
          .thenApply(Response::getResponseBody)
          .thenAccept(System.out::println)
          .join();

      CompletableFuture<Response> whenResponse =
          asyncHttpClient
              .prepareGet("http://www.baidu.com/")
              .execute()
              .toCompletableFuture()
              .exceptionally(
                  t -> {
                    /* Something wrong happened... */
                    return null;
                  })
              .thenApply(
                  response -> {
                    /*  Do something with the Response */
                    return response;
                  });
      whenResponse.join(); // wait for completion
    }
  }
}
