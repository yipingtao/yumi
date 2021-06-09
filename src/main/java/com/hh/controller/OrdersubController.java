package com.hh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hh.entity.Ordersub;
import com.hh.entity.RequestHelper;
import com.hh.service.IOrdersubService;
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
    *  前端控制器
    * </p>
*
*/
    @Controller
@RequestMapping("/ordersub")
public class OrdersubController {
final Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private IOrdersubService ordersubService;

@ResponseBody
@RequestMapping(value ="/addOrdersub", method = RequestMethod.POST)
public SimpleResultHelp <List<Ordersub>> addOrdersub(@RequestBody Ordersub ordersub) {
    Map<String, Object> maps = new HashMap<>();
    SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
    ordersub.setCreateTime(System.currentTimeMillis());
    ordersubService.saveOrUpdate(ordersub);
    QueryWrapper queryWrapper =  new QueryWrapper();
    List<Ordersub> ordersubList  = ordersubService.list(queryWrapper);
    ordersubList.stream().forEach(it -> {
    if (CommonUtil.notNull(it.getCreateTime())) {
    it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
    }
    });
    simpleResultHelp.setData(ordersubList );
    return simpleResultHelp;
    }
        @ResponseBody
        @RequestMapping(value ="/editOrdersub", method = RequestMethod.POST)
        public SimpleResultHelp<List<Ordersub>> editOrdersub(@RequestBody Ordersub ordersub) {
            SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
            ordersubService.saveOrUpdate(ordersub);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id",ordersub.getId());
            List<Ordersub> ordersubList  = ordersubService.list(queryWrapper);
            ordersubList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
            it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            }
            });
                simpleResultHelp.setData(ordersubList );
                return simpleResultHelp;
                }

       @ResponseBody
       @RequestMapping(value ="/delOrdersub", method = RequestMethod.POST)
       public SimpleResultHelp<Boolean> delOrdersub(@RequestParam("id") Integer id) {
           return new SimpleResultHelp(ordersubService.removeById(id));
           }

        @ResponseBody
        @RequestMapping(value ="/getOrdersub", method = RequestMethod.POST)
        public SimpleResultHelp<List<Ordersub>> getOrdersub(@RequestBody Ordersub ordersub) {
        QueryWrapper queryWrapper = new QueryWrapper();
       queryWrapper.allEq(ClassUtil.setConditionMap(ordersub));
        List<Ordersub> ordersubList  = ordersubService.list(queryWrapper);
        ordersubList.stream().forEach(it -> {
        if (CommonUtil.notNull(it.getCreateTime())) {
        it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
        }
        });
        Long currentTime = System.currentTimeMillis();
        Collections.sort(ordersubList , Comparator.comparing(Ordersub::getCreateTime).reversed());
        return new SimpleResultHelp(ordersubList );
        }


        @ResponseBody
        @RequestMapping(value ="/delOrdersubByids", method = RequestMethod.POST)
        public SimpleResultHelp<Boolean> delOrdersub(@RequestBody Map ids) {
            return new SimpleResultHelp(ordersubService.removeByIds((List<String>) (ids.get("ids"))));
                }

                @ResponseBody
                @RequestMapping(value ="/getPageOrdersubList", method = RequestMethod.GET)
                public ResultHelp getPageordersubList (RequestHelper requestHelper) {
               Page<Ordersub> pages = new Page(requestHelper.getPage(), requestHelper.getPageSize());
                Map params = requestHelper.apply();
                QueryWrapper<Ordersub> queryWrapper = new QueryWrapper<>();
                queryWrapper.allEq(ClassUtil.setConditionMap(params));
                IPage<Ordersub> page = ordersubService.page(pages, queryWrapper);
                List<Ordersub> ordersubList = pages.getRecords();
                ordersubList.stream().forEach(it -> {
                if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                }
                 });
                 return CommonUtil.getResultHelp(page);
                }
                }
