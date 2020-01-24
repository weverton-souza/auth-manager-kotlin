package auth.manager.security.jwt

import auth.manager.security.configuration.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(authenticationManager: AuthenticationManager,
                              private val jwtConfiguration: JwtConfiguration,
                              private val userService: UserService) : BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")
        if (header != null && header.startsWith("Bearer")) {
            val authenticationToken = getAuthentication(header.substring(7))
            if (authenticationToken != null) {
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        if (this.jwtConfiguration.isValidToken(token)) {
            val username: String? = this.jwtConfiguration.getUserName(token)
            val userDetail: UserDetails = this.userService.loadUserByUsername(username!!)
            return UsernamePasswordAuthenticationToken(userDetail, null, userDetail.authorities)
        }
        return null
    }
}