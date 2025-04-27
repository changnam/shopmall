package com.honsoft.shopmall.controller;

import java.util.Enumeration;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

//@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model m) {
        // 여기에 Thymeleaf 에러 포함 모든 에러가 온다
        Object status = request.getAttribute("jakarta.servlet.error.status_code");
        Object exception = request.getAttribute("jakarta.servlet.error.exception");
//        Enumeration<String> attrNames = request.getAttributeNames();
//        while(attrNames.hasMoreElements()) {
//        	System.out.println("attrName: "+attrNames.nextElement());
//        }
        //System.out.println("에러 상태 코드: " + status);
        //System.out.println("예외 정보: " + exception);

        // 필요하면 상태코드별로 다른 뷰 리턴 가능
//        return "error"; // templates/error.html 보여주기
        
        m.addAttribute("status", status.toString());
        m.addAttribute("error", "error");
        m.addAttribute("message", ((Exception)exception).getMessage());
		return "errorPage";
    }
}
