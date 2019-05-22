package com.ymm.ymmtvportal.filter;

import com.ymm.ymmtvcommon.pojo.UserLogin;
import com.ymm.ymmtvcommon.pojo.Userinfo;
import com.ymm.ymmtvportal.dao.UserDao;
import com.ymm.ymmtvportal.dao.UserinfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

//用户权限拦截器
@Component
public class AutoLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserinfoDao userinfoDao;

    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object object = request.getSession().getAttribute("userinfo");
        if (object == null) {
            Cookie[] cookies = request.getCookies();//获得cookies
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        String token = cookie.getValue();
                        UserLogin userLogin = new UserLogin();
                        userLogin.setToken(token);
                        List<UserLogin> select = userDao.select(userLogin);
                        if (!ListUtils.isEmpty(select)) {
                            UserLogin user = select.get(0);
                            Userinfo userinfo = userinfoDao.selectByPrimaryKey(user.getLoginAccount());
                            userinfo.setLastLoginTime(user.getLastLogintime());
                            userinfo.setEmail(user.getEmail());
                            userinfo.setCreateTime(user.getCreateTime());
                            request.getSession().setAttribute("userinfo", userinfo);
                        }
                    }
                }

            }
        }

        return true;    //如果session里有user，表示该用户已经登陆，放行，用户即可继续调用自己需要的接口
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
