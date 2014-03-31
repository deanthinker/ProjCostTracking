/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author richardc
 */
@Entity
@Table(name = "project")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityProject.findAll", query = "SELECT e FROM EntityProject e"),
    @NamedQuery(name = "EntityProject.findByProjectid", query = "SELECT e FROM EntityProject e WHERE e.projectid = :projectid"),
    @NamedQuery(name = "EntityProject.findByFdrfdname", query = "SELECT e FROM EntityProject e WHERE e.fdrfdname = :fdrfdname"),
    @NamedQuery(name = "EntityProject.findByFdpstart", query = "SELECT e FROM EntityProject e WHERE e.fdpstart = :fdpstart"),
    @NamedQuery(name = "EntityProject.findByFdpend", query = "SELECT e FROM EntityProject e WHERE e.fdpend = :fdpend"),
    @NamedQuery(name = "EntityProject.findByFdnote", query = "SELECT e FROM EntityProject e WHERE e.fdnote = :fdnote")})
public class EntityProject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer projectid;
    @Basic(optional = false)
    private String fdrfdname;
    @Temporal(TemporalType.DATE)
    private Date fdpstart;
    @Temporal(TemporalType.DATE)
    private Date fdpend;
    private Integer fdnote;
    @JoinColumn(name = "fdrprojtypeid", referencedColumnName = "projtypeid")
    @ManyToOne(optional = false)
    private EntityProjecttype fdrprojtypeid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fdrprojectid")
    private List<EntityProjtaskcost> entityProjtaskcostList;

    public EntityProject() {
    }

    public EntityProject(Integer projectid) {
        this.projectid = projectid;
    }

    public EntityProject(Integer projectid, String fdrfdname) {
        this.projectid = projectid;
        this.fdrfdname = fdrfdname;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getFdrfdname() {
        return fdrfdname;
    }

    public void setFdrfdname(String fdrfdname) {
        this.fdrfdname = fdrfdname;
    }

    public Date getFdpstart() {
        return fdpstart;
    }

    public void setFdpstart(Date fdpstart) {
        this.fdpstart = fdpstart;
    }

    public Date getFdpend() {
        return fdpend;
    }

    public void setFdpend(Date fdpend) {
        this.fdpend = fdpend;
    }

    public Integer getFdnote() {
        return fdnote;
    }

    public void setFdnote(Integer fdnote) {
        this.fdnote = fdnote;
    }

    public EntityProjecttype getFdrprojtypeid() {
        return fdrprojtypeid;
    }

    public void setFdrprojtypeid(EntityProjecttype fdrprojtypeid) {
        this.fdrprojtypeid = fdrprojtypeid;
    }

    @XmlTransient
    public List<EntityProjtaskcost> getEntityProjtaskcostList() {
        return entityProjtaskcostList;
    }

    public void setEntityProjtaskcostList(List<EntityProjtaskcost> entityProjtaskcostList) {
        this.entityProjtaskcostList = entityProjtaskcostList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectid != null ? projectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityProject)) {
            return false;
        }
        EntityProject other = (EntityProject) object;
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fdrfdname;
    }
    
}
