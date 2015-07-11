package com.aoxiu.aoxiuApp.controller;

import com.aoxiu.AoxiuConstant;
import com.aoxiu.AoxiuOrderStatus;
import com.aoxiu.ComRet;
import com.aoxiu.aoxiuApp.utils.GeneratorCode;
import com.aoxiu.aoxiuApp.utils.MatrixToImageWriter;
import com.aoxiu.common.PaginationInfo;
import com.aoxiu.dao.photo.PhotographerOrderDao;
import com.aoxiu.dao.photo.QrCodeDao;
import com.aoxiu.meta.photo.PhotographerOrder;
import com.aoxiu.meta.photo.QrCode;
import com.aoxiu.meta.photo.common.OrderDetail;
import com.aoxiu.meta.photo.common.PhotographerOrderCommon;
import com.aoxiu.service.photo.OrderService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by panchao on 15/5/1.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OrderService orderService;

    @Resource
    private QrCodeDao qrCodeDao;

    @Resource
    private PhotographerOrderDao photographerOrderDao;

    /**
     * order-type:1
     * max_count:22
     * price_per_photo:22
     * watermark_type:
     * album_id:1
     * photographers_id:1
     * user_id:9
     * type:cameraman
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/addOrder.do")
    public Object addOrder(HttpServletRequest request, HttpServletResponse response) {   //直接跳到登陆页，或者登陆后的首页
//        String jsonStr = request.getParameter("params");
        String orderType = request.getParameter("order-type");
        String maxCount = request.getParameter("max_count");
        String pricePerPhoto = request.getParameter("price_per_photo");
        String watermarkType = request.getParameter("watermark_type");
        String albumId = request.getParameter("album_id");
        String photographersId = request.getParameter("photographers_id");
        String userId = request.getParameter("user_id");
        String type = request.getParameter("type");
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(orderType) || StringUtils.isEmpty(type) || StringUtils.isEmpty(maxCount)
                || StringUtils.isEmpty(pricePerPhoto) ||
                StringUtils.isEmpty(photographersId) || StringUtils.isEmpty(userId)) {
            resultMap.put(ComRet.retCode, ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc, ComRet.WRONG_PARAMETER_DESC);
            logger.error("[addOrder] add order wrong parameter");
            return resultMap;
        }
        Map<String, Object> map = null;
        try {
            PhotographerOrder photographerOrder = new PhotographerOrder();
            photographerOrder.setMaxSelectCount(Integer.valueOf(maxCount));
            photographerOrder.setPricePerPhoto(Double.valueOf(pricePerPhoto));
            if (StringUtils.isEmpty(watermarkType)) {
                photographerOrder.setAddWaterMark(0);
            } else {
                photographerOrder.setAddWaterMark(0);
                photographerOrder.setWatermarkType(watermarkType);
            }
            photographerOrder.setOrderStep(AoxiuOrderStatus.ORIGIN_NOT_UPLOADED);                               //订单状态
            photographerOrder.setPhotographersId(Integer.valueOf(photographersId));
            photographerOrder.setUserId(Integer.valueOf(userId));
//            Map<String,Object> map = new HashMap<>();
            map = orderService.addOrder(photographerOrder, type);
            resultMap.put(ComRet.retCode, ComRet.SUCESS);
            resultMap.put(ComRet.retDesc, ComRet.SUCESS_DESC);
//            resultMap.put("album_id",)
//            map.put("orderId",orderId);
            resultMap.put(ComRet.retData, map);
        } catch (Exception e) {
            resultMap.put(ComRet.retCode, ComRet.FAIL);
            resultMap.put(ComRet.retDesc, ComRet.FAIL_DESC);
            logger.error("[addOrder] add order fail -> " + e.getMessage());
            return resultMap;
        }

        return resultMap;

    }

    @RequestMapping("/updateOrder.do")
    @ResponseBody
    public Object uodateOrder(HttpServletRequest request, HttpServletResponse response) {
        String waterMark = request.getParameter("waterMark");
        String maxCount = request.getParameter("maxCount");
        String type = request.getParameter("type");
        String orderId = request.getParameter("orderId");
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(orderId)) {
            resultMap.put(ComRet.retCode, ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc, ComRet.WRONG_PARAMETER_DESC);
            logger.error("[updateOrder] update order wrong parameter");
            return resultMap;
        }
        int count = 0;
        String price = null;
        try {
            if(type.equals("extraPrice")){
                price = maxCount;
            }
            count = orderService.updateOrder(waterMark, maxCount, orderId, null,price);

//            if (type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR)) {
//                count = orderService.updateOrder(waterMark, maxCount, orderId, null);
//            } else if (type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_COMPNAY)) {
//                //TODO 婚庆订单
//            }
            resultMap.put(ComRet.retCode, ComRet.SUCESS);
            resultMap.put(ComRet.retDesc, ComRet.SUCESS_DESC);
        } catch (Exception e) {
            logger.error("[updateOrder] update order error " + e.getMessage());
            resultMap.put(ComRet.retCode, ComRet.FAIL);
            resultMap.put(ComRet.retDesc, ComRet.FAIL_DESC);
            return resultMap;
        }
        return resultMap;
    }

    @RequestMapping("/deleteOrder.do")
    public Object deleteOrder(HttpServletRequest request, HttpServletResponse response) {
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(orderId) || StringUtils.isEmpty(type)) {
            resultMap.put(ComRet.retCode, ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc, ComRet.WRONG_PARAMETER_DESC);
            logger.error("[deleteOrder] update order wrong parameter");
            return resultMap;
        }
        int count = 0;
        try {
            count = orderService.deleteOrder(orderId, type);
            if (count == 1) {
                resultMap.put(ComRet.retCode, ComRet.SUCESS);
                resultMap.put(ComRet.retDesc, ComRet.SUCESS_DESC);
            } else {
                resultMap.put(ComRet.retCode, ComRet.HAD_GETCODE);
                resultMap.put(ComRet.retDesc, ComRet.HAD_GETCODE_DESC);
            }

        } catch (Exception e) {
            logger.error("[updateOrder] update order error " + e.getMessage());
            resultMap.put(ComRet.retCode, ComRet.FAIL);
            resultMap.put(ComRet.retDesc, ComRet.FAIL_DESC);
            return resultMap;
        }
        return resultMap;

    }

    @RequestMapping("/getOrder.do")
    public String getOrder(HttpServletRequest request) {
        String photographerId = request.getParameter("photographerId");
        Map<String, Object> resultMap = new HashMap<>();
        String pageNum = request.getParameter("page_num");
        String recordPerPage = request.getParameter("record_per_page");
        if (StringUtils.isEmpty(photographerId)) {
            logger.error("[getOrder] get order wrong parameter");
            resultMap.put(ComRet.retCode, ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc, ComRet.WRONG_PARAMETER_DESC);
            return "error";
        }
        try {
            int pageNumInt = 0;
            int recordPerPageInt = 0;
            if (StringUtils.isEmpty(pageNum)) {
                pageNumInt = 1;
            } else {
                pageNumInt = Integer.valueOf(pageNum);
            }
            if (StringUtils.isEmpty(recordPerPage)) {
                recordPerPageInt = AoxiuConstant.AOXIU_RECORD_PER_PAGE;
            } else {
                recordPerPageInt = Integer.valueOf(recordPerPage);
            }
            PaginationInfo paginationInfo = new PaginationInfo();
            List<PhotographerOrderCommon> photographerOrderList = orderService.getPhotographerOrders(photographerId, pageNumInt, recordPerPageInt, paginationInfo);
//            request.setAttribute("result_map",photographerOrderList);
            resultMap.put(ComRet.retCode, ComRet.SUCESS);
            resultMap.put(ComRet.retDesc, ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData, photographerOrderList);
            request.setAttribute("data", photographerOrderList);
            request.setAttribute("totalPages", paginationInfo.getTotalPage());
            request.setAttribute("totalNums", paginationInfo.getTotalRecord());
            request.setAttribute("photographers_id", photographerId);
            return "order-admin";
        } catch (Exception e) {
            logger.error("[getOrder] get order wrong parameter －> " + e.getMessage(), e);
            resultMap.put(ComRet.retCode, ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc, ComRet.WRONG_PARAMETER_DESC);
            return "error";
        }
    }

    @RequestMapping("/getOrderJson.do")
    @ResponseBody
    public Object getOrderJson(HttpServletRequest request) {
        String photographerId = request.getParameter("photographer_id");
        String pageNum = request.getParameter("page_num");
        String recordPerPage = request.getParameter("record_per_page");
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(photographerId) || StringUtils.isEmpty(pageNum)
                || StringUtils.isEmpty(recordPerPage)) {
            logger.error("[getOrder] get order wrong parameter");
            resultMap.put(ComRet.retCode, ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc, ComRet.WRONG_PARAMETER_DESC);
            return resultMap;
        }
        try {
            List<PhotographerOrderCommon> photographerOrderList = orderService.getPhotographerOrders(photographerId,
                    Integer.parseInt(pageNum), Integer.parseInt(recordPerPage), null);
            resultMap.put(ComRet.retCode, ComRet.SUCESS);
            resultMap.put(ComRet.retDesc, ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData, photographerOrderList);
            return resultMap;
        } catch (Exception e) {
            logger.error("[getOrder] get order error －> " + e.getMessage());
            resultMap.put(ComRet.retCode, ComRet.FAIL);
            resultMap.put(ComRet.retDesc, ComRet.FAIL_DESC);
            return resultMap;
        }
    }

    @RequestMapping("/getCode.do")
    @ResponseBody
    public Object getCode(HttpServletRequest request) {
        String orderId = request.getParameter("order_id");
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(orderId)) {
            logger.error("[getCode] get code wrong parameter");
            resultMap.put(ComRet.retCode, ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc, ComRet.WRONG_PARAMETER_DESC);
            return resultMap;
        }
        try {

            String code = orderService.isGeneratorGetCode(orderId, 1);
            if (code != null) {
                code = GeneratorCode.generatorCode();
                orderService.updateOrder(null, null, orderId, code,null);
            }
            resultMap.put(ComRet.retCode, ComRet.SUCESS);
            resultMap.put(ComRet.retDesc, ComRet.SUCESS_DESC);
            resultMap.put("get_code", code);
            return resultMap;
        } catch (Exception e) {
            logger.error("[getCode] get code error －> " + e.getMessage());
            resultMap.put(ComRet.retCode, ComRet.FAIL);
            resultMap.put(ComRet.retDesc, ComRet.FAIL_DESC);
            return resultMap;
        }
    }

    @ResponseBody
    @RequestMapping("/searchOrder.do")
    public Object searchOrder(HttpServletRequest request){
        String key = request.getParameter("customerName");
        String photographerId = request.getParameter("photographerId");
        String pageNum = request.getParameter("page");
        String recordPerPage = request.getParameter("record_per_page");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(photographerId)){
            logger.error("[searchOrder]  wrong parameter");
            resultMap.put(ComRet.retCode, ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc, ComRet.WRONG_PARAMETER_DESC);
            return resultMap;
        }
        try{
            int pageNumInt = 0;
            int recordPerPageInt = 0;
            if (StringUtils.isEmpty(pageNum)) {
                pageNumInt = 1;
            } else {
                pageNumInt = Integer.valueOf(pageNum);
            }
            if (StringUtils.isEmpty(recordPerPage)) {
                recordPerPageInt = AoxiuConstant.AOXIU_RECORD_PER_PAGE;
            } else {
                recordPerPageInt = Integer.valueOf(recordPerPage);
            }
            PaginationInfo paginationInfo = new PaginationInfo();
            List<PhotographerOrderCommon> photographerOrderCommons = orderService.searchOrdersByKey(key,
                    AoxiuConstant.AOXIU_ORDER_SEARCH_CUSTOMER_NAME, photographerId,paginationInfo,pageNumInt,recordPerPageInt);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,photographerOrderCommons);
            resultMap.put("totalPages",paginationInfo.getTotalPage());
            resultMap.put("totalRecord",paginationInfo.getTotalRecord());
            return resultMap;

        }catch (Exception e){
            logger.error("[searchOrder]  error" + e.getMessage(),e);
            resultMap.put(ComRet.retCode, ComRet.FAIL);
            resultMap.put(ComRet.retDesc, ComRet.FAIL_DESC);
            return resultMap;
        }
    }

    @ResponseBody
    @RequestMapping("/filter.do")
    public Object filterOrder(HttpServletRequest request){
        String photographerId = request.getParameter("photographerId");
        String filter = request.getParameter("filter");
        String pageNum = request.getParameter("page");
        String recordPerPage = request.getParameter("record_per_page");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(filter) || StringUtils.isEmpty(photographerId)){
            logger.error("[filterOrder]  wrong parameter");
            resultMap.put(ComRet.retCode, ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc, ComRet.WRONG_PARAMETER_DESC);
            return resultMap;
        }
        try{
            int pageNumInt = 0;
            int recordPerPageInt = 0;
            if (StringUtils.isEmpty(pageNum)) {
                pageNumInt = 1;
            } else {
                pageNumInt = Integer.valueOf(pageNum);
            }
            if (StringUtils.isEmpty(recordPerPage)) {
                recordPerPageInt = AoxiuConstant.AOXIU_RECORD_PER_PAGE;
            } else {
                recordPerPageInt = Integer.valueOf(recordPerPage);
            }
            PaginationInfo paginationInfo = new PaginationInfo();
            List<PhotographerOrderCommon> photographerOrderCommons = orderService.searchOrdersByKey(filter,
                    AoxiuConstant.AOXIU_ORDER_SEARCH_CUSTOMER_STATUS,photographerId,paginationInfo,pageNumInt,recordPerPageInt);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,photographerOrderCommons);
            resultMap.put("totalPages",paginationInfo.getTotalPage());
            resultMap.put("totalRecord",paginationInfo.getTotalRecord());
            return resultMap;

        }catch (Exception e){
            logger.error("[filterOrder]  error" + e.getMessage(),e);
            resultMap.put(ComRet.retCode, ComRet.FAIL);
            resultMap.put(ComRet.retDesc, ComRet.FAIL_DESC);
            return resultMap;
        }
    }

    @RequestMapping("/gotoOrderDetail.do")
    public String gotoOrderDetail(HttpServletRequest request) {
        String orderId = request.getParameter("orderId");
        String customerId = request.getParameter("customerId");
        String photographerId = request.getParameter("photographers_id");
        if (StringUtils.isEmpty(orderId) && StringUtils.isEmpty(customerId)) {
            logger.info("[gotoOrderDetail] goto order detail parameter wrong");
            return "error";
        }
        try {
            OrderDetail orderDetail = null;
            if (StringUtils.isEmpty(customerId)) {
                orderDetail = orderService.getOrderDetailByOrderId(orderId);
            } else {
                orderDetail = orderService.getOrderDetailByCustomerId(customerId);
            }
            request.setAttribute("order", orderDetail);
            request.setAttribute("photographers_id", orderDetail.getPhotographerId());
            return "order-details";
        } catch (Exception e) {
            logger.error("[gotoOrderDetail] get order error->" + e.getMessage(), e);
            return "error";
        }
    }

    @ResponseBody
    @RequestMapping("/generateEntrance.do")
    public Object generateEntrance(HttpServletRequest request) {    //原片入口
        String orderId = request.getParameter("orderId");
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(orderId)) {
            logger.error("[generateEntrance] generate entrance wrong parameter");
            resultMap.put(ComRet.retCode, ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc, ComRet.WRONG_PARAMETER_DESC);
            return resultMap;
        }
        try {
            QrCode qrCode = qrCodeDao.getQrCodeByOrderId(orderId);
            Map<String, Object> map = orderService.getEntrance(orderId);
            if (qrCode == null || qrCode.getGetUnselectedUrl() == null) {
                String url = "http://115.159.36.177:8080/pictures/getUserContent?getCode=" + map.get("getCode") + "&type=unselected";     //TODO
                String filePath = request.getSession().getServletContext().getRealPath("qrCode");
                String qrCodeUrl = generatorQrCode(url, filePath, orderId + "unSelect");
                qrCode = new QrCode();
                qrCode.setOrderId(Integer.valueOf(orderId));
                qrCode.setGetUnselectedUrl(qrCodeUrl);
                qrCodeDao.insertQrCode(qrCode);
                resultMap.put("qrCode", qrCodeUrl);

            } else {
                resultMap.put("qrCode", qrCode.getGetUnselectedUrl());
            }
            resultMap.put("code", map.get("getCode"));
            resultMap.put(ComRet.retCode, ComRet.SUCESS);
            resultMap.put(ComRet.retDesc, ComRet.SUCESS_DESC);
            return resultMap;
        } catch (Exception e) {
            logger.error("[generateEntrance] generate entrance  error －> " + e.getMessage());
            resultMap.put(ComRet.retCode, ComRet.FAIL);
            resultMap.put(ComRet.retDesc, ComRet.FAIL_DESC);
            return resultMap;
        }
    }

    private String generatorQrCode(String url, String filePath, String name) {
        int width = 300;
        int height = 300;
        //二维码的图片格式
        String format = "gif";
        Hashtable hints = new Hashtable();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url,
                    BarcodeFormat.QR_CODE, width, height, hints);
            String qrCodeUrl = filePath + File.separator + name + ".gif";
            filePath = filePath.substring(filePath.lastIndexOf(("aoxiuPhoto")) + 11);
            filePath = "http://115.159.36.177:8080/" + filePath + "/" + name + ".gif";         //TODO
            //生成二维码
            File outputFile = new File(qrCodeUrl);
            if (outputFile.exists()) {                // 已经存在
                return filePath;
            }
            MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);

            return filePath;
        } catch (WriterException we) {
            //TODO 异常信息
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            //TODO 异常信息
            return null;
        }
    }
//    @RequestMapping("/getPhotographerContentContent.do")
//    @ResponseBody
//    public Object getPhotographerContent(HttpServletRequest request){
//        String photographersId = request.getParameter("photographers_id");
//        Map<String,Object> resultMap = new HashMap<>();
//        if(StringUtils.isEmpty(photographersId)){
//            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
//            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
//            logger.error("[getPhotographerContent] get Photographer Content wrong parameter");
//            return  resultMap;
//        }
//        try{
//
//        }catch (Exception e){
//            logger.error("[getPhotographerContent] get Photographer Content －> " + e.getMessage());
//            resultMap.put(ComRet.retCode,ComRet.FAIL);
//            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
//            return  resultMap;
//        }
//    }

}
