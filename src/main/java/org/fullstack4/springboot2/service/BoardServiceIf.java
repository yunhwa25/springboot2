package org.fullstack4.springboot2.service;

import org.fullstack4.springboot2.dto.BoardDTO;

public interface BoardServiceIf {
    int regist(BoardDTO boardDTO);
    BoardDTO view(int idx);
    void modify(BoardDTO boardDTO);
    void delete(BoardDTO boardDTO);
}
