package com.aoxiu.dao.photo;

import com.aoxiu.meta.photo.Appraisal;

import java.util.List;

/**
 * Created by panchao on 15/5/20.
 */
public interface AppraisalDao {
    public int insertAppraisal(Appraisal appraisal);
    public Appraisal selectAppraisalsByOrderId(String orderId);
}
