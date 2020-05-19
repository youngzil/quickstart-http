HttpPOST提交数据的四种方式解析.md


HTTP 协议是以 ASCII 码传输，建立在 TCP/IP 协议之上的应用层规范。
HTTP 协议规定的 HTTP 请求方法有 OPTIONS、GET、HEAD、POST、PUT、DELETE、TRACE、CONNECT 这几种。
其中 POST 一般用来向服务端提交数据，
协议规定 POST 提交的数据必须放在消息主体（entity-body）中，但协议并没有规定数据必须使用什么编码方式。但是，数据发送出去，还要服务端解析成功才有意义。

服务端通常是根据请求头（headers）中的 Content-Type 字段来获知请求中的消息主体是用何种方式编码，再对主体进行解析。

POST 提交数据方案，包含了 Content-Type 和消息主体编码方式两部分

Content-Type类型：
application/x-www-form-urlencoded （默认常用的）
multipart/form-data
application/json
text/xml（现在几乎不用）




application/x-www-form-urlencoded （默认常用的）

这应该是最常见的 POST 提交数据的方式了。浏览器的原生 <form> 表单，如果不设置 enctype
属性，那么最终就会以 application/x-www-form-urlencoded 方式提交数据。



multipart/form-data
这又是一个常见的 POST 数据提交的方式。我们使用表单上传文件时，必须让 <form> 表单的 enctyped
等于 multipart/form-data。


application/json
application/json 这种方案，可以方便的提交复杂的结构化数据， 这个 Content-Type 作为响应头大家肯定不陌生。现在越来越多的人把它作为请求头，用来告诉服务端消息主体是序列化后的 JSON 字符串。


text/xml
现在几乎不用

默认情况下，标准的跨域请求是不会发送cookie等用户认证凭据的，XMLHttpRequest 2的一个重要改进就是提供了对授信请求访问的支持。




参考
https://www.jianshu.com/p/e47abb91465d








在服务器端判断request来自Ajax请求(异步)还是传统请求(同步)：x-requested-with请求头  XMLHttpRequest（XHR）
根据x-requested-with 请求头 区分ajax请求还是普通请求

在服务器端判断request来自Ajax请求(异步)还是传统请求(同步)：
　　两种请求在请求的Header不同，Ajax 异步请求比传统的同步请求多了一个头参数
x-requested-with  XMLHttpRequest  //表明是AJax异步

　　可以看到 Ajax 请求多了个 x-requested-with ，可以利用它，request.getHeader(“x-requested-with”); 为 null，则为传统同步请求，为 XMLHttpRequest，则为 Ajax 异步请求。


可以用来判断客户端的请求是Ajax请求还是其他请求。。
若 req.headers['x-requested-with'].toLowerCase() == 'xmlhttprequest' 则为ajax请求。

这个参数其实是框架自己加的
对原生JavaScript利用XMLHttpRequest发生请求，并没有这个参数
如果你需要在后端区分Ajax提交，那就务必确认你所使用的前端框架设置了X-Request-With这个头信息，如果是写的原生JavaScript的话，那就一定要设置xhr.setRequestHeader('X-Request-With', 'XMLHttpRequest')


XMLHttpRequest
Ajax实现最重要的一个依赖就是：XMLHttpRequest，一个web提供的API对象。


总结
所以说，ajax或者说XMLHttpRequest发送的一次http请求，和普通的请求根本没有任何区别，对http协议来说，做的都是一样的事情；如果要说一点区别那可能就是X-Request-With这个头信息吧。如果没有这个头信息，除了浏览器谁也不知道这是一次ajax请求！

当然，正是由于XMLHttpRequest是web API，浏览器提供的一个接口，让我们在不刷新页面的前提下发送一次http请求，但是为了安全防止伪造，特别是在同源策略下，浏览器对XMLHttpRequest的请求做了一些限制，我们可以修改某些header头信息，但是有部分头信息是不允许修改的，浏览器会自动添加：（关于这些头信息的意义大家请仔细学习http）



http://wxb.github.io/2017/03/08/%E6%9C%89%E6%97%B6%E5%80%99%E6%9C%8D%E5%8A%A1%E7%AB%AF%E7%9C%9F%E7%9A%84%E6%B2%A1%E6%B3%95%E7%A1%AE%E5%AE%9A%E8%BF%99%E6%98%AF%E4%B8%80%E6%AC%A1Ajax%E8%AF%B7%E6%B1%82.html





