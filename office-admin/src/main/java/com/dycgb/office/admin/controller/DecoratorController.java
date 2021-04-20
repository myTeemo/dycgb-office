package com.dycgb.office.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SiteMesh装饰器控制器
 *
 * @author MY-HE
 * @date 2019-11-19 15:04
 */
@Controller
@RequestMapping("/decorators")
public class DecoratorController {
    @RequestMapping("/decorator")
    public String defaultDecorator(){
        return "/decorators/decorator";
    }
}
