package com.aoxiu.dao.photo.impl;

import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.QrCodeDao;
import com.aoxiu.meta.photo.QrCode;

/**
 * Created by panchao on 15/5/30.
 */
public class QrCodeDaoImpl extends BaseDao implements QrCodeDao {
    @Override
    public QrCode getQrCodeByOrderId(String orderId) {
        return getSqlSession().selectOne("qrCode.getQrCodeByOrderId",orderId);
    }

    @Override
    public int insertQrCode(QrCode qrCode) {
        return getSqlSession().insert("qrCode.insertQrCode",qrCode);
    }
}
