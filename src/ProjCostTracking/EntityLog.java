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
import javax.persistence.Lob;
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
@Table(name = "log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntityLog.findAll", query = "SELECT e FROM EntityLog e"),
    @NamedQuery(name = "EntityLog.findByLogid", query = "SELECT e FROM EntityLog e WHERE e.logid = :logid"),
    @NamedQuery(name = "EntityLog.findByEntrydate", query = "SELECT e FROM EntityLog e WHERE e.entrydate = :entrydate"),
    @NamedQuery(name = "EntityLog.findByLogtype", query = "SELECT e FROM EntityLog e WHERE e.logtype = :logtype"),
    @NamedQuery(name = "EntityLog.findByUserid", query = "SELECT e FROM EntityLog e WHERE e.userid = :userid")})
public class EntityLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer logid;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrydate;
    @Basic(optional = false)
    private int logtype;
    
    @Basic(optional = false)
    private String tbname;
    
    @Lob
    private String note;
    @Basic(optional = false)
    private int userid;

    public static int LOGIN_PASS = 0000;
    public static int LOGIN_FAIL = 9999;
    public static int LOGOUT = 0001;
    public static int ADD = 0002;
    public static int EDIT = 0003;
    public static int DEL = 0004;
    public static int MISC = 9998;
    
    public EntityLog() {
    }

    public EntityLog(Integer logid) {
        this.logid = logid;
    }
    public EntityLog(int logtype, int userid, String tbname, String note) {
        this.logtype = logtype;
        this.tbname = tbname;
        this.userid = userid;
        this.note = note;
    }

    public String getTbname() {
        return tbname;
    }

    public void setTbname(String tbname) {
        this.tbname = tbname;
    }
    

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public int getLogtype() {
        return logtype;
    }

    public void setLogtype(int changecode) {
        this.logtype = changecode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logid != null ? logid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityLog)) {
            return false;
        }
        EntityLog other = (EntityLog) object;
        if ((this.logid == null && other.logid != null) || (this.logid != null && !this.logid.equals(other.logid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjCostTracking.EntityLog[ logid=" + logid + " ]";
    }
    
}
