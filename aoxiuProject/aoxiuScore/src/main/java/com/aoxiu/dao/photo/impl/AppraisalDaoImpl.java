package com.aoxiu.dao.photo.impl;

import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.AppraisalDao;
import com.aoxiu.meta.photo.Appraisal;

import java.util.List;

/**
 * Created by panchao on 15/5/20.
 */
public class AppraisalDaoImpl extends BaseDao implements AppraisalDao {
    @Override
    public int insertAppraisal(Appraisal appraisal) {
        return getSqlSession().insert("appraisal.insertAppraisal",appraisal);
    }

    @Override
    public Appraisal selectAppraisalsByOrderId(String orderId) {
        return getSqlSession().selectOne("appraisal.selectAppraisalsByOrderId",orderId);
    }
}
