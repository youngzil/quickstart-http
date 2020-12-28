- [前端跨域问题（CORS）](#前端跨域问题（CORS）)
    - [浏览器同源策略与跨域请求](#浏览器同源策略与跨域请求)
    跨域定义：协议、域名、端口等，跨域是客户端浏览器独有的                
    何为同源（同域）：如果两个页面的协议，端口（如果有指定）和域名都相同，则两个页面具有相同的源。                
    不同源就是跨域：不同协议 ( https和http )、不同端口 ( 81和80)、不同域名 ( news和store )
    跨域限制：安全机制，限制的行为：Cookie、LocalStorage 和 IndexDB 无法读取、DOM 无法获得、AJAX 请求不能发送                
    浏览器将CORS请求分成两类：简单请求（simple request）和非简单请求（not-so-simple request）。                
    跨域解决方案：CORS                
    CORS预检请求：OPTIONS方法，预检请求头和返回头
    - [一次预检请求和返回](#一次预检请求和返回)
- [Cookie跨域共享参考](#Cookie跨域共享参考)
    跨域Cookie的解决方案：同Domain 和 不同Domain
    同Domain
        直接写在父Domain中
    不同Domain
        1、反向代理Nginx
        2、Jquery的jsonp方式请求
        3、CROS：前端会有预检请求，后端要有CORSFilter
- [CRSF跨站请求伪造和XSS攻击](#CRSF跨站请求伪造和XSS攻击)
    - [CRSF跨站请求伪造](#CRSF跨站请求伪造) : CSRF（Cross-site request forgery）跨站请求伪造：利用了系统对页面浏览器的信任（通过cookie伪装）
    - [XSS攻击原理及防御措施](#XSS攻击原理及防御措施) : XSS：Cross-Site Scripting（跨站脚本攻击）：XSS利用站点内的信任用户（伪装已经认证的用户）
    - [Click劫持](#Click劫持) : 利用了HTML中<iframe>标签的透明属性
- [如何使用SpringSecurity防御CSRF攻击](#如何使用SpringSecurity防御CSRF攻击)
- [CC攻击与DDOS攻击定义](#CC攻击与DDOS攻击定义)




---------------------------------------------------------------------------------------------------------------------

## 前端跨域问题（CORS）


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
                

### 浏览器同源策略与跨域请求
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
                
                
### CORS预检请求

预请求就是使用OPTIONS方法。跨域请求首先需要发送预请求，即使用 OPTIONS   方法发起一个预请求到服务器，以获知服务器是否允许该实际请求。预请求的使用，可以避免跨域请求对服务器的用户数据产生未预期的影响。                
                
跨域才会有预请求，但是不是所有跨域请求都会发送预请求的。 预请求服务器正常返回，浏览器还要判断是否合法，才会继续正常请求的。所以web服务程序需要针对options做处理，要不然OPTIONS的请求也会运行后端代码。一般预请求最好返回204(NO-Content)。                
                
“需预检的请求”要求必须首先使用 OPTIONS   方法发起一个预检请求到服务器，以获知服务器是否允许该实际请求。"预检请求“的使用，可以避免跨域请求对服务器的用户数据产生未预期的影响。                
                
在谷歌开发者工具上查看网络请求时，如果预请求是不在XHR这个分类中，可以在Other分类或者ALL中查看。                
                
                
                
什么时候会有预请求？                
一般服务器默认允许GET、POST、HEAD请求（前提跨域），所以这些请求，只要前端脚本不追加请求头，是不会有预请求发出的。这些请求叫简单请求。                
                
可以简单总结为只有GET、POST、HEAD才可能没有预请求。                
                
大多数浏览器不支持针对于预请求的重定向。如果一个预请求发生了重定向，浏览器将报告错误：                
                
The request was redirected to 'https://example.com/foo', which is disallowed for cross-origin requests that require preflight                
                
                
                
### 跨域解决方案
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



### 一次预检请求和返回

Access-Control-Request-Headers: appid,operid,pagecode,sign,timestamp                
Access-Control-Request-Method: POST                
Origin: http://test.runtime.vortex.zj.test.com:8000                
Referer:http://test.runtime.vortex.zj.test.com:8000/home                
                
                
Date:Wed, 30 Nov 2011 02:13:21 GMT                
Server:Jetty(7.5.4.v20111024)                
Access-Control-Allow-Credentials: true                
Access-Control-Allow-Headers: X-Requested-With,Content-Type,Accept,Origin,appId,appid,operId,operid,sign,timestamp,method,format,version,accessToken,accesstoken,openId,openid,busiSerial,busiserial,pageCode,pagecode                
Access-Control-Allow-Methods: GET,POST,HEAD,PUT                
Access-Control-Allow-Origin: http://test.runtime.vortex.zj.test.com:8000                
Access-Control-Max-Age: 1800                
Allow:POST,GET,OPTIONS,HEAD                
Content-Length:59                
Content-Type: application/vnd.sun.wadl+xml                
Last-Modified: ???, 29 ?? 2019 11:51:17 CST                
                
                
Date: Sat, 12 Oct 2019 09:38:57 GMT                
access-control-allow-origin: http://test.runtime.vortex.zj.test.com:8000                
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

## CRSF跨站请求伪造和XSS攻击

CSRF（Cross-site request forgery）跨站请求伪造：利用了系统对页面浏览器的信任（通过cookie伪装）              
XSS：Cross-Site Scripting（跨站脚本攻击）：XSS利用站点内的信任用户（伪装已经认证的用户）              
Click劫持：利用了HTML中<iframe>标签的透明属性              


[如何使用SpringSecurity防御CSRF攻击](#如何使用SpringSecurity防御CSRF攻击)



### CRSF跨站请求伪造

构成CSRF攻击是有条件的：              
1、客户端必须一个网站并生成cookie凭证存储在浏览器中              
2、该cookie没有清除，客户端又tab一个页面进行访问别的网站              
              
抵御CSRF攻击的关键在于：在请求中放入攻击者所不能伪造的信息，并且该信息不存在于Cookie之中。              
目前防御 CSRF 攻击主要有三种策略：              
1、验证 HTTP Referer 字段；              
2、在请求地址中添加 token 并验证；              
3、在 HTTP 头中自定义属性并验证。              
              


CSRF（Cross-site request forgery）跨站请求伪造，也被称为One Click Attack或者Session Riding，通常缩写为CSRF或XSRF，是一种对网站的恶意利用。
尽管听起来像跨站脚本（XSS），但它与XSS非常不同，XSS利用站点内的信任用户，而CSRF则通过伪装成受信任用户的请求来利用受信任的网站。
与XSS攻击相比，CSRF攻击往往不大流行（因此对其进行防范的资源也相当稀少）和难以防范，所以被认为比XSS更具危险性。 
CSRF是一种依赖web浏览器的、被混淆过的代理人攻击（deputy attack）。



CSRF是一种夹持用户在已经登陆的web应用程序上执行非本意的操作的攻击方式。相比于XSS，CSRF是利用了系统对页面浏览器的信任，XSS则利用了系统对用户的信任。                

XSS是跨站脚本攻击(Cross Site Scripting)，恶意攻击者往Web页面里插入恶意Script代码，当用户浏览该页之时，嵌入其中Web里面的Script代码会被执行，从而达到恶意攻击用户的目的。                
                
CSRF的全称是“跨站请求伪造”，而 XSS 的全称是“跨站脚本”。看起来有点相似，它们都是属于跨站攻击——不攻击服务器端而攻击正常访问网站的用户，但它们的攻击类型是不同维度上的分类。CSRF 顾名思义，是伪造请求，冒充用户在站内的正常操作。                



CRSF参考                
https://www.jianshu.com/p/d5423f930c73  
https://chuenwei.github.io/2016/08/02/xss-crsf/  
https://www.cnblogs.com/dalianpai/p/12393133.html  
https://zh.wikipedia.org/wiki/%E8%B7%A8%E7%AB%99%E8%AF%B7%E6%B1%82%E4%BC%AA%E9%80%A0  




### XSS攻击原理及防御措施
实施XSS攻击需要具备两个条件：              
一、需要向web页面注入恶意代码；              
二、这些恶意代码能够被浏览器成功的执行。              
              
解决方法              
1、一种方法是在表单提交或者url参数传递前，对需要的参数进行过滤,请看如下XSS过滤工具类代码              
2、二、 过滤用户输入的 检查用户输入的内容中是否有非法内容。如<>（尖括号）、”（引号）、 ‘（单引号）、%（百分比符号）、;（分号）、()（括号）、&（& 符号）、+（加号）等。、严格控制输出              
3、改成纯前端渲染，把代码和数据分隔开。              
4、对 HTML 做充分转义。  


XSS攻击是Web攻击中最常见的攻击方法之一，它是通过对网页注入可执行代码且成功地被浏览器 执行，达到攻击的目的，形成了一次有效XSS攻击，一旦攻击成功，它可以获取用户的联系人列表，然后向联系人发送虚假诈骗信息，可以删除用户的日志等等，有时候还和其他攻击方式同时实 施比如SQL注入攻击服务器和数据库、Click劫持、相对链接劫持等实施钓鱼，它带来的危害是巨 大的，是web安全的头号大敌。              
              
              
xss 分类：（三类）              
              
类型	存储区*	插入点*              
存储型 XSS	后端数据库	HTML              
反射型 XSS	URL	HTML              
DOM 型 XSS	后端数据库/前端存储/URL	前端 JavaScript              
              
1、反射型XSS：<非持久化> 攻击者事先制作好攻击链接, 需要欺骗用户自己去点击链接才能触发XSS代码（服务器中没有这样的页面和内容），一般容易出现在搜索页面。              
              
2、存储型XSS：<持久化> 代码是存储在服务器中的，如在个人信息或发表文章等地方，加入代码，如果没有过滤或过滤不严，那么这些代码将储存到服务器中，每当有用户访问该页面的时候都会触发代码执行，这种XSS非常危险，容易造成蠕虫，大量盗窃cookie（虽然还有种DOM型XSS，但是也还是包括在存储型XSS内）。              
              
3、DOM型XSS：基于文档对象模型Document Objeet Model，DOM)的一种漏洞。DOM是一个与平台、编程语言无关的接口，它允许程序或脚本动态地访问和更新文档内容、结构和样式，处理后的结果能够成为显示页面的一部分。DOM中有很多对象，其中一些是用户可以操纵的，如uRI ，location，refelTer等。客户端的脚本程序可以通过DOM动态地检查和修改页面内容，它不依赖于提交数据到服务器端，而从客户端获得DOM中的数据在本地执行，如果DOM中的数据没有经过严格确认，就会产生DOM XSS漏洞。              
              
XSS中的过滤是在前端怎么做？                
前端防xss分两类：1是提交数据的时候, 2是渲染数据的时候。                
                
提交数据, 即post表单, 或者ajax提交数据的时候, 对用户输入的内容进行过滤, 当前由于是前端操作, 随便找个懂点的都可以通过模拟请求绕过, 但是做还是要做。                
                
渲染数据, 这个是重点, 哪怕提交数据时, 被绕过(后端也没有处理), 渲染时予以过滤, 也能达到效果, 这里一般指ajax+template, 或者各种mvvm框架, 对于是用户提供的内容, 能用text方法的, 一律用text方法, 一定要用html方法的, 则进行数据过滤。                
                


浅谈XSS攻击原理与解决方法              
https://www.cnblogs.com/shawWey/p/8480452.html              
https://www.jianshu.com/p/4fcb4b411a66              
https://blog.csdn.net/free_xiaochen/article/details/82289316              
https://segmentfault.com/a/1190000016551188 



### Click劫持
防御
点击劫持的根本就是目标网站被攻击者通过 iframe 内嵌在他的网页中，所以只要阻止我们的网站被 iframe 内嵌即可。

1、JavaScript 禁止 iframe 内嵌
2、X-Frame-Options HTTP 响应头禁止 iframe 内嵌

X-Frame-Options 的兼容性非常好，基本上现在市面所有浏览器都支持。



Click劫持:
点击劫持（click jacking）也被称为 UI 覆盖攻击。它通过不可见框架底部的内容误导受害者点击，虽然受害者点击的是他所看到的网页，但其实他所点击的是被黑客精心构建的另一个置于原网页上面的透明页面。

原理
这种攻击利用了HTML中<iframe>标签的透明属性。

在 HTML 中，我们可以在 iframe 里面嵌套另一个网页。通过设置 iframe 的透明度，可以使 iframe 不可见，同时在其底部放一个很有诱惑力的图片，调整网页中 iframe 中网页提交按钮的位置，使其和图片里按钮的位置相同。如此，当用户点击图片中的按钮时，其实是点击了网页中的按钮。
————————————————
版权声明：本文为CSDN博主「xiko」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/zhoulei1995/article/details/79039142




---------------------------------------------------------------------------------------------------------------------
## 如何使用SpringSecurity防御CSRF攻击

Csrf Token
用户登录时，系统发放一个CsrfToken值，用户携带该CsrfToken值与用户名、密码等参数完成登录。系统记录该会话的 CsrfToken 值，之后在用户的任何请求中，都必须带上该CsrfToken值，并由系统进行校验。
这种方法需要与前端配合，包括存储CsrfToken值，以及在任何请求中（包括表单和Ajax）携带CsrfToken值。安全性相较于HTTP Referer提高很多，如果都是XMLHttpRequest，则可以统一添加CsrfToken值；但如果存在大量的表单和a标签，就会变得非常烦琐。

SpringSecurity中使用Csrf Token
Spring Security通过注册一个CsrfFilter来专门处理CSRF攻击，在Spring Security中，CsrfToken是一个用于描述Token值，以及验证时应当获取哪个请求参数或请求头字段的接口



详解如何在spring boot中使用spring security防止CSRF攻击
https://www.cnblogs.com/dalianpai/p/12393133.html
https://www.jb51.net/article/139595.htm



---------------------------------------------------------------------------------------------------------------------
## CC攻击与DDOS攻击定义

CC/DDOS攻击与流量攻击
CC攻击与DDOS攻击定义
CC攻击与DDOS攻击原理：
CC攻击与DDOS攻击方式
CC攻击与DDOS攻击区别
DDoS 攻击方式分类
DDoS反射放大之SSDP攻击
SYN Flood攻击(SYN洪水攻击)
ACK FLOOD攻击
Burpsuite的介绍与安装


DDoS全称:分布式拒绝服务(DDoS:Distributed Denial of Service)。
CC攻击全称Challenge Collapsar，中文意思是挑战黑洞，因为以前的抵抗DDoS攻击的安全设备叫黑洞，顾名思义挑战黑洞就是说黑洞拿这种攻击没办法，新一代的抗DDoS设备已经改名为ADS(Anti-DDoS System)，基本上已经可以完美的抵御CC攻击了。

CC攻击与DDOS攻击原理：
DDOS攻击:该攻击方式利用目标系统网络服务功能缺陷或者直接消耗其系统资源，使得该目标系统无法提供正常的服务。
攻击者进行拒绝服务攻击，实际上让服务器实现两种效果：一是迫使服务器的缓冲区满，不接收新的请求；二是使用IP欺骗，迫使服务器把合法用户的连接复位，影响合法用户的连接。

CC攻击的原理是通过代理服务器或者大量肉鸡模拟多个用户访问目标网站的动态页面，制造大量的后台数据库查询动作，消耗目标CPU资源，造成拒绝服务。
CC不像DDoS可以用硬件防火墙来过滤攻击，CC攻击本身的请求就是正常的请求。


CC攻击与DDOS攻击方式：
二者的攻击方式主要分为三种：直接攻击、代理攻击、僵尸网络攻击


CC攻击与DDOS攻击区别：
DDoS攻击打的是网站的服务器，而CC攻击是针对网站的页面攻击的，用术语来说就是，一个是WEB网络层拒绝服务攻击（DDoS），一个是WEB应用层拒绝服务攻击（CC），
网络层就是利用肉鸡的流量去攻击目标网站的服务器，针对比较本源的东西去攻击，服务器瘫痪了，那么运行在服务器上的网站肯定也不能正常访问了。
而应用层就是我们用户看得到的东西，就比如说网页，CC攻击就是针对网页来攻击的，CC攻击本身是正常请求，网站动态页面的正常请求也会和数据库进行交互的，当这种"正常请求"达到一种程度的时候，服务器就会响应不过来，从而崩溃。


CC攻击是DDOS(分布式拒绝服务)的一种，相比其它的DDOS攻击CC似乎更有技术含量一些。这种攻击你见不到虚假IP，见不到特别大的异常流量，但造成服务器无法进行正常连接，一条ADSL的普通用户足以挂掉一台高性能的Web服务器。由此可见其危害性，称其为"Web杀手"毫不为过。

CC攻击:利用大量代理服务器对目标计算机发起大量连接，导致目标服务器资源枯竭造成拒绝服务。

CC攻击模拟多个用户(多少线程就是多少用户)不停的进行访问(访问那些需要大量数据操作，就是需要大量CPU时间的页面).这一点用一个一般的性能测试软件就可以做到大量模拟用户并发。

CC攻击，一般是针对数据库的，CC攻击者常常用某个页面，这个页面很特别，在使用这个页面的时候，会调用大量数据库资源，例如搜索，当用户在进行搜索的时候，搜索数据往往都会经过数据库对所有的数据进行检索，例如一个网站的数据库是500m，当用户在网站上面进行搜索的时候，就会调用整个网站的资源，这时候就需要数据库进行一一的比对，如果说这时候有大量的搜索命令，使得数据库无法处理，往往会导致服务器宕机

CC攻击的原理就是攻击者控制某些主机不停地发大量数据包给对方服务器造成服务器资源耗尽，一直到宕机崩溃。

DDOS是主要针对IP的攻击，而CC攻击的主要是网页。CC攻击相对来说，攻击的危害不是毁灭性的，但是持续时间长；而DDOS攻击就是流量攻击，这种攻击的危害性较大，通过向目标服务器发送大量数据包，耗尽其带宽，需要足够的带宽和硬件防火墙才能防御。



Cc攻击是什么？
CC = Challenge Collapsar，意为“挑战黑洞”，其前身名为Fatboy攻击，是利用不断对网站发送连接请求致使形成拒绝服务的目的。
业界之所以把这种攻击称为CC(Challenge Collapsar)，是因为在DDOS攻击发展前期，绝大部分的DDOS攻击都能被业界知名的“黑洞”(Collapsar)抵挡住，而CC攻击的产生就是为了挑战“黑洞”，故而称之为Challenge Collapsar。
攻击者通过代理服务器或者肉鸡向向受害主机不停地发大量数据包，造成对方服务器资源耗尽，一直到宕机崩溃。

怎么判断自己是在被CC攻击
CC攻击主要工作原理是耗资源，这就需要看是那种攻击方式，
看抓包分析是否是通过多IP，刷新页面，如果是这是最典型的Cc攻击。

如果cc攻击你网站打不开，指定会有一种资源耗尽，才会引发网站打不开，打开卡。
可自行判断一下，是下列四种情况中的那一种。
1，耗Cpu资源
黑客用1万台肉鸡，刷新你网站动态页面，如果你程序不够健壮，cpu直接100%
2，耗内存资源
黑客只要刷新你动态页面中搜索数据库的内容，只要搜索量一大，内存占满。网站直接打不开或者是非常卡。
3，耗I/o资源
黑客找到上传文件，或者是下载文件的页面，在不停的上传与下载，磁盘资源点满
4，耗带宽资源
下面这个带宽接10G，攻击上来2G，能看流量占用多少，如果流量占满了，服务器直接掉包，掉线。网站一点都打不开。


DDoS 攻击方式分类
1、反射型：目前常见的反射攻击有：DNS 反射攻击、NTP 反射攻击、SSDP 反射攻击等。
2、流量放大型
通过递归等手法将攻击流量放大的攻击类型，比如：以反射型中常见的 SSDP 协议为例，攻击者将 Search type 设置为 ALL。搜索所有可用的设备和服务，这种递归效果产生的放大倍数是非常大的，攻击者只需要以较小的伪造源地址的查询流量就可以制造出几十甚至上百倍的应答流量发送至目标。
3、混合型
在实际情况中，攻击者只求达到打垮对方的目的。发展到现在，高级攻击者已经不倾向使用单一的攻击手段。而是根据目标系统的具体环境灵动组合，发动多种攻击手段。

一般而言，我们会根据针对的协议类型和攻击方式的不同，把 DDoS 分成 SYN Flood、ACK Flood、Connection Flood、UDP Flood、NTP Flood、SSDP Flood、DNS Flood、HTTP Flood、ICMP Flood、CC 等各类攻击类型。


DDoS反射放大之SSDP攻击
SSDP全称Simple Sever Discovery Protocol，它自己都说它简单了，其实真的很简单。为啥我先说它呢，还是因为上面说的Web情结，它的本质是一个在UDP上面的HTTP协议
之前一直搞Web安全，大家都知道使用BurpSuite抓包，一个请求对应一个响应，这是http协议定死了的。
所以到了网络层，思维不能存在定势，一个请求包可能对应多个响应包，这也是TCP/IP协议允许的，反射放大就是基于这个原理。
通过“以小博大”，“四两拨千斤”的姿态进行的DDoS攻击


SYN Flood攻击(SYN洪水攻击)。
SYN Flood攻击是一种典型的DoS（Denial of Service）攻击，是一种利用TCP协议缺陷，发送大量伪造的TCP连接请求，从而使被攻击方资源耗尽（CPU满负荷或内存不足）的攻击方式。
该攻击将使服务器TCP连接资源耗尽，停止响应正常的TCP连接请求。
ACK Flood攻击原理与SYN Flood攻击原理类似。


ACK FLOOD攻击
ack flood攻击同样是利用TCP三次握手的缺陷实现的攻击， ack flood攻击利用的是三次握手的第二段




每秒百万级CC攻击—-DDOS 防御事件
https://www.hi-linux.com/posts/50873.html#%E6%97%A0%E7%BA%BF-ddos-%E6%94%BB%E5%87%BB
https://cshihong.github.io/2019/05/14/%E7%BD%91%E7%BB%9C%E5%B1%82-TCP-UDP-%E6%94%BB%E5%87%BB%E4%B8%8E%E9%98%B2%E5%BE%A1%E5%8E%9F%E7%90%86/
https://zhuanlan.zhihu.com/p/53847917
https://zhuanlan.zhihu.com/p/82817326
https://blog.csdn.net/qq_34777600/article/details/81978262
http://www.ruanyifeng.com/blog/2018/06/ddos.html
https://www.infoq.cn/article/HR4dxexUuG7VU2TV1Ryk

DDoS反射放大之SSDP攻击
https://zhuanlan.zhihu.com/p/41573321



SYN Flood攻击原理与防范
https://blog.csdn.net/cpcpcp123/article/details/52739407
SYN FLOOD攻击和ACK FLOOD攻击
https://www.freebuf.com/column/132782.html
https://blog.csdn.net/chenyulancn/article/details/78832613


VDSL与ADSL的区别
https://blog.csdn.net/sj349781478/article/details/74058936


Burpsuite的介绍与安装
burpsuite常被黑客用来进行网站渗透
Burp Suite 是用于攻击web 应用程序的集成平台。它包含了许多工具，并为这些工具设计了许多接口，以促进加快攻击应用程序的过程。

参考
https://zhuanlan.zhihu.com/p/22369250






---------------------------------------------------------------------------------------------------------------------                
## Cookie跨域共享参考                
                
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


