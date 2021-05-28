package com.webblog.service;

import com.webblog.POJO.PostMapper;
import com.webblog.model.Person;
import com.webblog.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


public interface PostService {
    boolean createPost(MultipartFile file, Person user, String body, String title);
    Post getPostById(Long postId);
    String updatePost(Person person, Post post);
    String deletePost(Long postId, Person person);
    List<PostMapper> getPost(Person currentUser);
    List<Post> displayAllPostByFollower(Long id, Person person);
}
