package com.ztech.web.service.html;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService
public interface HtmlWebService {

	/**
	 * Download a HTML page
	 * 
	 * @param address The URL of the page to be downloaded
	 * @return A string representation of the HTML page
	 */
	@WebMethod
	public String downloadHtml(String address);
	
}
