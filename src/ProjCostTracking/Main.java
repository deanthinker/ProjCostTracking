/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.control.DatePicker;
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
        tr.put("fdnote", "備註");
        //entityUserlevel
        tr.put("userlevelid", "ID");
        tr.put("fdrlevel", "使用者層級值(0~99)");
        tr.put("fdrlevelname", "層級名稱");
        //entityEmployee
        tr.put("empid", "ID");
        tr.put("fdrname", "姓名");
        tr.put("fdlastname", "姓");
        tr.put("fdbadgeid", "工號");
        tr.put("fdtitle", "職稱");
        tr.put("fdgender", "性別");
        tr.put("fdbirthday", "生日(月/日/年");
        //entityUser
        tr.put("userid", "ID");
        tr.put("fdrusername", "使用者名稱");
        tr.put("fdrpassword", "密碼");
        tr.put("fdruserlevelid", "使用者層級");
        tr.put("fdempid", "員工");
        tr.put("fdrlog", "紀錄");
        
    }
    public static Integer getTextFieldInteger(String s){
        if (s.isEmpty())
            return 0;
        else
            return Integer.valueOf(s);
    }
    
    public static Date getDatePickerDate(DatePicker dp){
        Instant instant = dp.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);        
    }
    
    //create a window class that holds loader, parent and controller
    public static HashMap<String, Window> winCollection = new HashMap<>();

    public static String field2methodName(String fieldName) {
        //fieldName:  fdrname --> setFdrname
        return ("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
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
        winCollection.get(SCREEN_LOGIN).show();
        //winCollection.get(SCREEN_MAINMENU).show();
 
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static void log(int logtype, String tablename, String note){
        EntityLog el = new EntityLog(logtype, Main.currentUser.getUserid(), tablename, note);
        if(!Main.db.em.getTransaction().isActive())
            Main.db.em.getTransaction().begin();

        Main.db.em.persist(el);
        Main.db.em.getTransaction().commit();
    }
}
