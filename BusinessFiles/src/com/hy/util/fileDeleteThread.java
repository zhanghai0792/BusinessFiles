package com.hy.util;

import java.io.File;

public class fileDeleteThread extends Thread{
 private File deleteFile;
 private long time=1000*90;//1分钟
public File getDeleteFile() {
	return deleteFile;
}
public void setDeleteFile(File deleteFile) {
	this.deleteFile = deleteFile;
}
public long getTime() {
	return time;
}
public void setTime(long time) {
	this.time = time;
}
public fileDeleteThread() {
	super();
	// TODO Auto-generated constructor stub
}
public fileDeleteThread(File deleteFile) {
	super();
	this.deleteFile = deleteFile;
}

 public fileDeleteThread(File deleteFile, long time) {
	super();
	this.deleteFile = deleteFile;
	this.time = this.time*5+time*10000;
}
public void run(){
	 try {
		this.sleep(time);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 if(deleteFile!=null&&deleteFile.exists()){
		 System.out.println("删除 "+deleteFile.getAbsolutePath());
		 deleteFile.delete();
		 }
 }
 public static void delete(File file){
	 new fileDeleteThread(file).start();
 }
 public static void delete(File file,long time){
	 new fileDeleteThread(file,time).start();
 }
}
