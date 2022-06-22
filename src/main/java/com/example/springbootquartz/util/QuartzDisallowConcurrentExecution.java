package com.example.springbootquartz.util;

import com.example.springbootquartz.domain.QuartzJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @author czq
 *
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, QuartzJob sysJob) throws Exception
    {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}