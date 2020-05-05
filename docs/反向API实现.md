
Reverse API（反向API实现）

“反向 API”家族：The House of “Reverse API”
这个家族的标志是电话，既是服务器调用客户端，而不能反过来调用。
实现的技术：
1、WebSocket技术
2、Comet技术
	1、长轮询（long polling）方法：
	    用JavaScript实现的轮询：HTTP轮询和JSONP轮询(可以跨域CORS请求)
	    捎带轮询（piggyback polling）
	2、流（streaming）方法：iframe流



实现反向Ajax通信的几种不同方式：轮询（polling）、捎带（piggyback）以及使用了长轮询（long-polling）和流（streaming）的Comet。
异步的JavaScript和XML（Asynchronous JavaScript and XML，Ajax），一种可通过JavaScript来访问的浏览器功能特性，其允许脚本向幕后的网站发送一个HTTP请求而又无需重新加载页面。



WebSocket：WebSocket 是一种传输协议，它通过一个 TCP 套接字连接在服务器和客户端之间建立一个持久的双向通信通道。当服务器到客户端或客户端到服务器需要近实时交换时，WebSocket 是一个不错的选择。这克服了 HTTP 请求 / 响应机制的限制（例如客户端轮询及相关的开销）。WebSocket 适用于需要实时更新的应用程序，因为服务器可以在连接上随时推送数据。所有现代浏览器都支持 WebSocket。
在HTML5标准中，定义了客户端和服务器通讯的WebSocket方式，在得到浏览器支持以后，WebSocket将会取代Comet成为服务器推送的方法，目前只有chrome默认支持，Firefox等浏览器因为安全性的考虑默认是关闭的。
所有浏览器都已经支持

Comet技术
Comet是一种用于web的技术，能使服务器能实时地将更新的信息传送到客户端，而无须客户端发出请求，目前有两种实现方式，长轮询和流。

长轮询是在打开一条连接以后保持，等待服务器推送来数据再关闭的方式。
iframe流：流方式是在页面中插入一个隐藏的iframe，利用其src属性在服务器和客户端之间建立一条长链接，服务器向iframe传输数据（通常是HTML，内有负责插入信息的javascript），来实时更新页面。



使用反向Ajax（Reverse Ajax）技术来开发事件驱动的web应用，以此来实现更好的用户体验。
使用了流（streaming）方法和长轮询（long polling）方法的Comet。
反向Ajax、轮询（polling）、流（streaming）、Comet和长轮询（long polling）
实现反向Ajax通信的几种不同方式：轮询（polling）、捎带（piggyback）以及使用了长轮询（long-polling）和流（streaming）的Comet。


反向Ajax（Reverse Ajax）本质上则是这样的一种概念：能够从服务器端向客户端发送数据。在一个标准的HTTP Ajax请求中，数据是发送给服务器端的，反向Ajax可以某些特定的方式来模拟发出一个Ajax请求，这些方式本文都会论及，这样的话，服务器就可以尽可能快地向客户端发送事件（低延迟通信）。

WebSocket技术来自HTML5，是一种最近才出现的技术，许多浏览器已经支持它（Firefox、Google Chrome、Safari等等）。WebSocket启用双向的、全双工的通信信道，其通过某种被称为WebSocket握手的HTTP请求来打开连接，并用到了一些特殊的报头。连接保持在活动状态，你可以用JavaScript来写和接收数据，就像是正在用一个原始的TCP套接口一样。

反向Ajax的目的是允许服务器端向客户端推送信息。Ajax请求在缺省情况下是无状态的，且只能从客户端向服务器端发出请求。你可以通过使用技术模拟服务器端和客户端之间的响应式通信来绕过这一限制。

HTTP轮询和JSONP轮询

轮询（polling）涉及了从客户端向服务器端发出请求以获取一些数据，这显然就是一个纯粹的Ajax HTTP请求。为了尽快地获得服务器端事件，轮询的间隔（两次请求相隔的时间）必须尽可能地小。但有这样的一个缺点存在：如果间隔减小的话，客户端浏览器就会发出更多的请求，这些请求中的许多都不会返回任何有用的数据，而这将会白白地浪费掉带宽和处理资源。

