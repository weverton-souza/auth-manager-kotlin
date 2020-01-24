package auth.manager.service

import auth.manager.dto.ProfileDTO
import auth.manager.generics.Response
import auth.manager.repository.ProfileRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

/**
 * @author Weverton Souza.
 * Created on 23/01/2020
 */
@Service
class ProfileService(private val profileRepository: ProfileRepository,
                     private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    fun saveOrUpdate(profileDTO: ProfileDTO): Response<ProfileDTO> {
        if (profileDTO.id == null) {
            profileDTO.id = UUID.randomUUID().toString()
        }

        if (null != profileDTO.password) {
            profileDTO.password = bCryptPasswordEncoder.encode(profileDTO.password)
        }

        val profileDTO2: ProfileDTO =  this.profileRepository.save(profileDTO.toDomain()).toDTO()

        return Response.Builder<ProfileDTO>()
                .content(profileDTO2)
                .code(HttpStatus.OK.name)
                .message(HttpStatus.OK.reasonPhrase)
                .build()
    }

    fun findById(id: String): Response<ProfileDTO> {
        val profileDTO: ProfileDTO = this.profileRepository.findById(id).map { iDomain -> iDomain.toDTO()  }.get()
        return Response.Builder<ProfileDTO>()
                .content(profileDTO)
                .code(HttpStatus.OK.name)
                .message(HttpStatus.OK.reasonPhrase)
                .build()
    }

    fun findAll(): Response<MutableList<ProfileDTO>> {
        return Response.Builder<MutableList<ProfileDTO>>()
                .content(this.profileRepository.findAll()
                    .stream()
                    .map { it.toDTO() }
                    .collect(Collectors.toList()))
                .code(HttpStatus.OK.name)
                .message(HttpStatus.OK.reasonPhrase)
                .build()
    }

    fun delete(id: String) {
        this.profileRepository.findById(id)
                .ifPresent(this.profileRepository::delete)
    }
}