/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import static ProjCostTracking.Main.CONFIG_FILENAME;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author richardc
 */
public class Config {
    private String db_ip;
    private String db_port;
    private String db_admin_username;
    private String db_admin_password;
    private String config_file_name;
    private boolean configOK = false;
    
    public Config(String name){
        config_file_name = name;
        if(!configFileExist()){  
            createDefaultConfig();   
        }
        
        configOK = readConfig();
    }
    public boolean isConfigOK(){
        return configOK;
    }
    public boolean configFileExist(){
        File f = new File(CONFIG_FILENAME);
        if(f.exists() && !f.isDirectory()) { 
            System.out.println("File " + CONFIG_FILENAME + " exist. OK");
            return true;
        }
        System.out.println("Can not find file " + CONFIG_FILENAME + "! FAIL");
        return false;
    }
    
    public boolean readConfig(){
        Properties prop = new Properties();
        InputStream input = null;
        //read config file
        try {
            input = new FileInputStream(CONFIG_FILENAME);
            prop.load(input);

            // get the property value and print it out
            setDb_ip(prop.getProperty("db_ip"));
            setDb_port(prop.getProperty("db_port"));
            setDb_admin_username(prop.getProperty("db_admin_username"));
            setDb_admin_password(prop.getProperty("db_admin_password"));
            
            System.out.println("Config read OK.");
            return conf_parameter_ok();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                    try {  input.close(); } catch (IOException e) {  e.printStackTrace(); }
            }
        }
        
        return false;
    }
    
    public void createDefaultConfig(){
    }
    
    public void createDefaultConfig_(){
	Properties prop = new Properties();
	OutputStream output = null;
        InputStream input = null;
        
	try {
            output = new FileOutputStream(CONFIG_FILENAME);
            // set the properties value
            prop.setProperty("db_ip", "localhost");
            prop.setProperty("db_port", "3306");
            prop.setProperty("db_admin_username", "root");
            prop.setProperty("db_admin_password", "1234");
            
            // save properties to project root folder
            prop.store(output, null);
            System.out.println("Config file " + CONFIG_FILENAME + " is now created!");
 
	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (output != null) {
			try {
                            output.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
 
	}
    }

    public void saveConfig(){
	Properties prop = new Properties();
	OutputStream output = null;
        InputStream input = null;
        
	try {
            output = new FileOutputStream(CONFIG_FILENAME);
            // set the properties value
            prop.setProperty("db_ip", db_ip);
            prop.setProperty("db_port", db_port);
            prop.setProperty("db_admin_username", db_admin_username);
            prop.setProperty("db_admin_password", db_admin_password);
            
            // save properties to project root folder
            prop.store(output, null);
            System.out.println("Config file " + CONFIG_FILENAME + " saved");
 
	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (output != null) {
			try {
                            output.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
 
	}
    }
    
    public String getConfig_file_name() {
        return config_file_name;
    }

    public void setConfig_file_name(String config_file_name) {
        this.config_file_name = config_file_name;
    }
    
    public String getDb_ip() {
        return db_ip;
    }

    public void setDb_ip(String db_ip) {
        this.db_ip = db_ip;
    }

    public String getDb_port() {
        return db_port;
    }

    public void setDb_port(String db_port) {
        this.db_port = db_port;
    }

    public String getDb_admin_username() {
        return db_admin_username;
    }

    public void setDb_admin_username(String db_admin_username) {
        this.db_admin_username = db_admin_username;
    }

    public String getDb_admin_password() {
        return db_admin_password;
    }

    public void setDb_admin_password(String db_admin_password) {
        this.db_admin_password = db_admin_password;
    }

    public boolean conf_parameter_ok(){
        if (getDb_ip()==null ){
            System.out.println("db_ip is not found in the config " + Main.CONFIG_FILENAME);
            return false;
        }
        else if (getDb_port()==null){
            System.out.println("db_ip is not found in the config " + Main.CONFIG_FILENAME);
            return false;
        }
        else if (getDb_admin_username()==null){
            System.out.println("db_admin_username is not found in the config " + Main.CONFIG_FILENAME);
            return false;
        }
        else if (getDb_admin_password()==null ){
            System.out.println("db_admin_password is not found in the config " + Main.CONFIG_FILENAME);
            return false;
        }
        return true;
    }    
    
}
