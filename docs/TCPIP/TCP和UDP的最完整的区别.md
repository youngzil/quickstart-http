TCP和UDP的最完整的区别

TCP（Transmission Control Protocol，传输控制协议）是面向连接的协议，也就是说，在收发数据前，必须和对方建立可靠的连接。 一个TCP连接必须要经过三次“对话”才能建立起来，其中的过程非常复杂， 只简单的描述下这三次对话的简单过程：  
  
UDP（User Data Protocol，用户数据报协议）  
  
  
TCP：三次握手、四次挥手，面向连接的  
UDP：UDP是无连接的，不保证数据丢失、数据顺序  
  
  
TCP与UDP基本区别  
  1.基于连接与无连接  
  2.TCP要求系统资源较多，UDP较少；   
  3.UDP程序结构较简单   
  4.流模式（TCP）与数据报模式(UDP);   
  5.TCP保证数据正确性，UDP可能丢包   
  6.TCP保证数据顺序，UDP不保证   
  
  
UDP应用场景：  
 1.面向数据报方式  
 2.网络数据大多为短消息  
 3.拥有大量Client  
 4.对数据安全性无特殊要求  
 5.网络负担非常重，但对响应速度要求高  
  
  
具体编程时的区别  
 1.socket()的参数不同  
　　 2.UDP Server不需要调用listen和accept  
　　 3.UDP收发数据用sendto/recvfrom函数  
　　 4.TCP：地址信息在connect/accept时确定  
　　 5.UDP：在sendto/recvfrom函数中每次均 需指定地址信息  
　　 6.UDP：shutdown函数无效  
  
TCP与UDP区别总结：  
1、TCP面向连接（如打电话要先拨号建立连接）;UDP是无连接的，即发送数据之前不需要建立连接  
2、TCP提供可靠的服务。也就是说，通过TCP连接传送的数据，无差错，不丢失，不重复，且按序到达;UDP尽最大努力交付，即不保  证可靠交付  
3、TCP面向字节流，实际上是TCP把数据看成一连串无结构的字节流;UDP是面向报文的  
 UDP没有拥塞控制，因此网络出现拥塞不会使源主机的发送速率降低（对实时应用很有用，如IP电话，实时视频会议等）  
4、每一条TCP连接只能是点到点的;UDP支持一对一，一对多，多对一和多对多的交互通信  
5、TCP首部开销20字节;UDP的首部开销小，只有8个字节  
6、TCP的逻辑通信信道是全双工的可靠信道，UDP则是不可靠信道  




参考  
https://blog.csdn.net/Li_Ning_/article/details/52117463  
https://zhuanlan.zhihu.com/p/24860273  
https://blog.csdn.net/u013346007/article/details/75116999  
https://blog.csdn.net/qq_18425655/article/details/51955674  



---------------------------------------------------------------------------------------------------------------------  
  
  
