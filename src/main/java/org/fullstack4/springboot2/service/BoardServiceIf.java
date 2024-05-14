package org.fullstack4.springboot2.service;

import org.fullstack4.springboot2.dto.BoardDTO;
import org.fullstack4.springboot2.dto.PageRequestDTO;
import org.fullstack4.springboot2.dto.PageResponseDTO;

public interface BoardServiceIf {
    int regist(BoardDTO boardDTO);
    BoardDTO view(int idx);
    void modify(BoardDTO boardDTO);
    void delete(int idx);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
}
