package com.sgcc.example;


import org.junit.Test;

import com.sgcc.web.util.ErrorUtil;

public class ErrorCodeTest {

	@Test
	public void getMSG() throws InterruptedException {
		
		for(int i=0;i<10;i++){
			String msg=ErrorUtil.getMsg("0");
			System.out.println(msg);
			Thread.sleep(2000);
		}
	}
}
