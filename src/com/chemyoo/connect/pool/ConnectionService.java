package com.chemyoo.connect.pool;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.chemyoo.annotations.NotField;
import com.chemyoo.annotations.Table;
import com.chemyoo.bean.User;
import com.chemyoo.enums.Roles;
import com.chemyoo.utils.ChemyooUtils;
import com.chemyoo.utils.TimeUtils;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年9月26日 上午11:56:42 
 * @since since from 2018年9月26日 上午11:56:42 to now.
 * @description class description
 */
public class ConnectionService<T> implements BaseService<T> {
	
	private static final String INSERT = "INSERT INTO {#.table} ({#.columns}) VALUES ({#.values})";
	
	private static final String UPDATE = "UPDATE {#.table} SET ({#.values}) {#.where}";
	
	private static final String DELETE = "DELETE {#.table} WHERE {#.where}";
	
	private static final String TABLE_REGEX = "{#.table}";
	
	private static final String COLUMN_REGEX = "{#.columns}";
	
	private static final String VALUES_REGEX = "{#.values}";
	
	private static final String WHERE_REGEX = "{#.where}";
	
	private static final String SELECT = "SELECT {#.columns} FROM {#.table} {#.where}";
	
	protected Class<T> entityClass;

	protected String classSimpleName;
	
	private Table table;
	
	private static final String COLUMN_NAMES = "columnNames";
	
	private static final String COLUMN_VALUES = "columnValues";
	
	@SuppressWarnings("unchecked")
	public ConnectionService() {
		this.entityClass = (Class<T>) getSuperClassGenricType(this.getClass());
		this.classSimpleName = this.entityClass.getSimpleName();
		this.table = entityClass.getAnnotation(Table.class);
	}
	
	public static Class<?> getSuperClassGenricType(final Class<?> clazz) {
		Type type = clazz.getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		if (!(params[0] instanceof Class<?>)) {
			return Object.class;
		}
		return (Class<?>) params[0];
	}

	
	public String getTableName() {
		if(table == null) {
			throw new IllegalArgumentException("此类没有表映射...");
		}
		return table.name();
	}

	@Override
	public boolean insert(T t) throws SQLException {
		Map<String, List<Object>> map = this.getInsertColumnNamesAndValues(t);
		Object[] params = map.get(COLUMN_VALUES).toArray();
		List<Object> names = map.get(COLUMN_NAMES);
		StringBuilder columnsBuider = new StringBuilder();
		StringBuilder valuesBuider = new StringBuilder();
		int index = 0;
		int size = names.size();
		for(Object name : names) {
			columnsBuider.append((String)name);
			valuesBuider.append("?");
			index ++;
			if(index < size) {
				columnsBuider.append(", ");
				valuesBuider.append(", ");
			}
		}
		String sql = INSERT.replace(TABLE_REGEX, getTableName())
				.replace(COLUMN_REGEX, columnsBuider.toString())
				.replace(VALUES_REGEX, valuesBuider.toString());
		return this.execute(sql,params);
	}

	@Override
	public int update(T t) throws SQLException {
		return 0;
	}

	@Override
	public int delete(T t) throws SQLException {
		return 0;
	}

	@Override
	public int[] insert(List<T> vos) throws SQLException {
		return new int[] {};
	}

	@Override
	public int[] update(List<T> vos) throws SQLException {
		return new int[] {};
	}

	@Override
	public int[] delete(List<T> vos) throws SQLException {
		return new int[] {};
	}

	@Override
	public boolean execute(String sql, Object... pramas) throws SQLException {
		Connection connect = ConnectionPoolsManager.getInstanse().getConnection();
		boolean r = false;
		try(PreparedStatement statement = connect.prepareStatement(sql);){
			if(ArrayUtils.isNotEmpty(pramas)) {
				for(int i = 0, size = pramas.length; i <  size; i++) {
					if(pramas[i] instanceof Date) {
						statement.setTimestamp(i + 1, new Timestamp(((Date)pramas[i]).getTime()));
					} else 
						statement.setObject(i + 1, pramas[i]);
				}
			}
			r =  statement.execute();
			ConnectionPoolsManager.getInstanse().freeConnection(connect);
		}
		return r;
	}

	@Override
	public int[] execute(List<String> sqls, Object[][] pramas) throws SQLException {
		return new int[] {};
	}
	
