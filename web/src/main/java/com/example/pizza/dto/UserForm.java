package com.example.pizza.dto;

import com.example.pizza.type.Role;
import com.example.pizza.vo.Authority;
import com.example.pizza.vo.UserVO;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserForm {
    private Integer id;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "영문/숫자/특수기호를 적어도 1개 이상 씩 포함한, 8~20자 비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "비밀번호를 다시 입력해주세요.")
    private String confirm;

    public UserVO toEntity() {
        // 기본 권한 USER로 설정
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(null, Role.USER.getValue(), null));

        return new UserVO(null, email, password, 1, authorities);
    }
}
