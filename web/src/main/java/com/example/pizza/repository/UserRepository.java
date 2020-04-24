package com.example.pizza.repository;

import com.example.pizza.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserVO, Integer> {
    // 유저를 email로 검색
    Optional<UserVO> findByEmail(String email);
}
