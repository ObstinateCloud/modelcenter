package com.sgcc.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CopyComment implements Job {
	
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// 在这里写你要执行的调度的具体业务
		System.out.println("复制文件");
	}
}
