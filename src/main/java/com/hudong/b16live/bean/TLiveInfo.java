package com.hudong.b16live.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity  
@Table(name="t_live_info")
public class TLiveInfo implements Serializable{

    private static final long serialVersionUID = -5809782578272943999L;
	/**
	 * 
	 */
	@Id
	@Column(name="ID")
    private Integer id;

	/**
	 * 
	 */
	@Column(name="TITLE")
    private String title; 

	/**
	 * 
	 */
	@Column(name="DETAIL")
    private String detail; 

	/**
	 * 
	 */
	@Column(name="START_TIME")
    private Date startTime; 

	/**
	 * 
	 */
	@Column(name="END_TIME")
    private Date endTime; 

	/**
	 * 
	 */
	@Column(name="STATUS")
    private int status;

	/**
	 * 
	 */
	@Column(name="DM_GROUP_ID")
    private String dmGroupId;

	/**
	 * 
	 */
	@Column(name="OBS_ID")
    private String obsId; 

	/**
	 * 
	 */
	@Column(name="ONLINE_NUM")
    private Integer onlineNum; 

	/**
	 * 
	 */
	@Column(name="ONLINE_NUM_ZSXS")
    private String onlineNumZsxs; 

	/**
	 * 
	 */
	@Column(name="LIVE_TYPE")
    private Integer liveType; 

	/**
	 * 
	 */
	@Column(name="IMG_URL_H")
    private String imgUrlH; 
     

	/**
	 * 
	 */
	@Column(name="CRE_TIME")
    private Date creTime; 
     

	/**
	 * 
	 */
	@Column(name="CRE_USER")
    private String creUser; 
     

	/**
	 * 
	 */
	@Column(name="MOD_TIME")
    private Date modTime; 
     

	/**
	 * 
	 */
	@Column(name="MOD_USER")
    private String modUser; 
     

	/**
	 * 
	 */
	@Column(name="SPEAKER_ID")
    private String speakerId; 
     

	/**
	 * 
	 */
	@Column(name="IS_HF")
    private int isHf;
     

	/**
	 * 
	 */
	@Column(name="IMG_URL_V")
    private String imgUrlV; 
     

	/**
	 * 
	 */
	@Column(name="DEL_FLAG")
    private int delFlag;
    @Column(name="OPT_NAME")
    private String optName;
    @Column(name="SPEAKER_NAME")
    private String speakerName;

    @Column(name="SPEAKER_GROUP_ID")
    private String speakerGroupId;
    @Column(name="CHANNEL_ID")
    private String channelId;

    /** 回放地址 **/
    private String hfurl;
    public String getHfurl() {
        return hfurl;
    }

    public void setHfurl(String hfurl) {
        this.hfurl = hfurl;
    }




    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }





    public String getSpeakerGroupId() {
        return speakerGroupId;
    }

    public void setSpeakerGroupId(String speakerGroupId) {
        this.speakerGroupId = speakerGroupId;
    }



    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }


     
      
    public void setId(Integer id){  
        this.id=id;  
    }  
      
    public Integer getId(){  
        return this.id;  
    }  
      
    public void setTitle(String title){  
        this.title=title;  
    }  
      
    public String getTitle(){  
        return this.title;  
    }  
      
    public void setDetail(String detail){  
        this.detail=detail;  
    }  
      
    public String getDetail(){  
        return this.detail;  
    }  
      
    public void setStartTime(Date startTime){  
        this.startTime=startTime;  
    }  
      
    public Date getStartTime(){  
        return this.startTime;  
    }  
      
    public void setEndTime(Date endTime){  
        this.endTime=endTime;  
    }  
      
    public Date getEndTime(){  
        return this.endTime;  
    }  
      
    public void setStatus(int status){
        this.status=status;  
    }  
      
    public int getStatus(){
        return this.status;  
    }  
      
    public void setDmGroupId(String dmGroupId){  
        this.dmGroupId=dmGroupId;  
    }  
      
    public String getDmGroupId(){  
        return this.dmGroupId;  
    }  
      
    public void setObsId(String obsId){  
        this.obsId=obsId;  
    }  
      
    public String getObsId(){  
        return this.obsId;  
    }  
      
    public void setOnlineNum(Integer onlineNum){  
        this.onlineNum=onlineNum;  
    }  
      
    public Integer getOnlineNum(){  
        return this.onlineNum;  
    }  
      
    public void setOnlineNumZsxs(String onlineNumZsxs){  
        this.onlineNumZsxs=onlineNumZsxs;  
    }  
      
    public String getOnlineNumZsxs(){  
        return this.onlineNumZsxs;  
    }  
      
    public void setLiveType(Integer liveType){  
        this.liveType=liveType;  
    }  
      
    public Integer getLiveType(){  
        return this.liveType;  
    }  
      
    public void setImgUrlH(String imgUrlH){  
        this.imgUrlH=imgUrlH;  
    }  
      
    public String getImgUrlH(){  
        return this.imgUrlH;  
    }  
      
    public void setCreTime(Date creTime){  
        this.creTime=creTime;  
    }  
      
    public Date getCreTime(){  
        return this.creTime;  
    }  
      
    public void setCreUser(String creUser){  
        this.creUser=creUser;  
    }  
      
    public String getCreUser(){  
        return this.creUser;  
    }  
      
    public void setModTime(Date modTime){  
        this.modTime=modTime;  
    }  
      
    public Date getModTime(){  
        return this.modTime;  
    }  
      
    public void setModUser(String modUser){  
        this.modUser=modUser;  
    }  
      
    public String getModUser(){  
        return this.modUser;  
    }  
      
    public void setSpeakerId(String speakerId){  
        this.speakerId=speakerId;  
    }  
      
    public String getSpeakerId(){  
        return this.speakerId;  
    }  
      
    public void setIsHf(int isHf){
        this.isHf=isHf;  
    }  
      
    public int getIsHf(){
        return this.isHf;  
    }  
      
    public void setImgUrlV(String imgUrlV){  
        this.imgUrlV=imgUrlV;  
    }  
      
    public String getImgUrlV(){  
        return this.imgUrlV;  
    }  
      
    public void setDelFlag(int delFlag){
        this.delFlag=delFlag;  
    }  
      
    public int getDelFlag(){
        return this.delFlag;  
    }  
      
}  