package org.fullstack4.springboot2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.springboot2.domain.BoardEntity;
import org.fullstack4.springboot2.dto.BoardDTO;
import org.fullstack4.springboot2.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardServiceIf {
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    @Override
    public int regist(BoardDTO boardDTO) {
        BoardEntity board = modelMapper.map(boardDTO, BoardEntity.class);
        int idx = boardRepository.save(board).getIdx();
        return idx;
    }

    @Override
    public BoardDTO view(int idx) {
        Optional<BoardEntity> result = boardRepository.findById(idx);
        BoardEntity board = result.orElse(null);
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<BoardEntity> result = boardRepository.findById(boardDTO.getIdx());
        BoardEntity board = result.orElse(null);
        board.modify(boardDTO.getUser_id(), boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getDisplay_date());
        boardRepository.save(board);
    }

    @Override
    public void delete(BoardDTO boardDTO) {

    }
}
