/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author richardc
 */
public class CurrentUser {
    private Integer userid;
    private String username, password;

    private int empid, level;
    private boolean log;
    private final Calendar login_time = Calendar.getInstance();
    public SimpleStringProperty namebind = new SimpleStringProperty(""); // this variable MUST be updated with username, so that changes are upaded for the binding listener
   
    public CurrentUser(){ //for Main.user for initialization
        setUser(0,"","",0,0, true);
    }
    public CurrentUser(Integer userid, String username, String password, Integer empid, int level, boolean log) {
        setUser( userid,  username,  password,  empid,  level,  log);
    }
        
    public void logout(){
        setUser(0,"","",0,0,true);
    }

    public void setUser(Integer userid, String username, String password, Integer empid, int level, boolean log) {
        setUserid(userid);
        setUsername(username);
        setPassword(password);
        setEmpid(empid);
        setLevel(level);
        setLog(log);
        
        this.namebind.setValue(username);//set value to notify the change
    }
    
    public void copyEntity(EntityUser in){
        setUser(in.getUserid(),
                in.getFdrusername(),
                in.getFdrpassword(),
                in.getFdempid().getEmpid(),
                in.getFdruserlevelid().getUserlevelid(),
                in.getFdrlog());
    }
    
    @Override
    public String toString() {
        return "User{" + "userid=" + userid + ", username=" + username + ", password=" + password + ", empid=" + empid + ", level=" + level + ", log=" + log + ", login_time=" + login_time + '}';
    }


    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLog(boolean log) {
        this.log = log;
    }

    public void setNamebind(SimpleStringProperty namebind) {
        this.namebind = namebind;
    }    
    
    public Integer getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getEmpid() {
        return empid;
    }

    public int getLevel() {
        return level;
    }
    public boolean getLog() {
        return log;
    }
    
    Calendar getLogin_time() {
        return login_time;
    }
    
    public void setUsername(String username){
        this.username = username;
        this.namebind.setValue(username); //set value to notify the change
    }
       
    
}
