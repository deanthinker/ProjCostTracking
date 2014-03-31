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
@Table(name = "cost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityCost.findAll", query = "SELECT e FROM EntityCost e"),
    @NamedQuery(name = "EntityCost.findByCostid", query = "SELECT e FROM EntityCost e WHERE e.costid = :costid"),
    @NamedQuery(name = "EntityCost.findByFdrcostname", query = "SELECT e FROM EntityCost e WHERE e.fdrcostname = :fdrcostname"),
    @NamedQuery(name = "EntityCost.findByFdrcost", query = "SELECT e FROM EntityCost e WHERE e.fdrcost = :fdrcost"),
    @NamedQuery(name = "EntityCost.findByFdrunit", query = "SELECT e FROM EntityCost e WHERE e.fdrunit = :fdrunit")})
public class EntityCost implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer costid;
    @Basic(optional = false)
    private String fdrcostname;
    @Basic(optional = false)
    private Integer fdrcost;
    @Basic(optional = false)
    private String fdrunit;
    
    @JoinColumn(name = "fdrcosttypeid", referencedColumnName = "costtypeid")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private EntityCosttype fdrcosttypeid;
    
    @Lob
    private String fdnote;

    
    
    public EntityCost() {
    }

    public EntityCost(Integer costid) {
        this.costid = costid;
    }

    public EntityCost(Integer costid, String fdrcostname, int fdrcost, String fdrunit) {
        this.costid = costid;
        this.fdrcostname = fdrcostname;
        this.fdrcost = fdrcost;
        this.fdrunit = fdrunit;
    }

    public EntityCosttype getFdrcosttypeid() {
        return fdrcosttypeid;
    }

    public void setFdrcosttypeid(EntityCosttype fdrcosttypeid) {
        this.fdrcosttypeid = fdrcosttypeid;
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

    public Integer getFdrcost() {
        return fdrcost;
    }

    public void setFdrcost(Integer fdrcost) {
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
        return fdrcostname;
    }
    
}
