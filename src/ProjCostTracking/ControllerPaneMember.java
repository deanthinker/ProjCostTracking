/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author richardc
 */
public class ControllerPaneMember implements Initializable {
    final List<Field> fList = new ArrayList<>();
    private ObservableList<EntityEmployee> availableEmpList = null;
    private ObservableList<EntityEmployee> projMemberList = null;
    final ObservableList<EntityDepartment> deptList = Main.db.getDepartmentList();
    
    @FXML
    private TableView<EntityEmployee> tbvProjMember;
    @FXML
    private Button btnAddMember;
    @FXML
    private Font x1;
    @FXML
    private Button btnDelMember;

    @FXML
    private TableView<EntityEmployee> tbvEmployee;
    private AnchorPane parent; //this is to be assigned from MainMenuController 
    private EntityProject proj;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEntityFields();
        /*
        proj = Main.db.getProjectList().get(1);
        showMember(proj);
        loadIntoEmployeeList(proj);
                */
    }
    
    public void reloadProj(EntityProject proj){
        showMember(proj);
        loadIntoEmployeeList(proj);
    }
    
    private ObservableList<EntityProject> getAllData() {
        List<EntityProject> thelist = Main.db.em.createQuery("select e from EntityProject e").getResultList();
        ObservableList<EntityProject> obslist = FXCollections.observableList(thelist);
        return obslist;
    }
    
    private void showMember(EntityProject proj){
        System.out.println("name:" + proj.getFdrpjname());
        ObservableList<EntityEmployee> obslist = FXCollections.observableList(proj.getEntityEmployeeList());           
         
        loadViewTable(obslist);        
    }
    
    private void loadIntoEmployeeList(EntityProject proj){
        tbvEmployee.getColumns().clear();
        List<EntityEmployee> projmember = proj.getEntityEmployeeList();
        List<EntityEmployee> availableEmployee = Main.db.getEmployeeList();
        
        for (EntityEmployee e: projmember){
            availableEmployee.remove(e);
        }
        
        ObservableList<EntityEmployee> obslist = FXCollections.observableList(availableEmployee);
        TableColumn nameCol = new TableColumn(Main.tr.get("fdrname"));
        nameCol.setCellValueFactory(new PropertyValueFactory<EntityEmployee,String>("fdrname"));
        
        TableColumn deptCol = new TableColumn(Main.tr.get("fdrdeptid"));
        deptCol.setCellValueFactory(new PropertyValueFactory<EntityEmployee,String>("fdrdeptid"));
        tbvEmployee.getColumns().addAll(nameCol, deptCol);
        tbvEmployee.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //auto size field width
        tbvEmployee.setItems(obslist);
        
    }
    
    private void loadEntityFields(){
        for (Field f : EntityEmployee.class.getDeclaredFields()){ //create a list of "f_" fields
            f.setAccessible(true); //make "private" member visible
            //we only handle those that name with "fd...."
            if (f.getName().substring(0, 2).equals("fd")){
                fList.add(f);
            }            
        } 

        System.out.println("Found "+fList.size() + " DB fields.");
        if (fList.isEmpty()) {System.out.print("Entity has zero field!"); return;}
    }

    public void loadViewTable(ObservableList ol){
        tbvProjMember.getColumns().clear();
        tbvProjMember.setEditable(false);

        for (Field f : fList){
            TableColumn c = new TableColumn( Main.tr.get(f.getName())  );//set  Column title
            c.setCellValueFactory(new PropertyValueFactory<>( f.getName() )); //set DB filed name
            //Handle Integer, int
            if (f.getType().equals(Integer.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityEmployee, Integer>, TableCell<EntityEmployee, Integer>>() {
                    @Override
                    public TableCell<EntityEmployee, Integer> call(TableColumn<EntityEmployee, Integer> arg0) {
                        return new TextFieldTableCell<>(new IntegerStringConverter());
                    }
                });
            } 
            //Handle Department Class
            else if (f.getType().equals(EntityDepartment.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityEmployee, EntityDepartment>, TableCell<EntityEmployee, EntityDepartment>>() {
                    @Override
                    public TableCell<EntityEmployee, EntityDepartment> call(TableColumn<EntityEmployee, EntityDepartment> arg0) {
                        return new ComboBoxTableCell<>(deptList);
                    }
                });
            }
            
            //Handle String type
            else if (f.getType().equals(String.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityEmployee, String>, TableCell<EntityEmployee, String>>() {
                    @Override
                    public TableCell<EntityEmployee, String> call(TableColumn<EntityEmployee, String> arg0) {
                        if (f.getName().contains("gender")  )
                            return new ComboBoxTableCell<>("M","F");
                        else
                            return new TextFieldTableCell<>(new DefaultStringConverter());
                    }
                });
            } 
            //Handle Float, float
            else if (f.getType().equals(Float.class) || f.getType().equals(float.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityEmployee, Float>, TableCell<EntityEmployee, Float>>() {
                    @Override
                    public TableCell<EntityEmployee, Float> call(TableColumn<EntityEmployee, Float> arg0) {
                        return new TextFieldTableCell<>(new FloatStringConverter());
                    }
                });
            } 
            
            //Handle Date type
            else if (f.getType().equals(Date.class)) {
                c.setCellFactory(new Callback<TableColumn<EntityEmployee, Date>, TableCell<EntityEmployee, Date>>() {
                    @Override
                    public TableCell<EntityEmployee, Date> call(TableColumn<EntityEmployee, Date> arg0) {
                        return new TextFieldTableCell<>(new DateStringConverter("dd/MM/yyyy"));
                    }
                });    
            }
            
            tbvProjMember.getColumns().add(c); //add column to the TableView
        }
         
        tbvProjMember.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //auto size field width
        tbvProjMember.setItems(ol);

        //Entity Managed mode
        if(!Main.db.em.getTransaction().isActive())
            Main.db.em.getTransaction().begin();
    }
    
    
    @FXML
    private void btnAddMember_onClick(ActionEvent event) {
        EntityEmployee emp = tbvEmployee.getSelectionModel().getSelectedItem();
        if (emp == null)
            return;
        tbvEmployee.getItems().remove(emp);
        tbvProjMember.getItems().add(emp);
        //proj.setEntityEmployeeList( tbvProjMember.getItems() );
        Main.db.em.getTransaction().commit();
        Main.db.em.getTransaction().begin();

    }

    @FXML
    private void btnDelMember_onClick(ActionEvent event) {
        EntityEmployee emp = tbvProjMember.getSelectionModel().getSelectedItem();
        if (emp == null)
            return;
        tbvProjMember.getItems().remove(emp);
        //proj.setEntityEmployeeList( tbvProjMember.getItems() );
        tbvEmployee.getItems().add(emp);
        Main.db.em.getTransaction().commit();
        Main.db.em.getTransaction().begin();

        
    
    }

    
    public void setParent(AnchorPane parent){
        this.parent = parent;
    }    
}
