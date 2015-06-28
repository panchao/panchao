package com.aoxiu.aoxiuApp.controller;

import com.aoxiu.ComRet;
import com.aoxiu.meta.photo.Album;
import com.aoxiu.meta.photo.AlbumOrder;
import com.aoxiu.meta.photo.common.AlbumOrderCommon;
import com.aoxiu.service.photo.AlbumService;
import freemarker.template.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/5/18.
 */
@Controller
@RequestMapping("/album")
public class AlbumController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AlbumService albumService;

    @RequestMapping("/albums.do")
    public String gotoAlbum(HttpServletRequest request){    //相册页面接口
        String getCode = request.getParameter("getCode");
        List<Album> albums = albumService.getAllAlbums();
        request.setAttribute("albums",albums);
        request.setAttribute("getCode",getCode);
        return "shop_index";
    }

    @RequestMapping("/submitOrder.do")
    @ResponseBody
    public Object submitAlbums(HttpServletRequest request,@RequestBody String jsonStr){    //提交相册订单接口
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(jsonStr)){
            logger.error("[submitAlbums] submit albums wrong params");
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            return resultMap;
        }
        try{
            albumService.submitOrder(jsonStr);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            return resultMap;
        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            logger.error("[submitAlbums] submit albums error ->" + e.getMessage(),e);
            return  resultMap;
        }
    }

    @RequestMapping("/gotoOrderConfirm.do")
    public String gotoOrderConfirm(HttpServletRequest request){
        String getCode = request.getParameter("getCode");
//        if(StringUtils.isEmpty(getCode)){
//           logger.info("[gotoOrderConfirm] wrong paramter");
//            return "error";
//        }
        try{

            List<AlbumOrderCommon> albumOrderCommons =  albumService.gselectAlbumOrders("c1c69613-6d84-4c8b-9c06-925800afaa6f");
            request.setAttribute("albumOrders",albumOrderCommons);
            request.setAttribute("coupon","暂时没有");
        }catch (Exception e){
            logger.error("[gotoOrderConfirm] error" + e.getMessage(),e);
        }
        return "order_confirm";
    }

}
