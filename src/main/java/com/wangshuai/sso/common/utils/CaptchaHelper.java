package com.wangshuai.sso.common.utils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码缓存工具类
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:19
 */
public class CaptchaHelper {

    public static final String CACHE_CAPTCHA = "_captcha";

    public static void setInCache(final HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage image = new Captcha() {
            protected void setInCache(String captcha) {
                request.getSession().setAttribute(CACHE_CAPTCHA, captcha);
            }
        }.generate();

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        try {
            out.flush();
        }
        finally {
            out.close();
        }
    }

    public static boolean validate(HttpServletRequest request, String captcha) {
        String sessionCaptcha = request.getSession().getAttribute(CaptchaHelper.CACHE_CAPTCHA).toString();
        return sessionCaptcha != null && sessionCaptcha.equalsIgnoreCase(captcha);
    }
}
