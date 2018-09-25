package com.chemyoo.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;

import com.chemyoo.enums.FileType;
import com.chemyoo.utils.ChemyooUtils;
import com.chemyoo.utils.LoggerUtils;
import com.chemyoo.utils.ReadFileToStream;

public class ReadLocalFiles {

	private String path = null;

	private String[] fileExt;

	private boolean multiSelection = true;

	private static final String NO_SET = "no set any file extension";
	private static final String COMMA = ",";

	/**
	 * path : the director which file exsits fileExt : the extension of file
	 * 
	 * @param path
	 * @param fileExt
	 */

	public ReadLocalFiles(String path, String... fileExt) {
		this.path = path;
		this.fileExt = fileExt;
	}

	public void setReadOnlyOne() {
		this.multiSelection = false;
	}

	public File[] readFiles() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		JFileChooser jFile = new JFileChooser(path, fsv);
		jFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFile.setMultiSelectionEnabled(multiSelection);
		jFile.setAcceptAllFileFilterUsed(true);// 去掉显示所有文件的按钮
		jFile.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				StringBuilder strBuff = new StringBuilder();
				if (fileExt != null) {
					for (String ext : fileExt) {
						strBuff.append(COMMA);
						strBuff.append(ext);
						strBuff.append(" ");
					}
					strBuff.deleteCharAt(0);
					return strBuff.toString();
				}
				return NO_SET;
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					File[] files = f.listFiles();
					if (files != null) {
						return files.length != 0;
					}
				} else if (contains(ReadLocalFiles.this.fileExt, ReadLocalFiles.this.getExtension(f))) {
					return true;
				}
				return false;
			}
		});
		File[] files = null;
		if (jFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			if (multiSelection) {
				files = jFile.getSelectedFiles();
			} else {
				files = new File[] { jFile.getSelectedFile() };
			}
		} else {
			LoggerUtils.logWarn(getClass(), "No file is selected!");
		}
		return files;
	}

	public <T> boolean contains(T[] array, T data) {
		for (T val : array) {
			if (val.equals(data))
				return true;
		}
		return false;
	}

	public String getExtension(File f) {
		if (f != null) {
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(i + 1).toLowerCase();
			}
			;
		}
		return "";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReadLocalFiles readFiles = new ReadLocalFiles("E:/2345Downloads", "ozip");
		readFiles.setReadOnlyOne();
		File[] files = readFiles.readFiles();
		if (files != null)
			for (File file : files) {
				ReadFileToStream reader = new ReadFileToStream(file);
				InputStream stream = reader.getInputStream();
			    try {
					OutputStream outputStream = new FileOutputStream(new File("D:/z.zip"));
				        int byteCount = 0;
				        byte[] bytes = new byte[1024];
				        while ((byteCount = stream.read(bytes)) != -1)
				        {
				            outputStream.write(bytes);
				        }
				        outputStream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				reader.closeQuietly();
			}
	}

}
