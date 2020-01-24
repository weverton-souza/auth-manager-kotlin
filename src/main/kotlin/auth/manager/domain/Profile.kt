package auth.manager.domain

import auth.manager.domain.interfaces.IDomain
import auth.manager.dto.ProfileDTO
import auth.manager.enumeration.Role
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

@Document
data class Profile(val id: String?,
                    val nickname: String?,
                    private val username: String?,
                    val accountNonExpired: Boolean?,
                    val accountNonLocked: Boolean?,
                    val credentialsNonExpired: Boolean?,
                    val enabled: Boolean?,
                    private val password: String?,
                    private var authorities: Collection<SimpleGrantedAuthority>?): IDomain, UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return authorities
                ?.stream()
                ?.map { x: GrantedAuthority -> SimpleGrantedAuthority(x.authority) }
                ?.collect(Collectors.toList())
    }

    override fun isEnabled(): Boolean = this.enabled!!

    override fun getUsername(): String = this.username!!

    override fun isCredentialsNonExpired(): Boolean = this.credentialsNonExpired!!

    override fun getPassword(): String? = this.password

    override fun isAccountNonExpired(): Boolean = this.accountNonExpired!!

    override fun isAccountNonLocked(): Boolean = this.accountNonLocked!!

    override fun toDTO(): ProfileDTO = ProfileDTO
            .Builder()
                .id(this.id!!)
                .nickname(this.nickname!!)
                .username(this.username!!)
                .accountNonExpired(this.accountNonExpired!!)
                .accountNonLocked(this.accountNonLocked!!)
                .credentialsNonExpired(this.credentialsNonExpired!!)
                .enabled(this.enabled!!)
                .authorities( this.authorities!!.stream().map { Role.valueOf(it.authority) }.collect(Collectors.toList()) )
                .build()

        class Builder(private var id: String? = null,
                      private var nickname: String? = null,
                      private var username: String? = null,
                      private var accountNonExpired: Boolean? = false,
                      private var accountNonLocked: Boolean? = false,
                      private var credentialsNonExpired: Boolean? = false,
                      private var enabled: Boolean? = false,
                      private var password: String? = null,
                      private var authorities: Collection<SimpleGrantedAuthority>? = null) {

            fun id(id: String) = apply { this.id = id }
            fun nickname(nickname: String) = apply { this.nickname = nickname }
            fun username(username: String) = apply { this.username = username }
            fun accountNonExpired(accountNonExpired: Boolean) = apply { this.accountNonExpired = accountNonExpired }
            fun accountNonLocked(accountNonLocked: Boolean) = apply { this.accountNonLocked = accountNonLocked }
            fun credentialsNonExpired(credentialsNonExpired: Boolean) = apply { this.credentialsNonExpired = credentialsNonExpired }
            fun enabled(enabled: Boolean) = apply { this.enabled = enabled }
            fun password(password: String) = apply { this.password = password }
            fun authorities(authorities: Collection<SimpleGrantedAuthority>) = apply {
                this.authorities = authorities
            }
            fun build() = Profile(id, nickname, username, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, password, authorities)
        }

}
