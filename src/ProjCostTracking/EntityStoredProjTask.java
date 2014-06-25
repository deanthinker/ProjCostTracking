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

/**
 *
 * @author richardc
 */
@Entity
@Table(name = "stored_proj_task")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityStoredProjTask.findAll", query = "SELECT e FROM EntityStoredProjTask e"),
    @NamedQuery(name = "EntityStoredProjTask.findByProjtaskid", query = "SELECT e FROM EntityStoredProjTask e WHERE e.projtaskid = :projtaskid"),
    @NamedQuery(name = "EntityStoredProjTask.findByFdrtaskname", query = "SELECT e FROM EntityStoredProjTask e WHERE e.fdrtaskname = :fdrtaskname"),
    @NamedQuery(name = "EntityStoredProjTask.findByFdstart", query = "SELECT e FROM EntityStoredProjTask e WHERE e.fdstart = :fdstart"),
    @NamedQuery(name = "EntityStoredProjTask.findByFdend", query = "SELECT e FROM EntityStoredProjTask e WHERE e.fdend = :fdend")})
public class EntityStoredProjTask implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer projtaskid;
    @Basic(optional = false)
    private String fdrtaskname;
    @Temporal(TemporalType.DATE)
    private Date fdstart;
    @Temporal(TemporalType.DATE)
    private Date fdend;
    @Lob
    private String fdnote;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projtaskid")
    private List<EntityStoredTaskCost> entityStoredTaskCostList;

    public EntityStoredProjTask() {
    }

    public EntityStoredProjTask(Integer projtaskid) {
        this.projtaskid = projtaskid;
    }

    public EntityStoredProjTask(Integer projtaskid, String fdrtaskname) {
        this.projtaskid = projtaskid;
        this.fdrtaskname = fdrtaskname;
    }

    public Integer getProjtaskid() {
        return projtaskid;
    }

    public void setProjtaskid(Integer projtaskid) {
        this.projtaskid = projtaskid;
    }

    public String getFdrtaskname() {
        return fdrtaskname;
    }

    public void setFdrtaskname(String fdrtaskname) {
        this.fdrtaskname = fdrtaskname;
    }

    public Date getFdstart() {
        return fdstart;
    }

    public void setFdstart(Date fdstart) {
        this.fdstart = fdstart;
    }

    public Date getFdend() {
        return fdend;
    }

    public void setFdend(Date fdend) {
        this.fdend = fdend;
    }

    public String getFdnote() {
        return fdnote;
    }

    public void setFdnote(String fdnote) {
        this.fdnote = fdnote;
    }

    @XmlTransient
    public List<EntityStoredTaskCost> getEntityStoredTaskCostList() {
        return entityStoredTaskCostList;
    }

    public void setEntityStoredTaskCostList(List<EntityStoredTaskCost> entityStoredTaskCostList) {
        this.entityStoredTaskCostList = entityStoredTaskCostList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projtaskid != null ? projtaskid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityStoredProjTask)) {
            return false;
        }
        EntityStoredProjTask other = (EntityStoredProjTask) object;
        if ((this.projtaskid == null && other.projtaskid != null) || (this.projtaskid != null && !this.projtaskid.equals(other.projtaskid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fdrtaskname;
    }
    
}
