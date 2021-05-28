package com.webblog.service;

import com.webblog.model.Person;

public interface LikesService {
    boolean likePost(Person person, Long postId, String action);
}
