package com.example.springbootquartz.util;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.example.springbootquartz.constants.ScheduleConstants;
import com.example.springbootquartz.domain.QuartzJob;
import com.example.springbootquartz.domain.QuartzJobLog;
import com.example.springbootquartz.service.IQuartzJobLogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * 抽象quartz调用
 *
 * @author dmp
 */
public abstract class AbstractQuartzJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * 线程本地变量
     */
    private static final ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        QuartzJob QuartzJob = new QuartzJob();
        BeanUtil.copyProperties(context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), QuartzJob);
        try {
            before(context, QuartzJob);
            if (QuartzJob != null) {
                doExecute(context, QuartzJob);
            }
            after(context, QuartzJob, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, QuartzJob, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param QuartzJob  系统计划任务
     */
    protected void before(JobExecutionContext context, QuartzJob QuartzJob) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param QuartzJob  系统计划任务
     */
    protected void after(JobExecutionContext context, QuartzJob QuartzJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final QuartzJobLog QuartzJobLog = new QuartzJobLog();
        QuartzJobLog.setJobName(QuartzJob.getJobName());
        QuartzJobLog.setJobGroup(QuartzJob.getJobGroup());
        QuartzJobLog.setInvokeTarget(QuartzJob.getInvokeTarget());
        QuartzJobLog.setStartTime(startTime);
        QuartzJobLog.setStopTime(new Date());
        long runMs = QuartzJobLog.getStopTime().getTime() - QuartzJobLog.getStartTime().getTime();
        QuartzJobLog.setJobMessage(QuartzJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            QuartzJobLog.setStatus("1");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String str = sw.toString();
            String errorMsg = StrUtil.sub(str, 0, 2000);
            QuartzJobLog.setExceptionInfo(errorMsg);
        } else {
            QuartzJobLog.setStatus("0");
        }

        // 写入数据库当中
        SpringUtil.getBean(IQuartzJobLogService.class).addJobLog(QuartzJobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param QuartzJob  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, QuartzJob QuartzJob) throws Exception;
}
