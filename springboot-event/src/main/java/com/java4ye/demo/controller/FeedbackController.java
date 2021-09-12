package com.java4ye.demo.controller;

import com.java4ye.demo.dto.ReaderFeedbackDTO;
import com.java4ye.demo.event.ReaderFeedbackEvent;
import com.java4ye.demo.event.ReaderFeedbackEventPublisher;
import com.java4ye.demo.uitls.DateTimeUtil;
import com.java4ye.demo.uitls.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@RestController
@Slf4j
@RequestMapping("feedback")
public class FeedbackController {

    @Autowired
    private ReaderFeedbackEventPublisher rfEventPublisher;

    @Autowired
    private  ApplicationContext applicationContext;

    @PostMapping("/reader")
    public void hello(HttpServletRequest request,@RequestBody ReaderFeedbackDTO readerFeedbackDTO) {
        IPUtil.logIpAddr(request);
        readerFeedbackDTO.setTime(DateTimeUtil.getTimeNow(null));
        log.info("{}", readerFeedbackDTO);
        // 方式 1
//        rfEventPublisher.publishFeedbackEvent(readerFeedbackDTO);
        // 方式 1
        applicationContext.publishEvent(new ReaderFeedbackEvent(this,readerFeedbackDTO));

    }

}
