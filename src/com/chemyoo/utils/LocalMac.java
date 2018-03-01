package com.chemyoo.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

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
					InetAddress ia = null;
					byte[] mac = null;
					try {
						// 如果没有发现 non-loopback地址.只能用最次选的方案
//						ia = InetAddress.getLocalHost();
//						mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
						
						Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
						boolean backup = false;
						NetworkInterface netWorkInterface = null;
						InetAddress inetAddress = null;
						Enumeration<InetAddress> address;
						label:
						while (netInterfaces.hasMoreElements()) {
							netWorkInterface = netInterfaces.nextElement();
							address = netWorkInterface.getInetAddresses();
							while(address.hasMoreElements()) {
								inetAddress = address.nextElement();
								// 排除loopback类型地址
								if(!inetAddress.isLoopbackAddress()) {
									if(inetAddress.isSiteLocalAddress()) {
										 // 如果是site-local地址，就是当前使用网卡地址
										ia = inetAddress;
										break label;
									} else if(!backup){
										// site-local类型的地址未被发现，先记录候选地址
										ia = inetAddress;
										backup = true;
									}
								} 
							}
						}
						//如果没有发现其他可用地址，则选用默认地址：127.0.0.1(localhost)
						if(ia == null) {
							ia = InetAddress.getLocalHost();
						}
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
							if(str.length() == 1) {
								instanse.append("0");
							}
							instanse.append(str);
						}
					} catch (SocketException e) {
						e.printStackTrace();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
				return instanse.toString().toUpperCase();
			}
		}
	}
	
	public static String[] getAllMacAddress() {
		List<String> macAddress = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			if(netInterfaces != null) {
				NetworkInterface netWorkInterface = null;
				while (netInterfaces.hasMoreElements()) {
					netWorkInterface = netInterfaces.nextElement();
					byte[] hardwareAddress = netWorkInterface.getHardwareAddress();
					if(hardwareAddress != null) {
						StringBuffer str = new StringBuffer();
						for(int i = 0; i < hardwareAddress.length ; i++) {
							String s = Integer.toHexString(hardwareAddress[i]&0xff);
							if(s.length() == 1) {
								str.append("0");
							}
							str.append(s);
							str.append("-");
						}
						str.deleteCharAt(str.length() - 1);
						macAddress.add(str.toString().toUpperCase());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return  macAddress.toArray(new String[macAddress.size()]);
	}
	
	public static String localIPAddress() {
		StringBuffer buffer = new StringBuffer();
		try {
			InetAddress ia = InetAddress.getLocalHost();
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			boolean backup = false;
			NetworkInterface netWorkInterface = null;
			InetAddress inetAddress = null;
			Enumeration<InetAddress> address;
			label:
			while (netInterfaces.hasMoreElements()) {
				netWorkInterface = netInterfaces.nextElement();
				address = netWorkInterface.getInetAddresses();
				while(address.hasMoreElements()) {
					inetAddress = address.nextElement();
					// 排除loopback类型地址
					if(!inetAddress.isLoopbackAddress()) {
						if(inetAddress.isSiteLocalAddress()) {
							 // 如果是site-local地址，就是当前使用网卡地址
							ia = inetAddress;
							break label;
						} else if(!backup && (ia instanceof Inet4Address)){
							// site-local类型的地址未被发现，先记录候选地址
							ia = inetAddress;
							backup = true;
						}
					} 
				}
			}
			buffer.append(ia.getHostAddress());
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	public static String toString(String[] strings) {
		StringBuffer stringBuffer= new StringBuffer("[");
		for(String str : strings) {
			stringBuffer.append(str).append(", ");
		}
		int length = stringBuffer.length();
		stringBuffer.delete(length - 2, length);
		stringBuffer.append("]");
		return stringBuffer.toString();
	}
	
	public static void main(String [] args) throws IOException
	{
		JOptionPane.showMessageDialog(null,LocalMac.localIPAddress() + "/" +Mac.getInstanse(),"本机IP/MAC地址为：",JOptionPane.PLAIN_MESSAGE);
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
		fw.append("本机所有MAC地址："+LocalMac.toString(LocalMac.getAllMacAddress())+lineSeparator);
//		System.err.println(LocalMac.toString(LocalMac.getAllMacAddress()));
//		System.err.println("IP:" + LocalMac.localIPAddress() + " MAC:" + Mac.getInstanse());
		fw.close();
	}
}
