package com.webblog.repository;

import com.webblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostById(Long postId);
    Post findPostByIdAndPersonId(Long postId, Long personId);
    Post findPostByTitleAndBody(String title, String body);
    List<Post> findAllByStatusIsOrderById(String status);
    List<Post> findPostByPersonId(Long id);
}
