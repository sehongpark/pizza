package com.example.pizza.service;

import com.example.pizza.dto.UserForm;
import com.example.pizza.repository.AuthorityRepository;
import com.example.pizza.repository.UserRepository;
import com.example.pizza.type.Role;
import com.example.pizza.vo.Authority;
import com.example.pizza.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Transactional
    public Integer join(UserForm userForm) {
        // 비밀번호 암호화
        String encoded = new BCryptPasswordEncoder().encode(userForm.getPassword());
        userForm.setPassword(encoded);
        log.info(userForm.toString());

        // VO 생성
        UserVO userVO = userForm.toEntity();
        log.info(userVO.toString());

        List<Authority> authorities = userVO.getAuthorities();
        if ("admin@email.com".equals(userVO.getEmail())) {
            authorities.add(new Authority(null, "ROLE_ADMIN", null));
        }
        log.info(authorities.toString());

        // DB 저장
        List<Authority> savedAuthorities = authorityRepository.saveAll(authorities);
        log.info(savedAuthorities.toString());

        return userRepository.save(userVO).getId();
    }

    @Override
//    @Transactional // 트랜잭션 안걸면 에러.. 왜그럴까?
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        // 이메일 확인
//        log.info(userEmail.toString());

        // 사용자 획득
        UserVO userVO = userRepository.findByEmail(userEmail).orElse(null);
        log.info(userVO.toString());

        return userVO;
    }
}
