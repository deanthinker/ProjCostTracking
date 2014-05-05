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
@Table(name = "costtype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityCosttype.findAll", query = "SELECT e FROM EntityCosttype e"),
    @NamedQuery(name = "EntityCosttype.findByCosttypeid", query = "SELECT e FROM EntityCosttype e WHERE e.costtypeid = :costtypeid"),
    @NamedQuery(name = "EntityCosttype.findByFdrtypename", query = "SELECT e FROM EntityCosttype e WHERE e.fdrtypename = :fdrtypename")})
public class EntityCosttype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer costtypeid;
    @Basic(optional = false)
    private String fdrtypename;
    @Lob
    private String fdnote;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fdrcosttypeid")
    private List<EntityCost> entityCostList;

    public EntityCosttype() {
    }

    public EntityCosttype(Integer costtypeid) {
        this.costtypeid = costtypeid;
    }

    public EntityCosttype(Integer costtypeid, String fdrtypename) {
        this.costtypeid = costtypeid;
        this.fdrtypename = fdrtypename;
    }

    public Integer getCosttypeid() {
        return costtypeid;
    }

    public void setCosttypeid(Integer costtypeid) {
        this.costtypeid = costtypeid;
    }

    public String getFdrtypename() {
        return fdrtypename;
    }

    public void setFdrtypename(String fdrtypename) {
        this.fdrtypename = fdrtypename;
    }

    public String getFdnote() {
        return fdnote;
    }

    public void setFdnote(String fdnote) {
        this.fdnote = fdnote;
    }

    @XmlTransient
    public List<EntityCost> getEntityCostList() {
        return entityCostList;
    }

    public void setEntityCostList(List<EntityCost> entityCostList) {
        this.entityCostList = entityCostList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (costtypeid != null ? costtypeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityCosttype)) {
            return false;
        }
        EntityCosttype other = (EntityCosttype) object;
        if ((this.costtypeid == null && other.costtypeid != null) || (this.costtypeid != null && !this.costtypeid.equals(other.costtypeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fdrtypename;
    }
    
}
