package com.aoxiu.aoxiuApp.controller;

import com.aoxiu.AoxiuConstant;
import com.aoxiu.ComRet;
import com.aoxiu.aoxiuApp.uploadPictures.GeneratorToken;
import com.aoxiu.common.PaginationInfo;
import com.aoxiu.dao.photo.PhotoDao;
import com.aoxiu.dao.photo.PhotographerOrderDao;
import com.aoxiu.meta.photo.Photo;
import com.aoxiu.meta.photo.PhotographerOrder;
import com.aoxiu.meta.photo.Photographers;
import com.aoxiu.meta.photo.common.PhotographersContent;
import com.aoxiu.meta.photo.common.PhotographersPhoto;
import com.aoxiu.service.photo.OrderService;
import com.aoxiu.service.photo.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by panchao on 15/5/2.
 */
@RequestMapping("/pictures")
@Controller
public class PictureController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private OrderService orderService;
    @Resource
    private PhotoService photoService;

    @Resource
    private PhotographerOrderDao photographerOrderDao;
    /**
     * @param request
     * @param response
     * @return
     */
     @RequestMapping("/addContent.do")
     @ResponseBody
    public Object addContent(HttpServletRequest request,HttpServletResponse response){
        String contentName = request.getParameter("albumName");
        String orderId = request.getParameter("orderId");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(contentName) || StringUtils.isEmpty(orderId)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.error("[addContent] add content wrong parameter");
            return  resultMap;
        }
         int id = orderService.addNewContent(contentName,orderId,null);
         try{
        if(id != 0){
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put("album_id",id);
        }else{
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            logger.error("[addContent] add content fail");
        }
         }catch (Exception e){
             resultMap.put(ComRet.retCode,ComRet.FAIL);
             resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
             logger.error("[addContent] add content fail");
         }

        return resultMap;
    }

    @RequestMapping("/updateContent")
    @ResponseBody
    public Object updateContent(HttpServletRequest request,HttpServletResponse  response){
        String contentId = request.getParameter("contentId");
        String contentName = request.getParameter("contentName");
        String contentPhoto = request.getParameter("contentPhoto");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(contentId) || (StringUtils.isEmpty(contentName) && StringUtils.isEmpty(contentPhoto))){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.error("[updateContent] update content wrong parameter");
            return  resultMap;
        }
        try {
            if (orderService.updateContent(contentId, contentName, contentPhoto)) {
                resultMap.put(ComRet.retCode, ComRet.SUCESS);
                resultMap.put(ComRet.retDesc, ComRet.SUCESS_DESC);
            } else {
                resultMap.put(ComRet.retCode, ComRet.FAIL);
                resultMap.put(ComRet.retDesc, ComRet.FAIL_DESC);
            }
        } catch (Exception e) {
            resultMap.put(ComRet.retCode, ComRet.FAIL);
            resultMap.put(ComRet.retDesc, ComRet.FAIL_DESC);
            logger.error("[updateContent] add content fail -> " + e.getMessage());
        }
        return resultMap;
    }
    @RequestMapping("/uploadPictures.do")
    @ResponseBody
    public Object uploadPictures(HttpServletRequest request,HttpServletResponse response ,@RequestBody String jsonStr){
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(jsonStr)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.error("[uploadPictures] upload pictures wrong parameter");
            return  resultMap;
        }
        try{
            List<Photo> photos = orderService.uploadPhotos(jsonStr);
//            if(isSucess){
//                resultMap.put(ComRet.retCode,ComRet.SUCESS);
//                resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
//            }else{
//                resultMap.put(ComRet.retCode,ComRet.FAIL);
//                resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
//                logger.error("[addContent] add content fail");
//            }
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,photos);
        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            logger.error("[addContent] add content fail -> " + e.getMessage());
        }
        return  resultMap;
    }

    @RequestMapping("/getPictures")
    public String getPictures(HttpServletRequest request,HttpServletResponse response){
        String contentId = request.getParameter("contentId");
        if(StringUtils.isEmpty(contentId)){
            logger.error("[getPictures] upload pictures wrong parameter");
            return  "error";
        }
        Map<String,String> urls = null;
        try{
            urls = orderService.getPicturesUrl(contentId);
            request.setAttribute("resultMap",urls);
            return "photoList";
        }catch (Exception e){
            logger.error("[addContent] add content fail -> " + e.getMessage());
            return "error";
        }


    }

    @RequestMapping("/getSelectedPictures.do")
    public Object getSelectedPictrues(HttpServletRequest request){
        String getCode = request.getParameter("get_code");
        Map<String,String> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(getCode)){
            logger.info("[getSelectedPictrues] get selected pictures wrong parameter");
            return "error";
        }
        try{
            resultMap  = orderService.getSelectedPhotos(getCode);
            request.setAttribute("result_map", resultMap);
            return "refine_result";
        }catch (Exception e){
            logger.error("[getSelectedPictrues] get selected pictures error -> " + e.getMessage());
            return "error";
        }
    }

    @RequestMapping("/downloadName.do")
    @ResponseBody
    public void downloadName(HttpServletRequest request,HttpServletResponse response){
        String customerId = request.getParameter("customerId");
        if(StringUtils.isEmpty(customerId)){
            logger.info("[downloadName] wrong paramter");

        }
        OutputStream outputStream = null;
        try{
            String filedisplay = "精修片.txt";
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition","attachment;filename=" + filedisplay);
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
             outputStream = response.getOutputStream();
            String picturesName = photoService.getSelectedPhotosNamesByCustomerId(customerId);
            outputStream.write(picturesName.getBytes());
            outputStream.flush();
        }catch (Exception e){
            logger.error("[downloadName] system error " + e.getMessage(),e);
        }finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("[downloadName] close out error" + e.getMessage(),e);
                }
            }
        }
    }

    @RequestMapping("/unSelectManager.do")
    public String unSelectManager(HttpServletRequest request){
        String customerId = request.getParameter("customerId");
        String photograpersId = request.getParameter("photographerId");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(customerId)){
            logger.info("[selectedManager]  wrong parameter");
            return "error";
        }
        try{
            PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByUserId(customerId);
            PhotographersContent photographersContent = orderService.getMasterContents(photographerOrder.getOrderId() + "");
            List<PhotographersContent> photographersContents = orderService.getContentByAlbumId(photographersContent.getId() + "");
            List<PhotographersPhoto> photographersPhotos = orderService.getOrderMainContentPhotos(photographerOrder.getOrderId() + "");
            request.setAttribute("albums", photographersContents);
            request.setAttribute("photos",photographersPhotos);
            request.setAttribute("data",photographersPhotos);
            request.setAttribute("customerId",customerId);
            request.setAttribute("photographers_id",photograpersId);
            request.setAttribute("title","原片管理");
            request.setAttribute("masterContentId",photographersContent.getId());
            request.setAttribute("totalPages",200);
            request.setAttribute("totalPhotos",2000);
            return "admin-selected-original-photos";
        }catch (Exception e){
            logger.error("[getSelectedPictrues] get selected pictures error -> " + e.getMessage());
            return "error";
        }
    }

    @RequestMapping("/selectedManager.do")
    public String selectedManager(HttpServletRequest request){     //精修片管理
        String customerId = request.getParameter("customerId");
        String photograpersId = request.getParameter("photographerId");
        Map<String,String> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(customerId)){
            logger.info("[selectedManager]  wrong parameter");
            return "error";
        }
        try{
            PaginationInfo paginationInfo = new PaginationInfo();
            PhotographersContent photographersContent = orderService.getMasterContentsByCustomerId(customerId);
            List<Photo> photos  = orderService.getSelectedPhotosByCustomerId(customerId,1,AoxiuConstant.AOXIU_RECORD_PER_PAGE,paginationInfo);
            request.setAttribute("data", photos);
            request.setAttribute("customerId",customerId);
            request.setAttribute("photographers_id",photograpersId);
            request.setAttribute("totalPages",paginationInfo.getTotalPage());
            request.setAttribute("totalPhotos",paginationInfo.getTotalPage());
            request.setAttribute("title","精修片管理");
            request.setAttribute("masterContentId",photographersContent.getId());
            request.setAttribute("type","selected");
            request.setAttribute("domain","http://qiniu-plupload.qiniudn.com/");
            request.setAttribute("uptokenUrl","http://localhost:8080/pictures/getToken?type=3");
            request.setAttribute("albumId",photographersContent.getId());
            return "admin-selected-original-photos";
        }catch (Exception e){
            logger.error("[getSelectedPictrues] get selected pictures error -> " + e.getMessage());
            return "error";
        }
    }

    @RequestMapping("/selectedManagerJson.do")
    @ResponseBody
    public String selectedManagerJson(HttpServletRequest request){     //精修片管理
        String customerId = request.getParameter("customerId");
        String photograpersId = request.getParameter("photographerId");
        String pageNumStr = request.getParameter("page");
        String recordPerPageStr = request.getParameter("count");
        Map<String,String> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(customerId) || StringUtils.isEmpty(pageNumStr) ||
                StringUtils.isEmpty(recordPerPageStr)){
            logger.info("[selectedManager]  wrong parameter");
            return "error";
        }
        try{
            PaginationInfo paginationInfo = new PaginationInfo();
            PhotographersContent photographersContent = orderService.getMasterContentsByCustomerId(customerId);
            List<Photo> photos  = orderService.getSelectedPhotosByCustomerId(customerId,Integer.valueOf(pageNumStr),
                    Integer.valueOf(recordPerPageStr),paginationInfo);
            request.setAttribute("data", photos);
            request.setAttribute("customerId",customerId);
            request.setAttribute("photographers_id",photograpersId);
            request.setAttribute("totalPages",paginationInfo.getTotalPage());
            request.setAttribute("totalPhotos",paginationInfo.getTotalPage());
            request.setAttribute("title","精修片管理");
            request.setAttribute("masterContentId",photographersContent.getId());
            request.setAttribute("type","selected");
            request.setAttribute("domain","http://qiniu-plupload.qiniudn.com/");
            request.setAttribute("uptokenUrl","http://localhost:8080/pictures/getToken?type=3");
            request.setAttribute("albumId",photographersContent.getId());
            return "admin-selected-original-photos";
        }catch (Exception e){
            logger.error("[getSelectedPictrues] get selected pictures error -> " + e.getMessage());
            return "error";
        }
    }

    @RequestMapping("/checkSelectedList.do")
    public String getUserSelectedPictures(HttpServletRequest request){     //查看用户选修照片
        String pageNum = request.getParameter("page_num");
        String recordPerPage = request.getParameter("record_per_page");
        String customerId = request.getParameter("customerId");
        String photographersId = request.getParameter("photographers_id");
        if(StringUtils.isEmpty(customerId)){
            logger.info("[getUserSelectedPictures] wrong paramter");
            return "error";
        }
        try{
            int pageNumInt = 1;
//            int recordPerPageInt = AoxiuConstant.AOXIU_RECORD_PER_PAGE;
            int recordPerPageInt = 100;
            if(!StringUtils.isEmpty(pageNum)){
                pageNumInt = Integer.valueOf(pageNum);
            }
            if(!StringUtils.isEmpty(recordPerPage)){
                recordPerPageInt = Integer.valueOf(recordPerPage);
            }
            PaginationInfo paginationInfo = new PaginationInfo();
            List<Photo> photos = photoService.getSlectedPhotosByCustomerId(customerId,pageNumInt,recordPerPageInt,paginationInfo);
            request.setAttribute("data",photos);
            request.setAttribute("totalPages",11);
            request.setAttribute("totalPhotos",22);
            request.setAttribute("customerId",customerId);
            request.setAttribute("photographers_id",photographersId);
            return "check-list";
        }catch (Exception e){
            logger.error("[getUserSelectedPictures] error" + e.getMessage(),e);
            return "error";
        }
    }
    @RequestMapping("/getAlbumPictures.do")
    @ResponseBody
    public Object getAlbumPictures(HttpServletRequest request){
        String getCode = request.getParameter("getCode");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(getCode)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.error("[getAlbumPictures] get album pictures wrong parameter");
            return  resultMap;
        }
        try{
            List<PhotographersPhoto> photographersPhotos = orderService.getAlbumPictures(getCode);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,photographersPhotos);
            return  resultMap;

        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            logger.error("[getAlbumPictures] get album pictures  fail -> " + e.getMessage(),e);
            return  resultMap;
        }
    }

    @RequestMapping("/deletePictures.do")
    @ResponseBody
    public Object deletePictures(HttpServletRequest request,HttpServletResponse response){
        String photoIds = request.getParameter("photoIds");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(photoIds)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.error("[deletePictures] delete pictures wrong parameter");
            return  resultMap;
        }
        try{
            int count = orderService.deletePictures(photoIds);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,count);

        }catch (Exception e){
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            logger.error("[deletePictures] delete pictures fail -> " + e.getMessage());
            return resultMap;
        }

        return  resultMap;
    }

    @RequestMapping("/updatePicturesName")
    @ResponseBody
    public Object updatePicturesName(HttpServletRequest request,HttpServletResponse response){
        String jsonStr = request.getParameter("jsonStr");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(jsonStr)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.error("[updatePicturesName] updates pictures wrong parameter");
            return  resultMap;
        }
        try{
            int count = orderService.updatePicturesName(jsonStr);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,count);
        }catch (Exception e){
            logger.error("[updatePicturesName] update pictures fail -> " + e.getMessage());
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
        }
        return resultMap;
    }

    @RequestMapping("/addSelecedPictures.do")
    @ResponseBody
    public Object addSelecedPictures(HttpServletRequest request){    // 客户选择选修照片
        String picIds = request.getParameter("picIds");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(picIds)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.error("[addSelecedPictures] add selected pictures wrong parameter");
            return  resultMap;
        }
        try{
            int count = orderService.addSelectedPictures(picIds);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put(ComRet.retData,count);
        }catch (Exception e){
            logger.error("[addSelecedPictures] add selected  pictures fail -> " + e.getMessage());
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
        }
        return resultMap;
    }
    @RequestMapping("/getToken")
    @ResponseBody
    public Object getToken(HttpServletRequest request,HttpServletResponse response){
        String type = request.getParameter("type");
        GeneratorToken generatorToken = new GeneratorToken();
        String token = null;
        if(type.equals("0")){
            token = generatorToken.getUpToken0();
        }else if(type.equals("1")){
            token = generatorToken.getUpToken1();
        }else if(type.equals("2")){
            token = generatorToken.getUpToken2();
        }else if(type.equals("3")){
            token = generatorToken.getUpToken3();
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("uptoken",token);
        return resultMap;
    }
    @RequestMapping("/getUserContent")
    public String goCustomerUnselectPicturesContent(HttpServletRequest request,HttpServletResponse response){
        String getCode = request.getParameter("getCode");
        String type = request.getParameter("type");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(getCode) || StringUtils.isEmpty(type)){
            logger.error("[goCustomerUnselectPicturesContent] get pictures content wrong parameter");
            return "error";
        }
        try{
            Map<String,Object> map = orderService.getPhotoContentByCode(getCode,type);
            request.setAttribute("resultMap",map);
            return "getUserContent";
        }catch (Exception e){
            logger.error("[goCustomerUnselectPicturesContent] get pictures content wrong parameter");
            return "error";
        }
    }

    @RequestMapping("/album.do")
    @ResponseBody
    public Object getPhotographersAlbum(HttpServletRequest request){   //获得某一目录下的所有资源
        String photograperId = request.getParameter("photograper_id");
        String albumId = request.getParameter("album_id");
        String orderId = request.getParameter("orderId");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(photograperId) || (StringUtils.isEmpty(orderId) && StringUtils.isEmpty(albumId))){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.error("[getPhotographersAlbum] get photographers album  wrong parameter");
            return  resultMap;
        }
        try{
            List<PhotographersPhoto> photographersPhotos = null;
            List<PhotographersContent> photographersContents = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            if (!StringUtils.isEmpty(albumId)) {
                 photographersPhotos = orderService.getPhotosByAlbumId(albumId);
                 photographersContents = orderService.getContentByAlbumId(albumId);
            } else if(!StringUtils.isEmpty(orderId)) {
                PhotographersContent photographersContent = orderService.getMasterContents(orderId);
                photographersContents = orderService.getContentByAlbumId(photographersContent.getId() + "");
                photographersPhotos = orderService.getOrderMainContentPhotos(orderId);
            }
            map.put("albums", photographersContents);
            map.put("photos", photographersPhotos);
            resultMap.put(ComRet.retData,map);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
        }catch (Exception e){
            logger.error("[getPhotographersAlbum] get photographers album pictures fail -> " + e.getMessage());
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
        }

        return  resultMap;
    }

    @RequestMapping("/photographersAlbums.do")
    @ResponseBody
    public Object getPhotographersAlbums(HttpServletRequest request){
        String photograperId = request.getParameter("photograper_id");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(photograperId)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.error("[getPhotographersAlbum] get photographers album  wrong parameter");
            return  resultMap;
        }
        try{
            List<PhotographersContent> photographersContents = orderService.getPhotographersAlbumsByPhotographersId(photograperId);
            resultMap.put(ComRet.retCode,ComRet.SUCESS);
            resultMap.put(ComRet.retDesc,ComRet.SUCESS_DESC);
            resultMap.put("albums",photographersContents);
        }catch (Exception e){
            logger.error("[getPhotographersAlbum] get photographers album pictures fail -> " + e.getMessage());
            resultMap.put(ComRet.retCode,ComRet.FAIL);
            resultMap.put(ComRet.retDesc,ComRet.FAIL_DESC);
            return resultMap;
        }

        return  resultMap;
    }

    @RequestMapping("/getSelectedPhotosByOrderId.do")
    public String getSelectedPhotosByOrderId(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(orderId)){
            resultMap.put(ComRet.retCode,ComRet.WRONG_PARAMETER);
            resultMap.put(ComRet.retDesc,ComRet.WRONG_PARAMETER_DESC);
            logger.info("[getSelectedPhotosByOrderId] get selected photos by orderId wrong parameter");
            return  "error";
        }

        try{
            List<Photo> selectedPhotos = photoService.getSelectedPhotosByOrderId(orderId);
            resultMap.put(ComRet.retData,selectedPhotos);
            request.setAttribute("selectedPhotos",selectedPhotos);
            return "";
        }catch (Exception e){
            logger.error("[getSelectedPhotosByOrderId] get selected photos by orderId wrong parameter");
            return "error";
        }
    }




}
