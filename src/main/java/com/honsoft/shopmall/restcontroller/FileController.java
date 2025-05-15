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

import com.honsoft.shopmall.exception.UploadNotSupportedException;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@PostMapping("/upload")
	public ResponseEntity<List<String>> uploadFile(@RequestParam("files") MultipartFile[] files) {
		logger.info("upload file .....");

		if (files == null || files.length == 0) {
			throw new UploadNotSupportedException("No files to upload");
		}

		for (MultipartFile file : files) {
			logger.info("---------------------------------->");
			logger.info("name: {}", file.getOriginalFilename());
			checkFileType(file.getOriginalFilename());
		}
		return null;
	}

	private void checkFileType(String fileName) {
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String regExp = "^(jpg|jpeg|JPG|JPEG|png|PNG|gif|GIF|bmp|BMP)";

		if (!suffix.matches(regExp)) {
			throw new UploadNotSupportedException("file type not supported: " + suffix);
		}

	}

}
