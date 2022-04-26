Java中的Unirest：简化的轻量级HTTP客户端库。 


http://unirest.io/java.html
测试使用quickstart-httpserver作为服务端



基于apache httpcomponent的，unirest-java的github地址为https://github.com/kong/unirest-java/，其另外一个很牛逼的开源产品就是微服务的api网关kong

[unirest-java文档](http://kong.github.io/unirest-java/)
[unirest-java Github](https://github.com/Kong/unirest-java)
[unirest-java 使用示例](https://www.baeldung.com/unirest)

老的版本是2016停更，最新版本是1.4.9，包路径是com.mashape.unirest.
新的版本包路径是kong.unirest.







响应  
在接收到响应Unirest以对象的形式返回结果时，对于响应细节，该对象应该始终具有与每种语言相同的键。  
.getStatus（） - HTTP响应状态代码（示例：200）  
.getStatusText（） - HTTP响应状态文本（示例：“OK”）  
.getHeaders（） - HTTP响应标头  
.getBody（） - 解析响应正文（如适用），例如JSON响应将解析为对象/关联数组。  
.getRawBody（） - 未解析的响应正文  

 使用Unirest请求的数据一般是 JsonNode，若返回类型报错，一般为String，最后得到的为.asString();  
.header用了设置header的各种参数，包括token  
.routeParam用于设置路径中带有参数的如{cid}之类的  
.paramString用于设置get命令中 &的键值对  
.field用于设置post的参数，也可以直接用一个map,.fields(prams)    //prams是一个map，put了很多参数进去，和直接多个fields一样的效果  
返回的结果打印一般用，response.getBody( ).getObject( )    得到的JSON对象，之后的JSON解析出需要的内容都是以此为基础分层剥离。  
返回的状态用response.getStatus(),即返回的状态码，注意有个别成功码并不一样，如前台是200，后台是302  


轻量级的HTTP开发库 Unirest的用法：
 使用Unirest请求的数据一般是 JsonNode，若返回类型报错，一般为String，最后得到的为.asString();
.header用了设置header的各种参数，包括token
.routeParam用于设置路径中带有参数的如{cid}之类的
.paramString用于设置get命令中 &的键值对
.field用于设置post的参数，也可以直接用一个map,.fields(prams)    //prams是一个map，put了很多参数进去，和直接多个fields一样的效果
返回的结果打印一般用，response.getBody( ).getObject( )    得到的JSON对象，之后的JSON解析出需要的内容都是以此为基础分层剥离。
返回的状态用response.getStatus(),即返回的状态码，注意有个别成功码并不一样，如前台是200，后台是302




