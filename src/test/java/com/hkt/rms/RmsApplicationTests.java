package com.hkt.rms;

import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.persister.walking.spi.AttributeDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.dao.AlertProfile;
import com.hkt.rms.dao.UserStaffRepository;
import com.hkt.rms.schedule.HousekeepingJob;
import com.hkt.rms.service.HousekeepCommandService;
import com.hkt.rms.service.UserService;
import com.hkt.rms.vo.UserAlertVo;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RmsApplicationTests {

    @Autowired
    AlertProfile alertProfile;


    @Autowired
    UserService userService;

    @Autowired
    UserStaffRepository userStaffRepository;

    @Test
    public void user() {
        for (UserStaffParticulars userStaffParticulars : userStaffRepository.findAll()) {
            log.debug("{}",userStaffParticulars);
        }
    }

    @Test
    public void test3() {
        List<UserAlertVo> list = alertProfile.findAlertByUserName("80883564");
        for (UserAlertVo userAlertVo : list) {
            log.debug("{}", userAlertVo);
        }
    }

    @Test
    public void sqlArrayList() {
        String sql = "select *from housekeep_command";

        EntityManager em = emf.createEntityManager();
        EntityManagerFactory entityManagerFactory = em.getEntityManagerFactory();
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) entityManagerFactory
                .unwrap(SessionFactory.class);
        Map<String, EntityPersister> persisterMap = sessionFactory.getEntityPersisters();
        for (Map.Entry<String, EntityPersister> entity : persisterMap.entrySet()) {
            Class targetClass = entity.getValue().getMappedClass();
            SingleTableEntityPersister persister = (SingleTableEntityPersister) entity.getValue();
            Iterable<AttributeDefinition> attributes = persister.getAttributes();
            String entityName = targetClass.getSimpleName();// Entity的名称
            String tableName = persister.getTableName();// Entity对应的表的英文名
            System.out.println("类名：" + entityName + " => 表名：" + tableName);
            for (AttributeDefinition attr : attributes) {
                String propertyName = attr.getName(); // 在entity中的属性名称
                String[] columnName = persister.getPropertyColumnNames(propertyName); // 对应数据库表中的字段名
                String type = "";
                PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(targetClass, propertyName);
                if (targetPd != null) {
                    type = targetPd.getPropertyType().getSimpleName();
                }
                System.out.println("属性名：" + propertyName + " => 类型：" + type + " => 数据库字段名：" + columnName[0]);

            }}}



    @Autowired
    private HousekeepCommandService housekeepCommandService;

    @Test
    public void testlist() {
        System.out.println(housekeepCommandService.selectAllEnabledCommand());
    }

   @Test
    public void testHousekeepingJob() throws ParseException, SchedulerException{
      /*  SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler(); 
        JobDetail detail =new JobDetail("id1", "group1", HousekeepingJob.class);
        CronTrigger trigger = new CronTrigger("trigger1", "group1");
        trigger.setCronExpression(new CronExpression("0/5 * * * * *"));
        scheduler.scheduleJob(detail, trigger);
        scheduler.start();*/
       SchedulerFactory schedulerFactory = new StdSchedulerFactory();
       Scheduler scheduler = schedulerFactory.getScheduler();
       JobDetail jobDetail = JobBuilder.newJob(HousekeepingJob.class).withIdentity("job1", "group1").build();
       // 基于表达式构建触发器
       CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
       // CronTrigger表达式触发器 继承于Trigger
       // TriggerBuilder 用于构建触发器实例
       CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job1", "group1")
               .withSchedule(cronScheduleBuilder).build();
       scheduler.scheduleJob(jobDetail, cronTrigger);
       scheduler.start();
    }
}
