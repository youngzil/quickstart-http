Session和Cookie的区别与联系


客户端浏览器禁用Cookie，怎么跟踪会话
Cookie和Session区别
session跨域共享




客户端浏览器禁用Cookie，怎么跟踪会话：
1、重写URL：
    1、手动重写，在URL后面拼接请求中都带有sessioinId
    2、使用response.encodeURL()更加“智能”，它会判断客户端浏览器是否禁用了Cookie，如果禁用了，那么这个方法在URL后面追加jsessionid，否则不会追加。
2、隐藏表单：表单域中隐藏文本框



Cookie和Session区别

cookie存储在客户端（浏览器），session存储在服务端，简 单的说，当你登录一个网站的时候，如果web服务器端使用的是session，那么所有的数据都保存在服务器上面，客户端每次请求服务器的时候会发送 当前会话的sessionid，服务器根据当前sessionid判断相应的用户数据标志，以确定用户是否登录，或具有某种权限。由于数据是存储在服务器 上面，所以你不能伪造，但是如果你能够获取某个登录用户的sessionid，用特殊的浏览器伪造该用户的请求也是能够成功的。sessionid是服务 器和客户端链接时候随机分配的，一般来说是不会有重复，但如果有大量的并发请求，也不是没有重复的可能性。



session跨域共享

对于多网站(同一父域不同子域)单服务器，我们需要解决的就是来自不同网站之间SessionId的共享。由于域名不同（blog.yoodb.com 和daohang.yoodb.com），而SessionId又分别储存在各自的cookie中，因此服务器会认为对于两个子站的访问,是来自不同的会话。解决的方法是通过修改cookies的域名为父域名达到cookie共享的目的,从而实现SessionId的共享。带来的弊端就是，子站间的cookie信息也同时被共享了。



总结：
1）cookie数据存放在客户的浏览器上，session数据放在服务器上。
2）cookie不是很安全，别人可以分析存放在本地的cookie并进行cookie欺骗，考虑到安全应当使用session。
3）session会在一定时间内保存在服务器上。当访问增多，会比较占用你服务器的性能，考虑到减轻服务器性能方面，应当使用cookie。
4）单个cookie保存的数据不能超过4K，很多浏览器都限制一个站点最多保存20个cookie。
5）可以考虑将登陆信息等重要信息存放为session，其他信息如果需要保留，可以放在cookie中。



参考
https://blog.yoodb.com/yoodb/article/detail/1492




作者：Felix Zhang
链接：https://www.zhihu.com/question/61141564/answer/600462173
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

先说结论，secure没有强制https时候，http://a.com的http与https可以共享。

看一下cookie的主要属性答案就有了：
name字段为一个cookie的名称。
value字段为一个cookie的值。
domain字段为可以访问此cookie的域名。
path字段为可以访问此cookie的页面路径。 比如domain是abc.com,path是/test，那么只有/test路径下的页面可以读取此cookie。
expires/Max-Age 字段为此cookie超时时间。若设置其值为一个时间，那么当到达此时间后，此cookie失效。不设置的话默认值是Session，意思是cookie会和session一起失效。当浏览器关闭(不是浏览器标签页，而是整个浏览器) 后，此cookie失效。
Size字段 此cookie大小。
http字段  cookie的httponly属性。若此属性为true，则只有在http请求头中会带有此cookie的信息，而不能通过document.cookie来访问此cookie。
secure 字段 设置是否只能通过https来传递此条cookie


非顶级域名，如二级域名或者三级域名，设置的cookie的domain只能为顶级域名或者二级域名或者三级域名本身，不能设置其他二级域名的cookie，否则cookie无法生成。
顶级域名只能设置domain为顶级域名，不能设置为二级域名或者三级域名，否则cookie无法生成。
二级域名能读取设置了domain为顶级域名或者自身的cookie，不能读取其他二级域名domain的cookie。
所以要想cookie在多个二级域名中共享，需要设置domain为顶级域名，这样就可以在所有二级域名里面或者到这个cookie的值了。
顶级域名只能获取到domain设置为顶级域名的cookie，其他domain设置为二级域名的无法获取。