图1中的时间线说明了客户端发出了某些轮询请求，但没有信息返回这种情况，客户端必须要等到下一个轮询来获取两个服务器端接收到的事件。

JSONP轮询基本上与HTTP轮询一样，不同之处则是JSONP可以发出跨域请求（不是在你的域内的请求）。清单1使用JSONP来通过邮政编码获取地名，JSONP请求通常可通过它的回调参数和返回内容识别出来，这些内容是可执行的JavaScript代码


用JavaScript实现的轮询的优点和缺点：
　　1. 优点：很容易实现，不需要任何服务器端的特定功能，且在所有的浏览器上都能工作。
　　2. 缺点：这种方法很少被用到，因为它是完全不具伸缩性的。试想一下，在100个客户端每个都发出2秒钟的轮询请求的情况下，所损失的带宽和资源数量，在这种情况下30%的请求没有返回数据。

Piggyback
捎带轮询（piggyback polling）是一种比轮询更加聪明的做法，因为它会删除掉所有非必需的请求（没有返回数据的那些）。不存在时间间隔，客户端在需要的时候向服务器端发送请求。不同之处在于响应的那部分上，响应被分成两个部分：对请求数据的响应和对服务器事件的响应，如果任何一部分有发生的话。

在实现piggyback技术时，通常针对服务器端的所有Ajax请求可能会返回一个混合的响应

这种方法也有着一些优点和缺点：
　　1. 优点：没有不返回数据的请求，因为客户端对何时发送请求做了控制，对资源的消耗较少。该方法也是可用在所有的浏览器上，不需要服务器端的特殊功能。
　　2. 缺点：当累积在服务器端的事件需要传送给客户端时，你却一点都不知道，因为这需要一个客户端行为来请求它们。



Comet
　　使用了轮询或是捎带的反向Ajax非常受限：其不具伸缩性，不提供低延迟通信（只要事件一到达服务器端，它们就以尽可能快的速度到达浏览器端）。Comet是一个web应用模型，在该模型中，请求被发送到服务器端并保持一个很长的存活期，直到超时或是有服务器端事件发生。在该请求完成后，另一个长生存期的Ajax请求就被送去等待另一个服务器端事件。使用Comet的话，web服务器就可以在无需显式请求的情况下向客户端发送数据。

　　Comet的一大优点是，每个客户端始终都有一个向服务器端打开的通信链路。服务器端可以通过在事件到来时立即提交（完成）响应来把事件推给客户端，或者它甚至可以累积再连续发送。因为请求长时间保持打开的状态，故服务器端需要特别的功能来处理所有的这些长生存期请求。

　　Comet的实现可以分成两类：使用流（streaming）的那些和使用长轮询（long polling）的那些。


使用HTTP流的Comet
　　在流（streaming）模式中，有一个持久连接会被打开。只会存在一个长生存期请求（图3中的#1），因为每个到达服务器端的事件都会通过这同一连接来发送。因此，客户端需要有一种方法来把通过这同一连接发送过来的不同响应分隔开来。从技术上来讲，两种常见的流技术包括Forever Iframe（隐藏的IFrame），或是被用来在JavaScript中创建Ajax请求的XMLHttpRequest对象的多部分（multi-part）特性。

Forever Iframe（永存的Iframe）技术涉及了一个置于页面中的隐藏Iframe标签，该标签的src属性指向返回服务器端事件的servlet路径。每次在事件到达时，servlet写入并刷新一个新的script标签，该标签内部带有JavaScript代码，iframe的内容被附加上这一script标签，标签中的内容就会得到执行。

　　1. 优点：实现简单，在所有支持iframe的浏览器上都可用。
　　2. 缺点： 没有方法可用来实现可靠的错误处理或是跟踪连接的状态，因为所有的连接和数据都是由浏览器通过HTML标签来处理的，因此你没有办法知道连接何时在哪一端已被断开了。


