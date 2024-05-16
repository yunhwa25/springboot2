package org.fullstack4.springboot2.service;

import lombok.extern.log4j.Log4j2;
import org.fullstack4.springboot2.dto.BoardReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Log4j2
@SpringBootTest
public class BoardReplyServiceTests {
    @Autowired
    private BoardReplyServiceIf boardReplyService;

    @Test
    public void testRegist() {
        BoardReplyDTO replyDTO = BoardReplyDTO.builder()
                .board_idx(2)
                .user_id("test")
                .title("제목 테스트")
                .content("내용 테스트")
                .reg_date(LocalDateTime.now())
                .build();

        int result = boardReplyService.regist(replyDTO);
        log.info("result : " + result);
    }

}
