package com.zerox.trd.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zerox.trd.common.HttpClientFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * GFW web page (html)  bridge
 *
 * Created by Sam on 10/5/16.
 */
@Controller
public class HtmlBridgeController {

    private static Logger logger = LogManager.getLogger(HtmlBridgeController.class);

    @RequestMapping(value = "/html", method = RequestMethod.POST)
    @ResponseBody
    public String pullHtmlContent(@RequestBody String requestContent) {

        String htmlContent;
        try {
            JSONObject reqJson = JSON.parseObject(requestContent);
            logger.info("Request json:" + reqJson);
            String url = reqJson.getString("url");
            htmlContent = Base64.encodeBase64String(fetchHttp(url).getBytes("utf-8"));
            logger.info("Response data length:" + htmlContent.length());
        } catch (UnsupportedEncodingException encodingException) {
            logger.error("Unsupported encoding error", encodingException);
            htmlContent = "Unsupported encoding error";
        } catch (JSONException fastJsonException) {
            logger.error("Parse json error", fastJsonException);
            htmlContent = "Parse json error";
        } catch (Exception e) {
            logger.error("System error", e);
            htmlContent = "System error";
        }
        return htmlContent;
    }

    private String fetchHttp(String url) throws IOException{
        CloseableHttpResponse response = HttpClientFactory.httpClient().execute(new HttpGet(url));
        return EntityUtils.toString(response.getEntity(), "utf-8");

    }
}
