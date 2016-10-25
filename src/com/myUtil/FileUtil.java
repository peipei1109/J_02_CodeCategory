package com.myUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.WriteAbortedException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public abstract class FileUtil {
	
	static Pattern ipPattern =Pattern.compile("^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}(:.{1,5}|)");
	
	public ArrayList<String> getTextPaths(File directory, boolean subDirectoryIncluded) throws IOException{
		ArrayList<String> resultsList=new ArrayList<String>();
		File childFiles[] =directory.listFiles();
		for(File file: childFiles){
			if(file!=null && file.isFile()){
				resultsList.add(file.getCanonicalPath());
				}
			if(!subDirectoryIncluded||!file.isDirectory()){
				continue;
			}
			
			ArrayList<String> tmp=getTextPaths(file, true);
			
			if(tmp!=null){
				resultsList.addAll(tmp);
			}
			
		}
		
		if (resultsList.size()>0){
			return resultsList;
		}
		else
			return null;
		
	}
	
	
	public static String[] split(String str, char seperator){
		ArrayList<String> list=new ArrayList<String>();
		StringBuffer buffer =new StringBuffer();
		int len =str.length();
		for(int i=0;i<len;i++){
			char c=str.charAt(i);
			if(c!=seperator){
				buffer.append(c);
			}
			
			if(c==seperator||i==len-1){
				list.add(buffer.toString());
				buffer.setLength(0);
			}
		}
		
		
		String s[] =new String[list.size()];
		list.toArray(s);
		
		return s;
	}
			

	public String getHostName(String host){
		String hostName =null;
		if(ipPattern.matcher(host).matches()){
			hostName=split(host, ':')[0];
			
		}else{
			int lastDotIndex,lastBut2DotIndex;
			if(host.endsWith(".com.cn")){
				lastDotIndex=host.lastIndexOf(".com.cn");
			}
			else{
			//其他的如.com,.cn,.org, .net, .gov
			lastDotIndex=host.lastIndexOf(".");}
			
			lastBut2DotIndex =host.lastIndexOf(".",lastDotIndex-1);
			if(lastBut2DotIndex>=0&&lastDotIndex>lastBut2DotIndex){
				hostName=host.substring(lastBut2DotIndex+1, lastDotIndex);
			}
		}
		return hostName;
	}
	
	
	public String formatToUpperHexString(String str) throws ParseException{
		str=Integer.toHexString(Integer.parseInt(str)).toUpperCase();
		StringBuffer sb=new StringBuffer(str);
		while(sb.length()<4){
			sb.insert(0,"0");
			
		}
		
		return sb.toString();
	}
	
	/*ZIP文件*/
	 public static void readZipFile(String file) throws Exception {  
         ZipFile zf = new ZipFile(file);  
         InputStream in = new BufferedInputStream(new FileInputStream(file));  
         ZipInputStream zin = new ZipInputStream(in);  
         ZipEntry ze; 
        
         while ((ze = zin.getNextEntry()) != null) {  
             if (ze.isDirectory()) {
             } else {  
                 System.err.println("file - " + ze.getName() + " : "  
                         + ze.getSize() + " bytes");  
                 long size = ze.getSize();  
                 if (size > 0) {  
                     BufferedReader br = new BufferedReader(  
                             new InputStreamReader(zf.getInputStream(ze)));  
                     String line;  
                     while ((line = br.readLine()) != null) {  
                         System.out.println(line);  
                     }  
                     br.close();  //这里面就把ze.closeEntry()了，否则是不可能度第二个ze项的
                 }  
                 System.out.println();  
             }  
         }  
         zin.closeEntry();  
     }  
	
	/*网上趴下来的一个很好的例子.*/
    public static void writeFile(String content, String filePath) {
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileWriter fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    
    
    //对磁盘上的文件操作最好用字节流，节省内存~~~~
    //字符类型的读写是会先写到内存的缓存区内的，如果不关闭流就没有任何输出，而字节流可以边读边写，不一定要先关闭
	public abstract void readFile(String srcPath) throws NumberFormatException, IOException, ParseException;
	public abstract void writeFile() throws IOException;

}
