package com.aoxiu.aoxiuApp.controller;

import com.aoxiu.AoxiuConstant;
import com.aoxiu.ComRet;
import com.aoxiu.meta.photo.Company;
import com.aoxiu.meta.photo.Photographers;
import com.aoxiu.service.photo.LoginService;
import com.aoxiu.service.photo.RegisterUserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by panchao on 15/4/23.
 */
@Controller
@RequestMapping("/users")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private LoginService loginService;
    @Resource
    private RegisterUserService registerUserService;
    @RequestMapping("/login")
    public String login(HttpServletRequest request,HttpServletResponse response){
       String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        String type = request.getParameter("user-type");
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)
                || StringUtils.isEmpty(type)){
            logger.warn("[login] parameter is wrong");
            return "redirect:../index.html";
        }
        Object object = loginService.examinUser(userName,passWord,type);
        if(object != null){
        if(type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR)){
            String sessionId = request.getSession().getId();
            request.setAttribute("photographers_id",((Photographers)object).getId());
            request.setAttribute("user_name",((Photographers)object).getRealName());
        }else {
             request.setAttribute("company_id",((Company)object).getCompanyId());
        }
            request.setAttribute("user_name",userName);
            return "photo_new_order";
        }else{
            return "redirect:../index.html";
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "/index";

    }

    @RequestMapping("/goRegister.do")
    public String goRegister(HttpServletRequest request,HttpServletResponse response){
        return "/companyRegister";
    }

    @ResponseBody
    @RequestMapping("/valid.do")
    public Object valid(HttpServletRequest request,HttpServletResponse response){
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String,Object>  resultMap = new HashMap<>();
        if(StringUtils.isEmpty(userName)){
            resultMap.put("usr_exists",false);
            return  resultMap;
        }
        if(StringUtils.isEmpty(password)){

        }else{
        if(loginService.examinUserExisit(userName, AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR)){
            Photographers photographers = (Photographers)loginService.examinUser(userName, password, AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR);
            if(photographers != null){
                resultMap.put("usr_exists",true);
                resultMap.put("pw",true);
            }else {
                resultMap.put("usr_exists",true);
                resultMap.put("pw",false);
            }
        }else{
            resultMap.put("usr_exists",false);
        }
        }
        return  resultMap;
    }


    @RequestMapping("/validReg.do")
    @ResponseBody
    public Object validReg(HttpServletRequest request,HttpServletResponse response){
        String userName = request.getParameter("username");
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("info","demo info");
        try{
            if(loginService.examinUserExisit(userName,AoxiuConstant.AOXIU_USER_COMPNAY)){
                resultMap.put("status","y");
            }else {
                resultMap.put("status","n");
            }
        }catch (Exception e){
            resultMap.put("status","n");
        }
        return resultMap;
    }
    @RequestMapping("/validPhoneNumber.do")
    @ResponseBody
    public Object validPhoneNumber(HttpServletRequest request,HttpServletResponse response){
        String phoneNumber = request.getParameter("phoneNumber");
        String type = request.getParameter("type");
        String getCode = request.getParameter("getCode");
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(type)
                || StringUtils.isEmpty(getCode)){
            logger.warn("[login] parameter is wrong");
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            resultMap.put(ComRet.retData,null);
            return resultMap;
        }
         try{

             if(loginService.examinUserPhoneNumber(phoneNumber)){
                 Cookie cookie = new Cookie("phoneExamine",phoneNumber);
                 cookie.setPath("/");
                 response.addCookie(cookie);
                 resultMap.put(ComRet.retCode,ComRet.SUCESS);
                 resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
                 request.getSession().setAttribute(phoneNumber,"1");
                 if(type.equals("selected")){
                     resultMap.put(ComRet.retData,"/pictures/");
                 }else{     //原片地址
                     resultMap.put(ComRet.retData,"/pictures/getUserContent?getCode=" + getCode + "&type=" + type);
                     return getJsonMvc(resultMap);
                 }
                 return  resultMap;
             }else{
                 resultMap.put(ComRet.retCode,ComRet.PHONE_NUMBER);
                 resultMap.put(ComRet.retDesc,ComRet.PHONE_NUMBER_DESC);
                 resultMap.put(ComRet.retData,"/validPhonerNumber.html");
                 return resultMap;
             }
         }catch (Exception e){
             logger.error("[validPhoneNumber] some thing  wrong " + e.getMessage());
             resultMap.put(ComRet.retCode,ComRet.PHONE_NUMBER);
             resultMap.put(ComRet.retDesc,ComRet.PHONE_NUMBER_DESC);
             resultMap.put(ComRet.retData,"/validPhonerNumber.html");
             return resultMap;
         }
    }

    private  String getJsonMvc(Map<String,Object> resultMap){
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        return jsonObject.toString();
    }

    @RequestMapping("/gotoPhoneVerify.do")
    public String gotoPhoneVerify(HttpServletRequest request){
        String getCode = request.getParameter("getCode");
        String type = request.getParameter("type");
        request.setAttribute("getCode",getCode);
        request.setAttribute("type",type);
        request.setAttribute("lastUpdated","2015-05-08 12:33:33");
        return "client_verify";
    }

    @RequestMapping("/gotoPhotoNewOrder.do")
    public String gotoPhotoNewOrder(HttpServletRequest request,HttpServletResponse response){
        String photographerId = request.getParameter("photographers_id");
        request.setAttribute("photographers_id",photographerId);
        return "photo_new_order";
    }

    @RequestMapping("/gotoPhotographerHome.do")
    public String gotoPhotographerHome(HttpServletRequest request){
        String photographerId = request.getParameter("photographers_id");
        request.setAttribute("photographers_id",photographerId);
        return "photo_new_order";
    }


    @RequestMapping("/register.do")
    public String  register(HttpServletRequest request,HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String tel = request.getParameter("tel");
        String wechat = request.getParameter("wechat");
        String email = request.getParameter("email");
        String qq = request.getParameter("qq");
        String examCode = request.getParameter("yzm");
        String type = request.getParameter("user-type");
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(province) ||StringUtils.isEmpty(city)
                || StringUtils.isEmpty(district) || StringUtils.isEmpty(tel)
                || StringUtils.isEmpty(examCode) || StringUtils.isEmpty(type)){
            logger.warn("[register] param wrong");
            return  "companyRegister";
        }
        try {
            if (password == null || password.length() <= 6) {

                logger.warn("[register] wrong pass word");
                return  "companyRegister";
            }
            if (!loginService.examinUserExisit(username, type)) {

                logger.warn("[register] userName has exisited");
                return  "companyRegister";
            }
            boolean flag =  false;
            if(type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_COMPNAY)){
                flag = registerUserService.registerCompanyUser(username, password, province, city, district, tel, wechat, qq, email);
                if(flag){
                    return "/index";
                }else{
                    return  "/companyRegister";
                }
            }else{
                return  "/companyRegister";
            }

        }catch (Exception e){
            logger.error("[register] register user error -> " + e.getMessage());
            return  "/companyRegister";
        }


    }


}
