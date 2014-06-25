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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "cost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityCost.findAll", query = "SELECT e FROM EntityCost e"),
    @NamedQuery(name = "EntityCost.findByCostid", query = "SELECT e FROM EntityCost e WHERE e.costid = :costid"),
    @NamedQuery(name = "EntityCost.findByFdrcostname", query = "SELECT e FROM EntityCost e WHERE e.fdrcostname = :fdrcostname"),
    @NamedQuery(name = "EntityCost.findByFdrcost", query = "SELECT e FROM EntityCost e WHERE e.fdrcost = :fdrcost"),
    @NamedQuery(name = "EntityCost.findByFdrunit", query = "SELECT e FROM EntityCost e WHERE e.fdrunit = :fdrunit")})
public class EntityCost implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer costid;
    @Basic(optional = false)
    private String fdrcostname;
    @JoinColumn(name = "fdrcosttypeid", referencedColumnName = "costtypeid")
    @ManyToOne(optional = false)
    private EntityCosttype fdrcosttypeid;
    
    @Basic(optional = false)
    private Float fdrcost;
    @Basic(optional = false)
    private String fdrunit;
    @Lob
    private String fdnote;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fdrcostid")
    private List<EntityStoredTaskCost> entityStoredTaskCostList;
    private static final long serialVersionUID = 1L;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fdrcostid")
    private List<EntityTaskCost> entityTaskCostList;

    public EntityCost() {
    }

    public EntityCost(Integer costid) {
        this.costid = costid;
    }

    public EntityCost(Integer costid, String fdrcostname, Float fdrcost, String fdrunit) {
        this.costid = costid;
        this.fdrcostname = fdrcostname;
        this.fdrcost = fdrcost;
        this.fdrunit = fdrunit;
    }

    public Integer getCostid() {
        return costid;
    }

    public void setCostid(Integer costid) {
        this.costid = costid;
    }

    public String getFdrcostname() {
        return fdrcostname;
    }

    public void setFdrcostname(String fdrcostname) {
        this.fdrcostname = fdrcostname;
    }

    public Float getFdrcost() {
        return fdrcost;
    }

    public void setFdrcost(Float fdrcost) {
        this.fdrcost = fdrcost;
    }

    public String getFdrunit() {
        return fdrunit;
    }

    public void setFdrunit(String fdrunit) {
        this.fdrunit = fdrunit;
    }

    public String getFdnote() {
        return fdnote;
    }

    public void setFdnote(String fdnote) {
        this.fdnote = fdnote;
    }

    public EntityCosttype getFdrcosttypeid() {
        return fdrcosttypeid;
    }

    public void setFdrcosttypeid(EntityCosttype fdrcosttypeid) {
        this.fdrcosttypeid = fdrcosttypeid;
    }

    @XmlTransient
    public List<EntityTaskCost> getEntityTaskCostList() {
        return entityTaskCostList;
    }

    public void setEntityTaskCostList(List<EntityTaskCost> entityTaskCostList) {
        this.entityTaskCostList = entityTaskCostList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (costid != null ? costid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityCost)) {
            return false;
        }
        EntityCost other = (EntityCost) object;
        if ((this.costid == null && other.costid != null) || (this.costid != null && !this.costid.equals(other.costid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fdrcostname + ":\t$"+fdrcost;
    }

    public EntityCost(Integer costid, String fdrcostname, float fdrcost, String fdrunit) {
        this.costid = costid;
        this.fdrcostname = fdrcostname;
        this.fdrcost = fdrcost;
        this.fdrunit = fdrunit;
    }

    public void setFdrcost(float fdrcost) {
        this.fdrcost = fdrcost;
    } 
    
    @XmlTransient
    public List<EntityStoredTaskCost> getEntityStoredTaskCostList() {
        return entityStoredTaskCostList;
    }

    public void setEntityStoredTaskCostList(List<EntityStoredTaskCost> entityStoredTaskCostList) {
        this.entityStoredTaskCostList = entityStoredTaskCostList;
    } 
      
}
