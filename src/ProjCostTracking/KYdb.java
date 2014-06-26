/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public static EntityManager tmpem = null;
    
    public KYdb(String factoryName){
        //emf = Persistence.createEntityManagerFactory(factoryName);
        emf = getEntityManagerFactory();
        em = emf.createEntityManager();
        tmpem = emf.createEntityManager();
    }
    
    private EntityManagerFactory getEntityManagerFactory(){
        EntityManagerFactory f = null;
        
        Map properties = new HashMap();

        //!!!!! the following section is removed from persistence.xml  !!!
        /*
    <properties>
      <property name="eclipselink.logging.level" value="FINE"/>
      <property name="eclipselink.logging.level.sql" value="FINE"/>
      <property name="eclipselink.logging.parameters" value="true"/>
      <property name="eclipselink.logging.timestamp" value="true"/>
      <property name="eclipselink.logging.exceptions" value="true"/>
      <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/biotechcost?zeroDateTimeBehavior=convertToNull"/>
      <property name="javax.persistence.jdbc.password" value="1234"/>
      <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
      <property name="javax.persistence.jdbc.user" value="root"/>
    </properties>        
 */   
/*
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://23.229.154.169:3306/biotechcost?zeroDateTimeBehavior=convertToNull");
        properties.put("javax.persistence.jdbc.password", "Gg9223701");
        properties.put("javax.persistence.jdbc.user", "happy2");
 */       
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/biotechcost?zeroDateTimeBehavior=convertToNull");
        properties.put("javax.persistence.jdbc.password", "1234");
        properties.put("javax.persistence.jdbc.user", "root");
        
        //need to enable the following to use Config file feature
        //properties.put("javax.persistence.jdbc.url", "jdbc:mysql://"+Main.conf.getDb_ip()+":"+Main.conf.getDb_port() +"/biotechcost?zeroDateTimeBehavior=convertToNull");
        //properties.put("javax.persistence.jdbc.password", Main.conf.getDb_admin_password());
        //properties.put("javax.persistence.jdbc.user", Main.conf.getDb_admin_username());

        properties.put("eclipselink.logging.level", "FINE");
        properties.put("eclipselink.logging.level.sql", "FINE");
        properties.put("eclipselink.logging.parameters", "true");
        properties.put("eclipselink.logging.timestamp", "true");
        properties.put("eclipselink.logging.exceptions", "true");
        properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
       
        f = Persistence.createEntityManagerFactory("ProjCostTrackingPU",properties);
        
        return f;
        
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
    public ObservableList<EntityProject> getProjectList() {
        List<EntityProject> thelist = Main.db.tmpem.createQuery("select e from EntityProject e").getResultList();
        ObservableList<EntityProject> obslist = FXCollections.observableList(thelist);
        tmpem.clear();
        return obslist;
    }

    public ObservableList<EntityDepartment> getDepartmentList() {
        List<EntityDepartment> thelist = Main.db.tmpem.createQuery("select e from EntityDepartment e").getResultList();
        ObservableList<EntityDepartment> obslist = FXCollections.observableList(thelist);
        tmpem.clear();
        return obslist;
    }      

    public ObservableList<EntityProjecttype> getProjecttypeList() {
        List<EntityProjecttype> thelist = Main.db.tmpem.createQuery("select e from EntityProjecttype e").getResultList();
        ObservableList<EntityProjecttype> obslist = FXCollections.observableList(thelist);
        tmpem.clear();
        return obslist;
    }
    public ObservableList<EntityCost> getCostList() {
        List<EntityCost> thelist = Main.db.tmpem.createQuery("select e from EntityCost e").getResultList();
        ObservableList<EntityCost> obslist = FXCollections.observableList(thelist);
        tmpem.clear();
        return obslist;
    }
    public ObservableList<EntityStoredProjTask> getStoredTaskList() {
        List<EntityStoredProjTask> thelist = Main.db.tmpem.createQuery("select e from EntityStoredProjTask e").getResultList();
        ObservableList<EntityStoredProjTask> obslist = FXCollections.observableList(thelist);
        tmpem.clear();
        return obslist;
    }
    public EntityProjTask getProjTask(String id) {
        EntityProjTask task = (EntityProjTask)Main.db.tmpem.createQuery("select e from EntityProjTask e where e.projtaskid = " + id ).getSingleResult();
        
        tmpem.clear();
        return task;
    }    
    public ObservableList<EntityCosttype> getCosttypeList() {
        List<EntityCosttype> thelist = Main.db.tmpem.createQuery("select e from EntityCosttype e").getResultList();
        ObservableList<EntityCosttype> obslist = FXCollections.observableList(thelist);
        tmpem.clear();
        return obslist;
    }
    
    public ObservableList<EntityUserlevel> getUserlevelList() {
        List<EntityUserlevel> thelist = Main.db.tmpem.createQuery("select e from EntityUserlevel e").getResultList();
        ObservableList<EntityUserlevel> obslist = FXCollections.observableList(thelist);
        tmpem.clear();
        return obslist;
    }

    public ObservableList<EntityEmployee> getEmployeeList() {
        List<EntityEmployee> thelist = Main.db.tmpem.createQuery("select e from EntityEmployee e").getResultList();
        ObservableList<EntityEmployee> obslist = FXCollections.observableList(thelist);
        tmpem.clear();
        return obslist;
    }
    
    public void commit(){
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        
        em.getTransaction().commit();

        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
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
