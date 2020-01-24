package auth.manager.security.configuration

import auth.manager.security.jwt.JwtAuthenticationFilter
import auth.manager.security.jwt.JwtConfiguration
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
class WebSecurityConfiguration(val userService: UserService,
                               val jwtConfiguration: JwtConfiguration): WebSecurityConfigurerAdapter(), WebMvcConfigurer {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .cors()
                .disable()
                .csrf()
                .disable();
        http
                .addFilter(JwtAuthenticationFilter(authenticationManager()!!,
                        this.jwtConfiguration, userService))
        http
                .authorizeRequests()
                .anyRequest().authenticated()
        http.
                sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/v2/api-docs") //
                .antMatchers("/swagger-resources/**") //
                .antMatchers("/swagger-ui.html") //
                .antMatchers("/configuration/**") //
                .antMatchers("/webjars/**") //
                .antMatchers("/public")
                .antMatchers("/auth/**")
                .antMatchers(HttpMethod.POST, "/profiles/**")
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager? {
        return super.authenticationManager()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(this.userService).passwordEncoder(bCryptPasswordEncoder())
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}