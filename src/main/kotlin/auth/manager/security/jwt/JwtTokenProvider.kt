package auth.manager.security.jwt

import auth.manager.exceptions.ExceptionHandler
import auth.manager.security.configuration.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.commons.lang3.time.DateUtils
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
class JwtTokenProvider(val userService: UserService) {
    val secretKey: String = "5EstjYPSVvWK5m3sPPR7H29sr34mk5Rf7Z7BeUuVkVQxnap8MVBdQMmSgaAUnAQkymAaqMeFuqQND5xGzhgRc39QtECbd9zx8xFe"

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = this.userService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun createToken(username: String?, roles: Collection<GrantedAuthority>): String {
        val claims: Claims = Jwts.claims().setSubject(username)
        claims["auth"] = roles.stream().map { s -> SimpleGrantedAuthority(s.authority) }.filter(Objects::nonNull).collect(Collectors.toList())
        val now = Date()
        val validity = DateUtils.addHours(Date(), 1);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    fun getUsername(token: String?): String =  Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject

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
            e.printStackTrace()
            throw ExceptionHandler("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: IllegalArgumentException) {
            throw ExceptionHandler("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        }
}
