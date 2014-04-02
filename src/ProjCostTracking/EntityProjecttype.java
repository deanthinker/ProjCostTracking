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
@Table(name = "projecttype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityProjecttype.findAll", query = "SELECT e FROM EntityProjecttype e"),
    @NamedQuery(name = "EntityProjecttype.findByProjtypeid", query = "SELECT e FROM EntityProjecttype e WHERE e.projtypeid = :projtypeid"),
    @NamedQuery(name = "EntityProjecttype.findByFdrtypename", query = "SELECT e FROM EntityProjecttype e WHERE e.fdrtypename = :fdrtypename")})
public class EntityProjecttype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer projtypeid;
    @Basic(optional = false)
    private String fdrtypename;
    @Lob
    private String fdnote;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fdrprojtypeid")
    private List<EntityProject> entityProjectList;

    public EntityProjecttype() {
    }

    public EntityProjecttype(Integer projtypeid) {
        this.projtypeid = projtypeid;
    }

    public EntityProjecttype(Integer projtypeid, String fdrtypename) {
        this.projtypeid = projtypeid;
        this.fdrtypename = fdrtypename;
    }

    public Integer getProjtypeid() {
        return projtypeid;
    }

    public void setProjtypeid(Integer projtypeid) {
        this.projtypeid = projtypeid;
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
    public List<EntityProject> getEntityProjectList() {
        return entityProjectList;
    }

    public void setEntityProjectList(List<EntityProject> entityProjectList) {
        this.entityProjectList = entityProjectList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projtypeid != null ? projtypeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityProjecttype)) {
            return false;
        }
        EntityProjecttype other = (EntityProjecttype) object;
        if ((this.projtypeid == null && other.projtypeid != null) || (this.projtypeid != null && !this.projtypeid.equals(other.projtypeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.fdrtypename;
    }
    
}
