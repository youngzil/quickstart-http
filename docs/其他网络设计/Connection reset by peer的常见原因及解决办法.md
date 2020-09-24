Connection reset by peer的常见原因及解决办法



1，如果一端的Socket被关闭（或主动关闭，或因为异常退出而 引起的关闭），另一端仍发送数据，发送的第一个数据包引发该异常(Connect reset by peer)。
Socket默认连接60秒，60秒之内没有进行心跳交互，即读写数据，就会自动关闭连接。

2，一端退出，但退出时并未关闭该连接，另一端如果在从连接中读数据则抛出该异常（Connection reset）。

简单的说就是在连接断开后的读和写操作引起的。




Connection reset by peer的常见原因：
1）服务器的并发连接数超过了其承载量，服务器会将其中一些连接关闭；
2）客户关掉了浏览器，而服务器还在给客户端发送数据；
3）浏览器端按了Stop；
4）防火墙的问题；
5）JSP的buffer问题。






参考  
https://blog.csdn.net/xc_zhou/article/details/80950753  


