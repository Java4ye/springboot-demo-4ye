package com.java4ye.demo.event;

import com.java4ye.demo.dto.ReaderFeedbackDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * 事件发布
 *
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Component
@Slf4j
public class ReaderFeedbackEventPublisher implements ApplicationEventPublisherAware {


    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishFeedbackEvent(ReaderFeedbackDTO feedbackDTO) {
        log.info("[4ye] publish feedback event start ");
        applicationEventPublisher.publishEvent(new ReaderFeedbackEvent(this, feedbackDTO));
        log.info("[4ye] publish feedback event end ");
    }

}
