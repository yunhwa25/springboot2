package org.fullstack4.springboot2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.springboot2.domain.BoardEntity;
import org.fullstack4.springboot2.dto.BoardDTO;
import org.fullstack4.springboot2.dto.PageRequestDTO;
import org.fullstack4.springboot2.dto.PageResponseDTO;
import org.fullstack4.springboot2.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void delete(int idx) {
        boardRepository.deleteById(idx);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getSearch_types();
        String search_word = pageRequestDTO.getSearch_word();
        PageRequest pageable = pageRequestDTO.getPageable("idx");
        Page<BoardEntity> result = boardRepository.search2(pageable, types, search_word);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total_count((int)result.getTotalElements())
                .build();
    }
}
