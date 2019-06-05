package com.sgcc.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sgcc.web.dto.output.PageDTO;
import com.sgcc.web.entity.Demo;
import com.sgcc.web.entity.DemoExample;
import com.sgcc.web.mapper.extend.DemoMapperExtend;
import com.sgcc.web.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService{
	
	@Autowired
	private DemoMapperExtend demoDao;

	@Override
	public PageDTO getList(int page, int size) {
		PageHelper.startPage(page, size);
		DemoExample demoExample=new DemoExample();
		demoExample.setOrderByClause("user_Id asc");
        List<Demo> list=demoDao.selectByExample(demoExample);
        PageInfo<Demo> pageInfo=new PageInfo<>(list);
        PageDTO pageDTO=new PageDTO(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
		pageDTO.setDataList(pageInfo.getList());
        return pageDTO;
	}

	@Override
	public List<Demo> selectByUsername(String username) {
		return demoDao.selectByUsername(username);
	}
}
