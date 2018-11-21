package com.pxd.imooc.HelloQuartz;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class HelloScheduler {
   public static void main(String[] args) throws SchedulerException {
	JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "group1").usingJobData("job", "jobString")
			.build();
	
	System.out.println(jobDetail.getKey().getName()+"  "+jobDetail.getKey().getGroup()+"  "+
	jobDetail.getJobClass().getName());
	
	Date date = new Date();
	date.setTime(date.getTime()+3000);
	Date endDate = new Date();
	endDate.setTime(date.getTime()+6000);
	
	CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1").usingJobData("trigger", "triggerString")
     .startAt(date).endAt(endDate).withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ? *")).build();
	
	SchedulerFactory sfact = new StdSchedulerFactory();
	Scheduler scheduler = sfact.getScheduler();
	scheduler.start();
	scheduler.scheduleJob(jobDetail,trigger);
   }
}
