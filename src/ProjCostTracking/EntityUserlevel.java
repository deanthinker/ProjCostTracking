/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
    @NamedQuery(name = "EntityUserlevel.findByLevel", query = "SELECT e FROM EntityUserlevel e WHERE e.level = :level"),
    @NamedQuery(name = "EntityUserlevel.findByLevelname", query = "SELECT e FROM EntityUserlevel e WHERE e.levelname = :levelname")})
public class EntityUserlevel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userlevelid")
    private Integer userlevelid;
    @Basic(optional = false)
    @Column(name = "level")
    private int level;
    @Basic(optional = false)
    @Column(name = "levelname")
    private String levelname;
    @Lob
    @Column(name = "note")
    private String note;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userlevelid")
    private List<EntityUser> entityUserList;

    public EntityUserlevel() {
    }

    public EntityUserlevel(Integer userlevelid) {
        this.userlevelid = userlevelid;
    }

    public EntityUserlevel(Integer userlevelid, int level, String levelname) {
        this.userlevelid = userlevelid;
        this.level = level;
        this.levelname = levelname;
    }

    public Integer getUserlevelid() {
        return userlevelid;
    }

    public void setUserlevelid(Integer userlevelid) {
        this.userlevelid = userlevelid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
    
}
