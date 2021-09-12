package com.java4ye.demo.event;

import com.java4ye.demo.dto.ReaderFeedbackDTO;
import com.java4ye.demo.event.ReaderFeedbackEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 *
 * 监听 ReaderFeedbackEvent 并发送邮件
 *
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Slf4j
@Component
@Order(1)
public class SendMailListener implements ApplicationListener<ReaderFeedbackEvent> {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender jms;

    @Override
    public void onApplicationEvent(ReaderFeedbackEvent readerFeedbackEvent) {
        ReaderFeedbackDTO feedbackDTO = readerFeedbackEvent.getFeedbackDTO();
        log.info("[4ye] 有小伙伴反馈信息啦,内容如下:");
        log.info("{}", feedbackDTO);
        sendMail(feedbackDTO.toString());
    }

    private void sendMail(String text) {
        try {
            log.info("[4ye] 开始发送邮件");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo("2847730230@qq.com");
            message.setSubject("来自小伙伴的互动");
            message.setText(text);
            jms.send(message);
            log.info("[4ye] 邮件发送成功");
        } catch (Exception e) {
            log.error(" {} ", e.getMessage());
        }
    }

}
