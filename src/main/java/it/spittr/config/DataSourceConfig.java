package it.spittr.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * the application may use different databases 
 * for different environments(development, test, preprod, prod)
 * each environment has his own data source configuration 
 * that will activate based on the currently active profile
 * 
 * 
 * at the persistence level, the application uses the JPA technology 
 * with an container managed entity manager handled by the Spring framework
 * and Hibernate as the JpaVendorAdapter 
 * 
 * @author cristian
 *
 */
@Configuration
@EnableJpaRepositories(basePackages="it.spittr.data")
@EnableTransactionManagement
public class DataSourceConfig {

	@Bean
	// embedded h2(hypersonic) data source
	public DataSource embeddedDataSource() {

		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.H2)
								.addScript("classpath:schema.sql")
								.addScript("classpath:test-data.sql");
		
		EmbeddedDatabase embeddedDB = embeddedDatabaseBuilder.build();
		
		return embeddedDB;
	}
	
	@Bean
	// post processor bean needed by spring 
	// so that it can inject an entity manager directly into the persistence context
	public PersistenceAnnotationBeanPostProcessor paPostProcessor(){
		
		return new PersistenceAnnotationBeanPostProcessor();
	}
	
	@Bean
	// exception post translation used by spring 
	// so that it can translate persistence related exceptions
	public BeanPostProcessor persistenceTranslation(){
		
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	// jpa configuration with container managed entity manager
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){
		
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan("it.spittr.model");
		
		return entityManagerFactoryBean;
	}
	
	@Bean
	// jpa vendor adapter configuration
	public JpaVendorAdapter jpaVendorAdapter(){
		
		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		jpaAdapter.setDatabase(Database.HSQL);
		jpaAdapter.setShowSql(true);
		jpaAdapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
		
		return jpaAdapter;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory  entityManagerFactory){
	
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		
		return transactionManager;
	}
}
