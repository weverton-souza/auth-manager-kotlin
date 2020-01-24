package auth.manager.security.jwt

import io.jsonwebtoken.Claims

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

import java.util.*

@Component
class JwtConfiguration {
    val secretKey: String = "5EstjYPSVvWK5m3sPPR7H29sr34mk5Rf7Z7BeUuVkVQxnap8MVBdQMmSgaAUnAQkymAaqMeFuqQND5xGzhgRc39QtECbd9zx8xFe"

    fun generateToken(username: String?): String? {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(Date(System.currentTimeMillis() + 36000000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    fun isValidToken(token: String): Boolean {
        val claims = getClaims(token)
        if (claims != null) {
            val username = claims.subject
            val expirationDate: Date? = claims.expiration
            val now = Date(System.currentTimeMillis())
            return username != null && expirationDate != null && now.before(expirationDate)
        }
        return false
    }

    private fun getClaims(token: String): Claims? {
        return try {
            Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).body
        } catch (e: Exception) {
            Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).body
        }
    }

    fun getUserName(token: String): String? {
        val claims = getClaims(token)
        return claims?.subject
    }
}