package site.amcu.amcuweb.validate.image;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import site.amcu.amcuweb.validate.impl.AbstractValidateCodeProcessor;

import javax.imageio.ImageIO;

/**
 * @Description:    图片验证码的处理机制实现
 *                  由于图片验证码的发送行为较为单一,故不用实现sender
 * @Author: Ben-Zheng
 * @Date: 2018/10/28 9:50
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        ImageIO.write(imageCode.getImage(),
                "JPEG", /** 图片格式 */
                request.getResponse().getOutputStream());
    }

}
