package com.zh.am.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * 定时任务配置，调整默认线程数量
 *
 * @author zh
 * @date 2020/4/7
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
  @Override
  public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
    scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(5));
  }
}
