[项目地址](https://github.com/youngzil/quickstart-http)



- HTTP协议
    - [HTTP学习](docs/HTTP/HTTP学习.md)
        - [Cannot assign requested address问题总结](docs/HTTP/HTTP学习.md#Cannot assign requested address问题总结)
        - [服务端设置TCP参数端口重用带来的问题](docs/HTTP/HTTP学习.md#服务端设置TCP参数端口重用带来的问题)
    - [HTTP和HTTP2的区别](docs/HTTP/HTTP和HTTP2的区别.md)
    - [HTTP获取客户端真实IP](docs/HTTP/HTTP获取客户端真实IP.md)
    - [HTTP返回状态码](docs/HTTP/HTTP返回状态码.md)
    - [HTTPie工具](docs/HTTP/HTTPie工具.md)
    - [http响应头里没有或者有content-length的几种可能性](docs/HTTP/http响应头里没有或者有content-length的几种可能性.md)
    - [HTTP、HTTPS、HTTP2、HTTP3区别](docs/HTTP/HTTP、HTTPS、HTTP2、HTTP3.md)
    - [HttpPOST提交数据的四种方式解析](docs/HTTP/HttpPOST提交数据的四种方式解析.md)
    - [HTTP的长连接和短连接](docs/HTTP/HTTP的长连接和短连接.md)
    - [HTTP请求头](docs/HTTP/HTTP请求头.md)
    - [Http请求模拟工具](docs/HTTP/Http请求模拟工具.md)
    - 网络抓包工具
        - [tcpdump抓包分析和Wireshark分析](docs/tcpdump和Wireshark使用)
            - [tcpdump安装](docs/tcpdump和Wireshark使用/Wireshark和tcpdump监控.md#Tcpdump安装)
            - [tcpdump命令和使用](docs/tcpdump和Wireshark使用/Wireshark和tcpdump监控.md#Tcpdump命令和使用)
            - [tcpdump原理分析](docs/tcpdump和Wireshark使用/Wireshark和tcpdump监控.md#Tcpdump原理分析)
            - [tcpdump和Wireshark网站](docs/tcpdump和Wireshark使用/Wireshark和tcpdump监控.md#Tcpdump和Wireshark网站)
            - [使用Tcpdump和Wireshark抓包分析](docs/tcpdump和Wireshark使用/Wireshark和tcpdump监控.md#使用Tcpdump和Wireshark抓包分析)
                - [一次完整HTTP请求流程的抓包记录和分析](docs/tcpdump和Wireshark使用/Wireshark和tcpdump监控.md#一次完整HTTP请求流程的抓包记录和分析)
                - [HAProxy心跳检查问题](docs/tcpdump和Wireshark使用/Wireshark和tcpdump监控.md#HAProxy心跳检查问题)
                - [TCP协议的RST标志位](docs/tcpdump和Wireshark使用/Wireshark和tcpdump监控.md#TCP协议的RST标志位)
        - [Charles抓包神器](docs/Charles抓包神器.md)

- TCPIP协议
    - [TCPIP网络传输](docs/TCPIP/TCPIP网络传输学习.md)
    - [TCP和UDP的区别](docs/TCPIP/TCP和UDP的最完整的区别.md)
    - [TCP流量控制](docs/TCPIP/TCP流量控制、拥塞控制.md)

- 其他网络设计
    - [高性能短链设计](docs/其他网络设计/高性能短链设计.md)
    - [Connection reset by peer的常见原因及解决办法](docs/其他网络设计/Connection%20reset的常见原因及解决办法.md)
    - [Cookie的SameSite属性](docs/Cookie的SameSite属性.md)
    - [CORS跨域请求](docs/CORS跨域请求.md)
    - [RFC3986之URL编码与解码](docs/RFC3986之URL编码与解码.md)
    - [Session和Cookie的区别与联系](docs/Session和Cookie的区别与联系.md)
    - [User-Agent类型](docs/User-Agent类型.md)
    - [WebSocket技术](docs/WebSocket技术.md)
    - [反向API实现](docs/反向API实现.md)
    - [异步API的设计](docs/异步API的设计.md)
    - [淘宝全站HTTPS实践](docs/淘宝全站HTTPS实践.pdf)
    - [计算机网络： 同步传输和异步传输](docs/计算机网络：同步传输和异步传输.md)



TCP：三次握手四次挥手、 DNS 查询、慢启动（TCP拥塞控制）、、、
HTTP1.0：
HTTP1.1：
HTTPS：SSL/TLS、CA证书
SPDY：消息头压缩算法DEFLATE
HTTP2：消息头压缩算法HPACK
Nginx：


HTTP2：https://http2.akamai.com/

http2讲解https://legacy.gitbook.com/book/ye11ow/http2-explained/details

讲解
http://www.alloyteam.com/2015/03/http2-0-di-qi-miao-ri-chang/

服务间通信之Http框架
https://blog.csdn.net/u012734441/article/details/76602247


TCP/HTTP/UDP client/server with Reactor over Netty http://projectreactor.io
https://github.com/reactor/reactor-netty
参考
/Users/yangzl/git/quickstart-framework/quickstart-reactor/src/test/java/org/quickstart/reactor/http


使用reactor-netty发送form请求，参数里面有&符号，后端接收不了




---------------------------------------------------------------------------------------------------------------------  


💪一堂课搞定HTTPS底层原理
----------------
什么是 SSL TLS HTTPS X.509？
SSL 只能用在HTTP上吗？
Https底层原理，对称加密与非对称加密
CA机构参与与CA伪造
HTTPS真的安全吗？模拟如何破解？
浏览器同源策略与跨域请求
XSS攻击原理及防御措施
🎈如何使用SpringSecurity 防御CSRF攻击
🎈单点Session + Cookies权限控制与集群化服务中的无状态Token
🎈CC/DDOS攻击与流量攻击
301、302、307跳转陷阱
大厂都会问什么？BATJ一面套餐
程序员该如何面对36岁
如何从传统项目转型！服务中间件大五类+微服务系统架构


[嘘]分布式架构微服务IO模型必须先搞懂TCPIP通信原理
----------------
[闪电]tcpip在开发中的作用
[闪电]什么是socket
[闪电]什么是三次握手四次挥手
[闪电]如何应对百万连接，如何制造百万连接
[闪电]数据包如何发送出去
[闪电]什么是路由表什么IP地址什么是掩码
[闪电]什么是网关，什么是下一跳
[闪电]什么是链路层，什么是原子通信
[闪电]什么是连接池，并发和连接的关系
[闪电]什么时候能复用什么时候不能复用连接
[闪电]有状态无状态通信的本质是啥
[闪电]拆包粘包到底谁才是罪魁祸首
[闪电]什么是面向连接，什么是可靠传输
[闪电]什么是负载均衡的原子保障
[闪电]百万连接下代理层如何保障后端服务器
[闪电]深刻理解分层解耦的软件工程学" 


---------------------------------------------------------------------------------------------------------------------  