Multi-part XMLHttpRequest
　　第二种技术，更可靠一些，是XMLHttpRequest对象上使用某些浏览器（比如说Firefox）支持的multi-part标志。Ajax请求被发送给服务器端并保持打开状态，每次有事件到来时，一个多部分的响应就会通过这同一连接来写入
做法存在着一些优点和缺点：
　　1. 优点：只打开了一个持久连接，这就是节省了大部分带宽使用率的Comet技术。
　　2. 缺点：并非所有的浏览器都支持multi-part标志。某些被广泛使用的库，比如说用Java实现的CometD，被报告在缓冲方面有问题。例如，一些数据块（多个部分）可能被缓冲，然后只有在连接完成或是缓冲区已满时才被发送，而这有可能会带来比预期要高的延迟。


使用HTTP长轮询的Comet
　　长轮询（long polling）模式涉及了打开连接的技术。连接由服务器端保持着打开的状态，只要一有事件发生，响应就会被提交，然后连接关闭。接下来。一个新的长轮询连接就会被正在等待新事件到达的客户端重新打开。
　　你可以使用script标签或是单纯的XMLHttpRequest对象来实现HTTP长轮询。


因为所有现代的浏览器都支持跨域资源共享（Cross-Origin Resource Share，CORS）规范，该规范允许XHR执行跨域请求，因此基于脚本的和基于iframe的技术已成为了一种过时的需要。

　　把Comet做为反向Ajax的实现和使用的最好方式是通过XMLHttpRequest对象，该做法提供了一个真正的连接句柄和错误处理。考虑到不是所有的浏览器都支持multi-part标志，且多部分流可能会遇到缓冲问题，因此建议你选择经由HTTP长轮询使用XMLHttpRequest对象（在服务器端挂起的一个简单的Ajax请求）的Comet模式，所有支持Ajax的浏览器也都支持该种做法。



Socket.IO
　　Socket.IO是一个JavaScript客户端库，类似于WebSocket，其提供了单个的API来连接远程服务器，异步地发送和接收消息。通过提供一个通用的API，Socket.IO支持如下多种传输：WebSocket、Flash Socket、长轮询、流、永久Iframe和JSONP轮询。Socket.IO检测浏览器的功能，然后尝试选择可用的最好传输方式。Socket.IO库几乎兼容所有的浏览器（包括较旧的那些，比如IE 5.5）和移动浏览器，它还拥有其他的一些功能，比如说心跳、超时、断开连接和错误处理等。

　　Socket.IO网站（参见参考资料）详细描述了该库的使用方式，以及其所用到的浏览器和反向Ajax技术。基本情况是这样，Socket.IO用到了一个通信协议，该协议使得客户端的库能够与服务器端的端点进行通信，服务器端的端点能够理解Socket.IO协议。Socket.IO一开始是为Node JS这一用来构建更快的服务器的JavaScript引擎开发的，许多项目带来了对其他语言的支持，其中就包括了Java语言。



Atmosphere框架
　　Atmosphere是一个Java技术框架，其提供了通用的API来使用许多web服务器的Comet和WebSocket，这些web服务器包括了Tomcat、Jetty、GlassFish、Weblogic、Grizzly、JBossWeb、JBoss和Resin，其还支持任何支持Servlet 3.0规范的web服务器。



CometD框架
　　CometD框架是一个已经存在了好几年的基于HTTP的事件驱动的通信解决方案，其版本2增加了对注解配置和WebSocket的支持。CometD框架提供了一个Java服务器端的部分和一个Java客户端的部分，以及基于JQuery和Dojo的JavaScript客户端库。CometD使用了一个被称作Bayeux的标准的通信协议，允许你激活消息确认、流程控制、同步以及集群等的某些扩展。



参考
https://blog.csdn.net/shanliangliuxing/article/details/7755300
https://kb.cnblogs.com/page/112185/
https://kb.cnblogs.com/page/112616/
https://kb.cnblogs.com/page/114594/
https://kb.cnblogs.com/page/116653/





