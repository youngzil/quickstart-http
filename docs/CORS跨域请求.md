1、前端跨域问题（CORS）
跨域定义：协议、域名、端口等，跨域是客户端浏览器独有的
跨域限制：安全机制，限制的行为：Cookie、LocalStorage 和 IndexDB 无法读取、DOM 无法获得、AJAX 请求不能发送
跨域解决方案：CORS
CORS预检请求：OPTIONS方法，预检请求头和返回头

2、跨域Cookie的解决方案

3、






---------------------------------------------------------------------------------------------------------------------
https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Access_control_CORS
https://segmentfault.com/a/1190000006727486
http://www.ruanyifeng.com/blog/2016/04/cors.html
http://www.ruanyifeng.com/blog/2016/04/same-origin-policy.html
https://juejin.im/post/5c46af87e51d4552232feaeb
https://my.oschina.net/wsxiao/blog/1648996
https://www.smi1e.top/cross-origin-resource-sharing%EF%BC%88%E8%B7%A8%E5%9F%9F%E8%B5%84%E6%BA%90%E5%85%B1%E4%BA%AB%EF%BC%89/
https://blog.csdn.net/qq_31617637/article/details/72955239


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


浏览器将CORS请求分成两类：简单请求（simple request）和非简单请求（not-so-simple request）。
* 简单请求就是使用设定的请求方式请求数据
* 而非简单请求则是在使用设定的请求方式请求数据之前,先发送一个OPTIONS请求,看服务端是否允许客户端发送非简单请求。只有"预检"通过后才会再发送一次请求用于数据传输


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


CORS(Cross-Origin Resource Sharing, 跨源资源共享)是W3C出的一个标准，其思想是使用自定义的HTTP头部让浏览器与服务器进行沟通，从而决定请求或响应是应该成功，还是应该失败。

Access-Control-Allow-Origin: 允许跨域访问的域，可以是一个域的列表，也可以是通配符"*"。
注意Origin规则只对域名有效，并不会对子目录有效。不同子域名需要分开设置。
Access-Control-Allow-Credentials: 是否允许请求带有验证信息，这部分将会在下面详细解释
Access-Control-Expose-Headers: 允许脚本访问的返回头，请求成功后，脚本可以在XMLHttpRequest中访问这些头的信息(貌似webkit没有实现这个)
Access-Control-Max-Age: 缓存此次请求的秒数。在这个时间范围内，所有同类型的请求都将不再发送预检请求而是直接使用此次返回的头作为判断依据，非常有用，大幅优化请求次数
Access-Control-Allow-Methods: 允许使用的请求方法，以逗号隔开
Access-Control-Allow-Headers: 允许自定义的头部，以逗号隔开，大小写不敏感



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


Date: Sat, 12 Oct 2019 09:38:57 GMT
access-control-allow-origin: http://test.runtime.vortex.zj.chinamobile.com:8000
vary: origin
access-control-allow-methods: HEAD,POST,GET,PUT
access-control-allow-headers: "X-Requested-With,Content-Type,Accept,Origin,appId,appid,operId,operid,sign,timestamp,method,format,version,accessToken,accesstoken,openId,openid,busiSerial,busiserial,pageCode,pagecode"
access-control-allow-credentials: true
access-control-max-age: 86400
Content-Length: 0
Connection: Keep-alive
Via: 1.1 ID-5003323700051215 uproxy-2


问题
1、Access-Control-Max-Age不起作用，客户端是否禁用缓存：Disable cache
2、access-control-allow-headers设置成*不是匹配全部的意思，跨域请求头设置*无效




---------------------------------------------------------------------------------------------------------------------

CRSF跨站请求伪造
CSRF（Cross-site request forgery）跨站请求伪造，也被称为“One Click Attack”或者Session Riding，通常缩写为CSRF或者XSRF，是一种对网站的恶意利用。
CSRF是一种夹持用户在已经登陆的web应用程序上执行非本意的操作的攻击方式。相比于XSS，CSRF是利用了系统对页面浏览器的信任，XSS则利用了系统对用户的信任。


尽管听起来像跨站脚本（XSS），但它与XSS非常不同，XSS利用站点内的信任用户，而CSRF则通过伪装来自受信任用户的请求来利用受信任的网站。

XSS是跨站脚本攻击(Cross Site Scripting)，恶意攻击者往Web页面里插入恶意Script代码，当用户浏览该页之时，嵌入其中Web里面的Script代码会被执行，从而达到恶意攻击用户的目的。

CSRF的全称是“跨站请求伪造”，而 XSS 的全称是“跨站脚本”。看起来有点相似，它们都是属于跨站攻击——不攻击服务器端而攻击正常访问网站的用户，但它们的攻击类型是不同维度上的分类。CSRF 顾名思义，是伪造请求，冒充用户在站内的正常操作。


XSS中的过滤是在前端怎么做？
前端防xss分两类：1是提交数据的时候, 2是渲染数据的时候。

提交数据, 即post表单, 或者ajax提交数据的时候, 对用户输入的内容进行过滤, 当前由于是前端操作, 随便找个懂点的都可以通过模拟请求绕过, 但是做还是要做。

渲染数据, 这个是重点, 哪怕提交数据时, 被绕过(后端也没有处理), 渲染时予以过滤, 也能达到效果, 这里一般指ajax+template, 或者各种mvvm框架, 对于是用户提供的内容, 能用text方法的, 一律用text方法, 一定要用html方法的, 则进行数据过滤。

参考
https://www.jianshu.com/p/d5423f930c73
https://chuenwei.github.io/2016/08/02/xss-crsf/

---------------------------------------------------------------------------------------------------------------------
cookie跨域共享参考

https://www.cnblogs.com/1020182600HENG/p/7121148.html
http://byteliu.com/2019/04/11/%E8%B7%A8%E5%9F%9F%E8%B5%84%E6%BA%90%E5%85%B1%E4%BA%AB-CORS-%E7%9B%B8%E5%85%B3%E9%97%AE%E9%A2%98/


Jquery的jsonp方式请求
https://blog.csdn.net/hwhsong/article/details/84070918
https://www.cnblogs.com/feiyuanxing/p/4575888.html
https://www.cnblogs.com/chiangchou/p/jsonp.html



跨域Cookie的解决方案：同Domain 和 不同Domain
1、反向代理Nginx
2、Jquery的jsonp方式请求：需要自己写脚本发起请求，然后写个回调函数处理数据，JQuery对于Ajax的跨域请求有两类解决方案，不过都是只支持get方式。分别是JQuery的 jquery.ajax jsonp格式和jquery.getScript方式。 
3、CROS：前端会有预检请求，后端要有CORSFilter
前端发起ajax的时候，设置withCredentials:true，让浏览器发送请求的时候带着cookie
后端接受请求的时候，设置
// 让服务器接受cookie
header(“Access-Control-Allow-Credentials:true”);
//可接受的源里面包含要发过来cookie的源。
header(“Access-Control-Allow-Origin: http://account.tcs-y.com");



---------------------------------------------------------------------------------------------------------------------



