package com.chemyoo.init;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.chemyoo.annotations.Field;
import com.chemyoo.annotations.NotField;
import com.chemyoo.annotations.Table;
import com.chemyoo.connect.pool.ConnectionPoolsManager;
import com.chemyoo.entiry.ColunmEntiry;
import com.chemyoo.entiry.TableEntiry;
import com.chemyoo.enums.JavaType;
import com.chemyoo.utils.ChemyooUtils;
import com.chemyoo.utils.SystemUtils;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年1月8日 上午10:45:46
 * @param <T>
 * @since 2018年1月8日 上午10:45:46
 * @description 类说明
 */
public class ScanPackage extends HttpServlet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3231708832390454562L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String beanPath = config.getInitParameter("beanPackage");
		if (ChemyooUtils.isNotEmpty(beanPath)) {
			String[] beanPaths = beanPath.split(",");
			this.scanTables(beanPaths);
		}
	}

	private void scanTables(String[] beanPaths) throws ServletException {
		String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		System.err.println(classPath);
		List<TableEntiry> tables = new ArrayList<TableEntiry>();
		try {
			File file;
			for (String beanPath : beanPaths) {
				file = new File(classPath + beanPath.replace(".", SystemUtils.getFileSeparator()));
				if (!file.exists()) {
					throw new ServletException("package [" + beanPath + "] does not exists!");
				}
				File[] childrenFiles = file.listFiles();
				Class<?> clazz;
				TableEntiry table = null;
				for (File f : childrenFiles) {
					if (!f.getName().endsWith(".class")) {
						continue;
					}
					clazz = Class.forName(beanPath + "." + f.getName().replace(".class", ""));
					Table[] tableAnnotations = clazz.getAnnotationsByType(Table.class);
					if (tableAnnotations != null && tableAnnotations.length != 0) {
						table = new TableEntiry();
						table.setTableName(tableAnnotations[0].name());
						table.addColunms(this.getColunms(clazz));
						tables.add(table);
					} else {
						continue;
					}
				}
			}
			this.createTables(tables);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	private List<ColunmEntiry> getColunms(Class<?> clazz) {
		ColunmEntiry colunm = null;
		List<ColunmEntiry> columns = new ArrayList<ColunmEntiry>();
		java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
		for (java.lang.reflect.Field field : fields) {
			NotField notField = field.getAnnotation(NotField.class);
			Field annotation = field.getAnnotation(Field.class);
			if (notField != null) {
				continue;
			}
			colunm = new ColunmEntiry();
			if (annotation != null && !"".equals(annotation.name())) {
				colunm.setColunmName(annotation.name());
			} else {
				colunm.setColunmName(field.getName());
				if (annotation != null) {
					colunm.setPrimaryKey(annotation.primaryKey());
				}
			}
			colunm.setDataType(JavaType.getSqlType(field.getType(), annotation));
			columns.add(colunm);
		}
		return columns;
	}

	private void createTables(List<TableEntiry> tables) throws ServletException {
		if (ChemyooUtils.isNotEmpty(tables)) {
			List<String> sqls = new ArrayList<>();
			List<ColunmEntiry> colunms = null;
			for (TableEntiry table : tables) {
				StringBuffer strbuff = new StringBuffer();
				colunms = table.getColunms();
				if (ChemyooUtils.isEmpty(colunms)) {
					strbuff = null;
					throw new ServletException("The table entiry not include any field");
				}
				// strbuff.append("IF NOT EXSITS()");
				strbuff.append("CREATE TABLE ");
				strbuff.append(table.getTableName());
				strbuff.append("( ");

				for (ColunmEntiry colunm : colunms) {
					strbuff.append(SystemUtils.getLineSeparator());
					strbuff.append(colunm.getColunmName());
					strbuff.append(" ");
					strbuff.append(colunm.getDataType());
					strbuff.append(" ");
					strbuff.append((colunm.isPrimaryKey()) ? "PRIMARY KEY" : "");
					strbuff.append(",");
				}
				strbuff.delete(strbuff.length() - 1, strbuff.length());
				strbuff.append(SystemUtils.getLineSeparator());
				strbuff.append(") ");
				strbuff.append(SystemUtils.getLineSeparator());
				sqls.add(strbuff.toString());
			}
			this.excuteSql(sqls);
		}
	}

	private void excuteSql(List<String> sqls) {
		Connection connect = null;
		try {
			connect = ConnectionPoolsManager.getInstanse().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (
				Statement statement = connect.createStatement();
			) {
			connect.setAutoCommit(true);
			for(String sql : sqls)
				statement.addBatch(sql);
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPoolsManager.getInstanse().freeConnection(connect);
		}
	}
}
