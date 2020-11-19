package com.gaconnecte.auxilium.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gaconnecte.auxilium.service.StorageService;

/**
 * Service to manage files in the application
 * @author GA\issam.benali
 *
 */
@Service
@Transactional
public class StorageServiceImpl implements StorageService {

	private final Path rootLocation = Paths.get("upload-dir");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gaconnecte.auxilium.service.StorageService#store(org.springframework.web.
	 * multipart.MultipartFile)
	 */
	@Override
	public void store(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gaconnecte.auxilium.service.StorageService#loadFile(java.lang.String)
	 */
	@Override
	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gaconnecte.auxilium.service.StorageService#deleteAll()
	 */
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gaconnecte.auxilium.service.StorageService#init()
	 */
	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}

	}

}