HTTP的长连接和短连接


HTTP1.1规定了默认保持长连接（HTTP persistent connection ，也有翻译为持久连接），数据传输完成了保持TCP连接不断开（不发RST包、不四次握手），等待在同域名下继续用这个通道传输数据；相反的就是短连接。



使用设置
这里的设置，我们都以HTTP1.1协议为例子。

设置HTTP短连接
在首部字段中设置Connection:close，则在一次请求/响应之后，就会关闭连接。

设置HTTP长连接，有过期时间
在首部字段中设置Connection:keep-alive 和Keep-Alive: timeout=60，表明连接建立之后，空闲时间超过60秒之后，就会失效。如果在空闲第58秒时，再次使用此连接，则连接仍然有效，使用完之后，重新计数，空闲60秒之后过期。

设置HTTP长连接，无过期时间
在首部字段中只设置Connection:keep-alive，表明连接永久有效。




参考
https://www.cnblogs.com/cswuyg/p/3653263.html
https://www.cnblogs.com/0201zcr/p/4694945.html
https://blog.csdn.net/weixin_38054045/article/details/104155681
https://www.cnblogs.com/shoren/p/http-connection.html


