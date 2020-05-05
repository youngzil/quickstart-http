AsyncAPI（异步API）的设计

异步API的设计,AsyncAPI
AsyncAPI就是这样一种选择。本质上，AsyncAPI是消息传递范例的替代品，它是OpenAPI或RAML的替代品。





AsyncAPI介绍
AsyncAPI 是根据OpenAPI规范改编而来的

asyncapi 指南
asyncapi 是可以用来创建异步机器可读定义api的指南，我们可以用来创建事件驱动的架构。

说明
asyncapi 的定义类似openapi，目前指南版本为2.0，很值得学习下

客户端发出一个请求，要求创建资源。
1、请求创建资源
2、查询进展
3、删除查询链接


同步API
201 Created告诉客户端，请求成功，资源已经创建。新的资源的网址请看Location字段。

异步API请求
202 Accepted告诉客户端，请求已经接受，但还没有处理，可以去Location字段查询进展。
除了上面的头信息，服务器的回应如果有数据体，可以返回一些有效信息（比如任务完成的估计时间、当前状态等等）。


查询进展
200 Ok告诉客户端，请求成功，具体情况查看数据体。数据体里给出提示，异步操作已成功或还需要等待。
有一种特殊情况，用户查询异步操作的进展的时候，可能会希望，如果异步操作已经完成，就直接跳转到新资源。
这时，服务器回应 303。303 see other告诉客户端，重定向到不同的资源。Location字段就是跳转的目标，也就是新资源的网址。


删除查询链接
一旦异步操作完成，客户端可以要求服务器删除查询链接。
服务器回应 204。204 No Content告诉客户端，删除成功。以后，客户端再访问这个查询链接，服务器回应404 Not Found。
如果客户端不删除查询链接，服务器完成异步任务后，也可以自动删除。客户端再请求这个链接，服务器回应410 Gone，表示该链接永久性不再可用。


总结起来：
- 同步响应：201 Created
- 异步响应：202 Accepted

与其说异步api设计，不如说是对http status code的理解，有了理解，api设计自然就形成了，挺好，值得借鉴！





OpenAPI 3.0 VS AsyncAPI 2.0

OpenAPI Specification（以前称为Swagger）是一种解决方案，可为REST API生成机器可读的文档。在2016年，Swagger被正式更名为OpenAPI规范。
OpenAPI与语言无关，可用于自动生成有关各种功能，方法，参数，模型等的文档。
OpenAPI的主要卖点在于，以这种方式统一和标准化文档时，客户端库，源代码，可执行文件和文档都是本地同步的，因为规范文档本身用于生成其余大部分。

AsyncAPI与OpenAPI：有什么区别？

两种非常广泛的API的文档解决方案：OpenAPI和AsyncAPI。这两种解决方案都生成机器可读的文档，我们将在稍后讨论这个概念，但是它们针对的是截然不同的API类型。

四类api ：
1、同步服务api: 普通的Http无状态单次请求和响应 
2、异步服务api: 应用于服务提供商提供的服务无法在当时处理完毕，先返回一个请求响应，当服务处理结束以后再将服务处理结果返回给服务调用者 
3、订阅服务api: 类似rss.服务调用者只需要订阅服务即可获得服务提供商推送的服务内容 
4、大数据量上传api: 上传文件 



OpenAPI规范可用于保护和加速API生命周期。
https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md
https://app.swaggerhub.com/apis/echovue/UserPreference/1.0.0

OpenAPI Specification（以前称为Swagger）是一种解决方案，可为REST API生成机器可读的文档。Swagger最初于2010年开发，后来于2015年被SmartBear Software收购。随着Swagger的开发和扩展，在主要行业参与者的支持下，Open API Initiative发起了以开放格式进一步开发和推广Swagger工具集的过程，以确保标准化和支持。在2016年，Swagger被正式更名为OpenAPI规范。

OpenAPI与语言无关，可用于自动生成有关各种功能，方法，参数，模型等的文档。OpenAPI的主要卖点在于，以这种方式统一和标准化文档时，客户端库，源代码，可执行文件和文档都是本地同步的，因为规范文档本身用于生成其余大部分。

加上声明性的资源利用，这意味着最终用户不需要了解服务器实现，资源，对服务器代码的访问等任何知识就可以理解和使用服务，并且您已经拥有了非常丰富的资源。 RESTful Web服务的强大解决方案。



AsyncAPI：
https://www.asyncapi.com/docs/specifications/1.2.0/

AsyncAPI  最初是Hitch的一个辅助项目（API Changelog），后来由FranMéndez创建。AsyncAPI的开发人员发现消息驱动空间中缺少工具，因此，当为HTTP API提供大量相反的工具时，AsyncAPI决定文档规范中缺少标准化的部分。

从已确定的需求出发，他们开始寻找标准化的基于消息的规范系统的解决方案。他们制定了一个“ API理论”，可以这么说–本质上，具有如此众多的交互API类型，例如RESTful Web服务，GraphQL和消息驱动的体系结构，AsyncAPI可以用作各种格式，协议和规范的通用统一语言。 ，允许在消息驱动系统中进行标准化的通信。

这种支持表面上是有效的-通过他们自己的计算，假设他们为6种编程语言，4种模式格式和5种协议创建工具，他们将能够在消息驱动的空间中生成120种可能的组合。这是一个很大的数目，肯定可以提供最初设计的标准化解决方案。




参考
http://www.ruanyifeng.com/blog/2018/12/async-api-design.html
https://blog.csdn.net/weixin_36836847/article/details/95206329
https://nordicapis.com/7-protocols-good-for-documenting-with-asyncapi/
https://nordicapis.com/asyncapi-vs-openapi-whats-the-difference/
https://farazdagi.com/2014/rest-and-long-running-jobs/
https://medium.com/asyncapi/whats-new-on-asyncapi-lots-2d9019a1869d



