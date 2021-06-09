package com.hh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hh.entity.Goods;
import com.hh.entity.RequestHelper;
import com.hh.entity.Store;
import com.hh.service.IGoodsService;
import com.hh.service.IStoreService;
import com.hh.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 *
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IStoreService storeService;
    @ResponseBody
    @RequestMapping(value = "/addGoods", method = RequestMethod.POST)
    public SimpleResultHelp<List<Goods>> addGoods(@RequestBody Goods goods) {
        Map<String, Object> maps = new HashMap<>();
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
        goods.setCreateTime(System.currentTimeMillis());
        goodsService.saveOrUpdate(goods);
        QueryWrapper queryWrapper = new QueryWrapper();
        List<Goods> goodsList = goodsService.list(queryWrapper);
        goodsList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        simpleResultHelp.setData(goodsList);
        return simpleResultHelp;
    }

    @ResponseBody
    @RequestMapping(value = "/editGoods", method = RequestMethod.POST)
    public SimpleResultHelp<List<Goods>> editGoods(@RequestBody Goods goods) {
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
        goodsService.saveOrUpdate(goods);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", goods.getId());
        List<Goods> goodsList = goodsService.list(queryWrapper);
        goodsList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        simpleResultHelp.setData(goodsList);
        return simpleResultHelp;
    }

    @ResponseBody
    @RequestMapping(value = "/delGoods ", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> delGoods(@RequestParam("id") Integer id) {
        return new SimpleResultHelp(goodsService.removeById(id));
    }

    @ResponseBody
    @RequestMapping(value = "/getGoods", method = RequestMethod.POST)
    public SimpleResultHelp<List<Goods>> getGoods(@RequestBody Goods goods) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(ClassUtil.setConditionMap(goods));
        List<Goods> goodsList = goodsService.list(queryWrapper);
        goodsList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        Long currentTime = System.currentTimeMillis();
        Collections.sort(goodsList, Comparator.comparing(Goods::getCreateTime).reversed());
        return new SimpleResultHelp(goodsList);
    }


    @ResponseBody
    @RequestMapping(value = "/delGoodsByids", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> delGoods(@RequestBody Map ids) {
        return new SimpleResultHelp(goodsService.removeByIds((List<String>) (ids.get("ids"))));
    }

    @ResponseBody
    @RequestMapping(value = "/getPageGoodsList", method = RequestMethod.GET)
    public ResultHelp getPagegoodsList(RequestHelper requestHelper) {
        Page<Goods> pages = new Page(requestHelper.getPage(), requestHelper.getPageSize());
        Map params = requestHelper.apply();
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        queryWrapper.allEq(ClassUtil.setConditionMap(params));
        if (CommonUtil.notNull(params.get("name"))) {
            queryWrapper.like(true, "name", params.get("name"));
        }
        IPage<Goods> page = goodsService.page(pages, queryWrapper);
        List<Goods> goodsList = pages.getRecords();
        QueryWrapper<Store> squeryWrapper = new QueryWrapper<>();
        squeryWrapper.eq("isActive",1);
        List<Store> storeList = storeService.list(squeryWrapper);

        goodsList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
         String storeName = storeList.stream().filter(e->e.getId()==it.getStoreId()).findFirst().get().getName();
            it.setStoreName(storeName);
        });
        return CommonUtil.getResultHelp(page);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    public ResultHelp getAllList(RequestHelper requestHelper) {
        Map params = requestHelper.apply();
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(params,false);
        List<Goods> list = goodsService.list(queryWrapper);
        return new ResultHelp(list);
    }
}
