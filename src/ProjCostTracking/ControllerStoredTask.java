/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author richardc
 */
public class ControllerStoredTask implements Initializable {
    @FXML
    private Button btnAddTask;
    @FXML
    private Button btnSaveTask;
    @FXML
    private Button btnDeleteTask;
    @FXML
    private Button btnAddCost;
    @FXML
    private Button btnSaveCost;
    @FXML
    private Button btnDeleteCost;
    
    private AnchorPane parent; //this is to be assigned from MainMenuController 
    final List<Field> tfList = new ArrayList<>(); //task
    final List<Field> cfList = new ArrayList<>(); //cost
    final ObservableList<EntityCost> costList = Main.db.getCostList();    
    EntityStoredProjTask selectTask = null;
    EntityStoredTaskCost selectCost = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAddTask_onClick(ActionEvent event) {
    }

    @FXML
    private void btnSaveTask_onClick(ActionEvent event) {
    }

    @FXML
    private void btnDeleteTask_onClick(ActionEvent event) {
    }

    @FXML
    private void btnAddCost_onClick(ActionEvent event) {
    }

    @FXML
    private void btnSaveCost_onClick(ActionEvent event) {
    }

    @FXML
    private void btnDeleteCost_onClick(ActionEvent event) {
    }
    
    public void setParent(AnchorPane parent){
        this.parent = parent;
    }
    
}
