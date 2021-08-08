package com.assessment.code.biometricmatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(value = "biometric", tags = {"biometric Hello Endpoints"})
@RequestMapping("/biometric/hello")
public class HelloController {
	
	 @GetMapping("{name}")
	 @ApiOperation(value = "Hello name",
     notes = "Returns a 200 when successful.")
	 public String hello(@PathVariable("name") String name) {
	        log.info("Hello, name: " + name);
	        return "Hello " + name;
	 }
	 
	 @GetMapping("")
	 @ApiOperation(value = "Hello world test",
     notes = "Returns a 200 when successful.")
	 public String helloWorld() {
	        log.info("Hello...");
	        return "Hello World!";
	 }

}
