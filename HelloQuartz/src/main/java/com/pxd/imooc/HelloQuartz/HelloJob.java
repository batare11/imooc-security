package com.pxd.imooc.HelloQuartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

public class HelloJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
	  //编写具体的业务代码
	  JobKey jobKey = context.getJobDetail().getKey();
	  TriggerKey triggerKey = context.getTrigger().getKey();
	  
	  System.out.println(jobKey.getName()+" --this is jobInfo-- "+jobKey.getGroup());
	  System.out.println(triggerKey.getName()+" --this is triggerInfo-- "+triggerKey.getGroup());
	  
	 System.out.println("#############################################");
	 JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
	 JobDataMap triggerDataMap = context.getTrigger().getJobDataMap();
	 System.out.println(jobDataMap.getString("job")+"--job left;triggr right--"+triggerDataMap.getString("trigger"));
	 
	}
   
}
