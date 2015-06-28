package com.aoxiu.service.photo.impl;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.dao.photo.CustomerDao;
import com.aoxiu.dao.photo.PhotoContentDao;
import com.aoxiu.dao.photo.PhotoDao;
import com.aoxiu.dao.photo.PhotographerOrderDao;
import com.aoxiu.meta.photo.Photo;
import com.aoxiu.meta.photo.PhotoContent;
import com.aoxiu.meta.photo.PhotographerOrder;
import com.aoxiu.service.photo.PhotoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by panchao on 15/5/31.
 */
public class PhotoServiceImpl implements PhotoService {
    @Resource
    private PhotoContentDao photoContentDao;
    @Resource
    private PhotoDao photoDao;
    @Resource
    private PhotographerOrderDao photographerOrderDao;

    @Override
    public List<Photo> getSelectedPhotosByOrderId(String orderId) {
        List<PhotoContent> photoContents = photoContentDao.getPhotoContentsByOrderId(orderId);
        if (photoContents != null && photoContents.size() > 0) {
            List<Photo> photos = new ArrayList<>();
            for (PhotoContent tem : photoContents) {
                List<Photo> tempPhotos = photoDao.getSelectedPhotos(tem.getPhotoContentId());
                photos.addAll(tempPhotos);
            }
            return photos;
        }
        return null;
    }

    @Override
    public List<Photo> getSlectedPhotosByCustomerId(String customerId, int pageNum, int recordPerPage, PaginationInfo paginationInfo) {
        PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByUserId(customerId);
        List<Photo> photos = new ArrayList<>();
        if (photographerOrder != null) {
            List<PhotoContent> photoContents = photoContentDao.getPhotoContentsByOrderId(photographerOrder.getOrderId() + "");
            List<Integer> contentiDs = new ArrayList<>();
            if (photoContents != null) {
                for (PhotoContent photoContent : photoContents) {
                    contentiDs.add(photoContent.getPhotoContentId());
                }
                photos = photoDao.getSelectedPhotosBatches(contentiDs);
            }
        }
        return photos;
    }

    @Override
    public String getSelectedPhotosNamesByCustomerId(String customerId) {
        PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByUserId(customerId);
        String result = "";
        if (photographerOrder != null) {
            List<PhotoContent> photoContents = photoContentDao.getPhotoContentsByOrderId(photographerOrder.getOrderId() + "");
            List<Integer> contentiDs = new ArrayList<>();
            if (photoContents != null) {
                for (PhotoContent photoContent : photoContents) {
                    contentiDs.add(photoContent.getPhotoContentId());
                }
                List<String>  names = photoDao.getSelectedPhotosNames(contentiDs);
                if(names != null){
                    for(String string:names){
                        result += string + "\n";
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<Photo> getUnSelectPhotosByCustomerId(String customerId) {
        PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByUserId(customerId);
        List<Photo> photos = new ArrayList<>();
        if (photographerOrder != null) {
            List<PhotoContent> photoContents = photoContentDao.getPhotoContentsByOrderId(photographerOrder.getOrderId() + "");
            List<Integer> contentiDs = new ArrayList<>();
            if (photoContents != null) {
                for (PhotoContent photoContent : photoContents) {
                    contentiDs.add(photoContent.getPhotoContentId());
                }
                photos = photoDao.getUnSelectPhotosBatches(contentiDs);
            }
        }
        return photos;    }
}
