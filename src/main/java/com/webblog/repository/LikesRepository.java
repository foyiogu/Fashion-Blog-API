package com.webblog.repository;

import com.webblog.model.Likes;
import com.webblog.model.Person;
import com.webblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LikesRepository extends JpaRepository<Likes, Long> {
    void deleteLikesByPostAndPerson(Post post, Person person);
    List<Likes> findAllByPostId(Long postId);
    List<Likes> findAllByPostIdAndPersonId(Long postId, Long personId);
}
