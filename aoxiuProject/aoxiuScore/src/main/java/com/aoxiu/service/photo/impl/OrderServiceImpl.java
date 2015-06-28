package com.aoxiu.service.photo.impl;

import com.aoxiu.AoxiuConstant;
import com.aoxiu.AoxiuOrderStatus;
import com.aoxiu.common.PaginationInfo;
import com.aoxiu.dao.photo.*;
import com.aoxiu.meta.photo.*;
import com.aoxiu.meta.photo.common.OrderDetail;
import com.aoxiu.meta.photo.common.PhotographerOrderCommon;
import com.aoxiu.meta.photo.common.PhotographersContent;
import com.aoxiu.meta.photo.common.PhotographersPhoto;
import com.aoxiu.service.photo.OrderService;
import com.aoxiu.utils.GeneratorCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by panchao on 15/5/1.
 */
public class OrderServiceImpl implements OrderService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private PhotographerOrderDao photographerOrderDao;
    @Resource
    private PhotoContentDao photoContentDao;
    @Resource
    private PhotoDao photoDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private QrCodeDao qrCodeDao;


    @Override
    public int updateOrder(String waterMark, String maxCount, String orderId, String getCode,String price) {
        PhotographerOrder photographerOrder = new PhotographerOrder();
        if (!StringUtils.isEmpty(maxCount)) {
            photographerOrder.setMaxSelectCount(Integer.parseInt(maxCount));
        }
        if (!StringUtils.isEmpty(waterMark)) {
            photographerOrder.setAddWaterMark(Integer.parseInt(waterMark));
        }
        photographerOrder.setOrderId(Integer.parseInt(orderId));
        photographerOrder.setUpdateTime(new Date());
        if(!StringUtils.isEmpty(price)){
            photographerOrder.setPricePerPhoto(Double.valueOf(price));
        }
        if (!StringUtils.isEmpty(getCode)) {
            photographerOrder.setGetCode(getCode);
        }
//        photographerOrder.setT
        return photographerOrderDao.updatePhotographerOrder(photographerOrder);
    }

    @Override
    @Transactional
    public Map<String, Object> addOrder(PhotographerOrder photographerOrder, String type) {
        //TODO
        Map<String, Object> map = new HashMap<>();
        if (type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR)) {
            Date date = new Date();
            photographerOrder.setCreateTime(date);
            photographerOrder.setUpdateTime(date);
            photographerOrder.setSelectPhotoType(1);     //1表示摄影师 2表示婚庆
            PhotographerOrder tempOrder = orderIsExisted(photographerOrder);
            if (tempOrder != null) {
                photographerOrder.setOrderId(tempOrder.getOrderId());
                photographerOrderDao.updatePhotographerOrder(photographerOrder);
                PhotoContent photoContent = photoContentDao.getMainContent(tempOrder.getOrderId() + "");
                map.put("orderId", tempOrder.getOrderId());
                map.put("albumId", photoContent.getPhotoContentId());
                return map;
            } else {
                photographerOrderDao.insertPhotographerOrder(photographerOrder);
                int contentId = addNewContent(AoxiuConstant.AOXIU_ORDER_MASTER_CONTENT_NAME, photographerOrder.getOrderId() + "", null);   // 创建主目录
                map.put("orderId", photographerOrder.getOrderId());
                map.put("albumId", contentId);
                return map;
            }


        } else if (type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_COMPNAY)) {
            //TODO
            return map;
        }
