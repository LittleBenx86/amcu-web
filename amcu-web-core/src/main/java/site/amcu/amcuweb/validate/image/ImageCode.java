package site.amcu.amcuweb.validate.image;

import site.amcu.amcuweb.validate.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Description:    图片验证码
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 20:58
 */
public class ImageCode extends ValidateCode {
    /** 序列化uid */
    private static final long serialVersionUID = -8600703576779914912L;

    /** 验证码的背景图 */
    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn){
        super(code, expireIn);
        this.image = image;
    }

    /** 此构造器也较为少用 */
    public ImageCode(String code, LocalDateTime expireTime, BufferedImage image) {
        super(code, expireTime);
        this.image = image;
    }

    /***************** setter & getter 是为了自动注入 *********************/
    
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
