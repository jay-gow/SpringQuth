package com.jaya.SpringSecEx;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
	@RequestMapping("/")
	public String greet(HttpServletRequest request) {
		return "welcome chellam"+request.getSession().getId();
	}

}
