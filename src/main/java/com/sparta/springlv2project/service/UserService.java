package com.sparta.springlv2project.service;

import com.sparta.springlv2project.dto.userdto.LoginRequestDto;
import com.sparta.springlv2project.dto.userdto.SignupRequestDto;
import com.sparta.springlv2project.entity.User;
import com.sparta.springlv2project.jwt.JwtUtil;
import com.sparta.springlv2project.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // 이름과 비밀번호 양식
        String nameVal= "^[a-z0-9]{4,10}$";
        String passVal="^[a-zA-Z0-9]{8,15}$";
        if(!Pattern.matches(nameVal, username)) throw new IllegalArgumentException("이름이 올바르지 않습니다.");
        if(!Pattern.matches(passVal, requestDto.getPassword())) throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        // 사용자 등록
        User user = new User(username, password);
        userRepository.save(user);
    }

    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username= requestDto.getUsername();
        String password =requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                ()->new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");

        }
        String token = jwtUtil.createToken(user.getUsername());
        jwtUtil.addJwtToCookie(token, res);
    }
}
