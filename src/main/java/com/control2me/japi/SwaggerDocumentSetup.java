package com.control2me.japi;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import io.swagger.jaxrs.config.BeanConfig;

public class SwaggerDocumentSetup extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle( "Control 2 Me, General API's" );
        beanConfig.setDescription( "Our core API's that can be used for all sorts of stuff. Please Enjoy." );

        beanConfig.setSchemes(new String[]{"https"});
//        beanConfig.setHost("localhost:8080");
        beanConfig.setHost("www.control2me.com");
        beanConfig.setBasePath("/japi/services");
        beanConfig.setResourcePackage( "com.control2me.japi.services" );
        
        beanConfig.setScan(true);
	}
}
