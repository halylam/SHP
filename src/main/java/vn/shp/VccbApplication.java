package vn.shp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;

@SpringBootApplication
public class VccbApplication extends SpringBootServletInitializer {
	
	private static final String PATH = "/errors";

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(VccbApplication.class);
	}

	public static void main(String[] args) throws ParseException {
		ConfigurableApplicationContext context = SpringApplication.run(VccbApplication.class, args);
	}
	
	@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
      return (new EmbeddedServletContainerCustomizer() {
		@Override
		public void customize(ConfigurableEmbeddedServletContainer container) {
		   //route all errors towards /error .
		   final ErrorPage errorPage=new ErrorPage(PATH);
		   container.addErrorPages(errorPage);
		  }
	});
    }



}
