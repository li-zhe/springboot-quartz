package com.example.springbootquartz.util;

import com.example.springbootquartz.domain.QuartzJob;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 *
 * @author dmp
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, QuartzJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
