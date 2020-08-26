package org.smartframework.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片工具类
 *
 * @author chonglou
 * @date 2019/7/1711:00
 */
public abstract class ImageUtil {

    /**
     * 读取图片
     *
     * @param imgPath 图片路径
     * @return
     * @throws IOException
     */
    public static BufferedImage read(String imgPath) throws IOException {
        return ImageIO.read(new File(imgPath));
    }


    /**
     * 写出图片到文件
     *
     * @param image  缓冲图片
     * @param format 图片格式，png、jpg
     * @param file   输出文件
     * @throws IOException
     */
    public static void write(RenderedImage image, String format, File file) throws IOException {
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format "
                    + format + " to " + file);
        }
    }

    /**
     * 写出图片到流
     *
     * @param image  缓冲图片
     * @param format 图片格式，png、jpg
     * @param output 输出流
     * @return
     * @throws IOException
     */
    public static void write(RenderedImage image, String format, OutputStream output) throws IOException {
        if (!ImageIO.write(image, format, output)) {
            throw new IOException("Could not write an image of format " + format + " to output stream");
        }
    }

    /**
     * 压缩图片
     *
     * @param src    源图片
     * @param width  压缩宽度
     * @param height 压缩高度
     */
    public static BufferedImage compress(Image src, int width, int height) {
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        // 绘制缩小后的图
        g.drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();
        return tag;
    }

    /**
     * 嵌入图片,居中
     *
     * @param source     底层图片
     * @param embedImage 嵌入图片
     * @param rimColor   边框颜色
     * @param rimSize    边框尺寸
     * @return
     */
    public static BufferedImage embedImage(BufferedImage source, BufferedImage embedImage, Color rimColor, Float rimSize) {
        if (null == source || null == embedImage) {
            return source;
        }
        int x = (source.getWidth() - embedImage.getWidth(null)) / 2;
        int y = (source.getHeight() - embedImage.getHeight(null)) / 2;
        return embedImage(source, embedImage, x, y, rimColor, rimSize);
    }


    /**
     * 嵌入图片
     *
     * @param source     底层图片
     * @param embedImage 嵌入图片
     * @param x          坐标x
     * @param y          坐标y
     * @param rimColor   边框颜色
     * @param rimSize    边框尺寸
     * @return
     */
    public static BufferedImage embedImage(BufferedImage source, BufferedImage embedImage,
                                           int x, int y, Color rimColor, Float rimSize) {
        if (null == source || null == embedImage) {
            return source;
        }
        int arw = 16;
        int arh = 16;
        Graphics2D graph = source.createGraphics();
        graph.setColor(rimColor);
        graph.drawImage(embedImage, x, y, embedImage.getWidth(), embedImage.getHeight(), null);
        Shape shape = new RoundRectangle2D.Float(x, y, embedImage.getWidth(), embedImage.getHeight(), arw, arh);
        graph.setStroke(new BasicStroke(rimSize));
        graph.draw(shape);
        graph.dispose();
        return source;
    }


    /**
     * 在下方绘制文字
     *
     * @param source    底层图片
     * @param content   文字内容
     * @param font      字体
     * @param fontColor 字体颜色
     * @param fontRate  字体比例，调节字体左右位置,0.9左右
     * @return
     * @throws Exception
     */
    public static BufferedImage drawToDown(BufferedImage source, String content, Font font, Color fontColor, double fontRate) throws Exception {
        int width = source.getWidth();
        int height = source.getHeight() + font.getSize();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graph = image.getGraphics();
        graph.setColor(Color.WHITE);// 设置笔刷白色
        graph.fillRect(0, 0, width, height);
        graph.drawImage(source, 0, 0, null);
        /*
         * 写字
         */
        graph.setFont(font);// 设置字体
        graph.setColor(fontColor);
        int x = (int) (width / 2 - fontRate * graph.getFontMetrics().stringWidth(content) / 2);
        int y = source.getHeight() + font.getSize() / 2;
        graph.drawString(content, x, y);
        graph.dispose();
        return image;
    }
}
