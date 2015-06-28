package com.aoxiu.aoxiuApp.controller;

import com.aoxiu.ComRet;
import com.aoxiu.meta.photo.Photographers;
import com.aoxiu.service.photo.CompanyOperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/4/26.
 * 婚庆公司
 */
@Controller
@RequestMapping("/web/interfaces/company")
public class CompanyOperatorController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CompanyOperatorService companyOperatorService;

    @RequestMapping("/add.do")
    public Object add(HttpServletRequest request,HttpServletResponse response){
        String userId = request.getParameter("userId");
        String companyId = request.getParameter("companyId");
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(companyId)){
            logger.error("[add] add user  wrong params");
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            return resultMap;
        }
        if(companyOperatorService.addUserToCompany(userId,companyId)){
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
        }else{
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            logger.error("[add] add user to company error: userId = " + userId + " companyId = " + companyId);
        }
        return  resultMap;
    }

    @RequestMapping("/delete.do")
    public Object delete(HttpServletRequest request,HttpServletResponse response){
        String userId = request.getParameter("userId");
        String companyId = request.getParameter("companyId");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(companyId)){
            logger.error("[add] add user  wrong params");
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            return resultMap;
        }
        if(companyOperatorService.deleteUserFromCompany(userId,companyId)){
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
        }else{
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            logger.error("[delete] delete user from company error: userId = " + userId + " companyId = " + companyId);
        }

        return resultMap;
    }

    @RequestMapping("/query.do")
    public Object queryPhotographers(HttpServletRequest request,HttpServletResponse response){
        String companyId = request.getParameter("companyId");
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(companyId)){
            logger.error("[queryPhotographers] query  photographers  wrong params");
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            return resultMap;
        }
        List<Photographers> photographerses = companyOperatorService.getPhotographers(companyId);
        resultMap.put(ComRet.retCode,ComRet.SUCESS);
        resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
        resultMap.put(ComRet.retData,photographerses);
        return  resultMap;
    }
}
