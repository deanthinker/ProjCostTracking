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
@Table(name = "department")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityDepartment.findAll", query = "SELECT e FROM EntityDepartment e"),
    @NamedQuery(name = "EntityDepartment.findByDeptid", query = "SELECT e FROM EntityDepartment e WHERE e.deptid = :deptid"),
    @NamedQuery(name = "EntityDepartment.findByFdrdeptname", query = "SELECT e FROM EntityDepartment e WHERE e.fdrdeptname = :fdrdeptname")})
public class EntityDepartment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer deptid;
    private String fdrdeptname;
    
    @JoinColumn(name = "fdrparentid", referencedColumnName = "deptid")
    @ManyToOne
    private EntityDepartment fdrparentid;    
    
    @Lob
    private String fdnote;
    @OneToMany(mappedBy = "fdrparentid")
    private List<EntityDepartment> entityDepartmentList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fdrdeptid")
    private List<EntityEmployee> entityEmployeeList;

    public EntityDepartment() {
    }

    public EntityDepartment(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getFdrdeptname() {
        return fdrdeptname;
    }

    public void setFdrdeptname(String fdrdeptname) {
        this.fdrdeptname = fdrdeptname;
    }

    public String getFdnote() {
        return fdnote;
    }

    public void setFdnote(String fdnote) {
        this.fdnote = fdnote;
    }

    @XmlTransient
    public List<EntityDepartment> getEntityDepartmentList() {
        return entityDepartmentList;
    }

    public void setEntityDepartmentList(List<EntityDepartment> entityDepartmentList) {
        this.entityDepartmentList = entityDepartmentList;
    }

    public EntityDepartment getFdrparentid() {
        return fdrparentid;
    }

    public void setFdrparentid(EntityDepartment fdrparentid) {
        this.fdrparentid = fdrparentid;
    }

    @XmlTransient
    public List<EntityEmployee> getEntityEmployeeList() {
        return entityEmployeeList;
    }

    public void setEntityEmployeeList(List<EntityEmployee> entityEmployeeList) {
        this.entityEmployeeList = entityEmployeeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deptid != null ? deptid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityDepartment)) {
            return false;
        }
        EntityDepartment other = (EntityDepartment) object;
        if ((this.deptid == null && other.deptid != null) || (this.deptid != null && !this.deptid.equals(other.deptid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.fdrdeptname;
    }
    
}
