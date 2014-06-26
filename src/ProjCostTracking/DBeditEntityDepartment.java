/*
* The DB field name must comply with the rule
* All fields name should start with "fd" (meaning open for edit)
* All required fields should start with "fdr" (MUST NOT be empty data)
* All int type must be changed to Integer in the Entity class
* All float type must be changed to Float in the Entity class
* Parimary Key is not allowed for update
* Primary Key field name MUST NOTE begine with "fd"
* Entity Class name must begin with Entityxxxxx

 */

package ProjCostTracking;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
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
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;


public class DBeditEntityDepartment implements Initializable {
    final ObservableList<EntityDepartment> deptList = Main.db.getDepartmentList();
    final List<Field> fList = new ArrayList<>();
    final String tbname = "projecttype";
    
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
    @FXML
    private TextField txfSearch;
    @FXML
    private ComboBox<FieldQuery> cbxSearch;
    @FXML
    private Button btnSearch;
    
    private AnchorPane parent; //this is to be assigned from MainMenuController 
    private int commit_count=0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEntityFields();
        loadViewTable(getAllData());
        loadSearchComboBox();
    }
    
    private void loadEntityFields(){
        for (Field f : EntityDepartment.class.getDeclaredFields()){ //create a list of "f_" fields
            f.setAccessible(true); //make "private" member visible
            //we only handle those that name with "fd...."
            if (f.getName().substring(0, 2).equals("fd")){
                fList.add(f);
            }            
        } 

        System.out.println("Found "+fList.size() + " DB fields.");
        if (fList.isEmpty()) {System.out.print("Entity has zero field!"); return;}
    }
    
    private ObservableList<EntityDepartment> getAllData() {
        List<EntityDepartment> thelist = Main.db.em.createQuery("select e from EntityDepartment e").getResultList();
        ObservableList<EntityDepartment> obslist = FXCollections.observableList(thelist);
        return obslist;
    }
    
    public void loadSearchComboBox(){
        //ComboBox will contain an Observable list of FieldQuery object
        //The text shown in the ComboBox is defined by FieldQuery.toString()
        List<FieldQuery> fqList = new  ArrayList<>();
                
        for (Field f : fList){
            FieldQuery fq = new FieldQuery("EntityDepartment", f);
            if (f.getType().equals(String.class)
                    || f.getType().equals(Integer.class)
                    || f.getType().equals(Float.class) 
                    || f.getType().equals(Double.class)){
                fqList.add(fq);
                //System.out.println("add query:" + fq.getQString());
            }
        }
        ObservableList<FieldQuery> obsFQlist = FXCollections.observableList(fqList);
        cbxSearch.setItems(obsFQlist);
    }
    
    public void loadViewTable(ObservableList ol){
        tbvMain.getColumns().clear();
        btnSave.setDisable(true);
        tbvMain.setEditable(true);
        
        for (Field f : fList){
            TableColumn c = new TableColumn( Main.tr.get(f.getName())  );//set  Column title
            c.setCellValueFactory(new PropertyValueFactory<>( f.getName() )); //set DB filed name
            //Handle Integer, int
            if (f.getType().equals(Integer.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityDepartment, Integer>, TableCell<EntityDepartment, Integer>>() {
                    @Override
                    public TableCell<EntityDepartment, Integer> call(TableColumn<EntityDepartment, Integer> arg0) {
                        return new TextFieldTableCell<>(new IntegerStringConverter());
                    }
                });

                c.setOnEditCommit(
                        new EventHandler<CellEditEvent<EntityDepartment, Integer>>() {
                            @Override
                            public void handle(CellEditEvent<EntityDepartment, Integer> t) {
                                Method m = null;
                                EntityDepartment ul = (EntityDepartment) t.getTableView().getItems().get(t.getTablePosition().getRow());
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
                                saveOn();

                            }
                        }
                );
            } 
            
            //handle parentid --> EntityDepartment
            else if (f.getType().equals(EntityDepartment.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityDepartment, EntityDepartment>, TableCell<EntityDepartment, EntityDepartment>>() {
                    @Override
                    public TableCell<EntityDepartment, EntityDepartment> call(TableColumn<EntityDepartment, EntityDepartment> arg0) {
                        ComboBoxTableCell cbc = new ComboBoxTableCell<>(deptList);
                        return cbc;
                    }
                });

                c.setOnEditCommit(
                        new EventHandler<CellEditEvent<EntityDepartment, EntityDepartment>>() {
                            @Override
                            public void handle(CellEditEvent<EntityDepartment, EntityDepartment> t) {
                                Method m = null;
                                EntityDepartment dp = (EntityDepartment) t.getTableView().getItems().get(t.getTablePosition().getRow());
                                if (t.getOldValue()!=null)
                                    System.out.println("old:" + t.getOldValue().toString() + "\t new:" + t.getNewValue().toString());
                                if (t.getNewValue().getDeptid().intValue() == dp.getDeptid().intValue()) {
                                    Dialogs.create()
                                    .title("錯誤")
                                    .masthead("隸屬單位不能和部門相同")
                                    .message("請重新選擇")
                                    .showError();
                                    
                                    c.setUserData(t.getOldValue());
                                    return;
                                }
                                try {//get the "Method object" by supplying its String name
                                    String ttt = Main.field2methodName(f.getName());
                                    System.out.println("debug:"+ttt);
                                    m = dp.getClass().getMethod(ttt, EntityDepartment.class);
                                } catch (SecurityException | NoSuchMethodException e) {System.out.println("error:"+e.getMessage());}

                                try { if (m!=null) m.invoke(dp, t.getNewValue());} 
                                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                    System.out.println("No such method found for :" + f.getName());
                                }                                
                                saveOn();
                            }
                        }
                );
            }
            
            //Handle String type
            else if (f.getType().equals(String.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityDepartment, String>, TableCell<EntityDepartment, String>>() {
                    @Override
                    public TableCell<EntityDepartment, String> call(TableColumn<EntityDepartment, String> arg0) {
                        return new TextFieldTableCell<>(new DefaultStringConverter());
                    }
                });

                c.setOnEditCommit(
                        new EventHandler<CellEditEvent<EntityDepartment, String>>() {
                            @Override
                            public void handle(CellEditEvent<EntityDepartment, String> t) {
                                Method m = null;
                                EntityDepartment entity = (EntityDepartment) t.getTableView().getItems().get(t.getTablePosition().getRow());
                                System.out.println("old:" + t.getOldValue() + "\t new:" + t.getNewValue());
                                try {//get the "Method object" by supplying its String name
                                    m = entity.getClass().getMethod(Main.field2methodName(f.getName()), String.class);
                                } catch (SecurityException | NoSuchMethodException e) {System.out.println(e.getMessage());}

                                try { if (m!=null) m.invoke(entity, t.getNewValue());} 
                                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                    System.out.println("No such method found for :" + f.getName());
                                }                                
                                saveOn();
                            }
                        }
                );

            } 


            else{
                System.out.println("ERROR Exit! MUST handle type: " + f.getName().toString());
                System.exit(0);
            }
            
            tbvMain.getColumns().add(c); //add column to the TableView
        }
         
        tbvMain.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //auto size field width
        tbvMain.setItems(ol);

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
        final List<Control> ctrlList = new ArrayList<>();
        final List<Label> lblList = new ArrayList<>();
        
        //insert Controls into a list
        for (Field f : fList){
            lblList.add(new Label( Main.tr.get(f.getName())  ));
            if (f.getType().equals(EntityDepartment.class)){
                ComboBox<EntityDepartment> cbx = new ComboBox<>(deptList);
                //cbx.getSelectionModel().selectFirst();
                ctrlList.add(cbx);
            }
            else{    
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
                    Field f = fList.get(idx);
                    Control ctrl = ctrlList.get(idx);
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
                                    .message("Field '"+  Main.tr.get(f.getName()) +"' must be chosen.")
                                    .showError();
                            return false;
                        }                        
                    }
                }
                return true;
            }
            
            private boolean requiredDataOk(){
                boolean pass = true;
                for (int idx=0;idx < fList.size(); idx++){
                    Field f = fList.get(idx);
                    Control ctrl = ctrlList.get(idx);
                    //find required fields
                    if (fList.get(idx).getName().substring(0, 3).equals("fdr") ){

                        if (f.getType().equals(EntityDepartment.class)) {
                            if(  ((ComboBox<EntityEmployee>)ctrlList.get(idx)).getValue() == null ){   
                                pass = false;
                            }                            
                        }
                        else if (f.getType().equals(Date.class)){ 
                        }
                        else { //String, Integer, Float ... except Date
                            if ( ((TextField)ctrl).getText().isEmpty() ){
                                pass = false;
                            }
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
                return true;
            }
           
            private void saveRecord(){
                EntityDepartment entity = new EntityDepartment();
                save(ctrlList);
                loadViewTable(getAllData());
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
        
     Dialog dlg = new Dialog(null, "Add new record");

     final GridPane content = new GridPane();
     content.setHgap(10);
     content.setVgap(10);
     
     //add the generated Control comps to the Content 
     for (int idx=0; idx<fList.size(); idx++){
        content.add(lblList.get(idx), 0, idx);
        System.out.println("adding to content: "+ lblList.get(idx).getText());
        if (fList.get(idx).getType().equals(Date.class)){ //handle DATE
            content.add((DatePicker)ctrlList.get(idx), 1, idx);
            GridPane.setHgrow((DatePicker)ctrlList.get(idx), Priority.ALWAYS);
        }
        else if (fList.get(idx).getType().equals(EntityDepartment.class)){ //handle EntityDepartment
            content.add((ComboBox<EntityDepartment>)ctrlList.get(idx), 1, idx);    
        }
        else{ //treat all other type with TextField
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

    private void search(){
        ObservableList<EntityDepartment> obslist = null;
        FieldQuery fq = (FieldQuery) cbxSearch.getSelectionModel().getSelectedItem();
        if (txfSearch.getText().isEmpty()){
            obslist = getAllData();
        }
        else{
             List<EntityDepartment> thelist = Main.db.em.createNamedQuery(fq.getQString())
                    .setParameter(fq.getFieldName(), txfSearch.getText())
                    .getResultList();
            obslist = FXCollections.observableList(thelist);           
        }        
         
        loadViewTable(obslist);        
    }
    
    @FXML
    private void btnSave_onClick(ActionEvent event) {
        Main.db.commit();
        saveOff();
    }

    @FXML
    private void btnDelete_onClick(ActionEvent event) {
        //both Employee and Department refers to each Department record; we need to check both.
        EntityDepartment entity = (EntityDepartment)tbvMain.getSelectionModel().getSelectedItem();
        
        if (entity == null){
            Dialogs.create()
                .title("警告")
                .masthead("")
                .message("請先選擇一筆資料")
                .showError(); 
            return;
        }
        
        List<EntityEmployee> emplst = Main.db.em.createQuery("select e from EntityEmployee e where e.fdrdeptid = :id")
                .setParameter("id", entity)
                .getResultList();
        entity.setEntityEmployeeList(emplst);
        
        List<EntityDepartment> deptlst = Main.db.em.createQuery("select e from EntityDepartment e where e.fdrparentid = :id")
                .setParameter("id", entity)
                .getResultList();        
        entity.setEntityDepartmentList(deptlst);
        System.out.println("emp list:"+emplst.toString());

        if (emplst.size()> 0 || deptlst.size()> 0 ){
        Dialogs.create()
            .title("警告")
            .masthead("不可刪除")
            .message("此資料已被引用, 必須先變更以下設定: \n 職員:" + emplst.toString()
                    +"部門:" + deptlst.toString()
            )
            .showError();  
        }
        else{
            String response = delete(entity);
            if (response.equals("YES"))
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
    
    @FXML
    private void txfSearch_onEnter(ActionEvent event) {
        search();
    }

    @FXML
    private void cbxSearch_onChange(ActionEvent event) {
        
    }

    @FXML
    private void btnSearch_onClick(ActionEvent event) {
        search();
    }

    public void save(List<Control> ctrlList){
        EntityDepartment entity = new EntityDepartment();
        
        entity.setFdrdeptname(((TextField)(ctrlList.get(0))).getText());
        EntityDepartment ct = ((ComboBox<EntityDepartment>)ctrlList.get(1)).getValue();
        entity.setFdrparentid( ct  );        
        entity.setFdnote(((TextField)(ctrlList.get(2))).getText());

        if(!Main.db.em.getTransaction().isActive())
            Main.db.em.getTransaction().begin();

        Main.db.em.persist(entity);
        Main.db.em.getTransaction().commit();        
        Main.log(Main.LOGADD, tbname, entity.getDeptid().toString());
    }

    public String delete(EntityDepartment entity){
        String userline = "";
        
        Action response = Dialogs.create()
            .owner( null)
            .title("Confirmation")
            .masthead("Are you sure to delete : '"+ entity.getFdrdeptname()+"' ?")
            .message(userline)
            .showConfirm();

        System.out.println("response: " + response);        
          
        if (response.toString().equals("YES")){
            if(!Main.db.em.getTransaction().isActive())
                Main.db.em.getTransaction().begin();
            
            Main.db.em.remove(entity);
            Main.db.em.getTransaction().commit();
            Main.log(Main.LOGDEL, tbname, entity.getDeptid().toString());
        }
        
        
        return response.toString();
    }    

    
}
