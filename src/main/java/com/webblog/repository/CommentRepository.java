package com.webblog.repository;

import com.webblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
}
