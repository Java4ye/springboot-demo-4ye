package com.java4ye.demo.event;

import com.java4ye.demo.dto.ReaderFeedbackDTO;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 小伙伴反馈事件
 *
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Getter
public class ReaderFeedbackEvent extends ApplicationEvent {

    private ReaderFeedbackDTO feedbackDTO;

    public ReaderFeedbackEvent(Object source, ReaderFeedbackDTO feedbackDTO) {
        super(source);
        this.feedbackDTO = feedbackDTO;
    }

}
