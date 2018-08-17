package com.hudong.b16live.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

@Entity  
@Table(name="t_live_user")
public class TLiveUser implements Serializable{

    private static final long serialVersionUID = -5809782578272943333L;
	/**
	 * 
	 */
	@Id
	@Column(name="ID")
    private Integer id;

	/**
	 * 用户名
	 */
	@Column(name="USERNAME")
    private String username; 

	/**
	 * 密码
	 */
	@Column(name="PASSWORD")
    private String password; 

	/**
	 * 昵称
	 */
	@Column(name="NICKNAME")
    private String nickname;

	
	public TLiveUser() {
		super();
	}

	public TLiveUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	} 

}  