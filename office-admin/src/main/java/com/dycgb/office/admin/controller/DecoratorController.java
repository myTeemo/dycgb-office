package com.dycgb.office.admin.controller;

import com.dycgb.office.common.model.PaymentType;
import com.dycgb.office.common.service.PaymentTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * SiteMesh装饰器控制器
 *
 * @author MY-HE
 * @date 2019-11-19 15:04
 */
@Controller
@RequestMapping("/decorators")
public class DecoratorController {
    @Resource
    private PaymentTypeService paymentTypeService;

    @RequestMapping("/decorator")
    public String defaultDecorator(Model model) {
        List<PaymentType> paymentTypes = paymentTypeService.findAllPaymentTypes();
        model.addAttribute("paymentTypes", paymentTypes);
        return "/decorators/decorator";
    }
}
