package com.chemyoo.connect.pool;

import java.sql.SQLException;
import java.util.List;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年9月26日 上午11:49:48 
 * @since since from 2018年9月26日 上午11:49:48 to now.
 * @description class description
 */
public interface BaseService<T> {
	
	T findById(String id);
	
	List<T> find(T t);
	
	boolean insert(T t) throws SQLException;
	
	int update(T t) throws SQLException;
	
	int delete(T t) throws SQLException;
	
	int[] insert(List<T> vos) throws SQLException;
	
	int[] update(List<T> vos) throws SQLException;
	
	int[] delete(List<T> vos) throws SQLException;
	
	boolean execute(String sql, Object... pramas) throws SQLException;
	
	int[] execute(List<String> sqls, Object[][] pramas) throws SQLException;
	
	List<T> query(String sql, Object... pramas) throws SQLException;
}
