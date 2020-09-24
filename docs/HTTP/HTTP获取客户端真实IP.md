HTTP 获取客户端真实IP


1、Java代码获取
2、这些请求头的含义
3、Nginx配置请求头



1、Java代码获取
```
/**
 * 获取客户端的IP
 *
 * @param request
 * @return
 */
public static String getRemoteAddr(HttpServletRequest request) {
    String ip = "";
    try {
        ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                try {
                    InetAddress inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException e) {
                }
            }
        }
        return ip.split(",")[0];
    } catch (Exception e) {
    }
    return ip;
}

```



2、这些请求头的含义
先说说这些请求头的意思

X-Forwarded-For
这是一个 Squid 开发的字段，只有在通过了HTTP代理或者负载均衡服务器时才会添加该项。
格式为X-Forwarded-For:client1,proxy1,proxy2，一般情况下，第一个ip为客户端真实ip，后面的为经过的代理服务器ip。现在大部分的代理都会加上这个请求头。

Proxy-Client-IP/WL- Proxy-Client-IP
这个一般是经过apache http服务器的请求才会有，用apache http做代理时一般会加上Proxy-Client-IP请求头，而WL-Proxy-Client-IP是他的weblogic插件加上的头。

HTTP_CLIENT_IP
有些代理服务器会加上此请求头。

X-Real-IP
nginx代理一般会加上此请求头。



有几点要注意

1、这些请求头都不是http协议里的标准请求头，也就是说这个是各个代理服务器自己规定的表示客户端地址的请求头。如果哪天有一个代理服务器软件用oooo-client-ip这个请求头代表客户端请求，那上面的代码就不行了。

2、这些请求头不是代理服务器一定会带上的，网络上的很多匿名代理就没有这些请求头，所以获取到的客户端ip不一定是真实的客户端ip。代理服务器一般都可以自定义请求头设置。

3、即使请求经过的代理都会按自己的规范附上代理请求头，上面的代码也不能确保获得的一定是客户端ip。不同的网络架构，判断请求头的顺序是不一样的。

4、最重要的一点，请求头都是可以伪造的。如果一些对客户端校验较严格的应用（比如投票）要获取客户端ip，应该直接使用ip = request.getRemoteAddr ()，虽然获取到的可能是代理的ip而不是客户端的ip，但这个获取到的ip基本上是不可能伪造的，也就杜绝了刷票的可能。(有分析说arp欺骗+syn有可能伪造此ip，如果真的可以，这是所有基于TCP协议都存在的漏洞)，这个ip是tcp连接里的ip。
————————————————
版权声明：本文为CSDN博主「fengwind1」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/fengwind1/article/details/51992528





3、Nginx配置请求头

Nginx一般会配置：X-Forwarded-For 和 X-Real-IP

proxy_set_header  X-Real-IP  $remote_addr;
proxy_set_header  X-Forwarded-For  $proxy_add_x_forwarded_for;

proxy_set_header X-Forward-For $remote_addr ;

第一版配置：
proxy_set_header X-Forwarded-For $remote_addr;
#如上配置只能增加负载均衡ip地址，丢失了客户端真实ip和任意中间代理ip

第二版配置
proxy_set_header X-Forwarded-For "$http_x_forwarded_for, $remote_addr";
#如上配置在原有请求头X-Forwarded-For字段的基础上增加了连接nginx的服务器ip地址

第三版配置（最为简单）
proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
#$proxy_add_x_forwarded_for已经实现了第二版的功能，直接使用该内置变量即可
————————————————
版权声明：本文为CSDN博主「lylee1981」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/hellolingyun/article/details/34093223





参考
https://www.cnblogs.com/zwdx/p/8989663.html
https://segmentfault.com/a/1190000015379116

