package com.java4ye.demo.event;

import com.java4ye.demo.uitls.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * 项目启动时打印logo
 *
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Component
@Slf4j
public class Java4yeDemoLogoEventListener {

    private String banner=" _____                              __ __                      \n" +
            "/\\___ \\                            /\\ \\\\ \\                     \n" +
            "\\/__/\\ \\     __     __  __     __  \\ \\ \\\\ \\    __  __     __   \n" +
            "   _\\ \\ \\  /'__`\\  /\\ \\/\\ \\  /'__`\\ \\ \\ \\\\ \\_ /\\ \\/\\ \\  /'__`\\ \n" +
            "  /\\ \\_\\ \\/\\ \\L\\.\\_\\ \\ \\_/ |/\\ \\L\\.\\_\\ \\__ ,__\\ \\ \\_\\ \\/\\  __/ \n" +
            "  \\ \\____/\\ \\__/.\\_\\\\ \\___/ \\ \\__/.\\_\\\\/_/\\_\\_/\\/`____ \\ \\____\\\n" +
            "   \\/___/  \\/__/\\/_/ \\/__/   \\/__/\\/_/   \\/_/   `/___/> \\/____/\n" +
            "                                                   /\\___/      \n" +
            "                                                   \\/__/     ";

    @EventListener(ApplicationStartedEvent.class)
    public void sendSms() {
        System.out.println(banner);
        System.out.println(AnsiOutput.toString(AnsiColor.GREEN, "奇怪的相遇 哈哈哈 喜欢的话麻烦支持下博主呀~", AnsiColor.DEFAULT));
    }
}
