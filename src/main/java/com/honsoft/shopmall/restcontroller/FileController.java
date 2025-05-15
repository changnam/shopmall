package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
	private static final Logger logger= LoggerFactory.getLogger(FileController.class);
	
	@PostMapping("/upload")
	public ResponseEntity<List<String>> uploadFile(@RequestParam("files") MultipartFile[] files){
		logger.info("upload file .....");
		
		return null;
	}

}
