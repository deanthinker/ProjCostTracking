/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.lang.reflect.Field;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
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
        loadTableViewData();
    }
    
    public void loadTableViewData(){
        //tbvMain = new TableView<EntityUserlevel>();
        tbvMain.getColumns().clear();
        btnSave.setDisable(true);
        tbvMain.setEditable(true);
        
        List<EntityUserlevel> thelist = Main.db.em.createQuery("select e from EntityUserlevel e").getResultList();
        ObservableList<EntityUserlevel> obslist = FXCollections.observableList(thelist);
        
        TableColumn col1 = new TableColumn("UserLevelID");
        TableColumn col2 = new TableColumn("Level(0~99)");
        TableColumn col3 = new TableColumn("LevelName");
        TableColumn col4 = new TableColumn("Note");
        col1.setCellValueFactory(new PropertyValueFactory<EntityUserlevel, Integer>("userlevelid"));
        col2.setCellValueFactory(new PropertyValueFactory<EntityUserlevel, Integer>("fdrlevel"));
        col3.setCellValueFactory(new PropertyValueFactory<EntityUserlevel, String>("fdrlevelname"));
        col4.setCellValueFactory(new PropertyValueFactory<EntityUserlevel, String>("fdnote"));

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
                      ul.setFdrlevel(t.getNewValue());
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
                      ul.setFdrlevelname(t.getNewValue());
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
                      ul.setFdnote(t.getNewValue());
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
            System.out.println("selected:"+ul.getFdrlevelname());        

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

    
    class Person{
        String fdname;
        String fdaddress;
        String fdphone;
        Integer fdage;
        Date fdbirthday;
        String test;
        Float fdheight;
        public Person(String name){ this.fdname=name;  }
        public Person(String name, String address, String phone, Integer age) {
            this.fdname = name;
            this.fdaddress = address;
            this.fdphone = phone;
            this.fdage = age;
        }
    }
    @FXML
    private void btnAdd_onClick(ActionEvent event) {
        final Field[] farr = EntityUserlevel.class.getDeclaredFields();
        final List<Field> flist = new ArrayList<>();
        
        for (Field f : farr){ //create a list of "f_" fields
            f.setAccessible(true); //make "private" member visible
            if (f.getName().substring(0, 2).equals("fd")){
                flist.add(f);
            }            
        } 

        System.out.println("Person has "+flist.size() + " DB fields.");
        
        if (flist.isEmpty()) {System.out.print("Entity has zero field!"); return;}

        final List<Object> fieldlist = new ArrayList();
        final List<Label> lbllist = new ArrayList<>();
        
        //insert Controls into a list
        for (Field f : flist){
            lbllist.add(new Label(f.getName()));
            if (!f.getType().equals(Date.class)){
                fieldlist.add(new TextField());
            }
            else{
                DatePicker dp = new DatePicker( LocalDate.now());
                dp.setEditable(false);
                fieldlist.add(dp);
                Instant instant = dp.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                Date dt =  Date.from(instant);
                System.out.println("converted:"+ dt.toString());
            }
        }

        final Action actionLogin;    
        actionLogin = new AbstractAction("Save") {
            { 
                ButtonBar.setType(this, ButtonBar.ButtonType.OK_DONE);
            }
                        
            private boolean dataFormatIsOK(){
                for (int idx=0;idx<fieldlist.size();idx++){
                    Field f = flist.get(idx);
                    Object ctrl = fieldlist.get(idx);
                    if (f.getType().equals(String.class)){}
                    else if (f.getType().equals(Integer.class)  ||  f.getType().equals(int.class)  ){ 
                        if (! ((TextField)ctrl).getText().matches("[0-9]*")   ) {
                            Action response = Dialogs.create()
                                    .title("Error")
                                    .masthead("Format Error")
                                    .message("Field '"+ f.getName() +"' must be Integer.")
                                    .showError();
                            return false;
                        }
                    }
                    else if (f.getType().equals(Float.class) || f.getType().equals(float.class)     ){ 
                        if (! ((TextField)ctrl).getText().matches("[0-9]*\\.?[0-9]*")   ) {
                            Action response = Dialogs.create()
                                    .title("Error")
                                    .masthead("Format Error")
                                    .message("Field '"+ f.getName() +"' must be Float.")
                                    .showError();
                            return false;
                        }
                    }
                    else if (f.getType().equals(Date.class)){ }
                }
                return true;
            }
            
            private boolean requiredDataOk(){
                for (int idx=0;idx < flist.size(); idx++){
                    Field f = flist.get(idx);
                    Object ctrl = fieldlist.get(idx);
                    //find required fields
                    if (flist.get(idx).getName().substring(0, 3).equals("fdr") ){
                        if (!f.getType().equals(Date.class)){ //String, Integer, Float ... except Date
                            if ( ((TextField)ctrl).getText().isEmpty() ){
                                Action response = Dialogs.create()
                                       .title("Error")
                                       .masthead("Data Required")
                                       .message("Please enter data for the field: '"+ f.getName() +"'")
                                       .showError();
                                return false;
                            }
                        }
                        else if (f.getType().equals(Date.class)){ }
                    }
                }
                return true;
            }
            
            private Integer getTextFieldInteger(TextField txf){
                if (txf.getText().length() == 0)
                    return 0;
                else
                    return Integer.valueOf(txf.getText());
            }
            
            private void saveRecord(){
                EntityUserlevel ul = new EntityUserlevel();
                ul.setFdrlevel( getTextFieldInteger(  (TextField)(fieldlist.get(0))  )  );
                ul.setFdrlevelname( ((TextField)(fieldlist.get(1))).getText()    );
                ul.setFdnote( ((TextField)(fieldlist.get(2))).getText()    );
                if(!Main.db.em.getTransaction().isActive())
                    Main.db.em.getTransaction().begin();
                
                Main.db.em.persist(ul);
                Main.db.em.getTransaction().commit();
                loadTableViewData();
            }
            
            // This method is called when the login button is clicked...
            public void execute(ActionEvent ae) {
                Dialog dlg = (Dialog) ae.getSource();
                if (dataFormatIsOK() && requiredDataOk()){
                    //save the record
                    saveRecord();
                    dlg.hide();
                    
                }
            }
        };    
        
     Dialog dlg = new Dialog(null, "Login Dialog");

     final GridPane content = new GridPane();
     content.setHgap(10);
     content.setVgap(10);
          
     for (int idx=0; idx<flist.size(); idx++){
        content.add(lbllist.get(idx), 0, idx);
        if (flist.get(idx).getType().equals(Date.class)){
            content.add((DatePicker)fieldlist.get(idx), 1, idx);
            GridPane.setHgrow((DatePicker)fieldlist.get(idx), Priority.ALWAYS);
        }
        else{
            content.add((TextField)fieldlist.get(idx), 1, idx);
            GridPane.setHgrow((TextField)fieldlist.get(idx), Priority.ALWAYS);
        }
    }

     dlg.setResizable(false);
     dlg.setIconifiable(false);

     dlg.setContent(content);
     dlg.getActions().addAll(actionLogin, Dialog.Actions.CANCEL);
     dlg.show();
         

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
                entry += ", " + u.getFdrusername();
            }
            entry = "This will also delete other " + ul.getEntityUserList().size() + " user account(s): " + entry;
        }
        
        Action response = Dialogs.create()
            .owner( null)
            .title("Confirmation")
            .masthead("Are you sure to delete UserLevel: '"+ul.getFdrlevelname()+"' ?")
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
