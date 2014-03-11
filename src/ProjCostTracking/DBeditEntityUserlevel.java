/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import static javax.security.auth.callback.ConfirmationCallback.NO;
import static javax.security.auth.callback.ConfirmationCallback.YES;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author richardc
 */
public class DBeditEntityUserlevel implements Initializable {
    @FXML
    private AnchorPane myanchorpane;
    @FXML
    private TableView tbvMain;
    @FXML
    private Button btnAdd;
    @FXML
    private Font x1;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClose;

    private AnchorPane parent; //this is to be assigned from MainMenuController 
    private int commit_count=0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //tbvMain = new TableView<EntityUserlevel>();
        btnSave.setDisable(true);
        tbvMain.setEditable(true);
        
        List<EntityUserlevel> thelist = Main.db.em.createQuery("select e from EntityUserlevel e").getResultList();
        ObservableList<EntityUserlevel> obslist = FXCollections.observableList(thelist);
        
        TableColumn col1 = new TableColumn("UserLevelID");
        TableColumn col2 = new TableColumn("level");
        TableColumn col3 = new TableColumn("LevelName");
        TableColumn col4 = new TableColumn("Note");
        col1.setCellValueFactory(new PropertyValueFactory<EntityUserlevel, Integer>("userlevelid"));
        col2.setCellValueFactory(new PropertyValueFactory<EntityUserlevel, Integer>("level"));
        col3.setCellValueFactory(new PropertyValueFactory<EntityUserlevel, String>("levelname"));
        col4.setCellValueFactory(new PropertyValueFactory<EntityUserlevel, String>("note"));

        //PRIMARY KEY IＳ　ＮＯＴ　ＡＬＬＯＷＥＤ　ｔｏ　ｕｐｄａｔｅ
        col2.setCellFactory(new Callback<TableColumn<EntityUserlevel,Integer>, TableCell<EntityUserlevel,Integer>>() {
            @Override
            public TableCell<EntityUserlevel, Integer> call(TableColumn<EntityUserlevel, Integer> arg0) {
                    return new TextFieldTableCell<>( new IntegerStringConverter()  );
            }
        });
                
        col2.setOnEditCommit(
            new EventHandler<CellEditEvent<EntityUserlevel, Integer>>() {
                @Override
                public void handle(CellEditEvent<EntityUserlevel, Integer> t) {
                      EntityUserlevel ul = (EntityUserlevel) t.getTableView().getItems().get(  t.getTablePosition().getRow()   );
                      System.out.println("old:"+t.getOldValue() + "\t new:"+ t.getNewValue());
                      ul.setLevel(t.getNewValue());
                      saveOn();
                }
            }
        );
       
        col3.setCellFactory(new Callback<TableColumn<EntityUserlevel,String>, TableCell<EntityUserlevel,String>>() {
            @Override
            public TableCell<EntityUserlevel, String> call(TableColumn<EntityUserlevel, String> arg0) {
                    return new TextFieldTableCell<>( new DefaultStringConverter()  );
            }
        });
                
        col3.setOnEditCommit(
            new EventHandler<CellEditEvent<EntityUserlevel, String>>() {
                @Override
                public void handle(CellEditEvent<EntityUserlevel, String> t) {
                      EntityUserlevel ul = (EntityUserlevel) t.getTableView().getItems().get(  t.getTablePosition().getRow()   );
                      System.out.println("old:"+t.getOldValue() + "\t new:"+ t.getNewValue());
                      ul.setLevelname(t.getNewValue());
                      saveOn();
                }
            }
        );

        col4.setCellFactory(new Callback<TableColumn<EntityUserlevel,String>, TableCell<EntityUserlevel,String>>() {
            @Override
            public TableCell<EntityUserlevel, String> call(TableColumn<EntityUserlevel, String> arg0) {
                    return new TextFieldTableCell<>( new DefaultStringConverter()  );
            }
        });
                
        col4.setOnEditCommit(
            new EventHandler<CellEditEvent<EntityUserlevel, String>>() {
                @Override
                public void handle(CellEditEvent<EntityUserlevel, String> t) {
                      EntityUserlevel ul = (EntityUserlevel) t.getTableView().getItems().get(  t.getTablePosition().getRow()   );
                      System.out.println("old:"+t.getOldValue() + "\t new:"+ t.getNewValue());
                      ul.setNote(t.getNewValue());
                      saveOn();
                }
            }
        );        
        
       
        tbvMain.getColumns().addAll(col1, col2, col3, col4);

        PropertyValueFactory p = new PropertyValueFactory<EntityUserlevel, Integer>("userlevelid");
        
        System.out.println("col size:"+ tbvMain.getColumns().size());
        System.out.println("col1 text:"+ col1.getText());
        System.out.println("col1 cell bind:" + p.getProperty());
        
        
        tbvMain.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
     
        tbvMain.setItems(obslist);
        
        EntityUserlevel ul = (EntityUserlevel)tbvMain.getSelectionModel().getSelectedItem();
        if (ul == null)
            System.out.println("none selected");
        else
            System.out.println("selected:"+ul.getLevelname());        

        //Entity Managed mode
        if(!Main.db.em.getTransaction().isActive())
            Main.db.em.getTransaction().begin();

        
    }
    
    public void saveOn(){
        commit_count++;
        btnSave.setDisable(false);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
    }
    
    public void saveOff(){
        commit_count=0;
        btnSave.setDisable(true);
        btnAdd.setDisable(false);
        btnDelete.setDisable(false);
    }    
    
    public void setParent(AnchorPane parent){
        this.parent = parent;
    }

    @FXML
    private void btnAdd_onClick(ActionEvent event) {
        

    }

    @FXML
    private void btnSave_onClick(ActionEvent event) {
        Main.db.em.getTransaction().commit();
        saveOff();
    }

    @FXML
    private void btnDelete_onClick(ActionEvent event) {
        EntityUserlevel ul = (EntityUserlevel)tbvMain.getSelectionModel().getSelectedItem();
        
        String entry = "";
        if (ul.getEntityUserList().size() > 0 ){
            for (EntityUser u : ul.getEntityUserList()){
                entry += ", " + u.getUsername();
            }
            entry = "This will also delete other " + ul.getEntityUserList().size() + " user account(s): " + entry;
        }
        
        Action response = Dialogs.create()
            .owner( null)
            .title("Confirmation")
            .masthead("Are you sure to delete UserLevel: '"+ul.getLevelname()+"' ?")
            .message(entry)
            .showConfirm();

        System.out.println("response: " + response);        
        
        
        if (response.toString().equals("YES")){
            if(!Main.db.em.getTransaction().isActive())
                Main.db.em.getTransaction().begin();
            
            Main.db.em.remove(ul);
            Main.db.em.getTransaction().commit();
            tbvMain.getItems().remove(tbvMain.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    private void btnClose_onClick(ActionEvent event) {
        if(Main.db.em.getTransaction().isActive()){
            Main.db.em.clear();
        }
        
        parent.getChildren().clear();
    }
    
}
