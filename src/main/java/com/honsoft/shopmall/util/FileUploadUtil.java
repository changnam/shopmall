package com.honsoft.shopmall.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import net.coobird.thumbnailator.Thumbnails;

@Component
public class FileUploadUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

	@Value("${file.uploadDir}")
	private String uploadPath;

	@PostConstruct
	public void init() {
		File tempFolder = new File(uploadPath);
		if (tempFolder.exists() == false) {
			tempFolder.mkdir();
		}

		uploadPath = tempFolder.getAbsolutePath();
		logger.info("------------------------------->");
		logger.info("uploadPath: {}", uploadPath);
	}

	public List<String> upload(MultipartFile[] files) {
		List<String> result = new ArrayList<>();

		for (MultipartFile file : files) {
			logger.info("------------------------------->");
			logger.info("name: {}", file.getOriginalFilename());

			if (file.getContentType().startsWith("image") == false) {
				logger.error("File type not supported: {}", file.getContentType());
				continue;
			}

			String uuid = UUID.randomUUID().toString();
			String savedFileName = uuid + "_" + file.getOriginalFilename();

			try (InputStream in = file.getInputStream();
					OutputStream out = new FileOutputStream(uploadPath + File.separator + savedFileName)) {
				FileCopyUtils.copy(in, out);

				Thumbnails.of(new File(uploadPath + File.separator + savedFileName)).size(100, 100)
						.toFile(uploadPath + File.separator + "s_" + savedFileName);
				result.add(savedFileName);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}
}
