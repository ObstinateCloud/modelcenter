package com.sgcc.web.service;

import java.util.List;

import com.sgcc.web.dto.output.PageDTO;
import com.sgcc.web.entity.Demo;

public interface DemoService {

	public PageDTO getList(int page, int size);
	
	public List<Demo> selectByUsername(String username);

}
