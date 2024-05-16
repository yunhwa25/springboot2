package org.fullstack4.springboot2.repository;

import lombok.extern.log4j.Log4j2;
import org.fullstack4.springboot2.domain.BaseEntity;
import org.fullstack4.springboot2.domain.BoardEntity;
import org.fullstack4.springboot2.domain.BoardReplyEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class BoardReplyRepositoryTests {
    @Autowired(required = false)
    private BoardReplyRepository boardReplyRepository;

    @Test
    public void testRegist() {
        int bbs_idx = 2;
        BoardEntity board = BoardEntity.builder().idx(bbs_idx).build();

        IntStream.rangeClosed(1, 110).forEach(i -> {
            BoardReplyEntity reply = BoardReplyEntity.builder()
                    .board_idx(bbs_idx)
                    .user_id("test34")
                    .title("댓글 제목 테스트 " + i)
                    .content("댓글 내용 테스트 " + i)
                    .reg_date(LocalDateTime.now())
                    .build();

            BoardReplyEntity result = boardReplyRepository.save(reply);
            log.info("result : {} ", result);
        });
    }

}
