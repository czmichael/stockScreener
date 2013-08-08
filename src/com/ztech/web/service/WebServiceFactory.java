package com.ztech.web.service;

import javax.xml.ws.Service;

/**
 * Related classes and interfaces use Factory Method Pattern 
 */
public interface WebServiceFactory {
	
	public Service obtainService();

}