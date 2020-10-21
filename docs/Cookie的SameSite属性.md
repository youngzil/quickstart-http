Chrome 51 开始，浏览器的 Cookie 新增加了一个SameSite属性，用来防止 CSRF 攻击和用户追踪。


原因  
Chrome 80 默认将没有设置SameSite设置为SameSite=Lax

SameSite取值  

Strict

    Scrict最为严格，完全禁止第三方Cookie，跨站点时，任何情况下都不会发送Cookie  
    只有在访问最初设置的域时，才可访问具有此设置的 Cookie。换言之，Strict 会完全阻止跨站点使用 Cookie。这种选择最适用于需要高安全性的应用，如银行。
Lax

    Lax规则稍稍放宽，大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外。  
    Cookies with this setting are sent only on same-site requests or top-level navigation with non-idempotent HTTP requests, like HTTP GET . 因此，如果第三方可以使用cookie，但增加了安全优势，保护用户免受CSRF攻击的侵害，则将使用此选项。

None

    使用此设置的Cookie将像Cookie当前工作的方式一样工作。  
    网站可以选择显式关闭SameSite属性，将其设为None。  
    不过，前提是必须同时设置Secure属性（Cookie 只能通过 HTTPS 协议发送），否则无效


操作方法  
- 谷歌浏览器地址栏输入：chrome://flags/  
- 找到：SameSite by default cookies、Cookies without SameSite must be secure  
- 设置上面这两项设置成 Disable  


Cookie设置  
- 报文里面set-cookie，添加SameSite=None; Secure=true




参考  
[Chrome浏览器改变SameSite设置](https://juejin.im/post/6844904096655212558)  
[关于cookie的SameSite属性](https://segmentfault.com/a/1190000023890465)  
[Google Chrome SameSite Cookie 策略](https://docs.adobe.com/content/help/zh-Hans/target/using/implement-target/before-implement/privacy/google-chrome-samesite-cookie-policies.translate.html)  
[Cookie 的 SameSite 属性](http://www.ruanyifeng.com/blog/2019/09/cookie-samesite.html)  





