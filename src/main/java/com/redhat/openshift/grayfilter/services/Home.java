package com.redhat.openshift.grayfilter.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Home {

	public Home() {

	}

	@RequestMapping(method = RequestMethod.GET)
	public String applyGrayFilter() {
		return "Welcome";
	}

}