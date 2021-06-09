package com.hh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hh.entity.RequestHelper;
import com.hh.entity.Store;
import com.hh.service.IStoreService;
import com.hh.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@Controller
@RequestMapping("/store")
public class StoreController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IStoreService storeService;

    @ResponseBody
    @RequestMapping(value = "/addStore", method = RequestMethod.POST)
    public SimpleResultHelp<List<Store>> addStore(@RequestBody Store store) {
        Map<String, Object> maps = new HashMap<>();
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
        store.setCreateTime(System.currentTimeMillis());
        storeService.saveOrUpdate(store);
        QueryWrapper queryWrapper = new QueryWrapper();
        List<Store> storeList = storeService.list(queryWrapper);
        storeList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        simpleResultHelp.setData(storeList);
        return simpleResultHelp;
    }

    @ResponseBody
    @RequestMapping(value = "/editStore", method = RequestMethod.POST)
    public SimpleResultHelp<List<Store>> editStore(@RequestBody Store store) {
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
        storeService.saveOrUpdate(store);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", store.getId());
        List<Store> storeList = storeService.list(queryWrapper);
        storeList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        simpleResultHelp.setData(storeList);
        return simpleResultHelp;
    }

    @ResponseBody
    @RequestMapping(value = "/delStore/{id}", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> delStore(@PathVariable("id") Integer id) {
        return new SimpleResultHelp(storeService.removeById(id));
    }

    @ResponseBody
    @RequestMapping(value = "/getStore", method = RequestMethod.POST)
    public SimpleResultHelp<List<Store>> getStore(@RequestBody Store store) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(ClassUtil.setConditionMap(store));
        List<Store> storeList = storeService.list(queryWrapper);
        storeList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        Long currentTime = System.currentTimeMillis();
        Collections.sort(storeList, Comparator.comparing(Store::getCreateTime).reversed());
        return new SimpleResultHelp(storeList);
    }


    @ResponseBody
    @RequestMapping(value = "/delStoreByids", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> delStore(@RequestBody Map ids) {
        return new SimpleResultHelp(storeService.removeByIds((List<String>) (ids.get("ids"))));
    }

    @ResponseBody
    @RequestMapping(value = "/getPageStoreList", method = RequestMethod.GET)
    public ResultHelp getPagestoreList(RequestHelper requestHelper) {
        Page<Store> pages = new Page(requestHelper.getPage(), requestHelper.getPageSize());
        Map params = requestHelper.apply();
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(params,false);
        IPage<Store> page = storeService.page(pages, queryWrapper);
        List<Store> storeList = pages.getRecords();
        storeList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        return CommonUtil.getResultHelp(page);
    }


    @ResponseBody
    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    public ResultHelp getAllList(RequestHelper requestHelper) {
        Map params = requestHelper.apply();
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        if (CommonUtil.notNull(params.get("name"))) {
            queryWrapper.like(true, "name", params.get("name"));
        }
        List<Store> storeList = storeService.list(queryWrapper);
        return new ResultHelp(storeList);
    }
}
