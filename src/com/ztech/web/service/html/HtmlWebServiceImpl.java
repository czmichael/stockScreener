package com.ztech.web.service.html;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;



@WebService(endpointInterface = "com.ztech.web.service.html.HtmlWebService")
public class HtmlWebServiceImpl implements HtmlWebService {
	
	public static final String SERVICE_NAME = "HtmlWebServiceImplService";
	
	@WebMethod
	public String downloadHtml(String address) {
		try {
			return Jsoup.connect(address).get().html();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
