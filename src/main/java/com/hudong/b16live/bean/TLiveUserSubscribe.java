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
@Table(name="t_live_user_subscribe")
public class TLiveUserSubscribe implements Serializable{

	/**
	 * 
	 */
	@Id
	@Column(name="ID")
    private Integer id; 
     

	/**
	 * 
	 */
	@Column(name="USER_ID")
    private String userId; 
     

	/**
	 * 
	 */
	@Column(name="LIVE_ID")
    private Integer liveId; 
     

	/**
	 * 
	 */
	@Column(name="CREATE_TIME")
    private Date createTime;
     

	/**
	 * 
	 */
	@Column(name="USER_NAME")
    private String userName;

    /**
     *
     */
    @Column(name="PUSH_STATUS")
    private Integer pushStatus;

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
      
    public void setUserId(String userId){  
        this.userId=userId;  
    }  
      
    public String getUserId(){  
        return this.userId;  
    }  
      
    public void setLiveId(Integer liveId){  
        this.liveId=liveId;  
    }  
      
    public Integer getLiveId(){  
        return this.liveId;  
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUserName(String userName){
        this.userName=userName;  
    }  
      
    public String getUserName(){  
        return this.userName;  
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}