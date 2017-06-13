package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class testProperty {
	
	private String port;

    private String host;
    
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
	
    public testProperty(){
        Properties pro = new Properties();
        InputStream in = null;
        try {
            //String path = getClass().getClassLoader().getResource("system" + File.separator + "service"  + File.separator +  "mongodb.properties").toString();
            in = testProperty.class.getResourceAsStream("/config/mongodb.properties");;
            pro.load(in);
            in.close();
            String port=pro.getProperty("mongodb.port");
            String host=pro.getProperty("mongodb.host");
            setHost(port);
            setPort(host);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public static void main(String[] args) {
		new testProperty();
	}
}
