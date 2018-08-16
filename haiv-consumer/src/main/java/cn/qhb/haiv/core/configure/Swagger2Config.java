package cn.qhb.haiv.core.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2接口文档配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * swagger2标题描述
     */
    @Value("${custom.swagger2_description}")
    private String SWAGGER2_DESCRIPTION;
    /**
     * swagger2扫描包
     */
    @Value("${custom.swagger2_basepackage}")
    private String SWAGGER2_BASEPACKAGE;
    /**
     * swagger2文档版本
     */
    @Value("${custom.swagger2_version}")
    private String SWAGGER2_VERSION;
    /**
     * swagger2
     */
    @Value("${custom.swagger2_title}")
    private String SWAGGER2_TITLE;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER2_BASEPACKAGE))    //"cn.qhb.haiv.controller"
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(SWAGGER2_TITLE)
                .description(SWAGGER2_DESCRIPTION)
                .version(SWAGGER2_VERSION)
                .build();
    }
}
