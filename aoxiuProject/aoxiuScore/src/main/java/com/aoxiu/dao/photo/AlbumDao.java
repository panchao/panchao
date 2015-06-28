package com.aoxiu.dao.photo;

import com.aoxiu.meta.photo.Album;

import java.util.List;

/**
 * Created by panchao on 15/5/18.
 */
public interface AlbumDao {
    /**
     *
     * @return
     */
    public List<Album> getAlbums();

    /**
     *
     * @param id
     * @return
     */
    public Album getAlbum(String id);

    /**
     *
     * @param ids
     * @return
     */
    public List<Album> getAlbumsByIds(List<String> ids);

}
