package com.waychel.cloudcontroller;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * author: PengCheng
 * time: 2017/12/13 0013
 * desc:
 */

public class HttpManager {

    public static void getHttpRequest(String url, Map<String, String> params, Callback<String> callback) {
        OkHttpUtils
                .get()
                .url(url)
                .addHeader("Accept", "application/json")
                /*.addHeader("Authorization", TextUtils.isEmpty(UserInfoManager.getInstance().getUserInfoBean().getApiToken()) ? "" : UserInfoManager.getInstance().getUserInfoBean().getApiToken())
                .addHeader("X-App-Client", "android")
                .addHeader("X-Product-Version", Constant.X_PRODUCT_VERSION)*/
                .params(params == null ? new HashMap<String, String>() : params)
                .build()
                .connTimeOut(30_000L)
                .execute(callback);
    }

    public static void postHttpRequest(String url, Map<String, String> params, Callback<String> callback) {
        OkHttpUtils
                .post()
                .url(url)
                /*.addHeader("Authorization", TextUtils.isEmpty(UserInfoManager.getInstance().getUserInfoBean().getApiToken()) ? "" : UserInfoManager.getInstance().getUserInfoBean().getApiToken())
                .addHeader("Accept", "application/json")
                .addHeader("X-App-Client", "android")
                .addHeader("X-Product-Version", Constant.X_PRODUCT_VERSION)*/
                .params(params == null ? new HashMap<String, String>() : params)
                .build()
                .connTimeOut(30_000L)
                .execute(callback);
    }

    public static void postHttpJsonRequest(String url, String jsonStr, Callback<String> callback) {
        OkHttpUtils
                .postString()
                .url(url)
                /*.addHeader("Authorization", TextUtils.isEmpty(UserInfoManager.getInstance().getUserInfoBean().getApiToken()) ? "" : UserInfoManager.getInstance().getUserInfoBean().getApiToken())*/
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(jsonStr)
                .build()
                .connTimeOut(30_000L)
                .execute(callback);
    }

    public static void putRequest(String url, String body, Callback<String> callback) {
        OkHttpUtils
                .put()
                .url(url)
               /* .addHeader("Authorization", TextUtils.isEmpty(UserInfoManager.getInstance().getUserInfoBean().getApiToken()) ? "" : UserInfoManager.getInstance().getUserInfoBean().getApiToken())
                .addHeader("Accept", "application/json")
                .addHeader("X-App-Client", "android")
                .addHeader("X-Product-Version", Constant.X_PRODUCT_VERSION)*/
                .requestBody(RequestBody.create(null, body))
                .build()
                .connTimeOut(60_000L)
                .writeTimeOut(60_000L)
                .readTimeOut(60_000L)
                .execute(callback);


    }

    public static void deteleRequest(String url, String body, Callback<String> callback) {
        OkHttpUtils
                .delete()
                .url(url)
               /* .addHeader("Authorization", TextUtils.isEmpty(UserInfoManager.getInstance().getUserInfoBean().getApiToken()) ? "" : UserInfoManager.getInstance().getUserInfoBean().getApiToken())
                .addHeader("Accept", "application/json")
                .addHeader("X-App-Client", "android")
                .addHeader("X-Product-Version", Constant.X_PRODUCT_VERSION)*/
                .requestBody(RequestBody.create(null, body))
                .build()
                .connTimeOut(30_000L)
                .execute(callback);


    }

    public static void getHttpRequestHeader(String url, Map<String, String> params,
                                            String sign,String timestamp,String versionCode ,Callback<String> callback) {


        OkHttpUtils
                .get()
                .url(url)
                .addHeader("Accept", "application/json")
                /*.addHeader("X-App-Client", "android")
                .addHeader("X-Product-Version", Constant.X_PRODUCT_VERSION)
                .addHeader("msCode","CRM")
                .addHeader("timestamp",timestamp)
                .addHeader("appVersion", versionCode)
                .addHeader("token",TextUtils.isEmpty(UserInfoManager.getInstance().getUserInfoBean().getApiToken()) ?
                        "" : UserInfoManager.getInstance().getUserInfoBean().getApiToken().replace("Bearer ", ""))
                .addHeader("appId",Constant.APPID)*/
                .addHeader("sign",sign)
                .params(params == null ? new HashMap<String, String>() : params)
                .build()
                .connTimeOut(30_000L)
                .execute(callback);
    }


    public static void postHttpRequestHeader(String url, Map<String, String> params,
                                            String sign,String timestamp,String versionCode, Callback<String> callback) {
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("Accept", "application/json")
                /*.addHeader("X-App-Client", "android")
                .addHeader("X-Product-Version", Constant.X_PRODUCT_VERSION)
                .addHeader("msCode","CRM")
                .addHeader("timestamp",timestamp)
                .addHeader("appVersion", versionCode)
                .addHeader("token",TextUtils.isEmpty(UserInfoManager.getInstance().getUserInfoBean().getApiToken()) ?
                        "" : UserInfoManager.getInstance().getUserInfoBean().getApiToken().replace("Bearer ", ""))
                .addHeader("appId",Constant.APPID)*/
                .addHeader("sign",sign)
                .params(params == null ? new HashMap<String, String>() : params)
                .build()
                .connTimeOut(30_000L)
                .execute(callback);
    }

    /*public static String encryptSignByMD5WithKey(String key, Map<String, String> paramMap) {
        if(StringUtils.isEmpty(key) || paramMap == null) {
            return null;
        }

        String sign = null;
        try {
            SortedMap<String, String> sortedParamMap = new TreeMap<>();
            sortedParamMap.putAll(paramMap);

            StringBuilder sb = new StringBuilder();
            Iterator it = sortedParamMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String k = (String) entry.getKey();
                if("sign".equalsIgnoreCase(k)) {
                    continue;
                }
                String v = (String) entry.getValue();
                sb.append(k + "=" + v + "&");
            }
            sb.append("key=" + key);
            sign = MD5Utils.md5(sb.toString());
//            logger.info("签名原文-{}", sb.toString());
//            logger.info("签名-{}", sign);
        } catch (Exception e) {
            Log.e("sign 签名失败-{}", paramMap.toString());
        }

        return sign;
    }*/
}
