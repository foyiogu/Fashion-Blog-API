package com.webblog.repository;

import com.webblog.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByCurrentUserIdAndAndFollowerId(Long currentUserId, Long followeeId);
    List<Follow> findAllByCurrentUserId(Long currentUserId);
    List<Follow> findAllByFollowerId(Long  currentUserId);

}
