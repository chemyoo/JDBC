package test.function;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.chemyoo.utils.HttpClientUtils;
import com.chemyoo.utils.LoggerUtils;
import net.sf.json.JSONArray;

/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年6月14日 上午11:13:48 
 * @since 2018年6月14日 上午11:13:48 
 * @description 类说明 
 */
public class ConnectUrlTest {

	public static void main(String[] args) {
		String res;
		try {
			res = HttpClientUtils.getRedirectUrl(
					"http://localhost:8080/resdata-nlp/wordAnalysis.do?text=" 
			+ URLEncoder.encode("小明和小红一起去上学了","utf-8"));
			JSONArray json = JSONArray.fromObject(res);
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) JSONArray.toCollection(json,String.class);
			LoggerUtils.logWarn(ConnectUrlTest.class, res);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}
