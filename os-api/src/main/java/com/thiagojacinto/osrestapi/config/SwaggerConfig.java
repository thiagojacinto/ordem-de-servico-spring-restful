package com.thiagojacinto.osrestapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Gestão Ordem de Serviço, em API REST")
				.description("\"Um exemplo de construção de uma API RESTful para sistema de gestão de Ordem de Serviços, construída com Java 11, Spring 2.3+ e MySQL 8+.\"")
				.version("0.1.0")
				.license("MIT License")
				.licenseUrl("https://github.com/thiagojacinto/ordem-de-servico-spring-restful/blob/master/LICENSE")
				.contact(new Contact("Thiago Jacinto", "https://github.com/thiagojacinto", ""))
				.build();
	}

}
