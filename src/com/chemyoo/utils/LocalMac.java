package com.chemyoo.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

/**
 * @author chemyoo
 *	本机Mac地址单例
 */
public class LocalMac 
{
	/**
	 * MAC地址静态内部类
	 */
	public static class Mac
	{
		private static StringBuffer instanse = null;
		private Mac() {}
		public static String getInstanse() 
		{
			synchronized (Mac.class) 
			{
				if(instanse == null || instanse.length()==0)
				{
					InetAddress ia;
					byte[] mac;
					try {
						ia = InetAddress.getLocalHost();
						mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
						instanse = new StringBuffer();
						for(int i=0; i<mac.length; i++) {
							if(i!=0) {
								instanse.append("-");
							}
							//字节转换为整数
							int temp = mac[i]&0xff;
							String str = Integer.toHexString(temp);
							//System.out.println("每8位:"+str);
							if(str.length()==1) {
								instanse.append("0"+str);
							}else {
								instanse.append(str);
							}
						}
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (SocketException e) {
						e.printStackTrace();
					}
				}
				return instanse.toString().toUpperCase();
			}
		}
	}
	
	public static void main(String [] args) throws IOException
	{
		JOptionPane.showMessageDialog(null,Mac.getInstanse(),"本机MAC地址为：",JOptionPane.PLAIN_MESSAGE);
		String userDir = System.getProperty("user.dir");
		String lineSeparator = System.getProperty("line.separator", "/n");
		File file = new File(userDir+"/logs.txt");
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter fw = new FileWriter(file,true);
		fw.append("本机MAC地址："+Mac.getInstanse()+lineSeparator);
		fw.close();
	}
}
