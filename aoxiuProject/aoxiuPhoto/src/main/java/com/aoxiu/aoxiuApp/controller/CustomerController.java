package com.aoxiu.aoxiuApp.controller;

import com.aoxiu.ComRet;
import com.aoxiu.common.PaginationInfo;
import com.aoxiu.meta.photo.Appraisal;
import com.aoxiu.meta.photo.Customer;
import com.aoxiu.service.photo.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by panchao on 15/5/17.
 * 客户相关
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private CustomerService customerService;

    @RequestMapping("/addCustomer.do")
    @ResponseBody
    private Object addCustomer(HttpServletRequest request,@RequestBody String jsonStr){   //新增客户
        Map<String,Object> resultMap = new HashMap<>();
        String username = request.getParameter("username");
        String wechat = request.getParameter("wechat");
        String photographersId = request.getParameter("photographersId");
        String email = request.getParameter("email");
        String qq = request.getParameter("qq");
        String phone = request.getParameter("phone");
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(photographersId)
                || StringUtils.isEmpty(phone)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.info("[addCustomer] add customer wrong parameter");
            return resultMap;
        }
        try{
            Customer customer = new Customer();
            customer.setPhoneNumber(phone);
            customer.setEmail(email);
            customer.setQQNumber(qq);
            customer.setRealName(username);
            customer.setWeChat(wechat);
            customer.setPhotographersId(Integer.parseInt(photographersId));
            customer = customerService.addCustomer(customer);
            if(customer != null){
                resultMap.put(ComRet.retCode,ComRet.SUCESS);
                resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
                resultMap.put(ComRet.retData,customer);
            }else{
                resultMap.put(ComRet.retCode,ComRet.FAIL);
                resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            }
        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            logger.error("[addCustomer] add customer error ->" + e.getMessage(),e);
            return  resultMap;
        }
        return resultMap;
    }

    @RequestMapping("/addCustomer1.do")
    @ResponseBody
    private Object addCustomer1(HttpServletRequest request,@RequestBody String jsonStr){   //新增客户，新建订单中的
        Map<String,Object> resultMap = new HashMap<>();
        String username = request.getParameter("username");
        String wechat = request.getParameter("wechat");
        String photographersId = request.getParameter("photographersId");
        String email = request.getParameter("email");
        String qq = request.getParameter("qq");
        String phone = request.getParameter("phone");
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(photographersId)
                || StringUtils.isEmpty(phone)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.info("[addCustomer] add customer wrong parameter");
            return resultMap;
        }
        try{
            List<Customer> customers = new ArrayList<>();
            Customer customer = new Customer();
            customer.setPhoneNumber(phone);
            customer.setEmail(email);
            customer.setQQNumber(qq);
            customer.setRealName(username);
            customer.setWeChat(wechat);
            customer.setPhotographersId(Integer.parseInt(photographersId));
            customer = customerService.addCustomer(customer);
            if(customer != null){
                customers.add(customer);
                resultMap.put(ComRet.retCode,ComRet.SUCESS);
                resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
                resultMap.put(ComRet.retData,customers);
            }else{
                resultMap.put(ComRet.retCode,ComRet.FAIL);
                resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
                resultMap.put(ComRet.retData,customers);
            }
        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            logger.error("[addCustomer] add customer error ->" + e.getMessage(),e);
            return  resultMap;
        }
        return resultMap;
    }

    @RequestMapping("/updateCustomer.do")
    @ResponseBody
    public Object updateCustomer(HttpServletRequest request,@RequestBody String jsonStr){  //更新客户资料
        Map<String,Object> resultMap = new HashMap<>();
        String wechat = request.getParameter("wechat");
        String photographersId = request.getParameter("photographersId");
        String email = request.getParameter("email");
        String qq = request.getParameter("qq");
        String phone = request.getParameter("phone");
        String customerId = request.getParameter("customerId");
        if(StringUtils.isEmpty(customerId)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.info("[updateCustomer] update customer wrong parameter");
            return resultMap;
        }
        try{
            customerService.updateCustomer(customerId,phone,qq,wechat,email);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retCode,ComRet.FAIL_DESC);
            logger.error("[updateCustomer] update customer error ->" + e.getMessage(),e);
            return resultMap;
        }
        return resultMap;
    }

    @RequestMapping("/getPhotographersUser.do")
    @ResponseBody
    public Object getPhotographersUser(HttpServletRequest request){   //获得客户
        String photographersId = request.getParameter("photographers_id");
        String pageNumStr = request.getParameter("pageNum");
        String recordPerPageStr = request.getParameter("recordPerPage");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(photographersId)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.info("[getPhotographersUser] get photographers customer wrong parameter");
        }
        try{
            int pageNum = 1;
            int recordPerPage = 10;
            if(StringUtils.isEmpty(recordPerPageStr)){
                recordPerPage = 10;
            }else{
                recordPerPage = Integer.valueOf(recordPerPageStr);
            }
            if(StringUtils.isEmpty(pageNumStr)){
                pageNum = 1;
            }else{
                pageNum = Integer.valueOf(pageNumStr);
            }
            PaginationInfo paginationInfo = new PaginationInfo();
            List<Customer> customerList = customerService.getPhotographersUsers(photographersId,paginationInfo,pageNum,recordPerPage);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,customerList);
            resultMap.put(ComRet.retPage,paginationInfo.getTotalPage());
            resultMap.put(ComRet.retCount,paginationInfo.getTotalRecord());
            return resultMap;
        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retCode,ComRet.FAIL_DESC);
            logger.error("[getPhotographersUser] get photographers customer  error ->" + e.getMessage(),e);
            return  resultMap;
        }
    }

    @RequestMapping("/deleteCustomer.do")
    @ResponseBody
    public Object deleteCustomer(HttpServletRequest request){   //删除客户
        String customerId = request.getParameter("customerId");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(customerId)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.info("[deleteCustomer] delete  customer wrong parameter");
        }
        try{
            if(customerService.deleteCustomer(customerId)){
                resultMap.put(ComRet.retCode,ComRet.SUCESS);
                resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            }else{
                resultMap.put(ComRet.retCode,ComRet.FAIL);
                resultMap.put(ComRet.retCode,ComRet.FAIL_DESC);
            }
        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retCode,ComRet.FAIL_DESC);
            logger.error("[deleteCustomer] delete  customer error ->" + e.getMessage(),e);
            return  resultMap;
        }

        return resultMap;
    }

    @RequestMapping("/photographersSearchCustomers.do")
    @ResponseBody
    public Object photographersSearchCustomers(HttpServletRequest request){  //搜索客户
        String photographersId = request.getParameter("photographersId");
        String customerName = request.getParameter("customerName");
        String pageNumStr = request.getParameter("pageNum");
        String recordPerPageStr = request.getParameter("recordPerPage");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(customerName) || StringUtils.isEmpty(photographersId)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.info("[photographersSearchCustomers] photographers search customer   wrong parameter");
        }
        try{
            int pageNum = 1;
            int recordPerPage = 10;
            if(StringUtils.isEmpty(recordPerPageStr)){
                recordPerPage = 10;
            }else{
                recordPerPage = Integer.valueOf(recordPerPageStr);
            }
            if(StringUtils.isEmpty(pageNumStr)){
                pageNum = 1;
            }else{
                pageNum = Integer.valueOf(pageNumStr);
            }
            PaginationInfo paginationInfo = new PaginationInfo();
            List<Customer> customers = customerService.getCustomersByNameAndPhotographerId(customerName,
                    Integer.valueOf(photographersId),paginationInfo,pageNum,recordPerPage);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,customers);
            resultMap.put(ComRet.retPage,paginationInfo.getTotalPage());
            resultMap.put(ComRet.retCount,paginationInfo.getTotalRecord());
        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retCode,ComRet.FAIL_DESC);
            logger.error("[photographersSearchCustomers] photographers search customers error ->" + e.getMessage(),e);
            return  resultMap;
        }
        return resultMap;
    }

    @RequestMapping("/gotoCustomer.do")
    public String gotoCustomer(HttpServletRequest request){
        String photographerId = request.getParameter("photographerId");
        String pageNumStr = request.getParameter("pageNum");
        String recordPerPageStr = request.getParameter("recordPerPage");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(photographerId)){
            logger.info("[gotoCustomer] goto customer wrong parameter");
            return "error";
        }
        try{
            int pageNum = 1;
            int recordPerPage = 10;
            if(StringUtils.isEmpty(recordPerPageStr)){
                recordPerPage = 10;
            }else{
                recordPerPage = Integer.valueOf(recordPerPageStr);
            }
            if(StringUtils.isEmpty(pageNumStr)){
                pageNum = 1;
            }else{
                pageNum = Integer.valueOf(pageNumStr);
            }
            PaginationInfo paginationInfo = new PaginationInfo();
            List<Customer> customers = customerService.getCustomersByPhotographerId(photographerId,pageNum,recordPerPage,paginationInfo);
            resultMap.put(ComRet.retData,customers != null?customers:new ArrayList<>());
            resultMap.put(ComRet.retPage,paginationInfo.getTotalPage());
            resultMap.put(ComRet.retCount,paginationInfo.getTotalRecord());
            request.setAttribute("photographerId",photographerId);
            request.setAttribute("customers",resultMap);
            return "clientmanage";
        }catch (Exception e){
            logger.error("[gotoCustomer] goto customer error");
            return "error";
        }

    }


}
