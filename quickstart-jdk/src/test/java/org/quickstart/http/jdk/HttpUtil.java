/**
 * 项目名称：quickstart-http-jdk 
 * 文件名：HttpUtil.java
 * 版本信息：
 * 日期：2018年4月23日
 * Copyright yangzl Corporation 2018
 * 版权所有 *
 */
package org.quickstart.http.jdk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * HttpUtil
 * 
 * @author：youngzil@163.com
 * @2018年4月23日 下午9:27:15
 * @since 1.0
 */
public class HttpUtil {

    // post请求
    public static final String HTTP_POST = "POST";

    // get请求
    public static final String HTTP_GET = "GET";

    // utf-8字符编码
    public static final String CHARSET_UTF_8 = "UTF-8";

    // HTTP内容类型
    public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

    // HTTP内容类型
    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded;charset=" + CHARSET_UTF_8;

    // 请求超时时间
    public static final int SEND_REQUEST_TIME_OUT = 20000;

    // 将读超时时间
    public static final int READ_TIME_OUT = 20000;

    /**
     * 
     * @param requestType 请求类型
     * @param urlStr 请求地址
     * @param body 请求发送内容
     * @return 返回内容
     * @throws Exception
     */
    public static String requestMethod(String requestType, String urlStr, String body) throws Exception {
        // long star = System.currentTimeMillis();
        boolean isDoInput = false;
        if (body != null && body.length() > 0) {
            isDoInput = true;
        }
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            if (isDoInput) {
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            }
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.setUseCaches(false);
            // httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
            httpURLConnection.setRequestProperty("Accept-Charset", CHARSET_UTF_8);
            httpURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE_FORM_URL);
            httpURLConnection.setRequestMethod(requestType);

            httpURLConnection.connect();

            if (isDoInput) {
                outputStream = httpURLConnection.getOutputStream();
                outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(body);
                outputStreamWriter.flush();// 刷新
            }
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("http error code is :" + httpURLConnection.getResponseCode());
            }

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, CHARSET_UTF_8);
                reader = new BufferedReader(inputStreamReader);

                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
            }
            // httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {// 关闭流
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // System.out.println("耗时："+(System.currentTimeMillis()-star)+"毫秒");
        return resultBuffer.toString();
    }

    /**
     * 请求重试机制 二次请求需等待0.5秒
     * 
     * @param requestType 请求类型
     * @param action 请求接口
     * @param params 请求参数 json格式
     * @param retry true-重试, false-不重试
     * @return
     */
    public static String retryHandler(String requestType, String action, String params, boolean retry) {
        try {
            return requestMethod(requestType, action, params);
        } catch (Exception e) {
            e.printStackTrace();
            if (retry) {
                try {
                    Thread.sleep(500);
                    return requestMethod(requestType, action, params);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        return "-100001"; // 服务端接口出错
    }

    public static void main(String[] args) {
        System.out.println("----------------star------------------");
        // String params =
        // "{\"msgheader\":{\"route_id\":\"LZZBGE\",\"route_name\":\"1\",\"login_type\":\"1\",\"mobile\":\"1\",\"timestamp\":\"1\"},\"msgbody\":{\"nonce\":\"1\",\"sign\":\"7BBC156C8D9586E4B874385059FB966F\"}}";
        String params = "{\"msgheader\":{},\"msgbody\":{\"keyword\":\"\",\"sign\":\"DAA6320AE7D6C11F369E80808E52A697\"}}";
        try {
            System.out.println(requestMethod(HTTP_POST, "wwww.baidu.com", params));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception");
        }
        System.out.println("----------------end------------------");
    }

}
