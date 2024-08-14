package com.teambridge.repository;

import com.teambridge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIsDeletedOrderByFirstNameDesc(Boolean isDeleted);

    User findByUserNameAndIsDeleted(String userName, Boolean isDeleted);

    List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String role, Boolean isDeleted);
}
