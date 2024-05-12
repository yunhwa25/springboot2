package org.fullstack4.springboot2.repository;

import lombok.extern.log4j.Log4j2;
import org.fullstack4.springboot2.domain.BaseEntity;
import org.fullstack4.springboot2.domain.BoardEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest  // 이 어노테이션을 써야지만 테스트 가능. 그래야 build gradle의 test 어쩌고 들이 동작함.
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testGetNow() {
        String now = boardRepository.getNow();
        log.info("============================");
        log.info("now : " + now);
        log.info("============================");
    }

    @Test
    public void testRegist() {
        log.info("============================");
        log.info("BoardRepositoryTest >> testRegist START");
        IntStream.rangeClosed(0, 10)
                .forEach(i -> {
                    BoardEntity bbs = BoardEntity.builder()
                            .user_id("test")
                            .title("테스트 제목 " + i)
                            .content("테스트 내용 " + i)
                            .display_date(new SimpleDateFormat("yyyy-MM-dd").format(
                                    new Date(2024-1900, 4, (i%10==0? 1 : i%10))).toString()
                            )
                            .build();

                    BoardEntity bbsResult = boardRepository.save(bbs); // save는 JPA에 있는 메서드 그대로 쓰는 것. 우리가 만들어 준 적 없음
                    log.info("result : " + bbsResult);
                });
        log.info("BoardRepositoryTest >> testRegist END");
        log.info("============================");
    }

    @Test
    public void testView() {
        int idx = 1;

        Optional<BoardEntity> result = boardRepository.findById(idx);
        BoardEntity bbs = result.orElse(null); // 값이 없으면 null로 해석해라.
        
        // 다양한 예외처리 방법이 있음
        // 트랜잭션을 적용하려면 예외처리가 반드시 필요. 그렇지 않으면 트랜잭션에서 빠짐. rollback 불가. 정상동작이 안되는 것.
//        result.get(); // 값이 없으면 NosuchElementException 발생
//        if (result.isPresent()) {} else {throw new IllegalArgumentException();}
//        result.orElseThrow(IllegalArgumentException::new);
//        result.orElseThrow(() -> new IllegalArgumentException("no find data"));
//        result.orElseGet(BoardEntity::new);
//        result.ifPresent(b -> {log.info("result: {}", b)});

        log.info("============================");
        log.info("BoardRepositoryTest >> testView START");
        log.info("bbs : " + bbs);
        log.info("BoardRepositoryTest >> testView END");
        log.info("============================");
    }

    @Test
    public void testModify() {
        int idx = 1;
        Optional<BoardEntity> result = boardRepository.findById(idx);
        BoardEntity bbs = result.orElse(
                BoardEntity.builder()
                        .idx(idx)
                        .user_id("test")
                        .title("제목 수정 1") // 내용 똑같으면 업데이트 일어나지 않음
                        .content("내용 수정 1")
                        .display_date(
                                new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString()
                        )
                        .build()
        );

        bbs = BoardEntity.builder()
                .idx(idx) // key가 되는 컬럼이 들어오면 update로, 그렇지 않으면 insert로 처리함.
                .user_id("test")
                .title("제목 수정 1")
                .content("내용 수정 1")
                .display_date(
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString()
                )
                .build();

//        bbs.modify("test", "제목 수정 1", "내용 수정 1", ""); // 이런식으로 직접 넣어주는 과정이 귀찮으니 위처럼 builder를 사용한 것
        boardRepository.save(bbs);

        log.info("============================");
        log.info("BoardRepositoryTest >> testModify START");
        log.info("bbs : " + bbs);
        log.info("BoardRepositoryTest >> testModify END");
        log.info("============================");

        // hibernate가 select만 하고 update를 하지 않았다는 건, 그 데이터가 없다는 것
    }

    @Test
    public void testDelete() {
        int idx = 1;
        boardRepository.deleteById(idx);
    }

    @Test
    public void testSearch() {
        log.info("============================");
        log.info("BoardRepositoryTest >> testList START");

        // 여기가 order by와 limit 부분
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        boardRepository.search(pageable);

        log.info("BoardRepositoryTest >> testList END");
        log.info("============================");
    }
}
