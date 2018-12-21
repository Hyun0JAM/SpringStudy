package com.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.service.MybatisTestService;

@Controller
public class TempController {

	@Autowired
	MybatisTestService service;
	
	@RequestMapping(value="/abc.action",method={RequestMethod.GET})
	public String MybatisTest1(HttpServletRequest req){
		return "home";
	}
}
