package com.hudong.b16live.bean;
  

import java.util.Date;  
  
import java.io.Serializable;  
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * 
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
@Entity  
@Table(name="t_live_speaker")
public class TLiveSpeaker implements Serializable{
	/**
	 * 
	 */
	@Id
	@Column(name="ID")
    private Integer id; 
     

	/**
	 * 
	 */
	@Column(name="LIVE_ID")
    private Integer liveId; 
     

	/**
	 * 
	 */
	@Column(name="ACCOUNT")
    private String account; 
     

	/**
	 * 
	 */
	@Column(name="MSG")
    private String msg; 
     

	/**
	 * 
	 */
	@Column(name="CREATE_TIME")
    private Date createTime;


    /**
     *
     */
    @Column(name="DEL_FLAG")
    private Integer delFlag;


    public void setId(Integer id){  
        this.id=id;  
    }  
      
    public Integer getId(){  
        return this.id;  
    }  
      
    public void setLiveId(Integer liveId){  
        this.liveId=liveId;  
    }  
      
    public Integer getLiveId(){  
        return this.liveId;  
    }  
      
    public void setAccount(String account){  
        this.account=account;  
    }  
      
    public String getAccount(){  
        return this.account;  
    }  
      
    public void setMsg(String msg){  
        this.msg=msg;  
    }  
      
    public String getMsg(){  
        return this.msg;  
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}