package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.User;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by IBA on 05/04/2018.
 */
public interface StorageService {

	public void store(MultipartFile file);
	
	public Resource loadFile(String filename);
	
	public void deleteAll();
	
	public void init();
}
