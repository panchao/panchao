package com.aoxiu.dao.photo.impl;

import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.AlbumDao;
import com.aoxiu.meta.photo.Album;

import java.util.List;

/**
 * Created by panchao on 15/5/18.
 */
public class AlbumDaoImpl extends BaseDao implements AlbumDao {
    @Override
    public List<Album> getAlbums() {
        return getSqlSession().selectList("album.selectAlbum");
    }

    @Override
    public Album getAlbum(String id) {
        return getSqlSession().selectOne("album.selectAlbumById",id);
    }

    @Override
    public List<Album> getAlbumsByIds(List<String> ids) {
        return getSqlSession().selectList("album.selectAlbumsByIds",ids);
    }
}
