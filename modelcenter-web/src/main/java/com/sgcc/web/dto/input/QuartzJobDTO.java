package com.sgcc.web.dto.input;

public class QuartzJobDTO {

	private String jobName;//定时任务名称
	private String jobGroupName; //定时任务组
	private String triggerName; //触发器名称
	private String triggerGroupName;//触发器组
	private String jobClassPath;//定时任务处理类包全路径
	private Class<?> jobClass; //定时任务处理类
	private String cron;//定时表达式
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroupName() {
		return jobGroupName;
	}
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroupName() {
		return triggerGroupName;
	}
	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}
	public String getJobClassPath() {
		return jobClassPath;
	}
	public void setJobClassPath(String jobClassPath) {
		this.jobClassPath = jobClassPath;
	}
	public Class<?> getJobClass() throws ClassNotFoundException {
		if (jobClass==null) {
			try {
				jobClass=Class.forName(jobClassPath);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new ClassNotFoundException("任务处理类不存在，请检查!");
			}
		}
		return jobClass;
	}
	public void setJobClass(Class<?> jobClass) {
		this.jobClass = jobClass;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	@Override
	public String toString() {
		return "QuartzJobDTO [jobName=" + jobName + ", jobGroupName=" + jobGroupName + ", triggerName=" + triggerName
				+ ", triggerGroupName=" + triggerGroupName + ", jobClassPath=" + jobClassPath + ", jobClass=" + jobClass
				+ ", cron=" + cron + "]";
	}
}
