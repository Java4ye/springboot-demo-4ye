package com.java4ye.demo.uitls;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
public class DateTimeUtil {

    private DateTimeUtil() {
    }

    private static final DateTimeFormatter defaultDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public static String getTimeNow(DateTimeFormatter dateTimeFormatter) {
        if (dateTimeFormatter == null) {
            dateTimeFormatter = defaultDateTimeFormatter;
        }
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

}
