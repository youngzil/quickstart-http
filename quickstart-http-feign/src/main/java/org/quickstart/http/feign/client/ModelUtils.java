package org.quickstart.http.feign.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by 9527 on 2017/8/16.
 */
public class ModelUtils {
    public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public ModelUtils() {
    }

    public static String toString(Object o) {
        return GSON.toJson(o);
    }
}
