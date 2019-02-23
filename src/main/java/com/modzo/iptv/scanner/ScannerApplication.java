package com.modzo.iptv.scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@EnableSwagger2
public class ScannerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ScannerApplication.class, args);
	}
}