package auth.manager.security.jwt

import auth.manager.dto.UserLogin
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtUsernameAndPasswordAuthenticationFilter: UsernamePasswordAuthenticationFilter() {
    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {
        return try {
            val userLogin: UserLogin = ObjectMapper()
                    .readValue(request.inputStream, UserLogin::class.java)
            val authToken = UsernamePasswordAuthenticationToken(userLogin.username, userLogin.userPassword)
            this.authenticationManager.authenticate(authToken)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}