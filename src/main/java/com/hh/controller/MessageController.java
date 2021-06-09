package com.hh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hh.entity.Message;
import com.hh.entity.RequestHelper;
import com.hh.service.IMessageService;
import com.hh.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
* <p>
    *  前端控制器
    * </p>
*
*/
    @Controller
@RequestMapping("/message")
public class MessageController {
final Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private IMessageService messageService;

@ResponseBody
@RequestMapping(value ="/addMessage", method = RequestMethod.POST)
public SimpleResultHelp <List<Message>> addMessage(@RequestBody Message message) {
    Map<String, Object> maps = new HashMap<>();
    SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
    message.setCreateTime(System.currentTimeMillis());
    messageService.saveOrUpdate(message);
    QueryWrapper queryWrapper =  new QueryWrapper();
    List<Message> messageList  = messageService.list(queryWrapper);
    messageList.stream().forEach(it -> {
    if (CommonUtil.notNull(it.getCreateTime())) {
    it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
    }
    });
    simpleResultHelp.setData(messageList );
    return simpleResultHelp;
    }
        @ResponseBody
        @RequestMapping(value ="/editMessage", method = RequestMethod.POST)
        public SimpleResultHelp<List<Message>> editMessage(@RequestBody Message message) {
            SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
            messageService.saveOrUpdate(message);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id",message.getId());
            List<Message> messageList  = messageService.list(queryWrapper);
            messageList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
            it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            }
            });
                simpleResultHelp.setData(messageList );
                return simpleResultHelp;
                }

       @ResponseBody
       @RequestMapping(value ="/delMessage/{id}", method = RequestMethod.POST)
       public SimpleResultHelp<Boolean> delMessage(@PathVariable("id") Integer id) {
           return new SimpleResultHelp(messageService.removeById(id));
           }

        @ResponseBody
        @RequestMapping(value ="/getMessage", method = RequestMethod.POST)
        public SimpleResultHelp<List<Message>> getMessage(@RequestBody Message message) {
        QueryWrapper queryWrapper = new QueryWrapper();
       queryWrapper.allEq(ClassUtil.setConditionMap(message));
        List<Message> messageList  = messageService.list(queryWrapper);
        messageList.stream().forEach(it -> {
        if (CommonUtil.notNull(it.getCreateTime())) {
        it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
        }
        });
        Long currentTime = System.currentTimeMillis();
        Collections.sort(messageList , Comparator.comparing(Message::getCreateTime).reversed());
        return new SimpleResultHelp(messageList );
        }


        @ResponseBody
        @RequestMapping(value ="/delMessageByids", method = RequestMethod.POST)
        public SimpleResultHelp<Boolean> delMessage(@RequestBody Map ids) {
            return new SimpleResultHelp(messageService.removeByIds((List<String>) (ids.get("ids"))));
                }

        @ResponseBody
        @RequestMapping(value ="/getPageMessageList", method = RequestMethod.GET)
        public ResultHelp getPagemessageList (RequestHelper requestHelper) {
       Page<Message> pages = new Page(requestHelper.getPage(), requestHelper.getPageSize());
        Map params = requestHelper.apply();
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(params,false);
        IPage<Message> page = messageService.page(pages, queryWrapper);
        List<Message> messageList = pages.getRecords();
        messageList.stream().forEach(it -> {
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
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(ClassUtil.setConditionMap(params),false);
        List<Message> messageList = messageService.list(queryWrapper);
        return new ResultHelp(messageList);
    }

    @ResponseBody
    @RequestMapping(value = "/changeState", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> changeState(@RequestBody Message message) {
        return new SimpleResultHelp(messageService.updateById(message));
    }
 }
