package com.dycgb.office.admin.controller;

import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.User;
import com.dycgb.office.common.service.UserService;
import com.dycgb.office.common.utils.CustomResponse;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 用户控制器
 * @Author myhe
 * @Date 2021/4/8 上午10:27
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 根据类别查询用户信息
     *
     * @param cid 类别ID
     */
    @GetMapping
    @ResponseBody
    private CustomResponse findUsersByCategory(@RequestParam("category_id") Long cid) {
        Category category = new Category();
        category.setId(cid);
        List<User> users = userService.findUsersByCategory(category);
        return CustomResponse.OK(ErrorCodeEnum.USER_QUERY_OK, users);
    }
}
