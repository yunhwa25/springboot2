package org.fullstack4.springboot2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.springboot2.dto.BoardDTO;
import org.fullstack4.springboot2.dto.PageRequestDTO;
import org.fullstack4.springboot2.dto.PageResponseDTO;
import org.fullstack4.springboot2.service.BoardServiceIf;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardController {
    private final BoardServiceIf boardService;
    // 의존성 : 생성자에서 직접 주입(주로 사용해라), 메서드에서 주입(런타임 시에 에러를 찾을 수 있으니 되도록 사용하지 말 것)

    @GetMapping("/list")
    public void list(
        PageRequestDTO pageRequestDTO,
        Model model
    ) {
        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.list(pageRequestDTO);

        log.info("pageResponseDTO : {}", pageResponseDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);
    }

    @GetMapping("/view")
    public void view(
            int idx,
            PageRequestDTO pageRequestDTO,
            Model model
    ) {
        log.info("==========================================");
        log.info("BoardController view START");

        BoardDTO boardDTO = boardService.view(idx);
        model.addAttribute("dto", boardDTO);

        log.info("BoardController view END");
        log.info("==========================================");
    }

    @RequestMapping(value="/regist", method = {RequestMethod.GET})
    public void registGET(Model model) {

    }

    @RequestMapping(value="/regist", method = {RequestMethod.POST})
    public String registPOST(
            @Valid BoardDTO boardDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        log.info("=============================================");
        log.info("BoardController >> registPOST START");

        if (bindingResult.hasErrors()) {
            log.info("BoardController >> regist POST ERROR");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/regist";
        }

        int result_idx = boardService.regist(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);

        log.info("boardDTO : {}", boardDTO);
        log.info("result_idx : {}", result_idx);
        log.info("BoardController >> registPOST END");
        log.info("=============================================");
        return "redirect:/board/list";
    }

}