	public List<T> query(String sql, Object... pramas) throws SQLException {
		Connection connect = ConnectionPoolsManager.getInstanse().getConnection();
		List<T> list = new ArrayList<>();
		ResultSet r = null;
		try(PreparedStatement statement = connect.prepareStatement(sql);){
			if(ArrayUtils.isNotEmpty(pramas)) {
				for(int i = 0, size = pramas.length; i <  size; i++) {
					if(pramas[i] instanceof Date) {
						statement.setTimestamp(i + 1, new Timestamp(((Date)pramas[i]).getTime()));
					} else 
						statement.setObject(i + 1, pramas[i]);
				}
			}
			r =  statement.executeQuery();
			ConnectionPoolsManager.getInstanse().freeConnection(connect);
			list = ConnectionPoolsManager.getInstanse().aliasToBean(entityClass, r);
		} catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map<Object, Object>> queryToMap(String sql, Object... pramas) throws SQLException {
		Connection connect = ConnectionPoolsManager.getInstanse().getConnection();
		List<Map<Object, Object>> list = new ArrayList<>();
		ResultSet r = null;
		try(PreparedStatement statement = connect.prepareStatement(sql);){
			if(ArrayUtils.isNotEmpty(pramas)) {
				for(int i = 0, size = pramas.length; i <  size; i++) {
					if(pramas[i] instanceof Date) {
						statement.setTimestamp(i + 1, new Timestamp(((Date)pramas[i]).getTime()));
					} else 
						statement.setObject(i + 1, pramas[i]);
				}
			}
			r =  statement.executeQuery();
			ConnectionPoolsManager.getInstanse().freeConnection(connect);
			list = ConnectionPoolsManager.getInstanse().aliasToMap(r);
		}
		return list;
	}
	
	private Map<String, List<Object>> getInsertColumnNamesAndValues(T t) {
		List<Object> columnNames = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		for(Field field : entityClass.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object value = field.get(t);
				com.chemyoo.annotations.Field f = field.getAnnotation(com.chemyoo.annotations.Field.class);
				NotField notfield = field.getAnnotation(NotField.class);
				String name = field.getName();
				if(f != null) {
					String tempName = f.name();
					if(ChemyooUtils.isNotEmpty(tempName)) {
						name = tempName;
					}
				}
				if(f != null && f.primaryKey()) {
					values.add(ChemyooUtils.keyGenerator(false));
					columnNames.add(name);
				} else if(value != null && notfield == null) {
					values.add(field.get(t));
					columnNames.add(name);
				}
				field.setAccessible(false);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		Map<String, List<Object>> mapValues = new HashMap<>();
		mapValues.put(COLUMN_NAMES, columnNames);
		mapValues.put(COLUMN_VALUES, values);
		return mapValues;
	}
	
	private String getAllColumns() {
		StringBuilder builder = new StringBuilder();
		for(Field field : entityClass.getDeclaredFields()) {
			com.chemyoo.annotations.Field f = field.getAnnotation(com.chemyoo.annotations.Field.class);
			NotField notfield = field.getAnnotation(NotField.class);
			String name = field.getName();
			if(f != null) {
				String tempName = f.name();
				if(ChemyooUtils.isNotEmpty(tempName)) {
					name = tempName;
				}
			}
			if(notfield == null) {
				builder.append(name).append(", ");
			}
		}
		int length = builder.length();
		if(length > 0) {
			builder.deleteCharAt(length - 2);
		}
		return builder.toString();
	}
	
	private String getIdColumns() {
		for(Field field : entityClass.getDeclaredFields()) {
			com.chemyoo.annotations.Field f = field.getAnnotation(com.chemyoo.annotations.Field.class);
			String name = field.getName();
			if(f != null && f.primaryKey()) {
				String tempName = f.name();
				if(ChemyooUtils.isNotEmpty(tempName)) {
					name = tempName;
				}
				return name;
			}
		}
		return null;
	}

	@Override
	public T findById(String id) {
		List<T> list = this.getEmptyList();
		try {
			String idCol = this.getIdColumns();
			StringBuilder whereBuilder = new StringBuilder();
			if(idCol != null) {
				whereBuilder.append("WHERE ").append(idCol).append(" = ? ");
			}
			String sql = SELECT.replace(COLUMN_REGEX, getAllColumns()).replace(TABLE_REGEX, getTableName())
					.replace(WHERE_REGEX, whereBuilder.toString());
			list = query(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.isEmpty() ? null : list.get(0);
	}
	
	private List<T> getEmptyList(){
		return new ArrayList<>();
	}

	@Override
	public List<T> find(T t) {
		return null;
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setDr(0);
		user.setCreateTime(TimeUtils.getNow());
		user.setRole(Roles.COMMON.getCode());
		try {
			ConnectionService<User> service = new ConnectionService<User>(){};
			service.insert(user);
			System.err.println(service.findById("20180927153002799J"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
