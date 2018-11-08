package site.amcu.amcuweb.config.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.io.Serializable;

/**
 * @Description:
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 15:39
 */
@WebServlet(
        urlPatterns = {"/druid/*"},
        initParams = {
                /** 白名单，如果不配置或者value为空，则会允许所有 */
                @WebInitParam(name="allow", value="127.0.0.1,192.0.0.1"),
                /** 黑名单，与白名单存在相同的ip地址的时候，阻止该ip的连接请求，优先于白名单 */
                @WebInitParam(name="deny", value="192.0.0.1"),
                /** 用户名 */
                @WebInitParam(name="loginUsername", value="root"),
                /** 密码 */
                @WebInitParam(name="loginPassword", value="root123456"),
                /** 禁止页面的reset all功能 */
                @WebInitParam(name="resetEnable", value="false")
        }
)
public class DruidStatViewServlet extends StatViewServlet implements Servlet {
    private static final long serialVersionUID = 1L;
}
