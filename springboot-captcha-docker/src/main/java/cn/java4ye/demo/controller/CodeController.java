package cn.java4ye.demo.controller;

import cn.java4ye.demo.DemoApplication;
import cn.java4ye.demo.common.exception.DownloadException;
import com.pig4cloud.captcha.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.URL;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@RestController
@RequestMapping("/code")
public class CodeController {

    /**
     * 生成验证码
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/math")
    public void getCode(HttpServletResponse response) throws Exception {

        ServletOutputStream outputStream = response.getOutputStream();

//        算术验证码 数字加减乘除. 建议2位运算就行:captcha.setLen(2);
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 40);

        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        System.out.println(result);
        captcha.out(outputStream);
    }

    @GetMapping("/cn")
    public void getCode2(HttpServletResponse response) throws Exception {

        ServletOutputStream outputStream = response.getOutputStream();

        ChineseCaptcha captcha = new ChineseCaptcha(120, 40);

        // 获取运算的结果
        String result = captcha.text();
        System.out.println(result);
        captcha.out(outputStream);
    }


    /**
     * 有 bug ，自行修改~
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response) throws Exception {
        String resourcePath = "/java4ye.txt";
        URL resource = DemoApplication.class.getResource(resourcePath);
        String path = resource.getPath().replace("%20", " ");
        try (ServletOutputStream outputStream = response.getOutputStream();
             FileInputStream fileInputStream = new FileInputStream(path)) {


            byte[] bytes = new byte[8192];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }

            String fileName = "java4ye.txt";
//            response.setHeader("content-type", "application/octet-stream;charset=UTF-8");
//            response.setContentType("application/octet-stream");
//            response.setHeader("Access-Control-Expose-Headers", "File-Name");
//            response.setHeader("File-Name", fileName);

            int i = 1 / 0;

            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            outputStream.write(baos.toByteArray());

        } catch (Exception e) {
            throw new DownloadException(e);
        }

    }


    @GetMapping("/en")
    public void getCode3(HttpServletResponse response) throws Exception {

        ServletOutputStream outputStream = response.getOutputStream();

        SpecCaptcha captcha = new SpecCaptcha(120, 40);

        // 获取运算的结果
        String result = captcha.text();
        System.out.println(result);
        captcha.out(outputStream);
    }

    @GetMapping("/en/gif")
    public void getCode4(HttpServletResponse response) throws Exception {

        ServletOutputStream outputStream = response.getOutputStream();

        GifCaptcha captcha = new GifCaptcha(120, 40);

        // 获取运算的结果
        String result = captcha.text();
        System.out.println(result);
        captcha.out(outputStream);
    }

    @GetMapping("/cn/gif")
    public void getCode5(HttpServletResponse response) throws Exception {

        ServletOutputStream outputStream = response.getOutputStream();

        // 中文动态验证码
        ChineseGifCaptcha captcha = new ChineseGifCaptcha(120, 40);

        // 获取运算的结果
        String result = captcha.text();
        System.out.println(result);
        captcha.out(outputStream);
    }


}

