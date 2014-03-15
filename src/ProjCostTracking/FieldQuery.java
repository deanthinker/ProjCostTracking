/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

public class FieldQuery{
    private String queryString = "";
    private String fieldName = "";
    
    public FieldQuery(String entity, String fieldName){
        //EntityUserlevel, fdrlevelname = EntityUserlevel.findByFdrlevelname
        //EntityUserlevel.findByFdrlevelname definition can be found in EntityUserlevel.java 
        this.fieldName = fieldName;
        this.queryString = entity+".findBy" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
        
    @Override
    public String toString() { // !!this will be shown in the ComboBox!!!
        return getColString();
    }
    
    public String getColString(){
        return Main.tr.get(fieldName);
    }

    public String getQString() {
        return this.queryString;
    }

    public String getFieldName() {
        return this.fieldName;
    }
    
    
}
