/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author richardc
 */
public class KYdb {
    Util u = new Util();
    
    public static EntityManagerFactory emf = null;
    public static EntityManager em = null;
    
    public KYdb(String factoryName){
        emf = Persistence.createEntityManagerFactory(factoryName);
        em = emf.createEntityManager();
    }
    
    public EntityManager getEntityManager(){
        return this.em;
    }    
        
    public boolean setLoginUser(String username, String password){
        List<EntityUser> ul = em.createQuery("select u from EntityUser u where u.fdrusername = '" + username + "' and  u.fdrpassword = '" + password +"' ").getResultList();
        if (ul != null){
            for (EntityUser user : ul){
                System.out.println("username: " + user.getFdrusername());
                Main.currentUser.copyEntity(user); //copy the vlalue; not the Instance
                return true;
            }
        }
        return false;
    }    
    
    //the following commented out code uses traditional JDBC connection ; it is now replaced by JPA (Java Persistence API)
    /* 
    private static Connection conn = null;
    public void setupConnection(){
        if (conn != null) return; // if initialized then dont' init again

            String driver = "com.mysql.jdbc.Driver";
            String serverip = "localhost";
            String dbTable = "biotechcost";
            String username = "root";
            String password = "1234";

            String connURL = "jdbc:mysql://" + serverip + "/" + dbTable
                            + "?useUnicode=true&characterEncoding=utf-8";
            u.debug("loading database");
            u.debug(connURL);
            u.debug("user:" + username + "  password:" + password);

            try {
                    Class.forName(driver); // 註冊driver
                    this.conn = DriverManager.getConnection(connURL, username, password);
                    u.debug("database connected!");

            } catch (ClassNotFoundException e) {
                    System.out.println("DriverClassNotFound :" + e.toString());
            }// 有可能會產生sqlexception
            catch (SQLException x) {
                    System.out.println("Exception :" + x.toString());
            }

    }
    
    public Connection getMySQLcon(){
        if (conn != null)
            return conn;
        else{
            u.debug("Connection is not initialized yet!");
            return null;
        }
    }

    
    public void setUser_old(String username, String password){
        Statement stat = null;
        ResultSet rs = null;
        String sql = "Select * from biotechcost.user where username ='" + username + "' and password = '"+ password +"' ";
        u.debug(sql);

        try {
                stat = conn.createStatement();
                rs = stat.executeQuery(sql);
                if (rs.next()) { 
                    Main.currentUser.setUser(rs.getString("userid"), rs.getString("username"), rs.getString("password"), rs.getString("empid"), rs.getInt("userlevelid"), rs.getInt("log"));
                }
                
        }catch (SQLException e) {
                u.debug("setUser Exception :" + e.toString());
        }
    }
    */
    
    

    
}
