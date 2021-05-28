package com.webblog.service;

import com.webblog.POJO.FavouriteMapper;
import com.webblog.model.Person;

import java.util.List;

public interface FavouriteService {
    List<FavouriteMapper> getFavouritesByUser(Person person);
    boolean saveFavourite(Person person, Long postId);

}
