package com.java4ye.demo.event;

import com.java4ye.demo.uitls.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * 这里选择 www.smschinese.com.cn 这个平台，有 5 条免费的测试下
 * 当反馈信息有点赞时才发送短信通知😄
 *
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */

@Slf4j
@Component
@Order(2)
public class SendSmsListener {

    @EventListener(condition = "#feedbackEvent.feedbackDTO.like==true")
    public void sendSms(ReaderFeedbackEvent feedbackEvent) {
        log.info("[4ye] 开始发送短信");
        SmsUtil.sendSMS(
                "Java4ye",
                "改成自己的",
                "改成自己的",
                "4ye今天又是元气满满的一天呀!,有小伙伴喜欢你的文章啦~,快去看看叭!!",
                "utf8"
        );
        log.info("[4ye] 短信发送结束");
    }
}
