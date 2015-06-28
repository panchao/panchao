package com.aoxiu.aoxiuApp.controller;

import com.aoxiu.AoxiuConstant;
import com.aoxiu.ComRet;
import com.aoxiu.common.PaginationInfo;
import com.aoxiu.meta.photo.Customer;
import com.aoxiu.service.photo.CustomerService;
import com.aoxiu.service.photo.QueryUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by panchao on 15/4/26.
 */
@Controller
@RequestMapping("/query")
public class QueryUserInfoCotroller {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private QueryUserInfoService queryUserInfoService;
    @Resource
    private CustomerService customerService;

    @RequestMapping("/phone.do")
    @ResponseBody
    public Object queryUserInfoByPhone(HttpServletRequest request,HttpServletResponse response){
        String phoneNumber = request.getParameter("phoneNumber");
        String type = request.getParameter("type");
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(type)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.warn("[queryUserInfoByPhone] wrong parameter");
            return resultMap;
        }
        Object object = queryUserInfoService.queryUserByPhoneNumber(phoneNumber,type);
        if(object != null){
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,object);
        }else{
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
        }
        return resultMap;
    }

    @RequestMapping("/userId.do")
    @ResponseBody
    public Object queryUserInfoByUserId(HttpServletRequest request,HttpServletResponse response){
        String userId = request.getParameter("userId");
        String type = request.getParameter("type");
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(type)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.warn("[queryUserInfoByUserId] wrong parameter");
            return resultMap;
        }
        Object object = queryUserInfoService.queryUserByUserId(userId, type);
        if(object != null){
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,object);
        }else{
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
        }
        return resultMap;
    }

    @RequestMapping("/userName.do")
    @ResponseBody
    public Object queryUserInfoByUserName(HttpServletRequest request,HttpServletResponse response){
        String userName = request.getParameter("userName");
        String type = request.getParameter("type");
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(type)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.warn("[queryUserInfoByUserName] wrong parameter");
            return resultMap;
        }
        Object object = queryUserInfoService.queryUserByUserId(userName, type);
        if(object != null){
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,object);
        }else{
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
        }
        return resultMap;
    }

    @RequestMapping("/getPhotographerUsers")
    @ResponseBody
    public Object getPhotographerUsers(HttpServletRequest request){
        String photographerId = request.getParameter("photographers_id");
        String pageNumberStr = request.getParameter("pageNum");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(photographerId)){
            logger.error("[getPhotographerUsers] wrong parameter");
            return "error";
        }
        try{
            int pageNumber = 0;
            if(pageNumberStr != null){
                pageNumber = Integer.valueOf(pageNumberStr);
            }else {
                pageNumber = 1;
            }
            PaginationInfo paginationInfo = null;
            paginationInfo = new PaginationInfo();
            List<Customer> customerList = customerService.getPhotographersUsers(photographerId,paginationInfo,pageNumber, AoxiuConstant.AOXIU_RECORD_PER_PAGE);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,customerList);
            return  resultMap;
        }catch (Exception e){
            logger.error("[getPhotographerUsers] get photographers user error -> " +e.getMessage());
            return "error";
        }
    }
}
