package com.example.springbootquartz.controller;

import cn.hutool.core.util.StrUtil;

import com.example.springbootquartz.constants.CommonConstants;
import com.example.springbootquartz.domain.QuartzJob;
import com.example.springbootquartz.domain.Result;
import com.example.springbootquartz.domain.TableDataInfo;
import com.example.springbootquartz.exception.TaskException;
import com.example.springbootquartz.service.IQuartzJobService;
import com.example.springbootquartz.util.CronUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 调度任务信息操作处理
 *
 * @author liyuzhe
 */
@RestController
@RequestMapping("/job")
public class QuartzJobController extends BaseController {
	@Autowired
	private IQuartzJobService jobService;

	/**
	 * 查询定时任务列表
	 */
	@GetMapping("/list")
	public TableDataInfo list(QuartzJob sysJob) {
		startPage();
		List<QuartzJob> list = jobService.selectJobList(sysJob);
		return getDataTable(list);
	}

	/**
	 * 导出定时任务列表
	 */
	@PostMapping("/export")
	public void export(HttpServletResponse response, QuartzJob sysJob) {
		List<QuartzJob> list = jobService.selectJobList(sysJob);
	}

	/**
	 * 获取定时任务详细信息
	 */
	@GetMapping(value = "/{jobId}")
	public Result getInfo(@PathVariable("jobId") Long jobId) {
		return Result.success(jobService.selectJobById(jobId));
	}

	/**
	 * 新增定时任务
	 */
	@PostMapping
	public Result add(@RequestBody QuartzJob job) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(job.getCronExpression())) {
			return error("新增任务'" + job.getJobName() + "'失败，Cron表达式不正确");
		} else if (StrUtil.containsIgnoreCase(job.getInvokeTarget(), CommonConstants.LOOKUP_RMI)) {
			return error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi://'调用");
		} else if (StrUtil.containsIgnoreCase(job.getInvokeTarget(), CommonConstants.LOOKUP_LDAP)) {
			return error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap://'调用");
		} else if (StrUtil.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{CommonConstants.HTTP, CommonConstants.HTTPS})) {
			return error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)//'调用");
		} else if (StrUtil.containsAnyIgnoreCase(job.getInvokeTarget(), CommonConstants.JOB_ERROR_STR)) {
			return error("新增任务'" + job.getJobName() + "'失败，目标字符串存在违规");
		}
//		job.setCreateBy("admin");
		return toResult(jobService.insertJob(job));
	}

	/**
	 * 修改定时任务
	 */
	@PutMapping
	public Result edit(@RequestBody QuartzJob job) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(job.getCronExpression())) {
			return error("修改任务'" + job.getJobName() + "'失败，Cron表达式不正确");
		} else if (StrUtil.containsIgnoreCase(job.getInvokeTarget(), CommonConstants.LOOKUP_RMI)) {
			return error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi://'调用");
		} else if (StrUtil.containsIgnoreCase(job.getInvokeTarget(), CommonConstants.LOOKUP_LDAP)) {
			return error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap://'调用");
		} else if (StrUtil.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{CommonConstants.HTTP, CommonConstants.HTTPS})) {
			return error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)//'调用");
		} else if (StrUtil.containsAnyIgnoreCase(job.getInvokeTarget(), CommonConstants.JOB_ERROR_STR)) {
			return error("修改任务'" + job.getJobName() + "'失败，目标字符串存在违规");
		}
//		job.setUpdateBy("admin");
		return toResult(jobService.updateJob(job));
	}

	/**
	 * 定时任务状态修改
	 */
	@PutMapping("/changeStatus")
	public Result changeStatus(@RequestBody QuartzJob job) throws SchedulerException {
		QuartzJob newJob = jobService.selectJobById(job.getJobId());
		newJob.setStatus(job.getStatus());
		return toResult(jobService.changeStatus(newJob));
	}

	/**
	 * 定时任务立即执行一次
	 */
	@PutMapping("/run")
	public Result run(@RequestBody QuartzJob job) throws SchedulerException {
		jobService.run(job);
		return Result.success();
	}

	/**
	 * 删除定时任务
	 */
	@DeleteMapping("/{jobIds}")
	public Result remove(@PathVariable Long[] jobIds) throws SchedulerException, TaskException {
		jobService.deleteJobByIds(jobIds);
		return Result.success();
	}
}
