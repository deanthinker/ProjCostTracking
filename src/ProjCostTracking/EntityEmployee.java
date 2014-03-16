/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author richardc
 */
@Entity
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityEmployee.findAll", query = "SELECT e FROM EntityEmployee e"),
    @NamedQuery(name = "EntityEmployee.findByEmpid", query = "SELECT e FROM EntityEmployee e WHERE e.empid = :empid"),
    @NamedQuery(name = "EntityEmployee.findByFdrname", query = "SELECT e FROM EntityEmployee e WHERE e.fdrname = :fdrname"),
    @NamedQuery(name = "EntityEmployee.findByFdlastname", query = "SELECT e FROM EntityEmployee e WHERE e.fdlastname = :fdlastname"),
    @NamedQuery(name = "EntityEmployee.findByFdbadgeid", query = "SELECT e FROM EntityEmployee e WHERE e.fdbadgeid = :fdbadgeid"),
    @NamedQuery(name = "EntityEmployee.findByFdtitle", query = "SELECT e FROM EntityEmployee e WHERE e.fdtitle = :fdtitle"),
    @NamedQuery(name = "EntityEmployee.findByFdgender", query = "SELECT e FROM EntityEmployee e WHERE e.fdgender = :fdgender"),
    @NamedQuery(name = "EntityEmployee.findByFdbirthday", query = "SELECT e FROM EntityEmployee e WHERE e.fdbirthday = :fdbirthday")})
public class EntityEmployee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer empid;
    @Basic(optional = false)
    private String fdrname;
    private String fdlastname;
    private String fdbadgeid;
    private String fdtitle;
    private String fdgender;
    @Temporal(TemporalType.DATE)
    private Date fdbirthday;
    @Lob
    private String fdnote;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fdempid")
    private List<EntityUser> entityUserList;

    public EntityEmployee() {
    }

    public EntityEmployee(Integer empid) {
        this.empid = empid;
    }

    public EntityEmployee(Integer empid, String fdrname) {
        this.empid = empid;
        this.fdrname = fdrname;
    }

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public String getFdrname() {
        return fdrname;
    }

    public void setFdrname(String fdrname) {
        this.fdrname = fdrname;
    }

    public String getFdlastname() {
        return fdlastname;
    }

    public void setFdlastname(String fdlastname) {
        this.fdlastname = fdlastname;
    }

    public String getFdbadgeid() {
        return fdbadgeid;
    }

    public void setFdbadgeid(String fdbadgeid) {
        this.fdbadgeid = fdbadgeid;
    }

    public String getFdtitle() {
        return fdtitle;
    }

    public void setFdtitle(String fdtitle) {
        this.fdtitle = fdtitle;
    }

    public String getFdgender() {
        return fdgender;
    }

    public void setFdgender(String fdgender) {
        this.fdgender = fdgender;
    }

    public Date getFdbirthday() {
        return fdbirthday;
    }

    public void setFdbirthday(Date fdbirthday) {
        this.fdbirthday = fdbirthday;
    }

    public String getFdnote() {
        return fdnote;
    }

    public void setFdnote(String fdnote) {
        this.fdnote = fdnote;
    }

    @XmlTransient
    public List<EntityUser> getEntityUserList() {
        return entityUserList;
    }

    public void setEntityUserList(List<EntityUser> entityUserList) {
        this.entityUserList = entityUserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empid != null ? empid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityEmployee)) {
            return false;
        }
        EntityEmployee other = (EntityEmployee) object;
        if ((this.empid == null && other.empid != null) || (this.empid != null && !this.empid.equals(other.empid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjCostTracking.EntityEmployee[ empid=" + empid + " ]";
    }
    public void save(List<Object> ctrlList){
        EntityEmployee entity = new EntityEmployee();
        entity.setFdrname( ((TextField)(ctrlList.get(0))).getText()    );
        entity.setFdlastname( ((TextField)(ctrlList.get(1))).getText()    );
        entity.setFdbadgeid( ((TextField)(ctrlList.get(2))).getText()    );
        entity.setFdtitle( ((TextField)(ctrlList.get(3))).getText()    );
        entity.setFdgender( ((TextField)(ctrlList.get(4))).getText()    );
        entity.setFdbirthday(  Main.getDatePickerDate((DatePicker)ctrlList.get(5))   );
        
        entity.setFdnote( ((TextField)(ctrlList.get(6))).getText()    );
        
        if(!Main.db.em.getTransaction().isActive())
            Main.db.em.getTransaction().begin();

        Main.db.em.persist(entity);
        Main.db.em.getTransaction().commit();        
    }

    public String delete(EntityEmployee entity){
        String userline = "";
        
        if (entity.getEntityUserList().size() > 0 ){
            for (EntityUser u : entity.getEntityUserList()){
                userline += ", " + u.getFdrusername();
            }
            userline = "This will also delete other " + entity.getEntityUserList().size() + " user account(s): " + userline;
        }
        
        Action response = Dialogs.create()
            .owner( null)
            .title("Confirmation")
            .masthead("Are you sure to delete : '"+ entity.getFdrname()+"' ?")
            .message(userline)
            .showConfirm();

        System.out.println("response: " + response);        
          
        if (response.toString().equals("YES")){
            if(!Main.db.em.getTransaction().isActive())
                Main.db.em.getTransaction().begin();
            
            Main.db.em.remove(entity);
            Main.db.em.getTransaction().commit();
        }
        return response.toString();
    }        
}
