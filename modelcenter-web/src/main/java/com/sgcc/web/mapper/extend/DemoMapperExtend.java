package com.sgcc.web.mapper.extend;

import java.util.List;

import com.sgcc.web.entity.Demo;
import com.sgcc.web.mapper.DemoMapper;

public interface DemoMapperExtend extends DemoMapper{
	public List<Demo> selectByUsername(String username);
}
