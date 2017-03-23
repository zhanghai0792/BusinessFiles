package com.hy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * ClassName: ZipUtil 
 * @Description: ant.jar
 * @author wwh
 * @date 2016-12-13
 */
public class ZipUtil {

	/**
	 * 压缩
	 * 
	 * @param src
	 *            源文件或者目录
	 * @param dest
	 *            压缩文件路径
	 * @throws IOException
	 */
	public static void zip(String src, String dest) throws IOException {
		ZipOutputStream out = null;
		try {
			File outFile = new File(dest);
			out = new ZipOutputStream(outFile);
			File fileOrDirectory = new File(src);

			if (fileOrDirectory.isFile()) {
				zipFileOrDirectory(out, fileOrDirectory, "");
			} else {
				File[] entries = fileOrDirectory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					// 递归压缩，更新curPaths
					zipFileOrDirectory(out, entries[i], "");
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	/**
	 * 递归压缩文件或目录
	 * 
	 * @param out
	 *            压缩输出流对象
	 * @param fileOrDirectory
	 *            要压缩的文件或目录对象
	 * @param curPath
	 *            当前压缩条目的路径，用于指定条目名称的前缀
	 * @throws IOException
	 */
	public static void zipFileOrDirectory(ZipOutputStream out, File fileOrDirectory, String curPath) throws IOException {
		FileInputStream in = null;
		if(fileOrDirectory==null||!fileOrDirectory.exists())
			return;
		try {
			if (!fileOrDirectory.isDirectory()) {// 压缩文件
				byte[] buffer = new byte[4096];
				int bytes_read;
				in = new FileInputStream(fileOrDirectory);
				ZipEntry entry = new ZipEntry(curPath + fileOrDirectory.getName());//发送到压缩实体
				out.putNextEntry(entry);
				while ((bytes_read = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytes_read);
				}
				out.closeEntry();
			} else {
				// 压缩目录
				File[] entries = fileOrDirectory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					//zipFileOrDirectory(out, entries[i], curPath + fileOrDirectory.getName() + "/");// 递归压缩，更新curPaths
					zipFileOrDirectory(out, entries[i], curPath + "/");// 递归压缩，更新curPaths
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	public static void zipAddExcel(ZipOutputStream out,String sheetName){
		ZipEntry entry = new ZipEntry(sheetName+".xls");//发送到压缩实体
		try {
			out.putNextEntry(entry);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 解压缩
	 * 
	 * @param zipFileName
	 *            源文件
	 * @param outputDirectory
	 *            解压缩后文件存放的目录
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void unzip(String zipFileName, String outputDirectory) throws IOException {

		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(zipFileName);
			Enumeration e = zipFile.getEntries();

			ZipEntry zipEntry = null;

			File dest = new File(outputDirectory);
			dest.mkdirs();

			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();

				String entryName = zipEntry.getName();

				InputStream in = null;
				FileOutputStream out = null;

				try {
					if (zipEntry.isDirectory()) {
						String name = zipEntry.getName();
						name = name.substring(0, name.length() - 1);

						File f = new File(outputDirectory + File.separator + name);
						f.mkdirs();
					} else {
						int index = entryName.lastIndexOf("\\");
						if (index != -1) {
							File df = new File(outputDirectory + File.separator + entryName.substring(0, index));
							df.mkdirs();
						}
						index = entryName.lastIndexOf("/");
						if (index != -1) {
							File df = new File(outputDirectory + File.separator + entryName.substring(0, index));
							df.mkdirs();
						}

						File f = new File(outputDirectory + File.separator + zipEntry.getName());
						// f.createNewFile();
						in = zipFile.getInputStream(zipEntry);
						out = new FileOutputStream(f);

						int c;
						byte[] by = new byte[1024];

						while ((c = in.read(by)) != -1) {
							out.write(by, 0, c);
						}
						out.flush();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
					throw new IOException("解压失败：" + ex.toString());
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException ex) {
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (IOException ex) {
						}
					}
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
			throw new IOException("解压失败：" + ex.toString());
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException ex) {
				}
			}
		}

	}

	public static void main(String[] args) {
		try {
			ZipUtil.zip("G:\\JavaWeb\\apache-tomcat-7.0.42\\webapps\\BusinessFiles\\temp", "G:\\JavaWeb\\apache-tomcat-7.0.42\\webapps\\BusinessFiles\\ziptemp\\temp.zip");
			//ZipUtil.unzip("F:\\test.zip", "F:\\test");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
//=================	
	 private static void zipNext(String souceFileName, String destFileName) {  
	        File file = new File(souceFileName);  
	        try {  
	            zipNext(file, destFileName);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    private static void zipNext(File souceFile, String destFileName) throws IOException {  
	        FileOutputStream fileOut = null;  
	        try {  
	            fileOut = new FileOutputStream(destFileName);  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        }  
	        ZipOutputStream out = new ZipOutputStream(fileOut);  
	        zip(souceFile, out, "");  
	        out.close();  
	    }  
	  
	    private static void zip(File souceFile, ZipOutputStream out, String base)  
	            throws IOException {  
	  
	        if (souceFile.isDirectory()) {  
	            File[] files = souceFile.listFiles();  
	            out.putNextEntry(new ZipEntry(base + "/"));  
	            base = base.length() == 0 ? "" : base + "/";  
	            for (File file : files) {  
	                zip(file, out, base + file.getName());  
	            }  
	        } else {  
	            if (base.length() > 0) {  
	                out.putNextEntry(new ZipEntry(base));  
	            } else {  
	                out.putNextEntry(new ZipEntry(souceFile.getName()));  
	            }  
	           // System.out.println("filepath=" + souceFile.getPath());  
	            FileInputStream in = new FileInputStream(souceFile);  
	  
	            int b;  
	            byte[] by = new byte[1024];  
	            while ((b = in.read(by)) != -1) {  
	                out.write(by, 0, b);  
	            }  
	            in.close();  
	        }  
	    }  
	    
	    
	    /** 
	     * Test 
	     */  
	    public static void test() {  
	        ZipUtil z = new ZipUtil();  
	        z.zipNext("D:\\image11", "D:/Data1.zip");  
	    } 
	    
	    
	   /* ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("/opt/download/test.zip"));  
	    ZipEntry entry = new ZipEntry("images/yule/me/a.jpg");//在ZIP文件中的目录结构，最前面不要有斜杠。  
	    zos.putNextEntry(entry);  
	    InputStream is = new FileInputStream("/images/yule/me/a.jpg");  
	    int len = 0;  
	    while ((len = is.read()) != -1)  
	        zos.write(len);  
	    is.close();  
	    zos.close();*/
//================
	    
	    
	    
	    
	    /**

	    *

	    * @description：压缩文件操作

	    * @param filePath

	    * 要压缩的文件路径

	    * @param descDir

	    * 压缩文件保存的路径

	    */

	    public static void zipFiles(String filePath, String descDir)

	{

		ZipOutputStream zos = null;

		try

		{

			// 创建一个Zip输出流

			zos = new ZipOutputStream(new FileOutputStream(descDir));

			// 启动压缩

			startZip(zos, "", filePath);

			// System.out.println("******************压缩完毕********************");

		}

		catch (IOException e)

		{

			// 压缩失败，则删除创建的文件

			File zipFile = new File(descDir);

			if (zipFile.exists())

			{

				zipFile.delete();

			}

			// System.out.println("******************压缩失败********************");

			e.printStackTrace();

		}

		finally

		{

			try

			{

				if (zos != null)

				{

					zos.close();

				}

			}

			catch (IOException e)

			{

				e.printStackTrace();

			}

		}

	}

	    /**

	    *

	    * @description：对目录中所有文件递归遍历进行压缩

	    * @param zos

	    * ZIP压缩输出流

	    * @param oppositePath

	    * 在zip文件中的相对路径

	    * @param directory

	    * 要压缩的文件的路径

	    * @throws IOException

	    */

	    private static void startZip(ZipOutputStream zos, String oppositePath,

	    String directory) throws IOException

	{

		File file = new File(directory);

		if (file.isDirectory())

		{

			// 如果是压缩目录

			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++)

			{

				File aFile = files[i];

				if (aFile.isDirectory())

				{

					// 如果是目录，修改相对地址

					String newOppositePath = oppositePath + aFile.getName()
							+ "/";

					// 压缩目录，这是关键，创建一个目录的条目时，需要在目录名后面加多一个"/"

					ZipEntry entry = new ZipEntry(oppositePath
							+ aFile.getName() + "/");

					zos.putNextEntry(entry);

					zos.closeEntry();

					// 进行递归调用

					startZip(zos, newOppositePath, aFile.getPath());

				}

				else

				{

					// 如果不是目录，则进行压缩

					zipFile(zos, oppositePath, aFile);

				}

			}

		}

		else

		{

			// 如果是压缩文件，直接调用压缩方法进行压缩

			zipFile(zos, oppositePath, file);

		}

	}

	    /**

	    *

	    * @description：

	    压缩单个文件到目录中

	    * @param zos

	    * zip输出流

	    * @param oppositePath

	    * 在zip文件中的相对路径

	    * @param file

	    * 要压缩的的文件

	    */

	    private static void zipFile(ZipOutputStream zos, String oppositePath,File file)
	    {

	    // 创建一个Zip条目，每个Zip条目都是必须相对于根路径

	    InputStream is = null;

	    try

	    {

	    ZipEntry entry = new ZipEntry(oppositePath + file.getName());

	    // 将条目保存到Zip压缩文件当中

	    zos.putNextEntry(entry);

	    // 从文件输入流当中读取数据，并将数据写到输出流当中.

	    is = new FileInputStream(file);

	    int length = 0;

	    int bufferSize = 1024;

	    byte[] buffer = new byte[bufferSize];

	    while ((length = is.read(buffer, 0, bufferSize)) >= 0)

	    {

	    zos.write(buffer, 0, length);

	    }

	    zos.closeEntry();

	    }

	    catch (IOException ex)

	    {

	    ex.printStackTrace();

	    }

	    finally

	    {

	    try

	    {

	    if (is != null)

	    {

	    is.close();

	    }

	    }

	    catch (IOException ex)

	    {

	    ex.printStackTrace();

	    }

	    }

	    }

	    /*********************************** 华丽的分割线 ***********************************/

	    /**

	    *

	    * @description：解压文件操作

	    * @param zipFilePath

	    * zip文件路径

	    * @param descDir

	    * 解压出来的文件保存的目录

	    * @throws IOException

	    */

	    public static void unZipFiles(String zipFilePath, String descDir)

	    {

	    File zipFile = new File(zipFilePath);

	    File pathFile = new File(descDir);

	    if (!pathFile.exists())

	    {

	    pathFile.mkdirs();

	    }

	    ZipFile zip = null;

	    InputStream in = null;

	    OutputStream out = null;

	    try

	    {

	    zip = new ZipFile(zipFile);

	    for (Enumeration<? extends ZipEntry> entries = zip.getEntries(); entries.hasMoreElements();)

	    {

	    ZipEntry entry = entries.nextElement();

	    String zipEntryName = entry.getName();

	    in = zip.getInputStream(entry);

	    String outPath = (descDir + "/" + zipEntryName).replaceAll("\\*", "/");;

	    // 判断路径是否存在,不存在则创建文件路径

	    File file = new File(outPath.substring(0,outPath.lastIndexOf('/')));

	    if (!file.exists())

	    {

	    file.mkdirs();

	    }

	    // 判断文件全路径是否为文件夹,如果是上面已经创建,不需要解压

	    if (new File(outPath).isDirectory())

	    {

	    continue;

	    }

	    out = new FileOutputStream(outPath);

	    byte[] buf1 = new byte[4 * 1024];

	    int len;

	    while ((len = in.read(buf1)) > 0)

	    {

	    out.write(buf1, 0, len);

	    }

	    in.close();

	    out.close();

	    }

//	    	System.out.println("******************解压完毕********************");

	    }

	    catch (IOException e)

	    {

	    pathFile.delete();

//	    	System.out.println("******************解压失败********************");

	    e.printStackTrace();

	    }

	    finally

	    {

	    try

	    {

	    if (zip != null)

	    {

	    zip.close();

	    }

	    if (in != null)

	    {

	    in.close();

	    }

	    if (out != null)

	    {

	    out.close();

	    }

	    }

	    catch (IOException e)

	    {

	    e.printStackTrace();

	    }

	    }

	    }

	    
}
