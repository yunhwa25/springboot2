package org.fullstack4.springboot2.repository;

import org.fullstack4.springboot2.domain.BoardReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardReplyRepository extends JpaRepository<BoardReplyEntity, Integer> {
    @Query("SELECT r FROM BoardReplyEntity r WHERE r.board_idx = :bbs_idx")
    Page<BoardReplyEntity> listOfBoardReply(@Param("bbs_idx") Integer bbs_idx, Pageable pageable);

}
