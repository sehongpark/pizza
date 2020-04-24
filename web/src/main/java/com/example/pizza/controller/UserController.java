package com.example.pizza.controller;

import com.example.pizza.dto.UserForm;
import com.example.pizza.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // 회원 가입 창
    @GetMapping("/signup")
    public String signup(UserForm form) {
        return "users/signup";
    }

    // 회원 가입 처리 - https://lalwr.blogspot.com/2018/05/valid-bindingresult.html
    @PostMapping("/users/signup")
    public String create(@Valid UserForm form,   // 입력 폼을 검증한다
                         BindingResult result) { // 검증 결과 객체
        // 비밀번호 불일치 시
        if (!form.getPassword().equals(form.getConfirm())) {
            log.info(String.format("PW(%s), CF(%s)", form.getPassword(), form.getConfirm()));
            FieldError confirmError = new FieldError("form", "confirm", "비밀번호가 일치하지 않습니다.");
            result.addError(confirmError);
        }

        // 폼 검증 실패 시,
        if (result.hasErrors()) {
            return "users/signup"; // 검증 실패 시, 가입 폼으로 이동
        }

        // 회원 가입
        userService.join(form);

        // 리다이렉트
        return "redirect:/signin";
    }

    // 로그인 창
    @GetMapping("/signin")
    public String signin() {
        return "users/signin";
    }
}