//        }
        return null;
    }

    private PhotographerOrder orderIsExisted(PhotographerOrder photographerOrder) {
        PhotographerOrder photographerOrder1 = photographerOrderDao.getPhotographerOrderByUserIdAndPhotographersId(photographerOrder.getUserId(), photographerOrder.getPhotographersId());
        return photographerOrder1;
    }

    @Override
    public int deleteOrder(String orderId, String type) {
        if (type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR)) {    //  摄影师订单
            PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderById(orderId);
            if (photographerOrder.getGetCode() != null) {
                return -1;
            } else {
                return photographerOrderDao.deletePhotographerOrder(orderId);
            }
        } else if (type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_COMPNAY)) {    //婚庆公司订单
            //TODO
        }
        return 0;
    }


    @Override
    public int addNewContent(String contentName, String orderId, String photoId) {
        PhotoContent photoContent = photoContentDao.getOrderMasterContentByOrderId(orderId);
        PhotoContent photoContentNew = null;
        if (photoContent == null) {            //没有主目录,创建主目录
            photoContentNew = new PhotoContent(contentName, 40, Integer.valueOf(orderId), 0);  // TODO  默认添加封面40
        } else {                              //有主目录，创建子目录
            photoContentNew = new PhotoContent(contentName, 40, Integer.valueOf(orderId), photoContent.getPhotoContentId());  // TODO  默认添加封面40
        }
        int contentCount = photoContentDao.insertPhotoContent(photoContentNew);
        return photoContentNew.getPhotoContentId();
    }

    @Override
    public Map<String, String> getPicturesUrl(String contentId) {
        List<Photo> photos = photoDao.getPhotosByContentId(contentId);
        PhotoContent photoContents = photoContentDao.getPhotoContentById(contentId);
        Map<String, String> picturesUrl = new HashMap<>();
        if (photos != null) {
            for (int i = 0; i < photos.size(); i++) {
                Photo tmpPhoto = photos.get(i);
                picturesUrl.put(((Integer) tmpPhoto.getPhotoId()).toString(), AoxiuConstant.AOXIU_PICTURES_PREFIX + tmpPhoto.getPhotoNameOld());
            }
        }
        return picturesUrl;
    }

    @Override
    public Map<String, Object> getPhotoContentByCode(String getCode, String type) {
        Map<String, Object> map = new HashMap<>();
        if (type.equals("selected")) {
//            PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByGetCodeSelected(getCode);
        } else {
            List<PhotoContent> photoContents = null;
            PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByGetCode(getCode);
            String resultStr = "";
            if (photographerOrder != null) {
                photoContents = photoContentDao.getPhotoContentsByOrderId(Integer.valueOf(photographerOrder.getOrderId()).toString());
                if (photoContents != null) {
                    Map<String, Object> map1 = new HashMap<>();
                    for (int i = 0; i < photoContents.size(); i++) {
                        Photo photo = null;
                        PhotoContent photoContent = null;
                        photo = photoDao.getPhotosByPhotoId(((Integer) photoContents.get(i).getContentPhotoId()).toString());
                        if (photo != null) {
//                            photoContent = photoContentDao.getPhotoContentById(((Integer)photo.getPhotoContentId()).toString());
//                            resultStr += photoContents.get(i).getContentName() + ":" + photo.getPhotoNameOld();
//                            resultStr = resultStr + ",";
                            map1.put(photoContents.get(i).getContentName(), AoxiuConstant.AOXIU_PICTURES_PREFIX + photo.getPhotoNameOld() + "?id=" + photoContents.get(i).getPhotoContentId());
                        }
                    }
                    map.put("size", photoContents.size());
                    map.put("urls", map1);
                }
            }
            return map;
        }
        return null;
    }

    @Override
    public boolean updateContent(String contentId, String contentName, String contentPhoto) {
        Map<String, Object> params = new HashMap<>();
        params.put("contentId", contentId);
        params.put("contentName", contentName);
        params.put("contentPhoto", contentPhoto);
        int count = photoContentDao.updateContent(params);
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int deletePictures(String pictures) {
        if (pictures == null || pictures.trim().equals("")) {
            return 0;
        }
        String[] pics = pictures.split(",");
        List<String> picList = new ArrayList<>();
        for (int i = 0; i < pics.length; i++) {
            picList.add(pics[i]);
        }
        return photoDao.deletePhotos(picList);

    }

    @Override
    public int updatePicturesName(String jsonStr) {
        JSONArray jsonArray = JSONArray.fromObject(jsonStr.toUpperCase());
        if (jsonArray != null && jsonArray.size() > 0) {
            List<Photo> photoLists = new ArrayList<>(jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Photo photo = new Photo();
                photo.setPhotoId(jsonObject.getInt("PHOTOID"));
                photo.setPhotoNameNew(jsonObject.getString("PHOTONEWNAME"));
                photoLists.add(photo);
            }
            return photoDao.updatePictures(photoLists);
        } else {
            return 0;
        }
    }

    @Override
    public int addSelectedPictures(String strs) {
        String[] ids = strs.split(",");
        List<Photo> photos = new ArrayList<>(ids.length);
        for (int i = 0; i < ids.length; i++) {
            Photo photo = new Photo();
            photo.setPhotoId(Integer.parseInt(ids[i]));
            photo.setPhotoType(2);
            photos.add(photo);
        }
        return photoDao.updatePictures(photos);
    }

    @Override
    public Map<String, String> getSelectedPhotos(String getCode) {
        PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByGetCode(getCode);
        if (photographerOrder != null) {
            Map<String, String> resultMap = new HashMap<>();
            List<PhotoContent> photoContents = photoContentDao.getPhotoContentsByOrderId(Integer.valueOf(photographerOrder.getOrderId()).toString());
            List<String> contentIds = new ArrayList<>();
            List<Photo> photos = null;
            for (int i = 0; photoContents != null && i < photoContents.size(); i++) {
                contentIds.add(((Integer) (photoContents.get(i).getPhotoContentId())).toString());
            }
            if (contentIds.size() > 0) {
                photos = photoDao.getSelectedPhotos(contentIds);
            }
            for (int i = 0; photos != null && i < photos.size(); i++) {
                Photo photo = photos.get(i);
                photo.getUpdateTimeStr();
                resultMap.put(photo.getPhotoNameOld(), photo.getUpdateTimeStr());
            }
            return resultMap;
        }
        return null;
    }

    @Override
    public List<PhotographerOrderCommon> getPhotographerOrders(String photographerId,
                                                               int pageNum, int recordPerPage, PaginationInfo paginationInfo) {
        List<PhotographerOrder> photographerOrderList = photographerOrderDao.getPhotographerOrdersByPhotographersIdByCond(
                photographerId, pageNum, recordPerPage, paginationInfo);
        List<PhotographerOrderCommon> photographerOrderCommons = new ArrayList<>();
        for (int i = 0; photographerOrderList != null && i < photographerOrderList.size(); i++) {
            PhotographerOrder temp = photographerOrderList.get(i);
            PhotographerOrderCommon tempCom = new PhotographerOrderCommon();
            tempCom.setAddWaterMark(temp.getAddWaterMark());
            tempCom.setCreateTime(temp.getCreateTime().toString());
            tempCom.setExtraPricePerPhoto(temp.getPricePerPhoto());
            tempCom.setMaxSelectCount(temp.getMaxSelectCount());
            tempCom.setOrderId(temp.getOrderId());
            tempCom.setWaterMarkType(temp.getWatermarkType());
            tempCom.setCustomerId(temp.getUserId());
            Customer customer = customerDao.getCustomerByUserId(temp.getUserId());
            if (temp.getGetCodeSelected() != null) {              //已经完成修片
                tempCom.setStatus("已经完成修片");
            } else if (temp.getGetCode() == null) {                //订单还没创建
                tempCom.setStatus("还没有上传原片");

            } else {
                if (customerSelectedPhotos(temp.getOrderId())) {  //正在修片
                    tempCom.setStatus("正在修片");
                } else {                                          //客户还没有选择要修的片子
                    tempCom.setStatus("客户还没有选择要修的片子");
                }
            }
            tempCom.setCustomerName(customer.getRealName());
            photographerOrderCommons.add(tempCom);
        }
        return photographerOrderCommons;

    }

    private boolean customerSelectedPhotos(int orderId) {
        List<PhotoContent> photoContents = photoContentDao.getPhotoContentsByOrderId(orderId + "");
        if (photoContents != null) {
            for (int i = 0; i < photoContents.size(); i++) {
                if (photoDao.getSelectedPhotos(photoContents.get(i).getPhotoContentId()) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getPhotographersContents(String photographersId) {
        List<PhotographerOrder> photographerOrderList = photographerOrderDao.getPhotographerOrdersByPhotographersIdByCond(photographersId, 0, 100000, null);
        if (photographerOrderList != null && photographerOrderList.size() > 0) {
            List<String> orderds = new ArrayList<>(photographerOrderList.size());
            for (int i = 0; i < photographerOrderList.size(); i++) {
                orderds.add(((Integer) photographerOrderList.get(i).getOrderId()).toString());
            }
            List<PhotographersContent> photographersContents = photoContentDao.getPhotographersContentsByOrderId(orderds);
            List<PhotographersPhoto> photographersPhotos = photoDao.getPhotosContentId(photographersContents);
        }
        return null;
    }

    @Override
    public List<PhotographersPhoto> getPhotosByAlbumId(String albumId) {
        List<Photo> photos = photoDao.getPhotosByContentId(albumId);
        if (photos != null && photos.size() > 0) {
            List<PhotographersPhoto> photographersPhotos = new ArrayList<>();
            for (int i = 0; i < photos.size(); i++) {
                Photo temp = photos.get(i);
                PhotographersPhoto photographersPhoto = new PhotographersPhoto();
                photographersPhoto.setName(temp.getPhotoNameNew() == null ? temp.getPhotoNameOld() : temp.getPhotoNameNew());
                photographersPhoto.setSrc(temp.getPhotoSrc() + temp.getPhotoNameOld());
                photographersPhotos.add(photographersPhoto);
            }

            return photographersPhotos;
        }
        return null;
    }

    @Override
    public List<PhotographersContent> getContentByAlbumId(String albumId) {
        return photoContentDao.getPhotographersContentsByParentId(albumId);
    }

    @Override
    public List<PhotographersPhoto> getAlbumPictures(String getCode) {
        PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByGetCode(getCode);
        List<PhotographersPhoto> photographersPhotos = new ArrayList<>();
        if (photographerOrder != null) {
            List<PhotoContent> photoContents = photoContentDao.getPhotoContentsByOrderId(Integer.valueOf(photographerOrder.getOrderId()).toString());
            List<String> contentIds = new ArrayList<>();
            List<Photo> photos = null;
            for (int i = 0; photoContents != null && i < photoContents.size(); i++) {
                contentIds.add(((Integer) (photoContents.get(i).getPhotoContentId())).toString());
            }
            if (contentIds.size() > 0) {
                photos = photoDao.getSelectedPhotos(contentIds);
            }
            for (int i = 0; photos != null && i < photos.size(); i++) {
                Photo photo = photos.get(i);
                PhotographersPhoto photographersPhoto = new PhotographersPhoto();
                photographersPhoto.setName(photo.getPhotoNameOld());
                photographersPhoto.setSrc(photo.getPhotoSrc() + "/" + photo.getPhotoNameOld());
                photographersPhoto.setId(((Integer) photo.getPhotoId()).toString());
                photographersPhotos.add(photographersPhoto);
            }
        }
        return photographersPhotos;
    }

    @Override
    public List<PhotographersContent> getPhotographersAlbumsByPhotographersId(String photographersId) {
        List<PhotographerOrder> orders = photographerOrderDao.
                getPhotographerOrdersByPhotographersIdByCond(photographersId, 0, Integer.MAX_VALUE, null);
        List<String> orderIds = new ArrayList<>();
        if (orders != null && orders.size() > 0) {
            for (int i = 0; i < orders.size(); i++) {
                orderIds.add(((Integer) orders.get(i).getOrderId()).toString());
            }
        }
        List<PhotographersContent> photoContents = photoContentDao.getPhotographersContentsByOrderId(orderIds);
        return photoContents;
    }

    @Override
    public Map<String, Object> getEntrance(String orderId) {
        PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderById(orderId);
        Map<String, Object> resultMap = new HashMap<>();
        if (photographerOrder.getGetCode() != null) {
            resultMap.put("getCode", photographerOrder.getGetCode());
            resultMap.put("qrCode", "pictures/getAlbumPictures.do?getCode=" + photographerOrder.getGetCode());   //TODO
        } else {
            String code = GeneratorCode.generatorCode();
            updateOrder(null, null, orderId, code,null);
            resultMap.put("getCode", code);
            resultMap.put("qrCode", "pictures/getAlbumPictures.do?getCode=" + code);    //TODO
        }

        return resultMap;
    }

    @Override
    public String isGeneratorGetCode(String orderId, int type) throws Exception {
        PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderById(orderId);
        if (photographerOrder != null) {
            if (type == AoxiuConstant.AOXIU_PHOTO_UNSELECTED) {
                if (photographerOrder.getGetCode() != null) {
                    return photographerOrder.getGetCode();
                } else {
                    return null;
                }
            } else if (type == AoxiuConstant.AOXIU_PHOTO_SELECTED) {
                if (photographerOrder.getGetCodeSelected() != null) {
                    return photographerOrder.getGetCodeSelected();
                } else {
                    return null;
                }
            } else {
                throw new Exception("system error - > type error,  type =  " + type);
            }
        } else {
            throw new Exception("system error - > can not find photographer order,orderId = " + orderId);
        }
    }

    @Override
    public PhotographersContent getMasterContents(String orderId) {     //TODO 没必要封装
        PhotoContent photoContent = photoContentDao.getOrderMasterContentByOrderId(orderId);
        if (photoContent == null) {
            return null;
        }
        PhotographersContent photographersContent = new PhotographersContent();
        photographersContent.setId(photoContent.getPhotoContentId());
        photographersContent.setName(photoContent.getContentName());
        return photographersContent;
    }

    @Override
    public OrderDetail getOrderDetailByOrderId(String orderId) {
        PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderById(orderId);
        QrCode qrCode = null;
        if (photographerOrder != null) {
            Customer customer = customerDao.getCustomerByUserId(photographerOrder.getUserId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCustomerName(customer.getRealName());
            orderDetail.setOrderId(Integer.valueOf(orderId));
            orderDetail.setPhoneNumber(customer.getPhoneNumber());
            orderDetail.setSelectedCount(photographerOrder.getMaxSelectCount());
            orderDetail.setOrderId(photographerOrder.getOrderId());
            qrCode = qrCodeDao.getQrCodeByOrderId(photographerOrder.getOrderId() + "");
            if(photographerOrder.getGetCode() == null){
                orderDetail.setUnSelectedUrl("/img/logo.png");
            }else{
                if(qrCode != null && qrCode.getGetUnselectedUrl() != null){
                    orderDetail.setUnSelectedUrl(qrCode.getGetUnselectedUrl());
                }else{
                    orderDetail.setUnSelectedUrl("/img/logo.png");
                }
            }
            if(photographerOrder.getGetCodeSelected() == null){
                orderDetail.setSelectedUrl("/img/logo.png");
            }else{
                if(qrCode != null && qrCode.getGetselectedUrl() != null){
                    orderDetail.setSelectedUrl(qrCode.getGetselectedUrl());
                }else{
                    orderDetail.setSelectedUrl("/img/logo.png");
                }
            }
            orderDetail.setPricePerPhoto(photographerOrder.getPricePerPhoto());
            orderDetail.setPhotographerId(photographerOrder.getPhotographersId());
            orderDetail.setUserId(photographerOrder.getUserId());
            if (photographerOrder.getSelectPhotoType() == 1) {
                orderDetail.setOrderType("原片");
            } else {
                orderDetail.setOrderType("精修片");
            }
            return orderDetail;
        }
        return null;
    }

    @Override
    public List<PhotographersPhoto> getOrderMainContentPhotos(String orderId) {
        List<PhotographersPhoto> photographersPhotos = new ArrayList<>();
        PhotoContent mainContent = photoContentDao.getMainContent(orderId);
        if (mainContent == null) {
            return photographersPhotos;
        }
        List<Photo> photos = photoDao.getPhotosByContentId(mainContent.getPhotoContentId() + "");
        for (int i = 0; photos != null && i < photos.size(); i++) {
            Photo photo = photos.get(i);
            PhotographersPhoto temp = new PhotographersPhoto();
            temp.setId(photo.getPhotoId() + "");
            temp.setSrc(photo.getPhotoSrc() + photo.getPhotoNameOld());
            temp.setName(photo.getPhotoNameNew() == null ? photo.getPhotoNameOld() : photo.getPhotoNameNew());
            photographersPhotos.add(temp);
        }
        return photographersPhotos;
    }

    @Override
    public OrderDetail getOrderDetailByCustomerId(String customerId) {
        PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByUserId(customerId);
        if (photographerOrder == null) {
            return null;
        }
        Customer customer = customerDao.getCustomerByUserId(Integer.valueOf(customerId));
        QrCode qrCode = qrCodeDao.getQrCodeByOrderId(photographerOrder.getOrderId() + "");
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setPricePerPhoto(photographerOrder.getPricePerPhoto());
        orderDetail.setOrderId(photographerOrder.getOrderId());
        if(photographerOrder.getGetCode() == null){
            orderDetail.setUnSelectedUrl("/img/logo.png");
        }else{
            if(qrCode != null && qrCode.getGetUnselectedUrl() != null){
                orderDetail.setUnSelectedUrl(qrCode.getGetUnselectedUrl());
            }else{
                orderDetail.setUnSelectedUrl("/img/logo.png");
            }
        }
        if(photographerOrder.getGetCodeSelected() == null){
            orderDetail.setSelectedUrl("/img/logo.png");
        }else{
            if(qrCode != null && qrCode.getGetselectedUrl() != null){
                orderDetail.setSelectedUrl(qrCode.getGetselectedUrl());
            }else{
                orderDetail.setSelectedUrl("/img/logo.png");
            }
        }
        orderDetail.setSelectedCount(photographerOrder.getMaxSelectCount());
        orderDetail.setCustomerName(customer.getRealName());
        orderDetail.setPhoneNumber(customer.getPhoneNumber());
        orderDetail.setUserId(customer.getCustomerId());
        orderDetail.setPhotographerId(photographerOrder.getPhotographersId());
        if (photographerOrder.getSelectPhotoType() == 1) {
            orderDetail.setOrderType("原片");
        } else {
            orderDetail.setOrderType("精修片");
        }

        return orderDetail;
    }

    @Override
    public List<PhotographerOrderCommon> searchOrdersByKey(String key, String type, String photographersId,
                                                           PaginationInfo paginationInfo, int pageNum, int recordPerPage) {
        List<PhotographerOrderCommon> photographerOrderCommons = new ArrayList<>();
        if (type.equals(AoxiuConstant.AOXIU_ORDER_SEARCH_CUSTOMER_NAME)) {
            Map<String, Object> params = new HashMap<>();
            params.put("photographerId", photographersId);
            params.put("customerName", key);
            params.put("pageNum", pageNum);
            params.put("recordPerPage", recordPerPage);
            List<Customer> customers = customerDao.getCustomerByNameAndPhotoIdByCond(params, paginationInfo);
            if (customers == null) {
                return photographerOrderCommons;
            } else {
                for (Customer customer : customers) {
                    PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByUserId(customer.getCustomerId() + "");
                    PhotographerOrderCommon photographerOrderCommon = new PhotographerOrderCommon();
                    photographerOrderCommon.setCustomerId(customer.getCustomerId());
                    photographerOrderCommon.setStatus(photographerOrder.getOrderStep() + "");
                    photographerOrderCommon.setOrderId(photographerOrder.getOrderId());
                    photographerOrderCommon.setCreateTime(photographerOrder.getCreateTime().toString());
                    photographerOrderCommon.setCustomerName(customer.getRealName());
//                    if(photographerOrder.getOrderStep().equals("A")){
//
//                    }
                    photographerOrderCommons.add(photographerOrderCommon);
                }
            }
        } else if (type.equals(AoxiuConstant.AOXIU_ORDER_SEARCH_CUSTOMER_STATUS)) {
            Map<String, Object> params = new HashMap<>();
            int status = 1;
            if (key.equals(AoxiuOrderStatus.ORIGIN_NOT_UPLOADED)) {
                status = 2;
            } else if (key.equals(AoxiuOrderStatus.ORIGIN_PAGE_NOT_GENERATED)) {
                status = 1;
            } else if (key.equals(AoxiuOrderStatus.SELECT_TRIM_INFO_NOT_SUBMITTED)) {
                status = 3;
            } else if (key.equals(AoxiuOrderStatus.SELECTED_NOT_UPLOADED)) {
                status = 4;
            } else if (key.equals(AoxiuOrderStatus.UNCOMMENTED)) {
                status = 5;
            } else {
                status = 6;
            }
            params.put("photographersId", photographersId);
            params.put("pageNum", pageNum);
            params.put("recordPerPage", recordPerPage);
            params.put("status", status);
            List<PhotographerOrder> photographerOrderList = photographerOrderDao.
                    getPhotographerOrderByStatusAndPhotographerIdByCond(params, paginationInfo);
            if (photographerOrderList != null) {
                for (PhotographerOrder photographerOrder : photographerOrderList) {
                    Customer customer = customerDao.getCustomerByUserId(photographerOrder.getUserId());
                    PhotographerOrderCommon photographerOrderCommon = new PhotographerOrderCommon();
                    photographerOrderCommon.setCustomerId(customer.getCustomerId());
                    photographerOrderCommon.setCustomerName(customer.getRealName());
                    photographerOrderCommon.setCreateTime(photographerOrder.getCreateTime().toString());
                    photographerOrderCommon.setOrderId(photographerOrder.getOrderId());
                    photographerOrderCommon.setStatus(photographerOrder.getOrderStep() + "");
                    photographerOrderCommons.add(photographerOrderCommon);
                }
            }
        }
        return photographerOrderCommons;

    }
    @Override
    public List<Photo> getSelectedPhotosByCustomerId(String customerId,int pageNum,int recordPerPage,PaginationInfo paginationInfo) {
        PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByUserId(customerId);
        if(photographerOrder != null){
            PhotoContent photoContent = photoContentDao.getMainContent(photographerOrder.getOrderId() + "");
            return photoDao.getSelectedPhotosByCond(photoContent.getPhotoContentId(),paginationInfo,pageNum,recordPerPage);
        }
        return null;
    }


//    private void getQrCodeSrc(String url,HttpResponse response){
//        int width = 300;
//        int height = 300;
//        //二维码的图片格式
//        String format = "gif";
//        Hashtable hints = new Hashtable();
//        //内容所使用编码
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        BitMatrix bitMatrix = null;
//        try {
//            bitMatrix = new MultiFormatWriter().encode(url,
//                    BarcodeFormat.QR_CODE, width, height, hints);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        //生成二维码
//        File outputFile = new File("d:"+File.separator+"new.gif");
//        try {
//            MatrixToImageWriter.writeToFile(bitMatrix, format, response.get);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    @Transactional
    public boolean uploadPhotos(String jsonStr) {
//        PhotoContent photoContent = photoContentDao.getPhotoContentById(contentId);
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        String contentId = jsonObject.getString("album_id");
        JSONArray photoNames = jsonObject.getJSONArray("names");
        if (contentId.equals("")) {//TODO  得新建目录
//            addNewContent("主目录",)
        }
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < photoNames.size(); i++) {
            Date date = new Date();
            String tempObject = (String) photoNames.get(i);
            Photo photo = new Photo(Integer.valueOf(contentId), AoxiuConstant.AOXIU_PHOTO_UNSELECTED, tempObject, null, date, "http://7xis67.com2.z0.glb.qiniucdn.com/");
            photos.add(photo);
        }
        int count = 0;
        for (int i = 0; i < photos.size(); i++) {
            count += photoDao.insertPhoto(photos.get(i));
        }

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
