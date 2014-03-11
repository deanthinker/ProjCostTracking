/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
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
    @NamedQuery(name = "EntityUser.findByUsername", query = "SELECT e FROM EntityUser e WHERE e.username = :username"),
    @NamedQuery(name = "EntityUser.findByPassword", query = "SELECT e FROM EntityUser e WHERE e.password = :password"),
    @NamedQuery(name = "EntityUser.findByEntrydate", query = "SELECT e FROM EntityUser e WHERE e.entrydate = :entrydate"),
    @NamedQuery(name = "EntityUser.findByEmpid", query = "SELECT e FROM EntityUser e WHERE e.empid = :empid"),
    @NamedQuery(name = "EntityUser.findByLog", query = "SELECT e FROM EntityUser e WHERE e.log = :log")})
public class EntityUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userid")
    private Integer userid;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "entrydate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrydate;
    @Basic(optional = false)
    @Column(name = "empid")
    private int empid;
    @Lob
    @Column(name = "note")
    private String note;
    @Basic(optional = false)
    @Column(name = "log")
    private int log;
    @JoinColumn(name = "userlevelid", referencedColumnName = "userlevelid")
    @ManyToOne(optional = false)
    private EntityUserlevel userlevelid;

    public EntityUser() {
    }

    public EntityUser(Integer userid) {
        this.userid = userid;
    }

    public EntityUser(Integer userid, String username, String password, int empid, int log) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.empid = empid;
        this.log = log;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getLog() {
        return log;
    }

    public void setLog(int log) {
        this.log = log;
    }

    public EntityUserlevel getUserlevelid() {
        return userlevelid;
    }

    public void setUserlevelid(EntityUserlevel userlevelid) {
        this.userlevelid = userlevelid;
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
