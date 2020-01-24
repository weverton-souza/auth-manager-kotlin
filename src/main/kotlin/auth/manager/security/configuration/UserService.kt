package auth.manager.security.configuration

import auth.manager.domain.Profile
import auth.manager.repository.ProfileRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(val profileRepository: ProfileRepository): UserDetailsService {
    override fun loadUserByUsername(email: String): Profile {
        val optProfile: Optional<Profile> =  this.profileRepository.findByUsername(email)
        if(!optProfile.isPresent)
            throw Exception()
        return optProfile.get()
    }
}
