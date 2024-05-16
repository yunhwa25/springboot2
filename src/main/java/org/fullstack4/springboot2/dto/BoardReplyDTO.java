package org.fullstack4.springboot2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardReplyDTO {
    private int idx;

    @NotNull
    private int board_idx;

    @NotEmpty
    private String user_id;
    @NotEmpty
    @Size(min=1, max=100)
    private String title;
    @NotEmpty
    private String content;
    private String display_date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reg_date;
    private LocalDateTime modify_date;
}


