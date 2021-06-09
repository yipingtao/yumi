package com.hh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hh.entity.Comment;
import com.hh.entity.Ordermain;
import com.hh.entity.RequestHelper;
import com.hh.entity.User;
import com.hh.service.ICommentService;
import com.hh.service.IUserService;
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
@RequestMapping("/comment")
public class CommentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;
    @ResponseBody
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public SimpleResultHelp<List<Comment>> addComment(@RequestBody Comment comment) {
        Map<String, Object> maps = new HashMap<>();
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
        comment.setCreateTime(System.currentTimeMillis());
        commentService.saveOrUpdate(comment);
        QueryWrapper queryWrapper = new QueryWrapper();
        List<Comment> commentList = commentService.list(queryWrapper);
        commentList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        simpleResultHelp.setData(commentList);
        return simpleResultHelp;
    }

    @ResponseBody
    @RequestMapping(value = "/editComment", method = RequestMethod.POST)
    public SimpleResultHelp<List<Comment>> editComment(@RequestBody Comment comment) {
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
        commentService.saveOrUpdate(comment);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", comment.getId());
        List<Comment> commentList = commentService.list(queryWrapper);
        commentList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        simpleResultHelp.setData(commentList);
        return simpleResultHelp;
    }

    @ResponseBody
    @RequestMapping(value = "/delComment", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> delComment(@RequestParam("id") Integer id) {
        return new SimpleResultHelp(commentService.removeById(id));
    }

    @ResponseBody
    @RequestMapping(value = "/getComment", method = RequestMethod.POST)
    public SimpleResultHelp<List<Comment>> getComment(@RequestBody Comment comment) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(ClassUtil.setConditionMap(comment));
        List<Comment> commentList = commentService.list(queryWrapper);
        commentList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        Long currentTime = System.currentTimeMillis();
        Collections.sort(commentList, Comparator.comparing(Comment::getCreateTime).reversed());
        return new SimpleResultHelp(commentList);
    }


    @ResponseBody
    @RequestMapping(value = "/delCommentByids", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> delComment(@RequestBody Map ids) {
        return new SimpleResultHelp(commentService.removeByIds((List<String>) (ids.get("ids"))));
    }

    @ResponseBody
    @RequestMapping(value = "/getPageCommentList", method = RequestMethod.GET)
    public ResultHelp getPagecommentList(RequestHelper requestHelper) {
        Page<Comment> pages = new Page(requestHelper.getPage(), requestHelper.getPageSize());
        Map params = requestHelper.apply();
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(ClassUtil.setConditionMap(params));
        IPage<Comment> page = commentService.page(pages, queryWrapper);
        List<Comment> commentList = pages.getRecords();
        commentList.stream().forEach(it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });
        return CommonUtil.getResultHelp(page);
    }

    @ResponseBody
    @RequestMapping(value = "/saveCommnet", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> saveCommnet(@RequestBody Ordermain ordermain) {
        Comment comment = new Comment();
        comment.setStoreId(ordermain.getStoreId());
        comment.setUserId(ordermain.getUserId());
        comment.setContent(ordermain.getComment());
        comment.setCreateTime(System.currentTimeMillis());
        comment.setOrderId(ordermain.getId());
        commentService.saveOrUpdate(comment);
        return SimpleResultHelp.result(true);
    }


    @ResponseBody
    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    public ResultHelp getAllList(RequestHelper requestHelper) {
        Map params = requestHelper.apply();
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(params,false);
        List<Comment> list = commentService.list(queryWrapper);
        list.stream().forEach( it -> {
            if (CommonUtil.notNull(it.getCreateTime())) {
                it.setCreateTimeDesc(DateUtil.formatTime(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                User user =userService.getById(it.getUserId());
                it.setUser(user);
            }
        });
        Collections.sort(list, Comparator.comparing(Comment::getCreateTime));
        return new ResultHelp(list);
    }
}
