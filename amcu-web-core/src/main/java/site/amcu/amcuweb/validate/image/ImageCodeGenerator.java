package site.amcu.amcuweb.validate.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.validate.ValidateCode;
import site.amcu.amcuweb.validate.ValidateCodeGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Description:    图片验证码生成器(实现类)
 *                  图片验证码的生成业务实现类较多,并不适合直接配置为@Component
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 21:14
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;//通过该配置获取参数生成图片

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        /** 图片的宽度 */
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), 	//可从请求中获取配置的值
                "width", 													//名称为width
                securityProperties.getCode().getImage().getWidth());			//如果请求中不含有这个key，则使用默认的值
        /** 图片的高度 */
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), 	//可从请求中获取配置的值
                "height", 												//名称为height
                securityProperties.getCode().getImage().getHeight());			//如果请求中不含有这个key，则使用默认的值

        /** 在内存区域开拓图片的缓存空间 */
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        /** 创建图片 */
        Graphics graphics = bufferedImage.getGraphics();
        /** 生成随机的颜色点干扰 */
        graphics.setColor(this.getRandColor(200, 250));
        graphics.fillRect(0, 0, width, height);

        /**生成随机的颜色方块干扰*/
        graphics.setColor(Color.WHITE);
        graphics.drawRect(0, 0, width-1, height-1);

        /** 将图片转为2d样式 */
        Graphics2D graphics2d = (Graphics2D) graphics;
        /** 设置字体 */
        graphics2d.setFont(new Font("方正幼圆", Font.BOLD, 20));

        String words = "12AaBbCcDdEeFfGg3Hh4Ii5J6j7KkLl8MmN9n0OoPpQRSTUqrstuvVWXYZwxyz";

        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        int x = 10;
        for (int i = 0; i < securityProperties.getCode().getImage().getLength(); i++) {
            graphics2d.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            int angle = random.nextInt(60) - 30;
            double theta = angle * Math.PI / 180;
            int index = random.nextInt(words.length());
            char c = words.charAt(index);
            sb.append(c);
            graphics2d.rotate(theta,x,20);
            graphics2d.drawString(String.valueOf(c), x, 20);
            graphics2d.rotate(-theta, x, 20);
            x += 30;
        }

        /**生成随机颜色的干扰线条*/
        graphics.setColor(this.getRandColor(160, 200));
        int x1;
        int x2;
        int y1;
        int y2;
        for (int i = 0; i < 30; i++) {
            x1 = random.nextInt(width);
            x2 = random.nextInt(12);
            y1 = random.nextInt(height);
            y2 = random.nextInt(12);
            graphics.drawLine(x1, y1, x1 + x2, x2 + y2);
        }
        /** 生成图片并输出 */
        graphics.dispose();

        return new ImageCode(bufferedImage, 							//验证码背景图
                sb.toString(), 							                //随机的字符产
                securityProperties.getCode().getImage().getExpireIn());	//过期时间
    }

    /**
     * 随机颜色
     *
     * @param fc
     *            int
     * @param bc
     *            int
     * @return Color
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

}
