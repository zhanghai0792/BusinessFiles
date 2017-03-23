package com.hy.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownLoadImg {

	public static String getHtmlResourceByURL(String url,String encoding){
		StringBuffer sb=new StringBuffer();
		InputStreamReader sr=null;
		try {
			URL ur=new URL(url);
			URLConnection uc=ur.openConnection();
			 sr=new InputStreamReader(uc.getInputStream(),encoding);
			BufferedReader buffer=new BufferedReader(sr);
			String line=null;
			while((line=buffer.readLine())!=null){
				sb.append(line+"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(sr!=null){
				try {
					sr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
		
		
	}
	/**
	 *@Description:
	 */
	public static void main(String[] args) {
		
		String res=getHtmlResourceByURL("http://www.nipic.com/index.html","utf-8");
		System.out.println(res);
		Document d=Jsoup.parse(res);
		Elements es=d.getElementsByTag("img");
		for(Element e: es){
			String imgsrc=e.attr("src");
			//&&imgsrc.startsWith("http://")
			if(!"".equals(imgsrc)){
				String path="C:\\img";
				DownloadImages(path,imgsrc);
				System.out.println(imgsrc);
			}
		}
		
		
	}
	@SuppressWarnings("unused")
	private static void DownloadImages(String path, String imgsrc) {
		if(!imgsrc.contains("http")){
			imgsrc="http:"+imgsrc;
		}
		String name=imgsrc.substring(imgsrc.lastIndexOf("/"));
		File f=new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		try {
			System.out.println(imgsrc);
			URL url=new URL(imgsrc);
			HttpURLConnection hc=(HttpURLConnection) url.openConnection();
			InputStream is=hc.getInputStream();
			File fs=new File(path+name);
			FileOutputStream out=new FileOutputStream(fs);
			int i=0;
			while((i=is.read())!=-1){
				out.write(i);
			}
			is.close();
			out.close();
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
