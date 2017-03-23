package com.hy.servletListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class logListener
 *
 */
public class logListener implements ServletContextListener {
	 public static final String log4jdirkey = "log4jdir"; 
    public logListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	System.getProperties().remove(log4jdirkey);  
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent servletcontextevent)  { 
    	String log4jdir = servletcontextevent.getServletContext().getRealPath("/");  
        //System.out.println("log4jdir:"+log4jdir);  
        System.setProperty(log4jdirkey, log4jdir); 
    }
	
}
