package com.caonline.common;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;



public class GenericDAOImpl extends AbstractGenericDAO{
	
	/* Methods :: SpringJdbcTemplate Start ...........................................................................................................*/
	
	private static final Logger log = LoggerFactory.getLogger(GenericDAOImpl.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected <T> List<T>  findAll(DataSource dataSource, String query,Class<T> requiredType,Object... params) throws SQLException {
			return getSpringJdbcTemplate(dataSource).query(query,new BeanPropertyRowMapper(requiredType),params);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected <T> List<T>  findAll(DataSource dataSource,String query,Class<T> requiredType) throws SQLException {
			return getSpringJdbcTemplate(dataSource).query(query,new BeanPropertyRowMapper(requiredType));
	}
	
	@Override
	protected <T> List<T>  findAll(DataSource dataSource,String query,Object[] params,Class<T> requiredType) throws SQLException {
		return findAll(dataSource, query, requiredType, params);
	}
	
	@Override
	protected List<Map<String,Object>>  findList(DataSource dataSource,String query,Object... params) throws SQLException {
			return getSpringJdbcTemplate(dataSource).queryForList(query, params);
	}
	@Override
	protected List<Map<String, Object>> findList(DataSource dataSource,Object[] params, String query) throws SQLException {
		return findList(dataSource, query, params);
	}
	
	@Override
	protected <T> List<T> findList(DataSource dataSource,Object[] params, String query,Class<T> requiredType) throws SQLException {
		return getSpringJdbcTemplate(dataSource).queryForList(query, requiredType, params);
	}
	
	
	@Override
	protected List<Map<String,Object>> findList(DataSource dataSource,String query) throws SQLException {
			return getSpringJdbcTemplate(dataSource).queryForList(query);
	}
	
	@Override
	protected Map<String,Object>  findMap(DataSource dataSource,String query,Object... params) throws SQLException {
		    try {
		    	return getSpringJdbcTemplate(dataSource).queryForMap(query, params);
			} catch (EmptyResultDataAccessException e) {
				return new HashMap<String, Object>();
			}
	}
	
	@Override
	protected Map<String, Object> findMap(DataSource dataSource, Object[] params,String query) throws SQLException {
		return findMap(dataSource, query, params);
	}
	
	@Override
	protected Map<String,Object>  findMap(DataSource dataSource,String query) throws SQLException {
			return getSpringJdbcTemplate(dataSource).queryForMap(query);
	}
	
	@Override
	protected <T> T findObject(DataSource dataSource ,String query,Class<T> requiredType) throws SQLException {
			return getSpringJdbcTemplate(dataSource).queryForObject(query, requiredType);
	}
	
	@Override
	protected <T> T findObject(DataSource dataSource ,String query,Class<T> requiredType,Object... params) throws SQLException {
			return getSpringJdbcTemplate(dataSource).queryForObject(query, requiredType,params);
	}
	
	@Override
	protected <T> T findObject(DataSource dataSource,String query,Object[] params,Class<T> requiredType) throws SQLException {
		return findObject(dataSource, query, requiredType, params);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected <T> T  findBean(DataSource dataSource,String query,Class<T> requiredType,Object... params) throws SQLException {
			T object =null;
			if(params ==null || params.length ==0){
				log.error("illegal argument provide in  findBean : Please send valid params values, it should not be empty or null.");
			}else{
				List<T> objectList = (List<T>) getSpringJdbcTemplate(dataSource).query(query,new BeanPropertyRowMapper(requiredType),params);
				
				if(objectList.isEmpty()) {
					try{
						object = (T) requiredType.newInstance();
					} catch (Exception e) {
						log.error("Error occured in findBean method ",e);
					}
				} else {
					object = objectList.get(0);
				}
			}
			return object;
	}
	@Override
	protected <T> T  findBean(DataSource dataSource,String query,Object[] params,Class<T> requiredType) throws SQLException {
		return findBean(dataSource, query, requiredType, params);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected <T> T  findBean(DataSource dataSource,String query,Class<T> requiredType) throws SQLException {
		T object =null;
		List<T> objectList = (List<T>) getSpringJdbcTemplate(dataSource).query(query,new BeanPropertyRowMapper(requiredType));
		if(objectList.isEmpty()) {
			try {
				object = (T) requiredType.newInstance();
			} catch (Exception e) {
				log.error("Error occured in findBean method ",e);
			}
			
		} else {
			object = objectList.get(0);
		}
		return object;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <T> List<T>  findBeanList(DataSource dataSource,String query,Class<T> requiredType) throws SQLException {
			List<T> object = getSpringJdbcTemplate(dataSource).query(query,new BeanPropertyRowMapper(requiredType));
			return object;
	}
		
	@Override
	protected Long findTotalRecordCount(DataSource dataSource, String query,Object... params) throws SQLException{
		Long count = 0l;
		if(params ==null || params.length ==0){
			count =  getSpringJdbcTemplate(dataSource).queryForObject(query,Long.class);
		}else{
			count =  getSpringJdbcTemplate(dataSource).queryForObject(query,Long.class,params);
		}
		return count;
	}
	@Override
	protected Long findTotalRecordCount(DataSource dataSource, Object[] params,String query) throws SQLException{
		Long count = 0l;
		if(params ==null || params.length == 0){
			count =  getSpringJdbcTemplate(dataSource).queryForObject(query,Long.class);
		}else{
			count =  getSpringJdbcTemplate(dataSource).queryForObject(query,Long.class,params);
		}
		return count;
	}
	
	@Override
	protected Long findTotalRecordCount(DataSource dataSource, String query) throws SQLException{
		Long count = 0l;
		count =  getSpringJdbcTemplate(dataSource).queryForObject(query,Long.class);
		return count;
	}
	
	@Override
	protected void rollBackTransaction(PlatformTransactionManager transactionManager,TransactionStatus status) {
		transactionManager.rollback(status);
	}
	
	@Override
	protected void commitTransaction(PlatformTransactionManager transactionManager,TransactionStatus status) {
		transactionManager.commit(status);
	}
	
	@Override
	protected TransactionStatus beginTransAndGetStatus(PlatformTransactionManager transactionManager) {
	    TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		return status;
	}
	
	@Override
	protected JdbcTemplate getSpringJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource); 
	}
	
	@Override
	protected PlatformTransactionManager getTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource) ; 
	}
	
	@Override
	protected int saveOrUpdate(DataSource dataSource, String query)throws SQLException {
	    int affectedRows = getSpringJdbcTemplate(dataSource).update(query);
		return affectedRows;
	}
	
	@Override
	protected int saveOrUpdate(DataSource dataSource, String query,Object... params)throws SQLException {
		return  getSpringJdbcTemplate(dataSource).update(query);
	}
	
	@Override
	protected int delete(DataSource dataSource, String query) throws SQLException {
		 return saveOrUpdate(dataSource, query);
	}
	
	
	protected int delete(DataSource dataSource, String query,Object... params)throws SQLException {
		 return saveOrUpdate(dataSource, query,params);
	}
	
	/* Methods :: SpringJdbcTemplate Ends .....................................................................................................................*/
	
	/* Methods :: NamedParameterJdbcTemplate Starts ...........................................................................................................*/
	
	@Override
	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(DataSource dataSource) {
		return new  NamedParameterJdbcTemplate(dataSource);
	}
	
	/** 
	 	If you set column values in bean then send object instance which contains values. 
	 	In case where there no column value required to be send then simple pass ClassName.class 
	 	For Ex: 
	 	query = "INSERT INTO USERLOGIN (USER_ID)  VALUES ('80') ";
	 	saveOrUpdateBeanUsingNamedJDBC(dataSource, query,Login.class);
	 	
	 	If contains Value : then pass object of Login class
	 	Login login = new Login();
		login.setUserId("25");
		query = "INSERT INTO USERLOGIN (USER_ID)  VALUES (:userId) ";
		saveOrUpdateBeanUsingNamedJDBC(dataSource, query,login);
	 	 
	 
	 */
	@Override
	protected <T> int saveOrUpdateBeanUsingNamedJDBC(DataSource dataSource,String query,T obj)throws SQLException {
		int affectedRows = 0;
		SqlParameterSource sqlParams = new BeanPropertySqlParameterSource(obj);
		affectedRows = getNamedParameterJdbcTemplate(dataSource).update(query,sqlParams);
		return affectedRows;
	}
	
	@Override
	protected <T> T findObjectUsingNamedJDBC(DataSource dataSource, String query,Class<T> requiredType,Object obj)throws SQLException {
			SqlParameterSource sqlParams = new BeanPropertySqlParameterSource(obj);
			T t=  (T) getNamedParameterJdbcTemplate(dataSource).queryForObject(query,sqlParams,requiredType);
			return t;
	}
	
	@Override
	protected  Map<String, Object> findMapUsingNamedJDBC(DataSource dataSource, String query,Object obj )throws SQLException {
			SqlParameterSource sqlParams = new BeanPropertySqlParameterSource(obj);
			return getNamedParameterJdbcTemplate(dataSource).queryForMap(query,sqlParams);
	}
	@Override
	protected  List<Map<String,Object>> findAllUsingNamedJDBC(DataSource dataSource, String query,Object obj )throws SQLException {
			SqlParameterSource sqlParams = new BeanPropertySqlParameterSource(obj);
			return getNamedParameterJdbcTemplate(dataSource).queryForList(query,sqlParams);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected <T> List<T>  findAllUsingNamedJDBC(DataSource dataSource,String query,Class<T> requiredType,T obj) throws SQLException {
			SqlParameterSource sqlParams = new BeanPropertySqlParameterSource(obj);
			return getNamedParameterJdbcTemplate(dataSource).query(query, sqlParams, new BeanPropertyRowMapper(requiredType));
	}
	
	@Override
	protected <T> long saveAndGetAutoGeneratedKeyUsingNamedJDBC(DataSource dataSource,String query,Class<T> requiredType,T obj) {
			SqlParameterSource fileParameters = new BeanPropertySqlParameterSource(obj);
		    KeyHolder keyHolder = new GeneratedKeyHolder();
		    getNamedParameterJdbcTemplate(dataSource).update(query, fileParameters, keyHolder);
		    return keyHolder.getKey().longValue();
   }
	
	@Override
	protected <T> int deleteUsingNamedJDBC(DataSource dataSource,String query,T obj)throws SQLException {
		return saveOrUpdateBeanUsingNamedJDBC(dataSource, query, obj);
	}
	
	/* Methods :: NamedParameterJdbcTemplate Ends ...........................................................................................................*/
	

	
	@Override
	protected boolean resetSQL(StringBuilder sql) {
		sql.setLength(0);	
		return true;
	}
	
	protected <T> T newInstance(Class<T> c) throws Exception {
        return c.newInstance();

	}
	
	@Override
	protected int insert(DataSource dataSource, String tableName,Map<String,Object> paramMap) {
		int insertedRow = new SimpleJdbcInsert(dataSource).withTableName(tableName).execute(paramMap);
		return insertedRow;
	}
	
	@Override
	protected int insert(DataSource dataSource, String tableName,Map<String,Object> paramMap,String... columnNamesToBeInserted) {
		int insertedRow = new SimpleJdbcInsert(dataSource).withTableName(tableName).usingColumns(columnNamesToBeInserted).execute(paramMap);
		return insertedRow;
	}
	
	@Override
	protected Number insertAndReturnGeneratedKey(DataSource dataSource, String tableName,Map<String,Object> paramMap,String autoGeneratedKeycolumnName) {
		Number autoGeneratedRow = new SimpleJdbcInsert(dataSource).withTableName(tableName).usingGeneratedKeyColumns(autoGeneratedKeycolumnName).executeAndReturnKey(paramMap);
		return autoGeneratedRow;
	}
	
	@Override
	protected Number insertAndReturnGeneratedKey(DataSource dataSource, String tableName,Map<String,Object> paramMap,String autoGeneratedKeycolumnName,String... columnNamesToBeInserted) {
		Number autoGeneratedRow = new SimpleJdbcInsert(dataSource).withTableName(tableName)
														.usingColumns(columnNamesToBeInserted)
														.usingGeneratedKeyColumns(autoGeneratedKeycolumnName).executeAndReturnKey(paramMap);
		return autoGeneratedRow;
	}
	
	@Override
	protected KeyHolder insertAndReturnGeneratedKeyHolder(DataSource dataSource, String tableName,Map<String,Object> paramMap,String... autoGeneratedKeycolumnName) {
		KeyHolder keyHolder = new SimpleJdbcInsert(dataSource).withTableName(tableName).usingGeneratedKeyColumns(autoGeneratedKeycolumnName).executeAndReturnKeyHolder(paramMap);
		return keyHolder;
	}
	
	protected static String preparedMultipleSQLParamInput(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= length; i++) {
			sb.append("?,");
		}
		return sb.toString().substring(0, sb.toString().length()-1);
	}
	
	protected static List<Object> preparedMultipleSQLValueInput(List<Object> params, String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			params.add(ids[i]);
		}
		return params;
	}
	
}
