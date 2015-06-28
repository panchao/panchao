package com.aoxiu.aoxiuApp.Filter;


import com.aoxiu.ComRet;
import com.aoxiu.aoxiuApp.utils.CookieUtils;
import com.aoxiu.aoxiuApp.utils.StringMatchUtils;
import com.aoxiu.service.photo.LoginService;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/4/29.
 */
public class LoginFilter implements Filter {

    private List<String> filterUrls;
    private ApplicationContext applicationContext;
    private LoginService loginService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        filterUrls = (List<String>)applicationContext.getBean("filterUrls");
        loginService = (LoginService)applicationContext.getBean("loginService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUri = request.getRequestURI();
        String sessionId = CookieUtils.getAoxiuCookie(request);
        String loginUri = null;
        String loginUrl = null;
        loginUri = "users/login";
//        loginUrl = request.getScheme() + "://" + request.getServerName() + "/users/login?redirectURL="
//                + getGotoUrl(request);
        loginUrl = request.getScheme() + "://" + request.getServerName() + ":8080";


        if(StringMatchUtils.match("getUserContent",requestUri)){
            String flag = request.getParameter("flag");
            if(flag == null || !flag.equals("1")){

                String getCode = request.getParameter("getCode");
                String type = request.getParameter("type");
                String redirectUrl = request.getScheme() +"://" + request.getServerName() + ":8080/users/gotoPhoneVerify.do?getCode=" + getCode + "&type=" + type;
                request.setAttribute("getCode",getCode);
                request.setAttribute("type",type);
                response.sendRedirect(redirectUrl);
            }

        }

        if(!requestUri.equals("/")){
        for(int i = 0; i < filterUrls.size(); i++){
            if(StringMatchUtils.match(filterUrls.get(i),requestUri)){
                if(sessionId == null){     // 没有登陆
                    if("XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))){
                        Map<String, String> result = new HashMap<String, String>();
                        OutputStream os = response.getOutputStream();
                        result.put(ComRet.retCode,ComRet.NO_LOGIN);
                        result.put(ComRet.retDesc,ComRet.NO_LOGIN_DESC);
                        os.write(JSONObject.fromObject(result).toString().getBytes("UTF-8"));
                        response.setContentType("text/json; charset=UTF-8");
                        if (os != null) {
                            os.flush();
                            os.close();
                        }
                        return;
                    }
                    response.sendRedirect(loginUrl);   //跳转到登陆页
                }else {
                    String serverSessionId = request.getSession().getId();
                    if(!serverSessionId.equals(sessionId)){         //登陆失效
                        if("XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))){
                            Map<String, String> result = new HashMap<String, String>();
                            OutputStream os = response.getOutputStream();
                            result.put(ComRet.retCode,ComRet.NO_LOGIN);
                            result.put(ComRet.retDesc,ComRet.NO_LOGIN_DESC);
                            os.write(JSONObject.fromObject(result).toString().getBytes("UTF-8"));
                            response.setContentType("text/json; charset=UTF-8");
                            if (os != null) {
                                os.flush();
                                os.close();
                            }
                            return;
                        }
                        response.sendRedirect(loginUrl);   //跳转到登陆页
                    }
                }
            }
        }}

        chain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

    private String getGotoUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        // 获取所有参数
        StringBuffer paramsBuf = new StringBuffer("");
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String paramName = (String) e.nextElement();
            paramsBuf.append(paramName).append("=").append(request.getParameter(paramName)).append('&');
        }

        StringBuffer url = new StringBuffer(request.getRequestURL());
        if (paramsBuf != null && paramsBuf.length() > 0) {
            url.append("?").append(paramsBuf);
        }

        return URLEncoder.encode(url.toString(), "UTF-8");
    }

}
