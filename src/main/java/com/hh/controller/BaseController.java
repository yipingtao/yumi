package com.hh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.entity.User;
import com.hh.utils.RequestHelp;
import com.hh.utils.ResultHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
public class BaseController<M extends IService, T> {

    @Autowired
    public M service;

    /*查询分页*/
    @GetMapping(value = "/list")
    @ResponseBody
    ResultHelp list(RequestHelp requestHelper) {
        Page page = search(requestHelper);
        return getResultHelp(page);
    }

    /*查询所有数据*/
    @GetMapping(value = "/allList")
    @ResponseBody
    List<T> allList(RequestHelp requestHelper) {
        QueryWrapper wrapper = new QueryWrapper();
        List<T> list = service.list(wrapper);
        return list;
    }

    /**
     * 详情展示
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id:\\d+}/detail")
    @ResponseBody
    ResultHelp<T, Void> detail(@PathVariable("id") Integer id) {
        Object entity = service.getById(id);
        return new ResultHelp(entity);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    ResultHelp<T, Void> save (@RequestBody User entity) {
        boolean b = service.saveOrUpdate(entity);
        String resCode = b == true ? "0000" : "9999";
        return new ResultHelp(resCode);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id:\\d+}")
    @ResponseBody
    ResultHelp<T, Void> del(@PathVariable("id") Integer id) {
        boolean b = service.removeById(id);
        String resCode = b == true ? "0000" : "9999";
        return new ResultHelp(resCode);
    }

    Page search(RequestHelp requestHelper) {
        Page page = getPage(requestHelper);
        //查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        search(page, queryWrapper);
        return page;
    }

    Page search(Page page, QueryWrapper queryWrapper) {
        service.page(page, queryWrapper);
        return page;
    }

    /**
     * 获取翻页条件
     *
     * @param requestHelp
     * @return
     */
    Page getPage(RequestHelp requestHelp) {
        Page page = new Page(requestHelp.getPage(), requestHelp.getPageSize());
        if (requestHelp.getSortBy().trim().toLowerCase().equals("desc")) {
            page.setAsc(requestHelp.getSortField());
        }
        return page;
    }

    public ResultHelp getResultHelp(Page page) {
        ResultHelp resultHelp = new ResultHelp();
        resultHelp.setResCode("0000");
        resultHelp.setPageCount((int) page.getPages());
        resultHelp.setPage((int) page.getCurrent());
        resultHelp.setResultList(page.getRecords());
        resultHelp.setResultCount((int) page.getTotal());
        resultHelp.setPageSize((int) page.getSize());
        return resultHelp;
    }

}
