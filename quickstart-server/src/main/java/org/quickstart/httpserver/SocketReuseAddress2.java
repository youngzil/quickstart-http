package org.quickstart.httpserver;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019-08-07 19:25
 */
public class SocketReuseAddress2 {

  public static void main(String[] args) {
    try {

      ServerSocket socket1 = new ServerSocket();
      socket1.setReuseAddress(true);
      socket1.bind(new InetSocketAddress("0.0.0.0", 8899));
      System.out.println("socket1.getReuseAddress():" + socket1.getReuseAddress());


     /* ServerSocket socket2 = new ServerSocket();
      socket2.setReuseAddress(true);
      socket2.bind(new InetSocketAddress("127.0.0.1", 8899));
      System.out.println("socket2.getReuseAddress():" + socket1.getReuseAddress());*/

     System.in.read();

    } catch (Exception e) {

      e.printStackTrace();

    }
  }

}
