package com.java4ye.demo.uitls;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;


/**
 * 短信发送工具类  SMS发送
 *
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */

@Slf4j
public class SmsUtil {

    private SmsUtil() {
    }

    private static final String GBK_SMS_URL = "http://gbk.api.smschinese.cn";
    private static final String UTF8_SMS_URL = "http://utf8.api.smschinese.cn/";
    private static final String UTF8 = "utf8";
    private static final String GBK = "gbk";


    public static void sendSMS(String uid, String key, String smsMob, String smsText, String charset) {

        String url = GBK_SMS_URL;
        String charsetName = GBK;

        if (UTF8.equals(charset)) {
            url = UTF8_SMS_URL;
            charsetName = UTF8;
        }

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);

        post.addRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=" + charsetName);// 在头文件中设置转码

        //设置参数
        NameValuePair[] data = {
                new NameValuePair("Uid", uid),
                new NameValuePair("Key", key),
                new NameValuePair("smsMob", smsMob),
                new NameValuePair("smsText", smsText)
        };

        post.setRequestBody(data);

        try {
            client.executeMethod(post);
        } catch (Exception e) {
            log.error(" {} ", e.getMessage());
        }


        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        log.info("statusCode:" + statusCode);
//        for (Header h : headers) {
//            log.info(h.toString());
//        }

        String result = "";
        try {
            result = new String(post.getResponseBodyAsString().getBytes(charsetName));
        } catch (Exception e) {
            log.error(" {} ", e.getMessage());
        }

        post.releaseConnection();

        int code = Integer.parseInt(result);
        if (code > 0) {
            log.info("[4ye] SMS 发送成功,已经成功发送 {} 条啦", code);
        } else {
            log.warn("[4ye] SMS 发送失败,code: {}", code);
        }
    }

}
