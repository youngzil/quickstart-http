1、TCPIP网络传输学习.md  
2、Cannot assign requested address问题总结  
3、tcpdump抓包分析和Wireshark分析  
4、故障的表现是负载均衡无法与后端主机nginx无法建立TCP连接。通过抓包发现SYN包无反回，产生大量重送。  
5、  
6、  
7、  
8、  
9、  
10、  
11、  
12、  
13、  
14、  
15、  
16、  
17、  
  
  
  
  
  
  
---------------------------------------------------------------------------------------------------------------------  
Cannot assign requested address问题总结：压测出现的问题  
  
  
Caused by: java.net.ConnectException: Cannot assign requested address (connect failed)  
由于程序对外建立新连接，结果本地端口已经用完导致的异常。  
大致上是由于客户端频繁的连服务器，由于每次连接都在很短的时间内结束，导致很多的TIME_WAIT，以至于用光了可用的端 口号，所以新的连接没办法绑定端口，即“Cannot assign requested address”。是客户端的问题不是服务器端的问题。通过netstat，的确看到很多TIME_WAIT状态的连接。  
原因是客户端频繁的连服务器，由于每次连接都在很短的时间内结束，导致很多的TIME_WAIT，以至于用光了可用的端 口号，所以新的连接没办法绑定端口，因此就会跑出异常说 无法分配请求地址。这是一个客户端的问题，与程序服务是没有关系的，而我们目前的程序Http请求也是没有线程池的。  
  
服务器在存在大量短连接的情况下，Linux的TCP栈一般都会生成大量的 TIME_WAIT 状态的socket，有时候这个数量大到惊人的。而这个数量增大会占用系统的内核，而内核可不是无限的。  
  
  
查看:  
linux下可用netstat -a|grep TIME_WAIT 这个命令去查看一下连接数  
可以直接用命令netstat -ant|grep -i time_wait |wc -l 查看。  
  
  
  
解决方案  
1、横向扩展： 简单就是加机器，减少单台服务器的TCP创建次数。  
2、调整linux内核参数  
3、修改TCP短连接为长连接：连接池等  
  
  
  
因此比较直接的解决方式就是修改系统内核相关的参数：  
net.ipv4.ip_forward=1  表示开启对于TCP时间戳的支持,若该项设置为为0，则net.ipv4.tcp_tw_reuse设置不起作用，该值系统默认是0  
net.ipv4.tcp_syncookies = 1  表示开启对于TCP时间戳的支持,若该项设置为为0，则net.ipv4.tcp_tw_reuse设置不起作用，该值系统默认是0  
net.ipv4.tcp_tw_reuse = 1  表示开启重用。允许将TIME-WAIT sockets重新用于新的TCP连接，默认为0，表示关闭  
net.ipv4.tcp_tw_recycle = 1  表示开启TCP连接中TIME-WAIT sockets的快速回收，默认为0，表示关闭  
net.ipv4.tcp_fin_timeout = 30  修改系統默认的TIMEOUT时间，默认是60  
  
  
  
  
sysctl -w net.ipv4.ip_forward=1  
sysctl -w net.ipv4.tcp_fin_timeout=10  
sysctl -w net.ipv4.tcp_timestamps=1  
sysctl -w net.ipv4.tcp_tw_recycle=1  
sysctl -w net.ipv4.tcp_tw_reuse = 1  
  
  
编辑 /etc/sysctl.conf 文件，加入参数内容  
net.ipv4.ip_forward=1  
net.ipv4.tcp_fin_timeout=10  
net.ipv4.tcp_timestamps=1  
net.ipv4.tcp_tw_recycle=1  
net.ipv4.tcp_tw_reuse=1  
编辑完成后执行命令 /sbin/sysctl -p  让参数立即生效。  
  
  
  
    
修改方式：    
    
编辑 /etc/sysctl.conf 文件，加入参数内容，以上5个参数是我查到的认为有效的结果，然后我的实际情况仅修改了其中三个：    
net.ipv4.ip_forward=1    
net.ipv4.tcp_tw_reuse = 1    
net.ipv4.tcp_tw_recycle = 1    
编辑完成后执行命令 /sbin/sysctl -p  让参数立即生效。至于气他参数就看具体情况了。我这种修改方式是直接永久保存的，不会受网络服务重启，系统重启等的影响，若要临时修改请自行查询命令。    
    
    
执行命令修改如下内核参数 （需要root权限）     
    
