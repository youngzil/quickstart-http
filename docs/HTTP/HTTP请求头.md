HTTP请求头、返回头
HTTP请求头via


---------------------------------------------------------------------------------------------------------------------  

HTTP请求头、返回头

通常HTTP消息包括客户机向服务器的请求消息和服务器向客户机的响应消息。
客户端向服务器发送一个请求，请求头包含请求的方法、URI、协议版本、以及包含请求修饰符、客户信息和内容的类似于MIME的消息结构。
服务器以一个状态行作为响应，相应的内容包括消息协议的版本，成功或者错误编码加上包含服务器信息、实体元信息以及可能的实体内容。

DNS寻址【获得IP】---》初始化连接【创建套接字】---》初始化HTTP连接【等待发送和接受数据】---》数据发送【数据发送和接受】

Http协议定义了很多与服务器交互的方法，最基本的有4种，分别是GET、POST、PUT、DELETE。
一个URL地址用于描述一个网络上的资源，而HTTP中的GET、POST、PUT、 DELETE就对应着对这个资源的查、改、增、删4个操作，我们最常见的就是GET和POST了。
GET一般用于获取/查询资源信息，而POST一般用于更新资源信息。



HTTP协议请求头信息和响应头信息
http的请求部分
基本结构
  1、请求行 GET  /test/hello.html HTTP/1.1
  2、消息头(并不是每一次请求都一样)
  3、空行
  4、内容(内容名字=内容体)

