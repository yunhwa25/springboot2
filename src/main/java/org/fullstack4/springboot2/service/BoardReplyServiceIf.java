package org.fullstack4.springboot2.service;

import org.fullstack4.springboot2.dto.BoardReplyDTO;
import org.fullstack4.springboot2.dto.PageRequestDTO;
import org.fullstack4.springboot2.dto.PageResponseDTO;

public interface BoardReplyServiceIf {
    int regist(BoardReplyDTO replyDTO);

    PageResponseDTO<BoardReplyDTO> getListOfReply(int bbs_idx, PageRequestDTO pageRequestDTO);
}
