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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "EntityProject.findByFdrpjname", query = "SELECT e FROM EntityProject e WHERE e.fdrpjname = :fdrpjname"),
    @NamedQuery(name = "EntityProject.findByFdpstart", query = "SELECT e FROM EntityProject e WHERE e.fdpstart = :fdpstart"),
    @NamedQuery(name = "EntityProject.findByFdpend", query = "SELECT e FROM EntityProject e WHERE e.fdpend = :fdpend")})
public class EntityProject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer projectid;
    @Basic(optional = false)
    private String fdrpjname;
    @JoinColumn(name = "fdrowner", referencedColumnName = "empid")
    @ManyToOne(optional = false)
    private EntityEmployee fdrowner;
    
    @JoinColumn(name = "fdrclient", referencedColumnName = "empid")
    @ManyToOne(optional = false)
    private EntityEmployee fdrclient;
    
    @JoinColumn(name = "fdrprojtypeid", referencedColumnName = "projtypeid")
    @ManyToOne(optional = false)
    private EntityProjecttype fdrprojtypeid;    
    @Temporal(TemporalType.DATE)
    private Date fdpstart;
    @Temporal(TemporalType.DATE)
    private Date fdpend;
    @Lob
    private String fdpurpose;
    @Lob
    private String fdnote;
    @JoinTable(name = "project_employee", joinColumns = {
        @JoinColumn(name = "projectid", referencedColumnName = "projectid")}, inverseJoinColumns = {
        @JoinColumn(name = "empid", referencedColumnName = "empid")})
    @ManyToMany
    private List<EntityEmployee> entityEmployeeList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectid")
    private List<EntityProjTask> entityProjTaskList;


    public EntityProject() {
    }

    public EntityProject(Integer projectid) {
        this.projectid = projectid;
    }

    public EntityProject(Integer projectid, String fdrpjname) {
        this.projectid = projectid;
        this.fdrpjname = fdrpjname;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getFdrpjname() {
        return fdrpjname;
    }

    public void setFdrpjname(String fdrpjname) {
        this.fdrpjname = fdrpjname;
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

    public String getFdpurpose() {
        return fdpurpose;
    }

    public void setFdpurpose(String fdpurpose) {
        this.fdpurpose = fdpurpose;
    }

    public String getFdnote() {
        return fdnote;
    }

    public void setFdnote(String fdnote) {
        this.fdnote = fdnote;
    }

    @XmlTransient
    public List<EntityEmployee> getEntityEmployeeList() {
        return entityEmployeeList;
    }

    public void setEntityEmployeeList(List<EntityEmployee> entityEmployeeList) {
        this.entityEmployeeList = entityEmployeeList;
    }

    public EntityEmployee getFdrowner() {
        return fdrowner;
    }

    public void setFdrowner(EntityEmployee fdrowner) {
        this.fdrowner = fdrowner;
    }

    public EntityEmployee getFdrclient() {
        return fdrclient;
    }

    public void setFdrclient(EntityEmployee fdrclient) {
        this.fdrclient = fdrclient;
    }

    public EntityProjecttype getFdrprojtypeid() {
        return fdrprojtypeid;
    }

    public void setFdrprojtypeid(EntityProjecttype fdrprojtypeid) {
        this.fdrprojtypeid = fdrprojtypeid;
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
        return this.fdrpjname;
    }

    @XmlTransient
    public List<EntityProjTask> getEntityProjTaskList() {
        return entityProjTaskList;
    }

    public void setEntityProjTaskList(List<EntityProjTask> entityProjTaskList) {
        this.entityProjTaskList = entityProjTaskList;
    }

}
