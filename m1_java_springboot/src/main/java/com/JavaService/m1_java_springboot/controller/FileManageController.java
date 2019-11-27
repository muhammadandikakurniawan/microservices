package com.JavaService.m1_java_springboot.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.ByteStreams;

@RestController
@CrossOrigin
@RequestMapping("/m1_java_springboot/filemanage")
public class FileManageController {
	
	@Autowired
	private Environment env;
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	@RequestMapping("/base64upload")
	public HashMap<String,Object> Base64Save(@RequestBody HashMap<String,Object> params) throws IOException{
		HashMap<String,Object> res = new HashMap<String,Object>(){};
		
		String base64FileParam = (String) params.get("file");
		
		byte[] byteBase64File = Base64.decodeBase64(base64FileParam);
		InputStream inputStrm = new ByteArrayInputStream(byteBase64File);
		
		String exFile = URLConnection.guessContentTypeFromStream(inputStrm).split("/")[1];
		Date date =new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss.SSS");
		
		FileOutputStream fout = new FileOutputStream(this.uploadDir+date.getTime()+"."+exFile);
		fout.write(byteBase64File);
		
		res.put("ex_file",exFile);
		
		System.out.println("================================== END POINT PROCESS ==================================");
		System.out.println("Params = "+params);
		System.out.println("file_process = "+URLConnection.guessContentTypeFromStream(inputStrm));
		System.out.println("stream = "+inputStrm);
		System.out.println("date = "+date.getTime());
		System.out.println("date_format = "+dateFormat.toString());
		System.out.println("date_and_format = "+dateFormat.toString());
		System.out.println("new_file_name = "+this.uploadDir+date.getTime()+"."+exFile);
		
		return res;
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public HashMap<String,Object> SaveFile(@RequestParam("file") MultipartFile file){
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			//cara 1
			long startTime1 = System.nanoTime();

			byte[] fileByte = file.getBytes();
			
			long endTime1 = System.nanoTime();
			
			long processTime1 = endTime1 - startTime1;
			
			
			//cara2
			long startTime2 = System.nanoTime();
			
			InputStream fileInputStream2 = file.getInputStream();
			BufferedInputStream bufferInputStream2 = new BufferedInputStream(fileInputStream2);
			byte[] fileByte2 = ByteStreams.toByteArray(bufferInputStream2);
			
			long endTime2 = System.nanoTime();
			
			long processTime2 = endTime2 - startTime2;
			
			result.put("data1", Base64.encodeBase64String(fileByte));
			result.put("process_time1", processTime1);
			
			File f = new File(this.env.getProperty("file.upload-dir")+file.getOriginalFilename());
			
			if(f.exists()) {
				result.put("status", "file has been exists");
			}else if(!f.exists()) {
				FileOutputStream fout = new FileOutputStream(this.env.getProperty("file.upload-dir")+file.getOriginalFilename());
				fout.write(fileByte2);
			}
			
			
			result.put("data2", Base64.encodeBase64String(fileByte2));
			result.put("process_time2", processTime2);
			System.out.println("=================== encode 1 ===================");
			System.out.println(Base64.encodeBase64String(fileByte));
			System.out.println("=================== encode 2 ===================");
			System.out.println(Base64.encodeBase64String(fileByte2));
			System.out.println("file name = "+file.getOriginalFilename());

		}
		catch(Exception e) {
			result.put("error", e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value="/multifile",method=RequestMethod.POST)
	public HashMap<String,Object> SaveMultiFile(@RequestParam("file") MultipartFile[] file){
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		try {
			System.out.println("============================================");
			for(int i = 0; i < file.length; i++) {
				System.out.println(file[i].getOriginalFilename());
			}
			System.out.println("============================================");
			result.put("status", "ok");
		}
		catch(Exception e) {
			result.put("error", e.getMessage());
		}
		
		return result;
	}
	

}
