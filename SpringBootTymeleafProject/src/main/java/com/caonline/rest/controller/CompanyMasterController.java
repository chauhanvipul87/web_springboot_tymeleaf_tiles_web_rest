package com.caonline.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyMasterController extends BaseController {

	
    @RequestMapping(method = RequestMethod.GET, path="greeting", produces={"application/json"})
	public ABC greeting(){
    	ABC a = new ABC();
    	a.setName("Vipul");
		return a;
	}
	
    
    class ABC{
    	private String name ="";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
    	
    }
}
