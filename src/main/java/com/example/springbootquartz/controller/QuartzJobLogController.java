package com.example.springbootquartz.controller;

import com.example.springbootquartz.domain.QuartzJobLog;
import com.example.springbootquartz.domain.Result;
import com.example.springbootquartz.domain.TableDataInfo;
import com.example.springbootquartz.service.IQuartzJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度日志操作处理
 *
 * @author liyuzhe
 */
@RestController
@RequestMapping("/job/log")
public class QuartzJobLogController extends BaseController
{
    @Autowired
    private IQuartzJobLogService jobLogService;

    /**
     * 查询定时任务调度日志列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QuartzJobLog sysJobLog)
    {
        startPage();
        List<QuartzJobLog> list = jobLogService.selectJobLogList(sysJobLog);
        return getDataTable(list);
    }


    /**
     * 根据调度编号获取详细信息
     */
    @GetMapping(value = "/{configId}")
    public Result getInfo(@PathVariable Long jobLogId)
    {
        return Result.success(jobLogService.selectJobLogById(jobLogId));
    }

    /**
     * 删除定时任务调度日志
     */
    @DeleteMapping("/{jobLogIds}")
    public Result remove(@PathVariable Long[] jobLogIds)
    {
        return toResult(jobLogService.deleteJobLogByIds(jobLogIds));
    }

    /**
     * 清空定时任务调度日志
     */
    @DeleteMapping("/clean")
    public Result clean()
    {
        jobLogService.cleanJobLog();
        return Result.success();
    }
}
