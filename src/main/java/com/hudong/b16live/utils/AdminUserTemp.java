package com.hudong.b16live.utils;

import java.io.Serializable;

//临时登录用户
public class AdminUserTemp implements Serializable {
	
	private String username;
	private String password;
	
	public AdminUserTemp(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AdminUserTemp) {
			AdminUserTemp adminUserTemp = (AdminUserTemp)obj;
			return username.equals(adminUserTemp.getUsername().trim()) && 
					password.equals(adminUserTemp.getPassword().trim());
		}
		return false;
	}
	
	public static final String SESSION_USER_KEY = "session_user";
	//临时可登录用户
	public static final AdminUserTemp[] TEMP_USER_ARRAY = {
		new AdminUserTemp("admin","ShiliuLive20180814!!"),
		new AdminUserTemp("shiliulive01","ShiliuLive20180814!!"),
		new AdminUserTemp("shiliulive02","ShiliuLive20180814!!"),
		new AdminUserTemp("shiliulive03","ShiliuLive20180814!!"),
		new AdminUserTemp("shiliulive04","ShiliuLive20180814!!"),
		new AdminUserTemp("shiliulive05","ShiliuLive20180814!!"),
		new AdminUserTemp("shiliulive06","ShiliuLive20180814!!"),
		new AdminUserTemp("shiliulive07","ShiliuLive20180814!!"),
		new AdminUserTemp("shiliulive08","ShiliuLive20180814!!"),
		new AdminUserTemp("shiliulive09","ShiliuLive20180814!!"),
		new AdminUserTemp("shiliulive10","ShiliuLive20180814!!")
	};
	//校验用户
	public static boolean checkTempUser(AdminUserTemp tempUser) {
		if (tempUser == null) {
			return false;
		}
		for (AdminUserTemp adminUserTemp : TEMP_USER_ARRAY) {
			if (tempUser.equals(adminUserTemp)) {
				return true;
			}
		}
		return false;
	}
	
}
