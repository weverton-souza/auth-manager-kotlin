package auth.manager.security.jwt

import auth.manager.generics.Response
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException
import java.io.OutputStream
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class RestAuthenticationEntryPoint: AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(httpServletRequest: HttpServletRequest?, httpServletResponse: HttpServletResponse, e: AuthenticationException?) {
        val out: OutputStream = httpServletResponse.outputStream
        val mapper = ObjectMapper()

        out.flush()
    }
}