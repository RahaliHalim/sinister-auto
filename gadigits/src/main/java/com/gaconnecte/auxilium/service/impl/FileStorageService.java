/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.config.ApplicationProperties;
import java.io.File;
import java.nio.file.Path;
import java.io.FileOutputStream;
import org.springframework.core.io.Resource;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;

/**
 *
 * @author hannibaal
 */
@Service
public class FileStorageService {

	private final ApplicationProperties properties;
	private final Logger log = LoggerFactory.getLogger(FileStorageService.class);
    private Path fileStorageLocation;

	@Autowired
	public FileStorageService(ApplicationProperties properties) {
		this.properties = properties;
	}

	// file recording and name generation

	public String storeFile(MultipartFile file, String folder) throws Exception {

		try {
			File destFolder = new File(properties.getCheminFichier() + File.separator + folder);
			log.info("Folder path : {}", destFolder.getAbsolutePath());
			if (!destFolder.exists()) { // Create folder
				FileUtils.forceMkdir(destFolder);
			}
			// Generate a new name for file
			String newName = folder + "_" + System.currentTimeMillis() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());
			String fullFilePath = properties.getCheminFichier() + File.separator + folder + File.separator + newName;
			log.info("File full path : {}", fullFilePath);

			// save the file
			File destFile = new File(fullFilePath);
			destFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(destFile);
			fos.write(file.getBytes());
			fos.close();

			return newName;
		} catch (IOException ex) {
			throw new Exception("Could not store file. Please try again!", ex);
		}
	}

	// Update file recording
	public String updateFile(MultipartFile file, String folder, String name) throws Exception {
		try {
			File destFolder = new File(properties.getRootStoragePath() + File.separator + folder);
			if (!destFolder.exists()) { // Create folder
				FileUtils.forceMkdir(destFolder);
			}
			// Generate a new file
			String fullFilePath = properties.getRootStoragePath() + File.separator + folder + File.separator + name;
			log.info("File full path : {}", fullFilePath);
			// Save the file
			File destFile = new File(fullFilePath);
			destFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(destFile);
			fos.write(file.getBytes());
			fos.close();
			return name;
		} catch (IOException ex) {
			throw new Exception("Could not store file. Please try again!", ex);
		}
	}
	
