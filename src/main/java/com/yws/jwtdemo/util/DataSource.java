package com.yws.jwtdemo.util;

import java.util.HashMap;
import java.util.Map;

import com.yws.jwtdemo.entity.User;

public class DataSource {

	public static Map<String, User> getData() {
		Map<String,User> data = new HashMap<>();
		data.put("zhangsan", new User("zhangsan","123456","user","view"));
		data.put("lisi", new User("lisi","123456","admin","view,edit"));
		return data;
	}
	
}
