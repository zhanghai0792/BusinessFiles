package com.hy.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
/**
 * 将文件夹下面的文件
 * 打包成zip压缩文件
 * 
 * @author admin
 *
 */
public final class FileToZip {

	private FileToZip(){}
	
	/**
	 * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
	 * @param sourceFilePath :待压缩的文件路径
	 * @param zipFilePath :压缩后存放路径
	 * @param fileName :压缩后文件的名称
	 * @return
	 */
	public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){
		boolean flag = false;
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		
		if(sourceFile.exists() == false){
			System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");
		}else{
			try {
				File zipFile = new File(zipFilePath + "/" + fileName +".zip");
				if(zipFile.exists()){
					System.out.println(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");
				}else{
					File[] sourceFiles = sourceFile.listFiles();
					if(null == sourceFiles || sourceFiles.length<1){
						System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
					}else{
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024*10];
						for(int i=0;i<sourceFiles.length;i++){
							//创建ZIP实体，并添加进压缩包
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
							zos.putNextEntry(zipEntry);
							//读取待压缩的文件并写进压缩包里
							fis = new FileInputStream(sourceFiles[i]);
							bis = new BufferedInputStream(fis, 1024*10);
							int read = 0;
							while((read=bis.read(bufs, 0, 1024*10)) != -1){
								zos.write(bufs,0,read);
							}
						}
						flag = true;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally{
				//关闭流
				try {
					if(null != bis) bis.close();
					if(null != zos) zos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		return flag;
	}
	
	/** 
     * 解压文件到指定目录 
     * @param zipFile 
     * @param descDir 
     * @author isea533 
     */  
    @SuppressWarnings("rawtypes")  
    public static void unZipFiles(File zipFile,String descDir)throws IOException{  
        File pathFile = new File(descDir);  
        if(!pathFile.exists()){  
            pathFile.mkdirs();  
        }  
        ZipFile zip = new ZipFile(zipFile);  
        for(Enumeration entries = zip.entries();entries.hasMoreElements();){  
            ZipEntry entry = (ZipEntry)entries.nextElement();  
            String zipEntryName = entry.getName();  
            InputStream in = zip.getInputStream(entry);  
            String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");;  
            //判断路径是否存在,不存在则创建文件路径  
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
            if(!file.exists()){  
                file.mkdirs();  
            }  
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
            if(new File(outPath).isDirectory()){  
                continue;  
            }  
            //输出文件路径信息  
          //  System.out.println(outPath);  
              
            OutputStream out = new FileOutputStream(outPath);  
            byte[] buf1 = new byte[1024];  
            int len;  
            while((len=in.read(buf1))>0){  
                out.write(buf1,0,len);  
            }  
            in.close();  
            out.close();  
            }  
        System.out.println("******************解压完毕********************");  
    } 
	
	/**
	 * @Desription: 从压缩包中解压缩报名xls文件
	 * @param srcFile 压缩包文件
	 * @return xls文件
	 */
	
	private File unzip(File srcFile) {
		File xlsFile = null;//解压缩后的xls文件		
		ZipFile zipFile;// 定义输出的文件对象
		try {
			zipFile = new ZipFile(srcFile);// 实例化ZipFile对象			
			ZipInputStream zipInput = new ZipInputStream(new FileInputStream(srcFile));// 实例化ZIP输入流
			ZipEntry entry = null; // 定义一个
			InputStream input = null; // 定义输入流，
			OutputStream out = null; // 定义输出流，
			String fileName = null;
			
			while ((entry = zipInput.getNextEntry()) != null) { // 得到每一个
				fileName = entry.getName();
				//System.out.println(fileName);
				if (fileName.endsWith(".xls")) {	
					//解压到指定目录下
					/***///unZipFiles(srcFile,getServletContext().getRealPath("/") + "temp\\");
					/***///xlsFile = new File(getServletContext().getRealPath("/") + "temp\\"+fileName);	
					
					input = zipFile.getInputStream(entry);
					// 得到压缩实体的输入流对象
					out = new FileOutputStream(xlsFile);
					
					// 实例化输出流对象
					int temp = 0;
					while ((temp = input.read()) != -1) {
						out.write(temp);
					}
					input.close();
					out.close();
					return xlsFile;
				}
			}
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	public static void main(String[] args){
		String sourceFilePath = "D:\\TestFile";
		String zipFilePath = "D:\\tmp";
		String fileName = "12700153file";
		boolean flag = FileToZip.fileToZip(sourceFilePath, zipFilePath, fileName);
		if(flag){
			System.out.println("文件打包成功!");
		}else{
			System.out.println("文件打包失败!");
		}
	}
	
}
