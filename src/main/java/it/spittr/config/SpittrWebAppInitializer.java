package it.spittr.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {

		return new Class<?>[]{RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {

		return new Class<?>[]{WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {

		return new String[]{"/"};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {

		long maxFileSize = 4194304; // max loadable file size 4MB
		long maxRequestSize = 8388608; // max request size 8MB
		
		registration.setMultipartConfig(new MultipartConfigElement("/tmp/uploads", maxFileSize, maxRequestSize, 0));
	}

}
