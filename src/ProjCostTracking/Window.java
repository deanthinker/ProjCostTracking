/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author richardc
 */
public class Window {
    private String name;
    private String fxml_src;
    private FXMLLoader loader;
    private Parent parent;
    private Scene scene;
    private Stage stage = new Stage();
    private int modal = 0 ;
    private int transparent = 0;
    
    public Window(String name, String fxml_src) {
        this.name = name;
        this.fxml_src = fxml_src;
    }
    
    public Window(String name, String fxml_src, int modal, int transparent) {
        this.name = name;
        this.fxml_src = fxml_src;
        this.modal = modal;
        this.transparent = transparent;
    }
    
    public void loadWindow() {
        loader  = new FXMLLoader(getClass().getResource(fxml_src));
        
        try {
            parent = (Parent)loader.load();
        } catch (IOException ex) { Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex); }
        
        this.scene = new Scene(parent);
        stage.setScene(scene);
        
        if (modal == 1) setModal();
        if (transparent == 1) setTransparent();
        
    }
    
    public void setTransparent(){
         this.stage.initStyle(StageStyle.TRANSPARENT);
    }
    
    public void setModal(){
        this.stage.initModality(Modality.APPLICATION_MODAL);
    }
    
    public void showAndWait(){
        loadWindow();
        this.stage.showAndWait();
    }
    public void show(){
        loadWindow();
        this.stage.show();
    }
    
    public void close(){
        this.stage.close();
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
    
    public Scene getScene(){
        return scene;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFxml_src(String fxml_src) {
        this.fxml_src = fxml_src;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public String getFxml_src() {
        return fxml_src;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public Parent getParent() {
        return parent;
    }
    
    
}
