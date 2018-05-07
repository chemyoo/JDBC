package test.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.chemyoo.file.ReadLocalFiles;
import com.chemyoo.utils.ChemyooUtils;

/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年4月25日 上午11:37:16 
 * @since 2018年4月25日 上午11:37:16 
 * @description 类说明 
 */
public class ReadExcelData {
	
	private static final String R_KEY = "拜访里程";
	
	private static final String MONTH_NAME = "月份";
	
	public static void main(String[] args) throws FileNotFoundException {
		ReadLocalFiles reader = new ReadLocalFiles("E:\\Tencent Rec File","xls","xlsx");
		File[] files = reader.readFiles();
		if(files != null) {
			List<Map<String, Object>> list = null;
			List<Map<String, Object>> outData = new ArrayList<>();
			for(File f : files) {
				list = ChemyooUtils.commonReadExcelData(f.getAbsolutePath());
				if(outData.isEmpty()) {
					for(Map<String, Object> map : list) {
						map.put(map.get(MONTH_NAME)+"月", map.get(R_KEY));
						map.remove(MONTH_NAME);
						map.remove(R_KEY);
						outData.add(map);
					}
				} else {
					for(Map<String, Object> out : outData) {
						for(int i = list.size()-1;i >-1; i--) {
							if(out.get("行政区编码").equals(list.get(i).get("行政区编码"))) {
								out.put(list.get(i).get(MONTH_NAME)+"月", list.get(i).get(R_KEY));
								list.remove(i);
								break;
							}
						}
					}
					if(!list.isEmpty()) {
						for(Map<String, Object> map : list) {
							map.put(map.get(MONTH_NAME)+"月", map.get(R_KEY));
							map.remove(MONTH_NAME);
							map.remove(R_KEY);
							outData.add(map);
						}
					}
				}
			}
			File outFile = new File("D:/outFile/" + R_KEY +".xlsx");
			if(outFile.exists() && !outFile.delete()) {
				throw new FileNotFoundException("文件正在被其它程序使用！");
			} 
			else if(!new File("D:/outFile/").exists()){
				new File("D:/outFile/").mkdirs();
			}
			if(!outData.isEmpty()) {
				OutputStream outTarget = new FileOutputStream(outFile);
				List<String> titles = new ArrayList<>(outData.get(0).keySet());
				Collections.sort(titles,new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						int len1 = o1.length();
						int len2 = o2.length();
						int size = len1 > len2 ? len1 : len2;
						for(int i = 0; i < size; i++) {
							int code1 = o1.codePointAt(i);
							int code2 = o2.codePointAt(i);
							if(code1 != code2) {
								int res = code2 - code1;
								if(code1 > 47 && code1 < 68 && code2 > 47 && code2 < 68) {
									return -res;
								}
								return res;
							}
						}
						return len2 - len1;
					}
				});
				Collections.sort(outData,new Comparator<Map<String,Object>>() {
					@Override
					public int compare(Map<String,Object> o1, Map<String,Object> o2) {
						int len1 = ((String)o1.get("行政区编码")).length();
						int len2 = ((String)o2.get("行政区编码")).length();
						int size = len1 > len2 ? len1 : len2;
						for(int i = 0; i < size; i++) {
							int code1 = ((String)o1.get("行政区编码")).codePointAt(i);
							int code2 = ((String)o2.get("行政区编码")).codePointAt(i);
							if(code1 != code2) {
								return code1- code2;
							}
						}
						return len2 - len1;
					}
				});
				ChemyooUtils.commonExportData2Excel(outTarget , titles, outData, "0", true);
				System.err.println("文件路径："+outFile.getAbsolutePath());
			}
		}
	}
}
