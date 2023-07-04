package com.sparta.springlv2project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RenderController {
    @GetMapping("/board")
    public void test(){
        System.out.println("로그인 성공. 게시판 페이지 html 렌더링");
    }
    @GetMapping("/user/login-page")
    public void test2(){
        System.out.println("로그인 페이지 html 렌더링");
    }
}
