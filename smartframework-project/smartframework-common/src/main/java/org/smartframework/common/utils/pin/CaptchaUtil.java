/**
 * Created by Tiro on 2020/08/21.
 */
package org.smartframework.common.utils.pin;

import org.smartframework.common.utils.ImageUtil;
import org.smartframework.common.utils.RandomUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 类描述：验证码工具类 <br>
 *
 * @Description: 验证码工具类
 * @Author: Tiro
 * @Date: 2020/8/21 16:00
 */
public abstract class CaptchaUtil {

    /**
     * RGB颜色最大表示值
     */
    private static final int MAX_RGB_VAL = 255;

    /**
     * 写出二维码
     *
     * @param code
     * @param w
     * @param h
     * @param line
     * @param output
     * @throws IOException
     */
    public static void writeCode(String code, int w, int h, int line, OutputStream output) throws IOException {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        drawBackground(graphics, w, h, line);
        drawCharacter(graphics, code);
        ImageUtil.write(image, "JPEG", output);
    }


    /**
     * 绘制背景和干扰线
     *
     * @param graphics
     * @param w        宽
     * @param h        高
     * @param line     干扰线数量
     */
    private static void drawBackground(Graphics graphics, int w, int h, int line) {
        graphics.setColor(CaptchaUtil.randomColor(220, 250));
        graphics.fillRect(0, 0, w, h);
        for (int i = 0; i < line; i++) {
            graphics.setColor(CaptchaUtil.randomColor(40, 150));
            int x = RandomUtil.nextInt(w);
            int y = RandomUtil.nextInt(h);
            int x1 = RandomUtil.nextInt(w);
            int y1 = RandomUtil.nextInt(h);
            graphics.drawLine(x, y, x1, y1);
        }
    }


    /**
     * 绘制字符串
     *
     * @param graphics
     * @return
     */
    private static void drawCharacter(Graphics graphics, String code) {
        int fc = 50;
        int bc = 150;
        int size = 26;
        for (int i = 0; i < code.length(); i++) {
            char r = code.charAt(i);
            graphics.setColor(CaptchaUtil.randomColor(fc, bc));
            graphics.setFont(CaptchaUtil.arialBold(size));
            graphics.drawString(String.valueOf(r), 15 * i + 5, 19 + RandomUtil.nextInt(8));
        }
    }


    /**
     * 绘制颜色
     *
     * @param fc
     * @param bc
     * @return
     */
    private static Color randomColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        if (f > MAX_RGB_VAL) {
            f = MAX_RGB_VAL;
        }
        if (b > MAX_RGB_VAL) {
            b = MAX_RGB_VAL;
        }
        int abs = Math.abs(b - f);
        return new Color(f + RandomUtil.nextInt(abs),
                f + RandomUtil.nextInt(abs),
                f + RandomUtil.nextInt(abs));
    }


    /**
     * 获取指定尺寸的粗宋体
     *
     * @return
     */
    private static Font arialBold(int size) {
        return new Font("Arial", Font.BOLD, size);
    }

}
