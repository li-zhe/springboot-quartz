package com.example.springbootquartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@MapperScan("com.example.**.mapper")
@SpringBootApplication
public class SpringbootQuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootQuartzApplication.class, args);
	}

}
