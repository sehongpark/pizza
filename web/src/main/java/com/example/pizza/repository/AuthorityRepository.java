package com.example.pizza.repository;

import com.example.pizza.vo.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    // 사용자 id로 모든 권한을 가져옴
    List<Authority> findByUserId(Integer id);
}
