package com.sparta.springlv2project.filter;

import com.sparta.springlv2project.entity.User;
import com.sparta.springlv2project.jwt.JwtUtil;
import com.sparta.springlv2project.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@Order(1)
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String url = httpServletRequest.getRequestURI();
        if (url.equals("/") || (StringUtils.hasText(url) && url.startsWith("/user"))) {
            log.info("인증 처리를 하지 않는 url = " + url);
            // 회원가입, 로그인 관련 API는 인증이 필요 없이 요청 진행
            chain.doFilter(request, response); // 다음 Filter로 이동
        } else {
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);
            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring
                String token = jwtUtil.substringToken(tokenValue);

                // 토큰 검증
                if (!jwtUtil.validateToken(token)) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    statusMsg(httpServletResponse, "토큰 오류");
                    return;
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);
                User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                        new NullPointerException("Not Found User"));
                request.setAttribute("user", user);
                chain.doFilter(request, response); // 다음 Filter로 이동
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                statusMsg(httpServletResponse, "토큰 없어");
            }
        }
    }
    private void statusMsg(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = "{\"statusCode\":\"" + HttpServletResponse.SC_BAD_REQUEST + "\", \"message\":\"" + message + "\"}";
        response.getWriter().write(json);
    }

}