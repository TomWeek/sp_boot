package com.hudong.b16live.bean;
  

import java.util.Date;  
  
import java.io.Serializable;  
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
@Entity  
@Table(name="t_live_play_url")
public class TLivePlayUrl implements Serializable{

	@Id
	@Column(name="ID")
    private Integer id; 
    
	@Column(name="LIVE_ID")
    private Integer liveId; 
    
	@Column(name="PLAY_URL")
    private String playUrl; 
	
	@Column(name="FILE_ID")
    private String fileId; 
	
	@Column(name="TASK_ID")
    private String taskId; 
    
	@Column(name="ORDER_NUM")
    private Integer orderNum;
	
	@Column(name="DEL_FLAG")
    private int delFlag;
	
	@Column(name="CRE_TIME")
    private Date creTime;
      
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
      
    public void setPlayUrl(String playUrl){  
        this.playUrl=playUrl;  
    }  
      
    public String getPlayUrl(){  
        return this.playUrl;  
    }  
      
    public void setOrderNum(Integer orderNum){  
        this.orderNum=orderNum;  
    }  
      
    public Integer getOrderNum(){  
        return this.orderNum;  
    }

    public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
      
}  