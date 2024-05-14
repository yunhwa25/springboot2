package org.fullstack4.springboot2.service;

import lombok.extern.log4j.Log4j2;
import org.fullstack4.springboot2.dto.BoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class BoardServiceTests {
    @Autowired
    private BoardServiceIf boardService;

    @Test
    public void testRegist() {
        log.info("=================================");
        log.info("BoardServiceTests >> testRegist START");

        log.info("boardService.getClass().getName() : {}", boardService.getClass().getName());
        BoardDTO boardDTO = BoardDTO.builder()
                .user_id("test")
                .title("제목 테스트")
                .content("내용 테스트")
                .display_date("2024-05-13")
                .build();
        int result = boardService.regist(boardDTO);

        log.info("boardDTO : {}", boardDTO);
        log.info("result : {}", result);
        log.info("BoardServiceTests >> testRegist END");
        log.info("=================================");
    }

    @Test
    public void testModify() {
        log.info("=================================");
        log.info("BoardServiceTests >> testModify START");

        BoardDTO boardDTO = BoardDTO.builder()
                .idx(13)
                .user_id("test")
                .title("제목 테스트 13")
                .content("내용 테스트 13")
                .display_date("2024-05-13")
                .build();
        boardService.modify(boardDTO);

        log.info("boardDTO : {}", boardDTO);
        log.info("BoardServiceTests >> testModify END");
        log.info("=================================");
    }

    @Test
    public void testView() {
        log.info("=================================");
        log.info("BoardServiceTests >> testView START");
        int idx = 13;
        BoardDTO boardDTO = boardService.view(idx);

        log.info("boardDTO : {}", boardDTO);
        log.info("BoardServiceTests >> testView END");
        log.info("=================================");
    }

}
