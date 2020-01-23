package auth.manager.security.jwt

import auth.manager.enums.Role
import auth.manager.exceptions.ExceptionHandler
import auth.manager.security.configuration.ProfileService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider(val profileService: ProfileService) {
    val secretKey: String = ""
    val validityInMilliseconds: String = ""

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = this.profileService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun createToken(username: String?, roles: Collection<GrantedAuthority>): String {
        val claims: Claims = Jwts.claims().setSubject(username)
        claims["auth"] = roles.stream().map { s -> SimpleGrantedAuthority(s.authority) }.filter(Objects::nonNull).collect(Collectors.toList())
        val now = Date()
        val validity = Date(now.time.toString() + validityInMilliseconds)
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    fun getUsername(token: String?): String=  Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject

    fun resolveToken(req: HttpServletRequest): String {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null.toString()
    }

    fun validateToken(token: String): Boolean = try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            throw ExceptionHandler("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: IllegalArgumentException) {
            throw ExceptionHandler("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        }
}
