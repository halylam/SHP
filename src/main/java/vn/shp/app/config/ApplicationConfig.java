package vn.shp.app.config;

import ecm.service.EcmPropertyMapper;
import ecm.service.EcmService;
import ecm.service.impl.AlfrescoCmisServiceImpl;
import ecm.service.impl.AlfrescoPropertyServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@PropertySource("classpath:application.properties")
@Import(SecurityConfig.class)
public class ApplicationConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
	  ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
	  messageBundle.setBasenames("classpath:application", "classpath:messages/messages");
	  messageBundle.setDefaultEncoding("UTF-8");
	  return messageBundle;
	}

	@Bean(name = "sessionFactory")
	public HibernateJpaSessionFactoryBean sessionFactory() {
		return new HibernateJpaSessionFactoryBean();
	}

	@Bean(name="ecmService")
	@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public EcmService ecmServie(@Value("${alfresco.username}") String username,
								@Value("${alfresco.password}") String password,
								@Value("${alfresco.atomUrl}") String atomUrl,
								@Value("${alfresco.repositoryId}") String repositoryId) {
		return new AlfrescoCmisServiceImpl(username, password, atomUrl, repositoryId);
	}

	@Bean(name="propertyMapper")
	@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public EcmPropertyMapper ecmPropertyMapper() {
		return new AlfrescoPropertyServiceImpl();
	}


}
