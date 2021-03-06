
package ProjCostTracking;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author richardc
 */
public class ControllerMainMenu implements Initializable {

    @FXML
    private AnchorPane mainContent;
    
    @FXML
    private Label lblMessage;

    @FXML
    private MenuBar menu;

    @FXML
    private MenuItem mnuAbout;

    @FXML
    private MenuItem mnuCost;

    @FXML
    private MenuItem mnuCostType;

    @FXML
    private MenuItem mnuDBconn;

    @FXML
    private MenuItem mnuEmployee;

    @FXML
    private MenuItem mnuExit;

    @FXML
    private MenuItem mnuLogout;

    @FXML
    private MenuItem mnuProjCenter;

    @FXML
    private MenuItem mnuNewProject;
    
    @FXML
    private MenuItem mnuStoredTask;    
    
    @FXML
    private MenuItem mnuProjMember;

    @FXML
    private MenuItem mnuPreferences;

    @FXML
    private MenuItem mnuProjectType;

    @FXML
    private MenuItem mnuTask;

    @FXML
    private MenuItem mnuUser;

    @FXML
    private MenuItem mnuUserLevel;

    @FXML
    private MenuItem mnuDept;
    
    private Stage stage;
    
    StringProperty mainUsername = new SimpleStringProperty();
    
    private void test(){
       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Main.currentUser.namebind.addListener(new ChangeListener(){
        @Override public void changed(ObservableValue o,Object oldVal,Object newVal){
            if (Main.currentUser.namebind.getValue().length() > 0)
                lblMessage.setText("Welcome " + Main.currentUser.namebind.getValue());
            else
                lblMessage.setText("");
        }
      });
    }    

    @FXML
    private void mnuExit_onClick(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void mnuLogout_onClick(ActionEvent ecurrentUser) {
        Main.currentUser.logout();
        Main.winCollection.get(Main.SCREEN_LOGIN).showAndWait();
    }

    @FXML
    private void mnuNewProject_onClick(ActionEvent event) {
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DBedit.fxml"));
        DBeditEntityProject controller = new DBeditEntityProject();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);        
    }
    @FXML
    private void mnuStoredTask_onClick(ActionEvent event) {
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StoredTask.fxml"));
        ControllerStoredTask controller = new ControllerStoredTask();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);    
    }    
    
    @FXML
    private void mnuProjCenter_onClick(ActionEvent event) {
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectCenter.fxml"));
        ControllerProjectCenter controller = new ControllerProjectCenter();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);
        
    }

    @FXML
    private void mnuUser_onClick(ActionEvent event) {
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DBedit.fxml"));
        DBeditEntityUser controller = new DBeditEntityUser();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);
    }

    @FXML
    private void mnuEmployee_onClick(ActionEvent event) {
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DBedit.fxml"));
        DBeditEntityEmployee controller = new DBeditEntityEmployee();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);
    }

    @FXML
    private void mnuCost_onClick(ActionEvent event) {
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DBedit.fxml"));
        DBeditEntityCost controller = new DBeditEntityCost();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);     
    }

    @FXML
    private void mnuCostType_onClick(ActionEvent event) {
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DBedit.fxml"));
        DBeditEntityCosttype controller = new DBeditEntityCosttype();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);        
    }

    @FXML
    private void mnuProjectType_onClick(ActionEvent event) {
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DBedit.fxml"));
        DBeditEntityProjecttype controller = new DBeditEntityProjecttype();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);        
    }
    @FXML
    private void mnuProjMember_onClick(ActionEvent event) {
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectMemberPane.fxml"));
        ControllerPaneMember controller = new ControllerPaneMember();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);             
        
    }
    
    @FXML
    private void mnuUserLevel_onClick(ActionEvent event) {
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DBedit.fxml"));
        DBeditEntityUserlevel controller = new DBeditEntityUserlevel();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);
    }

    private void fitToParent(FXMLLoader loader){
        try {
            //expand the Child pane to the size of the parent
               Node n = (Node)loader.load();
               AnchorPane.setTopAnchor(n, 0.0);
               AnchorPane.setRightAnchor(n, 0.0);
               AnchorPane.setLeftAnchor(n, 0.0);
               AnchorPane.setBottomAnchor(n, 0.0);
               mainContent.getChildren().setAll(n);
        } catch (IOException e){
               System.out.println(e.getMessage());
        }        
    }
    @FXML
    private void mnuPreferences_onClick(ActionEvent event) {
        //need to enable the following to use Config file feature
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("preference.fxml"));
        ControllerPreference controller = new ControllerPreference();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);        
        
    }

    @FXML
    private void mnuDBconn_onClick(ActionEvent event) {
    }

    @FXML
    private void mnuAbout_onClick(ActionEvent event) {
        Main.winCollection.get(Main.SCREEN_HELP).show();
    }
    
    private void setStage(Stage s){
        this.stage = s;
    }

    @FXML
    private void mnuDept_onClick(ActionEvent event) {
         mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DBedit.fxml"));
        DBeditEntityDepartment controller = new DBeditEntityDepartment();

        loader.setController(controller);
        controller.setParent(mainContent);
        fitToParent(loader);    
    }
    
}
