WebSocket技术

WebSocket protocol 是HTML5一种新的协议。





WebSocket protocol 是HTML5一种新的协议。它实现了浏览器与服务器全双工通信(full-duplex)。一开始的握手需要借助HTTP请求完成。
目的：即时通讯，替代轮询
WebSocket将会取代Comet成为服务器推送的方法

在WebSocket中，只需要服务器和浏览器通过HTTP协议进行一个握手的动作，然后单独建立一条TCP的通信通道进行数据的传送。
Websocket是HTML5新增的一种全双工通信协议，客户端和服务端基于TCP握手连接成功后，两者之间就可以建立持久性的连接，实现双向数据传输。

原理：WebSocket同HTTP一样也是应用层的协议，但是它是一种双向通信协议，是建立在TCP之上的。

连接过程 —— 握手过程
1. 浏览器、服务器建立TCP连接，三次握手。这是通信的基础，传输控制层，若失败后续都不执行。
2. TCP连接成功后，浏览器通过HTTP协议向服务器传送WebSocket支持的版本号等信息。（开始前的HTTP握手）
3. 服务器收到客户端的握手请求后，同样采用HTTP协议回馈数据。
4. 当收到了连接成功的消息后，通过TCP通道进行传输通信。


WebSocket协议是html5的一种通信协议，该协议兼容我们常用的浏览器。例如Chrome、 Firefox、IE等。
它可以使客户端和服务端双向数据传输更加简单快捷，并且在TCP连接进行一次握手后，就可以持久性连接，同时允许服务端对客户端推送数据。
外加传统模式的协议一般HTTP请求可能会包含较长的头部，但真正有效的可能只有小部分，从而就占用了很多资源和带宽。因此WebSocket协议不仅可以实时通讯，支持扩展；也可以压缩节省服务器资源和带宽。 
WS协议和WSS协议两个均是WebSocket协议的SCHEM，两者一个是非安全的，一个是安全的。也是统一的资源标志符。就好比HTTP协议和HTTPS协议的差别。
非安全的没有证书，安全的需要SSL证书。（SSL是Netscape所研发，用来保障网络中数据传输的安全性，主要是运用数据加密的技术，能够避免数据在传输过程被不被窃取或者监听。）其中WSS表示在TLS之上的WebSocket。
WS一般默认是80端口，而WSS默认是443端口，大多数网站用的就是80和433端口。（在高防防护过程中，80和433端口的网站是需要备案才可以接入国内的。）当然网站也会有别的端口，这种如果做高防是方案是可以用海外高防的。WS和WSS的体现形式分别是TCP+WS AS WS ，TCP+TLS+WS AS WS。服务器网址就是 URL。

最后墨者安全再说下WebSocket协议的特点：
1、建立在 TCP 协议之上，服务端实现容易；
2、与 HTTP 协议有良好的兼容性，握手时不容易被屏蔽，可以通过各种 HTTP 代理服务器；
3、数据轻量，实时通讯；
4、可以发送文本和二进制数据。
5、不限制同源，客户端可以与任意服务器端进行通讯。

因此WebSocket协议的出现，为很多人解决了关于扩展以及兼容性协议的烦恼问题。



webSocket.readyState
readyState属性返回实例对象的当前状态，共有四种。
CONNECTING：值为0，表示正在连接。
OPEN：值为1，表示连接成功，可以通信了。
CLOSING：值为2，表示连接正在关闭。
CLOSED：值为3，表示连接已经关闭，或者打开连接失败。

WebSocket 事件
以下是 WebSocket 对象的相关事件。假定我们使用了以上代码创建了 Socket 对象：
事件	事件处理程序	描述
open	Socket.onopen	连接建立时触发
message	Socket.onmessage	客户端接收服务端数据时触发
error	Socket.onerror	通信发生错误时触发
close	Socket.onclose	连接关闭时触发


WebSocket 方法
以下是 WebSocket 对象的相关方法。假定我们使用了以上代码创建了 Socket 对象：
方法	描述
Socket.send()	 使用连接发送数据
Socket.close()	 关闭连接




WebSocket与HTTP的关系
相同点
1. 都是一样基于TCP的，都是可靠性传输协议。
2. 都是应用层协议。
不同点
1. WebSocket是双向通信协议，模拟Socket协议，可以双向发送或接受信息。HTTP是单向的。
2. WebSocket是需要握手进行建立连接的。

Websocket是HTML5新增的一种全双工通信协议，客户端和服务端基于TCP握手连接成功后，两者之间就可以建立持久性的连接，实现双向数据传输。

联系
WebSocket在建立握手时，数据是通过HTTP传输的。但是建立之后，在真正传输时候是不需要HTTP协议的。



WebSocket与Socket的关系
Socket其实并不是一个协议，而是为了方便使用TCP或UDP而抽象出来的一层，是位于应用层和传输控制层之间的一组接口。

Socket是应用层与TCP/IP协议族通信的中间软件抽象层，它是一组接口。在设计模式中，Socket其实就是一个门面模式，它把复杂的TCP/IP协议族隐藏在Socket接口后面，对用户来说，一组简单的接口就是全部，让Socket去组织数据，以符合指定的协议。

当两台主机通信时，必须通过Socket连接，Socket则利用TCP/IP协议建立TCP连接。TCP连接则更依靠于底层的IP协议，IP协议的连接则依赖于链路层等更低层次。

WebSocket则是一个典型的应用层协议。

区别
Socket是传输控制层协议，WebSocket是应用层协议。



HTML5与WebSocket的关系
WebSocket API 是 HTML5 标准的一部分， 但这并不代表 WebSocket 一定要用在 HTML 中，或者只能在基于浏览器的应用程序中使用。

实际上，许多语言、框架和服务器都提供了 WebSocket 支持，例如：
* 基于 C 的 libwebsocket.org
* 基于 Node.js 的 Socket.io
* 基于 Python 的 ws4py
* 基于 C++ 的 WebSocket++
* Apache 对 WebSocket 的支持： Apache Module mod_proxy_wstunnel
* Nginx 对 WebSockets 的支持： NGINX as a WebSockets Proxy 、 NGINX Announces Support for WebSocket Protocol 、WebSocket proxying
* lighttpd 对 WebSocket 的支持：mod_websocket




参考
http://www.ruanyifeng.com/blog/2017/05/websocket.html
https://www.cnblogs.com/Javi/p/9303020.html
https://www.jianshu.com/p/970dcfd174dc
https://www.cnblogs.com/jiangzhaowei/p/8781635.html
https://www.runoob.com/html/html5-websocket.html

