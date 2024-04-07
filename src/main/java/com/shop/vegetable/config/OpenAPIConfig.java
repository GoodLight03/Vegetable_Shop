// package com.shop.vegetable.config;

// import org.springdoc.core.GroupedOpenApi;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Contact;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.info.License;
// import springfox.documentation.builders.ApiInfoBuilder;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;
// import springfox.documentation.swagger2.annotations.EnableSwagger2;

// @Configuration
// @EnableSwagger2
// public class OpenAPIConfig {

//   @Bean
//   public Docket api() {
//     return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
//         .apiInfo(apiInfo())
//         .select()
//         .apis(RequestHandlerSelectors.basePackage("com.shop.vegetable.api"))
//         .build();
//   }

//   private ApiInfo apiInfo() {

//     return new ApiInfoBuilder().title("NAME OF SERVICE")
//         .description("API Endpoint Decoration")
//         .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
//         .version("1.0.0")
//         .build();
//   }

//   // @Bean
//   // public OpenAPI customOpenAPI(
//   // @Value("${application-description}") String appDesciption,
//   // @Value("${application-version}") String appVersion) {

//   // return new OpenAPI()
//   // .info(new Info().title("BOOK REST API Document")
//   // .version(appVersion)
//   // .contact(new Contact().name("Nguyen Thien
//   // Quang").email("nguyenthienquang20022003@gmail.com").url("https://techmaster.vn"))
//   // .description(appDesciption)
//   // .termsOfService("http://swagger.io/terms/")
//   // .license(new License().name("Apache 2.0")
//   // .url("http://springdoc.org")));
//   // }

//   // @Bean
//   // public GroupedOpenApi bookOpenApi() {
//   // String paths[] = {"/api/type/**"};
//   // return GroupedOpenApi.builder().group("types").pathsToMatch(paths)
//   // .build();
//   // }

//   // @Bean
//   // public GroupedOpenApi contactOpenApi() {
//   // String paths[] = {"/api/product/**"};
//   // return GroupedOpenApi.builder().group("products").pathsToMatch(paths)
//   // .build();
//   // }
// }
