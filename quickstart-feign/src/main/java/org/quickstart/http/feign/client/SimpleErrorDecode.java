package org.quickstart.http.feign.client;

import java.io.IOException;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;

/**
 * Created by 9527 on 2017/8/10.
 */
public class SimpleErrorDecode implements ErrorDecoder {

    private final Decoder decoder;
    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    public SimpleErrorDecode(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        Class error = null;
        switch (status) {
            case 400:
                error = Error.class;
                break;
            case 401:
                error = Error.class;
                break;
            case 403:
                error = Error.class;
                break;
            case 404:
                error = Error.class;
                break;
            case 409:
                error = Error.class;
                break;
            case 422:
                error = Error.class;
                break;
            default:
                error = Error.class;
        }

        try {
            return (Exception) decoder.decode(response, error);
        } catch (IOException e) {
            return defaultDecoder.decode(methodKey, response);
        }
    }
}
