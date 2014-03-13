/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.stage.Stage;

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

        //translate 
    static final Map<String, String> tr = new HashMap<>();
    static {
        tr.put("userlevelid", "ID");
        tr.put("fdrlevel", "使用者層級值");
        tr.put("fdrlevelname", "層級名稱(0~99)");
        tr.put("fdnote", "備註");
    }
    
    
    //create a window class that holds loader, parent and controller
    public static HashMap<String, Window> winCollection = new HashMap<>();

    public static String getEntityMethodName(String s) {
        return ("set" + s.substring(0, 1).toUpperCase() + s.substring(1));
    }

    public static void test(){

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

        //System.exit(0);
        loadWindows();
        winCollection.get(SCREEN_MAINMENU).show();
       
       
        

    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
