package com.caonline.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.tiles2.dialect.TilesDialect;
import org.thymeleaf.extras.tiles2.spring4.web.configurer.ThymeleafTilesConfigurer;
import org.thymeleaf.extras.tiles2.spring4.web.view.ThymeleafTilesView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

@SpringBootApplication
@ComponentScan(basePackages = {"com.caonline"})
public class CAOnlineApplication extends WebMvcConfigurerAdapter{

	private static final String PATH = "/errors";
	private static final String VIEWS = "classpath:/templates/";

	/*@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {

	           //route all errors towards /error .
	    	   final ErrorPage errorPage=new ErrorPage(PATH);
	    	   container.addErrorPages(errorPage);
	        }
	    };
   }*/
	
/*	@Bean(name = "templateResolver")
    public SpringResourceTemplateResolver createTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        return templateResolver;
    }


	@Bean(name = "templateEngine")
	public SpringTemplateEngine createTemplateEngine(SpringResourceTemplateResolver springResolver) {
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.addTemplateResolver(springResolver);
	    templateEngine.addDialect(new TilesDialect());
	    return templateEngine;
	}
	
	@Bean
	public ViewResolver createTilesViewResolver(SpringTemplateEngine templateEngine) {
	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	    viewResolver.setTemplateEngine(templateEngine);
	    viewResolver.setViewClass(ThymeleafTilesView.class);
	    viewResolver.setCache(false);
	    viewResolver.setCharacterEncoding("UTF-8");
	    viewResolver.setOrder(Ordered.LOWEST_PRECEDENCE);
	    return viewResolver;
	}*/
	
	@Bean
    public TemplateResolver templateResolver() {
        TemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public UrlTemplateResolver urlTemplateResolver() {
        return new UrlTemplateResolver();
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(templateResolver());
        templateEngine.addDialect(new TilesDialect());
        return templateEngine;
    }

    /**
     *  Handles all views except for the ones that are handled by Tiles. This view resolver
     *  will be executed as first one by Spring.
     */
    @Bean
    public ViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver vr = new ThymeleafViewResolver();
        vr.setTemplateEngine(templateEngine());
        vr.setCharacterEncoding("UTF-8");
        vr.setOrder(Ordered.HIGHEST_PRECEDENCE);
        // all message/* views will not be handled by this resolver as they are Tiles views
        vr.setExcludedViewNames(new String[]{"message/*"});
        return vr;
    }

    /**
     * Handles Tiles views.
     */
    @Bean
    public ViewResolver tilesViewResolver() {
        ThymeleafViewResolver vr = new ThymeleafViewResolver();
        vr.setTemplateEngine(templateEngine());
        vr.setViewClass(ThymeleafTilesView.class);
        vr.setCharacterEncoding("UTF-8");
        vr.setOrder(Ordered.LOWEST_PRECEDENCE);
        return vr;
    }    
	@Bean
	public ThymeleafTilesConfigurer tilesConfigurer() {
	    ThymeleafTilesConfigurer ttc = new ThymeleafTilesConfigurer();
	    ttc.setDefinitions(new String[]{"/WEB-INF/views/tiles/tiles-defs.xml"});
	    return ttc;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CAOnlineApplication.class, args);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/").setViewName("forward:/login.html");
	}

	
	
}
