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
@Table(name = "task")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityTask.findAll", query = "SELECT e FROM EntityTask e"),
    @NamedQuery(name = "EntityTask.findByTaskid", query = "SELECT e FROM EntityTask e WHERE e.taskid = :taskid"),
    @NamedQuery(name = "EntityTask.findByFdrtaskname", query = "SELECT e FROM EntityTask e WHERE e.fdrtaskname = :fdrtaskname")})
public class EntityTask implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer taskid;
    @Basic(optional = false)
    private String fdrtaskname;
    @Lob
    private String fdnote;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fdrtaskid")
    private List<EntityProjtaskcost> entityProjtaskcostList;

    public EntityTask() {
    }

    public EntityTask(Integer taskid) {
        this.taskid = taskid;
    }

    public EntityTask(Integer taskid, String fdrtaskname) {
        this.taskid = taskid;
        this.fdrtaskname = fdrtaskname;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getFdrtaskname() {
        return fdrtaskname;
    }

    public void setFdrtaskname(String fdrtaskname) {
        this.fdrtaskname = fdrtaskname;
    }

    public String getFdnote() {
        return fdnote;
    }

    public void setFdnote(String fdnote) {
        this.fdnote = fdnote;
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
        hash += (taskid != null ? taskid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityTask)) {
            return false;
        }
        EntityTask other = (EntityTask) object;
        if ((this.taskid == null && other.taskid != null) || (this.taskid != null && !this.taskid.equals(other.taskid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjCostTracking.EntityTask[ taskid=" + taskid + " ]";
    }
    
}
