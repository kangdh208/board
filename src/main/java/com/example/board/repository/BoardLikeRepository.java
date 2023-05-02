package com.example.board.repository;

import com.example.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByUserIdAndPostId(Long userId, Long boardId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}
