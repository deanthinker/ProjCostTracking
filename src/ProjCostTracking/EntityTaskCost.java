/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "task_cost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityTaskCost.findAll", query = "SELECT e FROM EntityTaskCost e"),
    @NamedQuery(name = "EntityTaskCost.findByTaskcostid", query = "SELECT e FROM EntityTaskCost e WHERE e.taskcostid = :taskcostid"),
    @NamedQuery(name = "EntityTaskCost.findByFdrqty", query = "SELECT e FROM EntityTaskCost e WHERE e.fdrqty = :fdrqty")})
public class EntityTaskCost implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer taskcostid;
    
    @JoinColumn(name = "projtaskid", referencedColumnName = "projtaskid")
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    private EntityProjTask projtaskid;
    
    @JoinColumn(name = "fdrcostid", referencedColumnName = "costid")
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    private EntityCost fdrcostid;    
        
    @Basic(optional = false)
    private Float fdrqty;
    
    @Basic(optional = true)
    private Float fdtotal;

    
    @Lob
    private String fdnote;



    public EntityTaskCost() {
    }

    public EntityTaskCost(Integer taskcostid) {
        this.taskcostid = taskcostid;
    }

    public EntityTaskCost(Integer taskcostid, float fdrqty) {
        this.taskcostid = taskcostid;
        this.fdrqty = fdrqty;
    }

    public Integer getTaskcostid() {
        return taskcostid;
    }

    public void setTaskcostid(Integer taskcostid) {
        this.taskcostid = taskcostid;
    }

    public String getFdnote() {
        return fdnote;
    }

    public void setFdnote(String fdnote) {
        this.fdnote = fdnote;
    }

    public EntityProjTask getProjtaskid() {
        return projtaskid;
    }

    public void setProjtaskid(EntityProjTask projtaskid) {
        this.projtaskid = projtaskid;
    }

    public EntityCost getFdrcostid() {
        return fdrcostid;
    }

    public void setFdrcostid(EntityCost fdrcostid) {
        this.fdrcostid = fdrcostid;
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskcostid != null ? taskcostid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityTaskCost)) {
            return false;
        }
        EntityTaskCost other = (EntityTaskCost) object;
        if ((this.taskcostid == null && other.taskcostid != null) || (this.taskcostid != null && !this.taskcostid.equals(other.taskcostid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjCostTracking.EntityTaskCost[ taskcostid=" + taskcostid + " ]";
    }
    
}
