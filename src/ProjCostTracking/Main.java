/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main extends Application {
    public static final String PACKAGE_NAME = "ProjCostTracking";
    public static final String ENTITY_MANAGER_FACTORY_NAME = "ProjCostTrackingPU"; // for DB initiation
    public static Stage mainStage = null;
    public static CurrentUser currentUser = new CurrentUser(); 
    public static final String SCREEN_MAINMENU = "Main"; 
    public static final String SCREEN_MAINMENU_FXML = "MainMenu.fxml";
    public static final String SCREEN_LOGIN = "Login"; 
    public static final String SCREEN_LOGIN_FXML = "Login.fxml";
    public static final String SCREEN_HELP = "Help"; 
    public static final String SCREEN_HELP_FXML = "Help.fxml";
    public static final String SCREEN_DBEDIT = "DBedit";
    public static final String SCREEN_DBEDIT_FXML = "DBedit.fxml";
    public static final int MODAL = 1;
    public static final int TRANSPARENT = 1;
    
    public static KYdb db = new KYdb(ENTITY_MANAGER_FACTORY_NAME);

    //create a window class that holds loader, parent and controller
    public static HashMap<String, Window> winCollection = new HashMap<>();
    
    public static void test(){
        System.out.println("hello!");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjCostTrackingPU");
        EntityManager em = emf.createEntityManager();
        //EntityManager em2 = emf.createEntityManager();
        
        em.getTransaction().begin();
        //em2.getTransaction().begin();
        

        
        EntityUserlevel ul = new EntityUserlevel((Integer) 4);
        ul.setLevelname("ttttttl4");
        /*
        em.persist(ul);
        System.out.println("before commit");
        try{
        em.getTransaction().commit();
        }
        catch (javax.persistence.PersistenceException e){
            System.out.printf("error commit~~");
        }System.out.println("after commit");
        */
        
        /*
        System.out.println("ul name:"+ul.getLevelname());
        
        EntityUser u = new EntityUser();
        u.setUsername("happy6");
        u.setPassword("55556");
        u.setUserlevelid(ul);
        u.setCreation(Calendar.getInstance().getTime());
        em.persist(u);
        
        System.out.println("before commit2");
        try{
            em.getTransaction().commit();
        }
        catch (javax.persistence.PersistenceException e){
            System.out.printf("error commit~~2");
        }
        System.out.println("after commit2");  
        */
        
        EntityUser u2 = em.find(EntityUser.class, 77);
        
        if (u2 != null)
            System.out.println("Userlevel:" + u2.getUserlevelid().getLevelname());
        else
            System.out.println("Userlevel:not found");
        
        List<EntityUser> ulist = em.createQuery("SELECT u from EntityUser u").getResultList();
        ObservableList<EntityUser> uolist = FXCollections.observableList(ulist);
        for (EntityUser e : uolist){
            System.out.println(e.toString());
        }
                
        em.close();
        emf.close();
    }
    
    private void debug(String msg){
        System.out.println(msg);
    }
    
    public void loadWindows(){
        winCollection.put(SCREEN_HELP, new Window(SCREEN_HELP, SCREEN_HELP_FXML));
        winCollection.put(SCREEN_DBEDIT, new Window(SCREEN_DBEDIT, SCREEN_DBEDIT_FXML));
        winCollection.put(SCREEN_LOGIN, new Window(SCREEN_LOGIN, SCREEN_LOGIN_FXML, MODAL, TRANSPARENT));
        winCollection.put(SCREEN_MAINMENU, new Window(SCREEN_MAINMENU, SCREEN_MAINMENU_FXML)); //this goes last
        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        loadWindows();
        winCollection.get(SCREEN_MAINMENU).show();
       
        //winCollection.get(SCREEN_LOGIN).showAndWait();
        

    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
