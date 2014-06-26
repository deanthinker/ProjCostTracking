/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author richardc
 */
@Entity
@Table(name = "stored_task_cost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityStoredTaskCost.findAll", query = "SELECT e FROM EntityStoredTaskCost e"),
    @NamedQuery(name = "EntityStoredTaskCost.findByTaskcostid", query = "SELECT e FROM EntityStoredTaskCost e WHERE e.taskcostid = :taskcostid"),
    @NamedQuery(name = "EntityStoredTaskCost.findByFdrqty", query = "SELECT e FROM EntityStoredTaskCost e WHERE e.fdrqty = :fdrqty"),
    @NamedQuery(name = "EntityStoredTaskCost.findByFdtotal", query = "SELECT e FROM EntityStoredTaskCost e WHERE e.fdtotal = :fdtotal")})
public class EntityStoredTaskCost implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer taskcostid;
    
    @JoinColumn(name = "projtaskid", referencedColumnName = "projtaskid")
    @ManyToOne(optional = false)
    private EntityStoredProjTask projtaskid;    
    
    @JoinColumn(name = "fdrcostid", referencedColumnName = "costid")
    @ManyToOne(optional = false)
    private EntityCost fdrcostid;
    
    @Basic(optional = false)
    private Float fdrqty;
    
    @Basic(optional = true)
    private Float fdtotal;
    
    @Lob
    private String fdnote;

    public EntityStoredTaskCost() {
    }

    public EntityStoredTaskCost(Integer taskcostid) {
        this.taskcostid = taskcostid;
    }

    public EntityStoredTaskCost(Integer taskcostid, Float fdrqty) {
        this.taskcostid = taskcostid;
        this.fdrqty = fdrqty;
    }

    public Integer getTaskcostid() {
        return taskcostid;
    }

    public void setTaskcostid(Integer taskcostid) {
        this.taskcostid = taskcostid;
    }

    public Float getFdrqty() {
        return fdrqty;
    }

    public void setFdrqty(Float fdrqty) {
        this.fdrqty = fdrqty;
    }

    public Float getFdtotal() {
        return fdtotal;
    }

    public void setFdtotal(Float fdtotal) {
        this.fdtotal = fdtotal;
    }

    public String getFdnote() {
        return fdnote;
    }

    public void setFdnote(String fdnote) {
        this.fdnote = fdnote;
    }

    public EntityCost getFdrcostid() {
        return fdrcostid;
    }

    public void setFdrcostid(EntityCost fdrcostid) {
        this.fdrcostid = fdrcostid;
    }

    public EntityStoredProjTask getProjtaskid() {
        return projtaskid;
    }

    public void setProjtaskid(EntityStoredProjTask projtaskid) {
        this.projtaskid = projtaskid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskcostid != null ? taskcostid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityStoredTaskCost)) {
            return false;
        }
        EntityStoredTaskCost other = (EntityStoredTaskCost) object;
        if ((this.taskcostid == null && other.taskcostid != null) || (this.taskcostid != null && !this.taskcostid.equals(other.taskcostid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjCostTracking.EntityStoredTaskCost[ taskcostid=" + taskcostid + " ]";
    }
    
}
