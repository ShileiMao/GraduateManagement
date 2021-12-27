package io.github.xmchxup.latticy.config;

import io.swagger.annotations.ApiOperation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Parameter;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	// 定义分隔符
	private static final String splitor = ";";

	// http://localhost:5000/swagger-ui.html#/
	@Bean
	Docket docket() {

		//在配置好的配置类中增加此段代码即可
		ParameterBuilder ticketPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<>();
		ticketPar.name("Authorization").description("登录校验")//name表示名称，description表示描述
				.modelRef(new ModelRef("string")).parameterType("header")
				.required(false).defaultValue("Bearer ").build();
		pars.add(ticketPar.build());

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
//                .apis(basePackage("io.github.xmchxup.latticy.controller.v1")) 这里采用包扫描的方式来确定要显示的接口
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))  //这里采用包含注解的方式来确定要显示的接口
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder()
				.title("毕设管理系统")
				.description("毕设管理系统  Api文档")
				.termsOfServiceUrl("https://xmchxup.github.io/")
				.version("1.0")
				.build();
	}
}
