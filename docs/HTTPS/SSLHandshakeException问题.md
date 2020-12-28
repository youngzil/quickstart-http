javax.net.ssl.SSLHandshakeException: null cert chain

在使用Socket SSL双向连接时，客户端可以连接上服务端，

但传输数据时报错javax.net.ssl.SSLHandshakeException: null cert chain

原因是使用的KeyStore文件不对

首先创建服务器端私有密钥和公共密钥
1. keytool -genkey -alias serverkey -keystore kserver.ks
2. keytool -export -alias serverkey -keystore kserver.ks -file server.crt
3. keytool -import -alias serverkey -file server.crt -keystore tclient.ks

下面创建客户器端私有密钥和公共密钥
1. keytool -genkey -alias clientkey -keystore kclient.ks
2. keytool -export -alias clientkey -keystore kclient.ks -file client.crt
3. keytool -import -alias clientkey -file client.crt -keystore tserver.ks

通过以上方式生成的密钥文件，server端应使用kserver.ks和tserver.ks，client端应使用kclient.ks和tclient.ks


[SSL连接报错：javax.net.ssl.SSLHandshakeException](https://blog.csdn.net/weixin_34122810/article/details/92189792)  


