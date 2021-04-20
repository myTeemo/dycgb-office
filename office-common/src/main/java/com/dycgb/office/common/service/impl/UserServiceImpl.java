package com.dycgb.office.common.service.impl;

import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.User;
import com.dycgb.office.common.repository.UserRepository;
import com.dycgb.office.common.service.UserService;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Description 用户业务逻辑处理实现类
 * @Author myhe
 * @Date 2021/4/8 上午10:23
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    /**
     * 根据类别查询用户
     *
     * @param category 类别
     */
    @Override
    public List<User> findUsersByCategory(Category category) {
        return userRepository.findUsersByCategory(category);
    }

    /**
     * 查询所有用户
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 创建一个 用户
     *
     * @param user 用户
     */
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     */
    @Override
    public User finUserById(@NonNull Long id) {
        Optional<User> oUser = userRepository.findUserById(id);

        if (oUser.isPresent()) {
            return oUser.get();
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.USER_QUERY_FAILED_NOT_FOUND);
    }
}
