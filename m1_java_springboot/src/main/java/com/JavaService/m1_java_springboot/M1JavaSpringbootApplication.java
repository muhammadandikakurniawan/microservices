package com.JavaService.m1_java_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.JavaService.m1_java_springboot.filter.AuthFilter;

@SpringBootApplication
@EnableSwagger2
@ComponentScan()
@EnableScheduling
@EnableMBeanExport(defaultDomain="m1_java_springboot")
public class M1JavaSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(M1JavaSpringbootApplication.class, args);
	}

    @Bean
    public FilterRegistrationBean FilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthFilter());
// In case you want the filter to apply to specific URL patterns only
        registration.addUrlPatterns("/m1_java_springboot/*");
        registration.setOrder(1);
        return registration;
    }
    
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.JavaService.m1_java_springboot"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(this.apiInfo())
//                .useDefaultResponseMessages(true);
//    }
//    
//    private ApiInfo apiInfo() {
//    	ApiInfo apiInfo = new ApiInfo(
//    			"1",
//    			"2",
//    			"api java sprinf boot",
//    			"5",
//    			"Licence of api",
//    			"Api Licence Url", null
//    			);
//    	return apiInfo;
//    }
}
