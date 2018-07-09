/**
 * 项目名称：quickstart-okhttp 
 * 文件名：BaseCallback.java
 * 版本信息：
 * 日期：2017年11月6日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.http.okhttp.v3.example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * BaseCallback
 * 
 * @author：yangzl@asiainfo.com
 * @2017年11月6日 下午11:30:22
 * @since 1.0
 */
public abstract class BaseCallback<T> {

    public Type mType;

    public BaseCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;

        return parameterized;// 这一行是我加的，代替下一行，没看懂下一行什么意思
        // return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public abstract void onBeforeRequest(Request request);

    public abstract void onFailure(Request request, Exception e);

    /**
     * 请求成功时调用此方法
     * 
     * @param response
     */
    public abstract void onResponse(Response response);

    /**
     *
     * 状态码大于200，小于300 时调用此方法 @param response @param t @throws
     */
    public abstract void onSuccess(Response response, T t);

    /**
     * 状态码400，404，403，500等时调用此方法
     * 
     * @param response
     * @param code
     * @param e
     */
    public abstract void onError(Response response, int code, Exception e);

    /**
     * Token 验证失败。状态码401,402,403 等时调用此方法
     * 
     * @param response
     * @param code
     * 
     */
    public abstract void onTokenError(Response response, int code);

}
