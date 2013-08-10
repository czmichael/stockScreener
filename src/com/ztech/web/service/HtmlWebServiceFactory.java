package com.ztech.web.service;

import static com.ztech.stock.constant.StockConstant.QNAME;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.ztech.exception.WebServiceException;
import com.ztech.web.service.html.HtmlWebServiceImpl;
import com.ztech.web.service.html.HtmlWebServicePublisher;


public class HtmlWebServiceFactory implements WebServiceFactory {

	public Service obtainService() {
		URL url;
		try {
			url = new URL(HtmlWebServicePublisher.HTML_WEB_SERVER_PUBLISH_URL + "?wsdl");
			QName qname = new QName(QNAME, HtmlWebServiceImpl.SERVICE_NAME);
			Service service = Service.create(url, qname);
			return service;
		} catch (MalformedURLException e) {
			throw new WebServiceException(e);
		}
	}
}
