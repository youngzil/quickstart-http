http响应头里没有或者有content-length的几种可能性


我们遇到的问题，使用reactor-netty作为Http的客户端，默认使用请求头中transfer-encoding: chunked，并且设置tcpClient的.chunkedTransfer(false)不生效，
发现是源码里面对POST请求强制使用transfer-encoding: chunked传输
后面升级reactor-netty的版本解决生效问题，不使用transfer-encoding传输
不好：太古老

使用tcpdump抓TCP请求报文
使用content-length传输：请求头和请求体中是....分隔
使用transfer-encoding传输：请求头和请求体中是....4d..分隔，多了4d..




http响应头里没有或者有content-length获取不到不生效的几种可能性：

对于http的请求返回结果要进行内容的长度校验主要有两种方式，二者互斥使用
1.客户端在http头(head)加Connection:keep-alive时，服务器的response是Transfer-Encoding:chunked的形式，通知页面数据是否接收完毕，例如长连接或者程序运行中可以动态的输出内容，例如一些运算比较复杂且需要用户及时的得到最新结果，那就采用chunked编码将内容分块输出。
2.除了如1所述之外的情况一般都是可以获取到Content-Length的。


在具体的HTTP交互中，客户端是如何获取消息长度的呢，主要基于以下几个规则：
1、响应为1xx，204，304相应或者head请求，则直接忽视掉消息实体内容。
2、如果有Transfer-Encoding，则优先采用Transfer-Encoding里面的方法来找到对应的长度。比如说Chunked模式。
3、有了Transfer-Encoding，则不能有Content-Length。
    “如果head中有Content-Length，那么这个Content-Length既表示实体长度，又表示传输长度。
    如果实体长度和传输长度不相等（比如说设置了Transfer-Encoding），那么则不能设置Content-Length。
    如果设置了Transfer-Encoding，那么Content-Length将被忽视”。
    这句话翻译的优点饶，其实关键就一点：有了Transfer-Encoding，则不能有Content-Length。


HTTP1.1必须支持chunk模式。因为当不确定消息长度的时候，可以通过chunk机制来处理这种情况。
在包含消息内容的header中，如果有content-length字段，那么该字段对应的值必须完全和消息主题里面的长度匹配。
“The entity-length of a message is the length of the message-body before any transfer-codings have been applied”
也就是有chunk就不能有content-length 。


其实后面几条几乎可以忽视，简单总结后如下：
1、Content-Length如果存在并且有效的话，则必须和消息内容的传输长度完全一致。（经过测试，如果过短则会截断，过长则会导致超时。）
2、如果存在Transfer-Encoding（重点是chunked），则在header中不能有Content-Length，有也会被忽视。
3、如果采用短连接，则直接可以通过服务器关闭连接来确定消息的传输长度。（这个很容易懂）

结合HTTP协议其他的特点，比如说Http1.1之前的不支持keep alive。那么可以得出以下结论：
1、在Http 1.0及之前版本中，content-length字段可有可无。因为这之前都不支持长连接.
2、在http1.1及之后版本。如果是keep alive，则content-length和chunk必然是二选一。若是非keep alive，则和http1.0一样。content-length可有可无.



综合之上总结, 我们的问题: tinyproxy HTTP服务器返回是以HTTP/1.0返回的, 而且标识了Connection: Close头. 
而浏览器请求是以HTTP/1.1请求, 并标识了Connection: Keep-Alive请求的, 所以浏览器期望收到至少带有Transfer-Encoding（重点是chunked）或者Content-Length其中一种方式的头. 
而我们的代理直接发送HTTP响应应该就有问题: 
尝试解决办法:
1. hmconfig解析HTTP, 收到的HTTP响应如果带有Connection: Close的数据发送完成后, 关闭对浏览器的连接.
2. 设备端解析HTTP, 对没有content-length的HTTP响应补充头.


参考
https://www.cnblogs.com/lovelacelee/p/5385683.html

