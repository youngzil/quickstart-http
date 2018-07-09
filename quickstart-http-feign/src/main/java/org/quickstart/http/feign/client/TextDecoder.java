package org.quickstart.http.feign.client;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;

/**
 * 仅仅适用于Http Response Body中的内容为普通文本信息
 * 没有特殊字符，没有双引号号等，非json格式，内容类似为：no resource found.
 * Created by 9527 on 2017/12/27.
 */
public class TextDecoder implements Decoder {

    private final Gson gson;

    public TextDecoder() {
        this(new Gson());
    }
    public TextDecoder(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        String content = "";
        Response.Body body = response.body();
        if (body != null)
            content = Util.toString(body.asReader());

        String json = generateJSONString(content);
        Object obj = null;
        try {
            obj = this.gson.fromJson(json, type);
        } catch (JsonIOException e) {
            if(e.getCause() != null && e.getCause() instanceof IOException) {
                throw (IOException)IOException.class.cast(e.getCause());
            }

            throw e;
        }

        return obj;
    }

    private String generateJSONString(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"message\": \"").append(message).append("\"");
        builder.append("}");

        return builder.toString();
    }
}
