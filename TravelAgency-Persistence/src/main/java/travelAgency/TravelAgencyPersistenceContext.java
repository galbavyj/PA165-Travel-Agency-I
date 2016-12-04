package travelAgency;

import javax.sql.DataSource;
import cz.muni.fi.pa165.travelAgency.persistence.dao.CustomerDao;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@ComponentScan(basePackageClasses={CustomerDao.class})
public class TravelAgencyPersistenceContext {

    @Bean
    public PersistenceExceptionTranslationPostProcessor postProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
	@Bean 
	public JpaTransactionManager transactionManager(){
		return  new JpaTransactionManager(entityManagerFactory().getObject());
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean  entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean jpaFactoryBean = new LocalContainerEntityManagerFactoryBean ();
		jpaFactoryBean.setDataSource(db());
		jpaFactoryBean.setLoadTimeWeaver(instrumentationLoadTimeWeaver());
		jpaFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		return jpaFactoryBean;
	}
	
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean(){
		return new LocalValidatorFactoryBean();
	}
	@Bean
	public LoadTimeWeaver instrumentationLoadTimeWeaver() {
		return new InstrumentationLoadTimeWeaver();
	}
	
	@Bean
	public DataSource db(){
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.DERBY).build();
	}
}