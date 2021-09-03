package com.zealcoder.shiroJWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zealcoder.exception.BizException;
import com.zealcoder.exception.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : zwy
 *  jwt过滤器，作为shiro的过滤器，对请求进行拦截并处理
    跨域配置不在这里配了，我在另外的配置类进行配置了，这里把重心放在验证上
 */
@Slf4j
@Component
public class JwtFilter extends BasicHttpAuthenticationFilter{

    /**
     * 进行token的验证
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        //在请求头中获取token
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization"); //前端命名Authorization
        //token不存在
        if(token == null || "".equals(token)){
            out(response,ResultBody.error("-1","token不存在"));
            return false;
        }
        //token存在，进行验证
        JwtToken jwtToken = new JwtToken(token);
        try {
            SecurityUtils.getSubject().login(jwtToken);  //通过subject，提交给myRealm进行登录验证
            return true;
        } catch (ExpiredCredentialsException e){
            out(response,ResultBody.error("-1","token过期"));
            return false;
        } catch (UnknownAccountException e){
            out(response,ResultBody.error("-1","无效token"));
            return false;
        }
    }
    /**
     * json形式返回结果token验证失败信息，无需转发
     */
    private void out(ServletResponse response, ResultBody res) throws IOException {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        ObjectMapper mapper = new ObjectMapper();
        String jsonRes = mapper.writeValueAsString(res);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.getOutputStream().write(jsonRes.getBytes());
    }
    /**
     * 过滤器拦截请求的入口方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return executeLogin(request, response);  //token验证
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * isAccessAllowed()方法返回false，即认证不通过时进入onAccessDenied方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
       return true;
    }

}

