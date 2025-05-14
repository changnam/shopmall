package com.honsoft.shopmall.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.honsoft.shopmall.entity.Board;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.honsoft.shopmall.repository", entityManagerFactoryRef = "mysqlEntityManagerFactory", transactionManagerRef = "mysqlTransactionManager")
public class DataSoureConfig {
	
	@Bean
	@ConfigurationProperties("mysql.datasource")
	public DataSourceProperties mysqlProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	public DataSource mysqlDataSource(@Qualifier("mysqlProperties") DataSourceProperties mysqlProperties) {
		return mysqlProperties.initializeDataSourceBuilder().build();
	}
	
	@Bean
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
      @Qualifier("mysqlDataSource") DataSource dataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true); // vendor 에 관계없이 ddl 생성 기능을 쓸것인지 설정
        vendorAdapter.setShowSql(false); 
        
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.honsoft.shopmall.entity");

        factory.setDataSource(dataSource);
        
     // Set additional JPA properties, Hibernate 관련 속성 설정 
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update"); //테이블 구조 DDL 필요시 create-only 로 할것
        jpaProperties.setProperty("hibernate.format_sql", "true");
        jpaProperties.setProperty("hibernate.show_sql", "false"); // Optional: to log SQL to console
        factory.setJpaProperties(jpaProperties);


        factory.afterPropertiesSet();
        
        return factory;
    }

	    @Bean
	    public PlatformTransactionManager mysqlTransactionManager(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
	        JpaTransactionManager txManager = new JpaTransactionManager();
	        txManager.setEntityManagerFactory(entityManagerFactory);
	        return txManager;
	    }

}