public String storePECFiles(MultipartFile file, String folder) throws Exception {
		
		try {
			File destFolder = new File(properties.getCheminFichier() + File.separator + folder);
			log.info("Folder path : {}", destFolder.getAbsolutePath());
			if (!destFolder.exists()) { // Create folder
				FileUtils.forceMkdir(destFolder);
			}
			// Generate name for file
			String newName = folder + "_" + System.nanoTime() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());

			String fullFilePath = properties.getCheminFichier() + File.separator + folder + File.separator + newName;
			log.info("File full path : {}", fullFilePath);

			// save the file
			File destFile = new File(fullFilePath);
			destFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(destFile);
			fos.write(file.getBytes());
			fos.close();

			return newName;
		} catch (IOException ex) {
			throw new Exception("Could not store file. Please try again!", ex);
		}
	}






	public String storePECFilesNw(MultipartFile file, String folder, String nomImage) throws Exception {
		
		try {
			
			File destFolder = new File(properties.getCheminFichier() + File.separator + folder);
			log.info("Folder path : {}", destFolder.getAbsolutePath());
			if (!destFolder.exists()) { // Create folder
				FileUtils.forceMkdir(destFolder);
			}
			
			// Generate name for file
			String newName = null;
			if(nomImage.equals("noExtension")) {
				newName = folder + "_" + System.nanoTime() + "."
						+ FilenameUtils.getExtension(file.getOriginalFilename());
			} else {
				
				Integer a = nomImage.lastIndexOf(".");
				String b = nomImage.substring(a + 1);
			

					newName = folder + "_" + System.nanoTime() + "."
							+ b;

			}
			String fullFilePath = properties.getCheminFichier() + File.separator + folder + File.separator + newName;
			log.info("File full path : {}", fullFilePath);

			// save the file
			File destFile = new File(fullFilePath);
			destFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(destFile);
			fos.write(file.getBytes());
			fos.close();

			return newName;
		} catch (IOException ex) {
			throw new Exception("Could not store file. Please try again!", ex);
		}
	}
	
	public void updatePECFiles(MultipartFile file, String folder, String oldName) throws Exception {
		
		try {
			File destFolder = new File(properties.getCheminFichier() + File.separator + folder);
			log.info("Folder path : {}", destFolder.getAbsolutePath());
			if (!destFolder.exists()) { // Create folder
				FileUtils.forceMkdir(destFolder);
			}
			// Generate name for file
			String newName = oldName;

			String fullFilePath = properties.getCheminFichier() + File.separator + folder + File.separator + newName;
			log.info("File full path : {}", fullFilePath);

			// save the file
			File destFile = new File(fullFilePath);
			destFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(destFile);
			fos.write(file.getBytes());
			fos.close();


		} catch (IOException ex) {
			throw new Exception("Could not store file. Please try again!", ex);
		}
		
	}
	
	// file recording and name generation

		public String storeFileForCompanyAndConcessionnaire(MultipartFile file, String folder, String nomImage) throws Exception {

			try {
				File destFolder = new File(properties.getCheminFichier() + File.separator + folder);
				log.info("Folder path : {}", destFolder.getAbsolutePath());
				if (!destFolder.exists()) { // Create folder
					FileUtils.forceMkdir(destFolder);
				}
				// Generate a new name for file
				String newName = null;
				if(nomImage.equals("noExtension")) {
					newName = folder + "_" + System.currentTimeMillis() + "."
							+ FilenameUtils.getExtension(file.getOriginalFilename());
				} else {
					
					Integer a = nomImage.lastIndexOf(".");
					String b = nomImage.substring(a + 1);
					newName = folder + "_" + System.currentTimeMillis() + "."
							+ b;
				}
				
				String fullFilePath = properties.getCheminFichier() + File.separator + folder + File.separator + newName;
				log.info("File full path : {}", fullFilePath);

				// save the file
				File destFile = new File(fullFilePath);
				destFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(file.getBytes());
				fos.close();

				return newName;
			} catch (IOException ex) {
				throw new Exception("Could not store file. Please try again!", ex);
			}
		}

	public Resource loadFileAsResource(String fileName) {
		String folder = null;
        try {
            Path filePath = fileStorageLocation.resolve(properties.getCheminFichier() + File.separator + folder + File.separator + fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                System.out.println("resource inexistante");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
		return null;
    }

    public File loadFile(String folderName, String fileName) {
        try {
            return new File(properties.getCheminFichier() + File.separator + folderName + File.separator + fileName);
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public File loadFile(String filePath) {
        try {
            return new File(filePath);
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    public File loadFileBonSortie(String fileName) {
        try {
            return new File(fileName);
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    public String storeFileReferentiel(MultipartFile file, String folder, String Label) throws Exception {

		try {
			File destFolder = new File(properties.getCheminFichier() + File.separator + folder);
			log.info("Folder path : {}", destFolder.getAbsolutePath());
			if (!destFolder.exists()) { // Create folder
				FileUtils.forceMkdir(destFolder);
			}
			// Generate a new name for file
			String newName = Label + "_" + System.currentTimeMillis() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());
			String fullFilePath = properties.getCheminFichier() + File.separator + folder + File.separator + newName;
			log.info("File full path : {}", fullFilePath);

			// save the file
			File destFile = new File(fullFilePath);
			destFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(destFile);
			fos.write(file.getBytes());
			fos.close();

			return newName;
		} catch (IOException ex) {
			throw new Exception("Could not store file. Please try again!", ex);
		}
	}

}
