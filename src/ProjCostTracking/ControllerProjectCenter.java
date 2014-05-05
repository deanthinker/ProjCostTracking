/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javax.persistence.TypedQuery;

/**
 * FXML Controller class
 *
 * @author richardc
 */
public class ControllerProjectCenter implements Initializable {
    @FXML
    private AnchorPane splTop;
    @FXML
    private AnchorPane splDown;
    @FXML
    private Tab tabMember;
    @FXML
    private Tab tabTaskCost;
    @FXML
    private Button btnClose;
    @FXML
    private AnchorPane paneMember;
    @FXML
    private AnchorPane paneTaskCost;

    
    private AnchorPane parent; //this is to be assigned from MainMenuController 
    private EntityProject proj = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXMLLoader fxmlloader;
        //add Member Tab pane
        paneMember.getChildren().clear();
        fxmlloader = new FXMLLoader(getClass().getResource("PaneProjectMember.fxml"));
  
        ControllerPaneMember ctrlMember = new ControllerPaneMember();
                
        fxmlloader.setController(ctrlMember);
        ctrlMember.setParent(paneMember);
        fitToParent(fxmlloader, paneMember);

        //add Member Tab pane
        paneTaskCost.getChildren().clear();
        fxmlloader = new FXMLLoader(getClass().getResource("PaneProjectTaskCost.fxml"));
  
        ControllerPaneProjectTaskCost ctrlTaskCost = new ControllerPaneProjectTaskCost();
                
        fxmlloader.setController(ctrlTaskCost);
        ctrlMember.setParent(paneTaskCost);
        fitToParent(fxmlloader, paneTaskCost);
                

        //add top pane
        splTop.getChildren().clear();
        FXMLLoader toploader = new FXMLLoader(getClass().getResource("PaneProjectSearch.fxml"));
  
        ControllerPaneProjectSearch ctrlpsearch = new ControllerPaneProjectSearch(){
            public void updateSelect(EntityProject p){
                proj = p;
                ctrlMember.reloadProj(proj);
                ctrlTaskCost.reloadProj(proj);
                System.out.println("Select PID:" + p.getProjectid());
            }
        };
        
        toploader.setController(ctrlpsearch);
        ctrlpsearch.setParent(splTop);
        fitToParent(toploader, splTop);
        
        
        
        
    }    
    @FXML
    private void tabMember_Clicked(Event event) {
    }

    @FXML
    private void tabTaskCost_Clicked(Event event) {
    }

    @FXML
    private void btnClose_onClick(ActionEvent event) {
        if(Main.db.em.getTransaction().isActive()){
            Main.db.em.clear();
        }
        
        parent.getChildren().clear();
    }
    
    public void setParent(AnchorPane parent){
        this.parent = parent;
    }  
    private void fitToParent(FXMLLoader loader, AnchorPane anch){
        try {
            //expand the Child pane to the size of the parent
               Node n = (Node)loader.load();
               AnchorPane.setTopAnchor(n, 0.0);
               AnchorPane.setRightAnchor(n, 0.0);
               AnchorPane.setLeftAnchor(n, 0.0);
               AnchorPane.setBottomAnchor(n, 0.0);
               anch.getChildren().setAll(n);
        } catch (IOException e){
               System.out.println(e.getMessage());
        }        
    }     
}
