package com.hkt.rms.schedule;

import lombok.extern.slf4j.Slf4j;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkt.rms.dao.schedule.HousekeepingDao;

@Slf4j

public class HousekeepingJob implements Job {

    @Autowired
    private HousekeepingDao housekeepingDao;

    public void execute(JobExecutionContext context) {
        try {
            log.info("===== preparing to start ==========");
           /* log.info("Job name:" + context.getJobDetail().getName());
            log.info("Group name:" + context.getJobDetail().getGroup());
            log.info("Trigger name:" + context.getTrigger().getName());*/
            log.info("Firing Time:" + context.getFireTime());

            housekeepingDao.run();
            log.info("===== end of scheduled task ========");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
