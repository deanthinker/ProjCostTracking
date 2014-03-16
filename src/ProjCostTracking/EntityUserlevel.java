/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.io.Serializable;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author richardc
 */
@Entity
@Table(name = "userlevel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityUserlevel.findAll", query = "SELECT e FROM EntityUserlevel e"),
    @NamedQuery(name = "EntityUserlevel.findByUserlevelid", query = "SELECT e FROM EntityUserlevel e WHERE e.userlevelid = :userlevelid"),
    @NamedQuery(name = "EntityUserlevel.findByFdrlevel", query = "SELECT e FROM EntityUserlevel e WHERE e.fdrlevel = :fdrlevel"),
    @NamedQuery(name = "EntityUserlevel.findByFdrlevelname", query = "SELECT e FROM EntityUserlevel e WHERE e.fdrlevelname LIKE :fdrlevelname"),
    @NamedQuery(name = "EntityUserlevel.findByFdnote", query = "SELECT e FROM EntityUserlevel e WHERE e.fdnote LIKE :fdnote")})
public class EntityUserlevel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer userlevelid;
    @Basic(optional = false)
    private Integer fdrlevel;
    @Basic(optional = false)
    private String fdrlevelname;
    @Lob
    private String fdnote;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fdruserlevelid")
    private List<EntityUser> entityUserList;

    public EntityUserlevel() {
    }

    public EntityUserlevel(Integer userlevelid) {
        this.userlevelid = userlevelid;
    }

    public EntityUserlevel(Integer userlevelid, int fdrlevel, String fdrlevelname) {
        this.userlevelid = userlevelid;
        this.fdrlevel = fdrlevel;
        this.fdrlevelname = fdrlevelname;
    }

    public Integer getUserlevelid() {
        return userlevelid;
    }

    public void setUserlevelid(Integer userlevelid) {
        this.userlevelid = userlevelid;
    }

    public Integer getFdrlevel() {
        return fdrlevel;
    }

    public void setFdrlevel(Integer fdrlevel) {
        this.fdrlevel = fdrlevel;
    }

    public String getFdrlevelname() {
        return fdrlevelname;
    }

    public void setFdrlevelname(String fdrlevelname) {
        this.fdrlevelname = fdrlevelname;
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
        hash += (userlevelid != null ? userlevelid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityUserlevel)) {
            return false;
        }
        EntityUserlevel other = (EntityUserlevel) object;
        if ((this.userlevelid == null && other.userlevelid != null) || (this.userlevelid != null && !this.userlevelid.equals(other.userlevelid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjCostTracking.EntityUserlevel[ userlevelid=" + userlevelid + " ]";
    }
  
    public void save(List<Object> ctrlList){
        EntityUserlevel ul = new EntityUserlevel();
        ul.setFdrlevel( Main.getTextFieldInteger(  ((TextField)(ctrlList.get(0))).getText()  )  );
        ul.setFdrlevelname( ((TextField)(ctrlList.get(1))).getText()    );
        ul.setFdnote( ((TextField)(ctrlList.get(2))).getText()    );
        if(!Main.db.em.getTransaction().isActive())
            Main.db.em.getTransaction().begin();

        Main.db.em.persist(ul);
        Main.db.em.getTransaction().commit();        
    }

    public String delete(EntityUserlevel entity){
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
            .masthead("Are you sure to delete : '"+ entity.getFdrlevelname()+"' ?")
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
