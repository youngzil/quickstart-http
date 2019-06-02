1、前端跨域问题（CORS）
跨域定义：协议、域名、端口等，跨域是客户端浏览器独有的
跨域限制：安全机制，限制的行为：Cookie、LocalStorage 和 IndexDB 无法读取、DOM 无法获得、AJAX 请求不能发送
跨域解决方案：CORS
CORS预检请求：OPTIONS方法，预检请求头和返回头

2、
3、






---------------------------------------------------------------------------------------------------------------------
https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Access_control_CORS
https://segmentfault.com/a/1190000006727486
http://www.ruanyifeng.com/blog/2016/04/cors.html
http://www.ruanyifeng.com/blog/2016/04/same-origin-policy.html
https://juejin.im/post/5c46af87e51d4552232feaeb

前端跨域问题（CORS）（Cross-Origin Resource Sharing）

主要是总结浏览器CORS跨域，其他的本人认为是伪跨域，如iframe、window.name、window.postMessage。

跨域定义
跨站 HTTP 请求(Cross-site HTTP request)是指发起请求的资源所在域不同于该请求所指向资源所在的域的 HTTP 请求。

跨域问题只有在浏览器才会出现，javascript等脚本的主动http请求才会出现跨域问题。后端获取http数据不会存在跨域问题。跨域问题可以说是浏览器独有的（或者说http客户端独有的，这个其实看制定者是否遵循协议）。

注意：有些浏览器不允许从HTTPS的域跨域访问HTTP，比如Chrome和Firefox，这些浏览器在请求还未发出的时候就会拦截请求，这是一个特例。

跨域资源共享(CORS) 是一种机制，它使用额外的 HTTP 头来告诉浏览器  让运行在一个 origin (domain) 上的Web应用被准许访问来自不同源服务器上的指定的资源。当一个资源从与该资源本身所在的服务器不同的域、协议或端口请求一个资源时，资源会发起一个跨域 HTTP 请求。


何为同源（同域）：如果两个页面的协议，端口（如果有指定）和域名都相同，则两个页面具有相同的源。
不同源就是跨域：不同协议 ( https和http )、不同端口 ( 81和80)、不同域名 ( news和store )



为什么要有跨域限制？安全机制
同源策略限制了从同一个源加载的文档或脚本如何与来自另一个源的资源进行交互。这是一个用于隔离潜在恶意文件的重要安全机制。
还是安全问题，如果不限制，那么CSRF（Cross-site request forgery，中文名称：跨站请求伪造）攻击就很容易实现了。


如果非同源，共有三种行为受到限制。
（1） Cookie、LocalStorage 和 IndexDB 无法读取。
（2） DOM 无法获得。
（3） AJAX 请求不能发送。


CORS预检请求
预请求就是使用OPTIONS方法。跨域请求首先需要发送预请求，即使用 OPTIONS   方法发起一个预请求到服务器，以获知服务器是否允许该实际请求。预请求的使用，可以避免跨域请求对服务器的用户数据产生未预期的影响。

跨域才会有预请求，但是不是所有跨域请求都会发送预请求的。 预请求服务器正常返回，浏览器还要判断是否合法，才会继续正常请求的。所以web服务程序需要针对options做处理，要不然OPTIONS的请求也会运行后端代码。一般预请求最好返回204(NO-Content)。

“需预检的请求”要求必须首先使用 OPTIONS   方法发起一个预检请求到服务器，以获知服务器是否允许该实际请求。"预检请求“的使用，可以避免跨域请求对服务器的用户数据产生未预期的影响。

在谷歌开发者工具上查看网络请求时，如果预请求是不在XHR这个分类中，可以在Other分类或者ALL中查看。



什么时候会有预请求？
一般服务器默认允许GET、POST、HEAD请求（前提跨域），所以这些请求，只要前端脚本不追加请求头，是不会有预请求发出的。这些请求叫简单请求。

可以简单总结为只有GET、POST、HEAD才可能没有预请求。

