package com.chemyoo.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class RestartLocalTomcat {
	
	private RestartLocalTomcat() {}
	
	public static void restart(String tomcatDir) {
		createCmdFile(tomcatDir);
		executeCmd(tomcatDir);
	}
	
	private static void executeCmd(String tomcatDir) {
		Runtime run = Runtime.getRuntime();
		try {
			Process ps = run.exec(tomcatDir + "\\bin\\restart.bat");
			//我很奇怪  下面的代码去掉的话 tomcat的黑框就不能出现  
			BufferedReader br = new BufferedReader(new InputStreamReader(
					ps.getInputStream(), "GBK"));// 注意中文编码问题
			String line;
			while ((line = br.readLine()) != null) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("StartedLog==>");
				stringBuilder.append(line);
				Logger.getLogger(RestartLocalTomcat.class).info(stringBuilder.toString());
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void createCmdFile(String tomcatDir) {
		File f = new File(tomcatDir + "\\bin\\restart.bat");
		try {
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			 //下面的必须加上
			bw.write("set CATALINA_HOME=" + tomcatDir);
			bw.newLine();
			bw.write("call " + f.getParent() + "\\shutdown.bat");
			bw.newLine();
			bw.write(" ping 127.0.0.1 -n 5  1>nul ");
			bw.newLine();
			bw.write("call " + f.getParent() + "\\startup.bat ");

			bw.close();
			fw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
