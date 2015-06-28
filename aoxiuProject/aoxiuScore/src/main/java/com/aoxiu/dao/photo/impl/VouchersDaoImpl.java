package com.aoxiu.dao.photo.impl;

import com.aoxiu.dao.BaseDao;
import com.aoxiu.meta.photo.Voucher;

import java.util.List;

/**
 * Created by panchao on 15/6/10.
 */
public class VouchersDaoImpl extends BaseDao implements VouchersDao {
    @Override
    public int insertVoucher(Voucher voucher) {
        return getSqlSession().insert("voucher.insertVoucher",voucher);
    }

    @Override
    public List<Voucher> getVoucherByUserId(String userId) {
        return getSqlSession().selectList("voucher.getVoucherByUserId", userId);
    }
}
