package auth.manager.security.configuration

import auth.manager.domain.Profile
import auth.manager.dto.ProfileDTO
import auth.manager.repository.ProfileRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProfileService(val profileRepository: ProfileRepository): UserDetailsService {

    fun saveOrUpdate(profileDTO: ProfileDTO): ProfileDTO {
        if(profileDTO.id == null) {
            profileDTO.id = UUID.randomUUID().toString()
        }
        return this.profileRepository.save(profileDTO.toDomain()).toDTO()
    }

    fun findById(id: String): ProfileDTO {
        return this.profileRepository.findById(id).map { iDomain -> iDomain.toDTO()  }.get()
    }

    override fun loadUserByUsername(email: String): Profile {
        val optProfile: Optional<Profile> =  this.profileRepository.findByUsername(email)
        if(!optProfile.isPresent)
            throw Exception()
        return optProfile.get()
    }
}