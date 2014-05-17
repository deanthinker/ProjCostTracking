/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author richardc
 */
public class ControllerPreference implements Initializable {
    @FXML
    private TextField txfDBip;
    @FXML
    private TextField txfDBport;
    @FXML
    private TextField txfDBusername;
    @FXML
    private PasswordField txfDBpassword;
    @FXML
    private Button btnSave;
    @FXML
    private Font x1;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnTestConn;
    private AnchorPane parent; //this is to be assigned from MainMenuController 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadConfigIntoField();
                
    }    
    public void setParent(AnchorPane parent){
        this.parent = parent;
    }
    private void loadConfigIntoField(){
        /*
        txfDBip.setText(Main.conf.getDb_ip());
        txfDBport.setText(Main.conf.getDb_port());
        txfDBusername.setText(Main.conf.getDb_admin_username());
        txfDBpassword.setText(Main.conf.getDb_admin_password());
                */
    }
    
    private boolean connectionOK(){
        boolean pass = false;
        String ip = txfDBip.getText();
        String port = txfDBport.getText();
        String username =  txfDBusername.getText();
        String password =  txfDBpassword.getText();
        
        EntityManagerFactory f = null;
        EntityManager e = null;
        
        Map properties = new HashMap();

        properties.put("eclipselink.logging.level", "FINE");
        properties.put("eclipselink.logging.level.sql", "FINE");
        properties.put("eclipselink.logging.parameters", "true");
        properties.put("eclipselink.logging.timestamp", "true");
        properties.put("eclipselink.logging.exceptions", "true");
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://"+ ip +":"+ port +"/biotechcost?zeroDateTimeBehavior=convertToNull");
        properties.put("javax.persistence.jdbc.password", password);
        properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
        properties.put("javax.persistence.jdbc.user", username);
        
        try{
            f = Persistence.createEntityManagerFactory("ProjCostTrackingPU",properties);       
            e = f.createEntityManager(); //actual TEST is here
            e.close();
            f.close();
            pass = true;
        }
         catch (Exception ex ) {
             return false;
         }
        
        return pass;
    }
    
    @FXML
    private void btnSave_onClick(ActionEvent event) {

    }

    @FXML
    private void btnClose_onClick(ActionEvent event) {
        if(Main.db.em.getTransaction().isActive()){
            Main.db.em.clear();
        }
        
        parent.getChildren().clear();        
    }

    @FXML
    private void btnTestConn_onClick(ActionEvent event) {
        if (connectionOK()){
            Dialogs.create()
                .title("Connection OK")
                .masthead("Connection OK")
                .message("Connection to Datebase: Succeed!")
                .showInformation();
            System.out.println("Connection to Datebase: Succeed!");
        }else{
            Dialogs.create()
                .title("Connection Fail")
                .masthead("Connection Fail")
                .message("Connection to Datebase: Failed!")
                .showError();     
            System.out.println("Connection to Database: Failed!");
        }
            
    }
    
}
