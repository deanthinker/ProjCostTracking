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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;

/**
 * FXML Controller class
 *
 * @author richardc
 */
public class ProjectMainController implements Initializable {

    final List<Field> projFieldsList = new ArrayList<>();
    final ObservableList<EntityProject> projList = Main.db.getProjectList();
    
    @FXML
    private Button btnNewProj;
    @FXML
    private ComboBox<EntityProject> cbxProject;
    @FXML
    private TableView<?> tbvMain;
    @FXML
    private TreeView<?> treeTask;
    @FXML
    private Button btnAddNew;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnDel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadProjFields();
        
    }    
    private void loadProjFields(){
        for (Field f : EntityProject.class.getDeclaredFields()){ //create a list of "f_" fields
            f.setAccessible(true); //make "private" member visible
            //we only handle those that name with "fd...."
            if (f.getName().substring(0, 2).equals("fd")){
                projFieldsList.add(f);
            }
        }

        System.out.println("Found "+projFieldsList.size() + " DB fields.");
        if (projFieldsList.isEmpty()) {System.out.print("Entity has zero field!"); return;}
    }
    
    @FXML
    private void btnNewProj_onClick(ActionEvent event) {
    }

    @FXML
    private void cbxProject_onClick(ActionEvent event) {
    }

    @FXML
    private void btnAddNew_onClick(ActionEvent event) {
    }

    @FXML
    private void btnClose_onClick(ActionEvent event) {
    }

    @FXML
    private void btnDel_onClick(ActionEvent event) {
    }
    
}
