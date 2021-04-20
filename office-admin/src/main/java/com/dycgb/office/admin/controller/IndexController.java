package com.dycgb.office.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description 后端管理系统控制器
 * @Author myhe
 * @Date 2021/4/6 下午5:53
 */
@Controller
public class IndexController {
    @GetMapping
    public String index() {
        return "blank";
    }
}
