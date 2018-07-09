/**
 * 项目名称：quickstart-okhttp 
 * 文件名：RequestGet.java
 * 版本信息：
 * 日期：2017年11月3日
 * Copyright asiainfo Corporation 2017
 * 版权所有 *
 */
package org.quickstart.http.okhttp.v3;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * RequestGet
 * 
 * @author：yangzl@asiainfo.com
 * @2017年11月3日 下午3:17:51
 * @since 1.0
 */
public class RequestGet {

    public static void main(String[] args) {
        // 创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        // 创建一个Request
        final Request request = new Request.Builder().url("https://github.com/hongyangAndroid").build();
        // new call
        Call call = mOkHttpClient.newCall(request);
        // 请求加入调度
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                // TODO Auto-generated method stub
                System.out.println("onFailure");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // TODO Auto-generated method stub
                System.out.println("onResponse");
                String htmlStr = response.body().string();
                System.out.println("return:" + htmlStr);

            }
        });

    }

}
