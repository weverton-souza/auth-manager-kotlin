package auth.manager.security.configuration

import auth.manager.security.jwt.JwtTokenFilterConfigurer
import auth.manager.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(var jwtTokenProvider: JwtTokenProvider): WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests()
                .antMatchers("/profiles/**").hasRole("ADMIN_LEV0")
                .anyRequest().authenticated()
        http.apply(JwtTokenFilterConfigurer(jwtTokenProvider));
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

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}