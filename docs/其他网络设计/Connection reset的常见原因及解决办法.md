Connection reset的常见原因及解决办法

简单的说就是在连接断开后的读和写操作引起的。



1. 如果一端的Socket被关闭（或主动关闭，或因为异常退出而 引起的关闭），另一端仍发送数据，发送的第一个数据包引发该异常(Connect reset by peer)。
Socket默认连接60秒，60秒之内没有进行心跳交互，即读写数据，就会自动关闭连接。

2. 一端退出，但退出时并未关闭该连接，另一端如果在从连接中读数据则抛出该异常（Connection reset）。  
    导致“Connection reset”的原因是服务器端因为某种原因关闭了Connection，而客户端依然在读写数据，此时服务器会返回复位标志“RST”，然后此时客户端就会提示“java.net.SocketException: Connection reset”。

另外还有一种比较常见的错误“Connection reset by peer”，该错误和“Connection reset”是有区别的：
- 服务器返回了“RST”时，如果此时客户端正在从Socket套接字的输出流中读数据则会提示Connection reset”；
- 服务器返回了“RST”时，如果此时客户端正在往Socket套接字的输入流中写数据则会提示“Connection reset by peer”。


可以看到握手时会在客户端和服务器之间传递一些TCP头信息，比如ACK标志、SYN标志以及挥手时的FIN标志等。

除了以上这些常见的标志头信息，还有另外一些标志头信息，比如推标志PSH、复位标志RST等。其中复位标志RST的作用就是“复位相应的TCP连接”。

前面说到出现“Connection reset”的原因是服务器关闭了Connection[调用了Socket.close()方法]。  
大家可能有疑问了：服务器关闭了Connection为什么会返回“RST”而不是返回“FIN”标志。  
原因在于Socket.close()方法的语义和TCP的“FIN”标志语义不一样：发送TCP的“FIN”标志表示我不再发送数据了，而Socket.close()表示我不在发送也不接受数据了。  
问题就出在“我不接受数据” 上，如果此时客户端还往服务器发送数据，服务器内核接收到数据，但是发现此时Socket已经close了，则会返回“RST”标志给客户端。当然，此时客户端就会提示：“Connection reset”。  
详细说明可以参考oracle的有关文档：http://docs.oracle.com/javase/1.5.0/docs/guide/net/articles/connection_release.html。



前面谈到了导致“Connection reset”的原因，而具体的解决方案有如下几种：
- 出错了重试；
- 客户端和服务器统一使用TCP长连接；
- 客户端和服务器统一使用TCP短连接。

Connection reset by peer的常见原因：
1）服务器的并发连接数超过了其承载量，服务器会将其中一些连接关闭；
2）客户关掉了浏览器，而服务器还在给客户端发送数据；
3）浏览器端按了Stop；
4）防火墙的问题；
5）JSP的buffer问题。




参考  
https://my.oschina.net/xionghui/blog/508758  
https://blog.csdn.net/xc_zhou/article/details/80950753  


