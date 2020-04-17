package com.zxl.creditcard.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import javax.net.ssl.*;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequest {
    //忽略证书的认证
    private static void SkipCertificateValidation() throws Exception {
        // 创建一个不验证证书链的信任管理器
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    /**
     * 请求方式
     *
     * @param urlStr 接口请求链接
     */
    public static JSONObject postRequestWithAuth(String urlStr, Map<String, Object> params) throws Exception {
        HashMap res = new HashMap<>();
        SkipCertificateValidation();//跳过证书认证
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDefaultUseCaches(false);//post请求不需要缓存
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

        //设置请求要加的参数
        SetRequestValue(con.getOutputStream(), params);

        con.setConnectTimeout(5000);
        int code = con.getResponseCode();
        if (code != 200) {
            System.out.println("接口发生未处理异常，请求状态码：" + code);
            return null;
            //con.getErrorStream()  可获得出错后的返回流
        }
        //接收请求参数
        InputStreamReader reader = new InputStreamReader(con.getInputStream(), "GB2312");
        BufferedReader buffer = new BufferedReader(reader);
        String str = buffer.readLine();
        JSONObject object = new JSONObject(str);
        return object;
    }

    /**
     * 解决gson默认将int转换为double
     */
    public HashMap<String, Object> gsonToMap(String strJson) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(
                        new TypeToken<HashMap<String, Object>>() {
                        }.getType(),
                        new JsonDeserializer<HashMap<String, Object>>() {
                            @Override
                            public HashMap<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                JsonObject jsonObject = json.getAsJsonObject();
                                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                                for (Map.Entry<String, JsonElement> entry : entrySet) {
                                    hashMap.put(entry.getKey(), entry.getValue());
                                }
                                return hashMap;
                            }
                        }).create();

        return gson.fromJson(strJson, new TypeToken<HashMap<String, Object>>() {}.getType());
    }

    //将参数拼接起来
    private static void SetRequestValue(OutputStream outputStream, Map<String, Object> params) throws IOException {
        if (params != null && params.size() > 0) {
            DataOutputStream out = new DataOutputStream(outputStream);
            StringBuilder requestString = new StringBuilder();
            for (String key : params.keySet()) {
                requestString.append(key.trim()).append("=").append(URLEncoder.encode(params.get(key).toString(), "UTF-8").trim()).append("&");
            }
            System.out.println("原始长度：" + requestString);
            System.out.println("需要的字符串：" + requestString.substring(0, requestString.length() - 1));
            out.writeBytes(requestString.toString());
            out.flush();
            out.close();
        }
    }

}
