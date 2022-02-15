package com.example.BioBank.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.stereotype.Component;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

@Component
public class HTTPRequestUtil {
    public String sendPost(String urlParam,JSONObject jsonObject){
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(urlParam);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);

        RequestEntity se = new StringRequestEntity(SerialUtil.toJsonStr(jsonObject));
        postMethod.setRequestEntity(se);

        postMethod.addRequestHeader("X-Bmob-Application-Id","37449b43508f392a2a67166cd97d5334");
        postMethod.addRequestHeader("X-Bmob-REST-API-Key","b53e7354418986ef64d7e8b64a0174b6");
        postMethod.addRequestHeader("Content-Type", "application/json");

        String result = "{}";
        try {
            httpClient.executeMethod(postMethod);
            result = postMethod.getResponseBodyAsString();
            postMethod.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String sendGet(String urlParam) throws IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建GET请求方法实例对象
        GetMethod getMethod = new GetMethod(urlParam);
        // 设置post请求超时时间
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod.addRequestHeader("Content-Type", "application/json");

        httpClient.executeMethod(getMethod);

        String result = getMethod.getResponseBodyAsString();
        getMethod.releaseConnection();
        return result;
    }
}