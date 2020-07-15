package cn.prosayj.authentication.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 图片验证码
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-10 上午 11:10
 * @since 1.0.0
 */
@Controller
public class CaptchaController {
    @Autowired
    private Producer captchaProducer;


    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        String capText = captchaProducer.createText();
        request.getSession().setAttribute("captcha", capText);
        BufferedImage bufferedImage = captchaProducer.createImage(capText);
        try (ServletOutputStream out = response.getOutputStream();) {
            ImageIO.write(bufferedImage, "jpg", out);
            out.flush();
        }
    }

}