常用请头信息
   Accept:text/html,image/*(告诉服务器，浏览器可以接受文本，网页图片)
   Accept-Charaset:ISO-8859-1 [接受字符编码：iso-8859-1]
   Accept-Encoding:gzip,compress[可以接受  gzip,compress压缩后数据]
   Accept-Language:zh-cn[浏览器支持的语言]   
   Host:localhost:8080[浏览器要找的主机]
   IF-MODIFIED-Since:Tue,11Jul 2000 18:23:51[告诉服务器我这缓存中有这个文件,该文件的时间]
   Referer:http://localhost:8080/test/abc.html[告诉服务器我来自哪里,常用于防止下载，盗链]
   User-Agent:Nozilla/4.0(Com...)[告诉服务器我的浏览器内核]
   Cookie：[Cookie，常用于认证]
   Connection:close/Keep-Alive [保持链接，发完数据后，我不关闭链接]
   Date:[浏览器发送数据的请求时间]

常用响应头信息
     location:http://www.baidu.org/index.jsp
     server:apache tomcat [告诉浏览器我是tomcat]
     Content-Encoding:gzip[告诉浏览器我使用了gzip]
     Content-Lenght:80 [告诉浏览器回送的数据大小]
     Content-Language:zh-cn[支持中文]
     Content-Type:text/html;charset=gb2312[内容格式和编码]
     Last-Modified:Tue,11 Juj,2000 18 18:29:20[告诉浏览器该资源上次更新
　　　　　　　时间是多少]
     Refresh:1;url=http://www.baidu.com[过多久刷新到哪里去]
     Content-Disposition;attachment;filename=aaa.zip[告诉浏览器有文件下载]
     Transfer-Encoding:chunked［传输编码］
     Set-Cookie:
     Expires:-1[告诉浏览器如何缓存页面]
     cache-Control:[告诉浏览器如何缓存页面(因为浏览器的兼容性最好设置两个)]
     pragma:no-cache
     Connection:close/Keep-Alive
     Date:Tue,11 Jul 2000 18:23:51

(1)有些网站对及时性比较高，我们不缓存页面
response.setDateHeader("Expires",-1);
//为了保证兼容性
response.setHeader("Cache-Control","no-cache")
response.setHeader("Pragma","no-cache")
(2)有些网站要求网页缓存一定时间，比如缓存一个小时
response.setDateHeader("Expires",System.currentimeMillis()*3600*1000*24);

通用信息头
Cache-Control:no-cache
Pragma:no-cache
Connection:close/Keep-Alive
Date:时间



一、HTTP头信息解读
　　HTTP的头域包括通用头、请求头、响应头和实体头四个部分。每个头域由一个域名，冒号（:）和域值三部分组成。
　　通用头部：是客户端和服务器都可以使用的头部，可以在客户端、服务器和其他应用程序之间提供一些非常有用的通用功能，如Date头部。
　　请求头部：是请求报文特有的，它们为服务器提供了一些额外信息，比如客户端希望接收什么类型的数据，如Accept头部。
　　响应头部：便于客户端提供信息，比如，客服端在与哪种类型的服务器进行交互，如Server头部。
　　实体头部：指的是用于应对实体主体部分的头部，比如，可以用实体头部来说明实体主体部分的数据类型，如Content-Type头部。

  1、HTTP通用头
　　通用头域包含请求和响应消息都支持的头域，通用头域包含缓存头部Cache-Control、Pragma及信息性头部Connection、Date、Transfer-Encoding、Update、Via。

  2、HTTP请求头
　　请求头用于说明是谁或什么在发送请求、请求源于何处，或者客户端的喜好及能力。服务器可以根据请求头部给出的客户端信息，试着为客户端提供更好的响应。请求头域可能包含下列字段Accept、Accept-Charset、Accept- Encoding、Accept-Language、Authorization、From、Host、If-Modified-Since、If-Match、If-None-Match、If-Range、If-Range、If-Unmodified-Since、Max-Forwards、Proxy-Authorization、Range、Referer、User-Agent。对请求头域的扩展要求通讯双方都支持，如果存在不支持的请求头域，一般将会作为实体头域处理。

  3、HTTP响应头
　　响应头向客户端提供一些额外信息，比如谁在发送响应、响应者的功能，甚至与响应相关的一些特殊指令。这些头部有助于客户端处理响应，并在将来发起更好的请求。响应头域包含Age、Location、Proxy-Authenticate、Public、Retry- After、Server、Vary、Warning、WWW-Authenticate。对响应头域的扩展要求通讯双方都支持，如果存在不支持的响应头域，一般将会作为实体头域处理。

  4、HTTP实体头
　　实体头部提供了有关实体及其内容的大量信息，从有关对象类型的信息，到能够对资源使用的各种有效的请求方法。总之，实体头部可以告知接收者它在对什么进行处理。请求消息和响应消息都可以包含实体信息，实体信息一般由实体头域和实体组成。实体头域包含关于实体的原信息，实体头包括信息性头部Allow、Location，内容头部Content-Base、Content-Encoding、Content-Language、Content-Length、Content-Location、Content-MD5、Content-Range、Content-Type，缓存头部Etag、Expires、Last-Modified、extension-header。



二、HTTP Request的Header信息
1、HTTP请求方式
GET
POST
PUT
HEAD
DELETE
CONNECT
TRACE
OPTIONS


2、HTTP Response的Header信息
Header	解释	示例
Accept-Ranges	表明服务器是否支持指定范围请求及哪种类型的分段请求	Accept-Ranges: bytes
Age	从原始服务器到代理缓存形成的估算时间（以秒计，非负）	Age: 12
Allow	对某网络资源的有效的请求行为，不允许则返回405	Allow: GET, HEAD
Cache-Control	告诉所有的缓存机制是否可以缓存及哪种类型	Cache-Control: no-cache
Content-Encoding	web服务器支持的返回内容压缩编码类型。	Content-Encoding: gzip
Content-Language	响应体的语言	Content-Language: en,zh
Content-Length	响应体的长度	Content-Length: 348
Content-Location	请求资源可替代的备用的另一地址	Content-Location: /index.htm
Content-MD5	返回资源的MD5校验值	Content-MD5: Q2hlY2sgSW50ZWdyaXR5IQ==
Content-Range	在整个返回体中本部分的字节位置	Content-Range: bytes 21010-47021/47022
Content-Type	返回内容的MIME类型	Content-Type: text/html; charset=utf-8
Date	原始服务器消息发出的时间	Date: Tue, 15 Nov 2010 08:12:31 GMT
ETag	请求变量的实体标签的当前值	ETag: “737060cd8c284d8af7ad3082f209582d”
Expires	响应过期的日期和时间	Expires: Thu, 01 Dec 2010 16:00:00 GMT
Last-Modified	请求资源的最后修改时间	Last-Modified: Tue, 15 Nov 2010 12:45:26 GMT
Location	用来重定向接收方到非请求URL的位置来完成请求或标识新的资源	Location: http://www.zcmhi.com/archives/94.html
Pragma	包括实现特定的指令，它可应用到响应链上的任何接收方	Pragma: no-cache
Proxy-Authenticate	它指出认证方案和可应用到代理的该URL上的参数	Proxy-Authenticate: Basic
refresh	应用于重定向或一个新的资源被创造，在5秒之后重定向（由网景提出，被大部分浏览器支持）	
Refresh: 5; url=
http://www.zcmhi.com/archives/94.html
Retry-After	如果实体暂时不可取，通知客户端在指定时间之后再次尝试	Retry-After: 120
Server	web服务器软件名称	Server: Apache/1.3.27 (Unix) (Red-Hat/Linux)
Set-Cookie	设置Http Cookie	Set-Cookie: UserID=JohnDoe; Max-Age=3600; Version=1
Trailer	指出头域在分块传输编码的尾部存在	Trailer: Max-Forwards
Transfer-Encoding	文件传输编码	Transfer-Encoding:chunked
Vary	告诉下游代理是使用缓存响应还是从原始服务器请求	Vary: *
Via	告知代理客户端响应是通过哪里发送的	Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)
Warning	警告实体可能存在的问题	Warning: 199 Miscellaneous warning
WWW-Authenticate	表明客户端请求实体应该使用的授权方案	WWW-Authenticate: Basic


3、http返回错误码
  　　HTTP响应码响应码由三位十进制数字组成，它们出现在由HTTP服务器发送的响应的第一行。响应码分五种类型，由它们的第一位数字表示：
  　　1xx：信息，请求收到，继续处理
  　　2xx：成功，行为被成功地接受、理解和采纳
  　　3xx：重定向，为了完成请求，必须进一步执行的动作
  　　4xx：客户端错误，请求包含语法错误或者请求无法实现
  　　5xx：服务器错误，服务器不能实现一种明显无效的请求





参考
https://blog.csdn.net/wangzhen_csdn/article/details/80776991
https://blog.csdn.net/alexshi5/article/details/80379086
https://www.cnblogs.com/yunlongaimeng/p/9802003.html




---------------------------------------------------------------------------------------------------------------------  
HTTP请求头via

via 值为: 下面是一些Demo
WTP/1.1 GDSZ-PS-GW010-WAP05.gd.test.com (Nokia WAP Gateway 4.0 CD3/ECD13_C/NWG4.0 CD3 ECD13_C 4.1.03)

Via：1.0 236-81.D07071953.sina.com.cn:80 (squid/2.6.STABLE13)
         1.0 squid76:80 (squid/2.6.STABLE22)


下面是解释

列出从客户端到 OCS 或者相反方向的响应经过了哪些代理服务器，他们用什么协议（和版本）发送的请求。
当客户端请求到达第一个代理服务器时，该服务器会在自己发出的请求里面添加 Via 头部，并填上自己的相关信息，
当下一个代理服务器 收到第一个代理服务器的请求时，会在自己发出的请求里面复制前一个代理服务器的请求的Via 头部，并把自己的相关信息加到后面， 
以此类推，当 OCS 收到最后一个代理服务器的请求时，检查 Via 头部，就知道该请求所经过的路由。
例如：Via：1.0 236-81.D07071953.sina.com.cn:80 (squid/2.6.STABLE13)

---------------------------------------------------------------------------------------------------------------------  



HTTP 请求头 Accept-Encoding 会将客户端能够理解的内容编码方式——通常是某种压缩算法——进行通知（给服务端）。通过内容协商的方式，服务端会选择一个客户端提议的方式，使用并在响应头 Content-Encoding 中通知客户端该选择。



[Accept-Encoding](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Accept-Encoding)  



