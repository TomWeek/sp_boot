package com.hudong.b16live.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.hudong.b16live.bean.TLiveUser;
import com.hudong.b16live.mapper.TLiveUserMapper;
import com.hudong.b16live.utils.MD5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * dao实现层
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
@Service
public class TLiveUserService {

	@Autowired
	private TLiveUserMapper tLiveUserMapper;
	
	//添加用户
	public void addUser(TLiveUser tLiveUser) {
		tLiveUserMapper.addUser(tLiveUser);
	}
	//修改用户
	public void updateUser(TLiveUser tLiveUser) {
		tLiveUserMapper.updateUser(tLiveUser);
	}
	//删除用户
	public void deleteById(int id) {
		tLiveUserMapper.deleteById(id);
	}
	//根据id查询用户
	public TLiveUser queryById(int id){
		return tLiveUserMapper.queryById(id);
	}
	//根据用户名查询用户
	public TLiveUser queryByName(String username){
		return tLiveUserMapper.queryByName(username);
	}
}
