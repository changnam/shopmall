package com.honsoft.shopmall.dto;

import java.time.LocalDateTime;

import com.honsoft.shopmall.entity.Board;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Builder
public class BoardFormDto {
	
	private Long id;
	private String writerid;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
	
	

    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .writerid(writerid)
                .writer(writer)
                .title(title)
                .content(content)
                .build();
        return build;
    }
   
    @Builder
    public BoardFormDto(Long id, String writerid, String writer, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writerid = writerid;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
