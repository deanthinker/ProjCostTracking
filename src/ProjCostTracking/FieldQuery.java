/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.lang.reflect.Field;

public class FieldQuery{
    private String queryString = "";
    private Field field = null;
    
    public FieldQuery(String entity, Field f){
        //EntityUserlevel, fdrlevelname = EntityUserlevel.findByFdrlevelname
        //EntityUserlevel.findByFdrlevelname definition can be found in EntityUserlevel.java 
        String fieldName = f.getName();
        this.field = f;
        this.queryString = entity+".findBy" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
        
    @Override
    public String toString() { // !!this will be shown in the ComboBox!!!
        return getColString();
    }
    
    public String getColString(){
        return Main.tr.get(field.getName());
    }

    public String getQString() {
        return this.queryString;
    }

    public String getFieldName() {
        return field.getName();
    }
    
    public Class<?> getType(){
        return field.getType();
    }
   
}
