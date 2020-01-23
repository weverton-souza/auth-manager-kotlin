package auth.manager.dto

import auth.manager.domain.Profile
import auth.manager.dto.interfaces.IDTO
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.security.authentication.jaas.AuthorityGranter
import org.springframework.security.core.GrantedAuthority

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProfileDTO(var id: String?,
                      var nickname: String?,
                      var username: String?,
                      var accountNonExpired: Boolean?,
                      var accountNonLocked: Boolean?,
                      var credentialsNonExpired: Boolean?,
                      var enabled: Boolean?,
                      var password: String?,
                      var authorities: Collection<AuthorityGranter>?): IDTO {

    override fun toDomain(): Profile = Profile
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

    data class Builder(var id: String? = null,
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
        fun build() = ProfileDTO(id, nickname, username, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, password, authorities)
    }
}