package org.fullstack4.springboot2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.springboot2.domain.BoardReplyEntity;
import org.fullstack4.springboot2.dto.BoardReplyDTO;
import org.fullstack4.springboot2.dto.PageRequestDTO;
import org.fullstack4.springboot2.dto.PageResponseDTO;
import org.fullstack4.springboot2.repository.BoardReplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardReplyServiceImpl implements BoardReplyServiceIf {
    private final BoardReplyRepository boardReplyRepository;
    private final ModelMapper modelMapper;

    @Override
    public int regist(BoardReplyDTO replyDTO) {
        log.info("=================================");
        log.info("BoardReplyServiceImpl >> regist START");
        log.info("BoardReplyServiceImpl >> replyDTO : " + replyDTO);
        BoardReplyEntity reply = modelMapper.map(replyDTO, BoardReplyEntity.class);

        log.info("BoardReplyServiceImpl >> reply : " + reply);




        int result = boardReplyRepository.save(reply).getIdx();
        log.info("BoardReplyServiceImpl >> regist END");
        log.info("=================================");
         return result;
    }

    @Override
    public PageResponseDTO<BoardReplyDTO> getListOfReply(int bbs_idx, PageRequestDTO pageRequestDTO) {
        PageRequest pageable = PageRequest.of(
                (pageRequestDTO.getPage() < 0? 0 : pageRequestDTO.getPage()-1), // page 시작이 0이라 이런 로직을 추가한 것
                pageRequestDTO.getPage_size(),
                Sort.by("idx").ascending()
        );

        Page<BoardReplyEntity> replyResult = boardReplyRepository.listOfBoardReply(bbs_idx, pageable);
        List<BoardReplyDTO> dtoList = replyResult.getContent().stream()
                .map(reply -> modelMapper.map(reply, BoardReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<BoardReplyDTO>withAll() // 시그니처가 여러개일 때, 알아서 매칭을 시켜달라는 것
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total_count((int)replyResult.getTotalElements())
                .build();
    }
}
