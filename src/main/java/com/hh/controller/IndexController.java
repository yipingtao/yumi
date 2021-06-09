package com.hh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */

@Controller
@RequestMapping("/home")
public class IndexController {
    @RequestMapping("/{page}.html")
    public String sindex(@PathVariable("page") String page) {
        return "home/" + page;
    }
}
