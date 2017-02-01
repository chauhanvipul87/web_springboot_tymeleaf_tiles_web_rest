package com.caonline.utility;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class CommonValidations{

	public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }
	
	public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
	
	public static boolean isNull( final Collection< ? > c ) {
	    return c == null;
	}

	public static boolean isNull( final Map< ?, ? > m ) {
	    return m == null;
	}
	
	public static boolean isNullOrEmpty( final Collection< ? > c ) {
	    return c == null || c.isEmpty();
	}

	public static boolean isNullOrEmpty( final Map< ?, ? > m ) {
	    return m == null || m.isEmpty();
	}
	
	public static boolean isNotNull( final Collection< ? > c ) {
	    return !(c == null);
	}

	public static boolean isNotNull( final Map< ?, ? > m ) {
	    return !(m == null);
	}
	
	public static boolean isNotNullOrEmpty( final Collection< ? > c ) {
	    return !(c == null || c.isEmpty());
	}
	
	public static boolean isNotNullOrEmpty( final Map< ?, ? > m ) {
	    return !(m == null || m.isEmpty());
	}
	
	public static boolean isCharacterString(String str){
		if (StringUtils.isBlank(str)){
	  		  return false;
	  	} 
		boolean isChar = str.matches("[a-zA-z]+");
		return isChar;
	 }
 
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	 
	
}
