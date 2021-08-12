package com.example.marketplace.mapper;

import com.example.marketplace.model.User;
import com.example.marketplace.payload.response.RegistrationResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findByUsername(String username);

    User findById(Long userId);

    Optional<User> findByEmail(String email);

    RegistrationResponse save(User user);
}
