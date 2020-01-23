package auth.manager.swagger

import com.google.common.base.Predicates
import com.google.common.collect.Lists
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*


@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("auth.manager.resource"))
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(metadata())
                .useDefaultResponseMessages(false)
                .securitySchemes(listOf(ApiKey("JWT", "Authorization", "header")))
                .securityContexts(Lists.newArrayList(securityContext()))
                .genericModelSubstitutes(Optional::class.java)

    private fun metadata(): ApiInfo = ApiInfoBuilder()
                .title("Auth Manager 1.0")
                .description("Java service with jwt")
                .version("1.0") //
                .contact(Contact("Weverton Souza", null, "wevreton.souza@zup.com.br"))
                .build()

    @Bean
    fun securityContext(): SecurityContext = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build()

    fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return Lists.newArrayList(
                SecurityReference("JWT", authorizationScopes))
    }
}