调低端口释放后的等待时间，默认为60s，修改为15~30s：    
sysctl -w net.ipv4.tcp_fin_timeout=30    
    
修改tcp/ip协议配置， 通过配置/proc/sys/net/ipv4/tcp_tw_resue, 默认为0，修改为1，释放TIME_WAIT端口给新连接使用：    
sysctl -w net.ipv4.tcp_timestamps=1    
    
修改tcp/ip协议配置，快速回收socket资源，默认为0，修改为1：    
sysctl -w net.ipv4.tcp_tw_recycle=1    
    
允许端口重用：    
sysctl -w net.ipv4.tcp_tw_reuse = 1    
    
sysctl -w net.ipv4.tcp_timestamps=1  开启对于TCP时间戳的支持,若该项设置为0，则下面一项设置不起作用    
sysctl -w net.ipv4.tcp_tw_recycle=1  表示开启TCP连接中TIME-WAIT sockets的快速回收    
    
linux内核中存在两个参数：    
      net.ipv4.tcp_tw_reuse = 1表示开启重用。允许将TIME-WAIT sockets重新用于新的TCP连接，默认为0，表示关闭；    
      net.ipv4.tcp_tw_recycle = 1表示开启TCP连接中TIME-WAIT sockets的快速回收，默认为0，表示关闭。    
在/etc/sysctl.conf文件中加入上述参数，然后执行/sbin/sysctl -p让参数生效。    
    
    
    
参考    
https://www.jianshu.com/p/51a953b789a4    
https://www.cnblogs.com/tongbk/p/10442595.html    
  
  
---------------------------------------------------------------------------------------------------------------------  
tcpdump抓包分析和Wireshark分析  
  
参考  
/Users/yangzl/git/quickstart-framework/quickstart-document/doc/base/Wireshark和tcpdump监控.md  
  
  
---------------------------------------------------------------------------------------------------------------------  
  
故障的表现是负载均衡无法与后端主机nginx无法建立TCP连接。通过抓包发现SYN包无反回，产生大量重送。  
  
通过netstat -s|grep reject可看到  
passive connections rejected because of  time stamp  
packets rejects in established  connections because of timestamp  
  
查询nginx主机tcp配置可见  
net.ipv4.tcp_tw_recycle = 1  
该配置(tcp_timestamps默认开启)可使服务主机缓存每个客户主机最新的时间戳。后续请求中如果时间戳小于缓存的时间戳，即视为无效而被丢弃。  
但是当多个客户端通过NAT方式联网，并服务端交互时，服务端看到的是同一个IP，这些客户端等同于一个。而这些客户端的时间戳可能存在差异，服务端直接丢弃时间戳小的数据包。  
tcp_tw_recycle改回默认值0后问题解决  
  
实际情况是尝试了各种办法都无法解决问题，最后对比了网关故障主机和其他正常主机TCP配置差异后，尝试修改后发现可用。以上分析是我事后猜的。  
  
net.ipv4.tcp_tw_recycle = 0  
net.ipv4.tcp_tw_reuse = 0  
  
  
  
---------------------------------------------------------------------------------------------------------------------  
Http讲解  
  
https://developer.mozilla.org/zh-CN/docs/Web/HTTP  
https://blog.csdn.net/qzcsu/article/details/72861891



  
---------------------------------------------------------------------------------------------------------------------  


CLOSE_WAIT状态的原因与解决方法
https://www.cnblogs.com/Leslieblog/p/10413202.html


Linux记录-TCP状态以及（TIME_WAIT/CLOSE_WAIT）分析
https://www.cnblogs.com/xinfang520/p/8961129.html

---------------------------------------------------------------------------------------------------------------------  
  
  
  
  
quickstart-application-container  
quickstart-webservice  
  
应用服务器：jboss、jetty、jersey、tomcat、weblogic、WebSphere、  
  
  
  
  
  
  
  
  
  
