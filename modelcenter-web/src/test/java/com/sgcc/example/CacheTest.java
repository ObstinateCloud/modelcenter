package com.sgcc.example;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.sgcc.JUnit4ClassRunner;
import com.sgcc.web.dto.output.PageDTO;
import com.sgcc.web.entity.Demo;
import com.sgcc.web.service.DemoService;

/**
 * 
	测试缓存有没有起作用
* <p>Title: TestCache</p>  

* <p>Description: </p>  

* @author mengjinyuan  

 */
@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring-beans.xml" })
public class CacheTest {

	@Autowired
	private DemoService demoService;
	
	@Test
	public void testInsertTableDataFromJSON() throws InterruptedException {
		  //分页处理，显示第一页的10条数据
		/*PageHelper.startPage(1, 2);
		List<Demo> list=demoService.getList();
		PageInfo<Demo> pageInfo=new PageInfo<>(list);
		System.out.println("数据"+list);
		System.out.println("总数："+pageInfo.getTotal());*/
		List<Demo> list=demoService.selectByUsername("张三");
		System.out.println(list);
		PageDTO list2=demoService.getList(1, 10);
		System.out.println(list2);
	}
}
