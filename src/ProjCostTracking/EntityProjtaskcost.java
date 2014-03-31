/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
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
@Table(name = "projtaskcost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityProjtaskcost.findAll", query = "SELECT e FROM EntityProjtaskcost e"),
    @NamedQuery(name = "EntityProjtaskcost.findByPk", query = "SELECT e FROM EntityProjtaskcost e WHERE e.pk = :pk"),
    @NamedQuery(name = "EntityProjtaskcost.findByFdrqty", query = "SELECT e FROM EntityProjtaskcost e WHERE e.fdrqty = :fdrqty")})
public class EntityProjtaskcost implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer pk;
    @Basic(optional = false)
    private int fdrqty;
    @Lob
    private String fdnote;
    @JoinColumn(name = "fdrcostid", referencedColumnName = "costid")
    @ManyToOne(optional = false)
    private EntityCost fdrcostid;
    @JoinColumn(name = "fdrprojectid", referencedColumnName = "projectid")
    @ManyToOne(optional = false)
    private EntityProject fdrprojectid;
    @JoinColumn(name = "fdrtaskid", referencedColumnName = "taskid")
    @ManyToOne(optional = false)
    private EntityTask fdrtaskid;

    public EntityProjtaskcost() {
    }

    public EntityProjtaskcost(Integer pk) {
        this.pk = pk;
    }

    public EntityProjtaskcost(Integer pk, int fdrqty) {
        this.pk = pk;
        this.fdrqty = fdrqty;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public int getFdrqty() {
        return fdrqty;
    }

    public void setFdrqty(int fdrqty) {
        this.fdrqty = fdrqty;
    }

    public String getFdnote() {
        return fdnote;
    }

    public void setFdnote(String fdnote) {
        this.fdnote = fdnote;
    }

    public EntityCost getFdrcostid() {
        return fdrcostid;
    }

    public void setFdrcostid(EntityCost fdrcostid) {
        this.fdrcostid = fdrcostid;
    }

    public EntityProject getFdrprojectid() {
        return fdrprojectid;
    }

    public void setFdrprojectid(EntityProject fdrprojectid) {
        this.fdrprojectid = fdrprojectid;
    }

    public EntityTask getFdrtaskid() {
        return fdrtaskid;
    }

    public void setFdrtaskid(EntityTask fdrtaskid) {
        this.fdrtaskid = fdrtaskid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pk != null ? pk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityProjtaskcost)) {
            return false;
        }
        EntityProjtaskcost other = (EntityProjtaskcost) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjCostTracking.EntityProjtaskcost[ pk=" + pk + " ]";
    }
    
}
