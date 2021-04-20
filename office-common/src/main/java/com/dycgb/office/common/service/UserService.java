package com.dycgb.office.common.service;

import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.User;

import java.util.List;

public interface UserService {
    List<User> findUsersByCategory(Category category);

    List<User> findAllUsers();

    User createUser(User build);

    User finUserById(Long id);
}
