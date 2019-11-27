package com.apigateway;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan()
@EnableScheduling
@EnableMBeanExport(defaultDomain="apiGateway")
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}
	
//	   @Bean
//	    public Docket api() {
//		    List<SecurityContext> security = new ArrayList<SecurityContext>();
//		    security.add(securityContext());
//	        return new Docket(DocumentationType.SWAGGER_2)
//	                .select()
//	                .apis(RequestHandlerSelectors.basePackage("com.apigateway"))
//	                .paths(PathSelectors.any())
//	                .build()
//	                .apiInfo(this.apiInfo())
//	                .useDefaultResponseMessages(true);
//	    }
//	   @Bean
//	   public Docket api() {
//	       return new Docket(DocumentationType.SWAGGER_2).select()
//	               .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//	               .paths(PathSelectors.any()).build().pathMapping("/apigateway")
//	               .apiInfo(apiInfo()).useDefaultResponseMessages(false);
//	   }
	   
	   private SecurityContext securityContext() {
		    return SecurityContext.builder()
		            .forPaths(PathSelectors.regex("/"))
		            .build();
	   }
	   
//	   @Bean
//	   ApiInfo apiInfo() {
//	       final ApiInfoBuilder builder = new ApiInfoBuilder();
//	       builder.title("BlazeMeter Spring Boot API").version("1.0").license("(C) Copyright BlazeMeter")
//	               .description("List of all endpoints used in API");
//	       return builder.build();
//	   }
//	    private ApiInfo apiInfo() {
//	    	@SuppressWarnings("deprecation")
//			ApiInfo apiInfo = new ApiInfo(
//	    			"1",
//	    			"2",
//	    			"api java sprinf boot",
//	    			"5",
//	    			"Licence of api",
//	    			"Api Licence Url", null
//	    			);
//	    	return apiInfo;
//	    }

}
