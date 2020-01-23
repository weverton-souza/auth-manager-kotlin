package auth.manager.domain

import auth.manager.domain.interfaces.IDomain
import auth.manager.dto.ProfileDTO
import auth.manager.enums.Role
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.authentication.jaas.AuthorityGranter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors
import java.util.stream.Stream

@Document
class Profile (private val id: String?,
               private val nickname: String?,
               private val username: String?,
               private val accountNonExpired: Boolean?,
               private val accountNonLocked: Boolean?,
               private val credentialsNonExpired: Boolean?,
               private val enabled: Boolean?,
               private val password: String?,
               private val authorities: Collection<AuthorityGranter>?): IDomain, UserDetails {

//        override fun getAuthorities(): MutableCollection<out GrantedAuthority>? = this.authorities
//        private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
//
//        }

        override fun getAuthorities(): Collection<GrantedAuthority> {
            val authorities: ArrayList<GrantedAuthority> = ArrayList()

            Role.values().forEach { role -> authorities.add(SimpleGrantedAuthority(role.name)) }
            Role.values().forEach { role -> authorities.add(SimpleGrantedAuthority(role.name)) }
            return authorities
        }

        override fun isEnabled(): Boolean = this.enabled!!

        override fun getUsername(): String = this.username!!

        override fun isCredentialsNonExpired(): Boolean = this.credentialsNonExpired!!

        override fun getPassword(): String = this.password!!

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
                    .password(this.password!!)
                    .authorities(this.authorities!!)
                    .build()

        class Builder( var id: String? = null,
                       var nickname: String? = null,
                       var username: String? = null,
                       var accountNonExpired: Boolean? = false,
                       var accountNonLocked: Boolean? = false,
                       var credentialsNonExpired: Boolean? = false,
                       var enabled: Boolean? = false,
                       var password: String? = null,
                       var authorities: Collection<AuthorityGranter>? = null) {

            fun id(id: String) = apply { this.id = id }
            fun nickname(nickname: String) = apply { this.nickname = nickname }
            fun username(username: String) = apply { this.username = username }
            fun accountNonExpired(accountNonExpired: Boolean) = apply { this.accountNonExpired = accountNonExpired }
            fun accountNonLocked(accountNonLocked: Boolean) = apply { this.accountNonLocked = accountNonLocked }
            fun credentialsNonExpired(credentialsNonExpired: Boolean) = apply { this.credentialsNonExpired = credentialsNonExpired }
            fun enabled(enabled: Boolean) = apply { this.enabled = enabled }
            fun password(password: String) = apply { this.password = password }
            fun authorities(authorities: Collection<AuthorityGranter>) = apply { this.authorities = authorities }
            fun build() = Profile(id, nickname, username, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, password, authorities)
        }

}
