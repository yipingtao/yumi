package com.hh.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hh.entity.RequestHelper;
import com.hh.entity.User;
import com.hh.exportEntity.UsersEntity;
import com.hh.service.IUserService;
import com.hh.utils.*;
import io.swagger.annotations.Api;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 *
 */
@Controller
@RequestMapping("/user")
@Api(value = "用户相关")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;
    @Value("${web.upload-path}")
    private String filePaths;


    @ResponseBody
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public SimpleResultHelp<List<User>> addUser(@RequestBody User user) {
        logger.info("新增用户={}", user);
        Map<String, Object> maps = new HashMap<>();
        if (!CommonUtil.notNull(user.getId()) && CommonUtil.notNull(user.getLoginName())) {
            maps.put("loginName", user.getLoginName());
            if (userService.listByMap(maps).size() > 0) {
                return SimpleResultHelp.result("9999","登录名已存在");
            }
        }
//        Map<String,Object> map2 = new HashMap<>();
//        if(CommonUtil.notNull(user.getCode())){
//            map2.put("code", user.getCode());
//            if(userService.selectByMap(map2).size()>0){
//                simpleResultHelp.setResDesc("工/学号已存在");
//                return simpleResultHelp;
//            }
//        }
        user.setCreateTime(System.currentTimeMillis());
        userService.saveOrUpdate(user);
        Map<String, Object> map = new HashMap<>();
//        map.put("type", user.getType());
        List<User> userList = (List<User>) userService.listByMap(map);
        return SimpleResultHelp.success(userList);
    }

    @ResponseBody
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public SimpleResultHelp<List<User>> editUser(@RequestBody User user) {
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp(null);
        userService.saveOrUpdate(user);
        Map<String, Object> map = new HashMap<>();
        List<User> userList = (List<User>) userService.listByMap(map);
        simpleResultHelp.setData(userList);
        return simpleResultHelp;
    }
    @ResponseBody
    @RequestMapping(value = "/weixinUserLogin", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> weixinUserLogin(@RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginName",user.getLoginName());
        List<User> userList =userService.list(queryWrapper);
        if(userList.size()==0){
            userService.save(user);
        }else {
             User user1 =   userList.get(0);
             user1.setPhoto(user.getPhoto());
            userService.updateById(user1);
        }
        return SimpleResultHelp.result(true);
    }



    @ResponseBody
    @RequestMapping(value = "/registUser", method = RequestMethod.POST)
    public SimpleResultHelp<List<User>> registUser(@RequestBody User user) {
        Map<String, Object> maps = new HashMap<>();
        if (CommonUtil.notNull(user.getLoginName())) {
            maps.put("loginName", user.getLoginName());
        }
        if (userService.listByMap(maps).size() > 0) {
            return new SimpleResultHelp<>(null);
        }
        user.setCreateTime(System.currentTimeMillis());
        userService.saveOrUpdate(user);
        Map<String, Object> map = new HashMap<>();
        List<User> userList = (List<User>) userService.listByMap(map);
        return new SimpleResultHelp(userList);
    }


    @ResponseBody
    @RequestMapping(value = "/delUser/{id}", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> delUser(@PathVariable("id") Integer id) {
        boolean flag = userService.removeById(id);
        return SimpleResultHelp.result(flag);
    }

    @ResponseBody
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public SimpleResultHelp<List<User>> getUser(@RequestBody User user) {
        List<User> userList = (List<User>) userService.listByMap(ClassUtil.setConditionMap(user));
        Collections.sort(userList, Comparator.comparing(User::getCreateTime).reversed());
        return  SimpleResultHelp.success(userList) ;
    }

    @ResponseBody
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public List<User> getUserList() {
        List<User> userList = userService.list(new QueryWrapper<User>().orderByAsc("id"));
        return userList;
    }

    @ResponseBody
    @RequestMapping(value = "/delUserByids", method = RequestMethod.POST)
    public SimpleResultHelp<Boolean> delUser(@RequestBody Map ids) {
        boolean flag = userService.removeByIds((List<String>) (ids.get("ids")));
        return SimpleResultHelp.result(flag);
    }

    @ResponseBody
    @RequestMapping(value = "/getPageUserList", method = RequestMethod.GET)
    public ResultHelp getPageUserList(RequestHelper requestHelper) {
        Page<User> pages = new Page(requestHelper.getPage(), requestHelper.getPageSize());
        Map params = requestHelper.apply();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (CommonUtil.notNull(params.get("name"))) {
            queryWrapper.like(true, "name", params.get("name"));
        }
        IPage<User> page = userService.page(pages, queryWrapper);
        return CommonUtil.getResultHelp(page);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public SimpleResultHelp<List<User>> login(User user) {
        List<User> userList = (List<User>) userService.listByMap(ClassUtil.setConditionMap(user));
        Collections.sort(userList, Comparator.comparing(User::getCreateTime).reversed());
        if (userList.size() == 0) {
            return SimpleResultHelp.result("9999", "用户名或密码错误");
        }
        String JwtString = "";
        try {
            JwtString = JwtUtils.generateToken(user, "", 10);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("生成token失败");
            return SimpleResultHelp.result("9999", "生成token失败");
        } finally {
        }
        System.out.println("token=" + JwtString);
        Map map = new HashMap();
        map.put("token", JwtString);
        map.put("user", userList.get(0));
        return SimpleResultHelp.success(map);
    }

    /*导出*/
    @ResponseBody
    @RequestMapping(value = "/exportUser", method = RequestMethod.GET)
    public void exportUser(HttpServletRequest request, HttpServletResponse response) {
        List<User> userList = userService.list();
        List<UsersEntity> userEntities = ConvertBeanUtils.copyList(userList, UsersEntity.class);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户登录名密码", "用户", ExcelType.XSSF), UsersEntity.class, userEntities);
        CommonUtil.exportCommon(request, response, "用户", workbook);
    }


}

