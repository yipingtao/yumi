package com.hh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hh.entity.*;
import com.hh.entity.requestVo.GoodsRequest;
import com.hh.entity.requestVo.OrderRequest;
import com.hh.service.IGoodsService;
import com.hh.service.IOrdermainService;
import com.hh.service.IOrdersubService;
import com.hh.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@Controller
@RequestMapping("/ordermain")
public class OrdermainController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IOrdermainService ordermainService;
    @Autowired
    private IOrdersubService ordersubService;
    @Autowired
    private IGoodsService goodsService;
    @ResponseBody
    @RequestMapping(value = "/addOrdermain", method = RequestMethod.POST)
    public SimpleResultHelp<List<Ordermain>> addOrdermain(@RequestBody Ordermain ordermain) {
        Map<String, Object> maps = new HashMap<>();
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
        ordermain.setCreateTime(System.currentTimeMillis());
        ordermainService.saveOrUpdate(ordermain);
        QueryWrapper queryWrapper = new QueryWrapper();
        List<Ordermain> ordermainList = ordermainService.list(queryWrapper);
        ordermainList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        simpleResultHelp.setData(ordermainList);
        return simpleResultHelp;
    }

    @ResponseBody
    @RequestMapping(value = "/editOrdermain", method = RequestMethod.POST)
    public SimpleResultHelp<List<Ordermain>> editOrdermain(@RequestBody Ordermain ordermain) {
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
        ordermainService.saveOrUpdate(ordermain);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", ordermain.getId());
        List<Ordermain> ordermainList = ordermainService.list(queryWrapper);
        ordermainList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        simpleResultHelp.setData(ordermainList);
        return simpleResultHelp;
    }

    @ResponseBody
    @RequestMapping(value = "/delOrdermain/{id}", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> delOrdermain(@PathVariable("id") Integer id) {
        return new SimpleResultHelp(ordermainService.removeById(id));
    }

    @ResponseBody
    @RequestMapping(value = "/getOrdermain", method = RequestMethod.POST)
    public SimpleResultHelp<List<Ordermain>> getOrdermain(@RequestBody Ordermain ordermain) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(ClassUtil.setConditionMap(ordermain));
        List<Ordermain> ordermainList = ordermainService.list(queryWrapper);
        ordermainList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        Long currentTime = System.currentTimeMillis();
        Collections.sort(ordermainList, Comparator.comparing(Ordermain::getCreateTime).reversed());
        return new SimpleResultHelp(ordermainList);
    }


    @ResponseBody
    @RequestMapping(value = "/delOrdermainByids", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> delOrdermain(@RequestBody Map ids) {
        return new SimpleResultHelp(ordermainService.removeByIds((List<String>) (ids.get("ids"))));
    }

    @ResponseBody
    @RequestMapping(value = "/getPageOrdermainList", method = RequestMethod.GET)
    public ResultHelp getPageordermainList(RequestHelper requestHelper) {
        Page<Ordermain> pages = new Page(requestHelper.getPage(), requestHelper.getPageSize());
        Map params = requestHelper.apply();
        QueryWrapper<Ordermain> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(params,false);
        IPage<Ordermain> page = ordermainService.page(pages, queryWrapper);
        List<Ordermain> ordermainList = pages.getRecords();
        ordermainList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        return CommonUtil.getResultHelp(page);
    }


    @ResponseBody
    @RequestMapping(value = "/changeState", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> changeState(@RequestBody Ordermain ordermain) {
        return new SimpleResultHelp(ordermainService.updateById(ordermain));
    }


    @ResponseBody
    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    @Transactional
    public SimpleResultHelp saveOrder(@RequestBody OrderRequest orderRequest) {
        Long currentTime = System.currentTimeMillis();
        Ordermain ordermain =  ConvertBeanUtils.convertBean(orderRequest,Ordermain.class);
        ordermain.setCode("DJ"+System.currentTimeMillis());
        ordermain.setCreateTime(currentTime);
        ordermain.setIsActive(1);
        ordermainService.saveOrUpdate(ordermain);
        for (GoodsRequest item: orderRequest.getGoodsList()) {
            Ordersub ordersub = new Ordersub();
            ordersub.setGoodsId(item.getId());
            ordersub.setMainId(ordermain.getId());
            ordersub.setAmount(item.getAmount());
            ordersub.setCreateTime(currentTime);
            ordersub.setMoney(item.getMoney());
            ordersub.setGoodName(item.getName());
            ordersubService.saveOrUpdate(ordersub);
        }
        return SimpleResultHelp.success(null);
    }

    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @ResponseBody
    public ResultHelp getAllList(RequestHelper requestHelper) {
        Map params = requestHelper.apply();
        QueryWrapper<Ordermain> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(params,false);
        List<Ordermain> list = ordermainService.list(queryWrapper);
        QueryWrapper<Goods> goodsWrapper = new QueryWrapper<>();
        List<Goods> goodsList = goodsService.list(goodsWrapper);

        QueryWrapper<Ordersub> squeryWrapper = new QueryWrapper<>();
        List<Ordersub> ordersubList = ordersubService.list(squeryWrapper);
        ordersubList.stream().forEach(
                it->{
                    Goods goods = goodsList.stream().filter(e->e.getId() == it.getGoodsId()).findFirst().get();
                    it.setGoods(goods);
                }
        );
        list.stream().forEach( it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                List<Ordersub> subList = ordersubList.stream().filter(e->e.getMainId()==it.getId()).collect(Collectors.toList());
                it.setOrdersubList(subList);
            }
        });
        Collections.sort(list, Comparator.comparing(Ordermain::getCreateTime).reversed());
        return new ResultHelp(list);
    }
}
