package org.fullstack4.springboot2.repository.search;

import org.fullstack4.springboot2.domain.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {
    Page<BoardEntity> search(Pageable pageable);

    Page<BoardEntity> search2(Pageable pageable, String[] types, String search_keyword);
}
