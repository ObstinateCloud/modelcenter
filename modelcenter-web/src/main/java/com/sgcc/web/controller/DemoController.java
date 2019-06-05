package com.sgcc.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sgcc.web.dto.output.PageDTO;
import com.sgcc.web.service.DemoService;

@Controller
@RequestMapping("/demo")
public class DemoController {
	
/*	@Autowired
	private QuartzManager quartzManager;*/
	
	@Autowired
	private DemoService demoService;
	@RequestMapping("/quartz/addJob")
	@ResponseBody
	public String addJob(){
//		quartzManager.addJob("testJob", "testGroup", "testTrigger", "testGroup",QuartzTest.class, "0/5 * * * * ?");
		
		return "add sucess!";
	}
	
	@RequestMapping("index")
	public String toVue(){
		return "webpage/index.html";
	}
	
	@RequestMapping("getList")
	@ResponseBody
	public PageDTO getList(int currentPage,int pageSize){
		PageDTO pageDTO=demoService.getList(currentPage,pageSize);
		return pageDTO;
	}
}
