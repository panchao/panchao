package com.aoxiu.dao.photo.impl;

import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.IDGeneratorDao;
import com.aoxiu.meta.photo.IDGenerator;

/**
 * Created by panchao on 15/4/26.
 */
public class IDGeneratorDaoImpl extends BaseDao implements IDGeneratorDao{
    @Override
    public IDGenerator getIDGerator() {
        return getSqlSession().selectOne("idgenerator.getID");
    }
}
