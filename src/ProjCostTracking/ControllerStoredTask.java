/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.persistence.TypedQuery;
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
public class ControllerStoredTask implements Initializable {
    final List<Field> tfList = new ArrayList<>(); //task
    final List<Field> cfList = new ArrayList<>(); //cost
    final ObservableList<EntityCost> costList = Main.db.getCostList();
    
    private AnchorPane parent; //this is to be assigned from MainMenuController 
    @FXML 
    private Label lblTaskCost;
    
    @FXML
    private AnchorPane paneMain;
    @FXML
    private AnchorPane paneTask;
    @FXML
    private Button btnAddTask;
    @FXML
    private Font x1;
    @FXML
    private Button btnSaveTask;
    @FXML
    private Button btnDeleteTask;
    @FXML
    private TableView<EntityStoredProjTask> tbvTask;
    @FXML
    private TableView<EntityStoredTaskCost> tbvCost;
    @FXML
    private AnchorPane paneCost;
    @FXML
    private Button btnAddCost;
    @FXML
    private Button btnSaveCost;
    @FXML
    private Button btnDeleteCost;
    
    EntityStoredProjTask selectTask = null;
    EntityStoredTaskCost selectCost = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     // setup ChangeListener
        tbvTask.getSelectionModel().selectedItemProperty().addListener(new ChangeListener (){
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                 selectTask  = tbvTask.getSelectionModel().getSelectedItem();
                if(selectTask != null){
                    System.out.println("Select ProjITask: " + selectTask.getFdrtaskname() );
                    loadCostViewTable(getCostAllData());
                    lblTaskCost.setText("NT$"+String.valueOf(   getTaskCost(selectTask)   ));
                    
                }
            }        
        });      

        tbvCost.getSelectionModel().selectedItemProperty().addListener(new ChangeListener (){
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                selectCost  = tbvCost.getSelectionModel().getSelectedItem();
            }        
        });      

        loadEntityFields();
        loadTaskViewTable(getTaskAllData());
    }    
   
    
    private float getTaskCost(EntityStoredProjTask pt){
        float taskcost = 0;
        float cost = 0;
        float qty = 0;
        float total = 0;
       
            List<EntityStoredTaskCost> clist = pt.getEntityStoredTaskCostList();
            System.out.println("task:"+pt.getFdrtaskname());
            for (EntityStoredTaskCost c : clist){
                System.out.println("\t\tcost:"+c.getFdrcostid().getFdrcostname());
                cost = c.getFdrcostid().getFdrcost();
                qty = c.getFdrqty();
                total = cost * qty;
                taskcost += total;
                System.out.println("\t\ttotal:" + cost + " x " + qty + " = " + total +  "\t sum:" + taskcost);
            }

        return taskcost;
    }
    
    private void loadEntityFields(){
        for (Field f : EntityStoredProjTask.class.getDeclaredFields()){ //create a list of "f_" fields
            f.setAccessible(true); //make "private" member visible
            //we only handle those that name with "fd...."
            if (f.getName().substring(0, 2).equals("fd")){
                tfList.add(f);
            }            
        } 

        System.out.println("Found "+tfList.size() + " DB fields.");
        if (tfList.isEmpty()) {System.out.print("Entity has zero field!"); return;}

        for (Field f : EntityStoredTaskCost.class.getDeclaredFields()){ //create a list of "f_" fields
            f.setAccessible(true); //make "private" member visible
            //we only handle those that name with "fd...."
            if (f.getName().substring(0, 2).equals("fd")){
                cfList.add(f);
            }            
        } 

        System.out.println("Found "+cfList.size() + " DB fields.");
        if (cfList.isEmpty()) {System.out.print("Entity has zero field!"); return;}        
        
    }
    
    public void loadTaskViewTable(ObservableList ol){
        tbvTask.getColumns().clear();
        btnSaveTask.setDisable(true);
        tbvTask.setEditable(true);

        for (Field f : tfList){
            TableColumn c = new TableColumn( Main.tr.get(f.getName())  );//set  Column title
            c.setCellValueFactory(new PropertyValueFactory<>( f.getName() )); //set DB filed name
            //Handle Integer, int
            if (f.getType().equals(Integer.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityStoredProjTask, Integer>, TableCell<EntityStoredProjTask, Integer>>() {
                    @Override
                    public TableCell<EntityStoredProjTask, Integer> call(TableColumn<EntityStoredProjTask, Integer> arg0) {
                        return new TextFieldTableCell<>(new IntegerStringConverter());
                    }
                });
                
                c.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<EntityStoredProjTask, Integer>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<EntityStoredProjTask, Integer> t) {
                                Method m = null;
                                EntityStoredProjTask ul = (EntityStoredProjTask) t.getTableView().getItems().get(t.getTablePosition().getRow());
                                System.out.println("old:" + t.getOldValue() + "\t new:" + t.getNewValue());
                                try {//get the "Method object" by supplying its String name
                                    m = ul.getClass().getMethod(Main.field2methodName(f.getName()), Integer.class);
                                } catch (SecurityException | NoSuchMethodException e) {
                                    System.out.println(Main.field2methodName(f.getName())  );
                                    e.printStackTrace();
                                }

                                try { if (m!=null) m.invoke(ul, t.getNewValue());} 
                                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                    System.out.println("No such method found for :" + f.getName());
                                }
                                task_saveOn();

                            }
                        }
                );                
            } 

            
            //Handle String type
            else if (f.getType().equals(String.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityStoredProjTask, String>, TableCell<EntityStoredProjTask, String>>() {
                    @Override
                    public TableCell<EntityStoredProjTask, String> call(TableColumn<EntityStoredProjTask, String> arg0) {
                        return new TextFieldTableCell<>(new DefaultStringConverter());
                    }
                });
                c.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<EntityStoredProjTask, String>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<EntityStoredProjTask, String> t) {
                                Method m = null;
                                EntityStoredProjTask entity = (EntityStoredProjTask) t.getTableView().getItems().get(t.getTablePosition().getRow());
                                System.out.println("old:" + t.getOldValue() + "\t new:" + t.getNewValue());
                                try {//get the "Method object" by supplying its String name
                                    m = entity.getClass().getMethod(Main.field2methodName(f.getName()), String.class);
                                } catch (SecurityException | NoSuchMethodException e) {System.out.println(e.getMessage());}

                                try { if (m!=null) m.invoke(entity, t.getNewValue());} 
                                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                    System.out.println("No such method found for :" + f.getName());
                                }                                
                                task_saveOn();
                            }
                        }
                );                
            } 
            
            //Handle Date type
            else if (f.getType().equals(Date.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityStoredProjTask, Date>, TableCell<EntityStoredProjTask, Date>>() {
                    @Override
                    public TableCell<EntityStoredProjTask, Date> call(TableColumn<EntityStoredProjTask, Date> arg0) {
                        return new TextFieldTableCell<>(new DateStringConverter("dd/MM/yyyy"));
                    }
                });  
                c.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<EntityStoredProjTask, Date>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<EntityStoredProjTask, Date> t) {
                                Method m = null;
                                EntityStoredProjTask ul = (EntityStoredProjTask) t.getTableView().getItems().get(t.getTablePosition().getRow());
                                System.out.println("old:" + t.getOldValue() + "\t new:" + t.getNewValue());
                                try {//get the "Method object" by supplying its String name
                                    m = ul.getClass().getMethod(Main.field2methodName(f.getName()), Date.class);
                                } catch (SecurityException | NoSuchMethodException e) {System.out.println(e.getMessage());}
                                
                                try { if (m!=null) m.invoke(ul, t.getNewValue());} //same as call  ul.setFdxxxxxxxx()
                                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                    System.out.println("No such method found for :" + f.getName());
                                }
                                task_saveOn();

                            }
                        }
                );                
                                
            }
            
            tbvTask.getColumns().add(c); //add column to the TableView
        }
        
        tbvTask.setItems(ol);
        tbvTask.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //auto size field width
        

        //Entity Managed mode
        if(!Main.db.em.getTransaction().isActive())
            Main.db.em.getTransaction().begin();
    }    

    public void loadCostViewTable(ObservableList ol){
        tbvCost.getColumns().clear();
        btnSaveCost.setDisable(true);
        tbvCost.setEditable(true);

        for (Field f : cfList){
            TableColumn c = new TableColumn( Main.tr.get(f.getName())  );//set  Column title
            c.setCellValueFactory(new PropertyValueFactory<>( f.getName() )); //set DB filed name
            
            
            //Handle TOTAL field
            if (f.getName().equals("fdtotal")) {
 
            }             
            //Handle Costtype Class
            else if (f.getType().equals(EntityCost.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityStoredTaskCost, EntityCost>, TableCell<EntityStoredTaskCost, EntityCost>>() {
                    @Override
                    public TableCell<EntityStoredTaskCost, EntityCost> call(TableColumn<EntityStoredTaskCost, EntityCost> arg0) {
                        return new ComboBoxTableCell<>(costList);
                    }
                });

                c.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<EntityStoredTaskCost, EntityCost>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<EntityStoredTaskCost, EntityCost> t) {
                                Method m = null;
                                EntityStoredTaskCost ec = (EntityStoredTaskCost) t.getTableView().getItems().get(t.getTablePosition().getRow());
                                System.out.println("old:" + t.getOldValue().toString() + "\t new:" + t.getNewValue().toString());
                                try {//get the "Method object" by supplying its String name
                                    String ttt = Main.field2methodName(f.getName());
                                    System.out.println("debug:"+ttt);
                                    m = ec.getClass().getMethod(ttt, EntityCost.class);
                                } catch (SecurityException | NoSuchMethodException e) {System.out.println("error:"+e.getMessage());}

                                try { if (m!=null) m.invoke(ec, t.getNewValue());} 
                                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                    System.out.println("No such method found for :" + f.getName());
                                }                                
                                cost_saveOn();
                            }
                        }
                );
            }
            
            //Handle Float, float
            else if (f.getType().equals(Float.class) || f.getType().equals(float.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityStoredTaskCost, Float>, TableCell<EntityStoredTaskCost, Float>>() {
                    @Override
                    public TableCell<EntityStoredTaskCost, Float> call(TableColumn<EntityStoredTaskCost, Float> arg0) {
                        return new TextFieldTableCell<>(new FloatStringConverter());
                    }
                });

                c.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<EntityStoredTaskCost, Float>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<EntityStoredTaskCost, Float> t) {
                                Method m = null;
                                EntityStoredTaskCost ul = (EntityStoredTaskCost) t.getTableView().getItems().get(t.getTablePosition().getRow());
                                System.out.println("old:" + t.getOldValue() + "\t new:" + t.getNewValue());
                                try {//get the "Method object" by supplying its String name
                                    m = ul.getClass().getMethod(Main.field2methodName(f.getName()), Float.class);
                                } catch (SecurityException | NoSuchMethodException e) {System.out.println(e.getMessage());}
                                
                                try { if (m!=null) m.invoke(ul, t.getNewValue());} //same as call  ul.setFdxxxxxxxx()
                                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                    System.out.println("No such method found for :" + f.getName());
                                }
                                cost_saveOn();

                            }
                        }
                );
            }             
            //Handle Integer, int
            else if (f.getType().equals(Integer.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityStoredTaskCost, Integer>, TableCell<EntityStoredTaskCost, Integer>>() {
                    @Override
                    public TableCell<EntityStoredTaskCost, Integer> call(TableColumn<EntityStoredTaskCost, Integer> arg0) {
                        return new TextFieldTableCell<>(new IntegerStringConverter());
                    }
                });
                
                c.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<EntityStoredTaskCost, Integer>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<EntityStoredTaskCost, Integer> t) {
                                Method m = null;
                                EntityStoredTaskCost ul = (EntityStoredTaskCost) t.getTableView().getItems().get(t.getTablePosition().getRow());
                                System.out.println("old:" + t.getOldValue() + "\t new:" + t.getNewValue());
                                try {//get the "Method object" by supplying its String name
                                    m = ul.getClass().getMethod(Main.field2methodName(f.getName()), Integer.class);
                                } catch (SecurityException | NoSuchMethodException e) {
                                    System.out.println(Main.field2methodName(f.getName())  );
                                    e.printStackTrace();
                                }

                                try { if (m!=null) m.invoke(ul, t.getNewValue());} 
                                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                    System.out.println("No such method found for :" + f.getName());
                                }
                                cost_saveOn();

                            }
                        }
                );                
            } 

            
            //Handle String type
            else if (f.getType().equals(String.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityStoredTaskCost, String>, TableCell<EntityStoredTaskCost, String>>() {
                    @Override
                    public TableCell<EntityStoredTaskCost, String> call(TableColumn<EntityStoredTaskCost, String> arg0) {
                        return new TextFieldTableCell<>(new DefaultStringConverter());
                    }
                });
                c.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<EntityStoredTaskCost, String>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<EntityStoredTaskCost, String> t) {
                                Method m = null;
                                EntityStoredTaskCost entity = (EntityStoredTaskCost) t.getTableView().getItems().get(t.getTablePosition().getRow());
                                System.out.println("old:" + t.getOldValue() + "\t new:" + t.getNewValue());
                                try {//get the "Method object" by supplying its String name
                                    m = entity.getClass().getMethod(Main.field2methodName(f.getName()), String.class);
                                } catch (SecurityException | NoSuchMethodException e) {System.out.println(e.getMessage());}

                                try { if (m!=null) m.invoke(entity, t.getNewValue());} 
                                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                    System.out.println("No such method found for :" + f.getName());
                                }                                
                                cost_saveOn();
                            }
                        }
                );                
            } 
            
            //Handle Date type
            else if (f.getType().equals(Date.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityStoredTaskCost, Date>, TableCell<EntityStoredTaskCost, Date>>() {
                    @Override
                    public TableCell<EntityStoredTaskCost, Date> call(TableColumn<EntityStoredTaskCost, Date> arg0) {
                        return new TextFieldTableCell<>(new DateStringConverter("dd/MM/yyyy"));
                    }
                });  
                c.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<EntityStoredTaskCost, Date>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<EntityStoredTaskCost, Date> t) {
                                Method m = null;
                                EntityStoredTaskCost ul = (EntityStoredTaskCost) t.getTableView().getItems().get(t.getTablePosition().getRow());
                                System.out.println("old:" + t.getOldValue() + "\t new:" + t.getNewValue());
                                try {//get the "Method object" by supplying its String name
                                    m = ul.getClass().getMethod(Main.field2methodName(f.getName()), Date.class);
                                } catch (SecurityException | NoSuchMethodException e) {System.out.println(e.getMessage());}
                                
                                try { if (m!=null) m.invoke(ul, t.getNewValue());} //same as call  ul.setFdxxxxxxxx()
                                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                    System.out.println("No such method found for :" + f.getName());
                                }
                                cost_saveOn();

                            }
                        }
                );                
                                
            }
            
            tbvCost.getColumns().add(c); //add column to the TableView
        }
        
        tbvCost.setItems(ol);
        tbvCost.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //auto size field width
      
        //Entity Managed mode
        if(!Main.db.em.getTransaction().isActive())
            Main.db.em.getTransaction().begin();
    }    
     
    public void task_saveOn(){
        btnSaveTask.setDisable(false);
        btnAddTask.setDisable(true);
        btnDeleteTask.setDisable(true);
    }
    
    public void task_saveOff(){
        btnSaveTask.setDisable(true);
        btnAddTask.setDisable(false);
        btnDeleteTask.setDisable(false);
    }    

    public void cost_saveOn(){
        btnSaveCost.setDisable(false);
        btnAddCost.setDisable(true);
        btnDeleteCost.setDisable(true);
    }
    
    public void cost_saveOff(){
        btnSaveCost.setDisable(true);
        btnAddCost.setDisable(false);
        btnDeleteCost.setDisable(false);
    }        
    
    @FXML
    private void btnAddTask_onClick(ActionEvent event) {
        final List<Control> ctrlList = new ArrayList<>();
        final List<Label> lblList = new ArrayList<>();
        
        //insert Controls into a list
        for (Field f : tfList){
            lblList.add(new Label( Main.tr.get(f.getName())  ));
             if (f.getType().equals(Date.class)){
                //handle DATE
                DatePicker dp = new DatePicker( LocalDate.now());
                dp.setEditable(true);
                ctrlList.add(dp);
                Instant instant = dp.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                Date dt =  Date.from(instant);
            }
            else{//handle other types
                ctrlList.add(new TextField());
            }
        }

        final Action actionSave;    
        actionSave = new AbstractAction("Save") {
            { 
                ButtonBar.setType(this, ButtonBar.ButtonType.OK_DONE);
            }
                        
            private boolean dataFormatIsOK(){
                for (int idx=0;idx<ctrlList.size();idx++){
                    Field f = tfList.get(idx);
                    Object ctrl = ctrlList.get(idx);
                    if (f.getType().equals(String.class)){}
                    else if (f.getType().equals(Integer.class)  ||  f.getType().equals(int.class)  ){ 
                        if (! ((TextField)ctrl).getText().matches("[0-9]*")   ) {
                            Action response = Dialogs.create()
                                    .title("Error")
                                    .masthead("Format Error")
                                    .message("Field '"+  Main.tr.get(f.getName()) +"' must be Integer.")
                                    .showError();
                            return false;
                        }
                    }
                    else if (f.getType().equals(Float.class) || f.getType().equals(float.class)     ){ 
                        if (! ((TextField)ctrl).getText().matches("[0-9]*\\.?[0-9]*")   ) {
                            Action response = Dialogs.create()
                                    .title("Error")
                                    .masthead("Format Error")
                                    .message("Field '"+  Main.tr.get(f.getName()) +"' must be Float.")
                                    .showError();
                            return false;
                        }
                    }
                    else if (f.getType().equals(Date.class)){ 
                        if ( ((DatePicker)ctrl).getValue() == null   )   {
                            Action response = Dialogs.create()
                                    .title("Error")
                                    .masthead("Format Error")
                                    .message("Field '"+  Main.tr.get(f.getName()) +"' must be set.")
                                    .showError();
                            return false;
                        }                        
                    }
                }
                return true;
            }
            
            private boolean requiredDataOk(){
                boolean pass = true;
                for (int idx=0;idx < tfList.size(); idx++){
                    Field f = tfList.get(idx);
                    Object ctrl = ctrlList.get(idx);
                    //find required fields
                    if (tfList.get(idx).getName().substring(0, 3).equals("fdr") ){
                        if (f.getType().equals(Date.class)){ 

                        }
                        else if (f.getType().equals(EntityDepartment.class)) {
                            if(  ((ComboBox<EntityEmployee>)ctrlList.get(idx)).getValue() == null ){   
                                pass = false;
                            }                            
                        }                        
                        else { //String, Integer, Float ... 
                            if ( ((TextField)ctrl).getText().isEmpty() ){
                                pass = false;
                            }                            
                        }
                        
                        if (!pass){
                            Action response = Dialogs.create()
                                   .title("Error")
                                   .masthead("Data Required")
                                   .message("Please enter data for the field: '"+  Main.tr.get(f.getName()) +"'")
                                   .showError();
                            return false;
                        }
                    }
                }
                return true;
            }
           
            private void saveRecord(){
                
                addTask(ctrlList);

                loadTaskViewTable(getTaskAllData());
            }
            
            // This method is called when the login button is clicked...
            @Override
            public void execute(ActionEvent ae) {
                Dialog dlg = (Dialog) ae.getSource();
                if (dataFormatIsOK() && requiredDataOk()){
                    //save the record
                    saveRecord();
                    dlg.hide();
                    
                }
            }
        };    
        
     Dialog dlg = new Dialog(null, "Add new record");

     final GridPane content = new GridPane();
     content.setHgap(10);
     content.setVgap(10);
          
     for (int idx=0; idx<tfList.size(); idx++){
        content.add(lblList.get(idx), 0, idx);
        System.out.println("adding to content: "+ lblList.get(idx).getText());
        
        if (tfList.get(idx).getType().equals(Date.class)){
            //handle DATE
            content.add((DatePicker)ctrlList.get(idx), 1, idx);
            GridPane.setHgrow((DatePicker)ctrlList.get(idx), Priority.ALWAYS);
        }
        else{
            content.add((TextField)ctrlList.get(idx), 1, idx);
            GridPane.setHgrow((TextField)ctrlList.get(idx), Priority.ALWAYS);
        }
    }

     dlg.setResizable(false);
     dlg.setIconifiable(false);

     dlg.setContent(content);
     dlg.getActions().addAll(actionSave, Dialog.Actions.CANCEL);
     dlg.show();
    }
   
    private ObservableList<EntityStoredProjTask> getTaskAllData() {
        TypedQuery<EntityStoredProjTask> query = Main.db.em.createQuery("select e from EntityStoredProjTask e", EntityStoredProjTask.class);
        List<EntityStoredProjTask> thelist = query.getResultList();
        ObservableList<EntityStoredProjTask> obslist = FXCollections.observableList(thelist);
        return obslist;
    }

    private ObservableList<EntityStoredTaskCost> getCostAllData() {
        TypedQuery<EntityStoredTaskCost> query = Main.db.em.createQuery("select e from EntityStoredTaskCost e WHERE e.projtaskid = :projtaskid", EntityStoredTaskCost.class);
        List<EntityStoredTaskCost> thelist = query.setParameter("projtaskid", selectTask ).getResultList();
        //!!!!!!!!!! MUST REFRESH so that 'total' filed can be displayed correctly 
        for (EntityStoredTaskCost e : thelist){ Main.db.em.refresh(e); } //!!!! IMPORTANT
        
        ObservableList<EntityStoredTaskCost> obslist = FXCollections.observableList(thelist);
        return obslist;
    }    
    
    
    public void addTask(List<Control> ctrlList){
        EntityStoredProjTask entity = new EntityStoredProjTask();
        
        entity.setFdrtaskname(  ((TextField)(ctrlList.get(0))).getText()  );
        entity.setFdstart( Main.getDatePickerDate((DatePicker)ctrlList.get(1)) );
        entity.setFdend( Main.getDatePickerDate((DatePicker)ctrlList.get(2)) );
        entity.setFdnote( ((TextField)(ctrlList.get(3))).getText());

        if(!Main.db.em.getTransaction().isActive())
            Main.db.em.getTransaction().begin();

        Main.db.em.persist(entity);
        Main.db.em.getTransaction().commit();        
        Main.log(Main.LOGADD, "proj_task", entity.getProjtaskid().toString());
    }
      
        
    
    
    public void addStoredProjTask(String taskname){
        EntityStoredProjTask storedtask = new EntityStoredProjTask();
        storedtask.setProjtaskid(selectTask.getProjtaskid());
        storedtask.setFdrtaskname(taskname);
        storedtask.setFdstart(selectTask.getFdstart());
        storedtask.setFdend(selectTask.getFdend());
        storedtask.setFdnote(selectTask.getFdnote());
        
        List<EntityStoredTaskCost> storedTaskCostList = new ArrayList<>();
        
        for (EntityStoredTaskCost tc : selectTask.getEntityStoredTaskCostList()){
            EntityStoredTaskCost stc = new EntityStoredTaskCost();
            stc.setFdrcostid(tc.getFdrcostid());
            stc.setFdrqty(tc.getFdrqty());
            stc.setFdtotal(tc.getFdtotal());
            stc.setProjtaskid(storedtask);
            
            storedTaskCostList.add(stc);
        }
        
        storedtask.setEntityStoredTaskCostList(storedTaskCostList);
        if(!Main.db.em.getTransaction().isActive())
            Main.db.em.getTransaction().begin();

        Main.db.em.persist(storedtask);
        Main.db.em.getTransaction().commit();        
        Main.log(Main.LOGADD, "stored_proj_task", storedtask.getProjtaskid().toString());    
        
        //reloadProj(selectProj);
        //storedTaskList = Main.db.getStoredTaskList();//update the storedTaskList
    }
    
    public void addCost(List<Control> ctrlList){
        EntityStoredTaskCost entity = new EntityStoredTaskCost();
        
        entity.setProjtaskid(selectTask);
        entity.setFdrcostid(  (EntityCost)((ComboBox)ctrlList.get(0)).getSelectionModel().getSelectedItem()       );
        entity.setFdrqty( Float.valueOf( ((TextField)(ctrlList.get(1))).getText() )  );
        //!!!! fdtotal is auto field therefore skipped
        entity.setFdnote( ((TextField)(ctrlList.get(3))).getText());

        if(!Main.db.em.getTransaction().isActive())
            Main.db.em.getTransaction().begin();

        Main.db.em.persist(entity);
        Main.db.em.getTransaction().commit();        
        Main.log(Main.LOGADD, "proj_task", entity.getProjtaskid().toString());
    }
        
    @FXML
    private void btnSaveTask_onClick(ActionEvent event) {
        Main.db.commit();
        
        task_saveOff();           
    }

    @FXML
    private void btnDeleteTask_onClick(ActionEvent event) {
        if (selectTask==null){
            Action response = Dialogs.create()
                    .title("Error")
                    .masthead("No Task is Selected")
                    .message("You have to select a TASK first.")
                    .showError();
            return; 
        }
        
        EntityStoredProjTask entity = (EntityStoredProjTask)tbvTask.getSelectionModel().getSelectedItem();
        
        if (entity == null) return;
        String response = deleteTask(entity);
        if (response.equals("YES"))
            tbvTask.getItems().remove(tbvTask.getSelectionModel().getSelectedIndex());
        
    }

    
    public String deleteTask(EntityStoredProjTask entity){
        String userline = "";
        
        Action response = Dialogs.create()
            .owner( null)
            .title("Confirmation")
            .masthead("Are you sure to delete : '"+ entity.getFdrtaskname()+"' ?")
            .message(userline)
            .showConfirm();

        if (response.toString().equals("YES")){
            if(!Main.db.em.getTransaction().isActive())
                Main.db.em.getTransaction().begin();
            
            //!!!! Extremely IMPORTANT!!!     must refresh or newly added item CAN NOT BE delteted properly!!!
            Main.db.em.refresh(entity);
            System.out.println("response: " + response );        

            Main.db.em.remove(entity);
            Main.db.em.getTransaction().commit();
            Main.log(Main.LOGDEL, "proj_task", entity.getProjtaskid()+":"+ entity.getFdrtaskname());
        }        
        return response.toString();
    }    

    public String deleteCost(EntityStoredTaskCost entity){
        String userline = "";
        
        Action response = Dialogs.create()
            .owner( null)
            .title("Confirmation")
            .masthead("Are you sure to delete : '"+ entity.getFdrcostid().getFdrcostname() + ": Qty:" + entity.getFdrqty() +"' ?")
            .message(userline)
            .showConfirm();

        System.out.println("response: " + response);        
          
        if (response.toString().equals("YES")){
            if(!Main.db.em.getTransaction().isActive())
                Main.db.em.getTransaction().begin();
            
            Main.db.em.remove(entity);
            Main.db.em.getTransaction().commit();
            Main.log(Main.LOGDEL, "task_cost", entity.getFdrcostid().getFdrcostname() + ": Qty:" + entity.getFdrqty());
        }
               
        return response.toString();
    }    
    
    @FXML
    private void btnAddCost_onClick(ActionEvent event) {
        if (selectTask==null){
            Action response = Dialogs.create()
                    .title("Error")
                    .masthead("No Task is Selected")
                    .message("You have to select a TASK first.")
                    .showError();
            return; 
        }
        
        final List<Control> ctrlList = new ArrayList<>();
        final List<Label> lblList = new ArrayList<>();
        
        //insert Controls into a list
        for (Field f : cfList){
            lblList.add(new Label( Main.tr.get(f.getName())  ));
            if (f.getType().equals(EntityCost.class)){
                ComboBox<EntityCost> cbx = new ComboBox<>(costList);
                ctrlList.add(cbx);      
            }
            else{//handle other types
                ctrlList.add(new TextField());
            }
        }

        final Action actionSave;    
        actionSave = new AbstractAction("Save") {
            { 
                ButtonBar.setType(this, ButtonBar.ButtonType.OK_DONE);
            }
                        
            private boolean dataFormatIsOK(){
                for (int idx=0;idx<ctrlList.size();idx++){
                    Field f = cfList.get(idx);
                    Object ctrl = ctrlList.get(idx);
                    if (f.getType().equals(String.class)){}
                    else if (f.getType().equals(Integer.class)  ||  f.getType().equals(int.class)  ){ 
                        if (! ((TextField)ctrl).getText().matches("[0-9]*")   ) {
                            Action response = Dialogs.create()
                                    .title("Error")
                                    .masthead("Format Error")
                                    .message("Field '"+  Main.tr.get(f.getName()) +"' must be Integer.")
                                    .showError();
                            return false;
                        }
                    }
                    else if (f.getType().equals(Float.class) || f.getType().equals(float.class)     ){ 
                        if (! ((TextField)ctrl).getText().matches("[0-9]*\\.?[0-9]*")   ) {
                            Action response = Dialogs.create()
                                    .title("Error")
                                    .masthead("Format Error")
                                    .message("Field '"+  Main.tr.get(f.getName()) +"' must be Float.")
                                    .showError();
                            return false;
                        }
                    }
                    else if (f.getType().equals(Date.class)){ 
                        if ( ((DatePicker)ctrl).getValue() == null   )   {
                            Action response = Dialogs.create()
                                    .title("Error")
                                    .masthead("Format Error")
                                    .message("Field '"+  Main.tr.get(f.getName()) +"' must be set.")
                                    .showError();
                            return false;
                        }                        
                    }
                }
                return true;
            }
            
            private boolean requiredDataOk(){
                boolean pass = true;
                for (int idx=0;idx < cfList.size(); idx++){
                    Field f = cfList.get(idx);
                    Object ctrl = ctrlList.get(idx);
                    //find required fields
                    if (cfList.get(idx).getName().substring(0, 3).equals("fdr") ){
                        if (f.getType().equals(Date.class)){ 

                        }
                        else if (f.getType().equals(EntityCost.class)) {
                            if(  ((ComboBox<EntityCost>)ctrlList.get(idx)).getValue() == null ){   
                                pass = false;
                            }                            
                        }                        
                        else { //String, Integer, Float ... 
                            if ( ((TextField)ctrl).getText().isEmpty() ){
                                pass = false;
                            }                            
                        }
                        
                        if (!pass){
                            Action response = Dialogs.create()
                                   .title("Error")
                                   .masthead("Data Required")
                                   .message("Please enter data for the field: '"+  Main.tr.get(f.getName()) +"'")
                                   .showError();
                            return false;
                        }
                    }
                }
                return true;
            }
           
            private void saveRecord(){
                
                addCost(ctrlList);
                refreshLabel();
                loadCostViewTable(getCostAllData());
            }
            
            // This method is called when the login button is clicked...
            @Override
            public void execute(ActionEvent ae) {
                Dialog dlg = (Dialog) ae.getSource();
                if (dataFormatIsOK() && requiredDataOk()){
                    //save the record
                    saveRecord();
                    dlg.hide();
                    
                }
            }
        };    
        
     Dialog dlg = new Dialog(null, "Add new record");

     final GridPane content = new GridPane();
     content.setHgap(10);
     content.setVgap(10);
          
     for (int idx=0; idx<cfList.size(); idx++){
        content.add(lblList.get(idx), 0, idx);
        System.out.println("adding to content: "+ lblList.get(idx).getText());
        
        if (cfList.get(idx).getType().equals(EntityCost.class)){
            //handle DATE
            content.add((ComboBox<EntityCost>)ctrlList.get(idx), 1, idx);
        }
        else{
            if (cfList.get(idx).getName().equals("fdtotal")) {
                ((TextField)ctrlList.get(idx)).setDisable(true);
            }
            content.add((TextField)ctrlList.get(idx), 1, idx);
            GridPane.setHgrow((TextField)ctrlList.get(idx), Priority.ALWAYS);
        }
    }

     dlg.setResizable(false);
     dlg.setIconifiable(false);

     dlg.setContent(content);
     dlg.getActions().addAll(actionSave, Dialog.Actions.CANCEL);
     dlg.show();        
    }

    @FXML
    private void btnSaveCost_onClick(ActionEvent event) {
        Main.db.commit();
        refreshLabel();
        cost_saveOff();        
        loadCostViewTable(getCostAllData());

    }

    @FXML
    private void btnDeleteCost_onClick(ActionEvent event) {
        if (selectCost==null){
            Action response = Dialogs.create()
                    .title("Error")
                    .masthead("No Cost is Selected")
                    .message("You have to select a Cost first.")
                    .showError();
            return; 
        }

        EntityStoredTaskCost entity = (EntityStoredTaskCost)tbvCost.getSelectionModel().getSelectedItem();
        if (entity == null) return;
        String response = deleteCost(entity);
        refreshLabel();
        if (response.equals("YES"))
            tbvCost.getItems().remove(tbvCost.getSelectionModel().getSelectedIndex());

    }
    
    public void setParent(AnchorPane parent){
        this.parent = parent;
    }
    protected void refreshLabel(){
                Main.db.em.refresh(selectTask); //so that the task cost label total can be updated 
                lblTaskCost.setText("NT$"+String.valueOf(   getTaskCost(selectTask)   ));
    }
}
