/**
 * 项目名称：quickstart-okhttp 
 * 文件名：OkHttpUtils.java
 * 版本信息：
 * 日期：2017年11月6日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.http.okhttp.v3.example;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttpUtils
 * 
 * @author：yangzl@asiainfo.com
 * @2017年11月6日 下午11:27:29
 * @since 1.0
 */
public class OkHttpUtils {

    private static OkHttpUtils mInstance;

    private OkHttpClient mHttpClient;

    private Gson mGson;

    static {
        mInstance = new OkHttpUtils();
    }

    private OkHttpUtils() {

        mHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = mHttpClient.newBuilder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        mGson = new Gson();

    };

    public static OkHttpUtils getInstance() {
        return mInstance;
    }

    public void doRequest(final Request request, BaseCallback baseCallback) {

        baseCallback.onBeforeRequest(request);

        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                baseCallback.onFailure(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {

                    String resultStr = response.body().string();

                    if (baseCallback.mType == String.class) {

                        /*baseCallback.onSuccess(response,resultStr);*/
                        callbackSuccess(baseCallback, response, resultStr);
                    } else {
                        try {

                            Object obj = mGson.fromJson(resultStr, baseCallback.mType);
                            /*baseCallback.onSuccess(response,obj);*/
                            callbackSuccess(baseCallback, response, obj);

                        } catch (com.google.gson.JsonParseException e) { // Json解析的错误
                            /*baseCallback.onError(response,response.code(),e);*/
                            callbackError(baseCallback, response, e);
                        }
                    }

                } else {
                    callbackError(baseCallback, response, null);
                    /*baseCallback.onError(response,response.code(),null);*/
                }

                /*mGson.fromJson(response.body().string(),baseCallback.mType);*/

            }

        });

    }

    public void get(String url, BaseCallback callback) {

        Request request = buildRequest(url, HttpMethodType.GET, null);

        doRequest(request, callback);

    }

    public void post(String url, Map<String, String> params, BaseCallback callback) {

        Request request = buildRequest(url, HttpMethodType.POST, params);

        doRequest(request, callback);
    }

    private Request buildRequest(String url, HttpMethodType methodType, Map<String, String> params) {

        Request.Builder builder = new Request.Builder().url(url);

        if (methodType == HttpMethodType.POST) {

            RequestBody body = builderFormData(params);

            builder.post(body);
        } else if (methodType == HttpMethodType.GET) {

            builder.get();
        }

        return builder.build();

    }

    private RequestBody builderFormData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private void callbackSuccess(final BaseCallback callback, final Response response, final Object obj) {

        // Android Handler
        // 创建handler进行UI界面的更新操作，创建callbackSuccess方法
        /*mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });*/
    }

    private void callbackError(final BaseCallback callback, final Response response, final Exception e) {

        // Android Handler
        // 创建handler进行UI界面的更新操作，创建callbackSuccess方法
        /*mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });*/
    }

}
