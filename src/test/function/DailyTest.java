package test.function;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.chemyoo.file.ReadLocalFiles;
import com.chemyoo.utils.HttpClientUtils;
import com.chemyoo.utils.LoggerUtils;
import com.chemyoo.utils.PropertiesUtil;
import com.chemyoo.utils.ReadFileToStream;
import com.chemyoo.utils.SystemUtils;

import net.sf.json.JSONObject;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年3月14日 下午1:55:19
 * @since 2018年3月14日 下午1:55:19
 * @description 类说明
 */
public class DailyTest {

	private DailyTest() {
	}

	private static void booleanArrayTest() {
		boolean[][] boolArrat = new boolean[4][4];
		boolean flag = false;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				flag = (Math.random() * 10) > 5;
				boolArrat[i][j] = flag;
				if (flag) {
					System.out.print("*");
				} else {
					System.out.print("-");
				}
			}
			System.out.println();
		}
		System.out.println("--------------------");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(boolArrat[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		booleanArrayTest();
		
//		String xml = HttpClientUtils.getRedirectUrl("https://raw.githubusercontent.com/chemyoo"
//				+ "/SpringPrject/dev/resourse/applicationContext.xml");
//		
//		//xml字符串转xml文档
//		StringReader sr = new StringReader(xml); 
//		InputSource is = new InputSource(sr); 
//		
//		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		
//		try {
//			DocumentBuilder db = dbFactory.newDocumentBuilder();
//			//网络链接转xml文档
//			Document doc = db.parse("https://raw.githubusercontent.com/chemyoo"
//					+ "/SpringPrject/dev/resourse/applicationContext.xml");
//			
//			db.parse(is);
//			
//			doc.getDocumentElement();
//		} catch (ParserConfigurationException e) {
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		LoggerUtils.logError(DailyTest.class, xml);
		
		
		PropertiesUtil.getProperties("databaseConfig.properties");
		
		LoggerUtils.logWarn(DailyTest.class, SystemUtils.getTomcatHome());
		
		
		ReadLocalFiles reader = new ReadLocalFiles("E:/Tencent Rec File", "json");
		reader.setReadOnlyOne();
		File[] file = reader.readFiles();
		ReadFileToStream stream = new ReadFileToStream(file[0]);
		InputStream inputStream= stream.getInputStream();
		JSONObject json;
		try {
			json = JSONObject.fromObject(IOUtils.toString(inputStream));
			System.err.println(json);
			stream.closeQuietly();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
