package reptiles.mydemo3;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class reportText {
	public static void main(String[] args) throws Exception {
		
		String str="aaaa\nffff";
		
		File file=new File("D:\\baiduDownload\\str.txt");
		
		OutputStream out=new FileOutputStream(file);
		
		InputStream   in_withcode   =   new   ByteArrayInputStream(str.getBytes("UTF-8")); 
	
		IOUtils.copy(in_withcode, out);
	}
}
