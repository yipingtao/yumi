package com.hh.controller;


import com.hh.entity.User;
import com.hh.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 */
@Controller
@RequestMapping("/userNew")
public class UserNewController extends BaseController<IUserService,User>{

}

