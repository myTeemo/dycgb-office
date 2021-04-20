package com.dycgb.office.admin.controller;

import com.dycgb.office.common.exception.ResourceAlreadyExistException;
import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.PaymentType;
import com.dycgb.office.common.service.PaymentTypeService;
import com.dycgb.office.common.utils.CustomResponse;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 付款类型控制器
 * @Author myhe
 * @Date 2021/4/3 上午9:04
 */
@Controller
@RequestMapping("/paymentType")
public class PaymentTypeController {
    @Resource
    private PaymentTypeService paymentTypeService;

    /**
     * 根据ID查询付款类型
     *
     * @param id 付款类型ID
     */
    @GetMapping("/{id}")
    @ResponseBody
    public CustomResponse findPaymentTypeById(@PathVariable("id") Long id) {
        try {
            PaymentType paymentType = paymentTypeService.findPaymentTypeById(id);
            return CustomResponse.OK(ErrorCodeEnum.PAYMENT_TYPE_QUERY_OK, paymentType);
        } catch (ResourceNotFoundException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    /**
     * 查询所有付款类型信息
     */
    @GetMapping
    @ResponseBody
    public CustomResponse findPaymentTypes() {
        List<PaymentType> paymentTypes = paymentTypeService.findAllPaymentTypes();
        return CustomResponse.OK(ErrorCodeEnum.PAYMENT_TYPE_QUERY_OK, paymentTypes);
    }

    /**
     * 新增付款类型
     *
     * @param paymentType 付款类型
     */
    @PostMapping
    @ResponseBody
    public CustomResponse createPaymentType(PaymentType paymentType) {
        try {
            PaymentType createdPaymentType = paymentTypeService.createPaymentType(paymentType);
            return CustomResponse.OK(ErrorCodeEnum.PAYMENT_TYPE_CREATE_OK, createdPaymentType);
        } catch (ResourceAlreadyExistException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    /**
     * 更新付款类型
     *
     * @param paymentType 付款类型
     */
    @PutMapping
    @ResponseBody
    public CustomResponse updatePaymentType(PaymentType paymentType) {
        try {
            PaymentType updatePaymentType = paymentTypeService.updatePaymentType(paymentType);
            return CustomResponse.OK(ErrorCodeEnum.PAYMENT_TYPE_UPDATE_OK, updatePaymentType);
        } catch (NullPointerException ne) {
            return CustomResponse.FAILED(ne.getMessage());
        } catch (ResourceNotFoundException | ResourceAlreadyExistException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    /**
     * 根据ID删除付款类型
     *
     * @param id 类型ID
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public CustomResponse deletePaymentTypeById(@PathVariable("id") Long id) {
        try {
            paymentTypeService.deletePaymentTypeById(id);
            return CustomResponse.OK(ErrorCodeEnum.PAYMENT_TYPE_DELETE_OK);
        } catch (ResourceNotFoundException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }
}
