package com.dycgb.office.common.repository;

import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByCategory(Category category);

    List<User> findAll();

    Optional<User> findUserById(Long id);
}
