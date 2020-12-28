package org.quickstart.http.jdk;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendPostDemo3 {
  public static void main(String[] args) throws Exception {
    String urlPath = new String("http://127.0.0.1:21100/http");

    // String param =
    // "{\"pub_info\":{\"imp_type\":\"IVRTwoConfirm\",\"sys_op_id\":\"ywdt\",\"sys_password\":\"9A5696B73B141F6DCF90F35C2BAF855E\"},\"busi_info\":{\"content\":\"即将为13988893993办理30元流量套餐业务，确认办理请按1，重听请按9\",\"vcid\":\"572\"}}";
    String param =
        "{\"pub_info\":{\"imp_type\":\"IVRTwoConfirm\",\"sys_op_id\":\"ywdt\",\"sys_password\":\"E8F91993C5317FB840FE9AB5518293FE\"},\"busi_info\":{\"content\":\"即将为13988893993办理30元流量套餐业务，确认办理请按1，重听请按9\",\"vcid\":\"571\"}}";

    // 建立连接
    URL url = new URL(urlPath);
    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
    // 设置参数
    httpConn.setDoOutput(true); // 需要输出
    httpConn.setDoInput(true); // 需要输入
    httpConn.setUseCaches(false); // 不允许缓存
    httpConn.setRequestMethod("POST"); // 设置POST方式连接
    // 设置请求属性
    httpConn.setRequestProperty("Content-Type", "application/json");
    // httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
    // httpConn.setRequestProperty("Charset", "UTF-8");
    httpConn.setRequestProperty("appCode", "AppCode_260");
    httpConn.setRequestProperty("apiCode", "GW_SYS_ESBWH02_QRY_OUTBOUND_TWOCONFIRM_001");
    httpConn.setRequestProperty("apiVersion", "1.0.0");
    httpConn.setRequestProperty("format", "http+json");
    httpConn.setRequestProperty("accessToken", "0500dcc4-daf7-4527-873d-8b596cb1bfa8");
    // 连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
    httpConn.connect();
    // 建立输入流，向指向的URL传入参数
    DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
    dos.writeBytes(param);
    dos.flush();
    dos.close();
    // 获得响应状态
    int resultCode = httpConn.getResponseCode();
    if (HttpURLConnection.HTTP_OK == resultCode) {
      StringBuffer sb = new StringBuffer();
      String readLine = new String();
      BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
      while ((readLine = responseReader.readLine()) != null) {
        sb.append(readLine).append("\n");
      }
      responseReader.close();
      System.out.println(sb.toString());
    }
  }
}
