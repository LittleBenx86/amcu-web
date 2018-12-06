package site.amcu.amcuweb.social;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description:    社交账号绑定实现
 *                  从第三方登录的页面跳回,需要返回页面
 * @Author: Ben-Zheng
 * @Date: 2018/11/09 19:37
 */
public class SocialBindingConnectedView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        response.setContentType("text/html;charset=UTF-8");

        if(null == model.get("connections")) {
            response.getWriter().write("<h3>解绑成功</h3><br><a href='/usr-center.html?type=oauth2-info'>点击此处返回</a>");
        } else {
            response.getWriter().write("<h3>绑定成功</h3><br><a href='/usr-center.html?type=oauth2-info'>点击此处返回</a>");
        }

    }

}
