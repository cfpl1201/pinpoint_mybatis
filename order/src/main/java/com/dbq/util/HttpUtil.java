package com.dbq.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @author dbq
 * @date 2021/4/18 22:39
 */
public class HttpUtil {
    private static Log log = LogFactory.getLog(HttpUtil.class);

    public static void doPost(String url) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(500).setConnectionRequestTimeout(100)
                .setSocketTimeout(500).build();
        httpPost.setConfig(requestConfig);
        StringEntity entity = new StringEntity("jsonObject.toJSONString()", StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        try {
            HttpResponse response = client.execute(httpPost);
        } catch (Exception e) {
            System.out.println("-----------> http error");
        }
    }
}
