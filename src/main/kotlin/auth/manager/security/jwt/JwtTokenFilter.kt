package auth.manager.security.jwt

import auth.manager.exceptions.ExceptionHandler
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication;


class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider): OncePerRequestFilter() {

    override fun doFilterInternal(httpServletRequest: HttpServletRequest,
                                  httpServletResponse: HttpServletResponse, filterChain: FilterChain) {
        val token: String = jwtTokenProvider.resolveToken(httpServletRequest)
        try {
            if (jwtTokenProvider.validateToken(token)) {
                val auth: Authentication = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (ex: ExceptionHandler) {
            SecurityContextHolder.clearContext()
            httpServletResponse.sendError(ex.httpStatus.value(), ex.message)
            return
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }
}