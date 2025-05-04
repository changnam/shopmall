package com.honsoft.shopmall.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.AccessLevel;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Data
public class Board {
	
	 @Id
	    @GeneratedValue
	    private Long id;

	    @Column(length = 10, nullable = false)
	    private String writerid;
	 
	    @Column(length = 10, nullable = false)
	    private String writer;

	    @Column(length = 100, nullable = false)
	    private String title;

	    @Column(columnDefinition = "TEXT", nullable = false)
	    private String content;

	    @CreatedDate
	    @Column(updatable = false)
	    private LocalDateTime createdDate;

	    @LastModifiedDate
	    private LocalDateTime modifiedDate;

	    @Builder
	    public Board(Long id, String writerid, String writer, String title, String content) {
	        this.id = id;
	        this.writerid = writerid;
	        this.writer = writer;
	        this.title = title;
	        this.content = content;
	    }  
    
	
}
