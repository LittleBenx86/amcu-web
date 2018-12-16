package site.amcu.amcuweb.utils;



import org.apache.commons.lang.StringUtils;

import java.util.Random;

/**
 * @Description:    随机用户名生成工具
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 11:59
 */
public final class RandomUsernameGeneratorUtils {

    public static String generatorUsername(int len) {
        String name = "";
        Random r = new Random();

        for (int i = 0; i < len; i++) {
            String chOrNum = r.nextInt(2) % 2 == 0 ? "ch" : "num";
            if("ch".equalsIgnoreCase(chOrNum)) {
                int temp = r.nextInt(2) % 2 == 0 ? 65 : 97;
                name += (char)(r.nextInt(26) + temp);
            } else {
                name += String.valueOf(r.nextInt(10));
            }
        }
        name += Integer.toHexString(r.hashCode() + r.nextInt(60));
        return name;
    }

}
