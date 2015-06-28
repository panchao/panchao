package com.aoxiu.dao.photo.impl;

import com.aoxiu.meta.photo.Voucher;

import java.util.List;

/**
 * Created by panchao on 15/6/10.
 */
public interface VouchersDao {
    public int insertVoucher(Voucher voucher);
    public List<Voucher> getVoucherByUserId(String userId);

}
