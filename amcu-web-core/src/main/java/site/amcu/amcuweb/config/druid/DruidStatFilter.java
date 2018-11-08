package site.amcu.amcuweb.config.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @Description:
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 15:36
 */
@WebFilter(
        filterName = "druidWebStatFilter",
        urlPatterns = {"/*"},
        initParams = {
                /* 配置本过滤器放行的请求后缀 */
                @WebInitParam(name="exclusions", value = "*.js,*.jpg,*.png,*.gif,*.ico,*.css,/druid/*")
        }
)
public class DruidStatFilter extends WebStatFilter {

}
