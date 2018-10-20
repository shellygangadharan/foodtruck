package com.foodtruck.demo;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;




@Component
public class UploadService {


	public File convertMultiPartToFile(MultipartFile file) throws IOException {
		String fileName ="";
		
		if ( file.getOriginalFilename() != null && file.getOriginalFilename().trim().length() >0 ) {
			fileName =  file.getOriginalFilename();
		}else {
			fileName = file.getName();
		}
		
		File convFile = new File(fileName);
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	
}
