package com.JavaService.m1_java_springboot.service;

import java.util.ArrayList;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.Signature;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	public String EncryptPasswordManual(String password) {
		String result = "";
		Integer passwordLength = password.length();
		try {
			ArrayList<String> chunkPassword = new ArrayList<String>();
			ArrayList<Integer> asciiCodePassword = new ArrayList<Integer>();
			ArrayList<String> randomChar = new ArrayList<String>();
			ArrayList<ArrayList<String>> randomPassword = new ArrayList<ArrayList<String>>() {
				{	
					add(new ArrayList<String>());
					add(new ArrayList<String>());
					add(new ArrayList<String>());
				}
			};
			ArrayList<String> chunkEncryptPass = new ArrayList<String>();
			
			if((passwordLength%2) == 0) {
				result = password;
			}else {
//				for(Integer i = 0; i < passwordLength; i++ ) {
//					chunkPassword.add(Character.toString(password.charAt(i)));
//					asciiCodePassword.add((int)password.charAt(i));
//					randomChar.add(Character.toString((char) Math.ceil((Math.random() * 10))));
//				}
				
				for(int i = 0; i < passwordLength; i++) {
//					//1
					if(((passwordLength+1)/2) == i) {
						
						randomPassword.get(0).set((passwordLength-1), Character.toString(password.charAt(i-1)));
					}
					randomPassword.get(0).set(0, Character.toString(password.charAt(i-1)));
					
					//2
					randomPassword.get(1).add(Character.toString(password.charAt((passwordLength-1)-i)));
				}
				result = String.join("",randomPassword.get(0));
			}
		}catch(Exception ex) {
			result = ex.getMessage();
		}
		return result;
	}
	
	public String EncryptPassword(String password) {
		String result = "";
		try {
	        SecretKeySpec skeyspec=new SecretKeySpec("key".getBytes(),"Blowfish");
	        Cipher cipher=Cipher.getInstance("Blowfish");
	        cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
	        byte[] encrypted=cipher.doFinal(password.getBytes());
	        String strData=new String(encrypted);
			
			result = Base64.encodeBase64String(strData.getBytes());
			
		}catch(Exception ex) {
			result = "ERROR-"+ex.getMessage();
		}
		
		return result;
	}
}
