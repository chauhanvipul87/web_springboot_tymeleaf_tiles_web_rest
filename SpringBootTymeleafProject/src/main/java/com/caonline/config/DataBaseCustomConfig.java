package com.caonline.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource(value = {"classpath:jdbc.properties"}, ignoreResourceNotFound = true)
public class DataBaseCustomConfig {

	/* COMMON DATABASE CONFIGURATION PROPERTIES START   */
	@Value("${c3p0.driverClassName}")
    private String driverClassName;
	
	@Value("${c3p0.minPoolSize}")
	private int minSize;
	 
	@Value("${c3p0.maxPoolSize}")
    private int maxSize;
 
    @Value("${c3p0.acquireIncrement}")
    private int acquireIncrement;
 
    @Value("${c3p0.maxStatements}")
    private int maxStatements;
    
    @Value("${c3p0.testConnectionOnCheckin}")
    boolean testConnectionOnCheckin;
    
    @Value("${c3p0.testConnectionOnCheckout}")
    boolean testConnectionOnCheckout;
 
    @Value("${c3p0.maxIdleTime}")
    private int maxIdleTime;
    
    @Value("${c3p0.idleConnectionTestPeriod}")
    private int idleTestPeriod;
    
    /* COMMON DATABASE CONFIGURATION PROPERTIES END   */	 
    
 
    /* INIDIVIDUAL DATABASE CONNECTION PROPERTIES START   */
    
    @Value("${c3p0.datasource.primary.url}")
    private String boesJdbcUrl;
 
    @Value("${c3p0.datasource.primary.username}")
    private String boesJdbcUsername;
 
    @Value("${c3p0.datasource.primary.password}")
    private String boesJdbcPassword;
    
    
    /* INIDIVIDUAL DATABASE CONNECTION PROPERTIES END   */
    @Primary
    @Bean(name = "boesDataSource", destroyMethod = "close")
	public DataSource getBoesDataSource() throws PropertyVetoException {
		 ComboPooledDataSource dataSource = new ComboPooledDataSource();
	     setDataSourceConfiguration(dataSource, this.boesJdbcUrl , this.boesJdbcUsername, this.boesJdbcPassword);
	     return dataSource;
	}
	private void setDataSourceConfiguration(ComboPooledDataSource dataSource, String url, String username, String password) throws PropertyVetoException{
		 	dataSource.setDriverClass(this.driverClassName);
	        dataSource.setJdbcUrl(url);
	        dataSource.setUser(username);
	        dataSource.setPassword(password);
	        
	        dataSource.setMinPoolSize(this.minSize);
	        dataSource.setMaxPoolSize(this.maxSize);
	        dataSource.setAcquireIncrement(this.acquireIncrement);
	        dataSource.setMaxStatements(this.maxStatements);
	        
	        
	        dataSource.setTestConnectionOnCheckin(this.testConnectionOnCheckin);
	        dataSource.setTestConnectionOnCheckout(this.testConnectionOnCheckout);
	        dataSource.setIdleConnectionTestPeriod(this.idleTestPeriod);
	        dataSource.setMaxIdleTime(this.maxIdleTime);
		
	}
	
}
