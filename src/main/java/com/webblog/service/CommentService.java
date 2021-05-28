package com.webblog.service;

import com.webblog.POJO.CommentMapper;
import com.webblog.model.Comment;
import com.webblog.model.Person;

import java.util.List;

public interface CommentService {
    boolean createComment(Long userId, Long postId, String comment);
    List<CommentMapper> getCommentsByPostId(Long postId);
    boolean editComment(Long commentId, Person person, Long postId, String comment);
    boolean deleteComment(Long commentId);
}
