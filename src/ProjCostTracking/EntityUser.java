/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author richardc
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityUser.findAll", query = "SELECT e FROM EntityUser e"),
    @NamedQuery(name = "EntityUser.findByUserid", query = "SELECT e FROM EntityUser e WHERE e.userid = :userid"),
    @NamedQuery(name = "EntityUser.findByFdrusername", query = "SELECT e FROM EntityUser e WHERE e.fdrusername = :fdrusername"),
    @NamedQuery(name = "EntityUser.findByFdrpassword", query = "SELECT e FROM EntityUser e WHERE e.fdrpassword = :fdrpassword"),
    @NamedQuery(name = "EntityUser.findByEntrydate", query = "SELECT e FROM EntityUser e WHERE e.entrydate = :entrydate"),
    @NamedQuery(name = "EntityUser.findByFdrlog", query = "SELECT e FROM EntityUser e WHERE e.fdrlog = :fdrlog")})
public class EntityUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer userid;
    @Basic(optional = false)
    private String fdrusername;
    @Basic(optional = false)
    private String fdrpassword;
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrydate;
    @Basic(optional = false)
    private boolean fdrlog;
    @Lob
    private String fdnote;
    @JoinColumn(name = "fdruserlevelid", referencedColumnName = "userlevelid")
    @ManyToOne(optional = false)
    private EntityUserlevel fdruserlevelid;
    @JoinColumn(name = "fdempid", referencedColumnName = "empid")
    @ManyToOne(optional = false)
    private EntityEmployee fdempid;

    public EntityUser() {
    }

    public EntityUser(Integer userid) {
        this.userid = userid;
    }

    public EntityUser(Integer userid, String fdrusername, String fdrpassword, boolean fdrlog) {
        this.userid = userid;
        this.fdrusername = fdrusername;
        this.fdrpassword = fdrpassword;
        this.fdrlog = fdrlog;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getFdrusername() {
        return fdrusername;
    }

    public void setFdrusername(String fdrusername) {
        this.fdrusername = fdrusername;
    }

    public String getFdrpassword() {
        return fdrpassword;
    }

    public void setFdrpassword(String fdrpassword) {
        this.fdrpassword = fdrpassword;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public boolean getFdrlog() {
        return fdrlog;
    }

    public void setFdrlog(boolean fdrlog) {
        this.fdrlog = fdrlog;
    }

    public String getFdnote() {
        return fdnote;
    }

    public void setFdnote(String fdnote) {
        this.fdnote = fdnote;
    }

    public EntityUserlevel getFdruserlevelid() {
        return fdruserlevelid;
    }

    public void setFdruserlevelid(EntityUserlevel fdruserlevelid) {
        this.fdruserlevelid = fdruserlevelid;
    }

    public EntityEmployee getFdempid() {
        return fdempid;
    }

    public void setFdempid(EntityEmployee fdempid) {
        this.fdempid = fdempid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityUser)) {
            return false;
        }
        EntityUser other = (EntityUser) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjCostTracking.EntityUser[ userid=" + userid + " ]";
    }
    
}
