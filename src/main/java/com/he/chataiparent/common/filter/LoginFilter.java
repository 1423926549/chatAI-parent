package com.he.chataiparent.common.filter;

import com.alibaba.fastjson.JSON;
import com.he.chataiparent.common.auth.AuthContextHolder;
import com.he.chataiparent.common.result.Result;
import com.he.chataiparent.model.vo.UserInfoVO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;


@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {
    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_PATTERN = new AntPathMatcher();

    /**
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        // 定义不需要处理的请求路径
        String[] urls = new String[]{
                "/user/login",
                "/user/logout",
                "/user/register",
                "/user/isLogin",
        };

        // 2. 判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        // 3. 如果不需要处理
        if (check) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getSession().getAttribute("userId") != null) {
            Long userId = (Long) request.getSession().getAttribute("userId");
            UserInfoVO userInfo = (UserInfoVO) request.getSession().getAttribute("userInfo");

            AuthContextHolder.setUserId(userId);
            AuthContextHolder.setUserInfo(userInfo);

            filterChain.doFilter(request, response);
        }

        response.getWriter().write(JSON.toJSONString(Result.fail("NOLOGIN")));
        return;
    }

    /**
     * 路径匹配，判断本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_PATTERN.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
