package it.spittr.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("it.spittr.web")
public class WebConfig extends WebMvcConfigurerAdapter{
	
//	@Bean
//	jsp view resolver
//	public ViewResolver viewResolver(){
//		
//		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//		resolver.setPrefix("/jsp/");
//		resolver.setSuffix(".jsp");
//		resolver.setExposeContextBeansAsAttributes(true);
//		
//		return resolver;
//	}
	
	// tiles configuration
	@Bean
	public TilesConfigurer tilesConfigurer() {
		
		TilesConfigurer tiles = new TilesConfigurer();
		tiles.setDefinitions(new String[]{"/layout/tiles.xml"});
		tiles.setCheckRefresh(true);
		
		return tiles;
	}
	
	// tiles view resolver
	@Bean
	public TilesViewResolver tilesViewResolver() {
		
		return new TilesViewResolver();
	}
	
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
		
		configurer.enable();
	}
	
	@Bean
	public MessageSource messageSource(){
		
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		
		return messageSource;
	}
	
}
