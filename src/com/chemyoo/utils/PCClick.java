package com.chemyoo.utils;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;

public class PCClick {

	public static void main(String[] args) throws AWTException, IOException, InterruptedException {
		
		Runtime rt = Runtime.getRuntime();
		Process ps = rt.exec("cmd /c C:\\Users\\Liujianqing\\Desktop\\StopEasyConnect.bat");
		ps.waitFor();
//		byte[] out = new byte[1024];
//		DataInputStream dos = new DataInputStream(ps.getInputStream());
//		while(dos.read(out) > 0) {
//			//ignore
//		}
//		dos.close();
		// CMD输出文字
//		String s = new String(out);
//		System.err.println(s);
		if (ps.exitValue() != 0) {
			System.err.println("程序未运行");
		} else {
			System.err.println("程序关闭");
		}
		
		File file = new File("C:\\Program Files (x86)\\Sangfor\\SSL\\SangforCSClient","SangforCSClient.exe");
		Desktop.getDesktop().open(file);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Double screenW = dimension.getWidth() / 2;
		Double screenH = dimension.getHeight() / 2;
		Robot robot = new Robot();
		robot.delay(15000);
		System.err.println(screenW + ", " + screenH);
		robot.mouseMove(screenW.intValue(), screenH.intValue());
		robot.delay(500);
		robot.mouseMove(screenW.intValue(), screenH.intValue());
		robot.delay(500);
		robot.mouseMove(screenW.intValue(), screenH.intValue());
		robot.delay(1000);
		robot.mouseMove(screenW.intValue() + 75, screenH.intValue() + 118);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(1000);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

}
