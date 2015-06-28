package com.aoxiu.aoxiuApp.controller;

import com.aoxiu.AoxiuConstant;
import com.aoxiu.ComRet;
import com.aoxiu.common.PaginationInfo;
import com.aoxiu.meta.photo.Appraisal;
import com.aoxiu.meta.photo.PhotographerOrder;
import com.aoxiu.meta.photo.common.PhotographerOrderAppraisal;
import com.aoxiu.meta.photo.common.PhotographerOrderCommon;
import com.aoxiu.service.photo.AppraisalService;
import com.aoxiu.service.photo.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by panchao on 15/5/17.
 */
@Controller
@RequestMapping("/appraisal")
public class AppraisalController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private AppraisalService appraisalService;

    @Resource
    private OrderService orderService;

    @RequestMapping("/addAppraisal.do")
    @ResponseBody
    public Object addAppraisal(HttpServletRequest request){    //添加评价
        String appraisalId = request.getParameter("order_id");
        String appraisalStar = request.getParameter("star");
        String appraisal = request.getParameter("appraisal");
        String type = request.getParameter("type");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(appraisalId) || StringUtils.isEmpty(appraisalStar) || StringUtils.isEmpty(appraisal)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.info("[addAppraisal] add appraisal   wrong parameter");
            return  resultMap;
        }

        try{
            appraisalService.addAppraisal(appraisalId,appraisal,appraisalStar,type);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            return  resultMap;
        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retCode,ComRet.FAIL_DESC);
            logger.error("[addAppraisal] add appraisal error ->" + e.getMessage(),e);
            return  resultMap;
        }
    }

    @RequestMapping("/getAppraisal.do")
    @ResponseBody
    public Object getAppraisal(HttpServletRequest request){
        Integer photographerId = Integer.valueOf(request.getParameter("photographerId"));
        String pageNumberStr = request.getParameter("pageNum");
        String filter = request.getParameter("filter");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(photographerId)){
            logger.info("[getAllAppraisal] get all  appraisal   wrong parameter");
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc, ComRet.FAIL_DESC);
            return resultMap;
        }
        try{
            int pageNumber = 0;
            if(pageNumberStr != null){
                pageNumber = Integer.valueOf(pageNumberStr);
            }else {
                pageNumber = 1;
            }
            PaginationInfo paginationInfo = null;
            List<PhotographerOrderAppraisal> photographerOrderAppraisals = null;
            paginationInfo = new PaginationInfo();
            photographerOrderAppraisals = appraisalService.getAllPhotographerOrderAppraisal(photographerId,
                    pageNumber, AoxiuConstant.AOXIU_RECORD_PER_PAGE,paginationInfo,filter);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put("commentInfo",photographerOrderAppraisals!=null?photographerOrderAppraisals:new ArrayList<>());
            resultMap.put("totalPages",paginationInfo.getTotalPage());
            resultMap.put("totalCount",paginationInfo.getTotalRecord());
            request.setAttribute("appraisals",resultMap);
            request.setAttribute("photographers_id",photographerId);
//            return "appraisalManager";
            return resultMap;
        }catch (Exception e){
            logger.error("[getAllAppraisal] get appraisal error ->" + e.getMessage(),e);
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            return resultMap;
        }
    }

    @RequestMapping("/getAllAppraisal.do")
    public String getAllAppraisal(HttpServletRequest request){      //获得所有订单，包括评价的和未评价的
        Integer photographerId = Integer.valueOf(request.getParameter("photographerId"));
        String pageNumberStr = request.getParameter("pageNum");
        String filter = request.getParameter("filter");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(photographerId)){
            logger.info("[getAllAppraisal] get all  appraisal   wrong parameter");
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
            List<PhotographerOrderAppraisal> photographerOrderAppraisals = null;
            paginationInfo = new PaginationInfo();
            photographerOrderAppraisals = appraisalService.getAllPhotographerOrderAppraisal(photographerId,
                            pageNumber, AoxiuConstant.AOXIU_RECORD_PER_PAGE,paginationInfo,filter);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put("commentInfo",photographerOrderAppraisals!=null?photographerOrderAppraisals:new ArrayList<>());
            resultMap.put("totalPages",paginationInfo.getTotalPage());
            resultMap.put("totalCount",paginationInfo.getTotalRecord());
            request.setAttribute("appraisals",resultMap);
            request.setAttribute("photographers_id",photographerId);
            return "appraisalManager";
//            return resultMap;
        }catch (Exception e){
            logger.error("[getAllAppraisal] get appraisal error ->" + e.getMessage(),e);
            return "error";
        }
    }

    @RequestMapping("/searchAppraisal.do")
    @ResponseBody
    public Object searchAppraisal(HttpServletRequest request){
        String customerName = request.getParameter("customerName");
        String photographerId = request.getParameter("photographerId");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(customerName) || StringUtils.isEmpty(photographerId)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.info("[searchAppraisal] search appraisal   wrong parameter");
            return  resultMap;
        }
        try{
            PaginationInfo paginationInfo = new PaginationInfo();
            List<PhotographerOrderAppraisal> photographerOrderAppraisals = null;
            photographerOrderAppraisals = appraisalService.searchAppraisalByName(customerName,photographerId,paginationInfo);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put("commentInfo",photographerOrderAppraisals);
            resultMap.put("totalPages",paginationInfo.getTotalPage());
            return  resultMap;
        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retCode,ComRet.FAIL_DESC);
            logger.error("[searchAppraisal] search appraisal error ->" + e.getMessage(),e);
            return  resultMap;
        }
    }
    @RequestMapping("/gotoAppraisalManager.do")
    public String gotoAppraisalManager(HttpServletRequest request){
        String photographerId = request.getParameter("photographerId");
        request.setAttribute("photographerId",photographerId);
//        HttpSession session = request.getSession().
        return  "appraisal";
    }

}
