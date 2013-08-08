package com.ztech.web.service.html;

import javax.xml.ws.Endpoint;

public class HtmlWebServicePublisher {

	public static final String HTML_WEB_SERVER_PUBLISH_URL = "http://127.0.0.1:9976/html_util";
	
	public static void main(String[ ] args) {
		Endpoint.publish(HTML_WEB_SERVER_PUBLISH_URL, new HtmlWebServiceImpl());
	}
}
