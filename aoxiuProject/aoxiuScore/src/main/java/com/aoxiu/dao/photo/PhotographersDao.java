package com.aoxiu.dao.photo;

import com.aoxiu.meta.photo.Photographers;
import com.aoxiu.meta.photo.UserCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by panchao on 15/4/25.
 */
@Repository("photographersDao")
public interface PhotographersDao {

    public Photographers getPhotographersByUserId(String userId);

    public Photographers getPhotographersByUserName(String userName);

    public Photographers getPhotographersByPhoneNumber(String phoneNumber);

    public List<Photographers> getPhotographersBatch(List<UserCompany> userCompanies);


}
