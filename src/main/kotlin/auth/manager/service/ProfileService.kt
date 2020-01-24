package auth.manager.service

import auth.manager.domain.Profile
import auth.manager.dto.ProfileDTO
import auth.manager.repository.ProfileRepository
import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Weverton Souza.
 * Created on 23/01/2020
 */
@Service
class ProfileService(private val profileRepository: ProfileRepository,
                     private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    fun saveOrUpdate(profileDTO: ProfileDTO): ProfileDTO {
        if(profileDTO.id == null) {
            profileDTO.id = UUID.randomUUID().toString()
        }

        profileDTO.password = bCryptPasswordEncoder.encode(profileDTO.password)
        return this.profileRepository.save(profileDTO.toDomain()).toDTO()
    }

    fun findById(id: String): ProfileDTO {
        return this.profileRepository.findById(id).map { iDomain -> iDomain.toDTO()  }.get()
    }

    @PreAuthorize("hasRole('ADMIN_LEV0')")
    fun findAll(): MutableList<Profile> {
        return this.profileRepository.findAll()
    }
}