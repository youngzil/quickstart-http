- [API规范和名词](#API规范和名词)
- [OAS介绍](#OAS介绍)
- [RAML介绍](#RAML介绍)
- [AMF介绍](#AMF介绍)
- [AsyncAPI介绍](#AsyncAPI介绍)
- [JSON Schema介绍](https://github.com/youngzil/quickstart-framework/blob/master/quickstart-json/docs/JSON%20Schema.md)


---------------------------------------------------------------------------------------------------------------------

## API规范和名词

API规范、API标准、API Specifications



### API
API(Application Programming Interface)

[What is an API? (Application Programming Interface)](https://www.mulesoft.com/resources/api/what-is-an-api)




### API规范、API标准、API Specifications
- [OpenAPI Specification (OAS)](#OAS)
- [RESTful API Modeling Language (RAML)](#RAML)
- Anything Modeling Language (AML) and the open source AML Modeling Framework (AMF).

OAS 和 RAML 是两种流行的API 描述格式
OpenAPI Specification (OAS) and RESTful API Modeling Language (RAML)


[An interactive REST console based on RAML/OAS files](https://github.com/mulesoft/api-console)

[RAML And OAS Support In API Console](https://medium.com/raml-api/oas-and-raml-support-in-api-console-4842fe009768)




### 接口设计
OpenAPI
OAuth2.0
Webhook
AsyncAPI（异步API）
HTTP APIs


OpenAPI Specifications, RAML, Blueprint, gRPC, OData, JSON Schema, GraphQL, AsyncAPI





### RAML
RAML is focused on modeling (or designing) APIs  
RAML 专注于建模（或设计）API

RAML和OAS是建模、设计、定义API
YAML是一种数据格式、数据标记语言，描述数据的

YAML 最初代表Yet Another Markup Language（仍是一种置标语言），但正式采用递归定义YAML Ain't Markup Language（YAML不是一种置标语言）。

RAML vs YAML Vs Swagger for API Specifications  
[RAML vs YAML Vs Swagger for API Specifications](https://stoplight.io/blog/raml-vs-yaml/)




当 RAML 创始人 MuleSoft 加入 OpenAPI Initiative 时，这一点就变得很清楚，OpenAPI Initiative 是一个由致力于发展 OpenAPI 规范的公司和个人组成的联盟。相反，问题在于是从 RAML 开始并转向 OpenAPI，还是从一开始就使用 OpenAPI。在 Stoplight，该工具围绕单一方法构建，支持 OpenAPI 格式。

Anything Modeling Language (AML) and the open source AML Modeling Framework (AMF).




### OAS
OAS 和 RAML 是两种流行的API 描述格式
OpenAPI Specification (OAS) and RESTful API Modeling Language (RAML)

OpenAPI 规范（当时称为Swagger）



一个名为webapi-parser的新解析器，它支持 RAML 和 OpenAPI/OAS。
[webapi-parser的新解析器](https://github.com/raml-org/webapi-parser)  

API Spec parser based on AMF. Currently supports RAML 0.8, RAML 1.0, OAS 2.0 and OAS 3.0(beta).

基于AMF 的API 规范解析器。目前支持 RAML 0.8、RAML 1.0、OAS 2.0 和 OAS 3.0(beta)。

这个项目是一个瘦包装器，它从AMF公开 API 规范相关的功能。它是用 Scala 编写的，有两个版本：JavaScript和Java。

[OAS vs. RAML: What’s the Difference?](https://nordicapis.com/oas-vs-raml-whats-the-difference/)  



ASC(API规范会议)
OpenAPI Initiative’s API Specifications Conference (ASC)

OpenAPI Initiative’s API Specifications Conference (ASC) is a place for API practitioners to come together and discuss the evolution of API technology.  ASC includes cutting-edge technology keynotes and sessions that chart the future of APIs with in-depth specification and standards discussions.  The event is designed to be highly interactive with plenty of discussion time throughout the workshops and sessions.
OpenAPI Specifications, RAML, Blueprint, gRPC, OData, JSON Schema, GraphQL, AsyncAPI, and other formats will all be topics at the event, enabling attendees to get familiar with these formats and discuss how to use them in practice.

OpenAPI Initiative 的 API 规范会议 (ASC) 是 API 从业者聚集在一起讨论 API 技术演进的场所。ASC 包括尖端技术主题演讲和会议，通过深入的规范和标准讨论来描绘 API 的未来。 该活动旨在高度互动，并在整个研讨会和会议期间提供充足的讨论时间。
OpenAPI 规范、RAML、Blueprint、gRPC、OData、JSON Schema、GraphQL、AsyncAPI 和其他格式都将成为活动的主题，使与会者能够熟悉这些格式并讨论如何在实践中使用它们。


[OpenAPI Initiative’s API Specifications Conference (ASC)](https://events.linuxfoundation.org/openapi-asc/)  


---------------------------------------------------------------------------------------------------------------------
## OAS介绍

OpenAPI Specification (OAS)


Compatible with JSON Schema
与 JSON 模式兼容


ASC 一个会议
OpenAPI Initiative’s API Specifications Conference (ASC)

OpenAPI Specifications, RAML, Blueprint, gRPC, OData, JSON Schema, GraphQL, AsyncAPI


OpenAPI 文档可以用 YAML 编写。该规范还支持 JSON 格式

[](https://www.openapis.org/)  
[The OpenAPI Specification Repository](https://github.com/OAI/OpenAPI-Specification)  
[]()  
[]()  
[]()


---------------------------------------------------------------------------------------------------------------------
## RAML介绍

The RESTful API Modeling Language (RAML) Spec

RESTful API 建模语言( RAML ) 是一种基于YAML的语言，用于描述RESTful API。[2]它提供了描述 RESTful 或实际 RESTful API 所需的所有信息。尽管在设计时考虑了 RESTful API，但 RAML 能够描述不遵守 REST 的所有约束的 API（因此描述为“实际上是 RESTful”）。它鼓励重用，支持发现和模式共享，并旨在实现基于价值的最佳实践出现。[3]






[RAML官网](https://raml.org/)  
[]()  
[RAML Specification](https://github.com/raml-org/raml-spec)  
[维基百科 RAML 页面](https://en.wikipedia.org/wiki/RAML_(software))  


[Introduction to RAML – The RESTful API Modeling Language](https://www.baeldung.com/raml-restful-api-modeling-language-tutorial)  
[]()  





---------------------------------------------------------------------------------------------------------------------
## AMF介绍

Anything Modeling Language (AML) and the open source AML Modeling Framework (AMF).


AMF (AML Modeling Framework) is an open-source library capable of parsing and validating AML metadata documents.

AMF（AML 建模框架）是一个开源库，能够解析和验证 AML 元数据文档。

AMF 的模块化设计使您能够创建能够解析 AML 未定义的其他元数据语法的插件。例如，最常用的 AMF 模块 web-api 模块可以解析RAML、OAS（原 Swagger）和AsyncAPI规范语言。


格式： YAML JSON



[](https://a.ml/docs/)  
[](https://github.com/aml-org/amf)  
[]()  
[]()  
[]()



---------------------------------------------------------------------------------------------------------------------
## AsyncAPI介绍

AsyncAPI 规范允许您创建异步 API 的机器可读定义。



[AsyncAPI官网](https://www.asyncapi.com/)  
[AsyncAPI Specification](https://github.com/asyncapi/spec)  
[]()  
[]()  
[]()  
[]()  
[]()  





---------------------------------------------------------------------------------------------------------------------

