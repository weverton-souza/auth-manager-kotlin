package auth.manager.resource

import auth.manager.domain.Profile
import auth.manager.dto.ProfileDTO
import auth.manager.dto.UserLogin
import auth.manager.generics.Response
import auth.manager.security.configuration.UserService
import auth.manager.security.jwt.JwtTokenProvider
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping("/auth")
@Api(value = "Auth", description = "User Authorization")
class AuthResource(var userService: UserService,
                   val authenticationManager: AuthenticationManager,
                   val jwtTokenProvider: JwtTokenProvider) {

    @PostMapping
    @Throws(AuthenticationException::class)
    fun createAuthenticationToken(@RequestBody userLogin: UserLogin): ResponseEntity<*>? {
        val authentication: Authentication = authenticationManager.authenticate (
                UsernamePasswordAuthenticationToken( userLogin.username, userLogin.userPassword )
        )
        SecurityContextHolder.getContext().authentication = authentication

        val profile: Profile = userService.loadUserByUsername(userLogin.username)
        val token: String = jwtTokenProvider.createToken(profile.username, profile.authorities)

        return ResponseEntity.ok<Any>(token)
    }

//    @GetMapping("/sign-in")
//    @ApiOperation(value = "Signing in of the user")
//    fun signIn(@RequestBody @Valid userLogin: UserLogin): Response<ProfileDTO> = Response.Builder<ProfileDTO>()
//                .content(this.userService.loadUserByUsername(userLogin.username).toDTO())
//                .code(HttpStatus.OK.name)
//                .message(HttpStatus.OK.reasonPhrase)
//                .build()
//
//    @PostMapping("/sign-up")
//    @ApiOperation(value = "Sign up in of the user")
//    fun signUp(@RequestBody @Valid profileDTO: ProfileDTO): Response<ProfileDTO> = Response.Builder<ProfileDTO>()
//            .content(this.userService.saveOrUpdate(profileDTO))
//            .code(HttpStatus.CREATED.name)
//            .message(HttpStatus.CREATED.reasonPhrase)
//            .build()

//    @GetMapping("/refresh")
//    fun refreshAndGetAuthenticationToken(request: HttpServletRequest): ResponseEntity<*>? {
//        val token = request.getHeader(tokenHeader)
//        val username: String = jwtTokenUtil.getUsernameFromToken(token)
//        val user: JwtUser = userDetailsService.loadUserByUsername(username) as JwtUser
//        return if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
//            val refreshedToken: String = jwtTokenUtil.refreshToken(token)
//            ResponseEntity.ok<Any>(JwtAuthenticationResponse(refreshedToken))
//        } else {
//            ResponseEntity.badRequest().body<Any>(null)
//        }
//    }
}
