package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.chemyoo.enums.FileType;
import com.chemyoo.file.ReadLocalFiles;
import com.chemyoo.utils.ChemyooUtils;
import com.chemyoo.utils.ReadFileToStream;

public class Test {

    String a = "hello";

    public static void main(String args[]){

        Test test = new Test();
        test.changeValue(test.a);
        System.err.println(test.a);
        
        ReadLocalFiles readFiles = new ReadLocalFiles("E:/", "docx","jpg","jpeg","zip","xlsx");
        readFiles.setReadOnlyOne();
        File[] files = readFiles.readFiles();
       	if(files == null || files.length == 0)
       	{
       		return;
       	}
        ReadFileToStream file = new ReadFileToStream(files[0]);
        InputStream is = file.getInputStream();
        //读取16进制文件头
        String filecontent = ChemyooUtils.getFileContent(is);
        String fileType = FileType.getFileType(filecontent);
        try {
			OutputStream out = new FileOutputStream("D:/"+files[0].getName().substring(0, files[0].getName().lastIndexOf(".")+1)+fileType);
			IOUtils.copy(is, out);
			IOUtils.closeQuietly(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    public void changeValue(String a)
    {
        a = "word";
        this.a = "xml";
    }
    
}
