package test;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.chemyoo.entiry.GeographicCoordinates;
import com.chemyoo.enums.FileType;
import com.chemyoo.enums.NumberFormatBase;
import com.chemyoo.enums.Roles;
import com.chemyoo.file.ReadLocalFiles;
import com.chemyoo.pub.SecurityCode;
import com.chemyoo.utils.ChemyooUtils;
import com.chemyoo.utils.HttpClientUtils;
import com.chemyoo.utils.LocationUtils;
import com.chemyoo.utils.LoggerUtils;
import com.chemyoo.utils.ReadFileToStream;
import com.chemyoo.utils.SystemUtils;
import com.chemyoo.utils.TimeUtils;

public class Test {

	String a = "hello";

	public static void main(String args[]) {

		String str = "杩滅▼涓绘満寮鸿揩鍏抽棴浜嗕竴涓幇鏈夌殑杩炴帴";
		System.out.println(ChemyooUtils.encode2UTF8(str, "GBK"));
		
		System.err.println(ChemyooUtils.formatDecimalDigits(4, NumberFormatBase.Binary));

		System.err.println();
		Test test = new Test();
		test.changeValue(test.a);
		System.err.println(test.a);
		test.a.toUpperCase();
		
		List<Test> list1 = new ArrayList<>();
		list1.add(test);
		List<Test> copy = ChemyooUtils.listCopyValue(list1);
		test.a = "change";
		
		System.err.println(SecurityCode.encrypt("12345678"));

		System.err.println(Roles.findRoleNameByCode(4));
		
		System.err.println(Roles.ROOT == null);
		
		System.err.println(HttpClientUtils.getRedirectUrl("http://www.5857.com/pcbz/76056.html"));

		System.err.println("日期验证：" + TimeUtils.validateDay("2018-03-01"));

		GeographicCoordinates coord1 = new GeographicCoordinates(0, 0);
		GeographicCoordinates coord2 = new GeographicCoordinates(120, 0);

		System.err.println(LocationUtils.getDistance(coord1, coord2) + "m");

		// ReadLocalFiles readFiles = new ReadLocalFiles("E:/", "ctr","jpg","png");

		OutputStream outTarget;
		try {
			outTarget = new FileOutputStream("D://aaaa.xlsx");
			List<String> titles = Arrays.asList("标题1", "标题2");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(titles.get(0), "测试1");
			map.put(titles.get(1), "测试2");
			list.add(map);
			list.add(map);
			list.add(map);
			// ChemyooUtils.commonExportData2Excel(outTarget, null , list, "", true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoggerUtils.logWarn(Test.class, SystemUtils.getClassPath());
		
		LoggerUtils.logWarn(Test.class, HttpClientUtils.getRedirectUrl("https://www.baidu.com"));
		
		try (FileWriter fw = new FileWriter("D:\\baidu.html");){
			LoggerUtils.logWarn(Test.class, HttpClientUtils.getRedirectUrl("https://www.baidu.com"));
			fw.write(HttpClientUtils.getRedirectUrl("https://www.baidu.com"));
			String tempName = "D:/t.abc";
			HttpClientUtils.dowload("http://www.lanrentuku.com/savepic/img/allimg/1609/5-160914192R2.gif",tempName);
			//校正名称
			Desktop.getDesktop().open(com.chemyoo.utils.IOUtils.validateFileName(new File(tempName)));
			HttpClientUtils.downloadFromInternet("https://www.baidu.com", "F:/music/baidu.html");
			HttpClientUtils.dowload("http://p.gdown.baidu.com/fdd85f511d3ae6e6b47a193f72232489141cf0cf4368df61827f9ec3765252353a979a4c5a9c19688b45ea732fb88cc25e78348250ff4c81e2abef957f93528877608e4405d19cb1e7865a14dda87cd41834029ffaebb7fbdd00f13edcbf48ac7f679ae9aa9eec6bb8420f96f1a4d67c", "F:/music/5146554.apk");
		} catch (IOException e) {
			e.printStackTrace();
		} 
		// readFiles.setReadOnlyOne();
		File[] files = null;// readFiles.readFiles();
		if (files == null || files.length == 0) {
			return;
		}
		// ReadFileToStream file = new ReadFileToStream(files[0]);
		// InputStream is = file.getInputStream();
		// String filecontent = ChemyooUtils.getFileContent(is);
		// String fileType = FileType.getFileType(filecontent);
		// //读取16进制文件头
		// String filecontent = ChemyooUtils.getFileContent(is);
		// String fileType = FileType.getFileType(filecontent);
		// try {
		// ReadFileToStream fileStream;
		// InputStream is;
		// for(File file : files) {
		// fileStream = new ReadFileToStream(file);
		// is = fileStream.getInputStream();
		// String filecontent = ChemyooUtils.getFileContent(is);
		// String fileType = FileType.getFileType(filecontent);
		// OutputStream out = new
		// FileOutputStream("D:/ThemeStore/new/"+file.getName().substring(0,
		// file.getName().lastIndexOf(".")+1)+fileType);
		// IOUtils.copy(is, out);
		// IOUtils.closeQuietly(out);
		// }
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}

	public void changeValue(String a) {
		a = "word";
		this.a = "xml";
	}

}
