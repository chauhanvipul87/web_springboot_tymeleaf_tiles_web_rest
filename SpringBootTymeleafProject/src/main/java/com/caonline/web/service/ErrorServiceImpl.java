package com.caonline.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ErrorServiceImpl implements ErrorService{
	
   @Autowired
   private Environment env;
 
   @Override
   public String generateErrorMessage(final int error_code){
	 String message="";
	 System.out.println("generateErrorMessage():error_code:=>" + error_code);
	   switch(error_code){  
		   case 400: message=env.getProperty("400");
		   			 break;
		   case 401: message=env.getProperty("401");
		   			 break;
		   case 404: message=env.getProperty("404");
		   			 break;
		   case 500: message=env.getProperty("500");
		   			 break;
		   //etc 
		   //Put in all Http error codes here.
	   }
	 return message;
    }
}
