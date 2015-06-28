package com.aoxiu.dao.photo;

import com.aoxiu.meta.photo.QrCode;

/**
 * Created by panchao on 15/5/30.
 */
public interface QrCodeDao {
    /**
     * 通过orderId获得 QrCode
     * @param orderId
     * @return
     */
    public QrCode getQrCodeByOrderId(String orderId);

    /**
     * 插入
     * @param qrCode
     * @return
     */
    public int insertQrCode(QrCode qrCode);
}
