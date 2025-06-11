package com.techblog.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Tech Blog API",
				version = "1.0",
				description = "Mô tả API cho hệ thống TechBlog"
		),
		tags = {
				@io.swagger.v3.oas.annotations.tags.Tag(name = "User Authentication", description = "Endpoints cho người dùng đăng kí, đăng nhập vào hệ thống")
		},
		servers = {
				@io.swagger.v3.oas.annotations.servers.Server(url = "http://localhost:8080", description = "Local server")
		}
)
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
