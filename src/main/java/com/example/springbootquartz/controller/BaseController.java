package com.example.springbootquartz.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springbootquartz.domain.PageDomain;
import com.example.springbootquartz.domain.Result;
import com.example.springbootquartz.domain.TableDataInfo;
import com.example.springbootquartz.domain.TableSupport;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtil.parseDateTime(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (ObjectUtil.isNotNull(pageNum) && ObjectUtil.isNotNull(pageSize)) {
            String orderBy = pageDomain.getOrderBy();
            // 检测orderBy是否合法
            String sqlPattern = "[a-zA-Z0-9_\\ \\,\\.]+";
            if (StrUtil.isNotBlank(orderBy) && !orderBy.matches(sqlPattern)) {
                throw new IllegalArgumentException("参数不符合规范，不能进行查询");
            }
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.OK.value());
        rspData.setRows(list);
        rspData.setMsg("查询成功");
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list,int total) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.OK.value());
        rspData.setRows(list);
        rspData.setMsg("查询成功");
        rspData.setTotal(total);
        return rspData;
    }

    protected <T> TableDataInfo getDataTable(List<?> list, Class<T> targetType) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.OK.value());
        rspData.setMsg("查询成功");
        rspData.setTotal(new PageInfo(list).getTotal());

        List<T> list4Target= BeanUtil.copyToList(list,targetType);
        rspData.setRows(list4Target);
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected Result<?> toResult(int rows) {
        return rows > 0 ? Result.success() : Result.error("");
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected Result<?> toResult(boolean result) {
        return result ? Result.success() : Result.error("");
    }

    /**
     * 返回成功消息
     */
    public Result<?> success(String message) {
        return Result.success(message);
    }

    /**
     * 返回失败消息
     */
    public Result<?> error(String message) {
        return Result.error(message);
    }
}
