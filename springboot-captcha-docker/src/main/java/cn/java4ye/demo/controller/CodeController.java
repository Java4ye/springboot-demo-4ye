package demo.controller;//package cn.java4ye.demo.controller;
//
//import com.pig4cloud.captcha.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//
//@RestController
//@RequestMapping("/code")
//public class CodeController {
//
//    /**
//     * 生成验证码
//     *
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/math")
//    public void getCode(HttpServletResponse response) throws Exception {
//
//        ServletOutputStream outputStream = response.getOutputStream();
//
////        算术验证码 数字加减乘除. 建议2位运算就行:captcha.setLen(2);
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 40);
//
//        // 几位数运算，默认是两位
//        captcha.setLen(2);
//        // 获取运算的结果
//        String result = captcha.text();
//        System.out.println(result);
//        captcha.out(outputStream);
//    }
//
//    @GetMapping("/cn")
//    public void getCode2(HttpServletResponse response) throws Exception {
//
//        ServletOutputStream outputStream = response.getOutputStream();
//
//        ChineseCaptcha captcha = new ChineseCaptcha(120, 40);
//
//        // 获取运算的结果
//        String result = captcha.text();
//        System.out.println(result);
//        captcha.out(outputStream);
//    }
//
//    @GetMapping("/en")
//    public void getCode3(HttpServletResponse response) throws Exception {
//
//        ServletOutputStream outputStream = response.getOutputStream();
//
//        SpecCaptcha captcha = new SpecCaptcha(120, 40);
//
//        // 获取运算的结果
//        String result = captcha.text();
//        System.out.println(result);
//        captcha.out(outputStream);
//    }
//
//    @GetMapping("/en/gif")
//    public void getCode4(HttpServletResponse response) throws Exception {
//
//        ServletOutputStream outputStream = response.getOutputStream();
//
//        GifCaptcha captcha = new GifCaptcha(120, 40);
//
//        // 获取运算的结果
//        String result = captcha.text();
//        System.out.println(result);
//        captcha.out(outputStream);
//    }
//
//    @GetMapping("/cn/gif")
//    public void getCode5(HttpServletResponse response) throws Exception {
//
//        ServletOutputStream outputStream = response.getOutputStream();
//
//        // 中文动态验证码
//        ChineseGifCaptcha captcha = new ChineseGifCaptcha(120, 40);
//
//        // 获取运算的结果
//        String result = captcha.text();
//        System.out.println(result);
//        captcha.out(outputStream);
//    }
//
//
//
//}
//
