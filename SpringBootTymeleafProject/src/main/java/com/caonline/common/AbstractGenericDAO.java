package com.caonline.common;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;


public abstract class AbstractGenericDAO {

	protected abstract JdbcTemplate getSpringJdbcTemplate(DataSource dataSource);
	protected abstract TransactionStatus beginTransAndGetStatus(PlatformTransactionManager transactionManager);
	protected abstract void rollBackTransaction(PlatformTransactionManager transactionManager,TransactionStatus status); 
	protected abstract void commitTransaction(PlatformTransactionManager transactionManager,TransactionStatus status);
	protected abstract PlatformTransactionManager getTransactionManager(DataSource dataSource);	
	
	protected abstract <T> List<T> findAll(DataSource dataSource, String query,Class<T> requiredType,Object... params) throws SQLException;
	protected abstract <T> List<T> findAll(DataSource dataSource, String query,Object[] params,Class<T> requiredType) throws SQLException;
	protected abstract <T> List<T> findAll(DataSource dataSource, String query,Class<T> requiredType ) throws SQLException;
	
	protected abstract List<Map<String, Object>> findList(DataSource dataSource, String query,Object... params) throws SQLException;
	protected abstract List<Map<String, Object>> findList(DataSource dataSource,Object[] params, String query) throws SQLException;
	protected abstract List<Map<String, Object>> findList(DataSource dataSource, String query)	throws SQLException;
	
	protected abstract Map<String, Object> findMap(DataSource dataSource, String query,Object... params) throws SQLException;
	protected abstract Map<String, Object> findMap(DataSource dataSource, String query)throws SQLException;
	protected abstract Map<String, Object> findMap(DataSource dataSource,Object[] params,String query) throws SQLException;
	
	
	protected abstract <T> T findBean(DataSource dataSource, String query,Class<T> requiredType,Object... params) throws SQLException;
	protected abstract <T> T findBean(DataSource dataSource, String query,Class<T> requiredType) throws SQLException;
	protected abstract <T> T findBean(DataSource dataSource, String query,Object[]params,Class<T> requiredType) throws SQLException;
	protected abstract <T> List<T> findBeanList(DataSource dataSource, String query,Class<T> requiredType) throws SQLException;	
	
	protected abstract <T> T findObject(DataSource dataSource, String query, Class<T> requiredType)throws SQLException;
	protected abstract <T> T findObject(DataSource dataSource, String query, Class<T> requiredType,Object... params) throws SQLException;
	protected abstract <T> T findObject(DataSource dataSource, String query, Object[]params,Class<T> requiredType) throws SQLException;
	
	
	protected abstract Long findTotalRecordCount(DataSource dataSource, String query,Object... params) throws SQLException;
	protected abstract Long findTotalRecordCount(DataSource dataSource, String query) throws SQLException;
	protected abstract Long findTotalRecordCount(DataSource dataSource, Object[] params,String query) throws SQLException;
	
	protected abstract boolean resetSQL(StringBuilder sql);
	
	protected abstract  int saveOrUpdate(DataSource dataSource, String query) throws SQLException;
	protected abstract  int saveOrUpdate(DataSource dataSource, String query,Object... params) throws SQLException;
	protected abstract  int delete(DataSource dataSource, String query) throws SQLException;
	protected abstract  int delete(DataSource dataSource, String query,Object... params) throws SQLException;
	
	
	//NamedParameterJdbc Template related methods.
	protected abstract NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(DataSource dataSource);
	protected abstract <T> int saveOrUpdateBeanUsingNamedJDBC(DataSource dataSource,	String query,T obj ) throws SQLException;
	protected abstract <T> int deleteUsingNamedJDBC(DataSource dataSource,	String query,T obj ) throws SQLException;
		
	protected abstract <T> T findObjectUsingNamedJDBC(DataSource dataSource ,String query,Class<T> requiredType, Object obj) throws SQLException;
	protected abstract Map<String, Object> findMapUsingNamedJDBC(DataSource dataSource,	String query, Object obj) throws SQLException;
	protected abstract List<Map<String, Object>> findAllUsingNamedJDBC(DataSource dataSource,String query, Object obj) throws SQLException;
	protected abstract <T> List<T> findAllUsingNamedJDBC(DataSource dataSource,String query, Class<T> requiredType,T obj) throws SQLException;
	protected abstract <T> List<T> findList(DataSource dataSource, Object[] params, String query,Class<T> requiredType) throws SQLException;
	
	protected abstract int insert(DataSource dataSource, String tableName,Map<String, Object> paramMap);
	protected abstract int insert(DataSource dataSource, String tableName,Map<String, Object> paramMap, String... columnNamesToBeInserted);
	protected abstract Number insertAndReturnGeneratedKey(DataSource dataSource, String tableName,Map<String, Object> paramMap, String autoGeneratedKeycolumnName,
			String... columnNamesToBeInserted);
	protected abstract Number insertAndReturnGeneratedKey(DataSource dataSource, String tableName,Map<String, Object> paramMap, String autoGeneratedKeycolumnName);
	protected abstract KeyHolder insertAndReturnGeneratedKeyHolder(DataSource dataSource,String tableName, Map<String, Object> paramMap,String... autoGeneratedKeycolumnName);
	protected abstract <T> long  saveAndGetAutoGeneratedKeyUsingNamedJDBC(DataSource dataSource, String query, Class<T> requiredType, T obj);
	
	
	
	
}