大多数浏览器不支持针对于预请求的重定向。如果一个预请求发生了重定向，浏览器将报告错误：

The request was redirected to 'https://example.com/foo', which is disallowed for cross-origin requests that require preflight



跨域解决方案
正如大家所知，出于安全考虑，浏览器会限制脚本中发起的跨站请求。但是为了能开发出更强大、更丰富、更安全的Web应用程序，开发人员渴望着在不丢失安全的前提下，Web 应用技术能越来越强大、越来越丰富。

Web 应用工作组( Web Applications Working Group )推荐了一种的机制，即跨源资源共享（Cross-Origin Resource Sharing (CORS)）。
CORS是跨源资源分享（Cross-Origin Resource Sharing）的缩写。它是W3C标准，是跨源AJAX请求的根本解决方法。

跨域资源共享标准新增了一组 HTTP 首部字段，允许服务器声明哪些源站有权限访问哪些资源。

跨域需要设置的HTTP首部字段
实现前后端跨域请求，需要设置下面相关的HTTP响应头:
字段名	必须设置与否   说明
Access-Control-Allow-Origin	是  默认不设置时不允许跨域，origin参数指定一个允许向该服务器提交请求的URI
Access-Control-Allow-Credentials	否  此字段是用来设置是否允许传cookie，默认为false
Access-Control-Allow-Methods	否  默认值一般为GET、HEAD、POST，所以请delete等方法的时候，默认会被限制，指明资源可以被请求的方式有哪些(一个或者多个)。这个响应头信息在客户端发出预检请求的时候会被返。这就看需要了。设置为*时，没有囊括全部方式，例如patch,所有还是设置为全部方式更保险。
Access-Control-Allow-Headers  浏览器自身附带的请求头默认是被允许的，但是前端代码追加的请求头，在跨域的时候是要被允许才可访问。而且浏览器本身默认自带请求头是不可修改的，如User-Agent、Origin等。
Access-Control-Max-Age	否   单位是秒，这个响应头告诉我们这次预请求的结果的有效期是多久，有效期期间内的请求都不用使用预请求。
一般只要设置好 Access-Control-Allow-Origin就可以跨域了，其他的字段都是配合使用的（其他字段有默认值）。



预检请求头信息：
Origin 首部字段表明预检请求或实际请求的源站。

Access-Control-Request-Method 首部字段用于预检请求。其作用是，将实际请求所使用的 HTTP 方法告诉服务器。
Access-Control-Request-Headers 首部字段用于预检请求。其作用是，将实际请求所携带的首部字段告诉服务器。



一次预检请求和返回：

Access-Control-Request-Headers: appid,operid,pagecode,sign,timestamp
Access-Control-Request-Method: POST
Origin: http://test.runtime.vortex.zj.chinamobile.com:8000
Referer:http://test.runtime.vortex.zj.chinamobile.com:8000/home


Date:Wed, 30 Nov 2011 02:13:21 GMT
Server:Jetty(7.5.4.v20111024)
Access-Control-Allow-Credentials: true
Access-Control-Allow-Headers: X-Requested-With,Content-Type,Accept,Origin,appId,appid,operId,operid,sign,timestamp,method,format,version,accessToken,accesstoken,openId,openid,busiSerial,busiserial,pageCode,pagecode
Access-Control-Allow-Methods: GET,POST,HEAD,PUT
Access-Control-Allow-Origin: http://test.runtime.vortex.zj.chinamobile.com:8000
Access-Control-Max-Age: 1800
Allow:POST,GET,OPTIONS,HEAD
Content-Length:59
Content-Type: application/vnd.sun.wadl+xml
Last-Modified: ???, 29 ?? 2019 11:51:17 CST



问题
1、Access-Control-Max-Age不起作用，客户端是否禁用缓存：Disable cache






---------------------------------------------------------------------------------------------------------------------




---------------------------------------------------------------------------------------------------------------------



