package com.yws.jwtdemo.service;

import org.springframework.stereotype.Service;

import com.yws.jwtdemo.entity.User;
import com.yws.jwtdemo.util.DataSource;

@Service
public class UserService {

	public User getUser(String username) {
		if(!DataSource.getData().containsKey(username)) {
			return null;
		} else {
			return DataSource.getData().get(username);
		}
	}
}
