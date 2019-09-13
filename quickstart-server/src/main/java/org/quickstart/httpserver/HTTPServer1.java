/**
 * 项目名称：quickstart-httpserver 文件名：HTTPServer.java 版本信息： 日期：2017年11月1日 Copyright yangzl Corporation 2017 版权所有 *
 */
package org.quickstart.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * HTTPServer
 * 
 * @author：youngzil@163.com
 * @2017年11月1日 上午10:06:07
 * @since 1.0
 */
public class HTTPServer1 {
  public static void main(String[] args) {
    try {
      ServerSocket ss = new ServerSocket();

      ss.setReuseAddress(true);
      ss.bind(new InetSocketAddress("127.0.0.1", 8899));

      while (true) {
        Socket socket = ss.accept();
        BufferedReader bd = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        /**
         * 接受HTTP请求
         */
        String requestHeader;
        int contentLength = 0;
        while ((requestHeader = bd.readLine()) != null && !requestHeader.isEmpty()) {
          System.out.println(requestHeader);
          /**
           * 获得GET参数
           */
          if (requestHeader.startsWith("GET")) {
            int begin = requestHeader.indexOf("/?") + 2;
            int end = requestHeader.indexOf("HTTP/");
            String condition = requestHeader.substring(begin, end);
            System.out.println("GET参数是：" + condition);
          }
          /**
           * 获得POST参数 1.获取请求内容长度
           */
          if (requestHeader.startsWith("Content-Length")) {
            int begin = requestHeader.indexOf("Content-Lengh:") + "Content-Length:".length();
            String postParamterLength = requestHeader.substring(begin).trim();
            contentLength = Integer.parseInt(postParamterLength);
            System.out.println("POST参数长度是：" + Integer.parseInt(postParamterLength));
          }
        }
        StringBuffer sb = new StringBuffer();
        if (contentLength > 0) {
          for (int i = 0; i < contentLength; i++) {
            sb.append((char) bd.read());
          }
          System.out.println("POST参数是：" + sb.toString());
        }
        // 发送回执
        PrintWriter pw = new PrintWriter(socket.getOutputStream());

        pw.println("HTTP/1.1 200 OK");
        pw.println("Content-type:text/html");
        pw.println();
        pw.println("<h1>Success HTTPServer1</h1>");

        pw.flush();
        socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
