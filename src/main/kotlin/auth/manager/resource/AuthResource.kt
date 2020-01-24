package auth.manager.resource

import auth.manager.domain.Profile
import auth.manager.dto.AuthDTO
import auth.manager.dto.UserLogin
import auth.manager.generics.Response
import auth.manager.security.configuration.UserService
import auth.manager.security.jwt.JwtConfiguration
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/auth")
@Api(value = "Authentication", tags = ["Authentication"], description = "User Authentication")
class AuthResource(var userService: UserService,
                   val authenticationManager: AuthenticationManager,
                   val jwtConfiguration: JwtConfiguration) {

    @PostMapping
    @Throws(AuthenticationException::class)
    fun createAuthenticationToken(@RequestBody userLogin: UserLogin): Response<AuthDTO> {
        val authentication: Authentication = authenticationManager.authenticate (
                UsernamePasswordAuthenticationToken( userLogin.username, userLogin.userPassword )
        )
        SecurityContextHolder.getContext().authentication = authentication

        val profile: Profile = userService.loadUserByUsername(userLogin.username)
        val token: String? = this.jwtConfiguration.generateToken(profile.username)

        val authUser = AuthDTO(profile.id, profile.nickname, profile.username, token )

        return Response.Builder<AuthDTO>()
                .content(authUser)
                .code(HttpStatus.OK.value().toString())
                .message(HttpStatus.OK.reasonPhrase)
                .build()
    }
}
