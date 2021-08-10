package ar.edu.davinci.dvdsPerezMartin;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import ar.edu.davinci.dvdsPerezMartin.controlador.view.ClienteThymeleafControlador;
import ar.edu.davinci.dvdsPerezMartin.convertion.DateFormatter;
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final Logger LOGGER = LoggerFactory.getLogger(ClienteThymeleafControlador.class);

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5")
    public ClassLoaderTemplateResolver templateResolver() {

        LOGGER.info("Template Resolver");
        
    	ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        LOGGER.info("Template Resolver result: " + templateResolver.toString());
        return templateResolver;
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public SpringTemplateEngine templateEngine() {

    	LOGGER.info("Spring Template Engine");
        
    	SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setTemplateEngineMessageSource(messageSource());
        templateEngine.setEnableSpringELCompiler(true);
  
        LOGGER.info("Spring Template Engine result: " + templateEngine.toString());
        return templateEngine;
    }

    @Bean
    @Description("Thymeleaf view resolver")
    public ViewResolver viewResolver() {

    	LOGGER.info("View Resolver");

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

        viewResolver.setTemplateEngine(templateEngine());
        
    	LOGGER.info("View Resolver result: " + viewResolver.toString());

        return viewResolver;
    }
    
    /* ******************************************************************* */
    /*  GENERAL CONFIGURATION ARTIFACTS                                    */
    /*  Static Resources, i18n Messages, Formatters (Conversion Service)   */
    /* ******************************************************************* */

	/*
     *  Dispatcher configuration for serving static resources
     */
    @Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/static/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/static/js/**").addResourceLocations("/js/");
    }	

//    @Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver slr = new SessionLocaleResolver();
//        slr.setDefaultLocale(Locale.US);
//        return slr;
//    }   
    /*
     *  Message externalization/internationalization
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("Messages");
        return messageSource;
    }
    
    /*
     * Add formatter for class {@link thymeleafexamples.stsm.business.entities.Variety}
     * and {@link java.util.Date} in addition to the one registered by default
     */
    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addFormatter(dateFormatter());
    }


    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }
}