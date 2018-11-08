package site.amcu.amcuweb.properties;

/**
 * @Description:    图形验证码的参数配置类
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 21:29
 */
public class ImageCodeProperties extends BasicValidateCodeProperties {

    /** 图片的长度 */
    private int width = 200;

    /** 图片的宽度 */
    private int height = 50;

    /**
     * 由于基础的验证码配置类的基础长度是6
     * 此处配置解决验证码长度不一致的问题
     */
    public ImageCodeProperties() {
        super.setLength(4);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
