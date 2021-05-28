package com.webblog.repository;

import com.webblog.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
   List<Favourite> findFavouriteByCurrentUserId(Long id);
   List<Favourite> findFavouriteByCurrentUserIdAndPostId(Long userId, Long postId);
}
