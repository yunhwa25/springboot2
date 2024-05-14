package org.fullstack4.springboot2.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.springboot2.domain.BoardEntity;
import org.fullstack4.springboot2.domain.QBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(BoardEntity.class);
    }


    @Override
    public Page<BoardEntity> search(Pageable pageable) {
        log.info("==============================");
        log.info("BoardSearchImpl >> search START");

        QBoardEntity qBoard = QBoardEntity.boardEntity; // QBoardEntity 객체 생성
        // SELECT ~~ FROM QBoardEntity <- tbl_board // tbl_board에서 SELECT 해오라는 뜻과 같음
        JPQLQuery<BoardEntity> query = from(qBoard);

//        query.where(qBoard.title.contains("제목"));
//        query.where(qBoard.content.like("내용"));

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(qBoard.title.contains("제목"));
        booleanBuilder.or(qBoard.content.contains("제목"));
        query.where(booleanBuilder);

        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        log.info("query : {}", query);

        List<BoardEntity> boards = query.fetch();
        int total = (int)query.fetchCount();

        log.info("boards : {}", boards);
        log.info("total : {}", total);


        log.info("BoardSearchImpl >> search END");
        log.info("==============================");

        return new PageImpl<>(boards, pageable, (long)total);
    }

    @Override
    public Page<BoardEntity> search2(Pageable pageable, String[] types, String search_keyword) {
        log.info("==============================");
        log.info("BoardSearchImpl >> search2 START");
        QBoardEntity qBoard = QBoardEntity.boardEntity;
        JPQLQuery<BoardEntity> query = from(qBoard);

        if ((types != null && types.length > 0) && (search_keyword != null && search_keyword.length() > 0)) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            // type : t(제목), c(내용), u(사용자아이디)
            for (String type : types) {
                switch (type) {
                    case "t" : booleanBuilder.or(qBoard.title.like("%" + search_keyword + "%"));
                    break;
                    case "c" : booleanBuilder.or(qBoard.content.like("%" + search_keyword + "%"));
                        break;
                    case "u" : booleanBuilder.or(qBoard.user_id.like("%" + search_keyword + "%"));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(qBoard.idx.gt(0));

        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        log.info("query : {}", query);

        List<BoardEntity> boards = query.fetch();
        int total = (int)query.fetchCount();

        log.info("boards : {}", boards);
        log.info("total : {}", total);


        log.info("BoardSearchImpl >> search2 END");
        log.info("==============================");

        return new PageImpl<>(boards, pageable, total);
    }
}
