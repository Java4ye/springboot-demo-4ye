package com.java4ye.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 小伙伴反馈信息 DTO
 *
 * 读者在什么时刻 喜欢/不喜欢 你的文章，文章链接,评论内容
 *
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Data
public class ReaderFeedbackDTO {

    /**
     * 读者
     */
    private String userName;

    /**
     * 点赞 👍 或 踩 👎
     */
    private Boolean like;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * 互动时间
     */
    private String time;

    /**
     * 文章名字
     */
    private String articleName;

    /**
     * 文章链接
     */
    private String articleLink;


}
