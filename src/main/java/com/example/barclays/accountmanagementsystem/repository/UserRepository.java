package com.example.barclays.accountmanagementsystem.repository;

import com.example.barclays.accountmanagementsystem.entity.Customer;
import com.example.barclays.accountmanagementsystem.entity.Role;
import com.example.barclays.accountmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserIdAndPasswordAndRole(int id, String password,Role role);
}
