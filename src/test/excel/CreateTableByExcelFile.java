package test.excel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.chemyoo.file.ReadLocalFiles;
import com.chemyoo.utils.ChemyooUtils;

/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年6月4日 下午6:47:23 
 * @since 2018年6月4日 下午6:47:23 
 * @description 类说明 
 */
public class CreateTableByExcelFile {
	
	public static void main(String[] args) {
		ReadLocalFiles reader = new ReadLocalFiles("F:\\fs不动产数据\\导出的数据", "xls", "xlsx");
		File[] files = reader.readFiles();
		if (files != null) {
			List<String> titles = null;
			String tableName;
			StringBuilder builder = new StringBuilder();
			for (File f : files) {
				if(f.isFile()) {
					titles = ChemyooUtils.getTitles(f.getAbsolutePath());
					if(titles.isEmpty()) {
						continue;
					}
					tableName = (f.getName().substring(0, f.getName().indexOf('.')));
					gentherSql(builder, tableName, titles);
					createSequence(builder, tableName);
					createTrigger(builder, tableName);
				}
			}
			try (FileWriter writer = new FileWriter("D:/tablesSql.sql")) {
				writer.write(builder.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.err.println();
		}
	}
	
	private static void gentherSql(StringBuilder builder, String tableName, List<String> colunms) {
		builder.append("create table ").append(tableName).append("(");
		builder.append(System.getProperty("line.separator"));
		int index = 1;
		for(String col : colunms) {
			builder.append(col);
			if("ID".equals(col)){
				builder.append(" number(19) not null");
			} else if(col.toLowerCase().endsWith("time")) {
				builder.append(" date");
			}else {
				builder.append(" varchar(255)");
			}
			if(index < colunms.size()) {
				builder.append(",");
				builder.append(System.getProperty("line.separator"));
			} else {
				builder.append(System.getProperty("line.separator"))
					   .append(");")
					   .append(System.getProperty("line.separator"))
					   .append("/");
			}
			index ++;
		}
		builder.append(System.getProperty("line.separator"));
	}
	
	private static void createSequence(StringBuilder builder, String tableName) {
		builder.append("create sequence ").append(tableName + "_SEQ")
			   .append(" increment by 1 start with 1 maxvalue 99999999999999999;")
			   .append(System.getProperty("line.separator"))
			   .append("/")
			   .append(System.getProperty("line.separator"));
	}
	
	private static void createTrigger(StringBuilder builder, String tableName) {
		builder.append("create or replace trigger ").append(tableName + "_TRIG")
			   .append(System.getProperty("line.separator"))
			   .append(" before insert on ")
			   .append(tableName)
			   .append(" for each row")
			   .append(System.getProperty("line.separator"))
			   .append("declare")
			   .append(System.getProperty("line.separator"))
			   .append(" -- local variables here")
			   .append(System.getProperty("line.separator"))
			   .append("begin")
			   .append(System.getProperty("line.separator"))
			   .append("select ")
			   .append(tableName.toLowerCase() + "_seq.nextval ")
			   .append("into :new.id from dual;")
			   .append(System.getProperty("line.separator"))
			   .append("end ")
			   .append(tableName + "_TRIG")
			   .append(";")
			   .append(System.getProperty("line.separator"))
			   .append("/")
			   .append(System.getProperty("line.separator"));
	}
	
}
